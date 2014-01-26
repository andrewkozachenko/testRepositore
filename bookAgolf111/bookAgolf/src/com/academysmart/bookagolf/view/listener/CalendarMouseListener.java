package com.academysmart.bookagolf.view.listener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.model.Order;

public class CalendarMouseListener implements MouseListener {

	private List<Order> ordersAtDateTime;
	private Composite parent;

	public CalendarMouseListener(Composite parent, List<Order> ordersAtDateTime) {
		this.parent = parent;
		this.ordersAtDateTime = ordersAtDateTime;
	}

	public CalendarMouseListener(Composite parent, Order order) {
		this.parent = parent;
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);
		this.ordersAtDateTime = orders;
	}

	@Override
	public void mouseDoubleClick(MouseEvent arg0) {
		// empty

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void mouseDown(MouseEvent arg0) {
		if ((ordersAtDateTime != null) && !ordersAtDateTime.isEmpty()) {
			final Shell dialog = new Shell(parent.getShell(),
					SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
			// dialog.setSize(400, 400);
			dialog.setLocation(Display.getCurrent().getCursorLocation());
			dialog.setText(Messages.CalendarMouseListener_title);
			dialog.setLayout(new FillLayout(SWT.HORIZONTAL));

			final ScrolledComposite sc2 = new ScrolledComposite(dialog,
					SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			sc2.setExpandHorizontal(true);
			sc2.setExpandVertical(true);
			final Composite c2 = new Composite(sc2, SWT.NONE);
			sc2.setContent(c2);
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			c2.setLayout(layout);
			Point dialogSize = new Point(200, 200);
			boolean isFirstOrder = true;
			for (Order order : ordersAtDateTime) {
				Label orderLabel = new Label(c2, SWT.PUSH);
				String labelText = order.getInfo();
				orderLabel.setText(labelText);
				if (isFirstOrder) {
					dialogSize = c2.computeSize(SWT.DEFAULT, SWT.DEFAULT);
					dialogSize.x += 35;
					dialogSize.y += 50;
					isFirstOrder = false;
				}

			}
			sc2.setMinSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));

			dialog.setSize(dialogSize);
			dialog.open();
		}
	}

	@Override
	public void mouseUp(MouseEvent arg0) {
		// empty

	}

}
