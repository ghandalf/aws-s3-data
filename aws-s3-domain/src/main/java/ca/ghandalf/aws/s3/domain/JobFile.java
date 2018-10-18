package ca.ghandalf.aws.s3.domain;

import java.io.Serializable;

public class JobFile implements Serializable {

	private static final long serialVersionUID = 4741439676987959095L;

	private String name;
	private String path;
	private String nimeType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNimeType() {
		return nimeType;
	}

	public void setNimeType(String nimeType) {
		this.nimeType = nimeType;
	}

}
