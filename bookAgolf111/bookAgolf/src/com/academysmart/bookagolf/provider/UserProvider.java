package com.academysmart.bookagolf.provider;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

import com.academysmart.bookagolf.logging.Loggers;
import com.academysmart.bookagolf.model.Order;
import com.academysmart.bookagolf.model.User;

public class UserProvider {

	private static Map<Long, User> users = new HashMap<Long, User>();

	public static void loadUsers() {
		Set<Long> userIds = new HashSet<Long>();
		List<Order> orders = OrderProvider.INSTANCE.getOrders();
		for (Order order : orders) {
			userIds.add(order.getUserId());
		}
		for (Long userId : userIds) {
			if (!users.containsKey(userId)) {
				try {
					User user = GolfApiProvider.getGolfApi().getUser(
							userId.intValue());
					users.put(userId, user);

				} catch (DataLengthException | IllegalStateException
						| InvalidCipherTextException | IOException
						| URISyntaxException e) {
					// System.out.println("Unable to load user " + userId);
					// e.printStackTrace();
					Loggers.FILE_LOGGER.error("Unable to load user " + userId,
							e);
				}
			}

		}
	}

	public static User getUser(Long userId) {
		return users.get(userId);
	}

}
