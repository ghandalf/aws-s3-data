package ca.ghandalf.aws.s3.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Job implements Serializable {

	private static final long serialVersionUID = 3593484703281003456L;

	private UUID id = UUID.randomUUID();
	private User user;
	private Date createdDate;
	private List<FileData> files;
	private String userData;
	
	public UUID getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<FileData> getFiles() {
		return files;
	}

	public void setFiles(List<FileData> files) {
		this.files = files;
	}

	public String getUserData() {
		return userData;
	}

	public void setUserData(String userData) {
		this.userData = userData;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + 
				", user=" + user + 
				", createdDate=" + createdDate + ", files=" + files + ", userDate="
				+ userData + "]";
	}

}
