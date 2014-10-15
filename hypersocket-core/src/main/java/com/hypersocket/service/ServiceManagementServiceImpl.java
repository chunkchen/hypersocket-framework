package com.hypersocket.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hypersocket.auth.AuthenticatedServiceImpl;
import com.hypersocket.i18n.I18NService;

@Service
public class ServiceManagementServiceImpl extends AuthenticatedServiceImpl implements ServiceManagementService {

	public static final String RESOURCE_BUNDLE = "ServiceManagement";
	
	@Autowired
	I18NService i18nService;
	
	
	@PostConstruct
	private void postConstruct() {
		i18nService.registerBundle(RESOURCE_BUNDLE);
	}
	
	Map<String,ManageableService> services = new HashMap<String,ManageableService>();
	
	@Override
	public void registerService(ManageableService service) {
		services.put(service.getResourceKey(), service);
	}

	@Override
	public Map<String, ManageableService> getRegisteredServices() {
		return services;
	}
}
