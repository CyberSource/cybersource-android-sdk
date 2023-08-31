package com.cybersource.inappsdk.connectors.inapp.transaction.client;

import static com.cybersource.inappsdk.connectors.inapp.transaction.client.InAppTransactionType.IN_APP_TRANSACTION_ANDROID_PAY;

/**
 * Provides data for Android Pay transaction
 *
 * Created by fzubair on 11/18/2015.
 */
final class InAppAndroidPayTransactionObject extends InAppTransaction {

    private InAppAndroidPayTransactionObject(Builder builder)
    {
        this.transactionType = builder.transactionType;
        this.merchantReferenceCode = builder.merchantReferenceCode;
        this.transactionTime = builder.transactionTime;
        this.transactionDate = builder.transactionDate;
        this.billTo = builder.billTo;
        this.encryptedPaymentData = builder.encryptedPaymentData;
        this.purchaseOrder = builder.purchaseOrder;
    }

    public static class Builder extends InAppTransaction.Builder{

        public Builder(){
            this.transactionType = IN_APP_TRANSACTION_ANDROID_PAY;
            this.merchantReferenceCode = Long.toString(System.currentTimeMillis());
        }

        @Override
        public InAppAndroidPayTransactionObject build(){
            return new InAppAndroidPayTransactionObject(this);
        }
    }
}
