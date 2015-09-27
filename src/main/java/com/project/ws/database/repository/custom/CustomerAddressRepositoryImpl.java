package com.project.ws.database.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.project.ws.database.domain.CustomerAddress;

public class CustomerAddressRepositoryImpl implements CustomerAddressCustomRepository {
	
		
		/**
		 * This EntityManager attribute is used to create the database queries 
		 */
		@PersistenceContext 
		private EntityManager em;
		
		public void setEntityManager(EntityManager em) {
			this.em = em;
		}

		/**
		 * Doing queries from database and mapped the results to the list of customer object.
		 */
		@Override
		public List<CustomerAddress> getAddress(Integer customerId) {
			String SQL = "select c from CustomerAddress c where customerId = " + customerId;
			TypedQuery<CustomerAddress> query = em.createQuery(SQL, CustomerAddress.class);
			List<CustomerAddress> addrList = query.getResultList();
			return addrList;
		}
		
		@Override
		@Transactional
		public Integer addCustomerAddress(Integer customerId, CustomerAddress customerAddress) {
			String SQL = "INSERT INTO customer_address (cust_id, cust_addr_line1, cust_addr_line2, cust_city, cust_state, cust_zip_code) VALUES(" +
					customerId + ", '" + customerAddress.getCustAddrLine1() + "', '" + customerAddress.getCustAddrLine2() + "', '" + customerAddress.getCustCity() + 
					"', '" + customerAddress.getCustState() + "', '" + customerAddress.getCustZipCode() + "')";

			Integer count = em.createNativeQuery(SQL).executeUpdate();
			if (count == 1) 
				System.out.println("address added successfully");
			else
				System.out.println("ERROR!!! Check logs/database");
			return count;

		}
		
		@Override
		@Transactional
		public Integer updateCustomerAddress(Integer customerId, CustomerAddress customerAddress) {
			String SQL="update customer_address set cust_addr_line1 = '" + customerAddress.getCustAddrLine1() + "', cust_addr_line2 = '" +
					customerAddress.getCustAddrLine2() + "', cust_city = '" + customerAddress.getCustCity() + "', cust_state = '" +
					customerAddress.getCustState() + "', cust_zip_code = '" + customerAddress.getCustZipCode() + "' where cust_id = " + 
					customerId;
			Integer count = em.createNativeQuery(SQL).executeUpdate();
			if (count == 1) 
				System.out.println("address updated successfully");
			else
				System.out.println("ERROR!!! Check logs/database");
			return count;
		}
		
		@Override
		@Transactional
		public Integer setDefaultAddress(Integer addressId) {
			String SQL="update customer_address set default_addr = 'Y' where cust_addr_id = " + addressId;
			Integer count = em.createNativeQuery(SQL).executeUpdate();
			if (count == 1) 
				System.out.println("default address set successfully");
			else
				System.out.println("ERROR!!! Check logs/database");
			return count;
		}
		
}
