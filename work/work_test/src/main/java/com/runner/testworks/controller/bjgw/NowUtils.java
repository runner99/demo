package com.runner.testworks.controller.bjgw;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author weizhenqiang
 * @date 2023/9/11 14:23
 */
public class NowUtils {
    public static void main(String[] args) {
        List<String> ipList = getIpList();
        StringBuffer stringBuffer = new StringBuffer();
        ipList.stream().forEach(obj->{
            int i = obj.indexOf(":");
            if (i==-1){
                stringBuffer.append("\"");
                stringBuffer.append(obj.trim());
                stringBuffer.append("\"");
                stringBuffer.append(",");
            }else {
                stringBuffer.append("\"");
                stringBuffer.append(obj.substring(0,i).trim());
                stringBuffer.append("\"");
                stringBuffer.append(",");
            }
        });
        System.out.println(stringBuffer.toString().substring(0,stringBuffer.length()-1));
        try {
            FileWriter write=new FileWriter("/test/result.txt");
            BufferedWriter bw=new BufferedWriter(write);
            bw.write(stringBuffer.toString().substring(0,stringBuffer.length()-1));
            bw.close();
            write.close();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public static List<String> getIpList(){
        String pathname = "/test/test.txt";

        ArrayList<String> list = new ArrayList<>();

        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader)
        ) {
            String line;

            while ((line = br.readLine()) != null) {

                list.add(line);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> result = new ArrayList<>();

        list.stream().forEach(obj->{
            if (obj!=null&&obj.trim().length()!=0){
                String[] split = obj.split(",");
                for (int i=0;i<split.length;i++){
                    result.add(split[i].trim());
                }
            }
        });

        return result;
    }

}
