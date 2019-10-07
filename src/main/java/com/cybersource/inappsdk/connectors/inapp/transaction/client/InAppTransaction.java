package com.cybersource.inappsdk.connectors.inapp.transaction.client;

import com.cybersource.inappsdk.datamodel.transaction.fields.SDKBillTo;
import com.cybersource.inappsdk.datamodel.transaction.fields.SDKCardData;
import com.cybersource.inappsdk.datamodel.transaction.fields.SDKPurchaseOrder;

/**
 * This class represents a transaction that is sent to the server.
 * 
 * Created by fzubair on 11/18/2015.
 */
public abstract class InAppTransaction {

	protected String merchantReferenceCode;
	protected InAppTransactionType transactionType;
	protected String transactionTime;
	protected String transactionDate;
	protected SDKCardData cardData;
	protected SDKBillTo billTo;
	protected String encryptedPaymentData;
	protected SDKPurchaseOrder purchaseOrder;

	InAppTransaction(Builder builder) {
	}

    InAppTransaction() {
    }

	public String getMerchantReferenceCode() {
		return merchantReferenceCode;
	}

	public final String getTransactionTime() {
		return transactionTime;
	}

	public final String getTransactionDate() {
		return transactionDate;
	}

	public SDKCardData getCardData() {
		return cardData;
	}

	public SDKBillTo getBillTo() {
		return billTo;
	}

	public SDKPurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public InAppTransactionType getTransactionType() {
		return transactionType;
	}

	public String getEncryptedPaymentData() {
		return encryptedPaymentData;
	}

	/**
	 * A factory method for creating proper transaction object.
	 * 
	 * @param type transaction type
	 * @return one of transaction objects
	 */
	public static InAppTransaction.Builder createTransactionObject(InAppTransactionType type) {

		switch (type) {
			case IN_APP_TRANSACTION_ENCRYPTION:
                return new InAppEncryptTransactionObject.Builder();
            case IN_APP_TRANSACTION_ANDROID_PAY:
                return new InAppAndroidPayTransactionObject.Builder();
            default:
                return new InAppEncryptTransactionObject.Builder();
        }
	}

    public static abstract class Builder {
		protected String merchantReferenceCode;
		protected InAppTransactionType transactionType;
		protected String transactionTime;
		protected String transactionDate;
		protected SDKCardData cardData;
        protected SDKBillTo billTo;
		protected String encryptedPaymentData;
		protected SDKPurchaseOrder purchaseOrder;

        public InAppTransaction.Builder merchantReferenceCode(String merchantReferenceCode) {
            this.merchantReferenceCode = merchantReferenceCode;
            return this;
        }

        public InAppTransaction.Builder cardData(SDKCardData cardData) {
            this.cardData = cardData;
            return this;
        }

        public InAppTransaction.Builder billTo(SDKBillTo billTo) {
            this.billTo = billTo;
            return this;
        }

		public InAppTransaction.Builder purchaseOrder(SDKPurchaseOrder purchaseOrder) {
			this.purchaseOrder = purchaseOrder;
			return this;
		}

        public InAppTransaction.Builder transactionDate(String transactionDate) {
            this.transactionDate = transactionDate;
            return this;
        }

        public InAppTransaction.Builder transactionTime(String transactionTime) {
            this.transactionTime = transactionTime;
            return this;
        }

		public InAppTransaction.Builder encryptedPaymentData(String encryptedPaymentData) {
			this.encryptedPaymentData = encryptedPaymentData;
			return this;
		}

        public abstract InAppTransaction build();

	}
}
