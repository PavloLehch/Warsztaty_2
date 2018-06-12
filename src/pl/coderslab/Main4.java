package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main4 {

	public static void main(String[] args) {
		try (Connection conn = SQLHelper.getConnection("Warsztaty_2")) {
			
			Scanner scan = new Scanner(System.in);
			System.out.println("Podaj numer id grupy");

			int id = scan.nextInt();
			UserGroup group = UserGroup.loadGroupById(conn, id);
			group.loadGroupById(conn, group.getId());
			
			System.out.println("id grupy: " + group.getId() + ", nazwa grupy " + group.getName());
			System.out.println();
			
			User [] users = User.loadAllByGrupId(conn, id);
			for (User user : users) {
				System.out.println("Użytkownik: " + user.getUsername() + ", email: " + user.getEmail());
				System.out.println("==============================================");

			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	
	
}
