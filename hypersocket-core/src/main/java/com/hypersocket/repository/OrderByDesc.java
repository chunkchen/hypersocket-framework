/*******************************************************************************
 * Copyright (c) 2013 Hypersocket Limited.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.hypersocket.repository;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public class OrderByDesc implements DetachedCriteriaConfiguration {

	String property;
	public OrderByDesc(String property) {
		this.property = property;
	}
	@Override
	public void configure(DetachedCriteria criteria) {
		criteria.addOrder(Order.desc(property));
	}
}
