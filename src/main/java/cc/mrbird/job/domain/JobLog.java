package cc.mrbird.job.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import cc.mrbird.common.annotation.ExportConfig;

@Table(name = "T_JOB_LOG")
public class JobLog implements Serializable {

	private static final long serialVersionUID = -7114915445674333148L;

	public static final String SEQ = "seq_job_log";

	@Id
	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "JOB_ID")
	@ExportConfig("任务ID")
	private Long jobId;

	@Column(name = "BEAN_NAME")
	@ExportConfig(value = "Bean名称")
	private String beanName;

	@Column(name = "METHOD_NAME")
	@ExportConfig(value = "方法名称")
	private String methodName;

	@Column(name = "PARAMS")
	@ExportConfig(value = "参数")
	private String params;

	@Column(name = "STATUS")
	@ExportConfig(value = "状态", convert = "s:0=成功,1=失败")
	private String status;

	@Column(name = "ERROR")
	@ExportConfig(value = "异常信息")
	private String error;

	@Column(name = "TIMES")
	@ExportConfig(value = "耗时（毫秒）")
	private Long times;

	@Column(name = "CREATE_TIME")
	@ExportConfig(value = "创建时间", convert = "c:cc.mrbird.common.util.poi.convert.TimeConvert")
	private Date createTime;

	/**
	 * @return LOG_ID
	 */
	public Long getLogId() {
		return logId;
	}

	/**
	 * @param logId
	 */
	public void setLogId(Long logId) {
		this.logId = logId;
	}

	/**
	 * @return JOB_ID
	 */
	public Long getJobId() {
		return jobId;
	}

	/**
	 * @param jobId
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	/**
	 * @return BEAN_NAME
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * @param beanName
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName == null ? null : beanName.trim();
	}

	/**
	 * @return METHOD_NAME
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName == null ? null : methodName.trim();
	}

	/**
	 * @return PARAMS
	 */
	public String getParams() {
		return params;
	}

	/**
	 * @param params
	 */
	public void setParams(String params) {
		this.params = params == null ? null : params.trim();
	}

	/**
	 * @return STATUS
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	/**
	 * @return ERROR
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error
	 */
	public void setError(String error) {
		this.error = error == null ? null : error.trim();
	}

	/**
	 * @return TIMES
	 */
	public Long getTimes() {
		return times;
	}

	/**
	 * @param times
	 */
	public void setTimes(Long times) {
		this.times = times;
	}

	/**
	 * @return CREATE_TIME
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}