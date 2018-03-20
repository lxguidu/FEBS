package cc.mrbird.system.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.StringUtils;
import cc.mrbird.system.domain.SysLog;
import cc.mrbird.system.service.LogService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("logService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl extends BaseService<SysLog> implements LogService {

	@Override
	public List<SysLog> findAllLogs(SysLog log) {
		Example example = new Example(SysLog.class);
		Criteria criteria = example.createCriteria();
		if (StringUtils.hasValue(log.getUsername())) {
			criteria.andCondition("username=", log.getUsername());
		}
		if (StringUtils.hasValue(log.getOperation())) {
			criteria.andCondition("operation like", "%" + log.getOperation() + "%");
		}
		example.setOrderByClause("create_time");
		return this.selectByExample(example);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteLogs(String logIds) {
		List<String> list = Arrays.asList(logIds.split(","));
		this.batchDelete(list, "id", SysLog.class);
	}

}
