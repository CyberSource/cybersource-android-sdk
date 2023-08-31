package com.cybersource.inappsdk.connectors.inapp.responses;

/**
 * Object InAppCcAuthReply stores all possible data of object
 * ccAuthReplay from gateway responses (most of fields are optional, so they
 * will be 'null', this object appears only in Authorize and Sale Methods)
 * 
 * @author fzubair
 */
	public class InAppCcAndroidPayAuthReply {

	public String accountBalance;
	public String accountBalanceCurrency;
	public String accountBalanceSign;
	public String affluenceIndicator;
	public String amount;
	public String authorizationCode;
	public String authorizedDateTime;
	public String avsCode;
	public String avsCodeRaw;
	public String cardCategory;
	public String cardCommercial;
	public String cardGroup;
	public String cardHealthcare;
	public String cardIssuerCountry;
	public String cardLevel3Eligible;
	public String cardPayroll;
	public String cardPINlessDebit;
	public String cardPrepaid;
	public String cardRegulated;
	public String cardSignatureDebit;
	public String cavvResponseCode;
	public String cavvResponseCodeRaw;
	public String cvCode;
	public String cvCodeRaw;
	public String evEmail;
	public String evEmailRaw;
	public String evName;
	public String evNameRaw;
	public String evPhoneNumber;
	public String evPhoneNumberRaw;
	public String evPostalCode;
	public String evPostalCodeRaw;
	public String evStreet;
	public String evStreetRaw;
	public String forwardCode;
	public String merchantAdviceCode;
	public String merchantAdviceCodeRaw;
	public String ownerMerchantID;
	public String paymentNetworkTransactionID;
	public String personalIDCode;
	public String posData;
	public String processorResponse;
	public String processorTransactionID;
	public String reasonCode;
	public String reconciliationID;
	public String referralResponseNumber;
	public String requestAmount;
	public String requestCurrency;
	public String transactionID;
}
