package cn.mrworlds.lingjingshijie.messagecenter.router;
import cn.mrworlds.lingjingshijie.messagecenter.Constant.ServiceConfig;
import cn.mrworlds.lingjingshijie.messagecenter.handler.callBack.SenderCallback;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.room.moudel.Room;
import cn.mrworlds.lingjingshijie.messagecenter.util.Cache;
import cn.mrworlds.lingjingshijie.messagecenter.util.SpringUtils;
import io.netty.channel.Channel;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

// 创建房间   availableUserMap，offlineUserMap  中初始化 roomID 对应的List

// 用户进入房间 availableUserMap添加，channelUser，channelRoom添加映射关系，
//              roomUserChannelMap添加映射关系，初始化 roomUserUnReceivedMessageMap，roomUserOffLineMessageMap
// 用户发消息  roomMessage 添加内容，roomUserUnReceivedMessageMap 追加发送失败的消息，roomUserOffLineMessageMap追加为接收到的消息
// 用户掉线  availableUserMap 移除 用户 ，offlineUserMap添加用户 ，channelUser ，channelRoom 删除
// 用户退出房间 roomUserChannelMap移除，roomUserOffLineMessageMap，roomUserUnReceivedMessageMap 移除availableUserMap，
//          offlineUserMap移除channelUser，channelRoom移除·

// 房间销毁 roomUserChannelMap，roomUserOffLineMessageMap，roomUserUnReceivedMessageMap，availableUserMap，availableUserMap 移除
public class RoomUserChannelRouter {
    private static Cache cache = SpringUtils.getBean(Cache.class);

    public static Integer STATE_RUNNING = 1;
    public static Integer STATE_OFFLINE = 0;

    private Map<UserRoom, Channel> roomUserChannelMap = new ConcurrentHashMap<>();
    private Map<UserRoom, List<MRTPCommand>> roomUserOffLineMessageMap = new ConcurrentHashMap<>();
    private Map<UserRoom, List<MRTPCommand>> roomUserUnReceivedMessageMap = new ConcurrentHashMap<>();
    private Map<Integer, Set<Integer>> availableUserMap = new ConcurrentHashMap<>();
    private Map<Integer, Set<Integer>> offlineUserMap = new ConcurrentHashMap<>();

    private Map<Channel, Integer> channelUser = new ConcurrentHashMap<>();
    private Map<Channel,Integer> channelRoom = new ConcurrentHashMap<>();

    private Map<Integer,List<MRTPCommand>> roomMessage = new ConcurrentHashMap<>();
    //存着从房间开启到房间结束整个过程中用户所接受到的所有消息
    private Map<UserRoom,List<MRTPCommand>> userRoomHasReceiveMsg = new ConcurrentHashMap<>();
    //存着正在传输中的消息
    private Map<UserRoom,List<MRTPCommand>> msgInTransform = new ConcurrentHashMap<>();

    public void unRegesterOne(Integer roomID) {
        Set<Integer> onlineUsers = availableUserMap.get(roomID);
        Set<Integer> offlineUsers = offlineUserMap.get(roomID);

        onlineUsers.addAll(offlineUsers);


        // 执行 quitRoom 方法会将 onlineUsers中的数据删掉，不能直接用onLineUser做遍历
        List<Integer> usersToQuit = new LinkedList<>();
        usersToQuit.addAll(onlineUsers);

        for (Integer userToQuit:usersToQuit) {
            System.out.println("quit for userID "+ usersToQuit +" and roomID "+roomID );
            this.quitRoom(userToQuit,roomID);
        }

        availableUserMap.remove(roomID);
        offlineUserMap.remove(roomID);
        roomMessage.remove(roomID);
    }

    public void offLineOneUser(Channel channel) {
        Integer userID = channelUser.get(channel);
        Integer roomID = channelRoom.get(channel);

        if(userID==null||roomID==null){
            return;
        }

        availableUserMap.get(roomID).remove(userID);
        offlineUserMap.get(roomID).add(userID);

        channelUser.remove(channel);
        channelRoom.remove(channel);
    }

    //用户退出房间
    public void quitRoom(Channel channel) {
        if(channel==null){
            return;
        }
        Integer userID = channelUser.get(channel);
        Integer roomID = channelRoom.get(channel);

        if(userID==null||roomID==null){
            return;
        }

        availableUserMap.get(roomID).remove(userID);
        offlineUserMap.get(roomID).remove(userID);

        UserRoom userRoom = new UserRoom(userID,roomID);
        roomUserOffLineMessageMap.remove(userRoom);
        roomUserUnReceivedMessageMap.remove(userRoom);
        userRoomHasReceiveMsg.remove(userRoom);

        roomUserChannelMap.remove(userRoom);
        msgInTransform.remove(userRoom);

        channelRoom.remove(channel);
        channelUser.remove(channel);
    }

