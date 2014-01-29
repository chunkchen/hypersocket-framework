/*******************************************************************************
 * Copyright (c) 2013 Hypersocket Limited.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.hypersocket.auth;

import java.util.List;

import com.hypersocket.repository.AbstractRepository;

public interface AuthenticationRepository extends AbstractRepository<Long> {

	public Credential createCredential(CredentialType type, Integer index, String resourceKey);
	
	public AuthenticationScheme createScheme(String name, List<String> modules, String resourceKey);
	
	public AuthenticationScheme createScheme(String name, List<String> modules, String resourceKey, boolean hidden);

	List<AuthenticationScheme> allSchemes();

	public List<AuthenticationModule> getModulesForScheme(
			AuthenticationScheme scheme);

	public AuthenticationScheme getScheme(String string);
	
}
