package com.cybersource.inappsdk.datamodel.transaction.fields;

import java.math.BigDecimal;

/**
 * Contains fields that describes basket item.
 * 
 * Created by fzubair on 11/19/2015.
 */
public final class SDKLineItem {

	private final String productName;
	private final BigDecimal unitPrice;
	private final int quantity;
	private final BigDecimal taxAmount;

	private SDKLineItem(Builder builder){
		this.productName = builder.productName;
		this.unitPrice = builder.unitPrice;
		this.quantity = builder.quantity;
		this.taxAmount = builder.taxAmount;
	}

	public String getProductName() {
		return productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public static class Builder{
		private final String productName;
		private final BigDecimal unitPrice;
		private final int quantity;
		private BigDecimal taxAmount = BigDecimal.ZERO;

		public Builder(String productName, BigDecimal unitPrice, int quantity) {
			this.productName = productName;
			this.unitPrice = unitPrice;
			this.quantity = quantity;
		}

		public SDKLineItem.Builder taxAmount(BigDecimal taxAmount) {
			if(taxAmount != null)
				this.taxAmount = taxAmount;
			return this;
		}

		public SDKLineItem build(){
			return new SDKLineItem(this);
		}
	}

	/**
	 * @return price of all items based on quantity and individual price
	 */
	public BigDecimal getLineItemsPriceForQuantity() {
		return unitPrice.multiply(new BigDecimal(quantity));
	}

}