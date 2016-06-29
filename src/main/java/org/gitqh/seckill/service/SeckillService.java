package org.gitqh.seckill.service;

import org.gitqh.seckill.dto.SeckillExecution;
import org.gitqh.seckill.dto.Exposer;
import org.gitqh.seckill.entity.Seckill;
import org.gitqh.seckill.exception.RepeatKillException;
import org.gitqh.seckill.exception.SeckillCloseException;
import org.gitqh.seckill.exception.SeckillException;

import java.util.List;

/**
 * 站在使用者角度设计
 * 关注接口方法粒度，参数，返回值
 * 要尽量简单
 * @author qh
 * @create 2016-06-19-22:44
 */
public interface SeckillService {

    /**
     * 获取全部秒杀商品列表
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 根据id获取秒杀商品
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);


    /**
     * 秒杀未开启输出系统时间和秒杀开启时间
     * 秒杀开启输出秒杀接口地址
     * @param seckillId
     * return exposer
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
      * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution seckillExecute(long seckillId, long userPhone, String md5)
        throws RepeatKillException,SeckillCloseException,SeckillException;

    /**
     * 执行秒杀操作使用存储过程
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution seckillExecuteProcedure(long seckillId, long userPhone, String md5);
}
