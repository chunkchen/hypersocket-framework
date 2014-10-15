package com.hypersocket.services.json;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "managedServices")
public class ManagedServicesList {
	List<ManagedService> managedServices;
	
	public ManagedServicesList() {
		super();
	}

	public ManagedServicesList(List<ManagedService> managedServices) {
		super();
		this.managedServices = managedServices;
	}

	public List<ManagedService> getServices() {
		return managedServices;
	}

	@XmlElement(name = "managedService")
	public void setServices(List<ManagedService> services) {
		this.managedServices = services;
	}

}
