package com.academysmart.bookagolf.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.joda.time.DateTime;

import com.academysmart.bookagolf.config.ApplicationConfigValues;
import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.provider.UserProvider;
import com.academysmart.bookagolf.view.OrderViewList;

/**
 * Is used to filter orders in {@link OrderViewList} by
 * {@link User#getFullName()}, {@link Order#getCreatedAt()},
 * {@link Order#getDate()}
 * 
 */
public class OrderFilter extends ViewerFilter {

	private String searchString;
	private Date createdDate;
	private Date gameDate;

	public void setSearchText(String s) {
		this.searchString = ".*" + s.toLowerCase() + ".*";
	}

	public void setCreatedDate(Date startDate) {
		this.createdDate = startDate;
	}

	public void setGameDate(Date endDate) {
		this.gameDate = endDate;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (((searchString == null) || (searchString.length() == 0))
				&& (createdDate == null) && (gameDate == null)) {
			return true;
		}
		// boolean isReturn = true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Order o = (Order) element;

		if ((searchString != null)
				&& (!UserProvider.getUser(o.getUserId()).getFullName()
						.toLowerCase().matches(searchString))) {
			return false;
		}

		try {
			if (createdDate != null) {
				DateTime createdDateTime = new DateTime(createdDate);
				DateTime orderCreatedDateTime = new DateTime(
						ApplicationConfigValues.API_DATE_TIME_FORMAT.parse(o
								.getCreatedAt()));
				if ((createdDateTime.getYear() != orderCreatedDateTime
						.getYear())
						|| (createdDateTime.getMonthOfYear() != orderCreatedDateTime
								.getMonthOfYear())
						|| (createdDateTime.getDayOfMonth() != orderCreatedDateTime
								.getDayOfMonth())) {
					// && (createdDate.equals(format.parse(o.getDate())))) {
					return false;
				}
			}
		} catch (ParseException e) {
			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		try {
			if (gameDate != null) {
				// && (gameDate.before(format.parse(o.getDate())))) {
				DateTime gameDateTime = new DateTime(gameDate);
				DateTime orderGameDateTime = new DateTime(format.parse(o
						.getDate()));
				if ((gameDateTime.getYear() != orderGameDateTime.getYear())
						|| (gameDateTime.getMonthOfYear() != orderGameDateTime
								.getMonthOfYear())
						|| (gameDateTime.getDayOfMonth() != orderGameDateTime
								.getDayOfMonth())) {
					return false;
				}
			}

		} catch (ParseException e) {

			// e.printStackTrace();
			Loggers.FILE_LOGGER.error("Error occured.", e);
		}
		return true;
	}

	public boolean select(Object element) {
		return select(null, null, element);
	}
}
