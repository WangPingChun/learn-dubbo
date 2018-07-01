package com.imooc.web.curator.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : chris
 * 2018-07-01
 */
public class ZkCurator {
    private static Logger log = LoggerFactory.getLogger(ZkCurator.class);
    /**
     * zookeeper客户端
     */
    private CuratorFramework client;

    public ZkCurator(CuratorFramework client) {
        this.client = client;
    }

    public void init() {
        // 使用命名空间
        client = client.usingNamespace("zk-curator-connector");
    }

    public boolean isAlive() {
        return client.getState() == CuratorFrameworkState.STARTED;
    }
}
