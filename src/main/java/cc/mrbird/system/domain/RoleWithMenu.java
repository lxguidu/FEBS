package cc.mrbird.system.domain;

import java.util.List;

public class RoleWithMenu extends Role{

	private static final long serialVersionUID = 2013847071068967187L;
	
	private Long menuId;
	
	private List<Long> menuIds;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public List<Long> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}
	
	

}
