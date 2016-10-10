package test.view.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.core.model.Case;
import test.core.model.Customer;
import test.core.service.CaseService;
import test.view.util.ViewModel;

@SessionScoped
@Named("caseLookup")
public class CaseLookupController implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Logger LOGGER = LoggerFactory.getLogger(CaseLookupController.class);

	@Inject
	private CaseLookupParameters parameters;

	@Inject
	private CustomerLookupComponent customerLookupComponent;

	@Inject
	private CaseService caseRepository;

	private List<CaseInfo> cases;
	private Integer selectedCaseKey;
	private Customer selectedCustomer;

	public void init() throws Exception {
		LOGGER.debug("init: ");

		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		customerLookupComponent.setParameters(parameters);
		customerLookupComponent.onSearchCustomerClick();
	}

	/**
	 * Callback for the CustomerLookupComponent customer selection notifications.
	 */
	public String onSelectedCustomerChangeCallback() {
		selectedCustomer = customerLookupComponent.getSelectedCustomer();
		return onSelectedCustomerChange();
	}

	/**
	 * Updates the cases data table.
	 */
	public String onSelectedCustomerChange() {
		LOGGER.debug("onSelectedCustomerChange: {}", selectedCustomer);

		// Reset the selected case.
		setSelectedCaseKey(null);
		onSelectedCaseKeyChange();

		if (selectedCustomer == null) {
			cases = Collections.emptyList();
			return null;
		}

		// Find the related case objects from the database.
		List<Case> dbCases = caseRepository.findByCustomerId(selectedCustomer.getId());

		// Create the view models.
		cases = new ArrayList<CaseInfo>();

		for (Case dbCase : dbCases) {
			addInfo(cases, new CaseInfo(dbCase));
		}

		return null;
	}

	/**
	 * Navigates to the selected case page.
	 */
	public String onSelectedCaseKeyChange() {
		LOGGER.debug("onSelectedCaseKeyChange: {}", selectedCaseKey);

		if (selectedCaseKey == null) {
			return null;
		}

		// Get the selected case object.
		Case c = cases.get(selectedCaseKey).getData();

		return "/tool/caseLookup?faces-redirect=true&id=" + c.getId();
	}

	/**
	 * Creates a Case entity and navigates to the case page.
	 */
	public String onCreateCaseClick() {
		LOGGER.debug("onCreateCaseClick: ");

		if (selectedCustomer == null) {
			return null;
		}

		return "/tool/caseLookup?faces-redirect=true";
	}

	public CaseLookupParameters getParameters() {
		return parameters;
	}

	public void setParameters(CaseLookupParameters parameters) {
		this.parameters = parameters;
	}

	public CustomerLookupComponent getCustomerLookupComponent() {
		return customerLookupComponent;
	}

	public void setCustomerLookupComponent(CustomerLookupComponent customerLookupComponent) {
		this.customerLookupComponent = customerLookupComponent;
	}

	public List<CaseInfo> getCases() {
		return cases;
	}

	public void setCases(List<CaseInfo> cases) {
		this.cases = cases;
	}

	public Integer getSelectedCaseKey() {
		return selectedCaseKey;
	}

	public void setSelectedCaseKey(Integer selectedCaseKey) {
		this.selectedCaseKey = selectedCaseKey;
	}

	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	/* UI Helper */
	public boolean isCaseSelected(CaseInfo info) {
		return info != null && info.getKey() != null 
			&& info.getKey().equals(selectedCaseKey);
	}

	/* UI Helper */
	public Case getSelectedCase() {
		if (selectedCaseKey != null) {
			return cases.get(selectedCaseKey).getData();
		}

		return null;
	}

	/* UI Helper */
	private <T extends ViewModel<?>> void addInfo(List<T> list, T info) {
		String key = String.valueOf(list.size());
		info.setKey(key);
		list.add(info);
	}

	/* View Model */
	public static class CaseInfo extends ViewModel<Case> {

		public CaseInfo(Case data) {
			setData(data);
		}
	}
}
