package com.runner.provider.config;

import com.runner.provider.common.Constant;
import org.I0Itec.zkclient.IZkStateListener;
import org.apache.zookeeper.Watcher;

public class Zklistener  implements IZkStateListener {
    @Override
    public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {

        if (state.getIntValue()!=3){
            Constant.isOnline = false;
            System.out.println("掉线啦！！！"+"状态："+state.name());
        }else {
            Constant.isOnline = true;
            System.out.println("你还在线😎");
        }

    }
//    application.properties
    @Override
    public void handleNewSession() throws Exception {
        System.out.println("第二个，目前不详");
    }

    @Override
    public void handleSessionEstablishmentError(Throwable error) throws Exception {
        System.out.println("------------------------连接失败---------------------");
        System.out.println("------------------------连接失败---------------------");
        System.out.println("------------------------连接失败---------------------");
    }
}
