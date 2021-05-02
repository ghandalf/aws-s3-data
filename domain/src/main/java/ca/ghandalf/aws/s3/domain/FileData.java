package ca.ghandalf.aws.s3.domain;

import java.io.Serializable;

public class FileData implements Serializable {

	private static final long serialVersionUID = -514714186147697614L;

	private String path;
	private String name;
	private String mimetype;
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	
	
}
