package cn.mrworlds.lingjingshijie.messagecenter.Constant;

public class ServiceConfig {
    public static final int ROOM_PURPOSE_GUIDE = 1;
    public static final int ROOM_PURPOSE_GAME = 2;
    public static final int ROOM_PURPOSE_CONTROL = 3;


    public static final Integer ROOM_STATE_UNBEGIN = 0;
    public static final Integer ROOM_STATE_RUNNING = 1;
    public static final Integer ROOM_STATE_SUSPEND = 2;
    public static final Integer ROOM_STATE_END = 3;

    public static String prefix = "http://172.17.148.18:11111/roomserver";

    public static String ROOM_ADDRESS_PARADIGM = "ws://47.93.26.215:{roomPort}/ws";
}
