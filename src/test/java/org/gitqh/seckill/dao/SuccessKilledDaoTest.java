package org.gitqh.seckill.dao;

import org.gitqh.seckill.entity.Seckill;
import org.gitqh.seckill.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by qh on 2016/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    /**
     * 插入一条秒杀成功的记录信息
     * @throws Exception
     */
    @Test
    public void insertSuccessKilled() throws Exception {
        System.out.println(successKilledDao.insertSuccessKilled(1000L,15201208718L));
    }

    @Test
    public void queryByIdWithPhone() throws Exception {
        SuccessKilled successKilled = successKilledDao.queryByIdWithPhone(1000L,15201208718L);
        Seckill seckill = successKilled.getSeckill();
        System.out.println(successKilled);
        System.out.println(seckill);
    }

}