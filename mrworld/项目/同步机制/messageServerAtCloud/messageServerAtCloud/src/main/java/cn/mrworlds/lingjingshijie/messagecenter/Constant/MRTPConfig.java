package cn.mrworlds.lingjingshijie.messagecenter.Constant;


import io.netty.util.AttributeKey;

public class MRTPConfig {

    public static final AttributeKey<Integer> WebSocketBin = AttributeKey.newInstance("WebSocketBin");
    /**
     * 服务器空闲检测超时时间
     */
    public static final int IDLE = 60 * 1000;


    /**
     * 主键分隔符,因为redis是KV数据库,当有多个主键时,将多个主键用分隔符链接起来,形成一个主键
     */
    public static final String LINK_CHAR_KEY = "-";
    /**
     * 不存在房间ID
     */
    public static final String NOT_EXIST_ROOM_ID = "NOT_EXIST_ROOM_ID";
    /**
     * 上次动作与当前操作最小的时间间隔
     */
    public static final long MIN_LAST_ACTION_INTERVAL = 1000;

    /**
     * 上次动作与当前操作最小的时间间隔
     */
    public static final long ROOM_RECYLE_INTERVAL = 3000;
    /**
     * gameID
     */
    public static final String KEY_GAME_ID = "GameID";

    public static final int DEFAULT_ROOM_PORT = 8899;
    public static final int DEFAULT_ROOM_PORT_WEBSOCKET = DEFAULT_ROOM_PORT-1;
    public static final int DEFAULT_ROOM_PORT_UDP= DEFAULT_ROOM_PORT-2;
    public static final int DEFAULT_GATEWAY_PORT = 12345;

    public static final int MAX_TASK_SIZE = 1024*10 ;
    //    public static String MessageQueueServerHost = "118.24.53.22";
    public static String MessageQueueServerHost = "192.168.1.118";
}
