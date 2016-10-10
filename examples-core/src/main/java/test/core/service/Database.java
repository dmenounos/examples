package test.core.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import test.core.model.Case;
import test.core.model.Customer;

@ApplicationScoped
public class Database {

	private Map<Long, Case> cases;
	private Map<Long, Customer> customers;

	public Database() {
		initCases();
		initCustomers();

		Case caseEntity = cases.get(1L);
		caseEntity.addCustomer(customers.get(1L));
		caseEntity.addCustomer(customers.get(2L));
		caseEntity.addCustomer(customers.get(3L));

		caseEntity = cases.get(2L);
		caseEntity.addCustomer(customers.get(4L));

		caseEntity = cases.get(3L);
		caseEntity.addCustomer(customers.get(5L));
	}

	private void initCases() {
		cases = new LinkedHashMap<Long, Case>();

		Date now = new Date();

		Case caseEntity = new Case();
		caseEntity.setId(nextCaseId());
		caseEntity.setCreateDate(now);
		caseEntity.setUpdateDate(now);
		cases.put(caseEntity.getId(), caseEntity);

		caseEntity = new Case();
		caseEntity.setId(nextCaseId());
		caseEntity.setCreateDate(now);
		caseEntity.setUpdateDate(now);
		cases.put(caseEntity.getId(), caseEntity);

		caseEntity = new Case();
		caseEntity.setId(nextCaseId());
		caseEntity.setCreateDate(now);
		caseEntity.setUpdateDate(now);
		cases.put(caseEntity.getId(), caseEntity);
	}

	private void initCustomers() {
		customers = new LinkedHashMap<Long, Customer>();

		Customer customerEntity = new Customer();
		customerEntity.setId(nextCustomerId());
		customerEntity.setFirstName("FIRST");
		customerEntity.setLastName("CUSTOMER");
		customerEntity.setTaxNumber("00000001");
		customers.put(customerEntity.getId(), customerEntity);

		customerEntity = new Customer();
		customerEntity.setId(nextCustomerId());
		customerEntity.setFirstName("SECOND");
		customerEntity.setLastName("CUSTOMER");
		customerEntity.setTaxNumber("00000002");
		customers.put(customerEntity.getId(), customerEntity);

		customerEntity = new Customer();
		customerEntity.setId(nextCustomerId());
		customerEntity.setFirstName("THIRD");
		customerEntity.setLastName("CUSTOMER");
		customerEntity.setTaxNumber("00000003");
		customers.put(customerEntity.getId(), customerEntity);

		customerEntity = new Customer();
		customerEntity.setId(nextCustomerId());
		customerEntity.setFirstName("FOURTH");
		customerEntity.setLastName("CUSTOMER");
		customerEntity.setTaxNumber("00000004");
		customers.put(customerEntity.getId(), customerEntity);

		customerEntity = new Customer();
		customerEntity.setId(nextCustomerId());
		customerEntity.setFirstName("FIFTH");
		customerEntity.setLastName("CUSTOMER");
		customerEntity.setTaxNumber("00000005");
		customers.put(customerEntity.getId(), customerEntity);
	}

	private Long nextCaseId() {
		return Long.valueOf(cases.size() + 1);
	}

	private Long nextCustomerId() {
		return Long.valueOf(customers.size() + 1);
	}

	public Map<Long, Case> getCases() {
		return cases;
	}

	public Map<Long, Customer> getCustomers() {
		return customers;
	}
}
