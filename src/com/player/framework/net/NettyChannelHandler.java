package com.player.framework.net;

import com.player.framework.serializer.Message;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyChannelHandler extends ChannelInboundHandlerAdapter {

	private MessageDispatcherFactory messageDispatcher;

	public NettyChannelHandler(MessageDispatcherFactory messageDispatcher) {
		super();
		this.messageDispatcher = messageDispatcher;
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		if (!ChannelSession.setSession(ctx.channel(), new PlayerSession(channel))) {
			ctx.channel().close();
		}
		IdSession session = ChannelSession.getSession(channel);
		this.messageDispatcher.onSessionCreated(session);
	}

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message = (Message) msg;
		IdSession session = ChannelSession.getSession(ctx.channel());
		this.messageDispatcher.dispatch(session, message);
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		IdSession session = ChannelSession.getSession(ctx.channel());
		this.messageDispatcher.onSessionClosed(session);
	}

	public void userEventTriggered(ChannelHandlerContext ctx, Object msg) throws Exception {
		IdSession session = ChannelSession.getSession(ctx.channel());
		this.messageDispatcher.onSessionClosed(session);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		try {
			ctx.close();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

}
