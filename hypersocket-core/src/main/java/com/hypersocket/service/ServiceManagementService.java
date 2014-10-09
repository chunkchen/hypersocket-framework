package com.hypersocket.service;

import java.util.Map;

import com.hypersocket.auth.AuthenticatedService;

public interface ServiceManagementService extends AuthenticatedService {

	public static final String RESOURCE_BUNDLE = "ServiceManagement";
	
	void registerService(ManageableService service);

	Map<String,ManageableService> getRegisteredServices();
}
