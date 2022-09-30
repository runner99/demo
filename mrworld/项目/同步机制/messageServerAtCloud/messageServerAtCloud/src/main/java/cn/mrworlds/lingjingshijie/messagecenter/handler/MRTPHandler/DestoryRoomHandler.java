package cn.mrworlds.lingjingshijie.messagecenter.handler.MRTPHandler;

import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCode;
import cn.mrworlds.lingjingshijie.messagecenter.protocol.MRTPCommand;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomServerRouter;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomUserChannelRouter;
import cn.mrworlds.lingjingshijie.messagecenter.util.MessageQueueUtil;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

public class DestoryRoomHandler implements IMRTPHandler {
    @Override
    public boolean handle(ChannelHandlerContext ctx, MRTPCommand msg) {

        if(msg.operateCode == MRTPCode.DESTORY_ROOM){
            destoryOneRoom(msg.roomID);

            return true;
        }

        return false;
    }

    public void destoryOneRoom(Integer roomID){

        if(roomID==null){
            return;
        }

        //清除roomUserChannel里的各个路由项
        RoomUserChannelRouter.getInstance().unRegesterOne(roomID);

        //清除roomID-RoomServer路由表，光比roomServer,释放占用port
        RoomServerRouter.getInstance().UnRegesterOne(roomID);

        //清除roomID-RoomInfo路由表
        RoomServerRouter.getInstance().unregesterOneRoom(roomID);

        //将未持久化的消息持久化
//        List<MRTPCommand> commands = RoomUserChannelRouter.getInstance().fetchMRTPCommand(roomID,);
//        if(commands!=null) {
//            for (MRTPCommand command : commands) {
//                MessageQueueUtil.SendMRTPCommand(command);
//            }
//        }
    }
}
