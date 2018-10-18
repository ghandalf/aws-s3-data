package ca.ghandalf.aws.s3.domain;

import java.util.HashMap;
import java.util.Map;

public class Label extends Print {

	private static final long serialVersionUID = 3586355567471801183L;

	private Map<String, String> mergeData = new HashMap<>();
	private String labelTemplate;

	public Map<String, String> getMergeData() {
		return mergeData;
	}

	public void setMergeData(Map<String, String> mergeData) {
		this.mergeData = mergeData;
	}

	public String getLabelTemplate() {
		return labelTemplate;
	}

	public void setLabelTemplate(String labelTemplate) {
		this.labelTemplate = labelTemplate;
	}

	@Override
	public String toString() {
		return "Label [mergeData=" + mergeData + ", labelTemplate=" + labelTemplate + "]";
	}

}
