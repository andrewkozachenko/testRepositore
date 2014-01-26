package com.academysmart.bookagolf.view.editingsupport;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.model.Order;

public class DetailsEditingSupport extends EditingSupport {

	public DetailsEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object arg0) {
		return true;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	protected CellEditor getCellEditor(Object item) {
		Order order = (Order) item;
		final Shell dialog = new Shell(getViewer().getControl().getShell(),
				SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		dialog.setLocation(Display.getCurrent().getCursorLocation());
		dialog.setText(Messages.DetailsEditingSupport_title);
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

		Label orderLabel = new Label(c2, SWT.PUSH);
		String labelText = order.getInfo();
		orderLabel.setText(labelText);

		sc2.setMinSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Point dialogSize = sc2.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		dialogSize.x += 25;
		dialogSize.y += 25;
		dialog.setSize(dialogSize);
		dialog.open();
		return null;
	}

	@Override
	protected Object getValue(Object arg0) {
		return null;
	}

	@Override
	protected void setValue(Object arg0, Object arg1) {
		// empty

	}

}