    //将用户挤下去
    public void squeezeDown(Channel channel){
        if(channel==null){
            return;
        }
        Integer userID = channelUser.get(channel);
        Integer roomID = channelRoom.get(channel);

        if(userID==null||roomID==null){
            return;
        }


        UserRoom userRoom = new UserRoom(userID,roomID);

        roomUserChannelMap.remove(userRoom);

        channelRoom.remove(channel);
        channelUser.remove(channel);

        channel.close();
    }

    public void quitRoom(Integer userID ,Integer roomID) {
        if(userID==null||roomID==null){
            return;
        }

        availableUserMap.get(roomID).remove(userID);
        offlineUserMap.get(roomID).remove(userID);

        UserRoom userRoom = new UserRoom(userID,roomID);
        roomUserOffLineMessageMap.remove(userRoom);
        roomUserUnReceivedMessageMap.remove(userRoom);
        userRoomHasReceiveMsg.remove(userRoom);
        msgInTransform.remove(userRoom);

        Channel channel = roomUserChannelMap.get(userRoom);

        channelRoom.remove(channel);
        channelUser.remove(channel);

        roomUserChannelMap.remove(userRoom);
        if(channel!=null&&channel.isActive()) {
            channel.close();
        }
    }

    //获取用户当前所在房间的详细信息
    public Integer roomInfo(Channel channel) {
        return channelRoom.get(channel);
    }

    //将房间内的消息取出
    public List<MRTPCommand> fetchMRTPCommand(Integer roomID,Integer start) {

        List<MRTPCommand> commands = roomMessage.get(roomID);

        if(start==commands.size()){
            return new LinkedList<>();
        }

        List<MRTPCommand> commands1 = commands.subList(start, commands.size() - 1);
        return commands1;
    }

    public void initialUserMap(Integer roomID) {
        if(!availableUserMap.containsKey(roomID)){
            availableUserMap.put(roomID,new HashSet<>());
        }

        if(!offlineUserMap.containsKey(roomID)){
            offlineUserMap.put(roomID,new HashSet<>());
        }
    }

    public void destoryUserMap(Integer roomID) {
        availableUserMap.remove(roomID);
        offlineUserMap.remove(roomID);
    }

    private static class SingleHolder {
        final static RoomUserChannelRouter holder = new RoomUserChannelRouter();
    }

    public static RoomUserChannelRouter getInstance() {
        return RoomUserChannelRouter.SingleHolder.holder;
    }

    //用户加入房间 private Map<UserRoom, Channel> roomUserChannelMap = new ConcurrentHashMap<>();
    //    private Map<UserRoom, List<MRTPCommand>> roomUserOffLineMessageMap = new ConcurrentHashMap<>();
    //    private Map<UserRoom, List<MRTPCommand>> roomUserUnReceivedMessageMap = new ConcurrentHashMap<>();
    public void regesterOne(Integer userID,Integer roomID,Channel channel,Integer lastMessageID){
         UserRoom userRoom = new UserRoom(userID,roomID);

        Set<Integer> availableUsers = availableUserMap.get(roomID);
        Set<Integer> offlineUsers = offlineUserMap.get(roomID);

        if(lastMessageID<0){
            regesterImmediately(userRoom,channel);
        }

        else if(availableUsers==null||offlineUsers==null){
            System.out.println("第一次加入");
            regesterOneFirst(userID,roomID,channel,lastMessageID);
        }

        else if(!availableUsers.contains(userID)&&!offlineUsers.contains(userID)){
            System.out.println("第一次加入");
            regesterOneFirst(userID,roomID,channel,lastMessageID);
        }

        else if((availableUsers.contains(userID)||offlineUsers.contains(userID))&&lastMessageID>0){
            System.out.println("掉线重连");
            regesterOneSecond(userID,roomID,channel,lastMessageID);
        }

        else if(lastMessageID==0&&(availableUsers.contains(userID)||offlineUsers.contains(userID))){
            System.out.println("其他机器挤下线加入");
            Channel oldChannel = roomUserChannelMap.get(userRoom);
            regesterOneAnother(userRoom,oldChannel,channel,lastMessageID);
        }
    }

