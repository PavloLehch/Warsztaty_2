package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MainUser {

	public static void main(String[] args) {
		String email = args[0];
		try (Connection conn = SQLHelper.getConnection("Warsztaty_2")) {

			String choice = "";
			while (true) {
				if (choice.equals("quit")) {
					break;
				}
				User user = User.loadUserByEmail(conn, email);

				user.loadUserByEmail(conn, email);
				System.out.println(String.format("Zalogowany użytkownik %s, id = %s, email:%s", user.getUsername(),
						user.getId(), user.getEmail()));
				System.out.println();

				Scanner scan = new Scanner(System.in);

				while (true) {
					System.out.println(
							"Wybierz jedną z opcji:\n add - dadawanie rozwiązania\n view - przeglądanie swoich rozwiązań\n quit - zakończenie programu");

					choice = scan.nextLine();
					if (choice.equals("add")) {
						System.out.println(
								String.format("Lista zadań, do których użytkownik %s jeszcze nie dodał rozwiązania:",
										user.getUsername()));
						System.out.println();

						Solution[] sols = Solution.loadAllNotSolByUserId(conn, user.getId());
						for (Solution sol : sols) {

							System.out.println(String.format("id zadania: %s, data inicjalizacji: %s",
									sol.getExcercise_id(), sol.getCreated()));
							System.out.println(
									"=====================================================================================");
						}

						System.out.println("Wprowadź id zadania, do jakigo chcesz dodać rozwiązanie :");
						int idExc = 0;
						while (true) {
							try {
								idExc = Integer.parseInt(scan.nextLine());
								break;
							} catch (NumberFormatException e) {
								System.out.println("Wpisz liczbę całkowitą:");
								// scan.nextLine();
							}
						}
						int have = 0;
						
						for (Solution sol : sols) {
							if (sol.getExcercise_id() == idExc) {
								have = 1;
							}
						}
						if (have == 0) {
							System.out.println("Nieprawidlowo podany numer zadania - lub nie isnieje lub rozwiązane");
							System.out.println();
							break;
						} else {
							try {
								Solution sol1 = new Solution();
								System.out.println("Wprowadź opis rozwiązania:");
								String description = scan.nextLine();

								sol1.setDescription(description);
								sol1.editToDB(conn, idExc, user.getId());

								System.out.println(
										String.format("Zadanie id = %s zapisane do bazy danych jak wykonane", idExc));
								System.out.println();
								break;

							} catch (MySQLIntegrityConstraintViolationException e) {
								System.out.println("Nie udało się wprowadzić zmiane");
								System.out.println();
							}
						}

					} else if (choice.equals("view")) {

						Solution[] sols = Solution.loadAllSolByUserId(conn, user.getId());
						System.out.println("Wszystkie rozwiązania:");
						System.out.println();

						for (Solution sol : sols) {

							System.out.println(String.format(
									"id zadania: %s, data inicjalizacji: %s, data wykonania: %s, opis: %s",
									+sol.getExcercise_id(), sol.getCreated(), sol.getUpdated(), sol.getDescription()));
							System.out.println(
									"=====================================================================================");
						}

					} else if (choice.equals("quit")) {
						System.out.println("Do widzenia!!!");
						break;
					} else {
						System.out.println("Uwaga! Tylko add, view lub quit");
						System.out.println();
					}
				}

			}
		} catch (SQLException e) {
			System.out.println(e);
		}

	}
}
