package cc.mrbird.common.handler;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 导出Excel设置接口。
 */
public interface ExportHandler {
	/**
	 * 设置表头样式
	 * 
	 * @param wb
	 *            当前Wordbook对象
	 * @return 处理后的样式
	 */
	CellStyle headCellStyle(SXSSFWorkbook wb);

	/**
	 * 设置导出的文件名（无需处理后缀）
	 * 
	 * @param sheetName
	 *            sheetName
	 * @return 处理后的文件名
	 */
	String exportFileName(String sheetName);
}
