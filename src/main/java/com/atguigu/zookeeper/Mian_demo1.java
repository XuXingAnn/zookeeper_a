package com.atguigu.zookeeper;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static javafx.scene.input.KeyCode.W;

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

//    获取节点数据并观测数据
    @Test
    public void getNodeAndWath() throws KeeperException, InterruptedException {
        final Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println(event.toString());
            }
        };
        // 一次性监听  数据改变一次后就不在监听
//        final byte[] data = zooKeeper.getData("/java", watcher, new Stat());

        // 永久性监听 使用init（）函数中的监听 ，执行完毕之后再次进行监听
        final byte[] data = zooKeeper.getData("/java", true, new Stat());
        String s = new String(data);
        System.out.println(s);
        Thread.sleep(Long.MAX_VALUE);
    }


    @Test
    public void existNode() throws KeeperException, InterruptedException {
        final Stat exists = zooKeeper.exists("/java", false);
        System.out.println(exists);
        final Stat exist2 = zooKeeper.exists("/java/dddd", false);
        System.out.println(exist2);
    }

}
