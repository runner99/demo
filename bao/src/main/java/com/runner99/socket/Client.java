package com.runner99.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private Socket client;    //定义客户端套接字

    //建立客户端函数
    void getClient() {
        try {
            client = new Socket("127.0.0.1", 10001);    //建立客户端，使用的IP为127.0.0.1，端口和服务器一样为1100
            System.out.println("客户端建立成功！");

            setClientMessage();        //调用客户端信息写入函数
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //定义客户端信息写入函数
    void setClientMessage() {
        try {
            OutputStream pt = client.getOutputStream();        //建立客户端信息输出流
            String printText = "1+1";
            System.out.println("发送消息："+printText);
            pt.write(printText.getBytes());        //以二进制的形式将信息进行输出

            InputStream input = client.getInputStream();    //建立客户端信息输入流
            byte[] b = new byte[1024];        //定义字节数组
            int len = input.read(b);    //读取接收的二进制信息流
            String data = new String(b, 0, len);
            System.out.println("收到服务器消息：" + "hhaha");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            //如果客户端信息流不为空，则说明客户端已经建立连接，关闭客户端
            if (client != null) {
                client.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //生成客户端类对象
        MyClient myClient = new MyClient();
        myClient.getClient();
    }
}