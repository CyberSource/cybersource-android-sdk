package com.cybersource.inappsdk.connectors.inapp.datamodel;

import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;

/**
 * Created by fzubair on 11/16/2015.
 */
public class InAppBillTo implements InAppBaseModel {

    public final String OBJECT_NAME = "billTo";

    public final String FIRST_NAME = "firstName";
    public final String LAST_NAME = "lastName";
    public final String EMAIL = "email";
    public final String POSTAL_CODE = "postalCode";
    public final String STREET1 = "street1";
    public final String STREET2 = "street2";
    public final String CITY = "city";
    public final String STATE = "state";
    public final String COUNTRY = "country";

    public String firstName;
    public String lastName;
    public String email;
    public String postalCode;
    public String street1;
    public String street2;
    public String city;
    public String state;
    public String country;

    /**
     * @param firstName
     * @param lastName
     * @param email
     * @param postalCode
     * @param street1
     * @param street2
     * @param city
     * @param state
     * @param country
     */
    public InAppBillTo(String firstName, String lastName, String email,
                       String postalCode, String street1, String street2,
                       String city, String state, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
        this.email = email;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    @Override
    public void updateEnvelope(SDKXMLParentNode request) {
        SDKXMLParentNode billTo = request.addNode(request.getNamespace(), OBJECT_NAME);
        if (this.firstName != null) {
            billTo.addTextNode(billTo.getNamespace(), FIRST_NAME, this.firstName);
        }
        if (this.lastName != null) {
            billTo.addTextNode(billTo.getNamespace(), LAST_NAME, this.lastName);
        }
        if (this.street1 != null) {
            billTo.addTextNode(billTo.getNamespace(), STREET1, this.street1);
        }
        if (this.street2 != null) {
            billTo.addTextNode(billTo.getNamespace(), STREET2, this.street2);
        }
        if (this.city != null) {
            billTo.addTextNode(billTo.getNamespace(), CITY, this.city);
        }
        if (this.state != null) {
            billTo.addTextNode(billTo.getNamespace(), STATE, this.state);
        }
        if (this.postalCode != null) {
            billTo.addTextNode(billTo.getNamespace(), POSTAL_CODE, this.postalCode);
        }
        if (this.country != null) {
            billTo.addTextNode(billTo.getNamespace(), COUNTRY, this.country);
        }
        if (this.email != null) {
            billTo.addTextNode(billTo.getNamespace(), EMAIL, this.email);
        }
    }
}
