package com.academysmart.bookagolf.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.config.ColorRegistryKeys;
import com.academysmart.bookagolf.config.ImageRegistryKeys;
import com.academysmart.bookagolf.connection.Connection;
import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.Equipment;
import com.academysmart.bookagolf.model.Field;
import com.academysmart.bookagolf.model.Game;
import com.academysmart.bookagolf.model.Order;
import com.academysmart.bookagolf.provider.ClubProvider;
import com.academysmart.bookagolf.provider.EquipmentProvider;
import com.academysmart.bookagolf.provider.FieldProvider;
import com.academysmart.bookagolf.provider.GameProvider;
import com.academysmart.bookagolf.provider.ImageProvider;
import com.academysmart.bookagolf.provider.LocalStorage;
import com.academysmart.bookagolf.provider.OrderProvider;
import com.academysmart.bookagolf.provider.UserProvider;
import com.academysmart.bookagolf.view.listener.CalendarMouseListener;
import com.academysmart.bookagolf.view.listener.FloatingPointVerifyListener;

public class MainWindow extends ApplicationWindow {
	private static MainWindow instance;

	TabItem ordersTab;
	TabItem pricesTab;
	TabFolder mainTabFolder;
	TabItem aboutTab;
	Group groupDaysNavigation;
	Group groupTimeGrid;
	Composite contentCalendarComposite;
	Composite contentListComposite;
	Composite pricesComposite;
	Date calendarViewSelectedDate = new Date();
	Date calendarViewBeginDate;
	Date calendarViewEndDate;
	// int selectedDatePosition = 3
	int selectedDatePosition = new org.joda.time.DateTime(
			calendarViewSelectedDate).getDayOfWeek() - 1;
	// TODO try to use Joda Time for this
	long oneDay = 24 * 3600 * 1000;
	long threeDays = 3 * oneDay;
	long oneWeek = 7 * oneDay;

	long beginDate = calendarViewSelectedDate.getTime()
			- (selectedDatePosition) * oneDay;
	// long endDate = calendarViewSelectedDate.getTime() + threeDays;
	SimpleDateFormat formatyyyymmdd = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$

	int listPageSizeIndex = 4;
	int listCurrentPage = 0;
	private Group filter_1;
	private Group filter_2;
	private Group filter_3;
	private Group filter_4;

	private ConnectionStatusItem connectionStatusItem;
	private OrderViewList orderViewList;
	private PricesViewListGame pricesViewListGame;
	private PricesViewEquipment pricesViewListHire;

	private boolean calendarSelected = false;

	private boolean connectionHasAuth = false;
	private boolean connectionHasApi = false;
	private boolean connectionHasInternet = false;

