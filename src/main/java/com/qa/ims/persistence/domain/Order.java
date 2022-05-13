package com.qa.ims.persistence.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

	private Long id;
	private Long customerId;
	private List<Item> items = new ArrayList<>();

	public Order(Long customerId) {
		this.customerId = customerId;
	}
	
	public Order(Long customerId, List<Item> items) {
		this.customerId = customerId;
		this.items = items;
	}

	public Order(Long id, Long customerId) {
		this.id = id;
		this.customerId = customerId;
	}
	
	public Order(Long id, Long customerId, List<Item> items) {
		this.id = id;
		this.customerId = customerId;
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void addItems(Item item) {
		this.items.add(item);
	}

	@Override
	public String toString() {
		List<Long> itemids = new ArrayList<>();
		Double orderprice = 0.0;
		for (Item i:this.items) {
			itemids.add(i.getId());
			orderprice += i.getPrice();
		}
		return "id:" + id + " customer id:" + customerId + " list of items:"+ itemids + " price: " + orderprice;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (getCustomerId() == null) {
			if (other.getCustomerId() != null)
				return false;
		} else if (!getCustomerId().equals(other.getCustomerId()))
			return false;
		if (getItems() == null) {
			if (other.getItems() != null)
				return false;
		} else if (!getItems().equals(other.getItems()))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



}