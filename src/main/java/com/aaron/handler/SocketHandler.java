package com.aaron.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.aaron.protocol.Protocol;
import com.aaron.utils.ByteUtils;
import com.aaron.net.SocketConnection;

/**
 * Created by Aaron Sheng on 5/26/17.
 * SocketHandler is a event handler for socket event.
 * SocketHandler is binded to a socket when socket is accepted.
 * Every SocketChannel has a SocketHandlet in its pipeline.
 */
@Component
@Scope("prototype")
public class SocketHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(SocketHandler.class);
    private ByteBuf mByteBuf;
    private SocketConnection mSocketConnection;

    @Autowired
    private HandlerDispatcher handlerDispatcher;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("connected: " + ctx);

        mByteBuf = ctx.alloc().buffer();
        mSocketConnection = new SocketConnection(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("removed: " + ctx);

        mSocketConnection.close();
        mByteBuf.release();
        mByteBuf = null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("exceptionCaught " + cause.toString());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws NumberFormatException {
        ByteBuf tmpBuf = (ByteBuf) msg;
        mByteBuf.writeBytes(tmpBuf);
        tmpBuf.release();

        while (mByteBuf.readableBytes() >= Protocol.MAX_HEAD_LEN) {
            // get header bytes from byte buffer.
            byte[] headBuf = new byte[Protocol.MAX_HEAD_LEN];
            mByteBuf.getBytes(0, headBuf);

            int bodyLen = ByteUtils.fourBytes2Int(headBuf);

            // header bytes present length wrong. break the loop.
            if (bodyLen <= 0 || bodyLen > Protocol.MAX_DATA_LEN) {
                mSocketConnection.close();
                break;
            }

            // not enough data. wait for next read.
            if (mByteBuf.readableBytes() < (bodyLen + Protocol.MAX_HEAD_LEN)) {
                break;
            }

            // get body bytes from byte buffer.
            byte[] bodyBuf = new byte[bodyLen];
            mByteBuf.getBytes(Protocol.MAX_HEAD_LEN, bodyBuf);

            boolean result = handlerDispatcher.distribute(mSocketConnection, new String(bodyBuf));

            // get head and body data from buffer. and decrease readIndex and writeIndex.
            ByteBuf pckBuf = mByteBuf.readBytes(Protocol.MAX_HEAD_LEN + bodyLen);
            pckBuf.release();
            mByteBuf.discardReadBytes();

            if (!result) {
                mSocketConnection.close();
                break;
            }
        }
    }
}
