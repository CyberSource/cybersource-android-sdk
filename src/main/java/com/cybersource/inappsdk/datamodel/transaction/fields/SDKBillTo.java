package com.cybersource.inappsdk.datamodel.transaction.fields;

/**
 * Created by fzubair on 11/16/2015.
 */
public final class SDKBillTo {

    //	required
    private final String firstName;
    private final String lastName;

    // optional
    private final String email;
    private final String postalCode;
    private final String street1;
    private final String street2;
    private final String city;
    private final String state;
    private final String country;

    /**
     * Creates an instance of object to store keyed card data. Also it sets a
     */
    private SDKBillTo(Builder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.postalCode = builder.postalCode;
        this.street1 = builder.street1;
        this.street2 = builder.street2;
        this.city = builder.city;
        this.state = builder.state;
        this.country = builder.country;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getState() {
        return state;
    }

    public String getStreet1() {
        return street1;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof SDKCardData)) {
            return false;
        }
        SDKBillTo other = (SDKBillTo)obj;
        if (firstName == null) {
            if (other.firstName != null) {
                return false;
            }
        } else if (!firstName.equals(other.firstName)) {
            return false;
        }
        if (lastName == null) {
            if (other.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(other.lastName)) {
            return false;
        }
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        if (postalCode == null) {
            if (other.postalCode != null) {
                return false;
            }
        } else if (!postalCode.equals(other.postalCode)) {
            return false;
        }
        return true;
    }

    public static class Builder {
        //	required
        private final String firstName;
        private final String lastName;

        // optional
        private String email;
        private String postalCode;
        private String street1;
        private String street2;
        private String city;
        private String state;
        private String country;

        // TODO: change documentation on github
        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public SDKBillTo.Builder email(String email) {
            this.email = email;
            return this;
        }

        public SDKBillTo.Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public SDKBillTo.Builder street1(String street1) {
            this.street1 = street1;
            return this;
        }

        public SDKBillTo.Builder street2(String street2) {
            this.street2 = street2;
            return this;
        }

        public SDKBillTo.Builder city(String city) {
            this.city = city;
            return this;
        }

        public SDKBillTo.Builder state(String state) {
            this.state = state;
            return this;
        }

        public SDKBillTo.Builder country(String country) {
            this.country = country;
            return this;
        }


        public SDKBillTo build(){
            return new SDKBillTo(this);
        }
    }

}
