package com.academysmart.bookagolf.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.snippets.viewers.BooleanCellEditor;
import org.eclipse.jface.util.Util;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.OwnerDrawLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.model.EquipmentSetItem;
import com.academysmart.bookagolf.model.Order;
import com.academysmart.bookagolf.model.PricesListComparatorHire;
import com.academysmart.bookagolf.provider.ClubProvider;
import com.academysmart.bookagolf.provider.EquipmentProvider;
import com.academysmart.bookagolf.provider.OrderProvider;

public class PricesViewListHire {
	private TableViewer viewer;
	private PricesListComparatorHire comparator;
	Image golfcar;
	Image time_one;
	Image time_two;
	Image set_of_golf_clubs;
	Image check;
	Image no;

	public abstract class EmulatedNativeCheckBoxLabelProvider extends
			OwnerDrawLabelProvider {
		private static final String TIME_ONE_KEY = "TIME_ONE"; //$NON-NLS-1$
		private static final String TIME_TWO_KEY = "TIME_TWO"; //$NON-NLS-1$

		public EmulatedNativeCheckBoxLabelProvider(ColumnViewer viewer) {
			if (JFaceResources.getImageRegistry().getDescriptor(TIME_ONE_KEY) == null) {
				JFaceResources.getImageRegistry().put(TIME_TWO_KEY,
						makeShot(viewer.getControl(), false));
				JFaceResources.getImageRegistry().put(TIME_ONE_KEY,
						makeShot(viewer.getControl(), true));
			}
		}

		private Image makeShot(Control control, boolean type) {
			// Hopefully no platform uses exactly this color because we'll make
			// it transparent in the image.
			Color greenScreen = new Color(control.getDisplay(), 222, 223, 224);

			Shell shell = new Shell(control.getShell(), SWT.NO_TRIM);

			// otherwise we have a default gray color
			shell.setBackground(greenScreen);

			if (Util.isMac()) {
				Button button2 = new Button(shell, SWT.CHECK);
				Point bsize = button2.computeSize(SWT.DEFAULT, SWT.DEFAULT);

				// otherwise an image is stretched by width
				bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
				bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
				button2.setSize(bsize);
				button2.setLocation(100, 100);
			}

			Button button = new Button(shell, SWT.CHECK);
			button.setBackground(greenScreen);
			button.setSelection(type);

			// otherwise an image is located in a corner
			button.setLocation(1, 1);
			Point bsize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);

			// otherwise an image is stretched by width
			bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
			bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
			button.setSize(bsize);

			shell.setSize(bsize);

			shell.open();

			GC gc = new GC(shell);
			Image image = new Image(control.getDisplay(), bsize.x, bsize.y);
			gc.copyArea(image, 0, 0);
			gc.dispose();
			shell.close();

			ImageData imageData = image.getImageData();
			imageData.transparentPixel = imageData.palette.getPixel(greenScreen
					.getRGB());

			Image img = new Image(control.getDisplay(), imageData);
			image.dispose();

			return img;
		}

		public Image getImage(Object element) {
			if (isChecked(element)) {
				return JFaceResources.getImageRegistry().get(TIME_ONE_KEY);
			} else {
				return JFaceResources.getImageRegistry().get(TIME_TWO_KEY);
			}
		}

		protected void measure(Event event, Object element) {
			event.height = getImage(element).getBounds().height;
		}

		protected void paint(Event event, Object element) {

			Image img = getImage(element);

			if (img != null) {
				Rectangle bounds;

				if (event.item instanceof TableItem) {
					bounds = ((TableItem) event.item).getBounds(event.index);
				} else {
					bounds = ((TreeItem) event.item).getBounds(event.index);
				}

				Rectangle imgBounds = img.getBounds();
				bounds.width /= 2;
				bounds.width -= imgBounds.width / 2;
				bounds.height /= 2;
				bounds.height -= imgBounds.height / 2;

				int x = bounds.width > 0 ? bounds.x + bounds.width : bounds.x;
				int y = bounds.height > 0 ? bounds.y + bounds.height : bounds.y;

				if (SWT.getPlatform().equals("carbon")) { //$NON-NLS-1$
					event.gc.drawImage(img, x + 2, y - 1);
				} else {
					event.gc.drawImage(img, x, y - 1);
				}

			}
		}

