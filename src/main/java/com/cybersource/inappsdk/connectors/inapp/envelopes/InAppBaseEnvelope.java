package com.cybersource.inappsdk.connectors.inapp.envelopes;

import com.cybersource.inappsdk.datamodel.response.SDKGatewayResponseType;
import com.cybersource.inappsdk.soap.model.SDKXMLTextNode;
import com.cybersource.inappsdk.common.error.SDKError;
import com.cybersource.inappsdk.connectors.inapp.InAppSDKApiClient;
import com.cybersource.inappsdk.connectors.inapp.responses.InAppResponseObject;
import com.cybersource.inappsdk.soap.envelope.SDKBaseSoapEnvelope;
import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;

import java.io.InputStream;

/**
 * Created by fzubair on 10/7/2015.
 */
public abstract class InAppBaseEnvelope extends SDKBaseSoapEnvelope {
    protected final String DEFAULT_CARD_CV_INDICATOR = "1";
    protected final String DEFAULT_POS_TERMINAL_ID = null;
    protected final String DEFAULT_POS_TERMINAL_CAPABILITY = "2";

    private static final String NS_PREFIX_SOAPENV = "SOAP-ENV";

    public static final String DEFAULT_SOAP_ACTION = "runTransaction";
    //public static String DEFAULT_NAMESPACE = "urn:schemas-cybersource-com:transaction-data-1.104";
    //private static final String DEFAULT_NAMESPACE = "urn:schemas-cybersource-com:transaction-data-1.90";
    private static final String METHOD_NAME = "requestMessage";
    private static final String NVP_METHOD_NAME = "nvpRequest";

    protected static final String HEADER_NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    protected static final String HEADER_PREFIX = "wsse";
    protected static final String HEADER_SECURITY = "Security";
    protected static final String HEADER_SECURITY_MUST_UNDERSTAND = "mustUnderstand";
    protected static final String HEADER_SECURITY_MUST_UNDERSTAND_VALUE = "1";
    protected static final String HEADER_USERNAMETOKEN = "UsernameToken";
    protected static final String HEADER_USERNAME = "Username";
    protected static final String HEADER_PASSWORD = "Password";
    protected static final String HEADER_PASSWORD_TYPE = "Type";
    protected static final String HEADER_PASSWORD_TYPE_VALUE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
    protected static final String HEADER_PASSWORD_TYPE_VALUE_DIGEST = "PasswordDigest";
    // -- Faizan -- added this field to provide uniqueness for the sdk identity
    // TODO: always update this version of client library with every new version release of the SDK
    protected static final String CLIENT_LIBRARY = "InAppSDK Android v2.0.0";
    protected static final String PAYMENT_SOLUTION = "006"; // 006 for Android Pay

    public final static String DESCRIPTOR_FID = "RklEPUNPTU1PTi5BTkRST0lELklOQVBQLlBBWU1FTlQ=";

    protected String merchantId;
    protected String messageSignature;

    public abstract InAppResponseObject parseResponse(InputStream inputStream);

    public abstract SDKGatewayResponseType getResponseType();

    InAppBaseEnvelope() {
    }

    /**
     * Allows to create header to given Envelope
     *
     * @param merchantID merchant ID
     * @param messageSignature signature
     * @return
     */
    protected void createEnvelopeHeader(String merchantID, String messageSignature) {

        this.merchantId = merchantID;
        this.messageSignature = messageSignature;

        SDKXMLParentNode header = getHeader().addNode(HEADER_NAMESPACE, HEADER_SECURITY);
        header.addAttribute(header.getNamespace(), HEADER_SECURITY_MUST_UNDERSTAND,
                HEADER_SECURITY_MUST_UNDERSTAND_VALUE);
        SDKXMLParentNode userToken = header.addNode(header.getNamespace(), HEADER_USERNAMETOKEN);
        userToken.addTextNode(userToken.getNamespace(), HEADER_USERNAME, merchantId);
        SDKXMLTextNode pass = userToken.addTextNode(userToken.getNamespace(), HEADER_PASSWORD, this.messageSignature);
        pass.addAttribute(null, HEADER_PASSWORD_TYPE, HEADER_PASSWORD_TYPE_VALUE_DIGEST);
    }

    /**
     * Creates a main body element
     *
     * @return
     */
    protected SDKXMLParentNode createRequestMessage() {
        SDKXMLParentNode request = getBody().addNode(InAppSDKApiClient.DEFAULT_NAMESPACE, METHOD_NAME);
        return request;
    }

    /**
     * Creates a main body element for NVP Request
     *
     * @return
     */
    protected SDKXMLParentNode createNVPRequestMessage() {
        // -- Faizan -- changed to match xml for apple pay
        SDKXMLParentNode request = getBody().addNode(InAppSDKApiClient.DEFAULT_NAMESPACE, NVP_METHOD_NAME);
        return request;
    }

    public SDKError parseGatewayError(InputStream inputStream) {
        return InAppResponseObject.createErrorResponse(inputStream);
    }

/*    public static final Parcelable.Creator<InAppBaseEnvelope> CREATOR = new Parcelable.Creator<InAppBaseEnvelope>() {
        public InAppBaseEnvelope createFromParcel(Parcel in) {
            return new InAppBaseEnvelope(in);
        }

        public InAppBaseEnvelope[] newArray(int size) {
            return new InAppBaseEnvelope[size];
        }
    };*/


/*
    protected InAppBaseEnvelope(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(merchantId);
        dest.writeString(messageSignature);
    }

    @Override
    public void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        merchantId = in.readString();
        messageSignature = in.readString();
    }*/

}