	ExecutorService executorService = Executors.newSingleThreadExecutor();

	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}
		return instance;
	}

	public static void main(String[] args) {
		// Locale.setDefault(Locale.ENGLISH);
		// ApplicationConfigValues.loadConfigProperties();
		// Creating main application window
		// MainWindow mWindow = new MainWindow();
		MainWindow mWindow = getInstance();
		// Adding close button
		mWindow.setBlockOnOpen(true);
		// Open main window
		mWindow.open();
		// Dispose resources
		mWindow.executorService.shutdownNow();
		Display.getCurrent().dispose();

	}

	public MainWindow() {
		super(null);
		// Creating status line
		addStatusLine();
		connectionStatusItem = new ConnectionStatusItem("1"); //$NON-NLS-1$
		getStatusLineManager().add(connectionStatusItem);
		getStatusLineManager().update(true);

		updateConnectionStatus();

		LocalStorage.getInstance().loadDataFromFile();
		if (connectionHasAuth) {
			LocalStorage.getInstance().updateViaApi();
			Loggers.FILE_LOGGER.info("Local storage synced"); //$NON-NLS-1$
		}

		// TODO consider centralized initialization
		UserProvider.loadUsers();

		// Creating menu
		createMenu();
		// startPeriodicalConnection();
		// activateRefreshTimer(Display.getCurrent());
		activateAsyncRefreshTimer(Display.getCurrent());

	}

	@Override
	public void create() {
		setShellStyle(SWT.DIALOG_TRIM);
		super.create();
	}

	// Creating menu
	private void createMenu() {
		// Добавляем панель меню
		addMenuBar();

		// Создание пункта меню Регистрация
		// MenuManager menuManager = new MenuManager("&Регистрация");
		// Добавление пункта меню в menubar
		// getMenuBarManager().add(menuManager);
		// Обработчик нажатия на пункте меню
		// menuManager.add(new Action("Регистрация") {
		//
		// @Override
		// public void run() {
		//
		// // Код обработки нажатия пункта меню
		// }
		// });

		// Создаем меню О программе
		MenuManager fileMenu = new MenuManager(Messages.MainWindow_AmpAbout);
		// Добавление пункта меню в menubar
		getMenuBarManager().add(fileMenu);
		// Добавляем разделитель в меню
		fileMenu.add(new Separator());
		// Обработчик нажатия на пункте меню
		fileMenu.add(new Action(Messages.MainWindow_About) {
			// Обработчик нажатия на пукт меню
			@Override
			public void run() {

				// Код обработки нажатия пункта меню
			}

		});

		// Создаем меню Помощь
		MenuManager helpMenu = new MenuManager(Messages.MainWindow_AmpHelp);
		// Добавление пункта меню в menubar
		getMenuBarManager().add(helpMenu);
		// Добавляем разделитель в меню
		helpMenu.add(new Separator());
		// Обработчик нажатия на пункте меню
		helpMenu.add(new Action(Messages.MainWindow_Help) {
			// Обработчик нажатия на пукт меню
			@Override
			public void run() {

				// Код обработки нажатия пункта меню
			}

		});

	}

	// Наполнение окна
	protected Control createContents(Composite parent) {

		// Установка текста статусной строки
		setStatus(Messages.MainWindow_this_status
				+ OrderProvider.INSTANCE.getNumberOfNewOrders());

		// Размер окна
		// parent.setSize(1150, 760);
		// Заголовок окна
		getShell().setText(Messages.MainWindow_WindowTitle);

		// Создание табов
		createColorResuorces();
		createImageResources();
		createTabs();

		Point windowSize = mainTabFolder.computeSize(-1, -1);
		windowSize.x += 30;
		windowSize.y += 50;
		parent.setSize(windowSize);
		return null;
	}

	// Создание табов
	private void createTabs() {

		mainTabFolder = new TabFolder(getShell(), SWT.NONE);
		// Вкладка Бронирование
		ordersTab = new TabItem(mainTabFolder, SWT.NONE);
		ordersTab.setText(Messages.MainWindow_ordersTab_text);
		ordersTab.setToolTipText(Messages.MainWindow_ordersTab_toolTipText);
		// Наполнение содержимым вкладки пункт List
		ordersTab.setControl(setContentList(mainTabFolder));

		// Наполнение содержимым вкладки пункт Calendar
		// bron.setControl(setContentCalendar(tabFolder));
		// Вкладка Предложение
		// TabItem predl = new TabItem(tabFolder, SWT.NULL);
		// predl.setText("Предложение");
		// predl.setToolTipText("Предложение о ...");
		// Вкладка Цены
		pricesTab = new TabItem(mainTabFolder, SWT.NONE);
		pricesTab.setText(Messages.MainWindow_prices_text);
		pricesTab.setToolTipText(Messages.MainWindow_prices_toolTipText);
		// Создание интерфейса вкладки "Цены"
		// с помощью наполнения содержимым вкладки
		pricesTab.setControl(setPricesOrder(mainTabFolder));

		// Вкладка Акции
		// TabItem share = new TabItem(tabFolder, SWT.NONE);
		// share.setText("Акции");
		// share.setToolTipText("Акции на ...");
		// Вкладка Мероприятия
		// TabItem actions = new TabItem(tabFolder, SWT.NONE);
		// actions.setText("Мероприятия");
		// actions.setToolTipText("Мероприятия по ...");
		// GalleryExampleTab galleryTab = new GalleryExampleTab();
		// galleryTab.create(tabFolder);
		// galleryTab.createParameters(tabFolder);
		// actions.setControl(galleryTab.createControl(tabFolder));
		// Вкладка Информация о компании
		aboutTab = new TabItem(mainTabFolder, SWT.NONE);
		aboutTab.setText(Messages.MainWindow_about_text);
		aboutTab.setToolTipText(Messages.MainWindow_about_toolTipText);

		// Создание интерфейса вкладки "Информация о компании"
		// с помощью вызова соответствующего конструктора
		aboutTab.setControl(new CompanyInfo(mainTabFolder));

	}

	private Button addNewOrderButton(Composite composite) {
		Button addNewOrderButton = new Button(composite, SWT.PUSH);
		addNewOrderButton.setText(Messages.MainWindow_addOrder);
		addNewOrderButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Shell dialog = new AddOrderView().createOrderDialog();
				dialog.addDisposeListener(new DisposeListener() {

					@Override
					public void widgetDisposed(DisposeEvent arg0) {
						executorService.execute(new Runnable() {

							@Override
							public void run() {
								OrderProvider.INSTANCE.refreshOrders();
								MainWindow.getInstance().getShell()
										.getDisplay().asyncExec(new Runnable() {

											@Override
											public void run() {
												if (calendarSelected) {
													ordersTab
															.setControl(setContentCalendar(mainTabFolder));
												} else {
													ordersTab
															.setControl(setContentList(mainTabFolder));
												}
											}
										});
							}
						});
					}
				});
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// empty

			}
		});
		return addNewOrderButton;
	}

	// Блок радиокнопок переключения режима просмотра бринорования
	private Group groupeButton(Composite composite, int selectedPosition) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(Messages.MainWindow_group_text);

		Button radioList = new Button(group, SWT.RADIO);
		radioList.setText(Messages.MainWindow_radioList_text);
		radioList.setBounds(10, 30, 220, 20);

		if (selectedPosition == 0) {
			radioList.setSelection(true);
		}
		radioList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button b = (Button) e.widget;
				if (b.getSelection()) {
					calendarSelected = false;
					ordersTab.setControl(setContentList(mainTabFolder));
				}

			}
		});

		Button radioCalendar = new Button(group, SWT.RADIO);
		radioCalendar.setText(Messages.MainWindow_radioCalendar_text);
		radioCalendar.setBounds(10, 50, 220, 30);
		if (selectedPosition == 1) {
			radioCalendar.setSelection(true);
		}
		radioCalendar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button b = (Button) e.widget;
				if (b.getSelection()) {
					calendarSelected = true;
					ordersTab.setControl(setContentCalendar(mainTabFolder));
				}
			}
		});

		return group;
	}

	private Control setContentList(TabFolder tabFolder) {

		Group sort;
		// Label l;
		Label outOf;
		Button before;
		Button next;

		if (contentListComposite != null) {
			contentListComposite.dispose();
		}
		contentListComposite = new Composite(tabFolder, SWT.NONE);
		contentListComposite.setLayout(new GridLayout(1, false));
		final String[] pageSizeData = { "10", "20", "30", "40", "50" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		orderViewList = new OrderViewList();
		orderViewList.addImages(contentListComposite.getDisplay());
		orderViewList.setPageSize(Integer
				.valueOf(pageSizeData[listPageSizeIndex]));
		orderViewList.createPartControl(contentListComposite);
		orderViewList.getViewer();

		addNewOrderButton(contentListComposite);

		sort = new Group(contentListComposite, SWT.NONE);
		sort.setLayout(new GridLayout(5, false));
		sort.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));

		groupeButton(sort, 0);

		outOf = new Label(sort, SWT.CENTER);
		outOf.setText(Messages.MainWindow_outOf_text);

		Combo viewBy = createCombo(sort, pageSizeData);
		viewBy.select(listPageSizeIndex);
		viewBy.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				listPageSizeIndex = ((Combo) e.widget).getSelectionIndex();
				ordersTab.setControl(setContentList(mainTabFolder));

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// empty

			}
		});

		before = new Button(sort, SWT.PUSH);
		before.setText(Messages.MainWindow_Previous
				+ pageSizeData[listPageSizeIndex]);
		before.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				orderViewList.showPreviousPage();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// empty

			}
		});

		next = new Button(sort, SWT.PUSH);
		next.setText(Messages.MainWindow_Next + pageSizeData[listPageSizeIndex]);
		next.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				orderViewList.showNextPage();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// empty

			}
		});

		return contentListComposite;
	}

	private Control setPricesOrder(TabFolder tabFolder) {
		Button buttonAddHire;
		Group filter;
		Label title_one;
		Label title_two;
		if (pricesComposite != null) {
			pricesComposite.dispose();
		}
		pricesComposite = new Composite(tabFolder, SWT.NONE);
		pricesComposite.setLayout(new GridLayout(1, true));

		// вкладка "Игра"
		filter = new Group(pricesComposite, SWT.NONE);
		filter.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		filter.setLayout(new GridLayout(1, true));

		title_one = new Label(filter, SWT.CENTER);
		title_one.setText(Messages.MainWindow_title_one_text);

		// меню вкладки "Игра"

		pricesViewListGame = new PricesViewListGame();
		pricesViewListGame.addImages(pricesComposite.getDisplay());
		pricesViewListGame.createPartControl(filter);
		pricesViewListGame.getViewer();

		// первое поле выбора атрибутов вкладки "Игра"
		filter_1 = new Group(pricesComposite, SWT.SEPARATOR | SWT.LEFT);
		filter_1.setLayoutData(new GridData(SWT.LEFT, SWT.SEPARATOR, true,
				false, 0, 0));
		filter_1.setLayout(new GridLayout(15, false));

		// "Время"
		// final Text timeStartText = new Text(filter_1, SWT.Expand |
		// SWT.BORDER);
		// timeStartText.setTextLimit(2);
		// timeStartText.selectAll();

		final Combo timeStartText = new Combo(filter_1, SWT.READ_ONLY);
		timeStartText
				.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		for (int i = 0; i < 24; i++) {
			timeStartText.add(String.valueOf(i));
		}
		if (timeStartText.getItemCount() > 0) {
			timeStartText.select(0);
		}

		Label timeStartLabel = new Label(filter_1, SWT.CENTER);
		timeStartLabel.setText(Messages.MainWindow_timeStartLabel_text);

		// final Text timeEndText = new Text(filter_1, SWT.Expand | SWT.BORDER);
		// timeEndText.setTextLimit(2);
		// timeEndText.selectAll();

		final Combo timeEndText = new Combo(filter_1, SWT.READ_ONLY);
		timeEndText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		for (int i = 0; i < 24; i++) {
			timeEndText.add(String.valueOf(i));
		}
		if (timeEndText.getItemCount() > 0) {
			timeEndText.select(timeEndText.getItemCount() - 1);
		}

		Label timeEndLabel = new Label(filter_1, SWT.CENTER);
		timeEndLabel.setText(Messages.MainWindow_timeEndLabel_text);

		// кнопки дней недели
		final Button mondayButton = new Button(filter_1, SWT.TOGGLE
				| SWT.BORDER);
		mondayButton.setText(Messages.MainWindow_mondayButton_text);

		final Button tuesdayButton = new Button(filter_1, SWT.TOGGLE
				| SWT.BORDER);
		tuesdayButton.setText(Messages.MainWindow_tuesdayButton_text);

		final Button wednesdayButton = new Button(filter_1, SWT.TOGGLE
				| SWT.BORDER);
		wednesdayButton.setText(Messages.MainWindow_wednesdayButton_text);

		final Button thursdayButton = new Button(filter_1, SWT.TOGGLE
				| SWT.BORDER);
		thursdayButton.setText(Messages.MainWindow_thursdayButton_text);

		final Button fridayButton = new Button(filter_1, SWT.TOGGLE
				| SWT.BORDER);
		fridayButton.setText(Messages.MainWindow_fridayButton_text);

		final Button saturdayButton = new Button(filter_1, SWT.TOGGLE
				| SWT.BORDER);
		saturdayButton.setText(Messages.MainWindow_saturdayButton_text);

		final Button sundayButton = new Button(filter_1, SWT.TOGGLE
				| SWT.BORDER);
		sundayButton.setText(Messages.MainWindow_sundayButton_text);

		Label Days = new Label(filter_1, SWT.CENTER);
		Days.setText(Messages.MainWindow_Days_text);

		// выпадающий список выбора названия поля
		final Combo fieldName = new Combo(filter_1, SWT.READ_ONLY);
		fieldName.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		List<Field> fields = FieldProvider.INSTANCE.getFields();
		for (Field field : fields) {
			fieldName.add(ClubProvider.getClub().getName() + ", " //$NON-NLS-1$
					+ field.getName());

		}
		if (fieldName.getItemCount() > 0) {
			fieldName.select(0);
		}
		// FieldName.add("Orange Lake Resort, The Legends");
		// FieldName.add("Orange Lake Resort, The Mysteries");

		Label labelFieldName = new Label(filter_1, SWT.CENTER);
		labelFieldName.setText(Messages.MainWindow_labelFieldName_text);

		// второе поле выбора атрибутов вкладки "Игра"
		filter_2 = new Group(pricesComposite, SWT.SEPARATOR | SWT.LEFT);
		filter_2.setLayoutData(new GridData(SWT.LEFT, SWT.SEPARATOR, false,
				false, 0, 0));
		filter_2.setLayout(new GridLayout(7, false));

		List<Equipment> equipmentsAvailable = EquipmentProvider.INSTANCE
				.getEquipments();
		Composite compositeCheckboxes = new Composite(filter_2, SWT.NONE);
		compositeCheckboxes.setLayoutData(new GridData(SWT.LEFT, SWT.SEPARATOR,
				false, false, 0, 0));
		compositeCheckboxes.setLayout(new GridLayout(
				equipmentsAvailable.size() >= 3 ? 3 : equipmentsAvailable
						.size(), false));

		for (Equipment equipment : equipmentsAvailable) {
			Button equipmentCheckBox = new Button(compositeCheckboxes,
					SWT.CHECK);
			equipmentCheckBox.setText(equipment.getName());
		}
		// // чекбокс "Гольфкар"
		// Button golfCar = new Button(filter_2, SWT.CHECK);
		// golfCar.setText(Messages.MainWindow_golfCar_text);
		//
		// // чекбокс "Тележка"
		// Button trolley = new Button(filter_2, SWT.CHECK);
		// trolley.setText(Messages.MainWindow_trolley_text);
		//
		// // чекбокс "Клюшка"
		// Button club = new Button(filter_2, SWT.CHECK);
		// club.setText(Messages.MainWindow_club_text);

		// выпадающий список выбора количества игроков
		final Combo numberOfPlayers = new Combo(filter_2, SWT.READ_ONLY);
		numberOfPlayers.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL));
		numberOfPlayers.add("1"); //$NON-NLS-1$
		numberOfPlayers.add("2"); //$NON-NLS-1$
		numberOfPlayers.add("3"); //$NON-NLS-1$
		numberOfPlayers.add("4"); //$NON-NLS-1$
		//		numberOfPlayers.add("5"); //$NON-NLS-1$
		//		numberOfPlayers.add("6"); //$NON-NLS-1$
		//		numberOfPlayers.add("7"); //$NON-NLS-1$
		//		numberOfPlayers.add("8"); //$NON-NLS-1$
		if (numberOfPlayers.getItemCount() > 0) {
			numberOfPlayers.select(0);
		}

		Label labelNumberOfPlayers = new Label(filter_2, SWT.CENTER);
		labelNumberOfPlayers
				.setText(Messages.MainWindow_labelNumberOfPlayers_text);

		// ввод количества денег
		final Text price = new Text(filter_2, SWT.Expand | SWT.BORDER);
		price.setTextLimit(10);
		price.addVerifyListener(new FloatingPointVerifyListener());
		price.selectAll();

		// выпадающий список выбора валюты
		Combo currency = new Combo(filter_2, SWT.READ_ONLY);
		currency.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		//		currency.add("USD"); //$NON-NLS-1$
		//		currency.add("UAH"); //$NON-NLS-1$
		if (ClubProvider.getClub() != null)
			currency.add(ClubProvider.getClub().getCurrency());
		if (currency.getItemCount() > 0) {
			currency.select(0);
		}

		Label labelPrice = new Label(filter_2, SWT.CENTER);
		labelPrice.setText(Messages.MainWindow_labelPrice_text);

		// кнопка "Добавить"
		Button Add = new Button(filter_2, SWT.NONE);
		Add.setText(Messages.MainWindow_Add_text);

		// обработчик события при нажатии на кнопку "Добавить"
		Add.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// empty
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Game game = new Game();
				game.setGolfFieldId(FieldProvider.INSTANCE.getFields()
						.get(fieldName.getSelectionIndex()).getId());
				game.setGameStart(Integer.parseInt(timeStartText.getText()));
				game.setGameEnd(Integer.parseInt(timeEndText.getText()));
				game.setPlayers(Integer.parseInt(numberOfPlayers.getText()));
				game.setPrice(Double.parseDouble(price.getText()));
				game.setMon(mondayButton.getSelection());
				game.setTue(tuesdayButton.getSelection());
				game.setWed(wednesdayButton.getSelection());
				game.setThu(thursdayButton.getSelection());
				game.setFri(fridayButton.getSelection());
				game.setSat(saturdayButton.getSelection());
				game.setSun(sundayButton.getSelection());
				game.setHoles(ClubProvider.getClub().getHolesList());
				// System.out.println(game);
				GameProvider.INSTANCE.addGame(game);
				pricesViewListGame.getViewer().refresh();

				MainWindow.getInstance().updateConnectionStatus();

				// timeStartText.setText("");
				if (timeStartText.getItemCount() > 0) {
					timeStartText.select(0);
				}
				// timeEndText.setText("");
				if (timeEndText.getItemCount() > 0) {
					timeEndText.select(0);
				}

				if (numberOfPlayers.getItemCount() > 0) {
					numberOfPlayers.select(0);
				}
				price.setText(""); //$NON-NLS-1$
				mondayButton.setSelection(false);
				tuesdayButton.setSelection(false);
				wednesdayButton.setSelection(false);
				thursdayButton.setSelection(false);
				fridayButton.setSelection(false);
				saturdayButton.setSelection(false);
				sundayButton.setSelection(false);
			}
		});

		filter_3 = new Group(pricesComposite, SWT.NONE);
		filter_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		filter_3.setLayout(new GridLayout(1, true));

		title_two = new Label(filter_3, SWT.CENTER);
		title_two.setText(Messages.MainWindow_title_two_text);

		// меню вкладки "Прокат"

		// pricesViewListHire = new PricesViewListHire();
		pricesViewListHire = new PricesViewEquipment();
		pricesViewListHire.addImages(pricesComposite.getDisplay());
		pricesViewListHire.createPartControl(filter_3);
		pricesViewListHire.getViewer();

		// поле выбора атрибутов вкладки "Прокат"
		filter_4 = new Group(pricesComposite, SWT.SEPARATOR | SWT.LEFT);
		filter_4.setLayoutData(new GridData(SWT.LEFT, SWT.SEPARATOR, false,
				false, 0, 0));
		filter_4.setLayout(new GridLayout(16, false));

		// "Время"
		// Label timeHire = new Label(filter_4, SWT.CENTER);
		// timeHire.setText(Messages.MainWindow_timeHire_text);

		// кнопки дней недели
		// Button mondayHire = new Button(filter_4, SWT.TOGGLE | SWT.BORDER);
		// mondayHire.setText(Messages.MainWindow_mondayHire_text);
		// ///////////////////////////////////////////////////////////////////

		// Button tuesdayHire = new Button(filter_4, SWT.TOGGLE | SWT.BORDER);
		// tuesdayHire.setText(Messages.MainWindow_tuesdayHire_text);

		// ///////////////////////////////////////////////////////////////////

		// Button wednesdayHire = new Button(filter_4, SWT.TOGGLE | SWT.BORDER);
		// wednesdayHire.setText(Messages.MainWindow_wednesdayHire_text);

		// ///////////////////////////////////////////////////////////////////

		// Button thursdayHire = new Button(filter_4, SWT.TOGGLE | SWT.BORDER);
		// thursdayHire.setText(Messages.MainWindow_thursdayHire_text);

		// ///////////////////////////////////////////////////////////////////
		// Button fridayHire = new Button(filter_4, SWT.TOGGLE | SWT.BORDER);
		// fridayHire.setText(Messages.MainWindow_fridayHire_text);

		// ///////////////////////////////////////////////////////////////////
		// Button saturdayHire = new Button(filter_4, SWT.TOGGLE | SWT.BORDER);
		// saturdayHire.setText(Messages.MainWindow_saturdayHire_text);

		// ///////////////////////////////////////////////////////////////////
		// Button sundayHire = new Button(filter_4, SWT.TOGGLE | SWT.BORDER);
		// sundayHire.setText(Messages.MainWindow_sundayHire_text);

		// ///////////////////////////////////////////////////////////////////
		// Label daysHire = new Label(filter_4, SWT.CENTER);
		// daysHire.setText(Messages.MainWindow_daysHire_text);

		// выпадающий список выбора кары
		// Combo accessoryHire = new Combo(filter_4, SWT.READ_ONLY);
		// accessoryHire
		// .setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		// List<Equipment> equipments =
		// EquipmentProvider.INSTANCE.getEquipments();
		// for (Equipment equipment : equipments) {
		// accessoryHire.add(equipment.getName());
		// }
		// if (accessoryHire.getItemCount() > 0) {
		// accessoryHire.select(0);
		// }
		// accessoryHire.add("Кара");
		// accessoryHire.add("Тележка");
		// accessoryHire.add("Набор клюшек");

		// Label accessory = new Label(filter_4, SWT.CENTER);
		// accessory.setText(Messages.MainWindow_accessory_text);

		// выпадающий список выбора количества аксессуаров
		// Combo numberOfPlayersHire = new Combo(filter_4, SWT.NONE);
		// numberOfPlayersHire.setLayoutData(new GridData(
		// GridData.HORIZONTAL_ALIGN_FILL));
		//		numberOfPlayersHire.add("1"); //$NON-NLS-1$
		//		numberOfPlayersHire.add("2"); //$NON-NLS-1$
		// if (numberOfPlayersHire.getItemCount() > 0) {
		// numberOfPlayersHire.select(0);
		// }
		//
		// Label number = new Label(filter_4, SWT.CENTER);
		// number.setText(Messages.MainWindow_number_text);
		final Text nameEquipment = new Text(filter_4, SWT.Expand | SWT.BORDER);

		Label labelNameEquipment = new Label(filter_4, SWT.CENTER);
		labelNameEquipment.setText(Messages.PricesViewListHire_Equipment);

		final Text priceEquipment = new Text(filter_4, SWT.Expand | SWT.BORDER);
		priceEquipment.addVerifyListener(new FloatingPointVerifyListener());
		priceEquipment.setTextLimit(10);
		priceEquipment.selectAll();

		// выпадающий список выбора валюты
		Combo currencyHire = new Combo(filter_4, SWT.READ_ONLY);
		currencyHire
				.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		//		currencyHire.add("USD"); //$NON-NLS-1$
		//		currencyHire.add("UAH"); //$NON-NLS-1$
		if (ClubProvider.getClub() != null)
			currencyHire.add(ClubProvider.getClub().getCurrency());
		if (currencyHire.getItemCount() > 0) {
			currencyHire.select(0);
		}

		Label labelPriceEquipment = new Label(filter_4, SWT.CENTER);
		labelPriceEquipment.setText(Messages.MainWindow_labelPrice_text);

		buttonAddHire = new Button(filter_4, SWT.NONE);
		buttonAddHire.setText(Messages.MainWindow_buttonAddHire_text);

		// обработчик события при нажатии на кнопку "Добавить"
		buttonAddHire.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// empty
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Equipment equipment = new Equipment();
				equipment.setName(nameEquipment.getText());
				equipment.setPrice(Double.parseDouble(priceEquipment.getText()));
				EquipmentProvider.INSTANCE.addEquipment(equipment);
				updatePricesTab();
				MainWindow.getInstance().updateConnectionStatus();
			}
		});
		return pricesComposite;
	}

	protected Combo createCombo(Composite parent, String[] data) {
		Combo combo = new Combo(parent, SWT.DROP_DOWN | SWT.READ_ONLY
				| SWT.V_SCROLL | SWT.H_SCROLL);

		setComboContents(data, combo);
		return combo;
	}

	protected void setComboContents(String[] data, Combo combo) {
		combo.removeAll();
		for (int i = 0; i < data.length; i++) {
			combo.add(data[i]);
		}
	}

	private Control setContentCalendar(TabFolder tabFolder) {

		if (contentCalendarComposite != null) {
			contentCalendarComposite.dispose();
		}
		contentCalendarComposite = new Composite(tabFolder, SWT.NONE);

		GridLayout rl = new GridLayout();
		rl.numColumns = 1;
		contentCalendarComposite.setLayout(rl);

		groupDaysNavigation = new Group(contentCalendarComposite, SWT.NONE);
		groupTimeGrid = new Group(contentCalendarComposite, SWT.SHELL_TRIM
				| SWT.V_SCROLL | SWT.BEGINNING);

		setDaysNavigation();

		// Блок таблицы диапазонов времени
		GridLayout gLable = new GridLayout();
		gLable.marginLeft = 30;
		gLable.numColumns = 13;
		gLable.marginRight = 30;
		gLable.marginTop = 20;
		gLable.marginBottom = 20;

		groupTimeGrid.setLayout(gLable);
		gLable.horizontalSpacing = 0;
		gLable.verticalSpacing = 0;

		for (int i = 0; i <= 5; i++) { // Количество строк таблицы
			for (int j = 0; j < 13; j++) { // Количество столбцов таблицы

				GridData gd = new GridData(GridData.FILL_HORIZONTAL);
				gd.widthHint = 60;
				gd.heightHint = 60;
				gd.horizontalAlignment = GridData.HORIZONTAL_ALIGN_CENTER;
				gd.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
				// gd.minimumHeight = 65;
				// gd.minimumWidth = 65;
				GridLayout gl = new GridLayout(1, true);
				gl.verticalSpacing = 0;

				Composite gridComposite = new Composite(groupTimeGrid, SWT.FILL
						| SWT.BORDER);
				gridComposite.setLayout(gl);
				CLabel cltemp = new CLabel(gridComposite, SWT.CENTER);
				GridData labelGridData = new GridData(GridData.FILL_BOTH);
				cltemp.setLayoutData(labelGridData);
				String timeString = (j + 8) + ":" + i + "0"; //$NON-NLS-1$ //$NON-NLS-2$
				cltemp.setText(timeString);
				Color cellColor = null;
				List<Order> ordersAtDateTime = OrderProvider.INSTANCE
						.getOrdersAtDateTime(
								formatyyyymmdd.format(calendarViewSelectedDate),
								timeString);
				for (Order order : ordersAtDateTime) {
					String cellText = UserProvider.getUser(order.getUserId())
							.getFullName();
					Label orderLabel = new Label(gridComposite, SWT.CENTER);
					GridData orderLabelGridData = new GridData(
							GridData.FILL_HORIZONTAL);
					orderLabelGridData.horizontalSpan = 0;
					orderLabelGridData.verticalSpan = 0;
					orderLabel.setLayoutData(orderLabelGridData);
					orderLabel.setText(cellText);
					if (order.getStatus() != Order.STATUS_CONFIRMED
							&& order.getStatus() != Order.STATUS_APPROVED) {
						cellColor = Display.getCurrent().getSystemColor(
								SWT.COLOR_RED);
					}
					if (order.getStatus() == Order.STATUS_CONFIRMED) {
						cellColor = Display.getCurrent().getSystemColor(
								SWT.COLOR_GREEN);
					}
					if (cellColor != null) {
						orderLabel.setForeground(cellColor);
					}
					orderLabel.addMouseListener(new CalendarMouseListener(
							groupTimeGrid, order));

				}

				// cltemp.addMouseListener(new CalendarMouseListener(
				// groupTimeGrid, ordersAtDateTime));

				gridComposite.setLayoutData(gd);
			}
		}
		addNewOrderButton(contentCalendarComposite);
		// Блок радио кнопок
		groupeButton(contentCalendarComposite, 1);

		return contentCalendarComposite;
	}

	private void setDaysNavigation() {
		GridLayout gridLayout;

		Button weekPrev = null;
		Button weekNext = null;
		Button dayPrev = null;
		Button dayNext = null;
		GridData gridData = null;
		CLabel nowLabel = null;

		SimpleDateFormat formatddMMYYYY;

		calendarViewBeginDate = new Date(beginDate);
		// calendarViewEndDate = new Date(endDate);

		Date[] displayedDates = new Date[7];
		Control[] groupChildren = groupDaysNavigation.getChildren();
		for (Control control : groupChildren) {
			control.dispose();
		}
		// Кнопка вызова календаря
		Button calendarButton = new Button(groupDaysNavigation, SWT.PUSH);
		// calendarButton.setText(" K ");
		calendarButton.setImage(JFaceResources
				.getImage(ImageRegistryKeys.CALENDAR));
		calendarButton.setToolTipText(Messages.MainWindow_CallCalendarToolTip);
		calendarButton.setSize(100, 20);
		calendarButton.setBounds(100, 20, 50, 30);
		calendarButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				final Shell dialog = new Shell(getShell(),
						SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
				dialog.setLocation(Display.getCurrent().getCursorLocation());
				// dialog.setSize(200, 260);
				dialog.setText(Messages.MainWindow_ChooseDate);
				RowLayout layoutDialog = new RowLayout();
				layoutDialog.type = SWT.VERTICAL;
				dialog.setLayout(layoutDialog);
				DateTime calendar = new DateTime(dialog, SWT.CALENDAR);
				calendar.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						DateTime dateTime = (DateTime) e.widget;
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
							calendarViewSelectedDate = formatyyyymmdd
									.parse(dateString);
						} catch (ParseException e1) {

							// e1.printStackTrace();
							Loggers.FILE_LOGGER.error("Error occured.", e1); //$NON-NLS-1$
						}
						selectedDatePosition = new org.joda.time.DateTime(
								calendarViewSelectedDate).getDayOfWeek() - 1;

						beginDate = calendarViewSelectedDate.getTime()
								- selectedDatePosition * oneDay;
						ordersTab.setControl(setContentCalendar(mainTabFolder));
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

		// Блок навигации по дням недели
		gridLayout = new GridLayout();
		gridLayout.numColumns = 30;
		gridLayout.horizontalSpacing = 0;
		groupDaysNavigation.setLayout(gridLayout);

		gridData = new GridData(GridData.FILL_HORIZONTAL);
		Label clear = new Label(groupDaysNavigation, SWT.PUSH | SWT.FLAT);
		clear.setSize(200, 300);
		clear.setText("                         "); //$NON-NLS-1$
		clear.setLayoutData(gridData);

		// Кнопка навигации по календарю
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		weekPrev = new Button(groupDaysNavigation, SWT.PUSH | SWT.FLAT);
		weekPrev.setText("<<"); //$NON-NLS-1$
		weekPrev.setLayoutData(gridData);
		weekPrev.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// empty

			}

			@Override
			public void mouseDown(MouseEvent arg0) {

				beginDate -= oneWeek;
				ordersTab.setControl(setContentCalendar(mainTabFolder));

			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// empty

			}
		});
		// Кнопка навигации по календарю
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		dayPrev = new Button(groupDaysNavigation, SWT.PUSH | SWT.FLAT);
		dayPrev.setText("<"); //$NON-NLS-1$
		dayPrev.setLayoutData(gridData);
		dayPrev.setBounds(10, 20, 20, 20);

		dayPrev.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// empty

			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				if (selectedDatePosition == 0) {
					beginDate -= oneWeek;
					selectedDatePosition = 6;
					// int dayofWeek = new org.joda.time.DateTime(beginDate)
					// .getDayOfWeek();
				} else {
					selectedDatePosition--;
				}
				ordersTab.setControl(setContentCalendar(mainTabFolder));
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// empty

			}
		});
		formatddMMYYYY = new SimpleDateFormat("dd-MM-YYYY"); //$NON-NLS-1$

		for (int i = 0; i < displayedDates.length; i++) {
			displayedDates[i] = new Date(beginDate + i * oneDay);
		}

		calendarViewSelectedDate = displayedDates[selectedDatePosition];

		for (int i = 0; i < displayedDates.length; i++) {
			nowLabel = new CLabel(groupDaysNavigation, SWT.CENTER
					| SWT.SHADOW_OUT);
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = 3;
			nowLabel.setLayoutData(gridData);
			nowLabel.setText(formatddMMYYYY.format(displayedDates[i]));
			List<Order> ordersAtDate = OrderProvider.INSTANCE
					.getOrdersAtDate(formatyyyymmdd.format(displayedDates[i]));
			for (Order order : ordersAtDate) {
				if (order.getStatus() != Order.STATUS_CONFIRMED
						&& order.getStatus() != Order.STATUS_APPROVED) {
					nowLabel.setForeground(Display.getCurrent().getSystemColor(
							SWT.COLOR_RED));
					break;
				}
			}
			if (i == selectedDatePosition) {
				nowLabel.setBackground(groupDaysNavigation.getDisplay()
						.getSystemColor(SWT.COLOR_CYAN));
			}
			final int labelPosition = i;
			nowLabel.addMouseListener(new MouseListener() {

				@Override
				public void mouseUp(MouseEvent arg0) {
					// empty

				}

				@Override
				public void mouseDown(MouseEvent arg0) {
					selectedDatePosition = labelPosition;
					ordersTab.setControl(setContentCalendar(mainTabFolder));
				}

				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
					// empty

				}
			});
			// gridData.widthHint = 70;
			// gridData.heightHint = 25;

		}

		// Кнопка навигации по календарю
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		dayNext = new Button(groupDaysNavigation, SWT.PUSH | SWT.FLAT);
		dayNext.setText(">"); //$NON-NLS-1$
		dayNext.setLayoutData(gridData);
		dayNext.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// empty

			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				if (selectedDatePosition == 6) {
					beginDate += oneWeek;
					selectedDatePosition = 0;
				} else {
					selectedDatePosition++;
				}
				ordersTab.setControl(setContentCalendar(mainTabFolder));

			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// empty
			}
		});
		// Кнопка навигации по календарю
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		weekNext = new Button(groupDaysNavigation, SWT.PUSH | SWT.FLAT);
		weekNext.setText(">>"); //$NON-NLS-1$
		weekNext.setLayoutData(gridData);

		weekNext.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				// empty

			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				beginDate += oneWeek;
				ordersTab.setControl(setContentCalendar(mainTabFolder));

			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// empty

			}
		});
	}

	public void updateConnectionStatus() {
		checkConnectionStatus();
		updateConnectionStatusViewElements();
	}

	private void checkConnectionStatus() {
		connectionHasAuth = false;
		connectionHasApi = false;
		connectionHasInternet = false;
		if (Connection.hasAuth()) {
			connectionHasAuth = true;
			connectionHasApi = true;
			connectionHasInternet = true;
		} else if (Connection.hasApiConnection()) {
			connectionHasApi = true;
			connectionHasInternet = true;
		} else if (Connection.hasInternetConnection()) {
			connectionHasInternet = true;
		}
	}

	private void updateConnectionStatusViewElements() {
		if (connectionHasAuth) {
			connectionStatusItem.setInternetColorGreen();
			connectionStatusItem.setApiColorGreen();
			connectionStatusItem.setAuthorizationColorGreen();
			connectionStatusItem
					.setIntertnetStatus(Messages.ConnectionStatusItem_Internet);
		} else if (connectionHasApi) {
			connectionStatusItem.setInternetColorGreen();
			connectionStatusItem.setApiColorGreen();
			connectionStatusItem.setAuthorizationColorGray();
			connectionStatusItem
					.setIntertnetStatus(Messages.ConnectionStatusItem_Internet);
		} else if (connectionHasInternet) {
			connectionStatusItem.setInternetColorGreen();
			connectionStatusItem.setApiColorGray();
			connectionStatusItem.setAuthorizationColorGray();
			connectionStatusItem
					.setIntertnetStatus(Messages.ConnectionStatusItem_Internet);
		} else {
			connectionStatusItem.setInternetColorRed();
			connectionStatusItem.setApiColorGray();
			connectionStatusItem.setAuthorizationColorGray();
			connectionStatusItem
					.setIntertnetStatus(Messages.ConnectionStatusItem_NoInternet);
		}
		getStatusLineManager().update(true);
	}

	public void updatePricesTab() {
		pricesTab.setControl(setPricesOrder(mainTabFolder));
		pricesViewListHire.getViewer().setInput(
				EquipmentProvider.INSTANCE.getEquipments());

		pricesViewListHire.getViewer().refresh();
	}

	private void createColorResuorces() {
		JFaceResources.getColorRegistry().put(ColorRegistryKeys.ODD_ROW,
				new RGB(238, 242, 247));
		JFaceResources.getColorRegistry().put(ColorRegistryKeys.EVEN_ROW,
				new RGB(235, 241, 222));
	}

	private void createImageResources() {
		if (JFaceResources.getImageRegistry().getDescriptor(
				ImageRegistryKeys.DETAILS) == null) {
			JFaceResources.getImageRegistry().put(
					ImageRegistryKeys.DETAILS,
					new Image(getShell().getDisplay(), this.getClass()
							.getResourceAsStream("icons/details.gif"))); //$NON-NLS-1$
		}
		if (JFaceResources.getImageRegistry().getDescriptor(
				ImageRegistryKeys.CALENDAR) == null) {
			JFaceResources.getImageRegistry().put(
					ImageRegistryKeys.CALENDAR,
					new Image(getShell().getDisplay(), this.getClass()
							.getResourceAsStream("icons/calendar.png"))); //$NON-NLS-1$
		}
		if (JFaceResources.getImageRegistry().getDescriptor(
				ImageRegistryKeys.GOLFCAR) == null) {
			JFaceResources.getImageRegistry().put(
					ImageRegistryKeys.GOLFCAR,
					new Image(getShell().getDisplay(), this.getClass()
							.getResourceAsStream("icons/golfcar.png"))); //$NON-NLS-1$
		}
		if (JFaceResources.getImageRegistry().getDescriptor(
				ImageRegistryKeys.NINE_HOLES) == null) {
			JFaceResources.getImageRegistry().put(
					ImageRegistryKeys.NINE_HOLES,
					new Image(getShell().getDisplay(), this.getClass()
							.getResourceAsStream("icons/time_two.png"))); //$NON-NLS-1$
		}
		if (JFaceResources.getImageRegistry().getDescriptor(
				ImageRegistryKeys.EIGHTEEN_HOLES) == null) {
			JFaceResources.getImageRegistry().put(
					ImageRegistryKeys.EIGHTEEN_HOLES,
					new Image(getShell().getDisplay(), this.getClass()
							.getResourceAsStream("icons/time_one.png"))); //$NON-NLS-1$
		}
		if (JFaceResources.getImageRegistry().getDescriptor(
				ImageRegistryKeys.SET_OF_GOLF_CLUBS) == null) {
			JFaceResources.getImageRegistry()
					.put(ImageRegistryKeys.SET_OF_GOLF_CLUBS,
							new Image(getShell().getDisplay(), this.getClass()
									.getResourceAsStream(
											"icons/set_of_golf_clubs.png"))); //$NON-NLS-1$
		}
		if (JFaceResources.getImageRegistry().getDescriptor(
				ImageRegistryKeys.EDIT) == null) {
			JFaceResources.getImageRegistry().put(
					ImageRegistryKeys.EDIT,
					new Image(getShell().getDisplay(), this.getClass()
							.getResourceAsStream("icons/check.png"))); //$NON-NLS-1$
		}
		if (JFaceResources.getImageRegistry().getDescriptor(
				ImageRegistryKeys.DELETE) == null) {
			JFaceResources.getImageRegistry().put(
					ImageRegistryKeys.DELETE,
					new Image(getShell().getDisplay(), this.getClass()
							.getResourceAsStream("icons/no.png"))); //$NON-NLS-1$
		}
	}

	private void activateAsyncRefreshTimer(final Display display) {
		display.timerExec(ApplicationConfigValues.UPDATE_INTERVAL,
				new Runnable() {
					public void run() {
						executorService.execute(new Runnable() {
							@Override
							public void run() {
								checkConnectionStatus();
								if (!display.isDisposed()) {
									display.asyncExec(new Runnable() {

										@Override
										public void run() {
											updateConnectionStatusViewElements();
										}
									});
								}

								if (connectionHasAuth) {
									LocalStorage.getInstance().updateViaApi();
									FieldProvider.INSTANCE.refreshFields();
									OrderProvider.INSTANCE.refreshOrders();
									GameProvider.INSTANCE.refreshGames();
									UserProvider.loadUsers();
									ImageProvider.INSTANCE.refreshImages();
									EquipmentProvider.INSTANCE
											.refreshEquipments();
									// pricesViewListGame.getViewer().setInput(
									// GameProvider.INSTANCE.getGames());
									// pricesViewListGame.getViewer().refresh();
									// List<Order> orders =
									// OrderProvider.INSTANCE
									// .getOrders();
									// final List<EquipmentSetItem>
									// equipmentItems = new ArrayList<>();
									// for (Order order : orders) {
									// // Parser adds 0 for empty equipment
									// // sets.
									// // Checking
									// // id != 0
									// // here
									// if (!order.getEquipmentSet().isEmpty()
									// && order.getEquipmentSet()
									// .get(0).getId() != null) {
									// equipmentItems.addAll(order
									// .getEquipmentSet());
									// }
									// }
									//									System.out.println("Data updated!"); //$NON-NLS-1$
									Loggers.FILE_LOGGER.info("Data updated!"); //$NON-NLS-1$
									if (!display.isDisposed()) {
										display.asyncExec(new Runnable() {

											@Override
											public void run() {
												orderViewList
														.getViewer()
														.setInput(
																OrderProvider.INSTANCE
																		.getOrders());
												orderViewList.getViewer()
														.refresh();
												updatePricesTab();
												if (calendarSelected) {
													ordersTab
															.setControl(setContentCalendar(mainTabFolder));
												}
												aboutTab.setControl(new CompanyInfo(
														mainTabFolder));
												setStatus(Messages.MainWindow_this_status
														+ OrderProvider.INSTANCE
																.getNumberOfNewOrders());
												setStatus(Messages.MainWindow_this_status
														+ OrderProvider.INSTANCE
																.getNumberOfNewOrders());
											}
										});
									}
								}

							}

						});
						display.timerExec(
								ApplicationConfigValues.UPDATE_INTERVAL, this);
					}

				});
	}

}