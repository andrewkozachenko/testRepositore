package com.academysmart.bookagolf.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.model.Equipment;
import com.academysmart.bookagolf.model.EquipmentSetItem;
import com.academysmart.bookagolf.provider.ClubProvider;
import com.academysmart.bookagolf.provider.EquipmentProvider;
import com.academysmart.bookagolf.view.editingsupport.AmountEditingSupport;
import com.academysmart.bookagolf.view.labelprovider.ColorBackgroundLabelProvider;

public class AddOrderViewListEquipment {
	public TableViewer viewer;

	public void createPartControl(Composite parent) {
		// comparator = new PricesListComparatorGame();
		createViewer(parent);
		// viewer.setComparator(comparator);
		ColumnViewerToolTipSupport.enableFor(viewer, ToolTip.NO_RECREATE);
	}

	private void createViewer(Composite parent) {

		viewer = new TableViewer(parent, SWT.CHECK | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);

		viewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		List<Equipment> availableEquipments = EquipmentProvider.INSTANCE
				.getEquipments();
		List<EquipmentSetItem> equipmentSetItems = new ArrayList<EquipmentSetItem>();
		for (Equipment equipment : availableEquipments) {
			EquipmentSetItem item = new EquipmentSetItem();
			item.setId(equipment.getId());
			item.setAmount(1);
			equipmentSetItems.add(item);
		}
		viewer.setInput(equipmentSetItems);
		// Set the sorter for the table

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		// gridData.verticalAlignment = GridData.CENTER;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		// gridData.horizontalAlignment = GridData.CENTER;
		gridData.heightHint = 200;
		viewer.getControl().setLayoutData(gridData);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				// IStructuredSelection selection = (IStructuredSelection)
				// viewer
				// .getSelection();
				// Object firstElement = selection.getFirstElement();
				// Do something with it
			}
		});

	}

	public TableViewer getViewer() {
		return viewer;
	}

	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { Messages.AddOrderViewListEquipment_Equipment, Messages.AddOrderViewListEquipment_Price, Messages.AddOrderViewListEquipment_Amount, Messages.AddOrderViewListEquipment_Subtotal };
		int[] bounds = { 550, 80, 80, 80 };

		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.CENTER);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(""); //$NON-NLS-1$
		column.setWidth(20);
		column.setResizable(true);
		column.setMoveable(true);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				return ""; //$NON-NLS-1$
			}

		});

		// колонка "Оборудование"
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				EquipmentSetItem equipment = (EquipmentSetItem) element;
				String eqName = String.valueOf(EquipmentProvider.INSTANCE
						.getEquipment(equipment.getId().intValue()).getName());

				return eqName;
			}
		});

		// колонка "Цена"
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				EquipmentSetItem equipment = (EquipmentSetItem) element;
				String priceString = String.valueOf(EquipmentProvider.INSTANCE
						.getEquipment(equipment.getId().intValue()).getPrice());//$NON-NLS-1$

				return priceString + " " //$NON-NLS-1$
						+ ClubProvider.getClub().getCurrency();
			}
		});
		// col.getColumn().setAlignment(SWT.RIGHT);

		// колонка "Кол-во"
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				int t = ((EquipmentSetItem) element).getAmount();
				return String.valueOf(t);
			}
		});
		col.setEditingSupport(new AmountEditingSupport(viewer));

		// колонка "Итого"
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				EquipmentSetItem equipment = (EquipmentSetItem) element;
				double t = equipment.getAmount()
						* EquipmentProvider.INSTANCE.getEquipment(
								equipment.getId().intValue()).getPrice();
				return String.valueOf(t) + " " //$NON-NLS-1$
						+ ClubProvider.getClub().getCurrency();
			}
		});
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.CENTER);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		// column.addSelectionListener(getSelectionAdapter(column, colNumber));
		return viewerColumn;
	}

	// private SelectionAdapter getSelectionAdapter(final TableColumn column,
	// final int index) {
	// SelectionAdapter selectionAdapter = new SelectionAdapter() {
	// @Override
	// public void widgetSelected(SelectionEvent e) {
	// comparator.setColumn(index);
	// int dir = comparator.getDirection();
	// viewer.getTable().setSortDirection(dir);
	// viewer.getTable().setSortColumn(column);
	// viewer.refresh(true);
	// }
	// };
	// return selectionAdapter;
	// }

	/**
	 * Passing the focus request to the viewer's control.
	 */

	public void setFocus() {
		viewer.getControl().setFocus();
	}

}
