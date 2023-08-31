package com.cybersource.inappsdk.connectors.inapp.services;

import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;

/**
 * Describes fields of "encryptPaymentDataService" object in WebService request
 * 
 * Created by fzubair on 10/8/2015.
 */
public class InAppEncryptPaymentDataService extends InAppBaseService {

	public final String OBJECT_NAME = "encryptPaymentDataService";
	public final String RUN = "run";

	public String run;

	/**
	 * All fields in constructor are required
	 * 
	 * @param run
	 */
	public InAppEncryptPaymentDataService(boolean run, String commerceIndicator) {
		this.run = String.valueOf(run);
	}

	@Override
	public void updateEnvelope(SDKXMLParentNode request) {
		if (validateObject()) {
			SDKXMLParentNode sdkxmlParentNode = request.addNode(request.getNamespace(), OBJECT_NAME);
			if (this.run != null) {
				sdkxmlParentNode.addAttribute(null, RUN, this.run);
			}
		}
	}

	private boolean validateObject() {
		if (this.run == null) {
			return false;
		} else {
			return true;
		}
	}
}
