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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.hypersocket.auth.json.AuthenticatedController;
import com.hypersocket.auth.json.AuthenticationRequired;
import com.hypersocket.auth.json.UnauthorizedException;
import com.hypersocket.i18n.I18N;
import com.hypersocket.json.RequestStatus;
import com.hypersocket.permissions.AccessDeniedException;
import com.hypersocket.service.ManageableService;
import com.hypersocket.service.ServiceManagementService;
import com.hypersocket.service.ServiceManagementServiceImpl;
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
	public ManagedServicesList getServices(HttpServletRequest request,
			HttpServletResponse response) throws AccessDeniedException,
			UnauthorizedException, IOException, SessionTimeoutException {

		setupAuthenticatedContext(sessionUtils.getSession(request),
				sessionUtils.getLocale(request));

		try {
			if (log.isInfoEnabled()) {
				log.info("Getting manageable services list");
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

	@AuthenticationRequired
	@RequestMapping(value = "serviceManagement/start", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public RequestStatus startService(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "service") String service)
			throws AccessDeniedException, UnauthorizedException, IOException,
			SessionTimeoutException {
		setupAuthenticatedContext(sessionUtils.getSession(request),
				sessionUtils.getLocale(request));
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		try {
			if (log.isInfoEnabled()) {
				log.info("Request to start service " + service);
			}
			ManageableService manageableService = serviceManagementService
					.getRegisteredServices().get(service);
			manageableService.start();
			return new RequestStatus(true, I18N.getResource(getSessionUtils()
					.getLocale(request),
					ServiceManagementServiceImpl.RESOURCE_BUNDLE,
					"serviceManagment." + service + ".start.success"));
		} catch (Exception e) {
			return new RequestStatus(false, I18N.getResource(getSessionUtils()
					.getLocale(request),
					ServiceManagementServiceImpl.RESOURCE_BUNDLE,
					"serviceManagment." + service + ".start.error"));
		} finally {
			clearAuthenticatedContext();
		}
	}

	@AuthenticationRequired
	@RequestMapping(value = "serviceManagement/stop", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public RequestStatus stopService(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "service") String service)
			throws AccessDeniedException, UnauthorizedException, IOException,
			SessionTimeoutException {
		setupAuthenticatedContext(sessionUtils.getSession(request),
				sessionUtils.getLocale(request));
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}
		try {
			if (log.isInfoEnabled()) {
				log.info("Request to stop service " + service);
			}
			ManageableService manageableService = serviceManagementService
					.getRegisteredServices().get(service);
			manageableService.stop();
			return new RequestStatus(true, I18N.getResource(getSessionUtils()
					.getLocale(request),
					ServiceManagementServiceImpl.RESOURCE_BUNDLE,
					"serviceManagment." + service + ".stop.success"));
		} catch (Exception e) {
			return new RequestStatus(false, I18N.getResource(getSessionUtils()
					.getLocale(request),
					ServiceManagementServiceImpl.RESOURCE_BUNDLE,
					"serviceManagment." + service + ".stop.error"));
		} finally {
			clearAuthenticatedContext();
		}
	}

}
