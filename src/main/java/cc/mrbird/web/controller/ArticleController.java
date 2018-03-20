package cc.mrbird.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.HttpUtils;
import cc.mrbird.common.util.StringUtils;
import cc.mrbird.common.util.UrlUtils;

@Controller
public class ArticleController {

	@RequestMapping("article")
	public String index() {
		return "web/article/article";
	}

	@RequestMapping("article/query")
	@ResponseBody
	public ResponseBo queryArticle(String date) {
		String param = "";
		String data = "";
		try {
			if (StringUtils.hasValue(date)) {
				param = "dev=1&date=" + date;
				data = HttpUtils.sendSSLPost(UrlUtils.MRYW_DAY_URL, param);
			} else {
				param = "dev=1";
				data = HttpUtils.sendSSLPost(UrlUtils.MRYW_TODAY_URL, param);
			}
			return ResponseBo.ok(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("获取文章失败，请联系网站管理员！");
		}
	}
}
