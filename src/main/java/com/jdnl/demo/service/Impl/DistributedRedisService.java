package com.jdnl.demo.service.Impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

@Service
public class DistributedRedisService implements com.jdnl.demo.service.DistributedRedisService {
    Jedis jedis;

    private Logger log = Logger.getLogger(getClass());
    private static final String LOCK_SUCCESS = "OK";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final String lockKey = "key";

    public String acquire() {
        try {
            long acquireTimeout = 1000 * 10;
            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            // 随机生成一个 value
            String requireToken = UUID.randomUUID().toString();
            while (System.currentTimeMillis() < end) {

                String result = jedis.set(lockKey, requireToken, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

                if (LOCK_SUCCESS.equals(result)) {
                    return requireToken;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            log.error("acquire lock due to error", e);
        }

        return null;
    }

    public boolean release(String identify) {
        if (identify == null) {
            return false;
        }

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = new Object();
        try {

            result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(identify));

            if (RELEASE_SUCCESS.equals(result)) {
                log.info("release lock success, requestToken:" + identify);
                return true;
            }
        } catch (Exception e) {
            log.error("release lock due to error", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        log.info("release lock failed, requestToken:" + identify + "result:" + result);

        return false;
    }
}
