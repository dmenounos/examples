package test.svhc;

import java.net.URL;

import test.svhc.tree.TreeIterator;
import test.svhc.tree.TreeProcessor;

public class Application {

	public void execute() {
		String xmlFile = "/svhc_repo.xml";
		URL xmlFileURL = getClass().getResource(xmlFile);

		if (xmlFile == null || xmlFileURL == null) {
			throw new RuntimeException("Cound not find xml file: " + xmlFile);
		}

		TreeProcessor treeProcessor = new TreeProcessor();
		treeProcessor.getCaseNames().add("R1");
		treeProcessor.getCaseNames().add("R2");
		treeProcessor.processXmlTree(xmlFile);
	}

	public static void main(String[] args) {
		Application app = new Application();
		app.execute();
	}
}
