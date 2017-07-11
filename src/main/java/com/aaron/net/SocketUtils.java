package com.aaron.net;

import com.alibaba.fastjson.JSONObject;
import com.aaron.protocol.Protocol;
import com.aaron.utils.ByteUtils;

public class SocketUtils {
    public static void sendPck(SocketConnection socketConnection, JSONObject jsonObject) {
        String bodyString = jsonObject.toJSONString();
        byte[] bodyBuffer = bodyString.getBytes();
        int bodyLen = bodyBuffer.length;

        byte[] headBuffer = ByteUtils.int2FourBytes(bodyLen);
        int headLen = headBuffer.length;

        if (bodyLen >= Protocol.MAX_DATA_LEN) {
            throw new RuntimeException("SocketUtils sendPck package body over flow");
        }

        byte[] buffer = new byte[headLen + bodyLen];
        System.arraycopy(headBuffer, 0, buffer, 0, headLen);
        System.arraycopy(bodyBuffer, 0, buffer, headLen, bodyLen);

        socketConnection.write(buffer);
    }

    public static void sendPck(SocketClient client, JSONObject transferJsonObject) {
        String bodyString = transferJsonObject.toJSONString();
        byte[] bodyBuffer = bodyString.getBytes();
        int bodyLen = bodyBuffer.length;

        byte[] headBuffer = ByteUtils.int2FourBytes(bodyLen);
        int headLen = headBuffer.length;

        if (bodyLen >= Protocol.MAX_DATA_LEN) {
            throw new RuntimeException("SocketUtils sendPck package body over flow");
        }

        byte[] buffer = new byte[headLen + bodyLen];
        System.arraycopy(headBuffer, 0, buffer, 0, headLen);
        System.arraycopy(bodyBuffer, 0, buffer, headLen, bodyLen);
        try {
            client.send(buffer);
        } catch (Exception e) {
           throw new RuntimeException("SocketUtils sendPck exception");
        }
    }
}
