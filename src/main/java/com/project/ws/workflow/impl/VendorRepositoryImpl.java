
package com.project.ws.workflow.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.project.ws.domain.Vendor;
import com.project.ws.workflow.custom.VendorCustomRepository;

public class VendorRepositoryImpl implements VendorCustomRepository {

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
		@Transactional
		public Integer addVendor(Vendor vendor) {
			//insert data in order_details
			String SQL = "INSERT INTO vendor (vendor_name, vendor_addrline1, vendor_addrline2, vendor_city, vendor_state, vendor_zip_code, vendor_country) VALUES('" +
						vendor.getVendorName() + "', '" + vendor.getVendorAddrLine1() + "', '" + vendor.getVendorAddrLine2() + "', '" +
						vendor.getVendorCity() + "', '" + vendor.getVendorState() + "', '" + vendor.getVendorZipCode() + "', '" + vendor.getVendorCountry() + "')";

			Integer count = em.createNativeQuery(SQL).executeUpdate();
			if (count == 1)
				System.out.println("vendor added successfully");
			else
				System.out.println("ERROR!!! Check logs/database");
			return count;
		}

		@Override
		@Transactional
		public Integer changeStatus(Integer vendorId, String flag) {
			String SQL = "update vendor set active_flag = '" + flag + "' where vendor_id = " + vendorId;
			Integer count = em.createNativeQuery(SQL).executeUpdate();
			if (count == 1)
				System.out.println("order shipped successfully");
			else
				System.out.println("ERROR!!! Check logs/database");
			return count;
		}

		@Override
		@Transactional
		public Integer updateAddress(Vendor vendor) {
			String SQL = "update vendor set vendor_addrline1 = '" + vendor.getVendorAddrLine1() + "', vendor_addrline2 = '" +
						vendor.getVendorAddrLine2() + "', vendor_city = '" + vendor.getVendorCity() + "', vendor_state = '" +
						vendor.getVendorState() + "', vendor_zip_code = '" + vendor.getVendorZipCode() + "' where vendor_id = " +
						vendor.getVendorId();
			Integer count = em.createNativeQuery(SQL).executeUpdate();
			if (count == 1)
				System.out.println("vendor address updated successfully");
			else
				System.out.println("ERROR!!! Check logs/database");
			return count;
		}

		@Override
		@Transactional
		public Integer settleAccount(Integer vendorId, Double amount, String paymentType) {
			String SQL = "select v from Vendor v where vendor_id = " + vendorId;
			TypedQuery<Vendor> query = em.createQuery(SQL, Vendor.class);
			Vendor vendor = query.getSingleResult(); 
			
			Double oldAmount = vendor.getVendorAmount();
			Double newAmount = 0.00;
			if(paymentType.equals("credit"))
				newAmount = oldAmount + amount;
			else if(paymentType.equals("debit"))
				newAmount = oldAmount - amount;
			
			SQL = "update vendor set amount_paid = " + newAmount + " where vendor_id = " + vendorId;
			Query nativeQuery = em.createNativeQuery(SQL);

			Integer count = nativeQuery.executeUpdate();
			if (count == 1)
				System.out.println("vendor amount settled successfully");
			else
				System.out.println("ERROR!!! Check logs/database");
			return count;
		}

		@Override
		@Transactional
		public String notifyVendor(Integer vendorId) {
			System.out.println("There is an order for product.Please check");
			return "notified";
		}






}
