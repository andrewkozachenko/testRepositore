package com.academysmart.bookagolf.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.snippets.viewers.BooleanCellEditor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.nebula.widgets.pagination.collections.PageResultContentProvider;
import org.eclipse.nebula.widgets.pagination.collections.PageResultLoaderList;
import org.eclipse.nebula.widgets.pagination.table.PageableTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.joda.time.DateTime;
import org.joda.time.Days;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.config.ImageRegistryKeys;
import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.ListComparator;
import com.academysmart.bookagolf.model.Order;
import com.academysmart.bookagolf.model.OrderFilter;
import com.academysmart.bookagolf.provider.OrderProvider;
import com.academysmart.bookagolf.provider.UserProvider;
import com.academysmart.bookagolf.view.editingsupport.DetailsEditingSupport;
import com.academysmart.bookagolf.view.editingsupport.OrderStatusEditingSupport;
import com.academysmart.bookagolf.view.labelprovider.ImageLabelProvider;

public class OrderViewList {

	private TableViewer viewer;
	private ListComparator comparator;
	Image detailsImage;
	Image calendarImage;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
	SimpleDateFormat createdAtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm"); //$NON-NLS-1$
	private OrderFilter filter;
	private Date filterStartDate;
	private Date filterEndDate;
	PageableTable paginationTable;
	int pageSize = 50;

	// public abstract class EmulatedNativeCheckBoxLabelProvider extends
	// OwnerDrawLabelProvider {
	//		private static final String CHECKED_KEY = "CHECKED"; //$NON-NLS-1$
	//		private static final String UNCHECK_KEY = "UNCHECKED"; //$NON-NLS-1$
	//
	// public EmulatedNativeCheckBoxLabelProvider(ColumnViewer viewer) {
	// if (JFaceResources.getImageRegistry().getDescriptor(CHECKED_KEY) == null)
	// {
	// JFaceResources.getImageRegistry().put(UNCHECK_KEY,
	// makeShot(viewer.getControl(), false));
	// JFaceResources.getImageRegistry().put(CHECKED_KEY,
	// makeShot(viewer.getControl(), true));
	// }
	// }
	//
	// private Image makeShot(Control control, boolean type) {
	// // Hopefully no platform uses exactly this color because we'll make
	// // it transparent in the image.
	// Color greenScreen = new Color(control.getDisplay(), 222, 223, 224);
	//
	// Shell shell = new Shell(control.getShell(), SWT.NO_TRIM);
	//
	// // otherwise we have a default gray color
	// shell.setBackground(greenScreen);
	//
	// if (Util.isMac()) {
	// Button button2 = new Button(shell, SWT.CHECK);
	// Point bsize = button2.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	//
	// // otherwise an image is stretched by width
	// bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
	// bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
	// button2.setSize(bsize);
	// button2.setLocation(100, 100);
	// }
	//
	// Button button = new Button(shell, SWT.CHECK);
	// button.setBackground(greenScreen);
	// button.setSelection(type);
	//
	// // otherwise an image is located in a corner
	// button.setLocation(1, 1);
	// Point bsize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	//
	// // otherwise an image is stretched by width
	// bsize.x = Math.max(bsize.x - 1, bsize.y - 1);
	// bsize.y = Math.max(bsize.x - 1, bsize.y - 1);
	// button.setSize(bsize);
	//
	// shell.setSize(bsize);
	//
	// shell.open();
	//
	// GC gc = new GC(shell);
	// Image image = new Image(control.getDisplay(), bsize.x, bsize.y);
	// gc.copyArea(image, 0, 0);
	// gc.dispose();
	// shell.close();
	//
	// ImageData imageData = image.getImageData();
	// imageData.transparentPixel = imageData.palette.getPixel(greenScreen
	// .getRGB());
	//
	// Image img = new Image(control.getDisplay(), imageData);
	// image.dispose();
	//
	// return img;
	// }
	//
	// public Image getImage(Object element) {
	// if (isChecked(element)) {
	// return JFaceResources.getImageRegistry().get(CHECKED_KEY);
	// } else {
	// return JFaceResources.getImageRegistry().get(UNCHECK_KEY);
	// }
	// }
	//
	// protected void measure(Event event, Object element) {
	// event.height = getImage(element).getBounds().height;
	// }
	//
	// protected void paint(Event event, Object element) {
	//
	// Image img = getImage(element);
	//
	// if (img != null) {
	// Rectangle bounds;
	//
	// if (event.item instanceof TableItem) {
	// bounds = ((TableItem) event.item).getBounds(event.index);
	// } else {
	// bounds = ((TreeItem) event.item).getBounds(event.index);
	// }
	//
	// Rectangle imgBounds = img.getBounds();
	// bounds.width /= 2;
	// bounds.width -= imgBounds.width / 2;
	// bounds.height /= 2;
	// bounds.height -= imgBounds.height / 2;
	//
	// int x = bounds.width > 0 ? bounds.x + bounds.width : bounds.x;
	// int y = bounds.height > 0 ? bounds.y + bounds.height : bounds.y;
	//
	//				if (SWT.getPlatform().equals("carbon")) { //$NON-NLS-1$
	// event.gc.drawImage(img, x + 2, y - 1);
	// } else {
	// event.gc.drawImage(img, x, y - 1);
	// }
	//
	// }
	// }
	//
	// protected abstract boolean isChecked(Object element);
	// }

