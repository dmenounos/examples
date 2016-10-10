package test.core.model;

public class Customer extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;
	private String taxNumber;

	private Case testCase;

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

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public Case getTestCase() {
		return testCase;
	}

	public void setTestCase(Case testCase) {
		this.testCase = testCase;
	}
}
