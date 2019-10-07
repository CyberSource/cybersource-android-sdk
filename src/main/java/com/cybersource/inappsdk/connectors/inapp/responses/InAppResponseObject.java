package com.cybersource.inappsdk.connectors.inapp.responses;

import android.text.TextUtils;
import android.util.Log;

import com.cybersource.inappsdk.common.error.SDKGatewayError;
import com.cybersource.inappsdk.common.error.SDKInternalError;
import com.cybersource.inappsdk.common.utils.SDKUtils;
import com.cybersource.inappsdk.datamodel.response.SDKGatewayResponseType;
import com.cybersource.inappsdk.datamodel.response.SDKResponseDecision;
import com.cybersource.inappsdk.datamodel.response.SDKResponseReasonCode;
import com.cybersource.inappsdk.common.error.SDKError;
import com.cybersource.inappsdk.datamodel.response.SDKGatewayResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Provides parsers for responses from CyberSource Gateway and also is an Object that store parsed data.
 * 
 * Created by fzubair on 10/8/2015.
 */
public final class InAppResponseObject extends InAppResponseFields {

	private final static String EMPTY_STRING = "";

	private final static String DECISION_ACCEPT = "ACCEPT";
	private final static String DECISION_ERROR = "ERROR";
	private final static String DECISION_REJECT = "REJECT";
	private final static String DECISION_REVIEW = "REVIEW";

	// Main Fields
	public String additionalData; // ccAuthReply
	public SDKResponseDecision decision; // all
	public String invalidField; // all
	public String merchantReferenceCode; // all
	public String missingField; // all
	public String reasonCode; // all
	public String timestampCreated; // all
	public String receiptNumber; // ccAuthReply
	public String requestID; // all
	public String requestToken; // all
	public InAppIcsMessageReply icsMessage; // all

	// Objects
	public InAppCcEncryptedPaymentDataReply ccEncryptedPaymentReply;
    public InAppCcAndroidPayAuthReply ccAuthReply;
    public InAppPurchaseTotalsReply purchaseTotals;

	// Response type
	public SDKGatewayResponseType type;

	private InAppResponseObject() {
	}

    /**
     * Parses and populates a hasmpa with name as key and value as value
     * @param nvpResponseString
     * @return
     */
    private static HashMap<String, String> parseNVPResponseString(String nvpResponseString) {
        HashMap<String, String> nvpMap;
        nvpMap = new HashMap<>();
        for (String pair : nvpResponseString.split("\n")) {
            if(pair == null || (pair.compareTo("") == 0))
                continue;
            String[] components = pair.split("=");
            nvpMap.put(components[0] != null ? components[0] : "",
                    (components.length > 1) && (components[1] != null) ? components[1] : "");
        }
        return nvpMap;
    }

    /**
	 * Parse AuthorizationResponse to InAppResponseObject
	 * 
	 * @param inputStream - Stream with response that will be parsed
	 * @param type - type of response
	 * @return
	 */
	public static InAppResponseObject createAndroidPayAuthResponse(InputStream inputStream,
																  SDKGatewayResponseType type) {

		Document doc = parseResponse(inputStream);
		getResponseStringWriter(doc);

		if (doc != null) {

			InAppResponseObject result = new InAppResponseObject();
			result.type = type;

			NodeList nl = doc.getElementsByTagName(REPLY_MESSAGE);

			Element reply = (Element)(nl.item(0));
			setDefaultFields(result, reply);
			result.additionalData = getValue(reply, ADDITIONAL_DATA);
			result.receiptNumber = getValue(reply, RECEIPT_NUMBER);

			Element pTotals = (Element)(reply.getElementsByTagName(PURCHASE_TOTALS).item(0));
			if (pTotals != null) {
				result.purchaseTotals = getPurchaseTotalsReply(pTotals);
			}

			Element ccAuthReplay = (Element)(reply.getElementsByTagName(CC_AUTH_REPLY).item(0));
			if (ccAuthReplay != null) {
				result.ccAuthReply = getAndroidPayAuthReply(ccAuthReplay);
			}

			return result;
		} else {
			return null;
		}
	}

