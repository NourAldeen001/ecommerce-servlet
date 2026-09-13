package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validator {
	
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    private static final String PHONE_REGEX = "^\\+(?:[0-9] ?){12,15}[0-9]$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
	
	private Validator() {}
	
	public static boolean isNotBlank(String s) {
		if(s == null || s.trim().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public static boolean isValidEmail(String email) {
		return checkPatternAndNonEmpty(EMAIL_PATTERN, email);
	}
	
	public static boolean isValidPhone(String phone) {
		return checkPatternAndNonEmpty(PHONE_PATTERN, phone);
	}
	
	public static boolean isStrongPassword(String password) {
		return checkPatternAndNonEmpty(PASSWORD_PATTERN, password);
	}
	
	
	private static boolean checkPatternAndNonEmpty(Pattern pattern, String s) {
		if(s == null || s.trim().isEmpty()) {
			return false;
		}
		Matcher matcher = pattern.matcher(s);
		return matcher.matches();
	}

}
