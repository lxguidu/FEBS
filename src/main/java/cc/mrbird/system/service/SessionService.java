package cc.mrbird.system.service;

import java.util.List;

import cc.mrbird.system.domain.UserOnline;

public interface SessionService {

	List<UserOnline> list();

	boolean forceLogout(String sessionId);
}
