package com.academysmart.bookagolf.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.EquipmentSetItem;
import com.academysmart.bookagolf.model.Order;
import com.academysmart.bookagolf.model.OrderedGame;
import com.academysmart.bookagolf.provider.EquipmentProvider;
import com.academysmart.bookagolf.provider.FieldProvider;
import com.academysmart.bookagolf.provider.OrderProvider;
import com.academysmart.bookagolf.view.listener.FloatingPointVerifyListener;

public class AddOrderView {

	private Text paidText;
	private Combo timeStartText;
	private Combo timeEndText;
	private Combo holesCombo;
	private Combo fieldsCombo;
	private DateTime dateCombo;
	private AddOrderViewListGame pvlGame;
	private AddOrderViewListEquipment equipment;
	private Double priceGames;
	private Double priceEquips;
	private Double priceTotal;
	private Double paid;
	private Long fieldId;
	private AddOrderDateTimeFieldSelectionListener selectionListener = new AddOrderDateTimeFieldSelectionListener();
	String date;
	List<OrderedGame> orderedGames;
	List<EquipmentSetItem> equipmentSet;

	void checkOut(AddOrderViewListGame pvlGame,
			AddOrderViewListEquipment equipment) {
		priceTotal = 0.0;
		priceGames = 0.0;
		priceEquips = 0.0;
		// paid = 0.0;
		int numberGames = pvlGame.getViewer().getTable().getItemCount();
		orderedGames = new ArrayList<OrderedGame>();
		for (int i = 0; i < numberGames; i++) {
			if (pvlGame.getViewer().getTable().getItem(i).getChecked()) {
				OrderedGame game = (OrderedGame) pvlGame.getViewer().getTable()
						.getItem(i).getData();
				priceGames += game.getPrice() * game.getPlayers();
				orderedGames.add(game);
			}
		}
		int numberEquipment = equipment.getViewer().getTable().getItemCount();
		equipmentSet = new ArrayList<EquipmentSetItem>();
		for (int i = 0; i < numberEquipment; i++) {
			if (equipment.getViewer().getTable().getItem(i).getChecked()) {
				long id = ((EquipmentSetItem) equipment.getViewer().getTable()
						.getItem(i).getData()).getId();
				priceEquips += EquipmentProvider.INSTANCE
						.getEquipment((int) id).getPrice()
						* ((EquipmentSetItem) equipment.getViewer().getTable()
								.getItem(i).getData()).getAmount();
				equipmentSet.add((EquipmentSetItem) equipment.getViewer()
						.getTable().getItem(i).getData());
			}
		}

		priceTotal = priceGames + priceEquips;
	}

	public AddOrderViewListGame getPvlGame() {
		return pvlGame;
	}

	public void setPvlGame(AddOrderViewListGame pvlGame) {
		this.pvlGame = pvlGame;
	}

	public AddOrderViewListEquipment getEquipment() {
		return equipment;
	}

	public void setEquipment(AddOrderViewListEquipment equipment) {
		this.equipment = equipment;
	}

