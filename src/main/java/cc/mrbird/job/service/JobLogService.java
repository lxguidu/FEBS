package cc.mrbird.job.service;

import java.util.List;

import cc.mrbird.common.service.IService;
import cc.mrbird.job.domain.JobLog;

public interface JobLogService extends IService<JobLog>{

	List<JobLog> findAllJobLogs(JobLog jobLog);

	void saveJobLog(JobLog log);
	
	void deleteBatch(String jobLogIds);
}
