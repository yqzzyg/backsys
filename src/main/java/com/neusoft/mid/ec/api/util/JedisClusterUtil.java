package com.neusoft.mid.ec.api.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.*;

/**
 * @author zhao.zhenyu
 * @description
 * @date 2018年11月14日 上午10:23:36
 */
public class JedisClusterUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JedisClusterUtil.class);

    private JedisCluster jedis = null;


    private JedisPool jedisPool;

    private JedisPoolConfig config;

    private int expireTime;

    private String redis_url;

    private int redis_port;

    private String redis_pwd;

    private static final String KEY_SPLIT = ":"; // 用于隔开缓存前缀与缓存键值

    private void initialPool() {

        JedisPoolConfig config = new JedisPoolConfig();

        config.setMinIdle(5);

        config.setMaxIdle(15);

        config.setMaxWaitMillis(10001);

        config.setTestOnBorrow(false);

        this.config = config;
    }

    public JedisClusterUtil(String hostAndPort, int timeOut, int expireTime) {

        // 获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
        try {
            initialPool();
            String[] serverArray = hostAndPort.split(",");
            Set<HostAndPort> nodes = new HashSet<>();
            for (String ipPort : serverArray) {
                String[] ipPortPair = ipPort.split(":");
                nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
            }
            this.jedis = new JedisCluster(nodes,timeOut,config);
            LOGGER.info("redis启动成功,hosts={}",hostAndPort);
        } catch (Exception e) {
            LOGGER.error("redis启动异常", e);
        }
    }

    public JedisClusterUtil() {
    }

    public void init() {
    }

    /**
     * 设置缓存
     *
     * @param prefix 缓存前缀（用于区分缓存，防止缓存键值重复）
     * @param key    缓存key
     * @param value  缓存value
     */
    public synchronized void set(String prefix, String key, String value) {
        jedis.set(prefix + KEY_SPLIT + key, value);
        LOGGER.debug("RedisUtil:set cache key={},value={}", prefix + KEY_SPLIT + key, value);
    }

    /**
     * 设置缓存，并且自己指定过期时间
     *
     * @param prefix
     * @param key
     * @param value
     * @param expireTime 过期时间
     */
    public synchronized void setWithExpireTime(String prefix, String key, String value, int expireTime) {
        jedis.setex(prefix + KEY_SPLIT + key, expireTime, value);
        LOGGER.debug("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", prefix + KEY_SPLIT + key, value, expireTime);
    }

    /**
     * 设置缓存，并且由配置文件指定过期时间
     *
     * @param prefix
     * @param key
     * @param value
     */
    public synchronized void setWithExpireTime(String prefix, String key, String value) {
        int EXPIRE_SECONDS = this.expireTime;
        jedis.setex(prefix + KEY_SPLIT + key, EXPIRE_SECONDS, value);
        LOGGER.debug("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", prefix + KEY_SPLIT + key, value, EXPIRE_SECONDS);
    }

    /**
     * 获取指定key的缓存
     *
     * @param prefix
     * @param key
     */
    public synchronized String get(String prefix, String key) {
        String value = jedis.get(prefix + KEY_SPLIT + key);
        LOGGER.debug("RedisUtil:get cache key={},value={}", prefix + KEY_SPLIT + key, value);
        return value;
    }

    /**
     * 删除指定key的缓存
     *
     * @param prefix
     * @param key
     */
    public synchronized void deleteWithPrefix(String prefix, String key) {
        Long iLong = jedis.del(prefix + KEY_SPLIT + key);
        System.out.println(iLong);
        LOGGER.debug("RedisUtil:delete cache key={}", prefix + KEY_SPLIT + key);
    }

    public synchronized void delete(String key) {
        jedis.del(key);
        LOGGER.debug("RedisUtil:delete cache key={}", key);
    }

    /**
     * @param key
     * @return
     * @author zhao.zhenyu
     * @description 检查给定 key 是否存在。
     * @date 2018年10月25日 上午10:25:00
     */
    public synchronized boolean exists(String key) {
        boolean flag = jedis.exists(key);
        return flag;
    }

    /**
     * @param key
     * @param seconds 秒
     * @author zhao.zhenyu
     * @description 设置key的生效时间
     * @date 2018年10月25日 上午10:24:34
     */

    public synchronized void expire(String key, int seconds) {
        jedis.expire(key, seconds);
    }

    /**
     * @param key
     * @param value
     * @return
     * @author zhao.zhenyu
     * @description 根据key更新value，返回OldValue，有效期失效
     * @date 2018年11月1日 上午9:58:15
     */
    public synchronized String getSet(String key, String value) {
        String oldValue = jedis.getSet(key, value);
        return oldValue;
    }

    /**
     * @param key
     * @return
     * @author zhao.zhenyu
     * @description 获取key的失效时间
     * @date 2018年11月1日 下午4:20:28
     */
    public synchronized int getTTL(String key) {
        int extime = Integer.parseInt(String.valueOf(jedis.ttl(key)));
        return extime;
    }

    /**
     * @param key
     * @param value
     * @author zhao.zhenyu
     * @description 向redis中添加list
     * @date 2018年11月14日 上午10:23:16
     */
    public synchronized void setList(String key, String value) {
        jedis.lpush(key, value);
    }

    /**
     * @param key
     * @return
     * @author zhao.zhenyu
     * @description 获取所有list里的值
     * @date 2018年11月14日 上午10:23:38
     */
    public synchronized List<String> getAllList(String key) {
        List<String> list = jedis.lrange(key, 0, jedis.llen(key));
        return list;
    }

    public String getRedis_url() {
        return redis_url;
    }

    public void setRedis_url(String redis_url) {
        this.redis_url = redis_url;
    }

    public int getRedis_port() {
        return redis_port;
    }

    public void setRedis_port(int redis_port) {
        this.redis_port = redis_port;
    }

    public String getRedis_pwd() {
        return redis_pwd;
    }

    public void setRedis_pwd(String redis_pwd) {
        this.redis_pwd = redis_pwd;
    }

}