    //用户第一次加入房间
    public void regesterOneFirst(Integer userID,Integer roomID,Channel channel,Integer lastMessageID){
        if(!roomMessage.containsKey(roomID)){
            roomMessage.put(roomID,new LinkedList<>());
        }

        availableUserMap.putIfAbsent(roomID,new HashSet<>());

        offlineUserMap.putIfAbsent(roomID,new HashSet<>());

        UserRoom key = new UserRoom(userID, roomID);

        roomUserOffLineMessageMap.putIfAbsent(key,new LinkedList<>());
        //在资源未完全准备好之前，把消息放到 offlineMessageMap 中
        offlineUserMap.get(roomID).add(userID);

        //添加user-room-channel 路由
        roomUserChannelMap.put(key,channel);
        channelUser.put(channel,userID);
        channelRoom.put(channel,roomID);

        //准备链表存放用户已经接收到的MRTP消息
        userRoomHasReceiveMsg.putIfAbsent(key,new LinkedList<>());

        msgInTransform.putIfAbsent(key,new LinkedList<>());

        //准备链表，存放用户尚未接收到的消息
        roomUserUnReceivedMessageMap.putIfAbsent(key,new LinkedList<>());

        //设值重连后的消息
        List<MRTPCommand> unReceiveMsg = new LinkedList<>();
        if(lastMessageID==0) {
            //先将加入房间前未能接收到的消息加到尚未接受的链表
            unReceiveMsg.addAll(0,roomMessage.get(key.roomID));
            userRoomHasReceiveMsg.get(key).clear();
        }

        //将从房间开启同步后的所有消息存到尚未接收到的消息队列中。
        roomUserUnReceivedMessageMap.get(key).addAll(unReceiveMsg);

        offlineUserMap.get(roomID).remove(userID);

        //资源准备就绪，用户正式加入房间
        availableUserMap.get(roomID).add(userID);
    }

    //用户网络掉线后，重连再次加入房间，会从lastMessageID开始接收
    public void regesterOneSecond(Integer userID,Integer roomID,Channel channel,Integer lastMessageID){

        UserRoom key = new UserRoom(userID, roomID);

        List<MRTPCommand> commands1 = roomUserUnReceivedMessageMap.get(key);
        List<MRTPCommand> unReceivedMsg = new LinkedList<>();
        if(commands1!=null) {
            unReceivedMsg.addAll(commands1);
        }
        if(roomUserUnReceivedMessageMap.containsKey(key)) {
            roomUserUnReceivedMessageMap.get(key).clear();
        }


        roomUserChannelMap.put(key,channel);

        channelUser.put(channel,userID);
        channelRoom.put(channel,roomID);

        userRoomHasReceiveMsg.putIfAbsent(key,new LinkedList<>());

        msgInTransform.putIfAbsent(key,new LinkedList<>());

        roomUserOffLineMessageMap.putIfAbsent(key,new LinkedList<>());

        roomUserUnReceivedMessageMap.putIfAbsent(key,new LinkedList<>());

        //设值重连后的消息
        List<MRTPCommand> unReceiveMsg = new LinkedList<>();
        if(lastMessageID==0) {
            unReceiveMsg.addAll(0,userRoomHasReceiveMsg.get(key));
            userRoomHasReceiveMsg.get(key).clear();
        }else {
            List<MRTPCommand> commands = userRoomHasReceiveMsg.get(key);
            int i = 0;
            Iterator<MRTPCommand> iterator = commands.iterator();

            while (iterator.hasNext()){
                MRTPCommand command = iterator.next();
                if(command.id>lastMessageID){
                    unReceiveMsg.add(i,command);
                    i++;
                    iterator.remove();
                }
            }
        }

        //将从房间开启同步后的所有消息都加入roomUserUnReceivedMessageMap,在下次发送消息前，一并发到客户端。
        roomUserUnReceivedMessageMap.get(key).addAll(unReceiveMsg);

        availableUserMap.get(roomID).add(userID);

        offlineUserMap.get(roomID).remove(userID);
    }

