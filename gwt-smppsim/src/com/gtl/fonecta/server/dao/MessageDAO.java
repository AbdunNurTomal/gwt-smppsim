package com.gtl.fonecta.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gtl.fonecta.client.bean.Message;

/**
 * DAO class for Message
 * 
 * @author devang
 * 
 */
public class MessageDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(MessageDAO.class);

	/**
	 * Method to save the message
	 * 
	 * @param message
	 */
	public void save(Message message) {
		Integer messageId = null;

		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			messageId = (Integer) session.save(message);
			message.setMsgId(messageId);
			transaction.commit();
			log.info("inserted 1 message of type " + message.getMessageType());
		} catch (Exception e) {
			log.error(e);
			log.error(e);
			transaction.rollback();
		} finally {
			session.clear();
			session.flush();
			session.close();
		}
	}

	/**
	 * Method to find list of message having DourceAddress and  DestinationAddress
	 * 
	 * @param sourceAddr
	 * @param destAddr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> findBySrcDestAddress(Long sourceAddr, Long destAddr) {
		List<Message> messageList = new ArrayList<Message>();
		Session session = null;
		try {
			log.info("Finding message for Handset number :"
					+ sourceAddr.toString() + " and Service number :"
					+ destAddr.toString());
			session = getSession();
			String queryString = "from Message where source_addr=? and dest_addr=?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, sourceAddr);
			queryObject.setParameter(1, destAddr);
			messageList = queryObject.list();
		} catch (Exception e) {
			log.error(e);
		}
		return messageList;
	}

	/**
	 * Method to find all message and return list of message
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> findAll() {
		List<Message> messageList = new ArrayList<Message>();
		Session session = null;
		try {
			log.info("Finding all messages");
			session = getSession();
			String queryString = "from Message order by message_type";
			Query queryObject = session.createQuery(queryString);
			messageList = queryObject.list();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			session.clear();
			session.flush();
			session.close();
		}
		return messageList;
	}

	/**
	 * Method to find message using message id.
	 * 
	 * @param msgId
	 * @return
	 */
	public Message findById(int msgId) {
		Message message = null;
		Session session = null;
		try {
			log.info("Finding message id :" + msgId);
			session = getSession();
			String queryString = "from Message where msgId=?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, msgId);
			queryObject.executeUpdate();
		} catch (Exception e) {
			log.error(e);
		}
		return message;
	}

	/**
	 * Method to delete specific message.
	 * 
	 * @param persistentInstance
	 */
	public void delete(Message persistentInstance) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.delete(persistentInstance);
			transaction.commit();
			log.info("delete message with msgID "
					+ persistentInstance.getMsgId());
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.clear();
			session.flush();
			session.close();
		}
	}

	/**
	 * Method to delete specific message having message ID as id.
	 * 
	 * @param id
	 */
	public void deleteById(int id) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String queryString = "delete from Message where id=?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, id);
			queryObject.executeUpdate();
			transaction.commit();
			log.info("delete message with msgID " + id);
		} catch (Exception e) {
			log.error(e);
			transaction.rollback();
		} finally {
			session.clear();
			session.flush();
			session.close();
		}
	}

	/**
	 * Method to delete all messages.
	 * 
	 */
	public void deleteAll() {
		Session session = null;
		Transaction transaction = null;
		try {
			log.info("Removing all messages...");
			System.out.println("Removing all messages..."); 
			session = getSession();
			transaction = session.beginTransaction();
			String queryString = "delete from Message";
			Query queryObject = session.createQuery(queryString);
			queryObject.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			log.error(e);
			transaction.rollback();
		}
	}
}
