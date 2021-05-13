package pruebitas_varias;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import db.interfaces.UMInterface;
import db.jdbc.SQL;
import db.jpa.JPAUserManager;
import db.pojos.*;

public class Main {


	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			String[] symbols = {"0", "1", "9", "7", "K", "Q", "a", "b", "c", "U","w","3","0"};
	        int length = 14;
	        Random random;
	            random = SecureRandom.getInstanceStrong();
	            StringBuilder sb = new StringBuilder(length);
	            for (int i = 0; i < length; i++) {
	                 int indexRandom = random.nextInt ( symbols.length );
	                 sb.append( symbols[indexRandom] );
	            }
	            String password = sb.toString();
	            System.out.println(password);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}
}


