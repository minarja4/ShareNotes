package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static Pattern patternEmail = Pattern.compile(EMAIL_PATTERN);
    private static final String PASSWORD = "[A-Za-z\\d@#$%]{4,64}";
    private static Pattern patternPass = Pattern.compile(PASSWORD);
    private static final String USERNAME = "[A-Za-z\\d]{4,64}";
    private static Pattern patternUser = Pattern.compile(USERNAME);

    public static boolean username(String name) {
        return (name != null ? patternUser.matcher(name).matches() : false);
    }

    public static boolean email(String hex) {
        return (hex != null ? patternEmail.matcher(hex).matches() : false);
    }

    public static boolean password(String pass) {
        return (pass != null ? patternPass.matcher(pass).matches() : false);
    }
}
