package com.webtest.webtest.json;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.lang.reflect.Member;
import java.util.Optional;


/**
 * @author weizhenqiang
 * @date 2023/5/18 16:42
 */
public class TestJson {


    private static String URL = "D:\\test\\operation.json";
    public static JSONObject getData(String methodName){
//        File file = new File("/capaa/services/data/"+url);
        File file = new File(URL);
        try {
            return readerMethod(file,methodName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static JSONObject readerMethod(File file,String methodName) throws IOException {
        FileReader fileReader = new FileReader(file);
        Reader reader = new InputStreamReader(new FileInputStream(file), "Utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        fileReader.close();
        reader.close();
        String jsonStr = sb.toString();
        Optional<JSONObject> jsonObject = Optional.ofNullable(JSON.parseObject(jsonStr)).map(sub -> sub.getJSONObject(methodName));

        return jsonObject.get();
    }

//    public static void main(String[] args) {
////        System.out.println(getData("operationEvent.json"));
//        System.out.println(getData("operationHidden"));
////        JSONObject data = getData("operationHidden.json");
////        Optional<JSONObject> total = Optional.ofNullable(data).map(sub -> sub.getJSONObject("testObj"));
////        System.out.println(total.get());
//
//    }
public static void main(String[] args) {
    String shell = "reboot";
    JSONObject jsonObject = JSONObject.parseObject( "{shellCmd:asdf}" );
    System.out.println(jsonObject);

}
}
