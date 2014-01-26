package com.academysmart.bookagolf.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import com.academysmart.bookagolf.view.OrderViewList;

/**
 * Is used to compare orders to sort them in {@link OrderViewList}
 * 
 */
public class ListComparator extends ViewerComparator {

	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public ListComparator() {
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
		Order o1 = (Order) e1;
		Order o2 = (Order) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = o1.getCreatedAt().compareTo(o2.getCreatedAt());
			break;
		case 1:
			rc = o1.getDate().compareTo(o2.getDate());
			break;
		case 2:
			rc = Long.compare(o1.getUserId(), o2.getUserId());
			break;
		case 3:
			rc = Integer.compare(o1.getPlayersNumber(), o2.getPlayersNumber());
			break;
		case 4:
			rc = Double.compare(o1.getPrice(), o2.getPrice());
			break;
		case 5:
			if (o1.getStatus() == o2.getStatus()) {
				rc = 0;
			} else
				rc = (o1.getStatus() > o2.getStatus() ? 1 : -1);
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
