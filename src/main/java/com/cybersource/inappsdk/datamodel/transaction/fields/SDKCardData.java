package com.cybersource.inappsdk.datamodel.transaction.fields;

import com.cybersource.inappsdk.common.utils.SDKCardUtils;
import com.cybersource.inappsdk.common.exceptions.SDKInvalidCardException;

/**
 * This class represents the manual card entry.
 * 
 * Created by fzubair on 10/8/2015.
 */
public final class SDKCardData{

	//	required
	private String accountNumber;
	private String expirationMonth;
	private String expirationYear;

	// optional
	private final String cvNumber;
    private final String lastFourDigits;
    private final SDKCardAccountNumberType cardAccountNumberType;
/*	private final String zip;
	private final SDKCardBrandType cardBrandType;
	private final SDKCardFundingType fundingType;
	private final SDKCardTokenizationMethod tokenizationMethod;*/

	/**
	 * Creates an instance of object to store keyed card data. Also it sets a
	 */
	private SDKCardData(Builder builder) throws SDKInvalidCardException{
        this.cardAccountNumberType = builder.cardAccountNumberType != null ? builder.cardAccountNumberType : SDKCardAccountNumberType.PAN;
        if(this.cardAccountNumberType == SDKCardAccountNumberType.PAN) {
            if (SDKCardUtils.isValid(builder.accountNumber)) {
                this.accountNumber = builder.accountNumber;
            }
        }
        else {
            this.accountNumber = builder.accountNumber;
        }
        if(SDKCardUtils.isValidExpirationDate(builder.expirationMonth, builder.expirationYear)) {
            this.expirationMonth = builder.expirationMonth;
            this.expirationYear = builder.expirationYear;
        }
		this.cvNumber = builder.cvNumber;
        this.lastFourDigits = builder.lastFourDigits;
/*		this.zip = builder.zip;
		this.cardBrandType = builder.cardBrandType != null ? builder.cardBrandType
                : SDKCardUtils.getBrandByCardNumber(this.accountNumber);
		this.fundingType = builder.fundingType;
		this.tokenizationMethod = builder.tokenizationMethod;*/
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public String getCardExpirationMonth() {
		return expirationMonth;
	}

	public String getCardExpirationYear() {
		return expirationYear;
	}

	public String getCvNumber() {
		return cvNumber;
	}

    public SDKCardAccountNumberType getCardAccountNumberType() {
        return cardAccountNumberType;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

/*    public String getZip() {
        return zip;
    }

	public SDKCardTokenizationMethod getTokenizationMethod() {
		return tokenizationMethod;
	}

	public SDKCardBrandType getCardBrandType() {
		return cardBrandType;
	}

	public SDKCardFundingType getFundingType() {
		return fundingType;
	}*/

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((cvNumber == null) ? 0 : cvNumber.hashCode());
		result = prime * result + ((expirationMonth == null) ? 0 : expirationMonth.hashCode());
		result = prime * result + ((expirationYear == null) ? 0 : expirationYear.hashCode());
        result = prime * result + ((lastFourDigits == null) ? 0 : lastFourDigits.hashCode());
/*		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
		result = prime * result + ((cardBrandType == null) ? 0 : cardBrandType.hashCode());
		result = prime * result + ((fundingType == null) ? 0 : fundingType.hashCode());
		result = prime * result + ((tokenizationMethod == null) ? 0 : tokenizationMethod.hashCode());*/
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
		SDKCardData other = (SDKCardData)obj;
		if (accountNumber == null) {
			if (other.accountNumber != null) {
				return false;
			}
		} else if (!accountNumber.equals(other.accountNumber)) {
			return false;
		}
		if (cvNumber == null) {
			if (other.cvNumber != null) {
				return false;
			}
		} else if (!cvNumber.equals(other.cvNumber)) {
			return false;
		}
		if (expirationMonth == null) {
			if (other.expirationMonth != null) {
				return false;
			}
		} else if (!expirationMonth.equals(other.expirationMonth)) {
			return false;
		}
		if (expirationYear == null) {
			if (other.expirationYear != null) {
				return false;
			}
		} else if (!expirationYear.equals(other.expirationYear)) {
			return false;
		}
        if (lastFourDigits == null) {
            if (other.lastFourDigits != null) {
                return false;
            }
        } else if (!lastFourDigits.equals(other.lastFourDigits)) {
            return false;
        }
/*		if (zip == null) {
			if (other.zip != null) {
				return false;
			}
		} else if (!zip.equals(other.zip)) {
			return false;
		}
		if (cardBrandType != other.cardBrandType) {
			return false;
		}
		if (fundingType != other.fundingType) {
			return false;
		}
		if (tokenizationMethod != other.tokenizationMethod) {
			return false;
		}*/

		return true;
	}

	public static class Builder {
		//	required
		private final String accountNumber;
		private final String expirationMonth;
		private final String expirationYear;

		// optional
		private String cvNumber;
        private SDKCardAccountNumberType cardAccountNumberType;
        private String lastFourDigits;
/*		private String zip;
		private SDKCardBrandType cardBrandType;
		private SDKCardFundingType fundingType;
		private SDKCardTokenizationMethod tokenizationMethod;*/

		public Builder(String accountNumber, String expirationMonth, String expirationYear) {
            this.accountNumber = accountNumber;
            this.expirationMonth = expirationMonth;
            this.expirationYear = expirationYear;
			setLastFourDigits();
		}

		public SDKCardData.Builder cvNumber(String cvNumber) {
			this.cvNumber = cvNumber;
			return this;
		}

        public SDKCardData.Builder type(SDKCardAccountNumberType accountNumberType) {
            this.cardAccountNumberType = accountNumberType;
            return this;
        }

/*		public SDKCardData.Builder setCardZipCode(String zip) {
			this.zip = zip;
			return this;
		}

		public SDKCardData.Builder setCardBrandType(SDKCardBrandType cardBrandType) {
			this.cardBrandType = cardBrandType;
			return this;
		}

		public SDKCardData.Builder setCardFundingType(SDKCardFundingType fundingType) {
			this.fundingType = fundingType;
			return this;
		}

		public SDKCardData.Builder setCardTokenizationMethod(SDKCardTokenizationMethod method) {
			this.tokenizationMethod = method;
			return this;
		}*/

		public SDKCardData build() throws SDKInvalidCardException{
			return new SDKCardData(this);
		}

        private void setLastFourDigits() {
            // if there are at least 4 digits
            if (accountNumber != null && accountNumber.length() >= 4) {
                lastFourDigits = accountNumber.substring(accountNumber.length() - 4, accountNumber.length());
            }
        }
	}

}
