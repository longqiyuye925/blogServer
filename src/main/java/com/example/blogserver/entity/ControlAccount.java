package com.example.blogserver.entity;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Hashtable;

/**
 * @Description: 这里用了一个单例的hashtable存储账户失败的次数，如果是集群或者分布式的情况，要根据数据量选择用数据库还是缓存中间件存储
 * @author: wsc
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/19
 */
@Component
public class ControlAccount {
    /**
     * 枚举类实现单例
     */
    public enum LockAccountHashtable {
        Instance;
        private Hashtable<String, Integer> lockAccount = null;

        private LockAccountHashtable() {
            lockAccount = new Hashtable<String, Integer>();
        }

        public Hashtable getLockAccount() {
            return lockAccount;
        }
    }

    public synchronized void putLockAccount(String key, Integer num) {
        Hashtable<String, Integer> lockAccount = LockAccountHashtable.Instance.getLockAccount();
        lockAccount.put(key, num);
    }

    public synchronized Hashtable<String, Integer> getLockTable() {
        Hashtable<String, Integer> lockAccount = LockAccountHashtable.Instance.getLockAccount();
        return lockAccount;
    }
}
