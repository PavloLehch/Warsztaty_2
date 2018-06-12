package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MainAdm1 {

	public static void main(String[] args) {

		try (Connection conn = SQLHelper.getConnection("Warsztaty_2")) {

			String choice = "";
			while (true) {
				if (choice.equals("quit")) {
					break;
				}
				User[] users = User.loadAllUsers(conn);
				for (User user : users) {

					System.out.println("id: " + user.getId() + ", uzytkownik " + user.getUsername() + ", email: "
							+ user.getEmail() + ", group id:" + user.getUser_group_id());
					System.out.println(
							"=====================================================================================");
				}

				Scanner scan = new Scanner(System.in);
				while (true) {
					System.out.println(
							"Wybierz opcję:\n add - dodanie użytkownika\n edit - edycja użytkownika\n delete -usunięcie użytkownika\n quit - zakończenie programu");
					choice = scan.nextLine();
					if (choice.equals("add")) {
						System.out.println("Podaj imię nowego użytkownika");
						String name = scan.nextLine();

						System.out.println("Podaj nazwisko nowego użytkownika");
						String surname = scan.nextLine();

						System.out.println("Podaj hasło nowego użytkownika");
						String password = scan.nextLine();

						System.out.println("Podaj id grupy nowego użytkownika");
						int user_group_id;
						while (true) {
							try {
								user_group_id = Integer.parseInt(scan.nextLine());
								break;
							} catch (NumberFormatException e) {
								System.out.println("Wpisz liczbę całkowitą:");
//								scan.nextLine();
							}
						}
						User user = new User();
						try {
							user.setUsername(String.format("%s %s", name, surname));
							user.setEmail(String.format("%s.%s@gmail.com", name, surname).toLowerCase());
							user.setPassword(password);
							user.setUser_group_id(user_group_id);
							user.saveToDB(conn);

							System.out.println(
									user.getUsername() + " " + "zapisany(a) do bazy dannych z id №" + user.getId());
							System.out.println();
							break;
						} catch (MySQLIntegrityConstraintViolationException e) {
							System.out.println("Podanej grupy nie istnieje, sprawdź dane");
							System.out.println();
						}

					} else if (choice.equals("edit")) {
						System.out.println("Podaj id użytkownika dla edycji");
						int id = 0;
						while (true) {
							try {
								id = Integer.parseInt(scan.nextLine());
								break;
							} catch (NumberFormatException e) {
								System.out.println("Wpisz liczbę całkowitą:");
//								scan.nextLine();
							}
						}
						User user = User.loadUserById(conn, id);
						try {
							user.loadUserById(conn, user.getId());
							System.out.println("Podaj nowe imię użytkownika");
							String name = scan.nextLine();

							System.out.println("Podaj nowe nazwisko użytkownika");
							String surname = scan.nextLine();

							System.out.println("Podaj nowe hasło użytkownika");
							String password = scan.nextLine();

							System.out.println("Podaj nowe id grupy użytkownika");
							int user_group_id;
							while (true) {
								try {
									user_group_id = Integer.parseInt(scan.nextLine());
									break;
								} catch (NumberFormatException e) {
									System.out.println("Wpisz liczbę całkowitą:");
//									scan.nextLine();
								}
							}

							user.setUsername(String.format("%s %s", name, surname));
							// user.setEmail(String.format("%s.%s@gmail.com", name, surname).toLowerCase());
							user.setPassword(password);
							user.setUser_group_id(user_group_id);
							user.saveToDB(conn);
							break;
							
						} catch (java.lang.NullPointerException e) {
							System.out.println("Użytkownika z takim id nie istnieje");
							System.out.println();
						}

						catch (MySQLIntegrityConstraintViolationException e) {
							System.out.println("Podanej grupy nie istnieje, sprawdź dane");
							System.out.println();
						}

					} else if (choice.equals("delete")) {
						System.out.println("Podaj id użytkownika dla usunięcia");
						int id = 0;
						while (true) {
							try {
								id = Integer.parseInt(scan.nextLine());
								break;
							} catch (NumberFormatException e) {
								System.out.println("Wpisz liczbę całkowitą:");
//								scan.nextLine();
							}
						}
						User user = User.loadUserById(conn, id);
						try {
							System.out.println(
									"Usunieto uzytkownika o id = " + user.getId() + " - " + user.getUsername());
							user.delete(conn);
							System.out.println();
							break;
						} catch (java.lang.NullPointerException e) {
							System.out.println("Użytkownika z takim id nie istnieje");
							System.out.println();
						}

					} else if (choice.equals("quit")) {
						System.out.println("Do widzenia!!!");
						break;
					} else {
						System.out.println("Uwaga! Tylko add, edit, delete lub quit");
						System.out.println();
					}
				}

			}

		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}