		protected abstract boolean isChecked(Object element);
	}

	public void addImages(Display display) {

		check = new Image(display, this.getClass().getResourceAsStream(
				"icons/check.png")); //$NON-NLS-1$
		no = new Image(display, this.getClass().getResourceAsStream(
				"icons/no.png")); //$NON-NLS-1$

	}

	public void createPartControl(Composite parent) {

		comparator = new PricesListComparatorHire();
		createViewer(parent);

		viewer.setComparator(comparator);
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
		List<Order> orders = OrderProvider.INSTANCE.getOrders();
		List<EquipmentSetItem> equipmentItems = new ArrayList<>();
		for (Order order : orders) {
			// Parser adds 0 for empty equipment sets. Checking id != 0 here
			if ((order.getEquipmentSet() != null)
					&& !order.getEquipmentSet().isEmpty()
					&& order.getEquipmentSet().get(0).getId() != null) {
				equipmentItems.addAll(order.getEquipmentSet());
			}
		}
		viewer.setInput(equipmentItems);
		// Set the sorter for the table

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = false;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.heightHint = 150;
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

	// колонка "Время игры"
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { Messages.PricesViewListHire_Time,
				"", "", "", "", "", "", "", //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
				Messages.PricesViewListHire_Equipment,
				Messages.PricesViewListHire_Pieces,
				Messages.PricesViewListHire_Price, "", "" }; //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-1$ //$NON-NLS-2$
		int[] bounds = { 100, 15, 15, 15, 15, 15, 15, 15, 200, 100, 100, 20, 20 };

		final BooleanCellEditor booleanCellEditor = new BooleanCellEditor(
				viewer.getTable());
		booleanCellEditor.setChangeOnActivation(true);

		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.CENTER);
		final TableColumn column = viewerColumn.getColumn();
		column.setText("");
		column.setWidth(0);
		column.setResizable(false);
		column.setMoveable(false);
		viewerColumn.setLabelProvider(new ColumnLabelProvider());

		// колонка "Время"
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// PricesOrderGame o = (PricesOrderGame) element;
				// return o.getTime();
				return ""; //$NON-NLS-1$
			}
		});

		// колонка "Понедельник"
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// PricesOrderGame o = (PricesOrderGame) element;
				// return o.isMonday();
				return ""; //$NON-NLS-1$
			}
		});

		// колонка "Вторник"
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// PricesOrderGame o = (PricesOrderGame) element;
				// return o.isTuesday();
				return ""; //$NON-NLS-1$
			}
		});

		// колонка "Среда"
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// PricesOrderGame o = (PricesOrderGame) element;
				// return o.isWednesday();
				return ""; //$NON-NLS-1$
			}
		});

		// колонка "Четверг"
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// PricesOrderGame o = (PricesOrderGame) element;
				// return o.isThursday();
				return ""; //$NON-NLS-1$
			}
		});

		// колонка "Пятница"
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// PricesOrderGame o = (PricesOrderGame) element;
				// return o.isFriday();
				return ""; //$NON-NLS-1$
			}
		});

		// колонка "Суббота"
		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// PricesOrderGame o = (PricesOrderGame) element;
				// return o.isSaturday();
				return ""; //$NON-NLS-1$
			}
		});

		// колонка "Воскресенье"
		col = createTableViewerColumn(titles[7], bounds[7], 7);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				// PricesOrderGame o = (PricesOrderGame) element;
				// return o.isSunday();
				return ""; //$NON-NLS-1$
			}
		});

		// колонка "Аксессуары"
		col = createTableViewerColumn(titles[8], bounds[8], 8);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EquipmentSetItem item = (EquipmentSetItem) element;
				return EquipmentProvider.INSTANCE.getEquipment(
						item.getId().intValue()).getName();
			}
		});

		// колонка "Количество аксессуаров"
		col = createTableViewerColumn(titles[9], bounds[9], 9);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EquipmentSetItem item = (EquipmentSetItem) element;
				return item.getAmount() + Messages.PricesViewListHire_Pieces;
			}
		});

		// колонка "Сумма"
		col = createTableViewerColumn(titles[10], bounds[10], 10);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				EquipmentSetItem item = (EquipmentSetItem) element;
				return EquipmentProvider.INSTANCE.getEquipment(
						item.getId().intValue()).getPrice()
						+ " " + ClubProvider.getClub().getCurrency(); //$NON-NLS-1$

			}
		});

		// иконка "check"
		col = createTableViewerColumn(titles[11], bounds[11], 11);
		col.setLabelProvider(new EmulatedNativeCheckBoxLabelProvider(viewer) {
			@Override
			public Image getImage(Object element) {
				return check;
			}

			@Override
			protected boolean isChecked(Object element) {
				return false;
			}
		});
		// иконка "no"
		col = createTableViewerColumn(titles[12], bounds[12], 12);
		col.setLabelProvider(new EmulatedNativeCheckBoxLabelProvider(viewer) {
			@Override
			public Image getImage(Object element) {
				return no;
			}

			@Override
			protected boolean isChecked(Object element) {
				return false;
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
				viewer.refresh();
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
