package com.academysmart.bookagolf.model;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import com.academysmart.bookagolf.provider.EquipmentProvider;
import com.academysmart.bookagolf.view.PricesViewListHire;

/**
 * Is used to compare orders to sort them in {@link PricesViewListHire}
 * 
 */
public class PricesListComparatorHire extends ViewerComparator {

	private int propertyIndex;
	private static final int DESCENDING = 1;
	private int direction = DESCENDING;

	public PricesListComparatorHire() {
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
		EquipmentSetItem eq1 = (EquipmentSetItem) e1;
		EquipmentSetItem eq2 = (EquipmentSetItem) e2;
		int rc = 0;
		switch (propertyIndex) {
		// case 0:
		// rc = Integer.compare(eq1.getGameStart(), eq2.getGameStart());
		// if (rc == 0) {
		// rc = Integer.compare(eq1.getGameEnd(), eq2.getGameEnd());
		// }
		// break;
		// case 1:
		// rc = Boolean.compare(eq1.isMon(), eq2.isMon());
		// break;
		// case 2:
		// rc = Boolean.compare(eq1.isTue(), eq2.isTue());
		// break;
		// case 3:
		// rc = Boolean.compare(eq1.isWed(), eq2.isWed());
		// break;
		// case 4:
		// rc = Boolean.compare(eq1.isThu(), eq2.isThu());
		// break;
		// case 5:
		// rc = Boolean.compare(eq1.isFri(), eq2.isFri());
		// break;
		// case 6:
		// rc = Boolean.compare(eq1.isSat(), eq2.isSat());
		// break;
		// case 7:
		// rc = Boolean.compare(eq1.isSun(), eq2.isSun());
		// break;
		case 8:
			rc = EquipmentProvider.INSTANCE
					.getEquipment(eq1.getId().intValue())
					.getName()
					.compareTo(
							EquipmentProvider.INSTANCE.getEquipment(
									eq2.getId().intValue()).getName());
			break;
		case 9:
			rc = Integer.compare(eq1.getAmount(), eq2.getAmount());
			break;
		case 10:
			rc = Double.compare(
					EquipmentProvider.INSTANCE.getEquipment(
							eq1.getId().intValue()).getPrice(),
					EquipmentProvider.INSTANCE.getEquipment(
							eq2.getId().intValue()).getPrice());
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
