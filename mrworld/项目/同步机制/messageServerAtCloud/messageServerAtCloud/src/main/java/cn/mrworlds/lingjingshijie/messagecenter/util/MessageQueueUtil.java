package cn.mrworlds.lingjingshijie.messagecenter.util;

import cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler.DestoryRoomHandler;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTP;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomServerRouter;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class MessageQueueUtil {
    public static Channel channel;

    public static void initCHannel(Channel ClientChannel){
        if(channel==null){
            log.info("开启定时任务，每隔一小时将为释放的房间资源释放");
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    flushUnClosedRoom();
                }
            }, 0, 1000*60*60*1);
        }
        channel = ClientChannel;
        log.info("逻辑中心已连接");
    }

    public static void SendMRTPCommand(MRTPCommand mrtpCommand){
        MRTP.writeAndFlush(channel,mrtpCommand);
    }

    public static void flushUnClosedRoom(){
        List<Integer> roomIDs = RoomServerRouter.getInstance().availableRooms();

        Cache cache = SpringUtils.getBean(Cache.class);

        for (Integer roomID : roomIDs) {
            String key =  "room_limits:" + roomID;

            if(!cache.hasKey(key)){
                new DestoryRoomHandler().destoryOneRoom(roomID);
            }
        }
    }
}