	public Double getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(Double priceTotal) {
		this.priceTotal = priceTotal;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public Shell createOrderDialog() {
		// setup the SWT window
		final Display display = Display.getCurrent();
		final Shell dialog = new Shell(display.getActiveShell(),
				SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		dialog.setLayout(new org.eclipse.swt.layout.GridLayout(1, false));

		// initialize a parent composite with a grid layout manager
		final Composite parent = new Composite(dialog, SWT.BORDER);
		parent.setLayout(new org.eclipse.swt.layout.GridLayout(2, false));

		// Date Selection as a drop-down
		dateCombo = new DateTime(parent, SWT.DATE | SWT.DROP_DOWN);
		dateCombo.addSelectionListener(selectionListener);
		Label dateLabel = new Label(parent, SWT.NONE);
		dateLabel.setText(Messages.AddOrderView_Date);

		Composite timeStartComposite = new Composite(parent, SWT.NONE);
		GridLayout timeStartLayout = new GridLayout();
		timeStartLayout.numColumns = 2;
		timeStartComposite.setLayout(timeStartLayout);
		timeStartText = new Combo(timeStartComposite, SWT.READ_ONLY);
		timeStartText
				.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		for (int i = 0; i < 24; i++) {
			timeStartText.add(String.valueOf(i));
		}
		if (timeStartText.getItemCount() > 0) {
			timeStartText.select(0);
		}
		timeStartText.addSelectionListener(selectionListener);

		Label timeStartLabel = new Label(timeStartComposite, SWT.CENTER);
		timeStartLabel.setText(Messages.MainWindow_timeStartLabel_text);

		// final Text timeEndText = new Text(filter_1, SWT.Expand | SWT.BORDER);
		// timeEndText.setTextLimit(2);
		// timeEndText.selectAll();
		Composite timeEndComposite = new Composite(parent, SWT.NONE);
		GridLayout timeEndLayout = new GridLayout();
		timeEndLayout.numColumns = 2;
		timeEndComposite.setLayout(timeEndLayout);

		timeEndText = new Combo(timeEndComposite, SWT.READ_ONLY);
		timeEndText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		for (int i = 0; i < 24; i++) {
			timeEndText.add(String.valueOf(i));
		}
		if (timeEndText.getItemCount() > 0) {
			timeEndText.select(timeEndText.getItemCount() - 1);
		}
		timeEndText.addSelectionListener(selectionListener);

		Label timeEndLabel = new Label(timeEndComposite, SWT.CENTER);
		timeEndLabel.setText(Messages.MainWindow_timeEndLabel_text);

		holesCombo = new Combo(parent, SWT.READ_ONLY);
		holesCombo.add("9"); //$NON-NLS-1$
		holesCombo.add("18"); //$NON-NLS-1$
		holesCombo.select(1);
		Label holesLabel = new Label(parent, SWT.CENTER);
		holesLabel.setText(Messages.AddOrderView_Holes);

		fieldsCombo = new Combo(parent, SWT.READ_ONLY);
		Label lblNewLabel = new Label(parent, SWT.NONE);
		lblNewLabel.setText(Messages.AddOrderView_ChooseField);
		String[] fields = FieldProvider.INSTANCE.getAllFieldNames();
		fieldsCombo.setItems(fields);
		fieldsCombo.addSelectionListener(selectionListener);
		new Label(parent, SWT.NONE);

		AddOrderViewListGame pvlGame = new AddOrderViewListGame();
		pvlGame.addImages(display);
		pvlGame.createPartControl(parent);
		pvlGame.setFocus();

		setPvlGame(pvlGame);

		AddOrderViewListEquipment equipment = new AddOrderViewListEquipment();
		equipment.createPartControl(parent);
		equipment.setFocus();
		setEquipment(equipment);

		paidText = new Text(parent, SWT.BORDER);
		paidText.setText("0.0"); //$NON-NLS-1$
		paidText.addVerifyListener(new FloatingPointVerifyListener());

		// paidText.addSelectionListener(new SelectionAdapter() {
		// public void widgetSelected(SelectionEvent event) {
		// paid = Double.parseDouble(paidText.getText().toString());
		//
		// }
		// });

		Label label = new Label(parent, SWT.NONE);
		label.setText(Messages.AddOrderView_MoneyPaid);
		new Label(parent, SWT.NONE);

		Button button = new Button(parent, SWT.NONE);
		button.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false,
				2, 1));
		button.setText(Messages.AddOrderView_AddOrder);
		button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				checkOut(getPvlGame(), getEquipment());
				Order order = createOrder();
				if (order.getOrderedGames() == null
						|| order.getOrderedGames().isEmpty()) {
					MessageDialog.openWarning(e.widget.getDisplay()
							.getActiveShell(), Messages.AddOrderView_Warning, Messages.AddOrderView_NoGameSelected);
					return;
				}
				OrderProvider.INSTANCE.addOrder(order);
				MainWindow.getInstance()
						.setStatus(
								Messages.MainWindow_this_status
										+ OrderProvider.INSTANCE
												.getNumberOfNewOrders());
				MainWindow.getInstance().updateConnectionStatus();
				dialog.close();
			}
		});

		Point dialogSize = parent.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		dialogSize.x += 25;
		dialogSize.y += 25;
		dialog.setSize(dialogSize);

		dialog.pack();
		dialog.open();

		return dialog;
	}

	public Order createOrder() {
		Order order = new Order();
		order.setClubId(ApplicationConfigValues.CLUB_ID);
		order.setUserId(ApplicationConfigValues.USER_ID);
		order.setPrice(getPriceTotal());
		order.setEquipmentPrice(priceEquips);
		for (OrderedGame orderedGame : orderedGames) {
			int timeStartSelected = Integer.parseInt(timeStartText.getText());
			if (timeStartSelected >= orderedGame.getStart()) {
				orderedGame.setStart(timeStartSelected);
			}
			int timeEndSelected = Integer.parseInt(timeEndText.getText());
			if (timeEndSelected <= orderedGame.getEnd()) {
				orderedGame.setEnd(timeEndSelected);
			}
			orderedGame.setHoles(Integer.parseInt(holesCombo.getText()));
		}
		order.setOrderedGames(orderedGames);
		order.setEquipmentSet(equipmentSet);
		paid = 0.0;
		try {
			paid = Double.parseDouble(paidText.getText());
		} catch (NumberFormatException e) {
			Loggers.FILE_LOGGER.debug("Empty paid value", e); //$NON-NLS-1$
		}
		order.setPaid(paid);

		order.setDate(getDateComboString());
		order.setStatus(Order.STATUS_PENDING);
		order.setSite(false);
		order.setCreatedAt(ApplicationConfigValues.API_DATE_TIME_FORMAT
				.format(new Date()));
		return order;
	}

	private String getDateComboString() {
		String dateString = String.valueOf(dateCombo.getYear()) + "-"; //$NON-NLS-1$
		if ((dateCombo.getMonth() + 1) < 10) {
			dateString += "0"; //$NON-NLS-1$
		}
		dateString += String.valueOf(dateCombo.getMonth() + 1) + "-"; //$NON-NLS-1$
		if (dateCombo.getDay() < 10) {
			dateString += "0"; //$NON-NLS-1$
		}
		dateString += dateCombo.getDay();
		return dateString;
	}

	private class AddOrderDateTimeFieldSelectionListener implements
			SelectionListener {

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			// empty

		}

		@Override
		public void widgetSelected(SelectionEvent event) {
			if (event.widget.equals(fieldsCombo)) {
				fieldId = FieldProvider.INSTANCE.getFields()
						.get(fieldsCombo.getSelectionIndex()).getId();
			}
			// pvlGame.selectField(fieldId);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
			try {
				Date date = format.parse(getDateComboString());
				pvlGame.selectFieldAndDateTime(fieldId, date,
						Integer.parseInt(timeStartText.getText()),
						Integer.parseInt(timeEndText.getText()));

			} catch (ParseException e) {
				Loggers.FILE_LOGGER.error("Error occured.", e); //$NON-NLS-1$
				// e.printStackTrace();
			}

		}

	}
}
