package ca.ghandalf.aws.s3.domain;

import java.util.List;

public class Fax extends Job {

	private static final long serialVersionUID = -1333820749710662806L;

	private List<String> to;
	private String subject;
	private String coverpage;

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCoverpage() {
		return coverpage;
	}

	public void setCoverpage(String coverpage) {
		this.coverpage = coverpage;
	}

	@Override
	public String toString() {
		return "Fax [to=" + to + ", subject=" + subject + ", coverpage=" + coverpage + ", toString()="
				+ super.toString() + "]";
	}

}
