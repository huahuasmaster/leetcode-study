package com.zyz.study.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/31 0:12
 */
public class BioServer {
    public static void main(String[] args) throws Exception {
        // 开启socket，会有三句系统调用: socket,bind,listen
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            // 获取一个客户端连接，如果没有会一直阻塞！系统调用为accept
            Socket client = serverSocket.accept();
            // 为了防止某个连接的阻塞造成整个BioServer的阻塞，针对每个连接都会单独使用线程进行业务处理
            new Thread(() -> {
                //模拟读取客户端数据操作
                try {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(client.getInputStream())
                    );
                    while (true) {
                        // 从连接中获取客户端传来的数据，如果此时没有数据会一直阻塞！系统调用为recv
                        String s = bufferedReader.readLine();
                        System.out.println(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }
}
