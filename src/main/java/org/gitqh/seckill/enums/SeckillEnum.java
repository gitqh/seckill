package org.gitqh.seckill.enums;

/**
 * 数据字典类，建议使用枚举
 * @author qh
 * @create 2016-06-20-11:54
 */
public enum SeckillEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEATE_KILL(-1,"重复秒杀"),
    INNOR_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");

    SeckillEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static SeckillEnum stateOf(int index) {
        for (SeckillEnum state:values()){
            if (state.getState() == index){
                return state;
            }
        }
        return null;
    }
    private int state;

    private String stateInfo;

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
}


