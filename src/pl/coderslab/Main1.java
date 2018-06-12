package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main1 {

	public static void main(String[] args) {
		try (Connection conn = SQLHelper.getConnection("Warsztaty_2")) {
//			
			Scanner scan = new Scanner(System.in);
//			
//			System.out.println("Podaj imię");
//			String name = scan.nextLine();
//			
//			System.out.println("Podaj nazwisko");
//			String surname = scan.nextLine();
//			
//			System.out.println("Podaj hasło");
//			String password = scan.nextLine();
//			
//			System.out.println("Podaj id grupy");
//			int user_group_id = scan.nextInt();
//			
//			User user = new User();
//			
//			user.setUsername(String.format("%s %s", name, surname));
//			user.setEmail(String.format("%s.%s@gmail.com", name,surname).toLowerCase());
//			user.setPassword(password);
//			user.setUser_group_id(user_group_id);
//			user.saveToDB(conn);
			
//			System.out.println(user.getUsername() +" " + "zapisany(a) do bazy dannych z id №" + user.getId());
			System.out.println("Podaj numer id");
					
			int id = scan.nextInt();
			User user =User.loadUserById(conn, id);
			user.loadUserById(conn, user.getId());
			user.getUsername();
			user.getEmail();
			
			
//			User [] users = User.loadAllUsers(conn);
//			for(User user : users) {
//				
			System.out.println("id: " + user.getId() + ", uzytkownik " + user.getUsername()  + ", email: " + user.getEmail() +  ", password: " + user.getPassword() + ", group id:" + user.getUser_group_id());
//			System.out.println("==================================================");
//			if (user.getId() ==9) {
//				user.delete(conn);
//				System.out.println("Usunieto uzytkownika o id = " + user.getId());
//			}
			
//			
//			
		}catch (SQLException e) {
			
		}
	}
	}	
		