    //用户挤掉已经在线上的用户
    public void regesterOneAnother(UserRoom key,Channel oldChannel,Channel channel,Integer lastMessageID){
        if(oldChannel!=null&&oldChannel.isActive()) {
            MRTP.returnStringMsg(MRTP.SQUEEZED_OFFLINE, "1", oldChannel);
        }

        squeezeDown(oldChannel);

        List<MRTPCommand> commands1 = roomUserUnReceivedMessageMap.get(key);

        List<MRTPCommand> unReceivedMsg = new LinkedList<>();

        if(commands1!=null) {
            unReceivedMsg.addAll(commands1);
        }

        if(roomUserUnReceivedMessageMap.containsKey(key)) {
            roomUserUnReceivedMessageMap.get(key).clear();
        }


        //如果是掉线重连，以前的channel被关闭了，直接在路由里替换成新的channel
        roomUserChannelMap.put(key,channel);

        channelUser.put(channel,key.userID);
        channelRoom.put(channel,key.roomID);


        //设值重连后的消息
        List<MRTPCommand> unReceiveMsg = new LinkedList<>();

        unReceiveMsg.addAll(0,userRoomHasReceiveMsg.get(key));
        unReceiveMsg.addAll(0,roomMessage.get(key.roomID));
        userRoomHasReceiveMsg.get(key).clear();


        //将从房间开启同步后的所有消息都加入roomUserUnReceivedMessageMap,在下次发送消息前，一并发到客户端。
        roomUserUnReceivedMessageMap.get(key).addAll(unReceiveMsg);

        availableUserMap.get(key.roomID).add(key.userID);

        offlineUserMap.get(key.roomID).remove(key.userID);
    }

    //用户加入房间后只接收当前消息，不再接收以前的消息.
    public void regesterImmediately(UserRoom key,Channel channel){
        if(!roomMessage.containsKey(key.roomID)){
            roomMessage.put(key.roomID,new LinkedList<>());
        }

        availableUserMap.putIfAbsent(key.roomID,new HashSet<>());

        offlineUserMap.putIfAbsent(key.roomID,new HashSet<>());

        roomUserOffLineMessageMap.putIfAbsent(key,new LinkedList<>());
        //在资源未完全准备好之前，把消息放到 offlineMessageMap 中
        offlineUserMap.get(key.roomID).add(key.getRoomID());

        //添加user-room-channel 路由
        roomUserChannelMap.put(key,channel);
        channelUser.put(channel,key.userID);
        channelRoom.put(channel,key.roomID);

        //准备链表存放用户已经接收到的MRTP消息
        userRoomHasReceiveMsg.putIfAbsent(key,new LinkedList<>());

        msgInTransform.putIfAbsent(key,new LinkedList<>());

        //准备链表，存放用户尚未接收到的消息
        roomUserUnReceivedMessageMap.putIfAbsent(key,new LinkedList<>());

        offlineUserMap.get(key.roomID).remove(key.userID);

        //资源准备就绪，用户正式加入房间
        availableUserMap.get(key.roomID).add(key.userID);
    }

    public void unRegetserOne(Integer userID,Integer roomID){
        if(userID==null||roomID==null){
            return;
        }
        roomUserChannelMap.remove(new UserRoom(userID,roomID));
    }

    public List<Channel> allChannels(Integer roomID){
        List<Channel> channels = new LinkedList<>();

        for (Map.Entry<UserRoom, Channel> userRoomChannelEntry : roomUserChannelMap.entrySet()) {
            UserRoom userRoom = userRoomChannelEntry.getKey();
            if(userRoom.getRoomID()==roomID){
                channels.add(userRoomChannelEntry.getValue());
            }
        }

        return channels;
    }

