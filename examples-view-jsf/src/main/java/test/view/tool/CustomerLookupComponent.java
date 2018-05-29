package test.view.tool;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import test.core.model.Customer;
import test.core.service.CustomerService;
import test.view.util.ViewModel;

public class CustomerLookupComponent implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Logger LOGGER = LoggerFactory.getLogger(CustomerLookupComponent.class);

	private UIComponent uiComponent;

	private CustomerLookupParameters parameters;

	@Inject
	private CustomerService customerService;

	private List<CustomerInfo> customers;
	private Integer selectedCustomerKey;

	public CustomerLookupComponent() {
		LOGGER.debug("CustomerLookupComponent: ");
		parameters = new CustomerLookupParameters();
	}

	/**
	 * Updates the customers data table.
	 */
	public String onSearchCustomerClick() {
		LOGGER.debug("onSearchCustomerClick: ");

		// Reset the selected customer.
		setSelectedCustomerKey(null);
		onSelectedCustomerKeyChange();

		if (parameters.isEmpty()) {
			customers = Collections.emptyList();
			return null;
		}

		customers = new ArrayList<CustomerInfo>();

		List<Customer> dbCustomers = customerService
			.findByCriteria(customerService.createCriteria()
				.id(parameters.getId())
				.firstName(parameters.getFirstName())
				.surname(parameters.getLastName())
				.taxNumber(parameters.getTaxNumber()));

		for (Customer dbCustomer : dbCustomers) {
			addInfo(customers, new CustomerInfo(dbCustomer));
		}

		return null;
	}

	/**
	 * Notifies the observer for the customer selection.
	 */
	public String onSelectedCustomerKeyChange() {
		LOGGER.debug("onSelectedCustomerKeyChange: {}", selectedCustomerKey);
		MethodExpression me = (MethodExpression) getUiComponent().getAttributes().get("onCustomerSelect");

		if (me != null) {
			ELContext ec = FacesContext.getCurrentInstance().getELContext();
			return (String) me.invoke(ec, new Object[0]);
		}

		return null;
	}

	public UIComponent getUiComponent() {
		return uiComponent;
	}

	public void setUiComponent(UIComponent uiComponent) {
		this.uiComponent = uiComponent;
	}

	public CustomerLookupParameters getParameters() {
		return parameters;
	}

	public void setParameters(CustomerLookupParameters parameters) {
		this.parameters = parameters;
	}

	public List<CustomerInfo> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerInfo> customers) {
		this.customers = customers;
	}

	public Integer getSelectedCustomerKey() {
		return selectedCustomerKey;
	}

	public void setSelectedCustomerKey(Integer selectedCustomerKey) {
		this.selectedCustomerKey = selectedCustomerKey;
	}

	/* UI Helper */
	public boolean isCustomerSelected(CustomerInfo info) {
		return info != null && info.getKey() != null 
			&& info.getKey().equals(selectedCustomerKey);
	}

	/* UI Helper */
	public Customer getSelectedCustomer() {
		if (selectedCustomerKey != null) {
			return customers.get(selectedCustomerKey).getData();
		}

		return null;
	}

	/* UI Helper */
	private <T extends ViewModel<?>> void addInfo(List<T> list, T info) {
		Integer key = Integer.valueOf(list.size());
		info.setKey(key);
		list.add(info);
	}

	/* View Model */
	public static class CustomerInfo extends ViewModel<Customer> {

		public CustomerInfo(Customer data) {
			setData(data);
		}
	}

	public static class CustomerLookupParameters implements Serializable {

		private static final long serialVersionUID = 1L;

		/**
		 * Identifier
		 */
		private String id;

		/**
		 * Α.Φ.Μ.
		 */
		private String taxNumber;

		/**
		 * Όνομα
		 */
		private String firstName;

		/**
		 * Επώνυμο
		 */
		private String lastName;

		public boolean isEmpty() {
			return StringUtils.isEmpty(id)
				&& StringUtils.isEmpty(taxNumber)
				&& StringUtils.isEmpty(firstName)
				&& StringUtils.isEmpty(lastName);
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTaxNumber() {
			return taxNumber;
		}

		public void setTaxNumber(String taxNumber) {
			this.taxNumber = taxNumber;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
	}
}
