package com.cybersource.inappsdk.connectors.inapp.responses;

/**
 * Contains all possible fields names in every object of CyberSource Gateway
 * response.
 * 
 * Created by fzubair on 10/8/2015.
 */
public class InAppResponseFields {
	// Gateway Reply Main Element
	protected static final String REPLY_MESSAGE = "c:replyMessage";
	protected static final String NVP_REPLY = "c:nvpReply";

	// Gateway Error Reply Main Element
	protected static final String REPLY_ERROR = "soap:Fault";

	// Error message field
	protected static final String ERROR_REPLY_FAULT_CODE = "faultcode";
	protected static final String ERROR_REPLY_FAULT_STRING = "faultstring";

	// Error codes
	protected static final String ERROR_CODE_INVALID_TOKEN = "TokenInvalid";

	// Basic Fields (in comments after variables there are info in with type of
	// response field will occur)
	protected static final String ADDITIONAL_DATA = "c:additionalData"; // ccAuthReply
	protected static final String DECISION = "c:decision"; // all
	protected static final String INVALID_FIELD = "c:invalidField"; // all
	protected static final String MERCHANT_REFERENCE_CODE = "c:merchantReferenceCode"; // all
	protected static final String MISSING_FIELD = "c:missingField"; // all
	protected static final String REASON_CODE = "c:reasonCode"; // all
	protected static final String RECEIPT_NUMBER = "c:receiptNumber"; // ccAuthReply
	protected static final String REQUEST_ID = "c:requestID"; // all
	protected static final String REQUEST_TOKEN = "c:requestToken"; // all
	protected static final String ICS_MESSAGE = "ics_message"; // all
	protected static final String ICS_RMSG = "ics_rmsg"; // all
	protected static final String ICS_RETURN_CODE = "ics_return_code"; // all
	protected static final String ICS_RFLAG = "ics_rflag"; // all

	// Purchase Totals
	protected static final String PURCHASE_TOTALS = "c:purchaseTotals";
	protected static final String PURCHASE_TOTALS_CURRENCY = "c:currency"; // ccAuthReply,
	// ccAuthReversalReply,
	// ccCaptureReply,
	// ccCreditReply

	// EMV Reply
	protected static final String EMV_REPLY = "c:emvReply";
	protected static final String EMV_REPLY_COMBINED_TAGS = "c:combinedTags"; // ccAuthReply

