package me.ianhe.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 简单Socket示例
 *
 * @author iHelin
 * @create 2017-03-29 22:09
 */
public class Service {

    private static Logger logger = LoggerFactory.getLogger(Service.class);

    public static void main(String[] args) {
        try {
            //创建一个ServerSocket监听8080端口
            ServerSocket serverSocket = new ServerSocket(8080);
            //等待请求
            Socket socket = serverSocket.accept();
            //接收到请求后使用socket进行通信，创建BufferedReader用于读取数据
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = bufferedReader.readLine();
            logger.debug("received from client: " + line);
            //创建PrintWriter，用于发送数据
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println("received data: " + line);
            printWriter.flush();
            //关闭资源
            printWriter.close();
            bufferedReader.close();
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
