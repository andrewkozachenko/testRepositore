package com.academysmart.bookagolf.view.listener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class IntegerVerifyListener implements VerifyListener {

	private String REGEX = "^[0-9]*";
	private Pattern pattern = Pattern.compile(REGEX);

	@Override
	public void verifyText(VerifyEvent e) {
		e.doit = false;
		if (e.character == '\b') {
			e.doit = true;
			return;
		}
		Matcher matcher = pattern.matcher(e.text);

		if (matcher.matches()) {
			e.doit = true;
		}

	}

}
