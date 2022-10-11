package com.runner;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZKClient implements Watcher {

    private ZooKeeper zookeeper;
    // 集群连接地址
    private static final String ZOOKEEPER_ADDRES = "47.93.26.215:2181";
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 创建zookeeper连接
     * @param connectAddres 连接地址
     * @param sessionTimeOut 超时时间
     */
    private void createConnection(String connectAddres, int sessionTimeOut) {
        try {
            zookeeper = new ZooKeeper(connectAddres, sessionTimeOut, this);
            System.out.println("开始连接zokeeper服务....");
            //等待直到连接创建成功
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建zookeeper节点
     * @param nodePath 节点路径
     * @param data 节点数据
     * @return 是否创建成功
     */
    private boolean createNode(String nodePath, String data) {
        try {
            exists(nodePath, true);
            zookeeper.create(nodePath, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("节点创建成功, Path:" + nodePath + ",data:" + data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 修改zookeeper节点数据
     * @param nodePath 节点路径
     * @param data 节点数据
     * @return 是否修改成功
     */
    private boolean updateNode(String nodePath, String data){
        exists(nodePath, true);
        try {
            zookeeper.setData(nodePath, data.getBytes(), -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 列出指定path下的孩子
     */
    private void list(String path) throws InterruptedException, KeeperException {
        System.out.print(path+":           ");
        byte[] data = zookeeper.getData(path, new ZKClient(), new Stat());
        if (data!=null){
            System.out.println(new String(data));
        }else {
            System.out.println("空");
        }

        List<String> list = null;
        try {
            list = zookeeper.getChildren(path,this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(list == null || list.isEmpty()){
            return;
        }
        for(String pa : list){
            //迭代输出path下的所有节点
            if(path.equals("/")){
                list(path + pa);
            }
            else{
                list(path + "/" + pa);
            }
        }
    }

    /**
     * 判断指定节点是否存在
     * @param node 节点路径
     */
    private Stat exists(String node, boolean needWatch) {
        try {
            return zookeeper.exists(node, needWatch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zookeeper事件监听
     * @param watchedEvent watchedEvent对象
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        Event.KeeperState zkState = watchedEvent.getState();
        String path = watchedEvent.getPath();
        Event.EventType eventType = watchedEvent.getType();

        if(zkState == Event.KeeperState.SyncConnected){
            if(eventType == Event.EventType.None){
                //连接建立事件
                System.out.println("建立zookeeper连接成功!");
                countDownLatch.countDown();
            }else if(eventType == Event.EventType.NodeCreated){
                //节点创建事件
                System.out.println("节点" + path + "被创建");
            }else if(eventType == Event.EventType.NodeDataChanged){
                //节点修改事件
                System.out.println("节点" + path + "被修改");

            }else if(eventType == Event.EventType.NodeDeleted){
                //节点删除事件
                System.out.println("节点" + path + "被删除");
            }
        }

    }

    @Test
    public void main() throws InterruptedException, KeeperException {
        ZKClient zkClient = new ZKClient();
        zkClient.createConnection(ZOOKEEPER_ADDRES, 5000);
//        zkClient.createNode("/testdemo01", "ga");
//        zkClient.createNode("/zk_test_parent/zk_test_child", "child_node");
//        zkClient.updateNode("/zk_test_parent","farther_node");
//        zkClient.createNode("/zk_test_parent/zk_test_child/zk_test_cc", "cc_node");

        zkClient.list("/testwork.mrworlds.cn");

//        System.out.println("-------------------------------------------------------------------");
//        System.out.println("-------------------------------------------------------------------");
//        System.out.println("-------------------------------------------------------------------");
//        System.out.println("-------------------------------------------------------------------");
//        zkClient.list("/beta");
        zkClient.list("/alpha");
        zkClient.list("/beta");
        zkClient.list("/gamma");

    }
}
