package com.academysmart.bookagolf.view.editingsupport;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;

import com.academysmart.bookagolf.model.OrderedGame;
import com.academysmart.bookagolf.provider.GameProvider;

public class OrderedGamePlayersEditingSupport extends EditingSupport {
	private final TableViewer viewer;

	public OrderedGamePlayersEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	protected void setValue(Object element, Object value) {
		OrderedGame orderedGame = (OrderedGame) element;
		int players = (int) value + 1;
		orderedGame.setPlayers(players);
		viewer.update(element, null);
	}

	@Override
	protected Object getValue(Object element) {
		OrderedGame orderedGame = (OrderedGame) element;
		return orderedGame.getPlayers() - 1;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		OrderedGame orderedGame = (OrderedGame) element;
		String[] playerValues = new String[GameProvider.INSTANCE.getGameById(
				orderedGame.getGameId()).getPlayers()];
		for (int i = 0; i < playerValues.length; i++) {
			playerValues[i] = String.valueOf(i + 1);
		}
		ComboBoxCellEditor cellEditor = new ComboBoxCellEditor(
				viewer.getTable(), playerValues, SWT.READ_ONLY);
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}
}
