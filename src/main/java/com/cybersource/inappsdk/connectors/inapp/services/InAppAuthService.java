package com.cybersource.inappsdk.connectors.inapp.services;

import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;

/**
 * Describes fields of "ccAuthService" object in WebService request
 * 
 * Created by fzubair on 11/18/2015.
 */
public class InAppAuthService extends InAppBaseService {

	public final String OBJECT_NAME = "ccAuthService";
	public final String RUN = "run";
	public final String PARTIAL_AUTH_INDICATOR = "partialAuthIndicator";

	public String run;
	public String partialAuthIndicator;

	/**
	 * All fields in constructor are required
	 *
	 * @param run
	 */
	public InAppAuthService(boolean run, boolean partialAuthIndicator) {
		this.run = String.valueOf(run);
		this.partialAuthIndicator = String.valueOf(partialAuthIndicator);
	}

	@Override
	public void updateEnvelope(SDKXMLParentNode request) {
		if (validateObject()) {
			SDKXMLParentNode creditService = request.addNode(request.getNamespace(), OBJECT_NAME);
			if (this.run != null) {
				creditService.addAttribute(null, RUN, this.run);
			}
			if (this.partialAuthIndicator != null) {
				creditService.addTextNode(creditService.getNamespace(), PARTIAL_AUTH_INDICATOR, partialAuthIndicator);
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
