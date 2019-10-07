package com.cybersource.inappsdk.connectors.inapp.transaction.client;

/**
 * Provides data for Encrypt transaction
 * 
 * Created by fzubair on 11/18/2015.
 */
final class InAppEncryptTransactionObject extends InAppTransaction {

	private InAppEncryptTransactionObject(Builder builder)
	{
		this.transactionType = builder.transactionType;
		this.merchantReferenceCode = builder.merchantReferenceCode;
        this.transactionTime = builder.transactionTime;
        this.transactionDate = builder.transactionDate;
        this.cardData = builder.cardData;
        this.billTo = builder.billTo;
	}

	public static class Builder extends InAppTransaction.Builder{

		public Builder(){
            this.transactionType = InAppTransactionType.IN_APP_TRANSACTION_ENCRYPTION;
            this.merchantReferenceCode = Long.toString(System.currentTimeMillis());
		}

        @Override
		public InAppEncryptTransactionObject build(){
			return new InAppEncryptTransactionObject(this);
		}
	}
}