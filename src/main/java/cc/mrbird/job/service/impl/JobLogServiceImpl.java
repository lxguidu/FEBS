package cc.mrbird.job.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.StringUtils;
import cc.mrbird.job.domain.JobLog;
import cc.mrbird.job.service.JobLogService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("JobLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobLogServiceImpl extends BaseService<JobLog> implements JobLogService {

	@Override
	public List<JobLog> findAllJobLogs(JobLog jobLog) {
		Example example = new Example(JobLog.class);
		Criteria criteria = example.createCriteria();
		if (StringUtils.hasValue(jobLog.getBeanName())) {
			criteria.andCondition("bean_name=", jobLog.getBeanName());
		}
		if (StringUtils.hasValue(jobLog.getMethodName())) {
			criteria.andCondition("method_name=", jobLog.getMethodName());
		}
		if (StringUtils.hasValue(jobLog.getStatus())) {
			criteria.andCondition("status=", Long.valueOf(jobLog.getStatus()));
		}
		example.setOrderByClause("log_id");
		return this.selectByExample(example);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void saveJobLog(JobLog log) {
		log.setLogId(this.getSequence(JobLog.SEQ));
		this.save(log);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteBatch(String jobLogIds) {
		List<String> list = Arrays.asList(jobLogIds.split(","));
		this.batchDelete(list, "logId", JobLog.class);
	}

}
