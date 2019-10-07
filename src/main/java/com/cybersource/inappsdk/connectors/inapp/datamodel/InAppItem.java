package com.cybersource.inappsdk.connectors.inapp.datamodel;

import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;

/**
 * Describes fields in "item" object in WebService request
 * 
 * @author fzubair
 */
public class InAppItem implements InAppBaseModel {

	public final String OBJECT_NAME = "item";
	public final String ID = "id";
	public final String UNIT_PRICE = "unitPrice";
	public final String QUANTITY = "quantity";
	public final String PRODUCT_CODE = "productCode";
	public final String PRODUCT_NAME = "productName";
	public final String PRODUCT_SKU = "productSKU";
	public final String TAX_AMOUNT = "taxAmount";

	public String unitPrice;
	public String quantity;
	public String id;
	public String productCode;
	public String productName;
	public String productSKU;
	public String taxAmount;

	/**
	 * @param id
	 * @param unitPrice
	 * @param quantity
	 * @param productCode
	 * @param productName
	 * @param productSKU
	 * @param taxAmount
	 */
	public InAppItem(String id, String unitPrice, String quantity, String productCode, String productName,
					 String productSKU, String taxAmount) {
		this.id = id;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.productCode = productCode;
		this.productName = productName;
		this.productSKU = productSKU;
		this.taxAmount = taxAmount;
	}

	@Override
	public void updateEnvelope(SDKXMLParentNode request) {
		if (validateObject()) {
			SDKXMLParentNode item = request.addNode(request.getNamespace(), OBJECT_NAME);
			if (this.id != null) {
				item.addAttribute(null, ID, this.id);
			}
			if (this.unitPrice != null) {
				item.addTextNode(item.getNamespace(), UNIT_PRICE, this.unitPrice);
			}
			if (this.quantity != null) {
				item.addTextNode(item.getNamespace(), QUANTITY, this.quantity);
			}
			if (this.productCode != null) {
				item.addTextNode(item.getNamespace(), PRODUCT_CODE, this.productCode);
			}
			if (this.productName != null) {
				item.addTextNode(item.getNamespace(), PRODUCT_NAME, this.productName);
			}
			if (this.productSKU != null) {
				item.addTextNode(item.getNamespace(), PRODUCT_SKU, this.productSKU);
			}
			if (this.taxAmount != null) {
				item.addTextNode(item.getNamespace(), TAX_AMOUNT, this.taxAmount);
			}
		}
	}

	private boolean validateObject() {
		return !(this.id == null && this.unitPrice == null && this.quantity == null && this.productCode == null
				&& this.productName == null && this.productSKU == null && this.taxAmount == null);
	}
}