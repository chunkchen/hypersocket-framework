package com.hypersocket.services.json;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="managedService")
public class ManagedService {
	private String name;
	private boolean isRunning;
	
	public ManagedService(){}
	
	public ManagedService(String name, boolean isRunning) {
		super();
		this.name = name;
		this.isRunning = isRunning;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	
	
	
}
