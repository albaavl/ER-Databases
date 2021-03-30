package jdbc;

import java.util.*;

public class Main {
	static String username;
	static String password;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to the Quiron's ER");
		System.out.println("Please enter your username and password:");
		System.out.println("Username:");
		username = sc.next();
		System.out.println("Password:");
		password = sc.next();
		if(username.equalsIgnoreCase("ms")&&password.equalsIgnoreCase("ms")) {
			System.out.println("Choose an option[1-]:");
			System.out.println("1.");
			
		}if(username.equalsIgnoreCase("as")&&password.equalsIgnoreCase("as")) {
			System.out.println("Choose an option[1-]:");
			System.out.println("1.");
			
		}if(username.equalsIgnoreCase("p")&&password.equalsIgnoreCase("p")) {
			System.out.println("Choose an option[1-]:");
			System.out.println(" 1.Create treatment \n 2.Edit treatment\n 3.");
			
		}else {
			
		}
		
sc.close();
	}

}
