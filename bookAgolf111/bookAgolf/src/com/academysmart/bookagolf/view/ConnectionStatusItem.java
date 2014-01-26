package com.academysmart.bookagolf.view;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.academysmart.bookagolf.i18n.Messages;

public class ConnectionStatusItem extends ContributionItem {

	private Label labelInternet;
	private Label labelApi;
	private Label labelAuthorization;

	private Color internetColor;
	private Color apiColor;
	private Color authorizationColor;
	
	private String intertnetStatus;

	public Label getLabelInternet() {
		return labelInternet;
	}

	public void setLabelInternet(Label labelInternet) {
		this.labelInternet = labelInternet;
	}

	public Label getLabelApi() {
		return labelApi;
	}

	public void setLabelApi(Label labelApi) {
		this.labelApi = labelApi;
	}

	public Label getLabelAuthorization() {
		return labelAuthorization;
	}

	public void setLabelAuthorization(Label labelAuthorization) {
		this.labelAuthorization = labelAuthorization;
	}

	public ConnectionStatusItem(String id) {
		super(id);
		internetColor = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		apiColor = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		authorizationColor = Display.getDefault()
				.getSystemColor(SWT.COLOR_GRAY);
		intertnetStatus = Messages.ConnectionStatusItem_Internet;
	}

	/**
	 * @return the intertnetStatus
	 */
	public String getIntertnetStatus() {
		return intertnetStatus;
	}

	/**
	 * @param intertnetStatus the intertnetStatus to set
	 */
	public void setIntertnetStatus(String intertnetStatus) {
		this.intertnetStatus = intertnetStatus;
	}

	public void fill(Composite parent) {

		labelInternet = new Label(parent, SWT.NONE);
		labelInternet.setText(intertnetStatus);
		labelInternet.setForeground(internetColor);

		// Label separator = new Label(parent, SWT.SEPARATOR | SWT.VERTICAL);
		// separator.setSize(20, 1);
		labelApi = new Label(parent, SWT.NONE);
		labelApi.setText(Messages.ConnectionStatusItem_API);
		labelApi.setForeground(apiColor);
		// Label separator = new Label(parent, SWT.SEPARATOR | SWT.VERTICAL);
		// separator.setSize(1, 20);
		labelAuthorization = new Label(parent, SWT.NONE);
		labelAuthorization.setText(Messages.ConnectionStatusItem_Authorization);
		labelAuthorization.setForeground(authorizationColor);

	}

	public void setInternetColorGreen() {
		internetColor = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
	}

	public void setApiColorGreen() {
		apiColor = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
	}

	public void setAuthorizationColorGreen() {
		authorizationColor = Display.getCurrent().getSystemColor(
				SWT.COLOR_GREEN);
	}

	public void setInternetColorRed() {
		internetColor = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	}

	public void setApiColorGray() {
		apiColor = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
	}

	public void setAuthorizationColorGray() {
		authorizationColor = Display.getCurrent()
				.getSystemColor(SWT.COLOR_GRAY);
	}

}
