package com.eryo.redis;

import redis.clients.jedis.Jedis;

/**
 * 添加字符串或更新字符串及其过期时间的操作
 */
public class StrResolver implements TypeResolver {

    private CounterSpec counterSpec;
    private Jedis jedis;

    @Override
    public String resolve() {
        String res = "没有进行有效操作";
        String key = counterSpec.getKeyFields();
        String value = counterSpec.getValueFields();
        int expireTime = counterSpec.getExpireTime();
        if (key != null) {
            if(jedis.exists(key) && jedis.type(key).equals("string")) {     //存在该字符串
                if (value != null) {    //更新该字符串的值
                    if(expireTime != 0) {   //更新该字符串的过期时间
                        jedis.setex(key, expireTime, value);
                        res = "键：" + key + "，键值为：" + value + "，过期时间为：" + expireTime;
                    } else {        //仅更新该字符串的值
                        jedis.set(key, value);
                        res = "键：" + key + "，键值为：" + value;
                    }
                } else {
                    if(expireTime != 0) {
                        jedis.expire(key, expireTime);
                        res = "键值为：" + jedis.get(key) + "，新设置过期时间为：" + expireTime + "秒";
                    } else {
                        res = "键值为：" + jedis.get(key) + "，过期时间为：" + jedis.ttl(key) + "秒";
                    }
                }
            } else {    //不存在该字符串
                if (value != null) {
                    if(expireTime != 0) {
                        jedis.setex(key, expireTime, value);
                        res = "键：" + key + "，键值为：" + value + "，过期时间为：" + expireTime;
                    } else {
                        jedis.set(key, value);
                        res = "键：" + key + "，键值为：" + value;
                    }
                }
            }
        }
        return res;
    }

    @Override
    public void setData(CounterSpec counterSpec, Jedis jedis) {
        this.counterSpec = counterSpec;
        this.jedis = jedis;
    }
}
