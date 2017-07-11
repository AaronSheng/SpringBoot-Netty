package com.aaron.handler;

import com.aaron.net.SocketConnection;
import com.aaron.net.SocketUtils;
import com.aaron.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Aaron Sheng on 5/26/17.
 * HandlerDispatcher is event dispatcher. It dispatches event to different handler.
 */
@Component
public class HandlerDispatcher {
    private static Logger logger = LogManager.getLogger(HandlerDispatcher.class);
    @Autowired
    private UserService userService;

    /**
     * distribute method response for distribute message to correspond handler.
     * @param socketConnection      socket connection.
     * @param msg                   received message.
     * @return if succeed handle message, return true. Or return false.
     */
    public boolean distribute(SocketConnection socketConnection, String msg) {
        System.out.println(socketConnection.getUserName());
        System.out.println("Receive: " + msg);

        JSONObject returnJsonObject = new JSONObject();
        returnJsonObject.put("result", "success");
        SocketUtils.sendPck(socketConnection, returnJsonObject);

        return true;
    }
}
