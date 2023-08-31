package com.cybersource.inappsdk.common.utils;

import com.cybersource.inappsdk.common.SDKCardBrandType;
import com.cybersource.inappsdk.common.exceptions.SDKInvalidCardException;
import com.cybersource.inappsdk.common.utils.SDKCardUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by fzubair on 10/26/2015.
 */
public class SDKCardUtilsTest {

    // Sample valid credit card numbers

    // AMERICAN EXPRESS
    private final String SAMPLE_CARD_AMERICAN_EXPRESS = "378282246310005";
    private final String SAMPLE_CARD_AMERICAN_EXPRESS_2 = "371449635398431";
    private final String SAMPLE_CARD_AMERICAN_EXPRESS_3 = "34343434343434";
    private final String SAMPLE_CARD_AMERICAN_EXPRESS_CORPORATE = "378734493671000";

    // DISCOVER
    private final String SAMPLE_CARD_DISCOVER = "6011111111111117";
    private final String SAMPLE_CARD_DISCOVER_2 = "6011000990139424";
    private final String SAMPLE_CARD_DISCOVER_3 = "6011000400000000";

    // DINERS CLUB
    private final String SAMPLE_CARD_DINERS_CLUB = "30569309025904";
    private final String SAMPLE_CARD_DINERS_CLUB_2 = "38520000023237";
    private final String SAMPLE_CARD_DINERS_CLUB_3 = "36700102000000";
    private final String SAMPLE_CARD_DINERS_CLUB_4 = "36148900647913";

    // JCB
    private final String SAMPLE_CARD_JCB = "3530111333300000";
    private final String SAMPLE_CARD_JCB_2 = "38520000023237";
    private final String SAMPLE_CARD_JCB_3 = "3528000700000000";

    // MASTER CARD
    private final String SAMPLE_CARD_MASTER_CARD = "5555555555554444";
    private final String SAMPLE_CARD_MASTER_CARD_2 = "5105105105105100";
    private final String SAMPLE_CARD_MASTER_CARD_3 = "5454545454545454";

    // VISA
    private final String SAMPLE_CARD_VISA = "4111111111111111";
    private final String SAMPLE_CARD_VISA_2 = "4012888888881881";
    private final String SAMPLE_CARD_VISA_3 = "4222222222222";
    private final String SAMPLE_CARD_VISA_4 = "4911830000000";
    private final String SAMPLE_CARD_VISA_5 = "4444333322221111";
    private final String SAMPLE_CARD_VISA_6 = "4917610000000000";
    // VISA DEBIT
    private final String SAMPLE_CARD_VISA_7 = "4462030000000000";
    private final String SAMPLE_CARD_VISA_8 = "4917610000000000003";
    // VISA ELECTRON
    private final String SAMPLE_CARD_VISA_9 = "4917300800000000";
    // VISA PURCHASING
    private final String SAMPLE_CARD_VISA_10 = "4484070000000000";

    // UNKNOWN
    private final String SAMPLE_CARD_UNKNOWN = "49927398716";

    // Sample invalid credit card numbers
    private final String SAMPLE_INVALID_CARD = "4111111111111112";
    private final String SAMPLE_INVALID_CARD_2 = "4242424242424241";
    private final String SAMPLE_INVALID_CARD_3 = "1234567812345678";
    private final String SAMPLE_INVALID_CARD_4 = "49927398717";

    // MASKED CARD NUMBERS
    private final String SAMPLE_MASKED_CARD_VISA = "************1111";
    private final String SAMPLE_MASKED_CARD_VISA_2 = "****-******-*1881";
    private final String SAMPLE_MASKED_CARD_VISA_3 = "****-******-*0000";

    // VALID YEARS
    private final String SAMPLE_VALID_YEAR = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    private final String SAMPLE_VALID_YEAR_2 = "4015";

    // INVALID YEARS
    private final String SAMPLE_INVALID_YEAR = "2014";
    private final String SAMPLE_INVALID_YEAR_2 = "0000";

