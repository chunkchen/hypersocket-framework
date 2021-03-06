/*******************************************************************************
 * Copyright (c) 2013 Hypersocket Limited.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.hypersocket.auth;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.hypersocket.i18n.I18N;
import com.hypersocket.input.FormTemplate;
import com.hypersocket.input.PasswordInputField;
import com.hypersocket.input.TextInputField;

@XmlRootElement(name = "form")
public class UsernameAndPasswordTemplate extends FormTemplate {

	public static final String USERNAME_FIELD = "username";
	public static final String PASSWORD_FIELD = "password";

	@SuppressWarnings("rawtypes")
	public UsernameAndPasswordTemplate(AuthenticationState state, Map params) {

		setResourceKey(UsernameAndPasswordAuthenticator.RESOURCE_KEY);

		TextInputField username = new TextInputField(USERNAME_FIELD, "", true, I18N
				.getResource(state.getLocale(),
						AuthenticationServiceImpl.RESOURCE_BUNDLE,
						"username.label"));

		if (state.getPrincipal() != null) {
			username.setDefaultValue(state.getPrincipal().getName());
		} else if (params.containsKey(USERNAME_FIELD)) {
			username.setDefaultValue(AuthenticationUtils.getRequestParameter(
					params, UsernameAndPasswordTemplate.USERNAME_FIELD));
		}

		fields.add(username);
		fields.add(new PasswordInputField(PASSWORD_FIELD, "", true, "Password"));

	}

}
