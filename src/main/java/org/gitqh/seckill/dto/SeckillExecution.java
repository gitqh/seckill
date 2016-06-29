package org.gitqh.seckill.dto;

import org.gitqh.seckill.entity.SuccessKilled;
import org.gitqh.seckill.enums.SeckillEnum;

/**
 * @author qh
 * @create 2016-06-19-23:02
 */
public class SeckillExecution {

    //秒杀商品Id
    private long seckillId;

    //秒杀状态
    private int state;

    //秒杀状态信息
    private String stateInfo;

    //秒杀成功对象
    private SuccessKilled successKilled;

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }

    //此处state，stateInfo不用枚举，因为json转换枚举有bug
    public SeckillExecution(long seckillId, SeckillEnum seckillEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillEnum.getState();
        this.stateInfo = seckillEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckillId, SeckillEnum seckillEnum) {
        this.seckillId = seckillId;
        this.state = seckillEnum.getState();
        this.stateInfo = seckillEnum.getStateInfo();
    }


    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }
}
