package com.academysmart.bookagolf.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import com.academysmart.bookagolf.provider.GameProvider;
import com.academysmart.bookagolf.view.PricesViewListGame;

/**
 * Is used to compare games to sort them in {@link PricesViewListGame}
 * 
 */
public class AddOrderGameComparator extends ViewerComparator {

	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public AddOrderGameComparator() {
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
		OrderedGame og1 = (OrderedGame) e1;
		OrderedGame og2 = (OrderedGame) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = Integer.compare(og1.getStart(), og2.getStart());
			if (rc == 0) {
				rc = Integer.compare(og1.getEnd(), og2.getEnd());
			}
			break;
		case 1:
			Game g1 = GameProvider.INSTANCE.getGameById(og1.getGameId());
			Game g2 = GameProvider.INSTANCE.getGameById(og2.getGameId());
			rc = Boolean.compare(g1.isMon(), g2.isMon());
			if (rc == 0) {
				rc = Boolean.compare(g1.isTue(), g2.isTue());
			}
			if (rc == 0) {
				rc = Boolean.compare(g1.isWed(), g2.isWed());
			}
			if (rc == 0) {
				rc = Boolean.compare(g1.isThu(), g2.isThu());
			}
			if (rc == 0) {
				rc = Boolean.compare(g1.isFri(), g2.isFri());
			}
			if (rc == 0) {
				rc = Boolean.compare(g1.isSat(), g2.isSat());
			}
			if (rc == 0) {
				rc = Boolean.compare(g1.isSun(), g2.isSun());
			}
			break;
		// case 2:
		// rc = Boolean.compare(g1.isTue(), g2.isTue());
		// break;
		// case 3:
		// rc = Boolean.compare(g1.isWed(), g2.isWed());
		// break;
		// case 4:
		// rc = Boolean.compare(g1.isThu(), g2.isThu());
		// break;
		// case 5:
		// rc = Boolean.compare(g1.isFri(), g2.isFri());
		// break;
		// case 6:
		// rc = Boolean.compare(g1.isSat(), g2.isSat());
		// break;
		// case 7:
		// rc = Boolean.compare(g1.isSun(), g2.isSun());
		// break;

		case 2:
			rc = GameProvider.INSTANCE.getFieldName(
					og1.getGolfFieldId().intValue()).compareTo(
					GameProvider.INSTANCE.getFieldName(og2.getGolfFieldId()
							.intValue()));
			break;
		case 3:
			rc = Integer.compare(og1.getPlayers(), og2.getPlayers());
			break;
		case 7:
			rc = Double.compare(og1.getPrice(), og2.getPrice());
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