	// Auth Reply Fields
	protected static final String CC_AUTH_REPLY = "c:ccAuthReply";
	protected static final String CC_AUTH_REPLY_ACCOUNT_BALANCE = "c:accountBalance";
	protected static final String CC_AUTH_REPLY_ACCOUNT_BALANCE_CURRENCY = "c:accountBalanceCurrency";
	protected static final String CC_AUTH_REPLY_ACCOUNT_BALANCE_SIGN = "c:accountBalanceSign";
	protected static final String CC_AUTH_REPLY_AFFLUENCE_INDICATOR = "c:affluenceIndicator";
	protected static final String CC_AUTH_REPLY_AMOUNT = "c:amount";
	protected static final String CC_AUTH_REPLY_AUTHORIZATION_CODE = "c:authorizationCode";
	protected static final String CC_AUTH_REPLY_AUTHORIZED_DATE_TIME = "c:authorizedDateTime";
	protected static final String CC_AUTH_REPLY_AVS_CODE = "c:avsCode";
	protected static final String CC_AUTH_REPLY_AVS_CODE_RAW = "c:avsCodeRaw";
	protected static final String CC_AUTH_REPLY_CARD_CATEGORY = "c:cardCategory";
	protected static final String CC_AUTH_REPLY_CARD_COMMERCIAL = "c:cardCommercial";
	protected static final String CC_AUTH_REPLY_CARD_GROUP = "c:cardGroup";
	protected static final String CC_AUTH_REPLY_CARD_HEALTHCARE = "c:cardHealthcare";
	protected static final String CC_AUTH_REPLY_CARD_ISSUER_COUNTRY = "c:cardIssuerCountry";
	protected static final String CC_AUTH_REPLY_CARD_LEVEL_3_ELIGIBLE = "c:cardLevel3Eligible";
	protected static final String CC_AUTH_REPLY_CARD_PAYROLL = "c:cardPayroll";
	protected static final String CC_AUTH_REPLY_CARD_PINLESS_DEBIT = "c:cardPINlessDebit";
	protected static final String CC_AUTH_REPLY_CARD_PREPAID = "c:cardPrepaid";
	protected static final String CC_AUTH_REPLY_CARD_REGULATED = "c:cardRegulated";
	protected static final String CC_AUTH_REPLY_CARD_SIGNATURE_DEBIT = "c:cardSignatureDebit";
	protected static final String CC_AUTH_REPLY_CAVV_RESPONSE_CODE = "c:cavvResponseCode";
	protected static final String CC_AUTH_REPLY_CAVV_RESPONSE_CODE_RAW = "c:cavvResponseCodeRaw";
	protected static final String CC_AUTH_REPLY_CV_CODE = "c:cvCode";
	protected static final String CC_AUTH_REPLY_CV_CODE_RAW = "c:cvCodeRaw";
	protected static final String CC_AUTH_REPLY_EV_EMAIL = "c:evEmail";
	protected static final String CC_AUTH_REPLY_EV_EMAIL_RAW = "c:evEmailRaw";
	protected static final String CC_AUTH_REPLY_EV_NAME = "c:evName";
	protected static final String CC_AUTH_REPLY_EV_NAME_RAW = "c:evNameRaw";
	protected static final String CC_AUTH_REPLY_EV_PHONE_NUMBER = "c:evPhoneNumber";
	protected static final String CC_AUTH_REPLY_EV_PHONE_NUMBER_RAW = "c:evPhoneNumberRaw";
	protected static final String CC_AUTH_REPLY_EV_POSTAL_CODE = "c:evPostalCode";
	protected static final String CC_AUTH_REPLY_EV_POSTAL_CODE_RAW = "c:evPostalCodeRaw";
	protected static final String CC_AUTH_REPLY_EV_STREET = "c:evStreet";
	protected static final String CC_AUTH_REPLY_EV_STREET_RAW = "c:evStreetRaw";
	protected static final String CC_AUTH_REPLY_FORWARD_CODE = "c:forwardCode";
	protected static final String CC_AUTH_REPLY_MERCHANT_ADVICE_CODE = "c:merchantAdviceCode";
	protected static final String CC_AUTH_REPLY_MERCHANT_ADVICE_CODE_RAW = "c:merchantAdviceCodeRaw";
	protected static final String CC_AUTH_REPLY_OWNER_MERCHANT_ID = "c:ownerMerchantID";
	protected static final String CC_AUTH_REPLY_PAYMENT_NETWORK_TRANSACTION_ID = "c:paymentNetworkTransactionID";
	protected static final String CC_AUTH_REPLY_PERSONAL_ID_CODE = "c:personalIDCode";
	protected static final String CC_AUTH_REPLY_POS_DATE = "c:posData";
	protected static final String CC_AUTH_REPLY_PROCESSOR_RESPONSE = "c:processorResponse";
	protected static final String CC_AUTH_REPLY_PROCESSOR_TRANSACTION_ID = "c:processorTransactionID";
	protected static final String CC_AUTH_REPLY_REASON_CODE = REASON_CODE;
	protected static final String CC_AUTH_REPLY_RECONCILATION_ID = "c:reconciliationID";
	protected static final String CC_AUTH_REPLY_REFERRAL_RESPONSE_NUMBER = "c:referralResponseNumber";
	protected static final String CC_AUTH_REPLY_REQUEST_AMOUNT = "c:requestAmount";
	protected static final String CC_AUTH_REPLY_REQUEST_CURRENCY = "c:requestCurrency";
	protected static final String CC_AUTH_REPLY_TRANSACTION_ID = "c:transactionID";

	// NVP Reply Fields
	protected static final String NVP_DECISION = "decision";
	protected static final String NVP_REASON_CODE = "reasonCode";
	protected static final String NVP_REQUEST_ID = "requestID";
	protected static final String NVP_REQUEST_TOKEN = "requestToken";
	protected static final String NVP_MERCHANT_REFERENCE_CODE = "merchantReferenceCode";

    // ENCRYPTION Reply Fields
	protected static final String C_ENCRYPTED_PAYMENT_REPLY = "c:encryptedPayment";
	protected static final String C_ENCRYPTED_PAYMENT_REPLY_DATA = "c:data";
	protected static final String C_ENCRYPTION_REPLY = "c:encryptPaymentDataReply";
	protected static final String C_ENCRYPTION_REPLY_REASON_CODE = "c:reasonCode";
	protected static final String C_ENCRYPTION_REPLY_REQUESTED_DATE_TIME = "c:requestDateTime";
	protected static final String TIME_STAMP = "wsu:Timestamp";
	protected static final String CREATED = "wsu:Created";
}
