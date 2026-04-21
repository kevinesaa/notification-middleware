package com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.validator;

import com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp.DefaultSmtpValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class DefaultSmtpValidatorEmailFormatTest {

    private DefaultSmtpValidator defaultSmtpValidator;

    @BeforeEach
    public void setup() {
        this.defaultSmtpValidator = new DefaultSmtpValidator();
    }


    @Test
    public void whenThereBasicFormatReturnTrue() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("test@test.com");
        Assertions.assertTrue(isValidFormat);
    }

    // --- Valid email formats ---

    @ParameterizedTest
    @ValueSource(strings = {
        "user.name@domain.com",
        "user+tag@domain.org",
        "user_name@sub.domain.com",
        "user123@domain.co",
        "user@domain.io",
        "user-name@domain.net",
        "user!name@domain.com",
        "user#name@domain.com",
        "user$name@domain.com",
        "user%name@domain.com",
        "user&name@domain.com",
        "user*name@domain.com",
        "user/name@domain.com",
        "user=name@domain.com",
        "user?name@domain.com",
        "user`name@domain.com",
        "user{name}@domain.com",
        "user|name@domain.com",
        "user~name@domain.com"
    })
    public void whenEmailHasValidSpecialCharsInLocalPartReturnTrue(final String email) {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(email);
        Assertions.assertTrue(isValidFormat);
    }

    @Test
    public void whenEmailIsUppercaseReturnTrue() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("USER@DOMAIN.COM");
        Assertions.assertTrue(isValidFormat);
    }

    @Test
    public void whenEmailHasSingleQuoteInLocalPartReturnTrue() {
        // Single quote is explicitly allowed by RFC 5322 practical regex

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user'name@domain.com");
        Assertions.assertTrue(isValidFormat);
    }

    @Test
    public void whenEmailHasSubdomainReturnTrue() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@mail.sub.domain.com");
        Assertions.assertTrue(isValidFormat);
    }

    @Test
    public void whenEmailHasNumericDomainReturnTrue() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@123.com");
        Assertions.assertTrue(isValidFormat);
    }

    @Test
    public void whenEmailHasNumericLocalPartReturnTrue() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("123456@domain.com");
        Assertions.assertTrue(isValidFormat);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "user@domain.photography",
        "user@domain.technology",
        "user@domain.international"
    })
    public void whenEmailHasLongTldReturnTrue(final String email) {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(email);
        Assertions.assertTrue(isValidFormat);
    }

    @Test
    public void whenEmailHasSingleCharLocalPartReturnTrue() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("a@domain.com");
        Assertions.assertTrue(isValidFormat);
    }

    @Test
    public void whenEmailHasHyphenInDomainReturnTrue() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@my-domain.com");
        Assertions.assertTrue(isValidFormat);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "user@domain.mx",
        "user@domain.uk",
        "user@domain.de"
    })
    public void whenEmailHasTwoLetterTldReturnTrue(final String email) {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(email);
        Assertions.assertTrue(isValidFormat);
    }

    // --- Invalid email formats ---

    @Test
    public void whenEmailValidWithoutTrimReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(" test@test.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailIsNullReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(null);
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailIsEmptyReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailIsWhiteSpaceReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(" ");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasNoAtSignReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("userdomain.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasNoDomainReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasNoLocalPartReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("@domain.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasMultipleAtSignsReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@@domain.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasSpaceInMiddleReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user name@domain.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasTrailingSpaceReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@domain.com ");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasOnlyAtSignReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("@");
        Assertions.assertFalse(isValidFormat);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "user@domain,com",
        "user@domain;com",
        "user@domain com"
    })
    public void whenEmailDomainHasInvalidSeparatorReturnFalse(final String email) {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(email);
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasMultipleAtSignsReturnFalse2() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@domain@other.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasUnicodeCharInLocalPartReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("üser@domain.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasUnicodeCharInDomainReturnFalse() {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@dömain.com");
        Assertions.assertFalse(isValidFormat);
    }

    // --- RFC 5322 practical regex now correctly rejects these invalid formats ---

    @Test
    public void whenEmailHasNoDotInDomainReturnFalse() {
        // RFC 5322 practical regex requires at least one dot in the domain part

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@domain");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailStartsWithDotInLocalPartReturnFalse() {
        // RFC 5322 practical regex does not allow leading dot in local part

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(".user@domain.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailEndsWithDotInLocalPartReturnFalse() {
        // RFC 5322 practical regex does not allow trailing dot in local part

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user.@domain.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailHasConsecutiveDotsInLocalPartReturnFalse() {
        // RFC 5322 practical regex does not allow consecutive dots in local part

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user..name@domain.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailDomainStartsWithDotReturnFalse() {
        // RFC 5322 practical regex does not allow leading dot in domain part

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@.domain.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailDomainEndsWithDotReturnFalse() {
        // RFC 5322 practical regex does not allow trailing dot in domain part

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@domain.");
        Assertions.assertFalse(isValidFormat);
    }

    // --- Additional RFC 5322 coverage ---

    @Test
    public void whenEmailHasDeepNestedSubdomainsReturnTrue() {
        // RFC 5322 allows multiple levels of subdomains

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@a.b.c.d.e.domain.com");
        Assertions.assertTrue(isValidFormat);
    }

    @Test
    public void whenEmailDomainLabelStartsWithHyphenReturnFalse() {
        // RFC 5321 prohibits domain labels starting with a hyphen

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@-domain.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailDomainLabelEndsWithHyphenReturnFalse() {
        // RFC 5321 prohibits domain labels ending with a hyphen

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@domain-.com");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailDomainHasConsecutiveDotsReturnFalse() {
        // Consecutive dots produce empty labels, which are invalid per RFC 5321

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@domain..com");
        Assertions.assertFalse(isValidFormat);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "user\n@domain.com",
        "user\r@domain.com",
        "user\t@domain.com",
        "user@domain\n.com"
    })
    public void whenEmailHasControlCharactersReturnFalse(final String email) {
        // Control characters are not valid in email addresses per RFC 5322

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(email);
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailTldIsNumericOnlyReturnFalse() {
        // RFC 5321 requires TLD to end with a letter; purely numeric TLDs are invalid

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@domain.123");
        Assertions.assertFalse(isValidFormat);
    }

    @Test
    public void whenEmailDomainHasSingleCharLabelReturnTrue() {
        // Single-character domain labels are valid per RFC 5321

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user@a.com");
        Assertions.assertTrue(isValidFormat);
    }

}
