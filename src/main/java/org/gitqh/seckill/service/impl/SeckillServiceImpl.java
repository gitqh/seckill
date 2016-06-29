package org.gitqh.seckill.service.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.gitqh.seckill.dao.SeckillDao;
import org.gitqh.seckill.dao.SuccessKilledDao;
import org.gitqh.seckill.dao.cache.RedisDao;
import org.gitqh.seckill.dto.SeckillExecution;
import org.gitqh.seckill.dto.Exposer;
import org.gitqh.seckill.entity.Seckill;
import org.gitqh.seckill.entity.SuccessKilled;
import org.gitqh.seckill.enums.SeckillEnum;
import org.gitqh.seckill.exception.RepeatKillException;
import org.gitqh.seckill.exception.SeckillCloseException;
import org.gitqh.seckill.exception.SeckillException;
import org.gitqh.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qh
 * @create 2016-06-19-23:19
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private RedisDao redisDao;

    //MD5盐值
    private final String slat = "!@#$%^&*AHIEDHG*&^^";

    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        //优化点：缓存优化：超时的基础上维护一致性
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null){
            //访问数据库
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null){
                return new Exposer(false, seckillId);
            }else {
                //放入redis
                redisDao.putSeckill(seckill);
            }
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() > endTime.getTime() || nowTime.getTime() < startTime.getTime()) {
            return new Exposer(false, seckillId, startTime.getTime(), nowTime.getTime(), endTime.getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true, seckillId, md5);
    }

    //转换序列字符串，不可逆
    private String getMd5(long seckillId) {
        String str = seckillId + "/" + slat;
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    //执行秒杀操作，减库存，保存秒杀信息
    @Override
    @Transactional
    public SeckillExecution seckillExecute(long seckillId, long userPhone, String md5)
            throws RepeatKillException, SeckillCloseException, SeckillException {
        //判断md5
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("数据被篡改！");
        }

        Date nowTime = new Date();
        try {
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                throw new SeckillCloseException("秒杀已经结束！");
            } else {
                //减库存成功，记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    throw new SeckillException("重复秒杀");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithPhone(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillEnum.SUCCESS, successKilled);
                }
            }
        } catch (RepeatKillException e1) {
            throw e1;
        } catch (SeckillCloseException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //编译期异常转换为运行期异常
            throw new SeckillException("秒杀内部异常:" + e.getMessage());
        }
    }

    @Override
    public SeckillExecution seckillExecuteProcedure(long seckillId, long userPhone, String md5) {
        if (md5 == null || !md5.equals(getMd5(seckillId))){
            return new SeckillExecution(seckillId,SeckillEnum.DATA_REWRITE);
        }
        Date killTime = new Date();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("killTime",killTime);
        map.put("result",0);
        try {
            seckillDao.killByProcedure(map);
            int result = MapUtils.getInteger(map,"result",-2);
            if (result == 1){
                SuccessKilled sk = successKilledDao.queryByIdWithPhone(seckillId,userPhone);
                return new SeckillExecution(seckillId,SeckillEnum.SUCCESS,sk);
            }else {
                return new SeckillExecution(seckillId,SeckillEnum.stateOf(result));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new SeckillExecution(seckillId,SeckillEnum.INNOR_ERROR);
        }
    }

}
