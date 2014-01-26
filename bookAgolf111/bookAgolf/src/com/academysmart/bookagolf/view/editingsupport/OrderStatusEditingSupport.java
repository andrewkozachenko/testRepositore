package com.academysmart.bookagolf.view.editingsupport;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.model.Order;
import com.academysmart.bookagolf.provider.OrderProvider;
import com.academysmart.bookagolf.view.MainWindow;

public class OrderStatusEditingSupport extends EditingSupport {
	private final TableViewer viewer;
	// private static final String[] STATUS_STRINGS = Order.STATUS_MAP.values()
	// .toArray(new String[0]);
	private static final String[] STATUS_STRINGS = new String[3];
	static {
		STATUS_STRINGS[0] = Order.STATUS_MAP.get(Order.STATUS_CONFIRMED);
		STATUS_STRINGS[1] = Order.STATUS_MAP.get(Order.STATUS_APPROVED);
		STATUS_STRINGS[2] = Order.STATUS_MAP.get(Order.STATUS_DECLINED_BY_CLUB);
	}

	public OrderStatusEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected void setValue(Object element, Object value) {
		Order order = (Order) element;
		int orderStatus = 0;
		String statusString = STATUS_STRINGS[(int) value];
		for (int status : Order.STATUS_MAP.keySet()) {
			if (statusString.equals(Order.STATUS_MAP.get(status))) {
				orderStatus = status;
				break;
			}
		}
		order.setStatus(orderStatus);

		OrderProvider.INSTANCE.updateOrderStatus(order);
		viewer.update(element, null);
		MainWindow.getInstance().setStatus(
				Messages.MainWindow_this_status
						+ OrderProvider.INSTANCE.getNumberOfNewOrders());
		MainWindow.getInstance().updateConnectionStatus();
	}

	@Override
	protected Object getValue(Object element) {
		Order order = (Order) element;
		String statusString = Order.STATUS_MAP.get(order.getStatus());
		for (int i = 0; i < STATUS_STRINGS.length; i++) {
			if (statusString.equals(STATUS_STRINGS[i])) {
				return i;
			}
		}
		return 0;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		ComboBoxCellEditor cellEditor = new ComboBoxCellEditor(
				viewer.getTable(), STATUS_STRINGS, SWT.READ_ONLY);
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}
}
