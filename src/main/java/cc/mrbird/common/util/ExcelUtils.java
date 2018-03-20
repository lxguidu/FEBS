package cc.mrbird.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.csvreader.CsvWriter;

import cc.mrbird.common.annotation.ExportConfig;
import cc.mrbird.common.handler.ExportHandler;
import cc.mrbird.common.util.poi.convert.ExportConvert;
import cc.mrbird.common.util.poi.pojo.ExportItem;

public class ExcelUtils {
	private Class<?> mClass = null;
	private HttpServletResponse mResponse = null;
	// 分Sheet机制：每个Sheet最多多少条数据
	private Integer mMaxSheetRecords = 10000;
	// 缓存数据格式器实例,避免多次使用反射进行实例化
	private Map<String, ExportConvert> mConvertInstanceCache = new HashMap<String, ExportConvert>();

	protected ExcelUtils() {
	}

	protected ExcelUtils(Class<?> clazz) {
		this(clazz, null);
	}

	protected ExcelUtils(Class<?> clazz, HttpServletResponse response) {
		this.mResponse = response;
		this.mClass = clazz;
	}

	/**
	 * 用于生成本地文件
	 * 
	 * @param clazz
	 *            实体Class对象
	 * @return ExcelUtils
	 */
	public static ExcelUtils $Builder(Class<?> clazz) {
		return new ExcelUtils(clazz);
	}

	/**
	 * 用于浏览器导出
	 * 
	 * @param clazz
	 *            实体Class对象
	 * @param response
	 *            原生HttpServletResponse对象
	 * @return ExcelUtils
	 */
	public static ExcelUtils $Export(Class<?> clazz, HttpServletResponse response) {
		return new ExcelUtils(clazz, response);
	}

	/**
	 * 分Sheet机制：每个Sheet最多多少条数据(默认10000)
	 * 
	 * @param size
	 *            数据条数
	 * @return this
	 */
	public ExcelUtils setMaxSheetRecords(Integer size) {
		this.mMaxSheetRecords = size;
		return this;
	}

