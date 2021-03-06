/*******************************************************************************
 * Copyright (c) 2013 Hypersocket Limited.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.hypersocket.auth;

import java.util.Map;

import com.hypersocket.input.FormTemplate;
import com.hypersocket.permissions.AccessDeniedException;

public interface PostAuthenticationStep {

	public boolean requiresProcessing(AuthenticationState state);
	
	public int getOrderPriority();
	
	public String getResourceKey();
	
	public AuthenticatorResult process(AuthenticationState state, @SuppressWarnings("rawtypes") Map parameters) throws AccessDeniedException;
	
	public FormTemplate createTemplate(AuthenticationState state);

	boolean requiresUserInput(AuthenticationState state);

	boolean requiresSession(AuthenticationState state);
}
