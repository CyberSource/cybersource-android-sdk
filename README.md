## :no_entry: [DEPRECATED] 

# CyberSource Android SDK

This SDK provides simple functionality to dispatch sensitive credit card data directly to CyberSource, returning a safe payment token that can be passed up to your mobile backend for standard CyberSource processing without the burden of credit card data ever hitting your server.  With this secure payment token your server can create a CyberSource subscription, long term token or payment.

**_NOTE: this SDK is not intended for AndroidPay transactions but rather is complimentary to the Google AndroidPay SDK.  The payment data blobs from this SDK and the Google AndroidPay SDK can be treated just the same for CyberSource payment processing._**

## SDK Installation

Android Studio is preferred because Eclipse will not be supported by Google much longer.

### Android Studio (or Gradle)

Add this line to your app's `build.gradle` inside the `dependencies` section as follows:

```groovy
    dependencies {
        compile 'com.cybersource:inappsdk:+'
    }
```

### Eclipse

1. Download the Android SDK jar file 'cybersource-inapp-android-x.x.x.jar' from 'cybersource.com/inapp-sdk'.
2. Include the `cybersource-inapp-android-x.x.x.jar` into the `libs` folder of your Android application project.

### Certificates
The In-App SDK expects the Android app project to have the server certificates placed in its `assets` folder under a new directory named `certificates`.

1. TEST ENVIRONMENT: Download the `.cer` file from https://mobiletest.ic3.com/mpos/transactionProcessor/
2. PRODUCTION ENVIRONMENT: Download the `.cer` file from https://mobile.ic3.com/mpos/transactionProcessor/

## SDK Usage
After the installation is succesfully complete, perform the following steps to program an Android app with this SDK.

1. To initiate requests with the SDK, create an API client that will make API requests on your behalf. The In-App SDK API client can be built as follows:

```java
// Parameters:
// 1) Context - current context
// 2) InAppSDKApiClient.Environment - CYBS ENVIRONMENT
// 3) API_LOGIN_ID String - merchant's API LOGIN ID 
apiClient = new InAppSDKApiClient.Builder (getActivity(),
                                          InAppSDKApiClient.Environment.ENV_TEST, API_LOGIN_ID) 
                                          .sdkConnectionCallback(this) // receive callbacks for connection results
                                          .sdkApiProdEndpoint(PAYMENTS_PROD_URL) // option to configure PROD Endpoint
                                          .sdkApiTestEndpoint(PAYMENTS_TEST_URL) // option to configure TEST Endpoint
                                          .sdkConnectionTimeout(5000) // optional connection time out in milliseconds
                                          .transactionNamespace(TRANSACT_NAMESPACE) // optional
                                          .build();
```

2. To make the API call, you can create a transaction object as follows:

```java
SDKTransactionObject 
  // Type of transaction object 
  .createTransactionObject(SDKTransactionType.SDK_TRANSACTION_ENCRYPTION)
  // Merchant reference code can be set to anything meaningful
  .merchantReferenceCode("Android_Sample_Code")
  // Card data to be encrypted
  .cardData(prepareTestCardData())
  // Optional billing info
  .billTo(prepareBillingInfo())
  .build();
```

3. A card object can be created as follows:

```java
SDKCardData cardData = new SDKCardData.Builder(ACCOUNT_NUMBER,
                                               EXPIRATION_MONTH, // MM
                                               EXPIRATION_YEAR) // YYYY
                                               .cvNumber(CARD_CVV) // Optional
                                               .type(SDKCardAccountNumberType.PAN) // Optional if unencrypted. If the value is set to a token then it is not optional and must be set to SDKCardType.TOKEN
                                               .build();
```

4. Billing information can be created as follows:

```java
SDKBillTo billTo = new SDKBillTo.Builder()
                .firstName("First Name")
                .lastName("Last Name")
                .postalCode("98052")
                .build();
```

5. When the API client and transaction information are ready, you can make a call to perform a specific API.

```java
// Parameters: 
// 1. InAppSDKApiClient.Api - Type of API to make a request
// 2. SDKTransactionObject - The transaction object for current transaction
// 3. Signature String - Fresh message signature for this transaction. The signature generation should always occur outside of a mobile application, for security reasons.  The sample code shows this process occurring inside the application for simplicity, but that workflow should not be used in production systems.
apiClient.performApi(InAppSDKApiClient.Api.API_ENCRYPTION, transactionObject, generateSignature(transactionObject));
```

6) To get a response back, the activity/fragment should implement the `SDKApiConnectionCallback` interface.

```java
@Override
public void onApiConnectionFinished(SDKGatewayResponse response) 
{ 
  Toast.makeText(getActivity(), 
                 response.getType() + " : " + response.getDecision().toString(),
                 Toast.LENGTH_LONG)
                 .show();
}
```
**OR**

In case of an error:

```java
@Override
public void onErrorReceived(SDKError error) 
{ 
  Toast.makeText(getActivity(), 
                 error.getErrorExtraMessage().toString(),
                 Toast.LENGTH_LONG)
                 .show();
}
```

