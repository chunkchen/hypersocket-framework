/*******************************************************************************
 * Copyright (c) 2013 Hypersocket Limited.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.hypersocket.auth;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hypersocket.realm.Realm;
import com.hypersocket.realm.RealmRestriction;
import com.hypersocket.repository.AbstractEntityRepositoryImpl;
import com.hypersocket.repository.DeletedCriteria;
import com.hypersocket.repository.DetachedCriteriaConfiguration;
import com.hypersocket.repository.DistinctRootEntity;

@Repository
public class AuthenticationSchemeRepositoryImpl extends AbstractEntityRepositoryImpl<AuthenticationScheme,Long>
		implements AuthenticationSchemeRepository {

	DetachedCriteriaConfiguration ORDER_BY_PRIORITY = new DetachedCriteriaConfiguration() {
		@Override
		public void configure(DetachedCriteria criteria) {
			criteria.setFetchMode("modules", FetchMode.JOIN);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(Order.asc("priority"));
		}
	};

	@Override
	public AuthenticationScheme createScheme(Realm realm, String name,
			List<String> templates, String resourceKey, boolean hidden) {

		AuthenticationScheme scheme = new AuthenticationScheme();
		scheme.setName(name);
		scheme.setRealm(realm);
		scheme.setResourceKey(resourceKey);
		scheme.setHidden(hidden);
		saveEntity(scheme);

		int idx = 0;
		for (String t : templates) {
			AuthenticationModule m = new AuthenticationModule();
			m.setScheme(scheme);
			m.setTemplate(t);
			m.setIndex(idx++);
			save(m);
		}

		return scheme;
	}

	@Override
	public List<AuthenticationScheme> allSchemes(Realm realm) {
		return allEntities(AuthenticationScheme.class, ORDER_BY_PRIORITY, new RealmRestriction(realm));
	}

	class SchemeRestriction implements DetachedCriteriaConfiguration {

		AuthenticationScheme scheme;

		SchemeRestriction(AuthenticationScheme scheme) {
			this.scheme = scheme;
		}

		@Override
		public void configure(DetachedCriteria criteria) {
			criteria.add(Restrictions.eq("scheme", scheme));

		}
	}

	@Override
	public AuthenticationScheme createScheme(Realm realm, String name, List<String> modules,
			String resourceKey) {
		return createScheme(realm, name, modules, resourceKey, false);
	}

	@Override
	public AuthenticationScheme getSchemeByResourceKey(Realm realm, String resourceKey) {
		return get("resourceKey", resourceKey, AuthenticationScheme.class, new RealmRestriction(realm));
	}

	@Override
	public List<AuthenticationScheme> getAuthenticationSchemes(Realm realm) {

		return allEntities(AuthenticationScheme.class, new DeletedCriteria(
				false), new DistinctRootEntity(), new RealmRestriction(realm));

	}

	@Override
	public AuthenticationScheme getSchemeById(Long id) {
		return get("id", id, AuthenticationScheme.class);
	}

	@Override
	public void saveScheme(AuthenticationScheme s) {
		saveEntity(s);
	}

	@Override
	public AuthenticationScheme getSchemeByName(Realm realm, String name) {
		return get("name", name, AuthenticationScheme.class, new RealmRestriction(realm));
	}

	@Override
	protected Class<AuthenticationScheme> getEntityClass() {
		return AuthenticationScheme.class;
	}
}