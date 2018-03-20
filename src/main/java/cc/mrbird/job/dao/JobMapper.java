package cc.mrbird.job.dao;

import java.util.List;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.job.domain.Job;

public interface JobMapper extends MyMapper<Job> {
	
	List<Job> queryList();
}