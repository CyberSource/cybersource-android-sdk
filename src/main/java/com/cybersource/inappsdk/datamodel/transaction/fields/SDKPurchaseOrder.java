package com.cybersource.inappsdk.datamodel.transaction.fields;

import com.cybersource.inappsdk.common.SDKCurrency;
import com.cybersource.inappsdk.common.utils.SDKMathUtils;

import java.math.BigDecimal;
import java.util.List;


/**
 * This class represents purchase and contains purchase data which are required
 * for transaction.
 * 
 * Created by fzubair on 11/19/2015.
 */
public final class SDKPurchaseOrder {

	private final List<SDKLineItem> items;
	private final SDKCurrency currency;

    private BigDecimal grandTotalAmount;
    private BigDecimal subtotalAmount;
    private BigDecimal shippingAmount;
    private BigDecimal totalTax;

	private SDKPurchaseOrder(Builder builder){
		this.items = builder.items;
		this.grandTotalAmount = builder.grandTotalAmount;
		this.subtotalAmount = builder.subtotalAmount;
		this.shippingAmount = builder.shippingAmount;
		this.totalTax = builder.totalTax;
		this.currency = builder.currency;
		updateTotals();
	}

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public SDKCurrency getCurrency() {
        return currency;
    }

    public BigDecimal getGrandTotalAmount() {
        return grandTotalAmount;
    }

    public List<SDKLineItem> getLineItems() {
        return items;
    }

    public BigDecimal getSubtotalAmount() {
        return subtotalAmount;
    }


	public static class Builder{
		private List<SDKLineItem> items;
		private BigDecimal grandTotalAmount = BigDecimal.ZERO;
		private BigDecimal subtotalAmount = BigDecimal.ZERO;
        private BigDecimal shippingAmount = BigDecimal.ZERO;
		private BigDecimal totalTax = BigDecimal.ZERO;
		private SDKCurrency currency = SDKCurrency.USD;

		public Builder() {
		}

		public SDKPurchaseOrder.Builder items(List<SDKLineItem> items) {
			this.items = items;
			return this;
		}
		public SDKPurchaseOrder.Builder grandTotalAmount(BigDecimal grandTotalAmount) {
            this.grandTotalAmount = grandTotalAmount;
			return this;
		}
        public SDKPurchaseOrder.Builder subtotalAmount(BigDecimal subtotalAmount) {
            this.subtotalAmount = subtotalAmount;
            return this;
        }
        public SDKPurchaseOrder.Builder shippingAmount(BigDecimal shippingAmount) {
            this.shippingAmount = shippingAmount;
            return this;
        }
		public SDKPurchaseOrder.Builder totalTax(BigDecimal totalTax) {
			this.totalTax = totalTax;
			return this;
		}
		public SDKPurchaseOrder.Builder currency(SDKCurrency currency) {
			this.currency = currency;
			return this;
		}

		public SDKPurchaseOrder build(){
			return new SDKPurchaseOrder(this);
		}
	}

	/**
	 * Calculates <code>totalAmount</code> and <code>subtotalAmount</code>.
	 */
	public void updateTotals() {

		if(items != null) {
			for (SDKLineItem item : items) {
				subtotalAmount = SDKMathUtils.add(subtotalAmount,
						item.getLineItemsPriceForQuantity());
				totalTax = SDKMathUtils.add(totalTax, item.getTaxAmount());
			}
		}
		grandTotalAmount = subtotalAmount;

		if (shippingAmount.compareTo(BigDecimal.ZERO) > 0) {
            grandTotalAmount = SDKMathUtils.add(grandTotalAmount, shippingAmount);
		}

		if (totalTax.compareTo(BigDecimal.ZERO) > 0) {
            grandTotalAmount = SDKMathUtils.add(grandTotalAmount, totalTax);
		}
	}

}