    //在房间里面广播消息
    public void publishMsg(Integer roomID,MRTPCommand mrtpCommand){

        Room room = RoomServerRouter.getInstance().RoomInfo(roomID);

        if(mrtpCommand.propagation == 0){

            if(room.getPurpose()== ServiceConfig.ROOM_PURPOSE_GUIDE) {
                mrtpCommand.propagation = MRTP.MSG_PROPAGATION_EXCEPT_OWNER;
            }

            else if(room.getPurpose()==ServiceConfig.ROOM_PURPOSE_GAME){
                mrtpCommand.propagation = MRTP.MSG_PROPAGATION_ROOM;
            }

            else if(room.getPurpose()==-1){
                mrtpCommand.propagation = MRTP.MSG_PROPAGATION_ROOM;
            }
        }

        //房间不处于正常运行时，不会转发IO消息
        if(!ServiceConfig.ROOM_STATE_RUNNING.equals(room.getState())&&mrtpCommand.operateCode==MRTP.FORWARD_IO_MESSAGE_RSP){
            return;
        }

        int i = room.messageIndex.addAndGet(1);
        mrtpCommand.id = i;
        mrtpCommand.gtime = System.currentTimeMillis();
        //记录房间里的每条命令
        roomMessage.get(roomID).add(mrtpCommand);

        mrtpCommand.roomID = roomID;

        //房间内所有人都能接受到
        if(mrtpCommand.propagation == MRTP.MSG_PROPAGATION_ROOM) {
            Set<Integer> availableUsers = new HashSet<>();
            availableUsers.addAll(availableUserMap.get(roomID));

            for (Integer availableUser : availableUsers) {

                this.sendMsg(new UserRoom(availableUser, roomID),room,mrtpCommand);
            }

            Set<Integer> offlineUsers = new HashSet<>();
            offlineUsers.addAll(offlineUserMap.get(roomID));

            if (offlineUsers == null || offlineUsers.isEmpty()) {
                return;
            }
            for (Integer offlineUser : offlineUsers) {
                UserRoom userRoom = new UserRoom(offlineUser, roomID);

                addOneOfflineCommand(userRoom,mrtpCommand);
            }
        }

        if(mrtpCommand.propagation == MRTP.MSG_PROPAGATION_EXCEPT_OWNER){
            Integer except_user = mrtpCommand.userID;

            Set<Integer> availableUsers = new HashSet<>();
            availableUsers.addAll(availableUserMap.get(roomID));

            for (Integer availableUser : availableUsers) {
                if(except_user.equals(availableUser)){
                    continue;
                }
                UserRoom userRoom = new UserRoom(availableUser, roomID);

                this.sendMsg(userRoom,room,mrtpCommand);
            }

            Set<Integer> offlineUsers = new HashSet<>();
            offlineUsers.addAll(offlineUserMap.get(roomID));

            if (offlineUsers == null || offlineUsers.isEmpty()) {
                return;
            }
            for (Integer offlineUser : offlineUsers) {
                if(except_user.equals(offlineUser)){
                    continue;
                }
                UserRoom userRoom = new UserRoom(offlineUser, roomID);

                addOneOfflineCommand(userRoom,mrtpCommand);
            }
        }

        if(mrtpCommand.propagation == MRTP.MSG_PROPAGATION_ONLY_OWNER){

            Integer owner = Integer.parseInt(room.getOwner());

            UserRoom userRoom = new UserRoom(owner,roomID);

            if(availableUserMap.get(roomID).contains(owner)){
                this.sendMsg(userRoom,room,mrtpCommand);
            }

            else {
                addOneOfflineCommand(userRoom,mrtpCommand);
            }
        }

        if(mrtpCommand.propagation == MRTP.MSG_PROPAGATION_ONLY_SENDER){
            Integer sender = mrtpCommand.userID;

            UserRoom userRoom = new UserRoom(sender,roomID);

            if(availableUserMap.get(roomID).contains(sender)){

                this.sendMsg(userRoom,room,mrtpCommand);
            }

            else {
                addOneOfflineCommand(userRoom,mrtpCommand);
            }
        }
    }


    private void sendMsg1(UserRoom userRoom,Room room,MRTPCommand mrtpCommand){


        Channel channel = roomUserChannelMap.get(userRoom);
        List<MRTPCommand> commands1 = roomUserUnReceivedMessageMap.get(userRoom);

        Iterator<MRTPCommand> iterator = commands1.iterator();

        try {
            while (iterator.hasNext()) {
                MRTPCommand command = iterator.next();
                MRTP.writeAndFlush(channel, command, new SenderCallback() {
                    @Override
                    public void callback(MRTPCommand command) {
                        if(ServiceConfig.ROOM_STATE_RUNNING.equals(room.getState())){
                            userRoomHasReceiveMsg.get(userRoom).add(mrtpCommand);
                        }
                    }
                }, new SenderCallback() {
                    @Override
                    public void callback(MRTPCommand command) {
                        System.out.println("一条消息发送失败，消息ID为："+command.id);
                        roomUserUnReceivedMessageMap.get(userRoom).add(command);
                    }
                });
                iterator.remove();
            }
        }catch (Exception e){

        }



        List<MRTPCommand> commands = roomUserOffLineMessageMap.get(userRoom);
        if (!commands.isEmpty()) {

            Iterator<MRTPCommand> iterator1 = commands.iterator();
            try {
                while (iterator1.hasNext()) {
                    MRTPCommand command = iterator1.next();
                    MRTP.writeAndFlush(channel, command);
                    iterator1.remove();
                }
            }catch (Exception e){}
        }

        MRTP.writeAndFlush(channel, mrtpCommand);

        if(ServiceConfig.ROOM_STATE_RUNNING.equals(room.getState())){
            userRoomHasReceiveMsg.get(userRoom).add(mrtpCommand);
        }
    }

