package cn.mrworlds.lingjingshijie.messagecenter.router;

import cn.mrworlds.lingjingshijie.messagecenter.Constant.ServiceConfig;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.room.moudel.Room;
import cn.mrworlds.lingjingshijie.messagecenter.server.RoomServer;
import cn.mrworlds.lingjingshijie.messagecenter.util.MessageQueueUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RoomServerRouter {
    // 每一个房间的创建都会建立一个服务器，监听指定的端口 。表示 roomID - 服务器 映射，通过 roomID 可以得到room
    private Map<Integer, RoomServer>  roomServerRouter = new ConcurrentHashMap<>();
    // 存储着roomID ,Room  ，通过roomID快速获取房间 信息
    private Map<Integer, Room>  roomRouter = new ConcurrentHashMap<>();
    // 存储系统中可用的端口号 【当房间销毁时回收房间使用的端口号】
    private List<Integer> availablePorts  = new ArrayList<>(1600);
    // 从 BG_PORT 开始 的 1600 个端口时系统可用的端口
    private static final Integer BG_PORT = 30010;

    private RoomServerRouter() {
        for(int i = 0; i<1000;i++) {
            availablePorts.add(-1);
        }
    }

    public void changeRoomInfo(Room room) {
        if(room!=null) {
            roomRouter.put(room.getId(), room);
        }
    }

    public boolean existRoom(Integer roomID) {
        return roomServerRouter.containsKey(roomID);
    }

    public List<Integer> availableRooms() {
        Set<Integer> integers = roomRouter.keySet();

        List list = new LinkedList();

        list.addAll(integers);

        return list;
    }

    private static class SingleHolder {
        final static RoomServerRouter holder = new RoomServerRouter();
    }

    public static RoomServerRouter getInstance() {
        return RoomServerRouter.SingleHolder.holder;
    }

    synchronized public int getOnePort(){
        int index = 0;
        for (int i = 0,n = availablePorts.size() ;i<n;i++){
            Integer num = availablePorts.get(i);
            if(num==-1){
                index  = i;
                break;
            }
        }
        int port = BG_PORT + index ;

        availablePorts.set(port-BG_PORT,port);

        return port;
    }

    public void RegesterOne(Integer roomID,Integer port) throws InterruptedException {

        RoomServer roomServer = new RoomServer(port);

        new Thread() {
            @Override
            public void run() {
                roomServer.start();
            }}.start();

        while(!roomServer.hasStarted()){
            Thread.sleep(1000);
        }

        roomServerRouter.put(roomID,roomServer);

        RoomUserChannelRouter.getInstance().initialUserMap(roomID);
    }


    public void releaseRoomPort(Integer port){

        availablePorts.set(port-BG_PORT,-1);
    }

    public void UnRegesterOne(Integer roomID){

        RoomUserChannelRouter.getInstance().destoryUserMap(roomID);

        RoomServer roomServer = roomServerRouter.get(roomID);

        if(roomServer == null){
            return;
        }

        int port = roomServer.getPort();

        releaseRoomPort(port);

        roomServer.close();

        roomServerRouter.remove(roomID);
    }

    public void regeterOneRoom(Room room){
        if(room!=null) {
            roomRouter.put(room.getId(), room);
            //启动定时器，按照一定频率将消息发送到逻辑中心进行持久化

            Timer timer = new Timer();
            timer.schedule(new PersistentMessageTask(timer,room.getId()),0,2000);
        }
    }

    public void unregesterOneRoom(Integer roomID){
        roomRouter.remove(roomID);
    }

    public Room RoomInfo(Integer roomID){
        return roomRouter.get(roomID);
    }
}
@Slf4j
class PersistentMessageTask extends TimerTask{

    private Integer roomID;

    private Timer timer;

    public PersistentMessageTask(Timer timer,Integer roomID){
        this.timer = timer;
        this.roomID = roomID;
    }

    @Override
    public void run() {
        log.info("开始将消息持久化");
        if (MessageQueueUtil.channel == null || !MessageQueueUtil.channel.isActive()) {
            return;
        }

        Room room = RoomServerRouter.getInstance().RoomInfo(roomID);

        if (room != null && !room.getState().equals(ServiceConfig.ROOM_STATE_END)) {
            flushMsg(room);
        } else {
            flushMsg(room);
            timer.cancel();
        }
    }

    private void flushMsg (Room room){
        Integer lastID = room.saveMsgID;
        List<MRTPCommand> commands = RoomUserChannelRouter.getInstance().fetchMRTPCommand(roomID, lastID);
        if (commands == null||commands.isEmpty()) {
            return;
        }

        for (MRTPCommand command : commands) {
            MessageQueueUtil.SendMRTPCommand(command);
        }

        room.saveMsgID = room.saveMsgID + commands.size();
    }
}
