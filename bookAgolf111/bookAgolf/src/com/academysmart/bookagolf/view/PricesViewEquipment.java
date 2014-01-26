package com.academysmart.bookagolf.view;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.academysmart.bookagolf.config.ImageRegistryKeys;
import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.model.Equipment;
import com.academysmart.bookagolf.model.PricesListEquipmentComparator;
import com.academysmart.bookagolf.provider.ClubProvider;
import com.academysmart.bookagolf.provider.EquipmentProvider;
import com.academysmart.bookagolf.view.editingsupport.EquipmentUpdateEditingSupport;
import com.academysmart.bookagolf.view.labelprovider.ColorBackgroundLabelProvider;
import com.academysmart.bookagolf.view.labelprovider.ImageColorBackgroundLabelProvider;

public class PricesViewEquipment {
	private TableViewer viewer;
	private PricesListEquipmentComparator comparator;

	Image check;
	Image no;

	public void addImages(Display display) {
		check = JFaceResources.getImage(ImageRegistryKeys.EDIT);
		no = JFaceResources.getImage(ImageRegistryKeys.DELETE);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void createPartControl(Composite parent) {

		comparator = new PricesListEquipmentComparator();
		createViewer(parent);
		viewer.setComparator(comparator);
		ColumnViewerToolTipSupport.enableFor(viewer, ToolTip.NO_RECREATE);
	}

	private void createViewer(Composite parent) {

		viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);

		viewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		viewer.setInput(EquipmentProvider.INSTANCE.getEquipments());
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
		String[] titles = { Messages.PricesViewListHire_Equipment,
				Messages.PricesViewListHire_Price, "", "" }; //$NON-NLS-1$//$NON-NLS-2$
		int[] bounds = { 710, 100, 20, 20 };

		// final BooleanCellEditor booleanCellEditor = new BooleanCellEditor(
		// viewer.getTable());
		// booleanCellEditor.setChangeOnActivation(true);

		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.CENTER);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(""); //$NON-NLS-1$
		column.setWidth(0);
		column.setResizable(false);
		column.setMoveable(false);
		viewerColumn.setLabelProvider(new ColumnLabelProvider());

		// Equipment name
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				Equipment equipment = (Equipment) element;
				return equipment.getName();
			}
		});

		// Price
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {
			@Override
			public String getText(Object element) {
				Equipment equipment = (Equipment) element;
				return equipment.getPrice() + " " //$NON-NLS-1$
						+ ClubProvider.getClub().getCurrency();
			}
		});

		// icon "check"
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ImageColorBackgroundLabelProvider() {

			@Override
			public Image getImage(Object element) {
				return check;
			}

			@Override
			public String getToolTipText(Object element) {
				return Messages.PricesViewListGame_edit;
			}

			@Override
			public Point getToolTipShift(Object object) {
				return new Point(5, 5);
			}

			@Override
			public int getToolTipDisplayDelayTime(Object object) {
				return 100; // msec
			}

			@Override
			public int getToolTipTimeDisplayed(Object object) {
				return 5000; // msec
			}
		});
		col.setEditingSupport(new EquipmentUpdateEditingSupport(viewer));
		// icon "no"
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ImageColorBackgroundLabelProvider() {

			@Override
			public Image getImage(Object element) {
				return no;
			}

			@Override
			public String getToolTipText(Object element) {
				return Messages.PricesViewListGame_delete;
			}

			@Override
			public Point getToolTipShift(Object object) {
				return new Point(5, 5);
			}

			@Override
			public int getToolTipDisplayDelayTime(Object object) {
				return 100; // msec
			}

			@Override
			public int getToolTipTimeDisplayed(Object object) {
				return 5000; // msec
			}

		});
		col.setEditingSupport(new EditingSupport(viewer) {

			@Override
			protected void setValue(Object arg0, Object arg1) {
				// empty

			}

			@Override
			protected Object getValue(Object arg0) {
				// empty
				return null;
			}

			@Override
			protected CellEditor getCellEditor(Object item) {
				Equipment equipment = (Equipment) item;
				//				System.out.println("Deleting game " + game.getId()); //$NON-NLS-1$
				//				System.out.println("price: " + game.getPrice()); //$NON-NLS-1$
				EquipmentProvider.INSTANCE.deleteEquipment(equipment.getId());
				// viewer.setInput(EquipmentProvider.INSTANCE.getEquipments());
				// viewer.refresh();
				MainWindow.getInstance().updatePricesTab();
				MainWindow.getInstance().updateConnectionStatus();
				return null;
			}

			@Override
			protected boolean canEdit(Object arg0) {
				return true;
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
		column.addSelectionListener(getSelectionAdapter(column, colNumber));
		return viewerColumn;
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column,
			final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				int dir = comparator.getDirection();
				viewer.getTable().setSortDirection(dir);
				viewer.getTable().setSortColumn(column);
				viewer.refresh(true);
			}
		};
		return selectionAdapter;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */

	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
