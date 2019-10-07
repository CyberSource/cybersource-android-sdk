package com.cybersource.inappsdk.connectors.inapp;

import android.content.Context;
import android.test.mock.MockContext;

import com.cybersource.inappsdk.common.SDKCurrency;
import com.cybersource.inappsdk.common.exceptions.SDKInvalidCardException;
import com.cybersource.inappsdk.connectors.inapp.InAppSDKApiClient;
import com.cybersource.inappsdk.connectors.inapp.transaction.client.InAppTransaction;
import com.cybersource.inappsdk.connectors.inapp.transaction.client.InAppTransactionType;
import com.cybersource.inappsdk.datamodel.transaction.fields.SDKCardData;
import com.cybersource.inappsdk.datamodel.transaction.fields.SDKPurchaseOrder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fzubair on 10/19/2015.
 */
public class InAppSDKApiClientTest {

    private final String CARD_NUMBER = "4111111111111111";
    private final String CARD_EXPIRATION_MONTH = "11";
    private final String CARD_EXPIRATION_YEAR = "2017";
    private final String CARD_ZIP = "98001";
    private final String CARD_CVV = "256";

    InAppSDKApiClient apiClient;
    Context context;
    String apiLoginID;

    @Before
    public void setUp() throws Exception {
        context = new MockContext();
        apiLoginID = "cybs_lg_sa_merchant";

        apiClient = new InAppSDKApiClient.Builder
                (context, InAppSDKApiClient.Environment.ENV_TEST, apiLoginID)
                .sdkConnectionCallback(null) // receive callbacks for connection results
                .build();
    }

    @After
    public void tearDown() throws Exception {
        context = null;
        apiClient = null;
    }

    @Test
    public void testApiClientBuilderThrowsInvalidMerchantIDException() throws Exception {
        apiLoginID = null;
        try
        {
            apiClient = new InAppSDKApiClient.Builder
                    (context, InAppSDKApiClient.Environment.ENV_TEST, apiLoginID)
                    .sdkConnectionCallback(null) // receive callbacks for connection results
                    .build();
            Assert.fail("Should have thrown Invalid Merchant ID Exception");
        }
        catch(NullPointerException e)
        {
            assertEquals("Invalid Merchant ID", e.getMessage());
        }
    }

    @Test
    public void testApiClientBuilderThrowsNullContextException() throws Exception {
        context = null;
        try
        {
            apiClient = new InAppSDKApiClient.Builder
                    (context, InAppSDKApiClient.Environment.ENV_TEST, apiLoginID)
                    .sdkConnectionCallback(null) // receive callbacks for connection results
                            // .transactionNamespace(TRANSACT_NAMESPACE) // optional - ApiClient has a default namespace too
                    .build();
            Assert.fail("Should have thrown Null Context Exception");
        }
        catch(NullPointerException e)
        {
            assertEquals("Context must not be null", e.getMessage());
        }
    }

    @Test
    public void testGetContext() throws Exception {
        assertNotNull(InAppSDKApiClient.getContext().get());
    }

    @Test
    public void testGetEnvironment() throws Exception {
        assertNotNull(apiClient.getEnvironment());
    }

/*    @Test
    public void testDispose() throws Exception {
        InAppSDKApiClient.dispose();
        assertNull(InAppGateway.getGateway());
    }*/

    @Test
    public void testConnect() throws Exception {
/*        assertTrue(apiClient.performApi(InAppSDKApiClient.Api.API_ENCRYPTION, SDKTransactionObject.
                createTransactionObject(SDKTransactionType.IN_APP_TRANSACTION_ENCRYPTION) // type of transaction object
                .merchantReferenceCode("Android_Sample_Code" + "_" + Long.toString(System.currentTimeMillis())) // you can set it to anything meaningful
                .cardData(prepareDummyCardData()) // card data to be encrypted
                .build(), "sdfskjdfs"));*/
    }

    @Test
    public void testApiClientConnectThrowsNullAPIException() throws Exception {
        try
        {
            apiClient.performApi(null, null, "DUMMY_MESSAGE_SIGNATURE");
            Assert.fail("Should have thrown Null API Exception");
        }
        catch(NullPointerException e)
        {
            assertEquals("API must not be null", e.getMessage());
        }
    }

