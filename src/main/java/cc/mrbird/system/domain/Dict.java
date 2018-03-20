package cc.mrbird.system.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import cc.mrbird.common.annotation.ExportConfig;

@Table(name = "T_DICT")
public class Dict {

	public static final String SEQ = "seq_dict";

	@Id
	@Column(name = "DICT_ID")
	@ExportConfig(value = "字典ID")
	private Long dictId;

	@Column(name = "KEY")
	@ExportConfig(value = "字典Key")
	private Long key;

	@Column(name = "VALUE")
	@ExportConfig(value = "字典Value")
	private String value;

	@Column(name = "TABLE_NAME")
	@ExportConfig(value = "列名")
	private String tableName;

	@Column(name = "FIELD_NAME")
	@ExportConfig(value = "表名")
	private String fieldName;

	/**
	 * @return DICT_ID
	 */
	public Long getDictId() {
		return dictId;
	}

	/**
	 * @param dictId
	 */
	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	/**
	 * @return KEY
	 */
	public Long getKey() {
		return key;
	}

	/**
	 * @param key
	 */
	public void setKey(Long key) {
		this.key = key;
	}

	/**
	 * @return VALUE
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value == null ? null : value.trim();
	}

	/**
	 * @return FIELD_NAME
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName == null ? null : fieldName.trim();
	}

	/**
	 * @return TABLE_NAME
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName == null ? null : tableName.trim();
	}
}