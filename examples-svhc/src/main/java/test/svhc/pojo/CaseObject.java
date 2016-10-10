package test.svhc.pojo;

import java.util.ArrayList;
import java.util.List;

public class CaseObject {

	private final String name;
	private final List<String> documents;

	private boolean binder;
	private boolean confidential;

	public CaseObject(String name) {
		this.name      = name;
		this.documents = new ArrayList<>();
	}

	public void addDocument(String document) {
		getDocuments().add(document);
	}

	public void finalizeDocuments() {
	}

	public boolean isBinder() {
		return binder;
	}

	public void setBinder(boolean binder) {
		this.binder = binder;
	}

	public boolean isConfidential() {
		return confidential;
	}

	public void setConfidential(boolean confidential) {
		this.confidential = confidential;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public String toDebugString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName() + " " + name + "\n");

		for (String document : getDocuments()) {
			sb.append(document + "\n");
		}

		return sb.toString();
	}
}
