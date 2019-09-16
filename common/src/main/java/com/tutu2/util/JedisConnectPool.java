package com.tutu2.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public abstract class JedisConnectPool {

    protected static JedisPool pool = null;
    // redis数据库, 几号库
    protected Integer RANDOM_DB_NUMBER;

    @Autowired
    protected Environment env;

    protected void check(){
        if (pool==null){
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(2000);
            config.setMaxIdle(10);
            config.setMinIdle(5);
            config.setLifo(true);
            config.setMaxWaitMillis(200);
            config.setMinEvictableIdleTimeMillis(1800000);
            config.setSoftMinEvictableIdleTimeMillis(1800000);
            config.setNumTestsPerEvictionRun(3);
            config.setTestOnBorrow(false);
            config.setTestWhileIdle(false);
            config.setTimeBetweenEvictionRunsMillis(-1);
            config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
            config.setBlockWhenExhausted(true);
            config.setJmxEnabled(false);
            pool = new JedisPool(config,env.getProperty("redis.ip"), Integer.parseInt(env.getProperty("redis.port")),
                    10000);
            RANDOM_DB_NUMBER = 15;
        }
    }


}
