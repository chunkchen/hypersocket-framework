package com.hypersocket.client.service;

import java.rmi.RemoteException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.hypersocket.client.db.HibernateSessionFactory;
import com.hypersocket.client.rmi.Connection;
import com.hypersocket.client.rmi.ConnectionImpl;
import com.hypersocket.client.rmi.ConnectionService;

public class ConnectionServiceImpl implements ConnectionService {

	Session session;
	public ConnectionServiceImpl() {
		session = HibernateSessionFactory.getFactory().openSession();
	}

	@Override
	public Connection createNew() {
		return new ConnectionImpl();
	}

	@Override
	public Connection save(Connection connection) {
	
		Transaction trans = session.beginTransaction();
		
		if(connection.getId()!=null) {
			session.merge(connection);
		} else {
			session.save(connection);
		}
		session.flush();
		trans.commit();
			
		return connection;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Connection> getConnections() throws RemoteException {
		
		Criteria crit = session.createCriteria(ConnectionImpl.class);
		return (List<Connection>) crit.list();
	}

	@Override
	public void delete(Connection con) {
		
		Transaction trans = session.beginTransaction();
		
		Criteria crit = session.createCriteria(ConnectionImpl.class);
		crit.add(Restrictions.eq("id", con.getId()));
		
		Connection toDelete = (Connection) crit.uniqueResult();
		session.delete(toDelete);
		session.flush();
		trans.commit();
		
	}

	@Override
	public Connection getConnection(String hostname) throws RemoteException {
		
		Criteria crit = session.createCriteria(ConnectionImpl.class);
		crit.add(Restrictions.eq("hostname", hostname));
		return (Connection) crit.uniqueResult();
		
	}
	
	@Override
	public Connection getConnection(Long id) throws RemoteException {
		
		Criteria crit = session.createCriteria(ConnectionImpl.class);
		crit.add(Restrictions.eq("id", id));
		return (Connection) crit.uniqueResult();
		
	}

}
