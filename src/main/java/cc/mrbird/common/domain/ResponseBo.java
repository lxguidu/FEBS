package cc.mrbird.common.domain;

import java.util.HashMap;

public class ResponseBo extends HashMap<String, Object> {

	private static final long serialVersionUID = -8713837118340960775L;

	// 成功
	private static final Integer SUCCESS = 0;
	// 警告
	private static final Integer WARN = 1;
	// 异常 失败
	private static final Integer FAIL = 500;

	public ResponseBo() {
		put("code", SUCCESS);
		put("msg", "操作成功");
	}

	public static ResponseBo error(Object msg) {
		ResponseBo ResponseBo = new ResponseBo();
		ResponseBo.put("code", FAIL);
		ResponseBo.put("msg", msg);
		return ResponseBo;
	}

	public static ResponseBo warn(Object msg) {
		ResponseBo ResponseBo = new ResponseBo();
		ResponseBo.put("code", WARN);
		ResponseBo.put("msg", msg);
		return ResponseBo;
	}

	public static ResponseBo ok(Object msg) {
		ResponseBo ResponseBo = new ResponseBo();
		ResponseBo.put("code", SUCCESS);
		ResponseBo.put("msg", msg);
		return ResponseBo;
	}

	public static ResponseBo ok() {
		return new ResponseBo();
	}

	public static ResponseBo error() {
		return ResponseBo.error("");
	}

	@Override
	public ResponseBo put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
