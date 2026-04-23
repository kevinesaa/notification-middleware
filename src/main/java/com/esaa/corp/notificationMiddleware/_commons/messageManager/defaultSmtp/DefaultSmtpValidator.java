package com.esaa.corp.notificationMiddleware._commons.messageManager.defaultSmtp;

import com.esaa.corp.notificationMiddleware._commons.models.api.request.PostMessageRequestModel;
import com.esaa.corp.notificationMiddleware._commons.models.api.response.CommonResponseModelEnum;
import com.esaa.corp.notificationMiddleware._commons.messageManager.abstracts.IInputValidator;

import java.util.List;
import java.util.Objects;
import java.util.Collection;
import java.util.Collections;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DefaultSmtpValidator implements IInputValidator {

    // https://www.regular-expressions.info/email.html
    // Practical implementation of RFC 5322 (without IP addresses, quoted strings, or square brackets).
    // Covers 99.99% of real-world email addresses.
    // Uses Pattern.CASE_INSENSITIVE flag to support uppercase letters.
    public static final String EMAIL_REGEX =
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z]+(?:[a-z0-9-]*[a-z])?$";

    @Override
    public CommonResponseModelEnum validate(final PostMessageRequestModel requestModel) {

        CommonResponseModelEnum result = null;

        final String senderEmail =
                requestModel.getSenderEmail() == null?null:requestModel.getSenderEmail().trim();
        if( senderEmail == null || senderEmail.isEmpty()) {
            result = CommonResponseModelEnum.MISSING_FIELD_SENDER_EMAIL;
        }


        if(result == null) {
            final long completeRecipientCount = countAllNotEmptyRecipients(requestModel);
            if(completeRecipientCount <= 0 ) {
                result = CommonResponseModelEnum.MISSING_FIELDS_RECIPIENT_EMAIL;
            }
        }

        if(result == null) {

            final List<String> emailList = combineAllEmails(requestModel);
            final boolean areEmailValid = emailList.stream()
                    .allMatch(this::isEmailFormatValid);
            if(!areEmailValid) {
                result = CommonResponseModelEnum.BAD_FORMAT_EMAILS;
            }
        }

        return result;
    }

    public long countAllNotEmptyRecipients(final PostMessageRequestModel requestModel) {

        final List<List<String>> allRecipients = Arrays.asList(
                requestModel.getRecipientsEmails(),
                requestModel.getCcEmails(),
                requestModel.getBccEmails()
        );

        final long total = allRecipients.stream()
                .filter(Objects::nonNull)
                .filter(e -> !e.isEmpty())
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .count();

        return total;
    }

    public List<String> combineAllEmails(final PostMessageRequestModel requestModel) {

        final List<List<String>> allRecipients = Arrays.asList(
                Collections.singletonList(requestModel.getSenderEmail()),
                requestModel.getReplayToEmails(),
                requestModel.getRecipientsEmails(),
                requestModel.getCcEmails(),
                requestModel.getBccEmails()
        );

        final List<String> emailList = allRecipients.stream()
                .filter(Objects::nonNull)
                .filter(e -> !e.isEmpty())
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        return emailList;
    }

    public boolean isEmailFormatValid(final String email) {

        if(email == null ) {
            return false;
        }

        return Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }
}
