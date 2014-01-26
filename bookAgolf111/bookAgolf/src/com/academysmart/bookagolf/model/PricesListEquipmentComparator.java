package com.academysmart.bookagolf.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import com.academysmart.bookagolf.view.PricesViewListGame;

/**
 * Is used to compare games to sort them in {@link PricesViewListGame}
 * 
 */
public class PricesListEquipmentComparator extends ViewerComparator {

	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public PricesListEquipmentComparator() {
		this.propertyIndex = 0;
		direction = DESCENDING;
	}

	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	public void setColumn(int column) {
		if (column == this.propertyIndex) {
			// Same column as last sort; toggle the direction
			direction = 1 - direction;
		} else {
			// New column; do an ascending sort
			this.propertyIndex = column;
			direction = DESCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		Equipment eq1 = (Equipment) e1;
		Equipment eq2 = (Equipment) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = eq1.getName().compareTo(eq2.getName());
			break;
		case 1:
			rc = Double.compare(eq1.getPrice(), eq2.getPrice());
			break;
		default:
			rc = 0;
		}
		// If descending order, flip the direction
		if (direction == DESCENDING) {
			rc = -rc;
		}
		return rc;
	}

}