## Sample Application
We have a sample application which demonstrates the SDK usage:  
   https://github.com/CyberSource/cybersource-android-samples
  

## Using the Payment Blob
Once you have the secure payment blob from our SDK you can post that up to your mobile backend/payment server (without incurring any additional PCI burden) and make a standard CyberSource API call.  For example, to authorize that card:  
  
````xml
<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
  <soapenv:Header>
    <wsse:Security soapenv:mustUnderstand="1" xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd">
      <wsse:UsernameToken>
        <wsse:Username>test_paymentech_001</wsse:Username>
        <wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wssusername-token-profile-1.0#PasswordText">POgqicGXdrqafap/7WzT/KcYVzuN/aW4u1cuRKawBvOg==</wsse:Password>
      </wsse:UsernameToken>
    </wsse:Security>
  </soapenv:Header>
  <soapenv:Body>
    <nvpRequest xmlns="urn:schemas-cybersource-com:transaction-data-1.121">
ccAuthService_run=true
merchantID=test_paymentech_001
merchantReferenceCode=23AEE8CB6B62EE2AF07
encryptedPayment_data=eyJkYXRhIjoiN2kxTUxjWEIxRXZoMjk2emRJdnFyY1hZXC93SG9zRE9jbXdVRE1PRG5tdk9xWXJZWXlpTXhmRzc0bTRWbndTblwvN1FEdjlYUm9kZHNxalc4aXpoTVA4cXpDOG5HVkhMa3ZES2ZVYVNqWnB3WURKS2JaYk5JNklVcE1QUjlPWmJ2NFJ5VE5tbUprcmlVU25JbFwvbVRobWprbkh1eXpwc3FFRzdVR21wcXNkOHczaG50ZStvaUpSU2pBbmtraTI1T2hmUFlqTU9CUE9BaHNFMUsxM0Npc3dNWTA4dEN5K1V3VEhuakY5MThyTFVkXC9jMnpKTW9BOEFjN042SHFPQklWa2plUVwvd25rOGkyeGNcL3lubE51bTlMMlR5RGRnRE42SHNYWjNRb3V6Z053cTZyTGFSYjU5WGpMSlVXUmRDcU5namtFTjZlUkE0Mk94NElubDlQc0RKblRpbzZ4SDVcLyIsImhlYWRlciI6eyJhcHBsaWNhdGlvbkRhdGEiOiI0Nzc1Njk2NDNEMzczMDM4MzI2MzM5MzEzNjJENjI2MjY0NjMyRDM0MzY2NTY1MkQzODM0MzEzMDJENjM2NDM1MzEzNDMxMzQzNTMzNjUzNjM1M0I0NDYxNzQ2NTU0Njk2RDY1M0QzMjMwMzEzNTJEMzEzMjJEMzEzNDU0MzIzMDNBMzMzMTNBMzAzNTJFMzEzMjMzMzAzOTMwMzI1QSIsImVwaGVtZXJhbFB1YmxpY0tleSI6Ik1Ga3dFd1lIS29aSXpqMENBUVlJS29aSXpqMERBUWNEUWdBRU80V2prRzFLYXlpbmhlRGY5QXFtRVJCeHRxMHdiaFBmS25qcUNCZ0w4RWw4NU45c3JBWVdhaEE1d29iaHFTQ0VQazc5Q0xKNGhRVXU1bENIem43ZVdBPT0iLCJwdWJsaWNLZXlIYXNoIjoieXRLSjgwc3JjWXppQnVwNzRcL0R5K3RTV1FQSENwXC9ZbkFabGhcL3lXOFk3ND0iLCJ0cmFuc2FjdGlvbklkIjoiNDUwMTI1MDYyMDA3NTAwMDAwMTUxOSJ9LCJzaWduYXR1cmVBbGdJbmZvIjoiU0hBMjU2Iiwic2lnbmF0dXJlIjoiWitjbWg4UXNzNUdQMzUrOEEzTkZOcUNNRThFbVBqb0xiKzRqUWpIVDdGMD0iLCJ2ZXJzaW9uIjoiMS4xLjEuMiJ9
paymentSolution=004
billTo_firstName=Brian
billTo_lastName=McManus
billTo_city=Bellevue
billTo_country=US
billTo_email=bmc@mail.com
billTo_state=WA
billTo_street1=213 yiui St
billTo_postalCode=98103
purchaseTotals_currency=USD
purchaseTotals_grandTotalAmount=200.23
    </nvpRequest>
  </soapenv:Body>
</soapenv:Envelope>

````

**_NOTE: You could also make an ics_create_subscription call to create a permanent card-on-file payment token, simply replace the card data fields with the encryptedPayment_data field, and don't forget to set paymentSolution to 004._**

## Google Play In-App Billing API
Google’s developer terms require that purchases related to the app, such as premium features or credits, are made via their native Google Play In-app Billing API.  See https://play.google.com/about/developer-content-policy.html for more details.
