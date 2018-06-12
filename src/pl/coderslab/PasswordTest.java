package pl.coderslab;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordTest {
	
	public static void main(String[] args) {
			
	String password = "Mark";
		
	// Hash a password for the first time
	String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
	
	System.out.println("Original haslo " + password);
	System.out.println("Haslo hash " + hashed);

	// gensalt's log_rounds parameter determines the complexity
	// the work factor is 2**log_rounds, and the default is 10
	hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
	System.out.println(hashed);

	// Check that an unencrypted password matches one that has
	// previously been hashed
	String candidate = "Bobby";
	if (BCrypt.checkpw(candidate, hashed)) {
		System.out.println("It matches");
	}else {
		System.out.println("It does not match");
	}	
	}
}
