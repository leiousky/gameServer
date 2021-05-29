package com.player.framework.net;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum SessionManager {

	INSTANCE;

	private ConcurrentMap<Integer, IdSession> Storage = new ConcurrentHashMap<>();

	public IdSession getSessionByPlayerId(int playerId) {
		return this.Storage.get(playerId);
	}

	public long getPlayerIdBySession(IdSession session) {
		if (session != null) {
			return session.getPlayerId();
		}
		return 0;
	}

	public void setSession(int playerId, IdSession session) {
		session.setAttribute(SessionProperty.PLAYER_ID, playerId);
		this.Storage.put(playerId, session);
	}

}
