package cc.mrbird.common.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.springframework.util.ResourceUtils;

import cc.mrbird.common.domain.ResponseBo;

public class FileUtils {

	/**
	 * 文件名加UUID
	 * 
	 * @param filename
	 * @return
	 */
	public static String makeFileName(String filename) {
		return UUID.randomUUID().toString() + "_" + filename;
	}

	/**
	 * 文件名特殊字符过滤
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String StringFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*+=|{}':; ',//[//]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 生成Excel文件
	 * 
	 * @param filename
	 * @param list
	 * @param clazz
	 * @return
	 */
	public static ResponseBo createExcelByPOIKit(String filename, List<?> list, Class<?> clazz) {

		if (list.isEmpty()) {
			return ResponseBo.warn("导出数据为空！");
		} else {
			boolean operateSign = false;
			String fileName = filename + ".xlsx";
			fileName = makeFileName(fileName);
			try {
				String path = ResourceUtils.getURL("classpath:").getPath() + "static/file/" + fileName;
				FileOutputStream fos = null;
				fos = new FileOutputStream(path);
				operateSign = ExcelUtils.$Builder(clazz)
						// 设置每个sheet的最大记录数,默认为10000,可不设置
						// .setMaxSheetRecords(10000)
						.toExcel(list, "查询结果", fos);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (operateSign) {
				return ResponseBo.ok(fileName);
			} else {
				return ResponseBo.error("导出Excel失败，请联系网站管理员！");
			}
		}
	}
	
	/**
	 * 生成Csv文件
	 * @param filename
	 * @param list
	 * @param clazz
	 * @return
	 */
	public static ResponseBo createCsv(String filename, List<?> list, Class<?> clazz) {

		if (list.isEmpty()) {
			return ResponseBo.warn("导出数据为空！");
		} else {
			boolean operateSign = false;
			String fileName = filename + ".csv";
			fileName = makeFileName(fileName);
			try {
				String path = ResourceUtils.getURL("classpath:").getPath() + "static/file/" + fileName;
				operateSign = ExcelUtils.$Builder(clazz)
						.toCsv(list, path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			if (operateSign) {
				return ResponseBo.ok(fileName);
			} else {
				return ResponseBo.error("导出Csv失败，请联系网站管理员！");
			}
		}
	}
}
