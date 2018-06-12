package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main2 {

	public static void main(String[] args) {
		try (Connection conn = SQLHelper.getConnection("Warsztaty_2")) {
			
			Scanner scan = new Scanner(System.in);
			System.out.println("Podaj numer id użytkownika");

			int id = scan.nextInt();
			User user = User.loadUserById(conn, id);
			user.loadUserById(conn, user.getId());
			
			System.out.println("id: " + user.getId() + ", uzytkownik " + user.getUsername());
			System.out.println();
			
			Solution [] sols = Solution.loadAllSolByUserId(conn, id);
			for (Solution sol : sols) {
				System.out.println("№ zadania: " + sol.getExcercise_id() + ", rozwiązanie: " + sol.getDescription());
				System.out.println("==============================================");

			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}