    @Test
    public void testApiClientConnectThrowsNullTransactionObjectException() throws Exception {
        try
        {
            apiClient.performApi(InAppSDKApiClient.Api.API_ENCRYPTION, null, "DUMMY_MESSAGE_SIGNATURE");
            Assert.fail("Should have thrown Null Transaction Object Exception");
        }
        catch(NullPointerException e)
        {
            assertEquals("Transaction Object must not be null", e.getMessage());
        }
    }

    // ************* Encryption API Tests ************* //

    @Test
    public void testApiClientConnectThrowsNullCardDataException() throws Exception {
        InAppTransaction transactionObject = InAppTransaction.
                createTransactionObject(InAppTransactionType.IN_APP_TRANSACTION_ENCRYPTION) // type of transaction object
                .cardData(null) // card data to be encrypted
                .build();
        try
        {
            apiClient.performApi(InAppSDKApiClient.Api.API_ENCRYPTION, transactionObject, "DUMMY_MESSAGE_SIGNATURE");
            Assert.fail("Should have thrown Null CardData Exception");
        }
        catch(NullPointerException e)
        {
            assertEquals("Missing fields: Card Data must not be null", e.getMessage());
        }
    }

    // ************* END Encryption API Tests ************* //

    // ************* Android Pay API Tests ************* //

    @Test
    public void testApiClientConnectThrowsNullPurchaseOrderException() throws Exception {
        InAppTransaction transactionObject = InAppTransaction.
                createTransactionObject(InAppTransactionType.IN_APP_TRANSACTION_ANDROID_PAY) // type of transaction object
                .purchaseOrder(null) // purchase order
                .build();
        try
        {
            apiClient.performApi(InAppSDKApiClient.Api.API_ANDROID_PAY, transactionObject, "DUMMY_MESSAGE_SIGNATURE");
            Assert.fail("Should have thrown Null Purchase Order Exception");
        }
        catch(NullPointerException e)
        {
            assertEquals("Missing fields: Purchase Order must not be null", e.getMessage());
        }
    }

    @Test
    public void testApiClientConnectThrowsNullPublicKeyException() throws Exception {

        apiClient = new InAppSDKApiClient.Builder
                (context, InAppSDKApiClient.Environment.ENV_TEST, apiLoginID)
                .sdkConnectionCallback(null) // receive callbacks for connection results
                .publicKey(null)
                .build();

        InAppTransaction transactionObject = InAppTransaction.
                createTransactionObject(InAppTransactionType.IN_APP_TRANSACTION_ANDROID_PAY) // type of transaction object
                .purchaseOrder(preparePurchaseOrder()) // purchase order
                .build();
        try
        {
            apiClient.performApi(InAppSDKApiClient.Api.API_ANDROID_PAY, transactionObject, "DUMMY_MESSAGE_SIGNATURE");
            Assert.fail("Should have thrown Null Public Key Exception");
        }
        catch(NullPointerException e)
        {
            assertEquals("Missing fields: Public Key must not be null", e.getMessage());
        }
    }

    private SDKPurchaseOrder preparePurchaseOrder(){
        SDKPurchaseOrder purchaseOrder = new SDKPurchaseOrder.Builder()
                .currency(SDKCurrency.USD)
                .items(null)
                .build();
        return purchaseOrder;
    }

    // ************* END Android Pay API Tests ************* //

    @Test
    public void testApiClientConnectThrowsInvalidMessageSignatureException() throws Exception {
        InAppTransaction transactionObject = InAppTransaction.
                createTransactionObject(InAppTransactionType.IN_APP_TRANSACTION_ENCRYPTION) // type of transaction object
                .cardData(prepareTestCardData()) // card data to be encrypted
                .build();
        try
        {
            apiClient.performApi(InAppSDKApiClient.Api.API_ENCRYPTION, transactionObject, null);
            Assert.fail("Should have thrown Invalid Message Signature Exception");
        }
        catch(NullPointerException e)
        {
            assertEquals("Invalid Message Signature", e.getMessage());
        }
    }

    private SDKCardData prepareTestCardData() {
        SDKCardData cardData = null;
        try {
            cardData = new SDKCardData.Builder(CARD_NUMBER, CARD_EXPIRATION_MONTH,
                    CARD_EXPIRATION_YEAR)
                    .build();
        } catch (SDKInvalidCardException e) {
            // Handle exception if the card is invalid
            e.printStackTrace();
        }
        return cardData;
    }
}