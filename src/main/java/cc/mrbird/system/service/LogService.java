package cc.mrbird.system.service;

import java.util.List;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.SysLog;

public interface LogService extends IService<SysLog> {
	
	List<SysLog> findAllLogs(SysLog log);
	
	void deleteLogs(String logIds);
}
