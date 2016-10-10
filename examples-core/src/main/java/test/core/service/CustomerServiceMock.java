package test.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import test.core.model.Customer;

@Stateless
public class CustomerServiceMock implements CustomerService {

	@Inject
	private Database database;

	@Override
	public Customer findById(Long id) {
		for (Customer customer : database.getCustomers().values()) {
			if (customer.getId().equals(id)) {
				return customer;
			}
		}

		return null;
	}

	@Override
	public List<Customer> findByCriteria(CustomerCriteria criteria) {
		List<Customer> customerList = new ArrayList<Customer>();

		for (Customer customer : database.getCustomers().values()) {
			if (true) {
				customerList.add(customer);
			}
		}

		return customerList;
	}

	@Override
	public CustomerCriteria createCriteria() {
		return new CustomerCriteriaBean();
	}

	private static class CustomerCriteriaBean implements CustomerCriteria {

		@Override
		public CustomerCriteria id(String id) {
			return this;
		}

		@Override
		public CustomerCriteria firstName(String firstName) {
			return this;
		}

		@Override
		public CustomerCriteria surname(String surname) {
			return this;
		}

		@Override
		public CustomerCriteria taxNumber(String taxNumber) {
			return this;
		}
	}
}
