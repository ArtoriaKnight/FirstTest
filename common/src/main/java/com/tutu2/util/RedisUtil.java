package com.tutu2.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Redis操作工具类
 *
 * @author
 */
@Component
public class RedisUtil extends JedisConnectPool {

    /**
     * 获取客户端链接
     *
     * @return
     */
    public Jedis getRedisClient() {
        check();
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redisClient;
    }

    public Jedis getRedisClient(int dbNum) {
        check();
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(dbNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return redisClient;
    }

    /**
     * redis 浮点小数计数
     *
     * @param key
     * @param Increment 增量
     * @param index
     * @return
     */
    public double setIncrbyfloat(String key, double Increment, int index) {
        check();
        double result = 0.0d;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.incrByFloat(key, Increment);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * 从redis获取字符串数据
     *
     * @param key
     * @return
     */
    public String getString(String key, int index) {
        check();
        String str = "";
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            str = redisClient.get(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return str;
    }


    /**
     * 从redis获取字符串数据一次获取多个key的值
     *
     * @param keys
     * @return
     */
    public List<String> mgetString(String... keys) {
        check();
        List<String> values = null;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            values = redisClient.mget(keys);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return values;
    }

    /**
     * <p>
     * 存放字符串到redis,返回一个整形数字
     * <p>
     * 数据库中已有该key值是执行替换操作
     * <p>
     *
     * @param key
     * @return
     */
    public boolean setString(String key, String value, int index) {
        check();
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            String result = redisClient.set(key, value);
            return result != null && result.equals("OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return false;
    }


    /**
     * 取到当前库key列表
     *
     * @param index
     * @return
     */
    public Long dbSize(int index) {
        check();
        Jedis jedis = null;
        Long ret = null;
        try {
            jedis = jcpool.getResource();
            jedis.select(index);
            ret = jedis.dbSize();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            jedis.close();
        }
        return ret;
    }

    /**
     * 取到当前库key列表
     *
     * @param index
     * @return
     */
//    public Set<String> keys(int index) {
//        check();
//        Jedis jedis = null;
//        Set<String> ret = null;
//        try {
//            jedis = jcpool.getResource();
//            jedis.select(index);
//            ret = jedis.keys("*");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            jedis.close();
//        }
//        return ret;
//    }

    /**
     * 存放字符串到redis并指定过期时间,返回一个整形数字
     *
     * @param key
     * @param value
     * @param index
     * @param seconds
     * @return
     */
    public boolean setString(String key, String value, int index, int seconds) {
        check();
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            String result = redisClient.setex(key, seconds, value);
            return result != null && result.equals("OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return false;
    }

    /**
     * 根据需求指定
     *
     * @param key
     * @param value
     * @param nxxx  NX|XX，  NX - 仅设置密钥（如果密钥尚不存在） XX - 仅设置密钥（如果密钥已存在）。
     * @param expx  EX|PX，  EX =秒; PX =毫秒
     * @param time
     * @param index
     * @return
     */
    public boolean setString(String key, String value, String nxxx, String expx, int time, int index) {
        check();
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            String result = redisClient.set(key, value, nxxx, expx, time);
            //注意：redis成功时返回大写的OK
            return result != null && result.equals("OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return false;
    }

    /**
     * 将 key 中储存的数字值增一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * 成功返回当前 Key 对应的值
     *
     * @param key
     * @param index
     * @return
     */
    public Long setIncr(String key, int index) {
        check();
        Jedis redisClient = null;
        Long result = 0L;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * 将 key 中储存的数字值减一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
     * 成功返回当前 Key 对应的值
     *
     * @param key
     * @param index
     * @return
     */
    public Long setDecr(String key, int index) {
        check();
        Jedis redisClient = null;
        Long result = 0L;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.decr(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * <p>
     * 存放字符串到redis,一次存放多条,返回一个整形数字
     * <p>
     * 参数格式 key,value,key,value,key,value
     * <p>
     * 数据库中已有该key值是执行替换操作
     * <p>
     *
     * @param keysvalues
     * @return
     */
    public boolean msetString(String... keysvalues) {
        check();
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            String result = redisClient.mset(keysvalues);
            return result != null && result.equals("OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return false;
    }

    /**
     * 设置指定key的有效时间
     *
     * @param key
     * @param time
     * @param index
     * @return
     */
    public Long expire(String key, int time, int index) {
        check();
        long result = 0;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.expire(key, time);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * <p>
     * 追加字符串到redis,返回操作后字符串的长度
     * <p>
     * 假如redis中没有对应的key的数据，则执行set操作，返回的是value的长度
     * <p>
     *
     * @param key
     * @return
     */
    public Long appendString(String key, String value, int index) {
        check();
        long result = 0;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.append(key, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * <p>
     * 删除redis中值
     * <p>
     * 删除的记录数条数
     * <p>
     *
     * @param key
     * @return
     */
    public Long delString(String key, int index) {
        check();
        long result = 0;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.del(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * <p>
     * 删除redis中值,一次删除多条
     * <p>
     * 删除的记录数条数
     * <p>
     *
     * @param keys
     * @return
     */
    public Long mdelString(String... keys) {
        check();
        long result = 0;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            result = redisClient.del(keys);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * <p>
     * 存放一个对象到redis set集合中
     * <p>
     * 数据库中已有该key值是执行替换操作
     * <p>
     *
     * @param key
     * @return
     */
    public Long setString(final String key, final String... members) {
        check();
        long result = 0;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            result = redisClient.sadd(key, members);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * 将map信息存放入redis中
     *
     * @param key
     * @param map
     * @return
     */
    public boolean setMap(final String key, Map<String, String> map, int index) {
        check();
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            String result = redisClient.hmset(key, map);
            return result != null && result.equals("OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return false;
    }

    /**
     * 检查数据是否存在
     *
     * @param key
     * @param index
     * @return
     */
    public boolean checkKey(String key, int index) {
        check();
        boolean flag = false;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            flag = redisClient.exists(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return flag;
    }

    /**
     * 从redis map中取值
     *
     * @param key
     * @param mapkey
     * @return
     */
    public String getValueFromMap(String key, String mapkey, int index) {
        check();
        String result = "";
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.hmget(key, mapkey).get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }


    /**
     * 从redis中取得Map
     *
     * @param key
     * @param index
     * @return
     */
    public Map<String, String> getMap(String key, int index) {
        check();
        Map<String, String> result = null;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.hgetAll(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }


    /**
     * 从Map根据Value获取key
     *
     * @param key
     * @param mapValue
     * @param index
     * @return
     */
    public String getKeyFromMap(String key, String mapValue, int index) {
        check();
        String value = "";
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            Set<String> keys = redisClient.hkeys(key);
            for (String k : keys) {
                String tempvalue = redisClient.hmget(key, k).get(0);
                if (tempvalue.equals(mapValue)) {
                    value = k;
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return value;
    }

    /**
     * 从redis map中删除值
     *
     * @param key
     * @param mapkey
     * @return
     */
    public long deleteValueFromMap(String key, String mapkey, int index) {
        check();
        long result = 0l;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.hdel(key, mapkey);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     *
     * @param key
     * @param value
     * @param index
     * @return 设置成功，返回 1 。设置失败，返回 0 。
     */
    public Integer setNxString(String key, String value, int index) {
        check();
        int result = 0;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.setnx(key, value).intValue();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }


    /**
     * 向map中增加/设置值
     *
     * @param key
     * @param mapkey
     * @return
     */
    public long setValueFromMap(String key, String mapkey, String mapValue, int index) {
        check();
        long result = 0l;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.hset(key, mapkey, mapValue);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }


    /**
     * 向map中指定的值增量
     *
     * @param key       key
     * @param mapkey    map->key
     * @param Increment 增加量
     * @param index
     * @return
     */
    public long incrValueFromMap(String key, String mapkey, long Increment, int index) {
        check();
        long result = 0l;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.hincrBy(key, mapkey, Increment);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }


    /**
     * 获得Map大小
     *
     * @param key
     * @param index
     * @return
     */
    public long getMapSize(String key, int index) {
        check();
        long result = 0l;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.hlen(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /** author hjy
     *  descr 获取 set 的大小 */
    public long setSize(String key, int index) {
        check();
        Long result = 0l;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.scard(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }


    public Set<String> smembers(String key, int index) {
        check();
        Set<String> result = null;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.smembers(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /** author hjy
     *  descr 获取 zset 的大小 */
    public long zCard(String key, int index) {
        check();
        Long result = 0l;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.zcard(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * 设置有序集合Zadd
     *
     * @param key
     */
    public long setZadd(String key, double score, String member, int index) {
        check();
        long result = 0l;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.zadd(key, score, member);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }


    /**
     * 读取指定区间的有序集合Zadd,倒序  zset
     *
     * @param key
     * @param start
     * @param end
     * @param index
     * @return
     */
    public Set<Tuple> getZadd(String key, long start, long end, int index) {
        check();
        Set<Tuple> result = null;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.zrevrangeWithScores(key, start, end);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * 读取指定区间的有序集合Zadd,倒序  zset
     *
     * @param key
     * @param start
     * @param end
     * @param index
     * @return
     */
    public Set<String> getZmember(String key, long start, long end, int index) {
        check();
        Set<String> result = null;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.zrevrange(key, start, end);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * 给指定的集合中某个成员增加score  zset
     *
     * @param key
     * @param score
     * @param member
     * @param index
     * @return
     */
    public Double incrZset(String key, double score, String member, int index) {
        check();
        Double result = 0.0;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.zincrby(key, score, member);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * 获取指定的集合中某个成员的排行,倒序  zset
     *
     * @param key
     * @param member
     * @param index
     * @return
     */
    public Long rankZset(String key, String member, int index) {
        check();
        Long result = 0l;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.zrevrank(key, member);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * 获取指定的集合中某个成员的分数  zset
     *
     * @param key
     * @param member
     * @param index
     * @return
     */
    public Double scoreZset(String key, String member, int index) {
        check();
        Double result = 0.0;
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            result = redisClient.zscore(key, member);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return result;
    }

    /**
     * 清空当前数据库中的所有 key
     */
    public boolean flushDB(int index) {
        check();
        Jedis redisClient = null;
        try {
            redisClient = jcpool.getResource();
            redisClient.select(index);
            String result = redisClient.flushDB();
            return result != null && result.equals("OK");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            redisClient.close();
        }
        return false;
    }

    /**
     * 浏览、访问次数累加随机值设置
     *
     * @param key
     * @param max
     * @return
     */
    public Integer setRandom(String key, Boolean condition, int max) {
        Integer newRandom = 0;
        Integer min = 1;
        Integer random = (int) (Math.random() * max) + min;

        try (Jedis jedis = this.getRedisClient()) {
            jedis.select(RANDOM_DB_NUMBER);
            if (jedis.exists(key)) {
                Integer oriRandom = Integer.valueOf(jedis.get(key));
                // 如果开关开启，累加随机数，否则累加1
                newRandom = oriRandom + (condition ? random : 1);
            } else {
                // 如果开关开启，直接设置随机数，否则设为1
                newRandom = (condition ? random : 1);
            }
            jedis.set(key, String.valueOf(newRandom));
            return newRandom;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("redis异常，随机值设置失败！");
            return 0;
        }
    }

    /**
     * 获取浏览、访问次数随机数，只取，不累加！！
     *
     * @param key
     * @return
     */
    public Integer getRandom(String key) {
        try (Jedis jedis = this.getRedisClient()) {
            jedis.select(RANDOM_DB_NUMBER);
            if (!jedis.exists(key)) {
                return 0;
            } else {
                String val = jedis.get(key);
                Integer random = Integer.valueOf(val);
                return random;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("redis异常，随机值设置失败！");
            return 0;
        }
    }
}
