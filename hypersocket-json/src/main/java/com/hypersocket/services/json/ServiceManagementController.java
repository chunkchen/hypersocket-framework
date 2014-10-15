package com.hypersocket.services.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hypersocket.auth.json.AuthenticatedController;
import com.hypersocket.auth.json.AuthenticationRequired;
import com.hypersocket.auth.json.UnauthorizedException;
import com.hypersocket.json.ResourceStatus;
import com.hypersocket.permissions.AccessDeniedException;
import com.hypersocket.service.ManageableService;
import com.hypersocket.service.ServiceManagementService;
import com.hypersocket.session.json.SessionTimeoutException;

@Controller
public class ServiceManagementController extends AuthenticatedController {

	static Logger log = LoggerFactory
			.getLogger(ServiceManagementController.class);

	@Autowired
	ServiceManagementService serviceManagementService;
	
	
	@AuthenticationRequired
	@RequestMapping(value = "serviceManagement/list", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public ManagedServicesList getServices(
			HttpServletRequest request, HttpServletResponse response)
			throws AccessDeniedException, UnauthorizedException, IOException,
			SessionTimeoutException {

		setupAuthenticatedContext(sessionUtils.getSession(request),
				sessionUtils.getLocale(request));

		try {
			if (log.isInfoEnabled()) {
				log.info("Getting managiable services list");
			}
			Map<String, ManageableService> serviceList = serviceManagementService
					.getRegisteredServices();
			Set<String> keySet = serviceList.keySet();
			List<ManagedService> services = new ArrayList<ManagedService>();
			for (String serviceName : keySet) {
				services.add(new ManagedService(serviceName, serviceList.get(
						serviceName).isRunning()));
			}

			return new ManagedServicesList(services);
		} finally {
			clearAuthenticatedContext();
		}

	}
}
