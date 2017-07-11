package com.aaron.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class SocketClient {
    private static final int WORKER_THREAD = 1;
    private static Bootstrap bootstrap = null;
    private Channel mChannel = null;
    private InetSocketAddress mInetSocketAddress = null;

    public SocketClient(InetSocketAddress socketAddress) throws SocketConnectException {
        if (bootstrap == null) {
            initBootstrap();
        }

        connect(socketAddress);
    }

    public SocketClient(String ip, int port) throws SocketConnectException {
        if (bootstrap == null) {
            initBootstrap();
        }

        connect(new InetSocketAddress(ip, port));
    }

    public void connect(InetSocketAddress socketAddress) throws SocketConnectException {
        try {
            if (mChannel != null)
                mChannel.close();

            mInetSocketAddress = socketAddress;
            mChannel = bootstrap.connect(mInetSocketAddress).sync().channel();

            System.out.println("Connect to " + mInetSocketAddress.toString() + " succeed!");
        } catch (Exception e) {
            throw new SocketConnectException("Connect to " + mInetSocketAddress.toString() + " fail!");
        }
    }

    public void send(byte[] bytes) throws Exception {
        ByteBuf byteBuf = mChannel.alloc().buffer(4);
        byteBuf.writeBytes(bytes);
        mChannel.writeAndFlush(byteBuf).sync();
    }

    public void close() {
        mChannel.close();
    }

    public boolean isClientActive() {
        return (mChannel != null) && (mChannel.isActive());
    }

    public InetSocketAddress getAddress(){
        return this.mInetSocketAddress;
    }

    private void initBootstrap() {
        EventLoopGroup group = new NioEventLoopGroup(WORKER_THREAD);

        bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
            }
        });
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
    }
}