package cn.mrworlds.lingjingshijie.messagecenter.Constant;

public class Api {
    private static String create_room_api = "/room/v1/admin/add";
    private static String cancle_occupation_room_api = "/room/v1/cancle/occupation";
    private static String remove_room_residue = "/room/v1/clearAll/room";

    public static String getCreate_room_api(){
        return ServiceConfig.prefix + create_room_api;
    }

    public static String getCancle_occupation_room_api(){
        return ServiceConfig.prefix + cancle_occupation_room_api;
    }

    public static String getRemove_room_residue(){return ServiceConfig.prefix + remove_room_residue;}
}
