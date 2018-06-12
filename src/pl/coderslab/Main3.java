package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main3 {

	public static void main(String[] args) {

		try (Connection conn = SQLHelper.getConnection("Warsztaty_2")) {

			Scanner scan = new Scanner(System.in);
			System.out.println("Podaj numer id zadania");
			
	
			
			int id = scan.nextInt();
			Excercise exc = Excercise.loadExcById(conn, id);
			exc.loadExcById(conn, exc.getId());
			
					
			System.out.println("id zadania: " + exc.getId() + ", nazwa zadania: " + exc.getTitle() + ", opis zadania: " + exc.getDescription());
			System.out.println();
			Solution[] sols = Solution.loadAllByExerciseId(conn, id);
			for (Solution sol : sols) {
				System.out.println("Opis rozwiązania: " + sol.getDescription() + ", data rozwiązania: " + sol.getUpdated());
				System.out.println("=============================================================");

			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
