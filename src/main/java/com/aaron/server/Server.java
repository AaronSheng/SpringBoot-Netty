package com.aaron.server;

import com.aaron.handler.SocketHandlerFactory;
import com.aaron.protocol.Protocol;
import com.aaron.thread.HeartThread;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Order(1)
public class Server extends Thread {
    private static final Logger logger = LogManager.getLogger(Server.class);
    @Autowired
    private HeartThread heartThread;
    @Autowired
    private SocketHandlerFactory socketHandlerFactory;

    @PostConstruct
    public void startServer() {
        heartThread.start();
        start();
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //Override
                        /**
                         * The method will be called when socket accepted.
                         * SocketHandler is added to channel's pipeline to handle event.
                         * SocketHandler must be prototype because it designed to be binded with socket.
                         */
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(socketHandlerFactory.getObject());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // bind and start to accept incoming connections.
            ChannelFuture channelFuture = serverBootstrap.bind(Protocol.DEFAULT_PORT).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("run", e);
        }
        finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
