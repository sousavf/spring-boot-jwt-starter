package pt.sousavf.backend.core.services;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidation {

    /**
     * According to the RFC 5322
     * <a href="https://www.rfc-editor.org/info/rfc5322">...</a>
     */
    public static final String emailRegexPattern = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public static boolean patternMatches(String emailAddress) {
        return Pattern.compile(emailRegexPattern)
                .matcher(emailAddress)
                .matches();
    }

}
