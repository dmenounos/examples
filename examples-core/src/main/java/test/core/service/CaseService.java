package test.core.service;

import java.util.List;

import test.core.model.Case;

public interface CaseService {

	List<Case> findByCustomerId(Long customerId);
}
