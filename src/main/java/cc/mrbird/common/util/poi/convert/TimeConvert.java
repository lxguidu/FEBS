package cc.mrbird.common.util.poi.convert;

import cc.mrbird.common.util.DateUtil;

public class TimeConvert implements ExportConvert {

	@Override
	public String handler(Object val) {
		try {
			if (val == null)
				return "";
			else {
				return DateUtil.formatCSTTime(val.toString(), "yyyy-MM-dd HH:mm:ss");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
