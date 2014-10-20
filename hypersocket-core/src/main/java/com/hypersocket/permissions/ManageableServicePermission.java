package com.hypersocket.permissions;

public enum ManageableServicePermission implements PermissionType {

	START_SERVICE("serviceManagment.service.start"), 
	STOP_SERVICE("serviceManagment.service.stop");

	private final String val;

	private PermissionType[] implies;

	private ManageableServicePermission(final String val,
			PermissionType... implies) {
		this.val = val;
		this.implies = implies;
	}

	@Override
	public String getResourceKey() {
		return val;
	}

	@Override
	public boolean isSystem() {
		return false;
	}

	@Override
	public PermissionType[] impliesPermissions() {
		return implies;
	}

	@Override
	public boolean isHidden() {
		return false;
	}

}
