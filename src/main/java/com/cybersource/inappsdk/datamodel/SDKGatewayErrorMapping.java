package com.cybersource.inappsdk.datamodel;

import com.cybersource.inappsdk.common.error.SDKGatewayError;

/**
 * Allows to get @SDKGatewayError from error code.
 * 
 * @author fzubair
 */
public class SDKGatewayErrorMapping {
	
	private final static String GATEWAY_ERROR_PREFIX = "2";

	public static SDKGatewayError getGatewayError(String reasonCode) {
		SDKGatewayError error = SDKGatewayError.SDK_GATEWAY_ERROR_GENERAL;

		reasonCode = GATEWAY_ERROR_PREFIX + reasonCode;

		int code = 0;
		if (reasonCode != null) {
			try {
				code = Integer.parseInt(reasonCode);
			} catch (NumberFormatException e) {
				// do nothing
			}
		}
		if (code != 0) {
			error = SDKGatewayError.getGatewayErrorByErrorCode(code);
		}
		return error;
	}
}