	public void addImages(Display display) {
		detailsImage = JFaceResources.getImage(ImageRegistryKeys.DETAILS); //$NON-NLS-1$
		calendarImage = JFaceResources.getImage(ImageRegistryKeys.CALENDAR); //$NON-NLS-1$
	}

	public void createPartControl(final Composite parent) {

		Group filterGroup;
		Label l;
		final Label receiptDate;
		final Label orderDate;
		final Button receiptCalendar;
		final Button orderCalendar;
		final Text searchName;
		filterGroup = new Group(parent, SWT.NONE);
		filterGroup.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1));
		filterGroup.setLayout(new GridLayout(6, true));

		l = new Label(filterGroup, SWT.CENTER);
		l.setText(Messages.OrderViewList_Filter);

		receiptDate = new Label(filterGroup, SWT.CENTER);
		final SimpleDateFormat receipt = new SimpleDateFormat("dd/MM/YYYY"); //$NON-NLS-1$
		receiptDate.setText(receipt.format(new Date()));
		// receiptDate.setText(receipt.format(OrderProvider.INSTANCE
		// .getMinOrderDate()));

		receiptCalendar = new Button(filterGroup, SWT.PUSH);
		receiptCalendar.setImage(calendarImage);
		// receiptCalendar.setText("open");
		receiptCalendar.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				final Shell dialog = new Shell(parent.getShell(),
						SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
				// dialog.setSize(200, 260);
				dialog.setLocation(Display.getCurrent().getCursorLocation());
				// dialog.setLocation(receiptCalendar.getLocation());
				dialog.setText(Messages.OrderViewList_Choose_date);
				RowLayout layoutDialog = new RowLayout();
				layoutDialog.type = SWT.VERTICAL;
				dialog.setLayout(layoutDialog);
				org.eclipse.swt.widgets.DateTime calendar = new org.eclipse.swt.widgets.DateTime(
						dialog, SWT.CALENDAR);
				calendar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						org.eclipse.swt.widgets.DateTime dateTime = (org.eclipse.swt.widgets.DateTime) e.widget;
						String dateString = String.valueOf(dateTime.getYear())
								+ "-"; //$NON-NLS-1$
						if ((dateTime.getMonth() + 1) < 10) {
							dateString += "0"; //$NON-NLS-1$
						}
						dateString += String.valueOf(dateTime.getMonth() + 1)
								+ "-"; //$NON-NLS-1$
						if (dateTime.getDay() < 10) {
							dateString += "0"; //$NON-NLS-1$
						}
						dateString += dateTime.getDay();
						// System.out.println(dateString);
						try {
							SimpleDateFormat formatyyyymmdd = new SimpleDateFormat(
									"yyyy-MM-dd"); //$NON-NLS-1$
							filterStartDate = formatyyyymmdd.parse(dateString);
							filter.setCreatedDate(filterStartDate);
							receiptDate.setText(receipt.format(filterStartDate));

							List<Order> filteredOrders = OrderProvider.INSTANCE
									.getFilteredOrders(filter);
							viewer.setInput(filteredOrders);
							paginationTable
									.setPageLoader(new PageResultLoaderList<Order>(
											filteredOrders));
							paginationTable.setCurrentPage(0);
							paginationTable.refreshPage();

							viewer.refresh();
						} catch (ParseException e1) {

							// e1.printStackTrace();
							Loggers.FILE_LOGGER.error("Error occured.", e1); //$NON-NLS-1$
						}
					}
				});

				Point dialogSize = calendar.computeSize(SWT.DEFAULT,
						SWT.DEFAULT);
				dialogSize.x += 15;
				dialogSize.y += 35;
				dialog.setSize(dialogSize);
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// empty

			}
		});

		orderDate = new Label(filterGroup, SWT.CENTER);
		SimpleDateFormat order = new SimpleDateFormat("dd/MM/YYYY"); //$NON-NLS-1$
		orderDate.setText(order.format(new Date()));
		// orderDate
		// .setText(order.format(OrderProvider.INSTANCE.getMaxOrderDate()));

		orderCalendar = new Button(filterGroup, SWT.PUSH);
		orderCalendar.setImage(calendarImage);
		// orderCalendar.setText("open");
		orderCalendar.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				final Shell dialog = new Shell(parent.getShell(),
						SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
				dialog.setLocation(Display.getCurrent().getCursorLocation());
				// dialog.setSize(200, 260);
				dialog.setText(Messages.OrderViewList_Choose_date);
				RowLayout layoutDialog = new RowLayout();
				layoutDialog.type = SWT.VERTICAL;
				dialog.setLayout(layoutDialog);
				org.eclipse.swt.widgets.DateTime calendar = new org.eclipse.swt.widgets.DateTime(
						dialog, SWT.CALENDAR);
				calendar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						org.eclipse.swt.widgets.DateTime dateTime = (org.eclipse.swt.widgets.DateTime) e.widget;
						String dateString = String.valueOf(dateTime.getYear())
								+ "-"; //$NON-NLS-1$
						if ((dateTime.getMonth() + 1) < 10) {
							dateString += "0"; //$NON-NLS-1$
						}
						dateString += String.valueOf(dateTime.getMonth() + 1)
								+ "-"; //$NON-NLS-1$
						if (dateTime.getDay() < 10) {
							dateString += "0"; //$NON-NLS-1$
						}
						dateString += dateTime.getDay();
						// System.out.println(dateString);
						try {
							SimpleDateFormat formatyyyymmdd = new SimpleDateFormat(
									"yyyy-MM-dd"); //$NON-NLS-1$
							filterEndDate = formatyyyymmdd.parse(dateString);
							filter.setGameDate(filterEndDate);
							orderDate.setText(receipt.format(filterEndDate));

							List<Order> filteredOrders = OrderProvider.INSTANCE
									.getFilteredOrders(filter);
							viewer.setInput(filteredOrders);
							paginationTable
									.setPageLoader(new PageResultLoaderList<Order>(
											filteredOrders));
							paginationTable.setCurrentPage(0);
							paginationTable.refreshPage();

							viewer.refresh();
						} catch (ParseException e1) {

							// e1.printStackTrace();
							Loggers.FILE_LOGGER.error("Error occured.", e1); //$NON-NLS-1$
						}
					}
				});
				Point dialogSize = calendar.computeSize(SWT.DEFAULT,
						SWT.DEFAULT);
				dialogSize.x += 15;
				dialogSize.y += 35;
				dialog.setSize(dialogSize);
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// empty

			}
		});
		searchName = new Text(filterGroup, SWT.SINGLE | SWT.BORDER);
		searchName.setTextLimit(15);
		searchName.setToolTipText(Messages.OrderViewList_Enter_name);
		searchName.selectAll();

		comparator = new ListComparator();
		filter = new OrderFilter();
		createViewer(parent);
		viewer.setComparator(comparator);

		searchName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent ke) {
				filter.setSearchText(searchName.getText());

				List<Order> filteredOrders = OrderProvider.INSTANCE
						.getFilteredOrders(filter);
				viewer.setInput(filteredOrders);
				paginationTable.setPageLoader(new PageResultLoaderList<Order>(
						filteredOrders));
				paginationTable.setCurrentPage(0);
				paginationTable.refreshPage();

				viewer.refresh();
			}
		});

		// filter = new OrderFilter();
		// viewer.addFilter(filter);

	}

	private void createViewer(Composite parent) {
		paginationTable = new PageableTable(parent, SWT.BORDER, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL, pageSize,
				PageResultContentProvider.getInstance(), null, null);
		paginationTable.setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer = paginationTable.getViewer();
		// viewer = new TableViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL
		// | SWT.FULL_SELECTION | SWT.BORDER);
		createColumns(parent, viewer);
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);

		viewer.setContentProvider(new ArrayContentProvider());
		// Get the content for the viewer, setInput will call getElements in the
		// contentProvider
		// viewer.setInput(OrderProvider.INSTANCE.getOrders());
		viewer.setInput(OrderProvider.INSTANCE.getFilteredOrders(filter));
		// Set the sorter for the table

		// Layout the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
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

		// paginationTable.setPageLoader(new PageResultLoaderList<Order>(
		// OrderProvider.INSTANCE.getOrders()));
		paginationTable.setPageLoader(new PageResultLoaderList<Order>(
				OrderProvider.INSTANCE.getFilteredOrders(filter)));
		paginationTable.setCurrentPage(0);

	}

	public TableViewer getViewer() {
		return viewer;
	}

	// This will create the columns for the table
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = { Messages.OrderViewList_Date_created,
				Messages.OrderViewList_Date_order, Messages.OrderViewList_Name,
				Messages.OrderViewList_Players, Messages.OrderViewList_Total,
				Messages.OrderViewList_Confirm, Messages.OrderViewList_Details };
		int[] bounds = { 120, 120, 100, 80, 80, 160, 100 };

		final BooleanCellEditor booleanCellEditor = new BooleanCellEditor(
				viewer.getTable());
		booleanCellEditor.setChangeOnActivation(true);

		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.CENTER);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(""); //$NON-NLS-1$
		column.setWidth(0);
		column.setResizable(false);
		column.setMoveable(false);
		viewerColumn.setLabelProvider(new ColumnLabelProvider());
		// column is for the Receipt Date
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.getColumn().setAlignment(SWT.CENTER);
		col.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				Order order = (Order) element;
				Date createdAt = null;
				try {
					createdAt = ApplicationConfigValues.API_DATE_TIME_FORMAT
							.parse(order.getCreatedAt());
				} catch (ParseException e) {
					createdAt = new Date();
					// e.printStackTrace();
					Loggers.FILE_LOGGER.error("Error occured.", e); //$NON-NLS-1$
				}
				String cellText = createdAtFormat.format(createdAt);
				return cellText;
			}

			@Override
			public Color getForeground(Object element) {
				return getOrderColor((Order) element);
			}
		});

		// column is for the Order Date
		col = createTableViewerColumn(titles[1], bounds[1], 1);

		col.setLabelProvider(new ColumnLabelProvider() {

			@Override
			public String getText(Object element) {
				Order order = (Order) element;
				String cellText = order.getGameDateTime();
				return cellText;
			}

			@Override
			public Color getForeground(Object element) {
				return getOrderColor((Order) element);
			}

		});

		// column is for the Name
		col = createTableViewerColumn(titles[2], bounds[2], 2);

		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Order order = (Order) element;
				String cellText = UserProvider.getUser(order.getUserId())
						.getFullName();
				return cellText;
			}

			@Override
			public Color getForeground(Object element) {
				return getOrderColor((Order) element);
			}

		});

		// column is for the Players
		col = createTableViewerColumn(titles[3], bounds[3], 3);

		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Order order = (Order) element;
				String cellText = String.valueOf(order.getPlayersNumber());
				return cellText;
			}

			@Override
			public Color getForeground(Object element) {
				return getOrderColor((Order) element);
			}
		});

		// column is for the Summ
		col = createTableViewerColumn(titles[4], bounds[4], 4);

		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Order order = (Order) element;
				String cellText = String.valueOf(order.getPrice());
				return cellText;
			}

			@Override
			public Color getForeground(Object element) {
				return getOrderColor((Order) element);
			}

		});

		// column is for the Confirm
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Order order = (Order) element;
				String cellText = Order.STATUS_MAP.get(order.getStatus());
				return cellText;
			}

			@Override
			public Color getForeground(Object element) {
				return getOrderColor((Order) element);
			}
		});
		col.setEditingSupport(new OrderStatusEditingSupport(viewer));

		// column is for the Details
		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ImageLabelProvider() {
			@Override
			public Image getImage(Object element) {
				return detailsImage;
			}
		});
		col.setEditingSupport(new DetailsEditingSupport(viewer));
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

	private Color getOrderColor(Order order) {
		Date orderDate = new Date();
		Date currentDate = new Date();
		try {
			orderDate = dateFormat.parse(order.getDate());
			currentDate = new Date();

		} catch (ParseException e) {
			Loggers.FILE_LOGGER.error("Error occured.", e); //$NON-NLS-1$
			// e.printStackTrace();
		}
		if (Days.daysBetween(new DateTime(orderDate), new DateTime(currentDate))
				.getDays() > 0) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
		}
		if (order.getStatus() != Order.STATUS_CONFIRMED
				&& order.getStatus() != Order.STATUS_APPROVED) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
		}
		if (order.isSite()) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
		}
		return Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setCurrentPage(int currentPageNumber) {
		paginationTable.setCurrentPage(currentPageNumber);
	}

	public void showNextPage() {
		if (paginationTable.getController().hasNextPage()) {
			paginationTable.setCurrentPage(paginationTable.getController()
					.getCurrentPage() + 1);
		}
	}

	public void showPreviousPage() {
		if (paginationTable.getController().hasPreviousPage()) {
			paginationTable.setCurrentPage(paginationTable.getController()
					.getCurrentPage() - 1);
		}
	}

	public int getTotalPages() {
		return paginationTable.getController().getTotalPages();
	}
}
