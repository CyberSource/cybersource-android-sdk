package com.cybersource.inappsdk.connectors.inapp.transaction;

import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;

import java.util.HashMap;

/**
 * Core class for every CYBS transaction type.
 * 
 * @author fzubair
 */
public abstract class InAppEnvelopeTransactionObject {

	public final String MERCHANT_ID = "merchantID";
	public final String MERCHANT_REFERENCE_CODE = "merchantReferenceCode";
	private final String CLIENT_LIBRARY = "clientLibrary";

	protected String merchantID;
	protected String merchantReferenceCode;
	protected String clientLibrary;

	/**
	 * Updates request with fields needed in WebService request.
	 * 
	 * @param request original request that is being created to be sent to the
	 *            gateway
	 */
	public abstract void updateEnvelope(SDKXMLParentNode request);

	/**
	 * Updates request with fields needed in WebService NVP request.
	 * @param request original request that is being created to be sent to the gateway
	 * @param nvpParams the name-value pairs
	 */
	public void updateEnvelope(SDKXMLParentNode request, HashMap<String, String> nvpParams){}

	protected void createMerchantData(SDKXMLParentNode request) {
		if (this.merchantID != null) {
			request.addTextNode(request.getNamespace(), MERCHANT_ID, this.merchantID);
		}
		if (this.merchantReferenceCode != null) {
			request.addTextNode(request.getNamespace(), MERCHANT_REFERENCE_CODE, this.merchantReferenceCode);
		}
		if(this.clientLibrary != null){
			request.addTextNode(request.getNamespace(), CLIENT_LIBRARY, this.clientLibrary);
		}
	}
}