    private final String SAMPLE_MONTH_JAN = "01";
    private final String SAMPLE_MONTH_FEB = "02";
    private final String SAMPLE_MONTH_OCT = "10";
    private final String SAMPLE_MONTH_DEC = "12";
    private final String SAMPLE_MONTH_INVALID = "2";
    private final String SAMPLE_MONTH_INVALID_2 = "22";
    private final String SAMPLE_MONTH_INVALID_3 = "00";

    @Test
    public void testIsValidForValidCards() throws Exception {
        // VALID CARDS
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_AMERICAN_EXPRESS));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_AMERICAN_EXPRESS_2));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_AMERICAN_EXPRESS_3));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_AMERICAN_EXPRESS_CORPORATE));

        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_DISCOVER));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_DISCOVER_2));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_DISCOVER_3));

        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_DINERS_CLUB));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_DINERS_CLUB_2));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_DINERS_CLUB_3));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_DINERS_CLUB_4));

        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_JCB));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_JCB_2));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_JCB_3));

        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_MASTER_CARD));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_MASTER_CARD_2));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_MASTER_CARD_3));

        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_VISA));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_VISA_2));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_VISA_3));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_VISA_4));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_VISA_5));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_VISA_6));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_VISA_7));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_VISA_8));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_VISA_9));
        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_VISA_10));

        assertTrue("This should have been a valid card", SDKCardUtils.isValid(SAMPLE_CARD_UNKNOWN));
    }

    @Test
    public void testIsValidForInvalidCards() throws Exception {
        // INVALID CARDS
        try {
            SDKCardUtils.isValid(SAMPLE_INVALID_CARD);
            Assert.fail("Should have thrown SDKInvalidCard Exception");
        } catch (SDKInvalidCardException e) {
            assertEquals("Invalid Card Number", e.getMessage());
        }
        try {
            SDKCardUtils.isValid(SAMPLE_INVALID_CARD_2);
            Assert.fail("Should have thrown SDKInvalidCard Exception");
        } catch (SDKInvalidCardException e) {
            assertEquals("Invalid Card Number", e.getMessage());
        }
        try {
            SDKCardUtils.isValid(SAMPLE_INVALID_CARD_3);
            Assert.fail("Should have thrown SDKInvalidCard Exception");
        } catch (SDKInvalidCardException e) {
            assertEquals("Invalid Card Number", e.getMessage());
        }
        try {
            SDKCardUtils.isValid(SAMPLE_INVALID_CARD_4);
            Assert.fail("Should have thrown SDKInvalidCard Exception");
        } catch (SDKInvalidCardException e) {
            assertEquals("Invalid Card Number", e.getMessage());
        }
    }

    @Test
    public void testGetBrandByCardNumber() throws Exception {
        // VALID CARDS
        assertEquals(SDKCardBrandType.SDK_AMERICAN_EXPRESS, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_AMERICAN_EXPRESS));
        assertEquals(SDKCardBrandType.SDK_AMERICAN_EXPRESS, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_AMERICAN_EXPRESS_2));
        //assertEquals(SDKCardBrandType.SDK_AMERICAN_EXPRESS, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_AMERICAN_EXPRESS_3));
        assertEquals(SDKCardBrandType.SDK_AMERICAN_EXPRESS, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_AMERICAN_EXPRESS_CORPORATE));

        assertEquals(SDKCardBrandType.SDK_DISCOVER, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_DISCOVER));
        assertEquals(SDKCardBrandType.SDK_DISCOVER, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_DISCOVER_2));
        assertEquals(SDKCardBrandType.SDK_DISCOVER, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_DISCOVER_3));

        assertEquals(SDKCardBrandType.SDK_DINERS_CLUB, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_DINERS_CLUB));
        //assertEquals(SDKCardBrandType.SDK_DINERS_CLUB, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_DINERS_CLUB_3));
        //assertEquals(SDKCardBrandType.SDK_DINERS_CLUB, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_DINERS_CLUB_4));
        //assertEquals(SDKCardBrandType.SDK_DINERS_CLUB, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_DINERS_CLUB_2));

        assertEquals(SDKCardBrandType.SDK_JCB, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_JCB));
        assertEquals(SDKCardBrandType.SDK_JCB, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_JCB_3));
        //assertEquals(SDKCardBrandType.SDK_JCB, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_JCB_2));

        assertEquals(SDKCardBrandType.SDK_MASTER_CARD, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_MASTER_CARD));
        assertEquals(SDKCardBrandType.SDK_MASTER_CARD, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_MASTER_CARD_2));
        assertEquals(SDKCardBrandType.SDK_MASTER_CARD, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_MASTER_CARD_3));

        assertEquals(SDKCardBrandType.SDK_VISA, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_VISA));
        assertEquals(SDKCardBrandType.SDK_VISA, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_VISA_2));
        assertEquals(SDKCardBrandType.SDK_VISA, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_VISA_3));
        assertEquals(SDKCardBrandType.SDK_VISA, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_VISA_4));
        assertEquals(SDKCardBrandType.SDK_VISA, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_VISA_5));
        assertEquals(SDKCardBrandType.SDK_VISA, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_VISA_6));
        assertEquals(SDKCardBrandType.SDK_VISA, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_VISA_7));
        //assertEquals(SDKCardBrandType.SDK_VISA, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_VISA_8));
        assertEquals(SDKCardBrandType.SDK_VISA, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_VISA_9));
        assertEquals(SDKCardBrandType.SDK_VISA, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_VISA_10));

        assertEquals(SDKCardBrandType.SDK_OTHER, SDKCardUtils.getBrandByCardNumber(SAMPLE_CARD_UNKNOWN));
    }

    @Test
    public void testisValidYear() throws Exception {
        // VALID YEARS
        assertTrue("This should have been a valid year", SDKCardUtils.isValidExpirationDate(SAMPLE_MONTH_OCT, SAMPLE_VALID_YEAR));
        assertTrue("This should have been a valid year", SDKCardUtils.isValidExpirationDate(SAMPLE_MONTH_DEC, SAMPLE_VALID_YEAR_2));
        assertTrue("This should have been a valid year", SDKCardUtils.isValidExpirationDate(SAMPLE_MONTH_JAN, SAMPLE_VALID_YEAR_2));

        // INVALID DATES
        try {
            SDKCardUtils.isValidExpirationDate(SAMPLE_MONTH_JAN, SAMPLE_VALID_YEAR); // JAN, 2015
            Assert.fail("Should have thrown SDKInvalidCard Exception");
        } catch (SDKInvalidCardException e) {
            assertEquals("Invalid Card Expiration Date", e.getMessage());
        }
        try {
            SDKCardUtils.isValidExpirationDate(SAMPLE_MONTH_OCT, SAMPLE_INVALID_YEAR); // OCT 2014
            Assert.fail("Should have thrown SDKInvalidCard Exception");
        } catch (SDKInvalidCardException e) {
            assertEquals("Invalid Card Expiration Date", e.getMessage());
        }
        try {
            SDKCardUtils.isValidExpirationDate(SAMPLE_MONTH_OCT, SAMPLE_INVALID_YEAR_2);
            Assert.fail("Should have thrown SDKInvalidCard Exception");
        } catch (SDKInvalidCardException e) {
            assertEquals("Invalid Card Expiration Date", e.getMessage());
        }
        try {
            SDKCardUtils.isValidExpirationDate(SAMPLE_MONTH_INVALID, SAMPLE_VALID_YEAR);
            Assert.fail("Should have thrown SDKInvalidCard Exception");
        } catch (SDKInvalidCardException e) {
            assertEquals("Invalid Card Date format", e.getMessage());
        }
        try {
            SDKCardUtils.isValidExpirationDate(SAMPLE_MONTH_INVALID_2, SAMPLE_VALID_YEAR);
            Assert.fail("Should have thrown SDKInvalidCard Exception");
        } catch (SDKInvalidCardException e) {
            assertEquals("Invalid Card Expiration Date", e.getMessage());
        }
        try {
            SDKCardUtils.isValidExpirationDate(SAMPLE_MONTH_INVALID_3, SAMPLE_VALID_YEAR_2);
            Assert.fail("Should have thrown SDKInvalidCard Exception");
        } catch (SDKInvalidCardException e) {
            assertEquals("Invalid Card Expiration Date", e.getMessage());
        }
    }
}