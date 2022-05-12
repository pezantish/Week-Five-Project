package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

//create (CRUD item to order_items)

/**
 * Takes in order details for CRUD functionality
 *
 */
public class OrderController implements CrudController<Order> {

	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private Utils utils;

	public OrderController(OrderDAO orderDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}

	/**
	 * Reads all orders to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		for (Order order : orders) {
			LOGGER.info(order);
		}
		return orders;
	}

	/**
	 * Creates a order by taking in user input
	 */
	@Override
	public Order create() {
		Long itemId = 0L;
		List<Long> ids = new ArrayList<>();
		LOGGER.info("Please enter  id for the order");
		Long id = utils.getLong();
		LOGGER.info("Please enter customer id for the order");
		Long customerId = utils.getLong();
		LOGGER.info("Please enter item id for the order, or \"-1\" to finish");
		while (itemId != -1){
			itemId = utils.getLong();
			ids.add(itemId);
		};
		LOGGER.info(ids);
		ids.remove(Long.valueOf(-1L));
		Order order = orderDAO.create(new Order(id, customerId, ids));
		LOGGER.info("Order created");
		return order;
	}

	/**
	 * Updates an existing order by taking in user input
	 */
	@Override
	public Order update() {
		Long itemId = 0L;
		LOGGER.info("Please enter the id of the order you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter a customer id");
		Long customerId = utils.getLong();
		Order order = orderDAO.update(new Order(id, customerId));
		LOGGER.info("Please enter item id for the order, or \"-1\" to finish");
		while (itemId != -1) {
			itemId = utils.getLong();
			order.addItems(itemId);
		};
		LOGGER.info("Customer Updated");
		return order;
	}

	/**
	 * Deletes an existing order by the id of the order
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}

}
