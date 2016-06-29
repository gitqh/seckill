-- 秒杀执行存储过程 --
DELIMITER $$
BEGIN
    DECLARE insert_count int DEFAULT 0;
    START TRANSACTION;
INSERT IGNORE INTO success_killed
(seckill_id,user_phone,create_time)
VALUES(v_seckill_id,v_phone,v_kill_time);
SELECT ROW_COUNT() INTO insert_count;
IF (insert_count = 0) THEN
ROLLBACK;
SET r_result = -1;
ELSEIF (insert_count < 0) THEN
ROLLBACK;
SET r_result = -2;
ELSE
UPDATE seckill SET number = number - 1 WHERE seckill_id = v_seckill_id
AND v_kill_time > start_time AND v_kill_time < end_time AND number > 0;
SELECT ROW_COUNT() into insert_count;
IF (insert_count = 0) THEN
SET r_result = 0;
ELSEIF (insert_count < 0) THEN
SET r_result = -2;
ELSE
SET r_result = 1;
END IF;
END IF;
END
-- 定义结束

DELIMITER ;
SET @r_result = -3;
call execute_seckill(1001,13508900000,NOW(),@r_result);
SELECT @r_result;


-- 1：存储过程优化：事务行级锁持有的时间
-- 2：不要过度依赖存储过程 银行业使用较多
-- 3：简单的逻辑可以应用存储过程
-- 4：QPS:一个秒杀单6000/qps