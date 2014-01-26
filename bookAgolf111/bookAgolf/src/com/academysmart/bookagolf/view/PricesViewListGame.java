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
import com.academysmart.bookagolf.model.Game;
import com.academysmart.bookagolf.model.PricesListComparatorGame;
import com.academysmart.bookagolf.provider.ClubProvider;
import com.academysmart.bookagolf.provider.GameProvider;
import com.academysmart.bookagolf.view.editingsupport.GameUpdateEditingSupport;
import com.academysmart.bookagolf.view.labelprovider.ColorBackgroundLabelProvider;
import com.academysmart.bookagolf.view.labelprovider.DaysOfWeekLabelProvider;
import com.academysmart.bookagolf.view.labelprovider.ImageColorBackgroundLabelProvider;

public class PricesViewListGame {
	private TableViewer viewer;
	private PricesListComparatorGame comparator;
	Image golfcar;
	Image nineHoles;
	Image eighteenHoles;
	Image set_of_golf_clubs;
	Image check;
	Image no;

	public void addImages(Display display) {
		golfcar = JFaceResources.getImage(ImageRegistryKeys.GOLFCAR);
		nineHoles = JFaceResources.getImage(ImageRegistryKeys.NINE_HOLES);
		eighteenHoles = JFaceResources
				.getImage(ImageRegistryKeys.EIGHTEEN_HOLES);
		set_of_golf_clubs = JFaceResources
				.getImage(ImageRegistryKeys.SET_OF_GOLF_CLUBS);
		check = JFaceResources.getImage(ImageRegistryKeys.EDIT);
		no = JFaceResources.getImage(ImageRegistryKeys.DELETE);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void createPartControl(Composite parent) {

		comparator = new PricesListComparatorGame();
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
		viewer.setInput(GameProvider.INSTANCE.getGames());
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

	// колонка "Время игры"
	private void createColumns(final Composite parent, final TableViewer viewer) {
		String[] titles = {
				Messages.PricesViewListGame_Time,
				"", Messages.PricesViewListGame_Field, //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-1$ //$NON-NLS-1$
				Messages.PricesViewListGame_Players,
				"", "", "", Messages.PricesViewListGame_Price, "", "" }; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		int[] bounds = { 100, 150, 280, 80, 20, 20, 20, 100, 20, 20 };

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

		// колонка "Время"
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				Game game = (Game) element;
				String gameTime = String.valueOf(game.getGameStart()) + ":00-" //$NON-NLS-1$
						+ String.valueOf(game.getGameEnd()) + ":00"; //$NON-NLS-1$
				return gameTime;
			}
		});

		// колонка "Дни недели"
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new DaysOfWeekLabelProvider(viewer));
		col.getColumn().setAlignment(SWT.RIGHT);

		// колонка "Поле"
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				Game game = (Game) element;
				String fieldTitle = ClubProvider.getClub().getName();
				fieldTitle += ", "; //$NON-NLS-1$
				fieldTitle += GameProvider.INSTANCE.getFieldName((int) game
						.getGolfFieldId());
				return fieldTitle;
			}
		});

		// колонка "Количество игроков"
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				Game game = (Game) element;
				return String.valueOf(game.getPlayers())
						+ Messages.PricesViewListGame_clients;
			}
		});

		// иконка "golfсar"
		col = createTableViewerColumn(titles[4], bounds[4], 4);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				return ""; //$NON-NLS-1$
			}

			@Override
			public Image getImage(Object element) {
				return golfcar;
			}

		});

		// иконка "time_one"
		col = createTableViewerColumn(titles[5], bounds[5], 5);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {
			@Override
			public Image getImage(Object element) {
				Game game = (Game) element;
				if (game.getHoles() == 9) {
					return nineHoles;
				}
				if (game.getHoles() == 18) {
					return eighteenHoles;
				}
				return null;

			}

			@Override
			public String getText(Object element) {
				Game game = (Game) element;
				if ((game.getHoles() != 9) && (game.getHoles() != 18)) {
					return String.valueOf(game.getHoles());
				}
				return null;

			}

		});

		// иконка "set_of_golf_clubs"
		col = createTableViewerColumn(titles[6], bounds[6], 6);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {
			@Override
			public Image getImage(Object element) {
				return set_of_golf_clubs;
			}

			@Override
			public String getText(Object element) {
				return ""; //$NON-NLS-1$
			}
		});

		// колонка "Сумма"
		col = createTableViewerColumn(titles[7], bounds[7], 7);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {
			@Override
			public String getText(Object element) {
				Game game = (Game) element;
				return String.valueOf(game.getPrice()) + " " //$NON-NLS-1$
						+ ClubProvider.getClub().getCurrency();
			}
		});

		// иконка "check"
		col = createTableViewerColumn(titles[8], bounds[8], 8);
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
		col.setEditingSupport(new GameUpdateEditingSupport(viewer));
		// иконка "no"
		col = createTableViewerColumn(titles[9], bounds[9], 9);
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
				Game game = (Game) item;
				//				System.out.println("Deleting game " + game.getId()); //$NON-NLS-1$
				//				System.out.println("price: " + game.getPrice()); //$NON-NLS-1$
				GameProvider.INSTANCE.deleteGame(game.getId());
				viewer.refresh();
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
