package ca.ghandalf.aws.s3.domain;

import java.util.List;

public class Email extends Job {

	private static final long serialVersionUID = -6270080933271270145L;

	private List<String> to;
	private List<String> cc;
	private List<String> bc;
	private String subject;
	private String message;
	private String mimeType;
	private String overlayFile;

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public List<String> getBc() {
		return bc;
	}

	public void setBc(List<String> bc) {
		this.bc = bc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getOverlayFile() {
		return overlayFile;
	}

	public void setOverlayFile(String overlayFile) {
		this.overlayFile = overlayFile;
	}

	@Override
	public String toString() {
		return "Email [to=" + to + ", cc=" + cc + ", bc=" + bc + ", subject=" + subject + ", message=" + message
				+ ", mimeType=" + mimeType + ", overlayFile=" + overlayFile + ", toString()=" + super.toString() + "]";
	}

}
