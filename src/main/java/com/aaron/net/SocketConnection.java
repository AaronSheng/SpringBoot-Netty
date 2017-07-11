package com.aaron.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import com.aaron.utils.time.MyTime;

public class SocketConnection {
    private ChannelHandlerContext mChannelHandlerContext;
    private String userName;
    private String timestamp;

    public SocketConnection(ChannelHandlerContext ctx) {
        mChannelHandlerContext = ctx;
        userName = null;
        timestamp = (new MyTime()).format() + ctx.name();
    }

    public void setUserName(String name) {
        if (!mChannelHandlerContext.isRemoved()) {
            userName = name;
        }
    }

    public String getUserName() {
        if (!mChannelHandlerContext.isRemoved()) {
            return userName;
        }
        return null;
    }

    public void write(byte[] bytes) {
        if (!mChannelHandlerContext.isRemoved()) {
            ByteBuf byteBuf = mChannelHandlerContext.alloc().buffer();
            byteBuf.writeBytes(bytes);
            // ChannelHandlerContext finish writing will free ByteBuf.
            mChannelHandlerContext.writeAndFlush(byteBuf);
        }
    }

    public void close() {
        if (!mChannelHandlerContext.isRemoved()) {
            mChannelHandlerContext.close();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SocketConnection) {
            SocketConnection socketConnection = (SocketConnection) object;
            if (timestamp.equals(socketConnection.timestamp)) {
                return true;
            }
        }
        return false;
    }
}
