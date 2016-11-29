package main;

import java.util.HashMap;

public class SetUp {
	int stock_size, napkin_stock, knife_stock, fork_stock, spoon_stock;

	public int getNapkin_stock() {
		return napkin_stock;
	}

	public void setNapkin_stock(int napkin_stock) {
		this.napkin_stock = napkin_stock;
	}

	public int getKnife_stock() {
		return knife_stock;
	}

	public void setKnife_stock(int knife_stock) {
		this.knife_stock = knife_stock;
	}

	public int getFork_stock() {
		return fork_stock;
	}

	public void setFork_stock(int fork_stock) {
		this.fork_stock = fork_stock;
	}

	public int getSpoon_stock() {
		return spoon_stock;
	}

	public void setSpoon_stock(int spoon_stock) {
		this.spoon_stock = spoon_stock;
	}

	HashMap<String, Integer> vending_money;
	HashMap<String, Integer> vending_item;
	String input;

	public SetUp(int stock) {
		setStock_size(stock);
		setVending_item(new HashMap<>());
		setVending_money(new HashMap<>());
		setMoney();
		setItems();
		setFork_stock(20);
		setKnife_stock(20);
		setSpoon_stock(20);
		setNapkin_stock(20);
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public int getStock_size() {
		return stock_size;
	}

	public void setStock_size(int stock_size) {
		this.stock_size = stock_size;
	}

	public HashMap<String, Integer> getVending_money() {
		return vending_money;
	}

	public void setVending_money(HashMap<String, Integer> vending_money) {
		this.vending_money = vending_money;
	}

	public HashMap<String, Integer> getVending_item() {
		return vending_item;
	}

	public void setVending_item(HashMap<String, Integer> vending_item) {
		this.vending_item = vending_item;
	}

	private void setMoney() {
		getVending_money().put("a", new Integer(5));
		getVending_money().put("b", new Integer(10));
		getVending_money().put("y", new Integer(20));
	}

	private void setItems() {
		getVending_item().put("N", new Integer(5));
		getVending_item().put("K", new Integer(25));
		getVending_item().put("F", new Integer(10));
		getVending_item().put("S", new Integer(15));
	}

	public boolean reStockMachine() {
		boolean completed = false;

		return completed;

	}

}
