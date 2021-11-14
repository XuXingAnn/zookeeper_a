package com.atguigu.zookeeper;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Mian_demo1 {


    private ZooKeeper zooKeeper = null;


//    连接 zookeeper
    @Before
    public void init() throws IOException {
        String url= "192.168.1.19:2181";
        int sessionTimeout = 200000;
        zooKeeper = new ZooKeeper(url, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("Watcher");
            }
        });
    }

    @Test
    public void  creatNode() throws KeeperException, InterruptedException {
        // 参数 1：要创建的节点的路径； 参数 2：节点数据 ； 参数 3：节点权限 ；参数 4：节点的类型
        final String s = zooKeeper.create("/java",
                "allen".getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(s);
    }

//    获取节点数据
    @Test
    public void getNode() throws KeeperException, InterruptedException {
        final byte[] data = zooKeeper.getData("/java", false, new Stat());
        String s = new String(data);
        System.out.println(s);
    }


}
