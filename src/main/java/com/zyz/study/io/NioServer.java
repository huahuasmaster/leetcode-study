package com.zyz.study.io;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @author 执零
 * @version 1.0
 * @date 2021/1/31 0:52
 */
public class NioServer {
    public static void main(String[] args) throws Exception {
        LinkedList<SocketChannel> clients = new LinkedList<>();
        ServerSocketChannel ss = ServerSocketChannel.open();// 开启socket。系统调用socket
        ss.bind(new InetSocketAddress(9090));// 绑定端口。系统调用bind
        ss.configureBlocking(false);// 重点，将socket改为了非阻塞。系统调用fcntl

        while (true) {
            // 系统调用accept。非阻塞：此方法在没有新连接时，会直接返回null
            SocketChannel newClient = ss.accept();
            if (newClient != null) {
                // 连接本身也是socket，设为非阻塞
                newClient.configureBlocking(false);
                clients.add(newClient);
            }

            // 建一个Buffer作为读取缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
            // 遍历现有连接。注意：没有新起线程，这是NIO相对BIO的优势
            for (SocketChannel client : clients) {
                // 系统调用recv。不会阻塞，根据返回结果进行处理
                int read = client.read(byteBuffer);
                if (read <= 0) {
                    // 已有的数据读完了
                } else {
                    // 有数据，执行处理
                }
            }

        }
    }
}
