package com.academysmart.bookagolf.view.editingsupport;

import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.model.Equipment;
import com.academysmart.bookagolf.model.Field;
import com.academysmart.bookagolf.model.Game;
import com.academysmart.bookagolf.provider.ClubProvider;
import com.academysmart.bookagolf.provider.EquipmentProvider;
import com.academysmart.bookagolf.provider.FieldProvider;
import com.academysmart.bookagolf.provider.GameProvider;
import com.academysmart.bookagolf.view.MainWindow;
import com.academysmart.bookagolf.view.listener.FloatingPointVerifyListener;

public class GameUpdateEditingSupport extends EditingSupport {

	public GameUpdateEditingSupport(ColumnViewer viewer) {
		super(viewer);
	}

	@Override
	protected boolean canEdit(Object arg0) {
		return true;
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	protected CellEditor getCellEditor(Object item) {
		final Game game = (Game) item;
		final Shell dialog = new Shell(getViewer().getControl().getShell(),
				SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		Rectangle shellBounds = getViewer().getControl().getShell().getBounds();
		dialog.setLocation(shellBounds.x + shellBounds.width / 2, shellBounds.y
				+ shellBounds.height / 2);
		dialog.setSize(400, 500);
		dialog.setText(Messages.GameUpdateEditingSupport_title);
		dialog.setLayout(new FillLayout(SWT.HORIZONTAL));

		final ScrolledComposite sc2 = new ScrolledComposite(dialog,
				SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		sc2.setExpandHorizontal(true);
		sc2.setExpandVertical(true);
		final Composite c2 = new Composite(sc2, SWT.NONE);
		sc2.setContent(c2);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		c2.setLayout(layout);
		new Label(c2, SWT.NONE);

		// "Время"
		Group timeGroup = new Group(c2, SWT.SEPARATOR | SWT.LEFT);
		timeGroup.setLayoutData(new GridData(SWT.LEFT, SWT.SEPARATOR, false,
				false, 0, 0));
		timeGroup.setLayout(new GridLayout(4, false));

		// final Text timeStartText = new Text(timeGroup, SWT.Expand |
		// SWT.BORDER);
		// timeStartText.setTextLimit(2);
		// timeStartText.setText(String.valueOf(game.getGameStart()));
		// timeStartText.selectAll();
		// Label timeStartLabel = new Label(timeGroup, SWT.CENTER);
		// timeStartLabel.setText(Messages.MainWindow_timeStartLabel_text);
		//
		// final Text timeEndText = new Text(timeGroup, SWT.Expand |
		// SWT.BORDER);
		// timeEndText.setTextLimit(2);
		// timeEndText.setText(String.valueOf(game.getGameEnd()));
		// timeEndText.selectAll();

		final Combo timeStartText = new Combo(timeGroup, SWT.READ_ONLY);
		timeStartText
				.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		for (int i = 0; i < 24; i++) {
			timeStartText.add(String.valueOf(i));
		}
		if (timeStartText.getItemCount() > 0) {
			timeStartText.select(0);
		}
		timeStartText.setText(String.valueOf(game.getGameStart()));

		Label timeStartLabel = new Label(timeGroup, SWT.CENTER);
		timeStartLabel.setText(Messages.MainWindow_timeStartLabel_text);

		// final Text timeEndText = new Text(filter_1, SWT.Expand | SWT.BORDER);
		// timeEndText.setTextLimit(2);
		// timeEndText.selectAll();

		final Combo timeEndText = new Combo(timeGroup, SWT.READ_ONLY);
		timeEndText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		for (int i = 0; i < 24; i++) {
			timeEndText.add(String.valueOf(i));
		}
		if (timeEndText.getItemCount() > 0) {
			timeEndText.select(0);
		}
		timeEndText.setText(String.valueOf(game.getGameEnd()));
		Label timeEndLabel = new Label(timeGroup, SWT.CENTER);
		timeEndLabel.setText(Messages.MainWindow_timeEndLabel_text);
		new Label(c2, SWT.NONE);

		// кнопки дней недели
		Group daysOfWeekGroup = new Group(c2, SWT.SEPARATOR | SWT.LEFT);
		daysOfWeekGroup.setLayoutData(new GridData(SWT.LEFT, SWT.SEPARATOR,
				false, false, 0, 0));
		daysOfWeekGroup.setLayout(new GridLayout(8, false));

		final Button mondayButton = new Button(daysOfWeekGroup, SWT.TOGGLE
				| SWT.BORDER);
		mondayButton.setText(Messages.MainWindow_mondayButton_text);
		mondayButton.setSelection(game.isMon());
		// ///////////////////////////////////////////////////////////////////

		final Button tuesdayButton = new Button(daysOfWeekGroup, SWT.TOGGLE
				| SWT.BORDER);
		tuesdayButton.setText(Messages.MainWindow_tuesdayButton_text);
		tuesdayButton.setSelection(game.isTue());
		// ///////////////////////////////////////////////////////////////////

		final Button wednesdayButton = new Button(daysOfWeekGroup, SWT.TOGGLE
				| SWT.BORDER);
		wednesdayButton.setText(Messages.MainWindow_wednesdayButton_text);
		wednesdayButton.setSelection(game.isWed());

		// ///////////////////////////////////////////////////////////////////

		final Button thursdayButton = new Button(daysOfWeekGroup, SWT.TOGGLE
				| SWT.BORDER);
		thursdayButton.setText(Messages.MainWindow_thursdayButton_text);
		thursdayButton.setSelection(game.isThu());
		// Слушатель при нажатии на кнопку "чт" // ///////////////////////////

		// ///////////////////////////////////////////////////////////////////
		final Button fridayButton = new Button(daysOfWeekGroup, SWT.TOGGLE
				| SWT.BORDER);
		fridayButton.setText(Messages.MainWindow_fridayButton_text);
		fridayButton.setSelection(game.isFri());
		// Слушатель при нажатии на кнопку "пт" // ///////////////////////////

		// ///////////////////////////////////////////////////////////////////
		final Button saturdayButton = new Button(daysOfWeekGroup, SWT.TOGGLE
				| SWT.BORDER);
		saturdayButton.setText(Messages.MainWindow_saturdayButton_text);
		saturdayButton.setSelection(game.isSat());
		// Слушатель при нажатии на кнопку "сб" // ///////////////////////////

		// ///////////////////////////////////////////////////////////////////
		final Button sundayButton = new Button(daysOfWeekGroup, SWT.TOGGLE
				| SWT.BORDER);
		sundayButton.setText(Messages.MainWindow_sundayButton_text);
		sundayButton.setSelection(game.isSun());
		// Слушатель при нажатии на кнопку "вс" // ///////////////////////////

		// ///////////////////////////////////////////////////////////////////

		Label Days = new Label(daysOfWeekGroup, SWT.CENTER);
		Days.setText(Messages.MainWindow_Days_text);
		new Label(c2, SWT.NONE);

		// выпадающий список выбора названия поля
		Group fieldGroup = new Group(c2, SWT.SEPARATOR | SWT.LEFT);
		fieldGroup.setLayoutData(new GridData(SWT.LEFT, SWT.SEPARATOR, false,
				false, 0, 0));
		fieldGroup.setLayout(new GridLayout(2, false));

		final Combo fieldName = new Combo(fieldGroup, SWT.READ_ONLY);
		fieldName.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		List<Field> fields = FieldProvider.INSTANCE.getFields();
		for (Field field : fields) {
			fieldName.add(ClubProvider.getClub().getName() + ", " //$NON-NLS-1$
					+ field.getName());

		}
		if (fieldName.getItemCount() > 0) {
			fieldName.select(fields.indexOf(FieldProvider.INSTANCE
					.getFieldById(game.getGolfFieldId())));
		}
		// FieldName.add("Orange Lake Resort, The Legends");
		// FieldName.add("Orange Lake Resort, The Mysteries");

		Label labelFieldName = new Label(fieldGroup, SWT.CENTER);
		labelFieldName.setText(Messages.MainWindow_labelFieldName_text);
		new Label(c2, SWT.NONE);

		// второе поле выбора атрибутов вкладки "Игра"
		Group checkBoxesGroup = new Group(c2, SWT.SEPARATOR | SWT.LEFT);
		checkBoxesGroup.setLayoutData(new GridData(SWT.LEFT, SWT.SEPARATOR,
				false, false, 0, 0));
		checkBoxesGroup.setLayout(new GridLayout(3, false));
		// Group filter_2 = new Group(c2, SWT.SEPARATOR | SWT.LEFT);
		// filter_2.setLayoutData(new GridData(SWT.LEFT, SWT.SEPARATOR, false,
		// false, 0, 0));
		// filter_2.setLayout(new GridLayout(53, false));
		// чекбокс "Гольфкар"
		List<Equipment> equipmentsAvailable = EquipmentProvider.INSTANCE
				.getEquipments();
		// Composite compositeCheckboxes = new Composite(filter_2, SWT.NONE);
		// compositeCheckboxes.setLayoutData(new GridData(SWT.LEFT,
		// SWT.SEPARATOR,
		// false, false, 0, 0));
		// compositeCheckboxes.setLayout(new GridLayout(
		// equipmentsAvailable.size() >= 3 ? 3 : equipmentsAvailable
		// .size(), false));

		for (Equipment equipment : equipmentsAvailable) {
			Button equipmentCheckBox = new Button(checkBoxesGroup, SWT.CHECK);
			equipmentCheckBox.setText(equipment.getName());
		}
		new Label(c2, SWT.NONE);
		// Button golfCar = new Button(checkBoxesGroup, SWT.CHECK);
		// golfCar.setText(Messages.MainWindow_golfCar_text);

		// чекбокс "Тележка"
		// Button trolley = new Button(checkBoxesGroup, SWT.CHECK);
		// trolley.setText(Messages.MainWindow_trolley_text);

		// чекбокс "Клюшка"
		// Button club = new Button(checkBoxesGroup, SWT.CHECK);
		// club.setText(Messages.MainWindow_club_text);

		// выпадающий список выбора количества игроков

		Group numberOfPlayersGroup = new Group(c2, SWT.SEPARATOR | SWT.LEFT);
		numberOfPlayersGroup.setLayoutData(new GridData(SWT.LEFT,
				SWT.SEPARATOR, false, false, 0, 0));
		numberOfPlayersGroup.setLayout(new GridLayout(2, false));

		final Combo numberOfPlayers = new Combo(numberOfPlayersGroup,
				SWT.READ_ONLY);
		numberOfPlayers.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL));
		numberOfPlayers.add("1"); //$NON-NLS-1$
		numberOfPlayers.add("2"); //$NON-NLS-1$
		numberOfPlayers.add("3"); //$NON-NLS-1$
		numberOfPlayers.add("4"); //$NON-NLS-1$
		// if (numberOfPlayers.getItemCount() > 0) {
		// numberOfPlayers.select(0);
		// }
		numberOfPlayers.setText(String.valueOf(game.getPlayers()));

		Label labelNumberOfPlayers = new Label(numberOfPlayersGroup, SWT.CENTER);
		labelNumberOfPlayers
				.setText(Messages.MainWindow_labelNumberOfPlayers_text);
		new Label(c2, SWT.NONE);

		// ввод количества денег
		Group priceGroup = new Group(c2, SWT.SEPARATOR | SWT.LEFT);
		priceGroup.setLayoutData(new GridData(SWT.LEFT, SWT.SEPARATOR, false,
				false, 0, 0));
		priceGroup.setLayout(new GridLayout(3, false));

		final Text price = new Text(priceGroup, SWT.Expand | SWT.BORDER);
		price.setTextLimit(10);
		price.addVerifyListener(new FloatingPointVerifyListener());
		price.setText(String.valueOf(game.getPrice()));
		price.selectAll();

		// выпадающий список выбора валюты
		Combo currency = new Combo(priceGroup, SWT.READ_ONLY);
		currency.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		//		currency.add("USD"); //$NON-NLS-1$
		//		currency.add("UAH"); //$NON-NLS-1$
		if (ClubProvider.getClub() != null)
			currency.add(ClubProvider.getClub().getCurrency());
		if (currency.getItemCount() > 0) {
			currency.select(0);
		}

		Label labelPrice = new Label(priceGroup, SWT.CENTER);
		labelPrice.setText(Messages.MainWindow_labelPrice_text);

		// кнопка "Обновить"
		Button updateButton = new Button(c2, SWT.NONE);
		updateButton.setText(Messages.GameUpdateEditingSupport_update);

		// обработчик события при нажатии на кнопку "Добавить"
		updateButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// empty
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {

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
				GameProvider.INSTANCE.updateGame(game);
				getViewer().refresh();
				MainWindow.getInstance().updateConnectionStatus();
				dialog.close();
			}
		});

		sc2.setMinSize(c2.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		Point dialogSize = sc2.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		dialogSize.x += 25;
		dialogSize.y += 25;
		dialog.setSize(dialogSize);
		dialog.open();
		return null;
	}

	@Override
	protected Object getValue(Object arg0) {
		return null;
	}

	@Override
	protected void setValue(Object arg0, Object arg1) {
		// empty

	}

}
