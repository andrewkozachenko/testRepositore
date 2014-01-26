package com.academysmart.bookagolf.view.editingsupport;

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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.academysmart.bookagolf.i18n.Messages;
import com.academysmart.bookagolf.model.Equipment;
import com.academysmart.bookagolf.provider.ClubProvider;
import com.academysmart.bookagolf.provider.EquipmentProvider;
import com.academysmart.bookagolf.view.MainWindow;
import com.academysmart.bookagolf.view.listener.FloatingPointVerifyListener;

public class EquipmentUpdateEditingSupport extends EditingSupport {

	public EquipmentUpdateEditingSupport(ColumnViewer viewer) {
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
		final Equipment equipment = (Equipment) item;
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

		Composite nameComposite = new Composite(c2, SWT.NONE);
		GridLayout nameLayout = new GridLayout();
		nameLayout.numColumns = 2;
		nameComposite.setLayout(nameLayout);
		final Text nameEquipment = new Text(nameComposite, SWT.Expand
				| SWT.BORDER);
		nameEquipment.setText(equipment.getName());

		Label labelNameEquipment = new Label(nameComposite, SWT.CENTER);
		labelNameEquipment.setText(Messages.PricesViewListHire_Equipment);

		Composite priceComposite = new Composite(c2, SWT.NONE);
		GridLayout priceLayout = new GridLayout();
		priceLayout.numColumns = 3;
		priceComposite.setLayout(priceLayout);

		final Text priceEquipment = new Text(priceComposite, SWT.Expand
				| SWT.BORDER);
		priceEquipment.setText(String.valueOf(equipment.getPrice()));
		priceEquipment.addVerifyListener(new FloatingPointVerifyListener());
		priceEquipment.setTextLimit(10);
		priceEquipment.selectAll();

		// выпадающий список выбора валюты
		Combo currencyHire = new Combo(priceComposite, SWT.READ_ONLY);
		currencyHire
				.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		//		currencyHire.add("USD"); //$NON-NLS-1$
		//		currencyHire.add("UAH"); //$NON-NLS-1$
		if (ClubProvider.getClub() != null)
			currencyHire.add(ClubProvider.getClub().getCurrency());
		if (currencyHire.getItemCount() > 0) {
			currencyHire.select(0);
		}

		Label labelPriceEquipment = new Label(priceComposite, SWT.CENTER);
		labelPriceEquipment.setText(Messages.MainWindow_labelPrice_text);

		Button buttonUpdateHire = new Button(c2, SWT.NONE);
		buttonUpdateHire.setText(Messages.GameUpdateEditingSupport_update);

		// обработчик события при нажатии на кнопку "Добавить"
		buttonUpdateHire.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// empty
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				equipment.setName(nameEquipment.getText());
				equipment.setPrice(Double.parseDouble(priceEquipment.getText()));
				EquipmentProvider.INSTANCE.updateEquipment(equipment);
				MainWindow.getInstance().updatePricesTab();
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
