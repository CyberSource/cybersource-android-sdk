package com.cybersource.inappsdk.datamodel.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * Response object of gateway requests
 * 
 * Created by fzubair on 10/8/2015.
 */
public class SDKGatewayResponse implements Parcelable{

	private final SDKResponseDecision decision;
	private final SDKResponseReasonCode reasonCode;
	private final String requestId;
    private final String requestToken;
	private final SDKGatewayResponseType type;
	private final String authorizationCode;
	private final BigDecimal authorizedAmount;
	private final String date;
	private final String time;
	private final String encryptedPaymentData;

	private SDKGatewayResponse(Builder builder) {
		this.decision = builder.decision;
		this.reasonCode = builder.reasonCode;
		this.requestId = builder.requestId;
		this.requestToken = builder.requestToken;
		this.type = builder.type;
		this.authorizationCode = builder.authorizationCode;
		this.authorizedAmount = builder.authorizedAmount;
		this.date = builder.date;
		this.time = builder.time;
		this.encryptedPaymentData = builder.encryptedPaymentData;
	}

	public SDKGatewayResponseType getType() {
		return type;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public String getDate() {
		return date;
	}

	public SDKResponseDecision getDecision() {
		return decision;
	}

	public SDKResponseReasonCode getReasonCode() {
		return reasonCode;
	}

	public String getRequestId() {
		return requestId;
	}

    public String getRequestToken() {
        return requestToken;
    }

	public String getEncryptedPaymentData() {
		return encryptedPaymentData;
	}

	public String getTime() {
		return time;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	private SDKGatewayResponse(Parcel in){
		this.decision = (SDKResponseDecision) in.readSerializable();
		this.reasonCode = (SDKResponseReasonCode) in.readSerializable();
		this.requestId = in.readString();
		this.requestToken =  in.readString();
		this.type = (SDKGatewayResponseType) in.readSerializable();
		this.authorizationCode = in.readString();
		this.authorizedAmount = BigDecimal.valueOf(in.readDouble());
		this.date = in.readString();
		this.time = in.readString();
		this.encryptedPaymentData = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeSerializable(decision);
		dest.writeSerializable(reasonCode);
		dest.writeString(requestId);
		dest.writeString(requestToken);
		dest.writeSerializable(type);
		dest.writeString(authorizationCode);
		dest.writeDouble(authorizedAmount.doubleValue());
		dest.writeString(date);
		dest.writeString(time);
		dest.writeString(encryptedPaymentData);
	}

	public static final Parcelable.Creator<SDKGatewayResponse> CREATOR = new Parcelable.Creator<SDKGatewayResponse>() {

		@Override
		public SDKGatewayResponse createFromParcel(Parcel in) {
			return new SDKGatewayResponse(in);
		}

		@Override
		public SDKGatewayResponse[] newArray(int size) {
			return new SDKGatewayResponse[size];
		}
	};

	public static class Builder {
		private final SDKGatewayResponseType type;
		private final SDKResponseReasonCode reasonCode;
		private final String requestId;
		private final String requestToken;
		private SDKResponseDecision decision;
		private String authorizationCode;
		private BigDecimal authorizedAmount;
		private String date;
		private String time;
		private String encryptedPaymentData;

		public Builder(SDKGatewayResponseType type, SDKResponseReasonCode reasonCode, String requestId, String requestToken){
			this.type = type;
			this.reasonCode = reasonCode;
			this.requestId = requestId;
			this.requestToken = requestToken;
		}

		public SDKGatewayResponse.Builder time(String time) {
			this.time = time;
			return this;
		}

		public SDKGatewayResponse.Builder authorizedAmount(BigDecimal authorizedAmount) {
			this.authorizedAmount = authorizedAmount;
			return this;
		}

		public SDKGatewayResponse.Builder authorizationCode(String authorizationCode) {
			this.authorizationCode = authorizationCode;
			return this;
		}

		public SDKGatewayResponse.Builder date(String date) {
			this.date = date;
			return this;
		}

		public SDKGatewayResponse.Builder decision(SDKResponseDecision decision) {
			this.decision = decision;
			return this;
		}

        public SDKGatewayResponse.Builder encryptedPaymentData(String data) {
            this.encryptedPaymentData = data;
            return this;
        }

		public SDKGatewayResponse build(){
			return new SDKGatewayResponse(this);
		}
	}
}