package me.ianhe.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Socket示例：客户端
 *
 * @author iHelin
 * @create 2017-03-29 22:14
 */
public class Client {

    private static Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        String msg = "Client data";
        try {
            Socket socket = new Socket("localhost", 8080);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter.println(msg);
            printWriter.flush();
            String line = is.readLine();
            logger.debug("received from server: " + line);
            printWriter.close();
            is.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