    //如何解决网络波动问题带来的id小的消息未接受到，id大的消息成功发送
    private void sendMsg(UserRoom userRoom,Room room,MRTPCommand mrtpCommand){
        //1.通过userRoom 获取channel
        Channel channel = roomUserChannelMap.get(userRoom);
        //2.获取掉线期间的消息
        List<MRTPCommand> commandsInQueue = new LinkedList<>();

        List<MRTPCommand> commands1 = roomUserUnReceivedMessageMap.get(userRoom);
        if(!commands1.isEmpty()) {
            commandsInQueue.addAll(commands1);
            roomUserUnReceivedMessageMap.get(userRoom).removeAll(commands1);
        }

        //3.获取发送失败的消息
        List<MRTPCommand> commands = roomUserOffLineMessageMap.get(userRoom);
        if(!commands.isEmpty()) {
            commandsInQueue.addAll(commands);
            roomUserOffLineMessageMap.get(userRoom).removeAll(commands);
        }

        //4.将消息去重，并将消息按照id排序
        if(!commandsInQueue.isEmpty()) {
            Set<MRTPCommand> cmds = new HashSet<>();
            cmds.addAll(commandsInQueue);
            commandsInQueue.clear();
            commandsInQueue.addAll(cmds);
            Collections.sort(commandsInQueue);
        }


        //5.加上mrtpCommand,赋值lastID
        List<MRTPCommand> commands2 = userRoomHasReceiveMsg.get(userRoom);
        int lastIDInReceive = commands2.isEmpty()?0:commands2.get(commands2.size()-1).id;
        List<MRTPCommand> commands3 = msgInTransform.get(userRoom);
        int lastIDInTransform = commands3.isEmpty()?0:commands3.get(commands3.size()-1).id;
        mrtpCommand.lastID = lastIDInReceive>lastIDInTransform?lastIDInReceive:lastIDInTransform;

        commandsInQueue.add(mrtpCommand);

        //6.将list里面的消息清空，并将新list按顺序输出
        roomUserUnReceivedMessageMap.get(userRoom).removeAll(roomUserOffLineMessageMap.get(userRoom));

        //7.如果发送成功，记录用户成功接收消息。如果发送失败，将失败记录计入发送失败队列
        for (MRTPCommand command : commandsInQueue) {
            MRTP.writeAndFlush(channel, command, new SenderCallback() {
                @Override
                public void callback(MRTPCommand command) {
                    msgInTransform.get(userRoom).remove(command);
                    addOneReceivedCommand(userRoom,room,command);
                }
            }, new SenderCallback() {
                @Override
                public void callback(MRTPCommand command) {
//                    msgInTransform.get(userRoom).remove(command);
//                    System.out.println("一条消息发送失败，消息ID为："+command.id);
//                    roomUserUnReceivedMessageMap.get(userRoom).add(command);
                }
            });

            msgInTransform.get(userRoom).add(command);
        }

    }

    private void  addOneReceivedCommand(UserRoom userRoom,Room room,MRTPCommand mrtpCommand){
        if(!MRTP.isIOCommand(mrtpCommand)){
            return;
        }
        if(ServiceConfig.ROOM_STATE_RUNNING.equals(room.getState())){
            userRoomHasReceiveMsg.get(userRoom).add(mrtpCommand);
        }
    }

    private void addOneOfflineCommand(UserRoom userRoom,MRTPCommand mrtpCommand){
        if(!MRTP.isIOCommand(mrtpCommand)){
            return;
        }
        roomUserOffLineMessageMap.putIfAbsent(userRoom,new LinkedList<>());
        roomUserOffLineMessageMap.get(userRoom).add(mrtpCommand);
    }
    class UserRoom{
        private int userID;
        private int roomID;

        public UserRoom(int userID,int roomID){
            this.userID = userID;
            this.roomID = roomID;
        }

        public int getRoomID(){
            return roomID;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserRoom userRoom = (UserRoom) o;
            return userID == userRoom.userID &&
                    roomID == userRoom.roomID;
        }

        @Override
        public int hashCode() {
            return Objects.hash(userID, roomID);
        }
    }
}
