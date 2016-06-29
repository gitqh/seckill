package org.gitqh.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.gitqh.seckill.entity.SuccessKilled;

/**
 * @author qh
 * @create 2016-06-13-23:08
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细
     * @param seckillId
     * @param userPhone
     * @return 返回插入数量 0表示插入失败
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据手机号查询成功秒杀明细
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithPhone(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
