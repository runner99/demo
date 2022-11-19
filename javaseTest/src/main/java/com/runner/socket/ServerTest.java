package com.runner.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    private ServerSocket server;
    private Socket client;

    public void getServer() throws IOException {

        server = new ServerSocket(1100);
        System.out.println("服务端建立成功！正在等待连接......");
        //接收到客户端的消息
        client = server.accept();
        InputStream is = client.getInputStream();
        byte[] b = new byte[1024];
        int len = is.read(b);
        String data = new String(b, 0,len);
        System.out.println("服务端接收到的消息："+data);

        //对消息进行处理
        int result = 0;
        try {
            int i = data.lastIndexOf("+");
            String begin = data.substring(0,i);
            String end = data.substring(i+1);
            result = Integer.parseInt(begin)+Integer.parseInt(end);
        }catch (Exception e){
            e.getMessage();
        }

        //结果返回给客户端
        OutputStream put = client.getOutputStream();
        String putText = ""+result;
        put.write(putText.getBytes());

        server.close();
    }

    public static void main(String[] args) throws IOException {
        ServerTest serverTest = new ServerTest();
        serverTest.getServer();
    }
}