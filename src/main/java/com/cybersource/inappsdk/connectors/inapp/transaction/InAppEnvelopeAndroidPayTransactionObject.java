package com.cybersource.inappsdk.connectors.inapp.transaction;

import com.cybersource.inappsdk.connectors.inapp.datamodel.InAppBillTo;
import com.cybersource.inappsdk.connectors.inapp.datamodel.InAppItem;
import com.cybersource.inappsdk.connectors.inapp.datamodel.InAppPurchaseTotals;
import com.cybersource.inappsdk.connectors.inapp.services.InAppAuthService;
import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;
import com.cybersource.inappsdk.connectors.inapp.datamodel.InAppEncryptedPayment;

import java.util.List;

/**
 * Contains payment data that will be send to WebService in request body;
 * 
 * @author fzubair
 */
public class InAppEnvelopeAndroidPayTransactionObject extends InAppEnvelopeTransactionObject {

	public List<InAppItem> items;
	public InAppPurchaseTotals purchaseTotals;
    public InAppBillTo billTo;
	public InAppAuthService ccAuthService;
    private String paymentSolution;

    public final String PAYMENT_SOLUTION = "paymentSolution";

    public InAppEncryptedPayment encryptedPayment;

	/**
	 * All fields in constructor are required
	 *
	 * @param merchantId
	 * @param merchantReferenceCode
	 * @param inAppBillTo
	 * @param inAppItems
	 * @param inAppPurchaseTotals
	 * @param inAppAuthService
	 * @param paymentSolution
	 * @param clientLibrary
	 * @param encryptedPayment
	 */
	public InAppEnvelopeAndroidPayTransactionObject(String merchantId, String merchantReferenceCode,
													InAppBillTo inAppBillTo, List<InAppItem> inAppItems,
													InAppPurchaseTotals inAppPurchaseTotals,
													InAppAuthService inAppAuthService, String paymentSolution,
													String clientLibrary, InAppEncryptedPayment encryptedPayment) {
		this.merchantID = merchantId;
		this.merchantReferenceCode = merchantReferenceCode;
		this.items = inAppItems;
		this.purchaseTotals = inAppPurchaseTotals;
        this.billTo = inAppBillTo;
        this.clientLibrary = clientLibrary;
		this.ccAuthService = inAppAuthService;
        this.paymentSolution = paymentSolution;
        this.encryptedPayment = encryptedPayment;
	}

	@Override
	public void updateEnvelope(SDKXMLParentNode request) {
		createMerchantData(request);
        if (this.billTo != null) {
            this.billTo.updateEnvelope(request);
        }
		if (this.items != null) {
			for (InAppItem item : this.items) {
				item.updateEnvelope(request);
			}
		}
		if (this.purchaseTotals != null) {
			this.purchaseTotals.updateEnvelope(request);
		}
        if (this.encryptedPayment != null) {
            this.encryptedPayment.updateEnvelope(request);
        }
		if (this.ccAuthService != null) {
			this.ccAuthService.updateEnvelope(request);
		}
        if (this.paymentSolution != null) {
            request.addTextNode(request.getNamespace(), PAYMENT_SOLUTION, this.paymentSolution);
        }
	}
}