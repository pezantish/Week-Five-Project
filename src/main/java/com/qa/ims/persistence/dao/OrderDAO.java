package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAO implements Dao<Order> {

	public static final Logger LOGGER = LogManager.getLogger();
	

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		List<Item> items = new ArrayList<>();
		ItemDAO itemDAO = new ItemDAO();
		Long id = resultSet.getLong("id");
		Long customerId = resultSet.getLong("customer_id");
		items.add(itemDAO.read(resultSet.getLong("item_id")));
		while(resultSet.next()) {
			items.add(itemDAO.read(resultSet.getLong("item_id")));
		}
		return new Order(id, customerId, items);
	}
	/**
	 * Reads all orders from the database
	 * 
	 * @return A list of orders
	 */
	@Override
	//ERROR
	public List<Order> readAll() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders JOIN order_items ON orders.id=order_items.order_id ORDER BY id");) {
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(modelFromResultSet(resultSet));
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Order readLatest() {
		try (Connection connection = DBUtils.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT item_id, customer_id ,MIN(ID) FROM orders JOIN order_items ON orders.id=order_items.order_id ORDER BY id");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Creates a order in the database
	 * 
	 * @param order - takes in a order object. id will be ignored
	 */
	@Override
	public Order create(Order order) {
		boolean flag = true;
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO orders(customer_id) VALUE (?)");) {
			statement.setLong(1, order.getCustomerId());
			statement.executeUpdate();

		} catch (Exception e) {
			flag = false;
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		for(Item i: order.getItems()) {	
				try (Connection connection = DBUtils.getInstance().getConnection();
						PreparedStatement statement = connection
								.prepareStatement("INSERT INTO order_items(order_id, item_id) VALUES (?, ?)");) {
					statement.setLong(1, order.getId());
					statement.setLong(2, i.getId());
					statement.executeUpdate();
				} catch (Exception e) {
					flag = false;
					LOGGER.debug(e);
					LOGGER.error(e.getMessage());
				}
			}
		if(flag) {
			return readLatest();
		}
		return null;
		}

	
	@Override
	public Order read(Long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders JOIN order_items ON order.id = order_items.order_id WHERE id = ?");) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery();) {
				resultSet.next();
				return modelFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Updates a order in the database
	 * 
	 * @param order - takes in a order object, the id field will be used to
	 *                 update that order in the database
	 * @return
	 */
	@Override
	public Order update(Order order) {
		updateIO(order);
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("UPDATE orders SET customer_id = ?? WHERE id = ?");) {
			statement.setLong(1, order.getCustomerId());
			statement.setLong(2, order.getId());
			statement.executeUpdate();
			return read(order.getId());
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		
		return null;
	}

	public void updateIO(Order order) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM order_items WHERE order_id = ?");) {
			statement.setLong(1, order.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		for(Item i: order.getItems()) {	
			try (Connection connection = DBUtils.getInstance().getConnection();
					PreparedStatement statement = connection
							.prepareStatement("INSERT INTO order_items(order_id, item_id) VALUES (?, ?)");) {
				statement.setLong(1, order.getId());
				statement.setLong(2, i.getId());
				statement.executeUpdate();
			} catch (Exception e) {
				LOGGER.debug(e);
				LOGGER.error(e.getMessage());
			}
		}
	}
	/**
	 * Deletes a order in the database
	 * 
	 * @param id - id of the order
	 */
	@Override
	public int delete(long id) {
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		try (Connection connection = DBUtils.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("DELETE FROM order_items WHERE order_id = ?");) {
			statement.setLong(1, id);
			return statement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

}