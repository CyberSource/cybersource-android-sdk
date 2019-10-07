package com.cybersource.inappsdk.connectors.inapp.envelopes;

import com.cybersource.inappsdk.connectors.inapp.datamodel.InAppBillTo;
import com.cybersource.inappsdk.connectors.inapp.datamodel.InAppItem;
import com.cybersource.inappsdk.connectors.inapp.datamodel.InAppPurchaseTotals;
import com.cybersource.inappsdk.connectors.inapp.services.InAppAuthService;
import com.cybersource.inappsdk.connectors.inapp.transaction.client.InAppTransaction;
import com.cybersource.inappsdk.datamodel.response.SDKGatewayResponseType;
import com.cybersource.inappsdk.common.error.SDKError;
import com.cybersource.inappsdk.common.utils.SDKUtils;
import com.cybersource.inappsdk.connectors.inapp.datamodel.InAppEncryptedPayment;
import com.cybersource.inappsdk.connectors.inapp.responses.InAppResponseObject;
import com.cybersource.inappsdk.connectors.inapp.transaction.InAppEnvelopeAndroidPayTransactionObject;
import com.cybersource.inappsdk.datamodel.transaction.fields.SDKBillTo;
import com.cybersource.inappsdk.datamodel.transaction.fields.SDKLineItem;
import com.cybersource.inappsdk.datamodel.transaction.fields.SDKPurchaseOrder;
import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fzubair on 11/18/2015.
 */
public class InAppAndroidPayEnvelope extends InAppBaseEnvelope{

    public static final String PUBLIC_KEY_HASH = "publicKeyHash";
    public static final String VERSION = "version";
    public static final String VERSION_NUMBER = "1.0";
    public static final String DATA = "data";

    InAppAndroidPayEnvelope() {
    }

    public InAppAndroidPayEnvelope(InAppTransaction transactionObject, String merchantId, String messageSignature) {
        createEnvelopeHeader(merchantId, messageSignature);
        InAppEnvelopeAndroidPayTransactionObject androidPayTransactionObject = convertTransactionObject(transactionObject, merchantId);
        createEnvelopeBody(androidPayTransactionObject);
    }

    private void createEnvelopeBody(InAppEnvelopeAndroidPayTransactionObject paymentObject) {
        SDKXMLParentNode request = this.createRequestMessage();
        paymentObject.updateEnvelope(request);
    }

    private InAppEnvelopeAndroidPayTransactionObject convertTransactionObject(InAppTransaction transactionObject,
                                                                              String merchantId) {
        String merchantReferenceCode = transactionObject.getMerchantReferenceCode();

        SDKBillTo billTo = transactionObject.getBillTo();
        InAppBillTo bill = null;
        if (billTo != null) {
            bill = new InAppBillTo(billTo.getFirstName(), billTo.getLastName(),
                    billTo.getEmail(), billTo.getPostalCode(), billTo.getStreet1(),
                    billTo.getStreet2(), billTo.getCity(), billTo.getState(),
                    billTo.getCountry());
        }

        InAppAuthService inAppAuthService  = new InAppAuthService(true, false);

        SDKPurchaseOrder purchaseOrder = transactionObject.getPurchaseOrder();
        List<InAppItem> items = null;
        InAppPurchaseTotals purchaseTotals = null;
        if(purchaseOrder != null) {
            purchaseTotals = new InAppPurchaseTotals(
                    purchaseOrder.getCurrency().name(),
                    SDKUtils.getGatewayAmountStringFromBigDecimal
                            (purchaseOrder.getGrandTotalAmount())
            );
            List<SDKLineItem> lineItems = purchaseOrder.getLineItems();
            if (lineItems != null) {
                items = new ArrayList<>();
                int id = 0;
                for (SDKLineItem item : lineItems) {
                    String itemId = String.valueOf(id);

                    InAppItem newItem = new InAppItem(itemId, String.valueOf(item.getUnitPrice()),
                            String.valueOf(item.getQuantity()), null, item.getProductName(), null,
                            String.valueOf(item.getTaxAmount()));
                    items.add(newItem);
                    id++;
                }
            }
        }

        String encryptedPaymentData = transactionObject.getEncryptedPaymentData();
        // TODO: CREATE SEC BLOB OUT OF THIS ANDROID PAY BLOB
        String secBlobEncryptedPayment = createSecServiceJson(encryptedPaymentData);
        secBlobEncryptedPayment = SDKUtils.getBase64Blob(secBlobEncryptedPayment);

        InAppEncryptedPayment inAppEncryptedPayment = new InAppEncryptedPayment
                (DESCRIPTOR_FID, secBlobEncryptedPayment);

        InAppEnvelopeAndroidPayTransactionObject inAppEnvelopeAndroidPayTransactionObject =
                new InAppEnvelopeAndroidPayTransactionObject(merchantId, merchantReferenceCode,
                        bill, items, purchaseTotals, inAppAuthService, PAYMENT_SOLUTION, CLIENT_LIBRARY,
                        inAppEncryptedPayment);
        return inAppEnvelopeAndroidPayTransactionObject;
    }

    private String createSecServiceJson(String androidPayBlob){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(PUBLIC_KEY_HASH, SDKUtils.getPublicKeyHash());
            jsonObject.put(VERSION, VERSION_NUMBER);
            jsonObject.put(DATA, androidPayBlob);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }


    @Override
    protected void createEnvelopeHeader(String merchantID, String messageSignature) {
        super.createEnvelopeHeader(merchantID, messageSignature);
    }

    @Override
    public SDKError parseGatewayError(InputStream inputStream) {
        return super.parseGatewayError(inputStream);
    }

    @Override
    public InAppResponseObject parseResponse(InputStream inputStream) {
        return InAppResponseObject.createAndroidPayAuthResponse(inputStream, getResponseType());
    }

    @Override
    public SDKGatewayResponseType getResponseType() {
        return SDKGatewayResponseType.SDK_ANDROID_PAY;
    }
}
