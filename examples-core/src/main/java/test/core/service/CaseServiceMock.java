package test.core.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import test.core.model.Case;
import test.core.model.Customer;

@Stateless
public class CaseServiceMock implements CaseService {

	@Inject
	private Database database;

	@Override
	public List<Case> findByCustomerId(Long customerId) {
		List<Case> caseList = new ArrayList<Case>();

		for (Case cas : database.getCases().values()) {
			for (Customer cus : cas.getCustomers()) {
				if (cus.getId().equals(customerId)) {
					caseList.add(cas);
					break;
				}
			}
		}

		return caseList;
	}
}
