package org.gitqh.seckill.service.impl;

import org.gitqh.seckill.dto.Exposer;
import org.gitqh.seckill.dto.SeckillExecution;
import org.gitqh.seckill.entity.Seckill;
import org.gitqh.seckill.exception.RepeatKillException;
import org.gitqh.seckill.exception.SeckillCloseException;
import org.gitqh.seckill.exception.SeckillException;
import org.gitqh.seckill.service.SeckillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by qh on 2016/6/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                        "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(SeckillServiceImplTest.class);

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("seckillList:{}",seckillList);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000L);
        logger.info("seckill:{}", seckill);
    }

    @Test
    public void seckillExecuteLogic() throws Exception {
        long seckillId = 1000L;
        long userPhone = 15201208711L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposerd()) {
            logger.info("exposer:{}",exposer);
            try {
                String md5 = exposer.getMd5();
                SeckillExecution seckillExecution = seckillService.seckillExecute(seckillId,userPhone,md5);
                logger.info("SeckillExecution:{}",seckillExecution);
            } catch (RepeatKillException | SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Test
    public void executeSeckillProcedure(){
        long seckillId= 1000L;
        long phone = 13578976788L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposerd()){
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.seckillExecuteProcedure(seckillId,phone,md5);
            logger.info(execution.getStateInfo());
        }
    }

}