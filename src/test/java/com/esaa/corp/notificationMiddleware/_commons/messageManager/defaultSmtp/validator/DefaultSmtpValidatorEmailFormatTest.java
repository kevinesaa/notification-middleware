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
        "USER@DOMAIN.COM",
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
        "user~name@domain.com",
        "user^name@domain.com"
    })
    public void whenEmailHasValidSpecialCharsInLocalPartReturnTrue(final String email) {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(email);
        Assertions.assertTrue(isValidFormat);
    }

    @Test
    public void whenEmailHasSingleQuoteInLocalPartReturnFalse() {
        // Single quote is in the RFC 5322 spec but the current regex does not match it

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid("user'name@domain.com");
        Assertions.assertFalse(isValidFormat);
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
    public void whenEmailDomainHasInvalidSeparatorReturnFalse(String email) {

        boolean isValidFormat = this.defaultSmtpValidator.isEmailFormatValid(email);
        Assertions.assertFalse(isValidFormat);
    }

}
