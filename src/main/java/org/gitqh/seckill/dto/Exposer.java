package org.gitqh.seckill.dto;

/**
 * 暴露秒杀地址DTO
 * @author qh
 * @create 2016-06-19-22:49
 */
public class Exposer {

    //是否开启秒杀
    private boolean exposerd;

    //秒杀商品主键
    private long seckillId;

    //一种加密策略
    private String md5;

    //系统当前时间
    private long now;

    //秒杀开启时间
    private long start;

    //秒杀结束时间
    private long end;

    @Override
    public String toString() {
        return "Exposer{" +
                "exposerd=" + exposerd +
                ", seckillId=" + seckillId +
                ", md5='" + md5 + '\'' +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    public Exposer(boolean exposerd, long seckillId, long start, long now, long end) {
        this.exposerd = exposerd;
        this.seckillId = seckillId;
        this.start = start;
        this.now = now;
        this.end = end;
    }

    public Exposer(boolean exposerd, long seckillId, String md5) {
        this.exposerd = exposerd;
        this.seckillId = seckillId;
        this.md5 = md5;
    }

    public Exposer(boolean exposerd, long seckillId) {
        this.exposerd = exposerd;
        this.seckillId = seckillId;
    }

    public boolean isExposerd() {
        return exposerd;
    }

    public void setExposerd(boolean exposerd) {
        this.exposerd = exposerd;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
