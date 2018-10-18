package ca.ghandalf.aws.s3.domain;

import java.io.Serializable;

public class JobUser implements Serializable {

	private static final long serialVersionUID = 4373413952171481171L;
	
	private String name;
	private String company;
	private String district;
	private UserType userType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}