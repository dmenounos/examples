package test.core.model;

import java.util.ArrayList;
import java.util.List;

public class Case extends AuditableEntity {

	private static final long serialVersionUID = 1L;

	private List<Customer> customers;

	public List<Customer> getCustomers() {
		if (customers == null) {
			customers = new ArrayList<Customer>();
		}

		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		getCustomers().clear();
		getCustomers().addAll(customers);
	}

	public void addCustomer(Customer customer) {
		getCustomers().add(customer);
		customer.setTestCase(this);
	}
}