	/**
	 * Parse EncryptionResponse to InAppResponseObject
	 *
	 * @param inputStream - Stream with response that will be parsed
	 * @param type - type of response
	 * @return
	 */
	public static InAppResponseObject createEncryptionResponse(InputStream inputStream,
																  SDKGatewayResponseType type) {

		Document doc = parseResponse(inputStream);
        getResponseStringWriter(doc);

		if (doc != null) {

			InAppResponseObject result = new InAppResponseObject();
			result.type = type;

            NodeList nodeList = doc.getElementsByTagName(TIME_STAMP);
            Element reply = (Element)(nodeList.item(0));

            result.timestampCreated = getValue(reply, CREATED);

            nodeList = doc.getElementsByTagName(REPLY_MESSAGE);
			reply = (Element)(nodeList.item(0));
			setDefaultFields(result, reply);
			result.additionalData = getValue(reply, ADDITIONAL_DATA);

            result.ccEncryptedPaymentReply = getEncryptedPaymentReply(reply);

			return result;
		} else {
			return null;
		}
	}

	private static void getResponseStringWriter(Document doc) {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult streamResult = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tf.newTransformer();
			transformer.transform(domSource, streamResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Log.d("SOAP Response", writer.toString());
	}


	public static SDKError createErrorResponse(InputStream inputStream) {
		SDKError error = SDKInternalError.SDK_INTERNAL_ERROR_SERVER;
		Document doc = parseResponse(inputStream);
		if (doc != null) {
			NodeList nl = doc.getElementsByTagName(REPLY_ERROR);
			Element reply = (Element)(nl.item(0));

			String extraMessage = "";

			String faultCode = getValue(reply, ERROR_REPLY_FAULT_CODE);
			if (faultCode != null) {
				if (faultCode.contains(ERROR_CODE_INVALID_TOKEN)) {
					error = SDKGatewayError.SDK_GATEWAY_ERROR_INVALID_TOKEN;
				}
				// TODO: add the rest of faultCOde mappings

				extraMessage += faultCode + ": ";
			}
			String errorMessage = getValue(reply, ERROR_REPLY_FAULT_STRING);
			if (errorMessage != null) {
				extraMessage += errorMessage;
			}

			error.setErrorExtraMessage(extraMessage);
		}

		return error;
	}

	private static void setDefaultFields(InAppResponseObject result, Element reply) {
		result.decision = getDecisionType(getValue(reply, DECISION));
		result.invalidField = getValue(reply, INVALID_FIELD);
		result.merchantReferenceCode = getValue(reply, MERCHANT_REFERENCE_CODE);
		result.missingField = getValue(reply, MISSING_FIELD);
		result.reasonCode = getValue(reply, REASON_CODE);
		result.requestID = getValue(reply, REQUEST_ID);
		result.requestToken = getValue(reply, REQUEST_TOKEN);

		Element icsMsg = (Element)(reply.getElementsByTagName(ICS_MESSAGE).item(0));
		if (icsMsg != null) {
			result.icsMessage = getICSMessageReply(icsMsg);
		}
	}


	private static SDKResponseDecision getDecisionType(String text) {
		if (text != null) {
			if (text.equals(DECISION_ACCEPT)) {
				return SDKResponseDecision.SDK_ACCEPT;
			} else if (text.equals(DECISION_ERROR)) {
				return SDKResponseDecision.SDK_ERROR;
			} else if (text.equals(DECISION_REJECT)) {
				return SDKResponseDecision.SDK_REJECT;
			} else if (text.equals(DECISION_REVIEW)) {
				return SDKResponseDecision.SDK_REVIEW;
			} else {
				return SDKResponseDecision.SDK_UNKNOWN;
			}
		} else {
			return null;
		}
	}

    private static InAppIcsMessageReply getICSMessageReply(Element element) {
        InAppIcsMessageReply icsMessageReply = new InAppIcsMessageReply();

        icsMessageReply.icsRmsg = getValue(element, ICS_RMSG);
        icsMessageReply.icsReturnCode = getValue(element, ICS_RETURN_CODE);
        icsMessageReply.icsRFlag = getValue(element, ICS_RFLAG);

        return icsMessageReply;
    }

    private static InAppPurchaseTotalsReply getPurchaseTotalsReply(Element element) {
        InAppPurchaseTotalsReply result = new InAppPurchaseTotalsReply();
        result.currency = getValue(element, PURCHASE_TOTALS_CURRENCY);
        return result;
    }


	private static InAppCcAndroidPayAuthReply getAndroidPayAuthReply(Element element) {

		InAppCcAndroidPayAuthReply ccAuthReply = new InAppCcAndroidPayAuthReply();

		ccAuthReply.accountBalance = getValue(element, CC_AUTH_REPLY_ACCOUNT_BALANCE);
		ccAuthReply.accountBalanceCurrency = getValue(element, CC_AUTH_REPLY_ACCOUNT_BALANCE_CURRENCY);
		ccAuthReply.accountBalanceSign = getValue(element, CC_AUTH_REPLY_ACCOUNT_BALANCE_SIGN);
		ccAuthReply.affluenceIndicator = getValue(element, CC_AUTH_REPLY_AFFLUENCE_INDICATOR);
		ccAuthReply.amount = getValue(element, CC_AUTH_REPLY_AMOUNT);
		ccAuthReply.authorizationCode = getValue(element, CC_AUTH_REPLY_AUTHORIZATION_CODE);
		ccAuthReply.authorizedDateTime = getValue(element, CC_AUTH_REPLY_AUTHORIZED_DATE_TIME);
		ccAuthReply.avsCode = getValue(element, CC_AUTH_REPLY_AVS_CODE);
		ccAuthReply.avsCodeRaw = getValue(element, CC_AUTH_REPLY_AVS_CODE_RAW);
		ccAuthReply.cardCategory = getValue(element, CC_AUTH_REPLY_CARD_CATEGORY);
		ccAuthReply.cardCommercial = getValue(element, CC_AUTH_REPLY_CARD_COMMERCIAL);
		ccAuthReply.cardGroup = getValue(element, CC_AUTH_REPLY_CARD_GROUP);
		ccAuthReply.cardHealthcare = getValue(element, CC_AUTH_REPLY_CARD_HEALTHCARE);
		ccAuthReply.cardIssuerCountry = getValue(element, CC_AUTH_REPLY_CARD_ISSUER_COUNTRY);
		ccAuthReply.cardLevel3Eligible = getValue(element, CC_AUTH_REPLY_CARD_LEVEL_3_ELIGIBLE);
		ccAuthReply.cardPayroll = getValue(element, CC_AUTH_REPLY_CARD_PAYROLL);
		ccAuthReply.cardPINlessDebit = getValue(element, CC_AUTH_REPLY_CARD_PINLESS_DEBIT);
		ccAuthReply.cardPrepaid = getValue(element, CC_AUTH_REPLY_CARD_PREPAID);
		ccAuthReply.cardRegulated = getValue(element, CC_AUTH_REPLY_CARD_REGULATED);
		ccAuthReply.cardSignatureDebit = getValue(element, CC_AUTH_REPLY_CARD_SIGNATURE_DEBIT);
		ccAuthReply.cavvResponseCode = getValue(element, CC_AUTH_REPLY_CAVV_RESPONSE_CODE);
		ccAuthReply.cavvResponseCodeRaw = getValue(element, CC_AUTH_REPLY_CAVV_RESPONSE_CODE_RAW);
		ccAuthReply.cvCode = getValue(element, CC_AUTH_REPLY_CV_CODE);
		ccAuthReply.cvCodeRaw = getValue(element, CC_AUTH_REPLY_CV_CODE_RAW);
		ccAuthReply.evEmail = getValue(element, CC_AUTH_REPLY_EV_EMAIL);
		ccAuthReply.evEmailRaw = getValue(element, CC_AUTH_REPLY_EV_EMAIL_RAW);
		ccAuthReply.evName = getValue(element, CC_AUTH_REPLY_EV_NAME);
		ccAuthReply.evNameRaw = getValue(element, CC_AUTH_REPLY_EV_NAME_RAW);
		ccAuthReply.evPhoneNumber = getValue(element, CC_AUTH_REPLY_EV_PHONE_NUMBER);
		ccAuthReply.evPhoneNumberRaw = getValue(element, CC_AUTH_REPLY_EV_PHONE_NUMBER_RAW);
		ccAuthReply.evPostalCode = getValue(element, CC_AUTH_REPLY_EV_POSTAL_CODE);
		ccAuthReply.evPostalCodeRaw = getValue(element, CC_AUTH_REPLY_EV_POSTAL_CODE_RAW);
		ccAuthReply.evStreet = getValue(element, CC_AUTH_REPLY_EV_STREET);
		ccAuthReply.evStreetRaw = getValue(element, CC_AUTH_REPLY_EV_STREET_RAW);
		ccAuthReply.forwardCode = getValue(element, CC_AUTH_REPLY_FORWARD_CODE);
		ccAuthReply.merchantAdviceCode = getValue(element, CC_AUTH_REPLY_MERCHANT_ADVICE_CODE);
		ccAuthReply.merchantAdviceCodeRaw = getValue(element, CC_AUTH_REPLY_MERCHANT_ADVICE_CODE_RAW);
		ccAuthReply.ownerMerchantID = getValue(element, CC_AUTH_REPLY_OWNER_MERCHANT_ID);
		ccAuthReply.paymentNetworkTransactionID = getValue(element, CC_AUTH_REPLY_PAYMENT_NETWORK_TRANSACTION_ID);
		ccAuthReply.personalIDCode = getValue(element, CC_AUTH_REPLY_PERSONAL_ID_CODE);
		ccAuthReply.posData = getValue(element, CC_AUTH_REPLY_POS_DATE);
		ccAuthReply.processorResponse = getValue(element, CC_AUTH_REPLY_PROCESSOR_RESPONSE);
		ccAuthReply.processorTransactionID = getValue(element, CC_AUTH_REPLY_PROCESSOR_TRANSACTION_ID);
		ccAuthReply.reasonCode = getValue(element, CC_AUTH_REPLY_REASON_CODE);
		ccAuthReply.reconciliationID = getValue(element, CC_AUTH_REPLY_RECONCILATION_ID);
		ccAuthReply.referralResponseNumber = getValue(element, CC_AUTH_REPLY_REFERRAL_RESPONSE_NUMBER);
		ccAuthReply.requestAmount = getValue(element, CC_AUTH_REPLY_REQUEST_AMOUNT);
		ccAuthReply.requestCurrency = getValue(element, CC_AUTH_REPLY_REQUEST_CURRENCY);
		ccAuthReply.transactionID = getValue(element, CC_AUTH_REPLY_TRANSACTION_ID);

		return ccAuthReply;
	}

    private static InAppCcEncryptedPaymentDataReply getEncryptedPaymentReply(Element element) {

        InAppCcEncryptedPaymentDataReply ccEncryptedPaymentReply = new InAppCcEncryptedPaymentDataReply();

        Element ccEncryptedPayment = (Element)(element.getElementsByTagName(C_ENCRYPTED_PAYMENT_REPLY).item(0));
        if (ccEncryptedPayment != null) {
            ccEncryptedPaymentReply.data = getValue(ccEncryptedPayment, C_ENCRYPTED_PAYMENT_REPLY_DATA);
        }

        Element ccEncryptPaymentDataReply = (Element)(element.getElementsByTagName(C_ENCRYPTION_REPLY).item(0));
        if (ccEncryptPaymentDataReply != null) {
            ccEncryptedPaymentReply.reasonCode = getValue(ccEncryptPaymentDataReply,
                    C_ENCRYPTION_REPLY_REASON_CODE);
            ccEncryptedPaymentReply.requestDateTime = getValue(ccEncryptPaymentDataReply,
                    C_ENCRYPTION_REPLY_REQUESTED_DATE_TIME);
        }

        return ccEncryptedPaymentReply;
    }

	public SDKGatewayResponse convertToGatewayResponse() {
		if(ccAuthReply != null)
			return convertAuthorizationToGatewayResponse();
		else
			return convertEncryptionToGatewayResponse();
	}

	private SDKGatewayResponse convertAuthorizationToGatewayResponse() {
		String dateTime = null;
		if (ccAuthReply != null) {
			dateTime = ccAuthReply.authorizedDateTime;
		}

		String date = null, time = null;
		if (dateTime != null) {
			date = SDKUtils.convertToLocalDate(dateTime);
			time = SDKUtils.convertToLocalTime(dateTime);
		}

		String authCode = (ccAuthReply != null) ? ccAuthReply.authorizationCode : null;
		BigDecimal authorizedAmount = BigDecimal.ZERO;

		if (ccAuthReply != null && !TextUtils.isEmpty(ccAuthReply.amount)) {
			authorizedAmount = new BigDecimal(ccAuthReply.amount);
		}

		SDKResponseReasonCode sdkResponseReasonCode = SDKResponseReasonCode
				.getResponseReasonCodeByValueMapping(reasonCode);

		return new SDKGatewayResponse.Builder
				(type, sdkResponseReasonCode, requestID, requestToken)
				.decision(decision)
				.date(date)
				.time(time)
				.authorizationCode(authCode)
				.authorizedAmount(authorizedAmount)
				.build();
	}

    private SDKGatewayResponse convertEncryptionToGatewayResponse() {
		String dateTime = null;
        if(ccEncryptedPaymentReply != null)
			dateTime = ccEncryptedPaymentReply.requestDateTime;

        String date = null, time = null;
        if (dateTime != null) {
            date = SDKUtils.convertToLocalDate(dateTime);
            time = SDKUtils.convertToLocalTime(dateTime);
        }

        SDKResponseReasonCode sdkResponseReasonCode = SDKResponseReasonCode
                .getResponseReasonCodeByValueMapping(reasonCode);

        return new SDKGatewayResponse.Builder
                (type, sdkResponseReasonCode, requestID, requestToken)
                .decision(decision)
                .date(date)
                .time(time)
                .encryptedPaymentData(ccEncryptedPaymentReply.data)
                .build();
    }

    public SDKGatewayResponse convertNVPToGatewayResponse(InAppResponseObject result) {
        SDKResponseReasonCode sdkResponseReasonCode = SDKResponseReasonCode
                .getResponseReasonCodeByValueMapping(result.reasonCode);

		SDKGatewayResponse response = new SDKGatewayResponse.Builder
                (type, sdkResponseReasonCode, result.requestID, result.requestToken)
                .decision(result.decision)
                .build();
        return response;
    }

	private static Document parseResponse(InputStream is) {
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		Document dom = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			dom = builder.parse(is);
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		return dom;
	}

	private static String getValue(Element item, String str) {
		if (item != null) {
			NodeList n = item.getElementsByTagName(str);
			String result = getElementValue(n.item(0));
			if (result.equals(EMPTY_STRING)) {
				return null;
			} else {
				return result;
			}
		} else {
			return null;
		}
	}

	private static String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
}