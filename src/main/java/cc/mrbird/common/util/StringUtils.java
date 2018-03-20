package cc.mrbird.common.util;

public class StringUtils {
	public static String getStringValue(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static int getStringLength(String str) {
		if (str == null) {
			return 0;
		}
		return str.getBytes().length;
	}

	public static boolean hasValue(Object o) {
		if (o == null || o.toString().trim().equals("") || o.toString().trim().equalsIgnoreCase("null")) {
			return false;
		}
		return true;
	}

	public static boolean isNumber(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		String reg = "^[-|+]?\\d*([.]\\d+)?$";
		return str.matches(reg);
	}

	public static int toInteger(String str) {
		return Integer.parseInt(str);
	}

	/**
	 * 功能：把数字转化成字符串， 并根据长度在前面补0
	 * 
	 * 
	 */
	public static String switchToString(int val, int len) {
		String strVal = "" + val;
		int strLen = strVal.length();
		if (strVal.length() < len) {
			for (int i = 0; i < len - strLen; ++i) {
				strVal = "0" + strVal;
			}
		}
		return strVal;
	}
}