	/**
	 * 导出Excel(此方式需依赖浏览器实现文件下载,故应先使用$Export()构造器)
	 * 
	 * @param data
	 *            数据集合
	 * @param sheetName
	 *            工作表名字
	 * @return true-操作成功,false-操作失败
	 */
	public boolean toExcel(List<?> data, String sheetName) {
		required$ExportParams();

		try {
			return toExcel(data, sheetName, mResponse.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 针对转换方法的默认实现(提供默认样式和文件命名规则)
	 * 
	 * @param data
	 *            数据集合
	 * @param sheetName
	 *            工作表名字
	 * @param out
	 *            输出流
	 * @return true-操作成功,false-操作失败
	 */
	public boolean toExcel(List<?> data, String sheetName, OutputStream out) {

		return toExcel(data, sheetName, new ExportHandler() {

			@Override
			public CellStyle headCellStyle(SXSSFWorkbook wb) {
				CellStyle cellStyle = wb.createCellStyle();
				Font font = wb.createFont();
				cellStyle.setFillForegroundColor((short) 12);
				cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充模式
				cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 上边框为细边框
				cellStyle.setBorderRight(CellStyle.BORDER_THIN);// 右边框为细边框
				cellStyle.setBorderBottom(CellStyle.BORDER_THIN);// 下边框为细边框
				cellStyle.setBorderLeft(CellStyle.BORDER_THIN);// 左边框为细边框
				cellStyle.setAlignment(CellStyle.ALIGN_LEFT);// 对齐
				cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
				cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
				font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
				// font.setFontHeightInPoints((short) 12);// 字体大小
				font.setColor(HSSFColor.WHITE.index);
				// 应用标题字体到标题样式
				cellStyle.setFont(font);
				return cellStyle;
			}

			@Override
			public String exportFileName(String sheetName) {
				return String.format("导出-%s-%s", sheetName, System.currentTimeMillis());
			}
		}, out);
	}

	public boolean toExcel(List<?> data, String sheetName, ExportHandler handler, OutputStream out) {
		required$BuilderParams();
		if (data == null || data.size() < 1) {
			return false;
		}

		// 导出列查询。
		ExportConfig currentExportConfig = null;
		ExportItem currentExportItem = null;
		List<ExportItem> exportItems = new ArrayList<ExportItem>();
		for (Field field : mClass.getDeclaredFields()) {

			currentExportConfig = field.getAnnotation(ExportConfig.class);
			if (currentExportConfig != null) {
				currentExportItem = new ExportItem().setField(field.getName())
						.setDisplay("field".equals(currentExportConfig.value()) ? field.getName()
								: currentExportConfig.value())
						.setWidth(currentExportConfig.width()).setConvert(currentExportConfig.convert())
						.setColor(currentExportConfig.color()).setReplace(currentExportConfig.replace());
				exportItems.add(currentExportItem);
			}

			currentExportItem = null;
			currentExportConfig = null;
		}

		// 创建新的工作薄。
		SXSSFWorkbook wb = POIUtils.newSXSSFWorkbook();

		double sheetNo = Math.ceil((double) data.size() / mMaxSheetRecords);// 取出一共有多少个sheet.

		// =====多sheet生成填充数据=====
		for (int index = 0; index <= (sheetNo == 0.0 ? sheetNo : sheetNo - 1); index++) {
			SXSSFSheet sheet = POIUtils.newSXSSFSheet(wb, sheetName + (index == 0 ? "" : "_" + index));

			// 创建表头
			SXSSFRow headerRow = POIUtils.newSXSSFRow(sheet, 0);
			for (int i = 0; i < exportItems.size(); i++) {
				SXSSFCell cell = POIUtils.newSXSSFCell(headerRow, i);
				POIUtils.setColumnWidth(sheet, i, exportItems.get(i).getWidth(), exportItems.get(i).getDisplay());
				cell.setCellValue(exportItems.get(i).getDisplay());

				CellStyle style = handler.headCellStyle(wb);
				if (style != null) {
					cell.setCellStyle(style);
				}
			}

			SXSSFRow bodyRow;
			String cellValue;
			SXSSFCell cell;
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			style.setFont(font);

			// 产生数据行
			if (data != null && data.size() > 0) {
				int startNo = index * mMaxSheetRecords;
				int endNo = Math.min(startNo + mMaxSheetRecords, data.size());

				for (int i = startNo; i < endNo; i++) {
					bodyRow = POIUtils.newSXSSFRow(sheet, i + 1 - startNo);
					for (int j = 0; j < exportItems.size(); j++) {
						// 处理单元格值
						cellValue = exportItems.get(j).getReplace();
						if ("".equals(cellValue)) {
							try {
								cellValue = BeanUtils.getProperty(data.get(i), exportItems.get(j).getField());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						// 格式化单元格值
						if (!"".equals(exportItems.get(j).getConvert())) {
							cellValue = convertCellValue(cellValue, exportItems.get(j).getConvert());
						}

						// 单元格宽度
						POIUtils.setColumnWidth(sheet, j, exportItems.get(j).getWidth(), cellValue);

						cell = POIUtils.newSXSSFCell(bodyRow, j);
						// fix: 当值为“”时,当前index的cell会失效
						cell.setCellValue("".equals(cellValue) ? null : cellValue);
						cell.setCellStyle(style);
					}
				}
			}
		}

		try {
			// 生成Excel文件并下载.(通过response对象是否为空来判定是使用浏览器下载还是直接写入到output中)
			POIUtils.writeByLocalOrBrowser(mResponse, handler.exportFileName(sheetName), wb, out);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean toCsv(List<?> data, String path) {
		try {
			required$BuilderParams();
			if (data == null || data.size() < 1) {
				return false;
			}

			// 导出列查询。
			ExportConfig currentExportConfig = null;
			ExportItem currentExportItem = null;
			List<ExportItem> exportItems = new ArrayList<ExportItem>();
			for (Field field : mClass.getDeclaredFields()) {

				currentExportConfig = field.getAnnotation(ExportConfig.class);
				if (currentExportConfig != null) {
					currentExportItem = new ExportItem().setField(field.getName())
							.setDisplay("field".equals(currentExportConfig.value()) ? field.getName()
									: currentExportConfig.value())
							.setConvert(currentExportConfig.convert()).setReplace(currentExportConfig.replace());
					exportItems.add(currentExportItem);
				}

				currentExportItem = null;
				currentExportConfig = null;
			}

			String cellValue;
			FileOutputStream out = new FileOutputStream(path);
			// 解决乱码
			out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });
			CsvWriter csvWriter = new CsvWriter(out, ',', Charset.forName("UTF-8"));
			List<Object> csvHeaders = new ArrayList<Object>();
			for (int i = 0; i < exportItems.size(); i++) {
				csvHeaders.add(exportItems.get(i).getDisplay());
			}
			String[] csvHeadersArr = csvHeaders.toArray(new String[csvHeaders.size()]);
			csvWriter.writeRecord(csvHeadersArr);
			for (int i = 0; i < data.size(); i++) {
				List<Object> csvContent = new ArrayList<Object>();
				for (int j = 0; j < exportItems.size(); j++) {
					// 处理单元格值
					cellValue = exportItems.get(j).getReplace();
					if (!StringUtils.hasValue(cellValue)) {
						try {
							cellValue = BeanUtils.getProperty(data.get(i), exportItems.get(j).getField());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					// 格式化单元格值
					if (StringUtils.hasValue(exportItems.get(j).getConvert())) {
						cellValue = convertCellValue(cellValue, exportItems.get(j).getConvert());
					}
					csvContent.add(cellValue);
				}
				String[] csvContentArr = csvContent.toArray(new String[csvContent.size()]);
				csvWriter.writeRecord(csvContentArr);
			}
			csvWriter.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// convertCellValue: number to String (beta)
	private String convertCellValue(Object oldValue, String format) {
		try {
			String protocol = format.split(":")[0];

			// 键值对字符串解析：s:1=男,2=女
			if ("s".equalsIgnoreCase(protocol)) {

				String[] pattern = format.split(":")[1].split(",");
				for (String p : pattern) {
					String[] cp = p.split("=");
					if (cp[0].equals(oldValue)) {
						return cp[1];
					}
				}

			}
			if ("c".equalsIgnoreCase(protocol)) {

				String clazz = format.split(":")[1];
				ExportConvert export = mConvertInstanceCache.get(clazz);
				if (export == null) {
					export = (ExportConvert) Class.forName(clazz).newInstance();
					mConvertInstanceCache.put(clazz, export);
				}

				if (mConvertInstanceCache.size() > 10)
					mConvertInstanceCache.clear();

				return export.handler(oldValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(oldValue);
	}

	private void required$BuilderParams() {
		if (mClass == null) {
			throw new IllegalArgumentException("请先使用cc.mrbird.util.ExcelUtils.$Builder(Class<?>)构造器初始化参数。");
		}
	}

	private void required$ExportParams() {
		if (mClass == null || mResponse == null) {
			throw new IllegalArgumentException(
					"请先使用cc.mrbird.util.ExcelUtils.$Export(Class<?>, HttpServletResponse)构造器初始化参数。");
		}

	}
}
