package cc.mrbird.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.util.HttpContextUtils;
import cc.mrbird.common.util.IPUtils;
import cc.mrbird.system.domain.SysLog;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.service.LogService;

@Aspect
@Component
public class LogAspect {

	@Autowired
	private LogService logService;

	@Pointcut("@annotation(cc.mrbird.common.annotation.Log)")
	public void pointcut() {
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint point) {
		Object result = null;
		long beginTime = System.currentTimeMillis();
		try {
			result = point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		long time = System.currentTimeMillis() - beginTime;
		saveLog(point, time);
		return result;
	}

	private void saveLog(ProceedingJoinPoint joinPoint, long time) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLog log = new SysLog();
		Log logAnnotation = method.getAnnotation(Log.class);
		if (logAnnotation != null) {
			log.setOperation(logAnnotation.value());
		}
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		log.setMethod(className + "." + methodName + "()");
		Object[] args = joinPoint.getArgs();
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paramNames = u.getParameterNames(method);
		if (args != null && paramNames != null) {
			String params = "";
			for (int i = 0; i < args.length; i++) {
				params += "  " + paramNames[i] + ": " + args[i];
			}
			log.setParams(params);
		}
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		log.setIp(IPUtils.getIpAddr(request));
		log.setUsername(user.getUsername());
		log.setTime(time);
		log.setCreateTime(new Date());
		log.setId(this.logService.getSequence(SysLog.SEQ));
		this.logService.save(log);
	}
}
