package com.jx.command;

public class BuyStock implements Order {
	
	private Stock stock;

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		stock.buy();
	}
	
	public BuyStock(Stock aStock) {
		this.stock = aStock;
	}
	

}
