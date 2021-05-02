package ca.ghandalf.aws.s3.domain;

public class Print extends Job {

	private static final long serialVersionUID = -5587888583815073079L;

	private String printer;
	private FormType formType;
	private int copies;
	private int drawer;
	private boolean duplex;
	private boolean hold;
	private boolean save;

	public String getPrinter() {
		return printer;
	}

	public void setPrinter(String printer) {
		this.printer = printer;
	}

	public FormType getFormType() {
		return formType;
	}

	public void setFormType(FormType formType) {
		this.formType = formType;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

	public int getDrawer() {
		return drawer;
	}

	public void setDrawer(int drawer) {
		this.drawer = drawer;
	}

	public boolean isDuplex() {
		return duplex;
	}

	public void setDuplex(boolean duplex) {
		this.duplex = duplex;
	}

	public boolean isHold() {
		return hold;
	}

	public void setHold(boolean hold) {
		this.hold = hold;
	}

	public boolean isSave() {
		return save;
	}

	public void setSave(boolean save) {
		this.save = save;
	}

}
