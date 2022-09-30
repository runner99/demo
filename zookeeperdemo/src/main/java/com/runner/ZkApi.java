package com.runner;

import org.I0Itec.zkclient.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.util.List;

public class ZkApi {
    /**
     * zookeeper地址
     */
    static final String CONNECT_ADDR = "192.168.224.129:2181";
    /**
     * session超时时间 ms
     */
    static final int SESSION_OUTTIME = 10000;
    /**
     * 创建节点
     * 掌握：创建持久节点、临时节点；创建有序节点；级联创建持久。
     */
    public void createNode(ZkClient ZKC) {
        //创建持久节点（没有值）
        ZKC.createPersistent("/libraries");
        //创建持久节点（有值）
        ZKC.createPersistent("/movies", "漫威电影");
        //级联创建持久节点，并为节点创建值（注意：前提是有/libraries节点）。
        ZKC.createPersistent("/libraries/medical-books", "医学书籍");
        ZKC.createPersistent("/libraries/medical-books/introductory-books","医学入门书");
        ZKC.createPersistent("/libraries/novel-books", "小说书籍");

        //创建临时节点（没有值）
        ZKC.createEphemeral("/nba");
        //创建临时节点（有值）
        ZKC.createEphemeral("/cba", "篮球");

        //创建临时有序节点
        ZKC.createEphemeralSequential("/234","324");
        //创建持久有序节点
        ZKC.createPersistentSequential("325555","32424");
    }

    /**
     * 删除节点
     * 掌握：删除单个节点；递归删除多个节点。
     */
    public void deleteNode(ZkClient ZKC) {
        //删除/movies节点
        ZKC.delete("/movies");
        //删除/libraries下的/medical-books和它下面的所有节点
        ZKC.deleteRecursive("/libraries/medical-books");
        //删除/libraries和它下面的所有节点
        ZKC.deleteRecursive("/libraries");

    }

    /**
     * 修改节点
     * 掌握：修改节点的内容；
     */
    public void updateNode(ZkClient ZKC){
        //修改前查看值
        String s = ZKC.readData("/libraries/medical-books").toString();
        System.out.println(s);

        //修改/libraries/medical-books的内容
        ZKC.writeData("/libraries/medical-books","伤寒杂病论");

        //修改以后查看值
        String s2 = ZKC.readData("/libraries/medical-books").toString();
        System.out.println(s2);
    }
    /**
     * 查询节点
     * 掌握：查询单个节点值；查询所有直接子节点的名称；查询节点是否存在。
     */
    public void selectNode(ZkClient ZKC) {
        List<String> list=ZKC.getChildren("/libraries");
        for(int i=0 ;i<list.size();i++){
            System.out.println("获得/libraries的子节点："+list.get(i));
            String s = ZKC.readData("/libraries/" + list.get(i)).toString();
            System.out.println("/libraries/" + list.get(i)+"的值："+s);
        }
        //查询/libraries/medical-books节点是否存在。
        boolean boo = ZKC.exists("/libraries/medical-books");
        System.out.println("节点是否存在："+boo);
    }

    public static void main(String[] args) throws Exception {
        ZkClient ZKC = new ZkClient(new ZkConnection(CONNECT_ADDR), SESSION_OUTTIME);
//        new ZkApi().createNode(ZKC);
//        new ZkApi().watcherContent(ZKC);
        new ZkApi().watcherNode(ZKC);
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 订阅子节点变化
     */
    public void watcherNode(ZkClient ZKC) throws Exception {
        //监听/libraries节点
        ZKC.subscribeChildChanges("/cba", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("父节点: " + parentPath);
                System.out.println("子节点集合: " + currentChilds);
            }
        });
        Thread.sleep(2000);
        ZKC.createPersistent("/cba","篮球");
        Thread.sleep(2000);
        ZKC.createPersistent("/cba/guangdong","广东队");
        Thread.sleep(2000);
        ZKC.createPersistent("/cba/bayi","八一队");
        Thread.sleep(2000);
        ZKC.delete("/cba/bayi");
        Thread.sleep(2000);
        ZKC.deleteRecursive("/cba");
    }

    /**
     * 订阅内容变化
     */
    public void watcherContent(ZkClient ZKC) throws Exception {
        ZKC.createPersistent("/nba","篮球");

        ZKC.subscribeDataChanges("/nba", new IZkDataListener() {
            @Override
            public void handleDataDeleted(String path) throws Exception {
                System.out.println("删除的节点为:" + path);
            }
            @Override
            public void handleDataChange(String path, Object data) throws Exception {
                System.out.println("修改的节点为:" + path + ", 修改的内容为:" + data);
            }
        });
        Thread.sleep(3000);
        ZKC.writeData("/nba","篮球008");
        Thread.sleep(3000);
        ZKC.delete("/nba");
    }

}

