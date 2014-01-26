package com.academysmart.bookagolf.view.editingsupport;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.EquipmentSetItem;

public class AmountEditingSupport extends EditingSupport {

	public AmountEditingSupport(ColumnViewer viewer) {
		super(viewer);
		this.viewer = (TableViewer) viewer;

	}

	private final TableViewer viewer;

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return new TextCellEditor(viewer.getTable());
	}

	@Override
	protected Object getValue(Object element) {
		return String.valueOf(((EquipmentSetItem) element).getAmount());
	}

	@Override
	protected void setValue(Object element, Object value) {
		Integer intValue = null;
		try {
			intValue = Integer.parseInt((String) value);
		} catch (NumberFormatException e) {
			Loggers.FILE_LOGGER.debug(
					"Amount of equipment must be integer value", e); //$NON-NLS-1$
		}
		if (intValue != null) {
			((EquipmentSetItem) element).setAmount(intValue);
			viewer.update(element, null);
		}
	}
}
