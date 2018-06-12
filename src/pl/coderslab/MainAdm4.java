package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MainAdm4 {

	public static void main(String[] args) {

		try (Connection conn = SQLHelper.getConnection("Warsztaty_2")) {

			String choice = "";
			while (true) {
				if (choice.equals("quit")) {
					break;
				}
				Scanner scan = new Scanner(System.in);
				while (true) {
					System.out.println(
							"Wybierz opcję:\n add - przypisywanie zadań do użytkowników\n view - przeglądanie rozwiązań danego użytkownika\n quit - zakończenie programu");
					choice = scan.nextLine();
					if (choice.equals("add")) {
						System.out.println("Lista wszystkich użytkowników");
						System.out.println();

						User[] users = User.loadAllUsers(conn);
						for (User user : users) {

							System.out.println("id: " + user.getId() + ", uzytkownik " + user.getUsername()
									+ ", email: " + user.getEmail() + ", group id:" + user.getUser_group_id());
							System.out.println(
									"=====================================================================================");
						}

						System.out.println("Podaj id użytkownika");
						int idUser = 0;
						while (true) {
							try {
								idUser = Integer.parseInt(scan.nextLine());
								break;
							} catch (NumberFormatException e) {
								System.out.println("Wpisz liczbę całkowitą:");
								// scan.nextLine();
							}
						}
						User user = User.loadUserById(conn, idUser);
						try {
							user.loadUserById(conn, user.getId());
							System.out.println(String.format("Wybrany użytkownik %s z id = %s ", user.getUsername(),
									user.getId()));

						} catch (java.lang.NullPointerException e) {
							System.out.println("Użytkownika z takim id nie istnieje");
							System.out.println();
						}

						System.out.println();
						Excercise[] excs = Excercise.loadExcUsers(conn);
						System.out.println("Wszystkie zadania:");
						System.out.println();

						for (Excercise exc : excs) {

							System.out.println("id zadania: " + exc.getId() + ", nazwa zadania: " + exc.getTitle()
									+ ", opis zadania: " + exc.getDescription());
							System.out.println(
									"=====================================================================================");
						}

						System.out.println();
						System.out.println("Podaj id zadania");
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
						Excercise exc = Excercise.loadExcById(conn, idExc);
						try {
							exc.loadExcById(conn, exc.getId());
							System.out.println(
									String.format("Wybrane zadanie '%s' z id = %s ", exc.getTitle(), exc.getId()));
							System.out.println();
						} catch (java.lang.NullPointerException e) {
							System.out.println("Zadania z takim id nie istnieje");
							System.out.println();
						}

						Solution[] sols = Solution.loadAllSolByUserId(conn, idUser);
						int have = 0;
						for (Solution sol : sols) {
							if (sol.getExcercise_id() == idExc) {
								have = 1;
							}
						}
						if (have == 1) {
							System.out.println("Ten użytkownik już ma to zadanie");
							System.out.println();
							break;
						} else {

						Solution sol1 = new Solution();
						sol1.setExcercise_id(idExc);
						sol1.setUsers_id(idUser);
						sol1.saveToDBToUser(conn);

						System.out.println(
								String.format("Do użytkownika z id=%s i nazwą %s dodane zadanie z id=%s i nazwą '%s'",
										user.getId(), user.getUsername(), exc.getId(), exc.getTitle()));
						System.out.println();
						break;
						}
					} else if (choice.equals("view")) {
						System.out.println("Podaj id użytkownika żeby wyświetlić jego rozwiązania");
						int idUser = 0;
						while (true) {
							try {
								idUser = Integer.parseInt(scan.nextLine());
								break;
							} catch (NumberFormatException e) {
								System.out.println("Wpisz liczbę całkowitą:");
								// scan.nextLine();
							}
						}
						User user = User.loadUserById(conn, idUser);
						try {
							user.loadUserById(conn, user.getId());
							System.out.println(String.format("Wybrany użytkownik z id=%s i imieniem %s", user.getId(),
									user.getUsername()));
							System.out.println();

							Solution[] sols = Solution.loadAllSolByUserId(conn, idUser);
							for (Solution sol : sols) {
								System.out.println("№ zadania: " + sol.getExcercise_id() + ", rozwiązanie: "
										+ sol.getDescription());
								System.out.println("==============================================");
							}
						} catch (java.lang.NullPointerException e) {
							System.out.println("Użytkownika z takim id nie istnieje");
							System.out.println();
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
