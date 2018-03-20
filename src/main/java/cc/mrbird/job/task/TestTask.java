package cc.mrbird.job.task;

import org.springframework.stereotype.Component;

@Component("testTask")
public class TestTask {

	public void test(String params) {
		System.out.println("我是带参数的test方法，正在被执行，参数为：" + params);
	}

	public void test1() {
		System.out.println("我是不带参数的test1方法，正在被执行");
	}

}
