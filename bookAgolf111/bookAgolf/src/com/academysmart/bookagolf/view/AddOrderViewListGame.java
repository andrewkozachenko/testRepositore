package com.academysmart.bookagolf.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.academysmart.bookagolf.config.ImageRegistryKeys;
import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.model.AddOrderGameComparator;
import com.academysmart.bookagolf.model.Game;
import com.academysmart.bookagolf.model.OrderedGame;
import com.academysmart.bookagolf.provider.ClubProvider;
import com.academysmart.bookagolf.provider.GameProvider;
import com.academysmart.bookagolf.view.editingsupport.OrderedGamePlayersEditingSupport;
import com.academysmart.bookagolf.view.labelprovider.ColorBackgroundLabelProvider;
import com.academysmart.bookagolf.view.labelprovider.DaysOfWeekLabelProvider;

public class AddOrderViewListGame {
	private TableViewer viewer;
	private AddOrderGameComparator comparator;
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

		comparator = new AddOrderGameComparator();
		createViewer(parent);
		viewer.setComparator(comparator);
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

		// viewer.setInput(getOrderedGamesList(GameProvider.INSTANCE.getGames()));
		viewer.setInput(getOrderedGamesList(GameProvider.INSTANCE
				.getGamesAtDateTime(new Date(), 0, 23)));
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
				"", //$NON-NLS-1$
				Messages.PricesViewListGame_Field,
				Messages.PricesViewListGame_Players, "", "", "", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				Messages.PricesViewListGame_Price,
				Messages.AddOrderViewListGame_SubTotal, "" }; //$NON-NLS-2$ //$NON-NLS-1$
		int[] bounds = { 100, 150, 280, 80, 20, 20, 20, 100, 100, 20 };

		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer,
				SWT.CENTER);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(""); //$NON-NLS-1$
		column.setWidth(20);
		column.setResizable(true);
		column.setMoveable(true);
		viewerColumn.setLabelProvider(new ColumnLabelProvider()
		// {
		//
		// @Override
		// public String getText(Object element) {
		// return "";
		// }
		//
		// }
				);

		// колонка "Время"
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				OrderedGame game = (OrderedGame) element;
				String gameTime = String.valueOf(game.getStart()) + ":00-" //$NON-NLS-1$
						+ String.valueOf(game.getEnd()) + ":00"; //$NON-NLS-1$
				return gameTime;
			}
		});

		// колонка "Дни недели"
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new DaysOfWeekLabelProvider(viewer) {
			@Override
			public Image getImage(Object element) {
				Game game = GameProvider.INSTANCE
						.getGameById(((OrderedGame) element).getGameId());
				return super.getImage(game);
			}
		});
		col.getColumn().setAlignment(SWT.RIGHT);

		// колонка "Поле"
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				OrderedGame game = (OrderedGame) element;
				String fieldTitle = ClubProvider.getClub().getName();
				fieldTitle += ", "; //$NON-NLS-1$
				fieldTitle += GameProvider.INSTANCE.getFieldName(game
						.getGolfFieldId().intValue());
				return fieldTitle;
			}
		});

		// колонка "Количество игроков"
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {

			@Override
			public String getText(Object element) {
				OrderedGame game = (OrderedGame) element;
				return String.valueOf(game.getPlayers())
						+ Messages.PricesViewListGame_clients;
			}
		});
		col.setEditingSupport(new OrderedGamePlayersEditingSupport(viewer));

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
				OrderedGame game = (OrderedGame) element;
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
				OrderedGame game = (OrderedGame) element;
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

		// колонка "Цена"
		col = createTableViewerColumn(titles[7], bounds[7], 7);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {
			@Override
			public String getText(Object element) {
				OrderedGame game = (OrderedGame) element;
				return String.valueOf(game.getPrice()) + " " //$NON-NLS-1$
						+ ClubProvider.getClub().getCurrency();
			}
		});
		// колонка "Итого"
		col = createTableViewerColumn(titles[8], bounds[8], 8);
		col.setLabelProvider(new ColorBackgroundLabelProvider() {
			@Override
			public String getText(Object element) {
				OrderedGame game = (OrderedGame) element;
				return String.valueOf(game.getPrice() * game.getPlayers())
						+ " " //$NON-NLS-1$
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

	public void selectField(long id) {

		viewer.setInput(getOrderedGamesList(GameProvider.INSTANCE
				.getGamesOnField(id)));
		viewer.refresh();
	}

	public void selectFieldAndDateTime(Long fieldId, Date date, int timeStart,
			int timeEnd) {
		viewer.setInput(getOrderedGamesList(GameProvider.INSTANCE
				.getGamesOnFieldAtDateTime(fieldId, date, timeStart, timeEnd)));
		viewer.refresh();
	}

	private List<OrderedGame> getOrderedGamesList(List<Game> games) {
		List<OrderedGame> orderedGames = new ArrayList<OrderedGame>();
		for (Game game : games) {
			OrderedGame orderedGame = new OrderedGame(game);
			orderedGames.add(orderedGame);
		}
		return orderedGames;
	}
}
