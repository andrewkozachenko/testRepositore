package com.academysmart.bookagolf.view.listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class FloatingPointVerifyListener implements VerifyListener {

	private String REGEX = "^[0-9]*\\.?[0-9]+$";
	private Pattern pattern = Pattern.compile(REGEX);

	@Override
	public void verifyText(VerifyEvent e) {
		e.doit = false;
		if (e.character == '\b') {
			e.doit = true;
			return;
		}
		Text textWidget = (Text) e.widget;
		Matcher matcher = pattern.matcher(textWidget.getText() + e.text + "0");
		// handle backspace

		if (matcher.matches()) {
			e.doit = true;
		}
	}

}
