package pt.sousavf.backend.core.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailValidationTest {

    @Test
    public void testUsingSimpleRegex() {
        final String emailAddress = "username@domain.com";
        assertTrue(EmailValidation.patternMatches(emailAddress, EmailValidation.emailRegexPattern));
    }

}