package com.runner;

import org.I0Itec.zkclient.IZkStateListener;
import org.apache.zookeeper.Watcher;

public class ZKwatcher implements IZkStateListener {


    @Override
    public void handleStateChanged(Watcher.Event.KeeperState state) throws Exception {

    }

    @Override
    public void handleNewSession() throws Exception {

    }

    @Override
    public void handleSessionEstablishmentError(Throwable error) throws Exception {

    }
}
