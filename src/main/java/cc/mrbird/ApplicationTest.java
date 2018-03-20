package cc.mrbird;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cc.mrbird.job.domain.Job;
import cc.mrbird.job.service.JobService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {
	
	
	@Autowired
	private JobService jobService;
	
	@Test
	public void test() throws Exception {
		Job job = new Job();
		job.setJobId(this.jobService.getSequence(Job.SEQ));
		job.setBeanName("testTask");
		job.setMethodName("test2");
		job.setCronExpression("0/3 * * * * ?");
		job.setStatus("1");
		job.setRemark("异常测试");
		job.setCreateTime(new Date());
		this.jobService.addJob(job);
	}
}
