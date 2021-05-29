package com.player.framework.net;

import java.util.Collection;
import com.player.framework.serializer.Message;

public class MessageRouter {

	public static void send(int playerId, Message message) {
		IdSession session = SessionManager.INSTANCE.getSessionById(playerId);
		send(session, message);
	}

	public static void send(Collection<Integer> playerIds, Message message) {
		for (int playerId : playerIds) {
			send(playerId, message);
		}
	}

	public static void send(IdSession session, Message message) {
		if (session == null || message == null) {
			return;
		}
		session.send(message);
	}

}
