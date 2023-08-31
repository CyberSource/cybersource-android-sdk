package com.cybersource.inappsdk.connectors.inapp.datamodel;

import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;

/**
 * Created by fzubair on 11/18/2015.
 */
public class InAppEncryptedPayment implements InAppBaseModel {

    public final String OBJECT_NAME = "encryptedPayment";
    public final String DESCRIPTOR = "descriptor";
    public final String DATA = "data";

    public String descriptor;
    public String data;

    /**
     * All fields in constructor are required
     *
     * @param descriptor the FID
     * @param data encrypted payment data as a blob
     */
    public InAppEncryptedPayment(String descriptor, String data) {
        this.descriptor = descriptor;
        this.data = data;
    }

    @Override
    public void updateEnvelope(SDKXMLParentNode request) {
        if (validateObject()) {
            SDKXMLParentNode purchase = request.addNode(request.getNamespace(), OBJECT_NAME);
           // if (this.descriptor != null) {
           //     purchase.addTextNode(purchase.getNamespace(), DESCRIPTOR, this.descriptor);
           // }
            if (this.data != null) {
                purchase.addTextNode(null, DATA, this.data);
            }
        }
    }

    private boolean validateObject() {
        return !(this.descriptor == null && this.data == null);
    }
}
