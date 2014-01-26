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
public class PricesListComparatorGame extends ViewerComparator {

	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public PricesListComparatorGame() {
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
		Game g1 = (Game) e1;
		Game g2 = (Game) e2;
		int rc = 0;
		switch (propertyIndex) {
		case 0:
			rc = Integer.compare(g1.getGameStart(), g2.getGameStart());
			if (rc == 0) {
				rc = Integer.compare(g1.getGameEnd(), g2.getGameEnd());
			}
			break;
		case 1:
			rc = Boolean.compare(g1.isMon(), g2.isMon());
			if(rc==0){
				rc = Boolean.compare(g1.isTue(), g2.isTue());
			}
			if(rc==0){
				rc = Boolean.compare(g1.isWed(), g2.isWed());
			}
			if(rc==0){
				rc = Boolean.compare(g1.isThu(), g2.isThu());
			}
			if(rc==0){
				rc = Boolean.compare(g1.isFri(), g2.isFri());
			}
			if(rc==0){
				rc = Boolean.compare(g1.isSat(), g2.isSat());
			}
			if(rc==0){
				rc = Boolean.compare(g1.isSun(), g2.isSun());
			}
			break;
//		case 2:
//			rc = Boolean.compare(g1.isTue(), g2.isTue());
//			break;
//		case 3:
//			rc = Boolean.compare(g1.isWed(), g2.isWed());
//			break;
//		case 4:
//			rc = Boolean.compare(g1.isThu(), g2.isThu());
//			break;
//		case 5:
//			rc = Boolean.compare(g1.isFri(), g2.isFri());
//			break;
//		case 6:
//			rc = Boolean.compare(g1.isSat(), g2.isSat());
//			break;
//		case 7:
//			rc = Boolean.compare(g1.isSun(), g2.isSun());
//			break;
			
		case 2:
			rc = GameProvider.INSTANCE.getFieldName((int) g1.getGolfFieldId())
					.compareTo(
							GameProvider.INSTANCE.getFieldName((int) g2
									.getGolfFieldId()));
			break;
		case 3:
			rc = Integer.compare(g1.getPlayers(), g2.getPlayers());
			break;
		case 7:
			rc = Double.compare(g1.getPrice(), g2.getPrice());
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
