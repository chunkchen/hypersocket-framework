package com.hypersocket.tests.configuration;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.hypersocket.auth.AuthenticationPermission;
import com.hypersocket.config.ConfigurationPermission;
import com.hypersocket.json.JsonConfiguration;
import com.hypersocket.json.JsonResourceStatus;
import com.hypersocket.json.JsonRoleResourceStatus;
import com.hypersocket.tests.AbstractServerTest;

public class AbstractConfigurationTest extends AbstractServerTest {

	@BeforeClass
	public static void init() throws Exception {
		logon("Default", "admin", "Password123?");
		JsonResourceStatus jsonCreateUser = createUser("Default", "user",
				"user", false);
		changePassword("user", jsonCreateUser);
		Long[] permissions = {
				getPermissionId(AuthenticationPermission.LOGON.getResourceKey()),
				getPermissionId(ConfigurationPermission.READ.getResourceKey()) };
		JsonRoleResourceStatus jsonCreateRole = createRole("newrole",
				permissions);
		addUserToRole(jsonCreateRole.getResource(), jsonCreateUser);
		logoff();
		logon("Default", "user", "user");
	}

	@AfterClass
	public static void clean() throws Exception {
		logoff();
	}

	// Inner Class for use configuration testing on WithAdminPermission and
	// Delegate permission testings
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class Resources {
		JsonConfiguration[] resources;

		public JsonConfiguration[] getResources() {
			return resources;
		}

		public void setResources(JsonConfiguration[] resources) {
			this.resources = resources;
		}

	}
}