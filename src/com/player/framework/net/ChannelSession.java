package com.player.framework.net;

import java.net.InetSocketAddress;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class ChannelSession {

	private static AttributeKey<IdSession> KEY = AttributeKey.valueOf(SessionProperty.SESSION_ID);

	public static IdSession getSession(Channel channel) {
		Attribute<IdSession> sessionAttr = channel.attr(KEY);
		return sessionAttr.get();
	}

	public static boolean setSession(Channel channel, IdSession session) {
		Attribute<IdSession> sessionAttr = channel.attr(KEY);
		return sessionAttr.compareAndSet(null, session);
	}

	public static String getIp(Channel channel) {
		return ((InetSocketAddress) channel.remoteAddress()).getAddress().toString().substring(1);
	}

}
