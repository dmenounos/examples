package test.core.service;

import java.util.List;

import test.core.model.Customer;

public interface CustomerService {

	Customer findById(Long id);

	List<Customer> findByCriteria(CustomerCriteria criteria);

	CustomerCriteria createCriteria();

	interface CustomerCriteria {

		CustomerCriteria id(String id);

		CustomerCriteria firstName(String firstName);

		CustomerCriteria surname(String surname);

		CustomerCriteria taxNumber(String taxNumber);
	}
}
