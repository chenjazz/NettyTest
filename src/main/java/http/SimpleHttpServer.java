package http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Chenjiazhi
 * 2018-06-22
 */
public class SimpleHttpServer {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup childGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.TRACE))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new HttpServerCodec(4096, 8192, 16384))
                                .addLast(new HttpServerExpectContinueHandler())
                                .addLast(new HttpObjectAggregator(1024 * 1024))
                                .addLast(new SimpleChannelInboundHandler<FullHttpRequest>() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        super.channelActive(ctx);
                                        System.out.println("------------");
                                    }

                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        super.channelRead(ctx, msg);
                                        System.out.println(msg);
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
                                        System.out.println("{{{{{{{"+msg.content().toString());

                                        DefaultHttpResponse response =
                                                new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                                        ctx.writeAndFlush(response);

                                        ctx.close();
                                    }
                                });
                    }
                })

        ;

        ChannelFuture sync = serverBootstrap.bind(8666).sync();

        sync.channel().closeFuture().sync();
    }

}
