package cn.mrworlds.lingjingshijie.messagecenter.protocol;

import cn.mrworlds.lingjingshijie.messagecenter.handler.callBack.SenderCallback;
import cn.mrworlds.lingjingshijie.messagecenter.router.RoomUserChannelRouter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MRTP {

    public static final int CMD_LOGIN_RSP = 2001;

    public static final int CREATE_ROOM_RSP_OK = 2021;
    public static final int CREATE_ROOM_RSP_FALSE = 2022;

    public static final int USER_ENTER_ROOM_RSP_OK = 2023;
    public static final int USER_ENTER_ROOM_RSP_FALSE = 20231;

    public static final int FORWARD_IO_MESSAGE_RSP = 2024;
    public static final int QUIT_ROOM_RSP = 2025;
    public static final int QUIT_ROOM_NOTIFYCATION = 2028;


    public static final int ROOM_SERVER_UNAVAILABLE = 2030;
    public static final int ROOM_SERVER_AVAILABLE = 2031;
    public static final int ROOM_SERVER_AVAILABLE_RSP = 2032;

    public static final int SQUEEZED_OFFLINE = 2040;

    public static final int ROOM_STATE_CHANGED = 2026;
    public static final int ROOM_OWNER_CHANGED = 2027;
    public static final int MSG_PROPAGATION_ROOM = 1;
    public static final int MSG_PROPAGATION_EXCEPT_OWNER = 2;
    public static final int MSG_PROPAGATION_ONLY_OWNER = 3;
    public static final int MSG_PROPAGATION_ONLY_SENDER = 4;


    private static GenericFutureListener<Future<? super Void>> listener = new GenericFutureListener<Future<? super Void>>() {

        @SneakyThrows
        @Override
        public void operationComplete(Future<? super Void> future)
                throws Exception {
            if (!future.isSuccess()) {
                Throwable cause = future.cause();

            }
        }
    };

    private static void send(final int cmd, final Channel toCtx,
                             final ByteBuf buf) {

        ChannelFuture future = toCtx.write(buf); // (3)
        future.addListener(listener);
        toCtx.flush();

    }

    public static void writeAndFlush(Channel toCtx, MRTPCommand cmd) {

        if (toCtx == null || cmd == null) {
            return;
        }
        ChannelFuture future = toCtx.writeAndFlush(new TextWebSocketFrame(cmd.toString()));
        future.addListener(listener);

    }

    public static void writeAndFlush(Channel toCtx, MRTPCommand cmd, SenderCallback successSend,SenderCallback failSend) {
        if (toCtx == null || cmd == null) {
            return;
        }
        ChannelFuture future = toCtx.writeAndFlush(new TextWebSocketFrame(cmd.toString()));
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future1) throws Exception {
                if(future1.isSuccess()){
                    Throwable cause = future1.cause();
                    if(cause!=null){

                        System.out.println("消息发送成功！,但是未被接收");
                    }
                    System.out.println("消息发送成功！");
                    successSend.callback(cmd);
                }
                else {
                    Throwable cause = future1.cause();
                    if(cause!=null){

                        System.out.println("消息发送失败！,但是未被接收");
                    }
                    System.out.println("消息发送失败！");
                    failSend.callback(cmd);
                }
            }
        });
    }

//    public static void writeAndFlushAsync(Channel toCtx, MRTPCommand cmd, SenderCallback successSend,SenderCallback failSend) throws InterruptedException {
//        if (toCtx == null || cmd == null) {
//            return;
//        }
//
//        ChannelFuture future = toCtx.writeAndFlush(new TextWebSocketFrame(cmd.toString())).sync();
//
//        if(future.isSuccess()){
//            successSend.callback(cmd);
//        }else {
//            failSend();
//        }
//
//    }

    public static void returnStringMsg(final int cmd, String msg, final Channel toCtx) {
        MRTPCommand command = new MRTPCommand(cmd, msg);
        writeAndFlush(toCtx, command);
    }


    public static boolean isIOCommand(MRTPCommand mrtpCommand) {

        return MRTP.FORWARD_IO_MESSAGE_RSP==mrtpCommand.operateCode;
    }
}
