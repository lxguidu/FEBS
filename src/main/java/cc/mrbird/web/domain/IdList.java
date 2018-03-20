package cc.mrbird.web.domain;

import java.io.Serializable;

public class IdList implements Serializable {

	private static final long serialVersionUID = -6051593356331868324L;

	private String res;

	private Long[] data;

	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public Long[] getData() {
		return data;
	}

	public void setData(Long[] data) {
		this.data = data;
	}

}
