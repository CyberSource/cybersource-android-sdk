package com.cybersource.inappsdk.connectors.inapp;

import android.content.Context;

import com.cybersource.inappsdk.common.SDKCore;
import com.cybersource.inappsdk.common.utils.SDKUtils;
import com.cybersource.inappsdk.connectors.inapp.transaction.client.InAppTransaction;
import com.cybersource.inappsdk.connectors.inapp.connection.InAppConnectionData;
import com.cybersource.inappsdk.datamodel.transaction.callbacks.SDKApiConnectionCallback;

import java.lang.ref.WeakReference;

/**
 * Created by fzubair on 10/9/2015.
 */
public class InAppSDKApiClient {
    public static String DEFAULT_NAMESPACE = "urn:schemas-cybersource-com:transaction-data-1.120";

    private static WeakReference<Context> context;
    // end point decision for CyberSource environment
    public enum Environment {ENV_TEST, ENV_PROD};
    private final Environment environment;
    private final String merchantID;
    private String publicKey = null;
    private final SDKApiConnectionCallback connectionCallback;

    // endpoint API to be used once the 'performApi' method is invoked
    public enum Api {API_ENCRYPTION, API_ANDROID_PAY}

    private InAppSDKApiClient(Builder builder) {
        this.context = builder.context;
        SDKCore.initialize(builder.context);
        this.environment = builder.environment;
        this.merchantID = builder.merchantID;
        setGatewayMerchantID();
        this.connectionCallback = builder.connectionCallback;
        if(builder.transactionNamespace != null)
            DEFAULT_NAMESPACE = builder.transactionNamespace;
        if(builder.apiProdEndpoint != null)
            configureProdEndpoint(builder.apiProdEndpoint);
        if(builder.apiTestEndpoint != null)
            configureTestEndpoint(builder.apiTestEndpoint);
        setActiveCurrentUrl();
        SDKUtils.PUBLIC_KEY = builder.publicKey;
        this.publicKey = builder.publicKey;
        configureConnectionTimeout(builder.connectionTimeout);
    }

    public static WeakReference<Context> getContext() {
        return context;
    }

    public Environment getEnvironment() {
        return environment;
    }

    private void setActiveCurrentUrl(){
            InAppConnectionData.PAYMENTS_CURRENT_URL = (this.environment == Environment.ENV_PROD) ?
                    InAppConnectionData.PAYMENTS_PROD_URL : InAppConnectionData.PAYMENTS_TEST_URL;
    }

    private void configureProdEndpoint(String prodEndpoint){
        InAppConnectionData.PAYMENTS_PROD_URL = prodEndpoint;
    }

    private void configureTestEndpoint(String testEndpoint){
        InAppConnectionData.PAYMENTS_TEST_URL = testEndpoint;
    }

    private void configureConnectionTimeout(int timeoutMillis){
        InAppConnectionData.connectionTimeout = timeoutMillis;
    }

    private void setGatewayMerchantID(){
        InAppGateway.getGateway().setMerchantID(merchantID);
    }

    public static void dispose() {
        InAppGateway.dispose();
    }

    public boolean performApi(Api api, InAppTransaction transactionObject, String messageSignature){
        if(api == null)
            throw new NullPointerException("API must not be null");
        if(transactionObject == null)
            throw new NullPointerException("Transaction Object must not be null");
        if(messageSignature == null || messageSignature.isEmpty())
            throw new NullPointerException("Invalid Message Signature");

        InAppGateway.getGateway().setMessageSignature(messageSignature);
        switch (api){
            case API_ENCRYPTION:
                if(transactionObject.getCardData() == null)
                    throw new NullPointerException("Missing fields: Card Data must not be null");
                return InAppGateway.getGateway().performEncryption(transactionObject, this.connectionCallback);
            case API_ANDROID_PAY:
                if(transactionObject.getPurchaseOrder() == null)
                    throw new NullPointerException("Missing fields: Purchase Order must not be null");
                if(this.publicKey == null)
                    throw new NullPointerException("Missing fields: Public Key must not be null");
                return InAppGateway.getGateway().performAndroidPayTransaction(transactionObject,
                        this.connectionCallback);
            default:
                return false;
        }
    }

    public static class Builder {
        private final WeakReference<Context> context;
        private final Environment environment;
        private final String merchantID;
        private SDKApiConnectionCallback connectionCallback;
        private String transactionNamespace = null;
        private String apiProdEndpoint = null;
        private String apiTestEndpoint = null;
        private String publicKey = null;
        private int connectionTimeout;

        public Builder(Context context, Environment environment, String merchantID){
            if(context == null)
                throw new NullPointerException("Context must not be null");
            if(merchantID == null || merchantID.isEmpty())
                throw new NullPointerException("Invalid Merchant ID");
            this.context = new WeakReference<>(context);
            this.environment = (environment == null) ? Environment.ENV_TEST : environment;
            this.merchantID = merchantID;
        }

        public InAppSDKApiClient.Builder transactionNamespace(String transactionNamespace) {
            this.transactionNamespace = transactionNamespace;
            return this;
        }

        public InAppSDKApiClient.Builder sdkConnectionCallback(SDKApiConnectionCallback connectionCallback) {
            this.connectionCallback = connectionCallback;
            return this;
        }

        public InAppSDKApiClient.Builder sdkApiProdEndpoint(String apiProdEndpoint) {
            this.apiProdEndpoint = apiProdEndpoint;
            return this;
        }

        public InAppSDKApiClient.Builder sdkApiTestEndpoint(String apiTestEndpoint) {
            this.apiTestEndpoint = apiTestEndpoint;
            return this;
        }

        public InAppSDKApiClient.Builder publicKey(String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public InAppSDKApiClient.Builder sdkConnectionTimeout(int timeoutMillis) {
            this.connectionTimeout = timeoutMillis;
            return this;
        }

        public InAppSDKApiClient build(){
            return new InAppSDKApiClient(this);
        }
    }
}
