package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Item;
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
	private ItemDAO itemDAO;
	private Utils utils;
	private List<Item> items;
	

	public OrderController(OrderDAO orderDAO, ItemDAO itemDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
		this.itemDAO = itemDAO;
		this.items = itemDAO.readAll();
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
		
		List<Item> uItems = new ArrayList<>();
		List<Long> ids = new ArrayList<>();
		LOGGER.info("Please enter  id for the order");
		Long id = utils.getLong();
		LOGGER.info("Please enter customer id for the order");
		Long customerId = utils.getLong();
		LOGGER.info("Please enter item ids for the order, or \"-1\" to finish");
		while (itemId != -1){
			itemId = utils.getLong();
			ids.add(itemId);
		};

		for (Item i:items) {
			if (ids.contains(i.getId())){
				uItems.add(i);
			}
		}
		LOGGER.info(ids);
		ids.remove(Long.valueOf(-1L));
		Order order = orderDAO.create(new Order(id, customerId, uItems));
		LOGGER.info("Order created");
		return order;
	}

	/**
	 * Updates an existing order by taking in user input
	 */
	@Override
	public Order update() {

		Long itemId = 0L;
		List<Item> uItems = new ArrayList<>();
		List<Long> ids = new ArrayList<>();
		LOGGER.info("Please enter  id for the order");
		Long id = utils.getLong();
		LOGGER.info("Please enter customer id for the order");
		Long customerId = utils.getLong();
		LOGGER.info("Please enter item ids for the order, or \"-1\" to finish");
		while (itemId != -1){
			itemId = utils.getLong();
			ids.add(itemId);
		};
		for (Item i:items) {
			if (ids.contains(i.getId())){
				uItems.add(i);
			}
		}
		LOGGER.info(ids);
		ids.remove(Long.valueOf(-1L));
		Order order = orderDAO.update(new Order(id, customerId, uItems));
		LOGGER.info("Order created");
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
