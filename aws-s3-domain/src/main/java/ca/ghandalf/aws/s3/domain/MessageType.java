package ca.ghandalf.aws.s3.domain;

public enum MessageType {
	Stream,
	Map,
	Text,
	Object,
	Bytes,
	Xml;
	
	public enum JobType {
		Email,
		Fax,
		File,
		Label,
		Print,
		Report;
	}
}
