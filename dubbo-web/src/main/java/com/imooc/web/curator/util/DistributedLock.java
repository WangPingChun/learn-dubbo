package com.imooc.web.curator.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 分布式锁的实现工具.
 *
 * @author : chris
 * 2018-07-01
 */
public class DistributedLock {
    private static Logger log = LoggerFactory.getLogger(DistributedLock.class);
    /**
     * 客户端
     */
    private CuratorFramework client;

    /**
     * 用于挂起请求,并且等待上一个分布式锁释放
     */
    private static CountDownLatch zkLockLatch = new CountDownLatch(1);

    /**
     * 分布式锁的总节点名
     */
    private static final String ZK_LOCK_PROJECT = "imooc-locks";

    /**
     * 分布式锁节点
     */
    private static final String DISTRIBUTED_LOCK = "distributed-lock";

    public DistributedLock(CuratorFramework client) {
        this.client = client;
    }

    public void init() {
        // 使用命名空间
        client = client.usingNamespace("zk-locks-namespace");
        try {
            if (client.checkExists().forPath("/" + ZK_LOCK_PROJECT) == null) {
                client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath("/" + ZK_LOCK_PROJECT);
            }
            // 针对zk的分布式锁节点,创建相应的watcher事件监听
            addWatcherToLock("/" + ZK_LOCK_PROJECT);
        } catch (Exception e) {
            log.error("客户端连接zookeeper服务器错误... 请重试...");
        }
    }

    /**
     * 创建watcher监听
     *
     * @param path
     */
    public void addWatcherToLock(String path) throws Exception {
        final PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) {
                if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                    String path = event.getData().getPath();
                    log.info(path);
                    log.info("上一个会话已释放锁或该会话已断开, 节点路径为: " + path);
                    if (path.contains(DISTRIBUTED_LOCK)) {
                        log.info("释放计数器, 让当前请求来获得分布式锁...");
                        zkLockLatch.countDown();
                    }
                }
            }
        });
    }

    /**
     * 获得分布式锁
     */
    public void getLock() {
        // 使用死循环,当且仅当上一个锁释放并且当前请求获得锁成功之后才跳出
        while (true) {
            try {
                client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath("/" + ZK_LOCK_PROJECT + "/" + DISTRIBUTED_LOCK);
                log.info("获得分布式锁成功...");
                return;
            } catch (Exception e) {
                log.info("获得分布式锁失败...");
                try {
                    // 如果没有获取到锁，需要重新设置同步资源值
                    if (zkLockLatch.getCount() <= 0) {
                        zkLockLatch = new CountDownLatch(1);
                    }
                    // 阻塞线程
                    zkLockLatch.await();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }

        }
    }

    /**
     * 释放分布式锁
     *
     * @return
     */
    public boolean releaseLock() {
        try {
            if (client.checkExists().forPath("/" + ZK_LOCK_PROJECT + "/" + DISTRIBUTED_LOCK) != null) {
                client.delete().forPath("/" + ZK_LOCK_PROJECT + "/" + DISTRIBUTED_LOCK);
            }
        } catch (Exception e) {
            return false;
        }
        log.info("分布式锁释放完毕");
        return true;
    }
}
