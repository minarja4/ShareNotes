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

    public static boolean email(String hex) {
        Matcher matcher = patternEmail.matcher(hex);
        return matcher.matches();
    }

    public static boolean password(String pass) {
        Matcher matcher = patternPass.matcher(pass);
        return matcher.matches();
    }
}
