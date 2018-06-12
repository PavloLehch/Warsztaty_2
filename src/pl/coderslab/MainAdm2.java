package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MainAdm2 {
	public static void main(String[] args) {

		try (Connection conn = SQLHelper.getConnection("Warsztaty_2")) {

			String choice = "";
			while (true) {
				if (choice.equals("quit")) {
					break;
				}
				Excercise[] excs = Excercise.loadExcUsers(conn);
				System.out.println("Wszystkie zadania:");
				System.out.println();
				
				for (Excercise exc : excs) {

					System.out.println("id zadania: " + exc.getId() + ", nazwa zadania: " + exc.getTitle()
							+ ", opis zadania: " + exc.getDescription());
					System.out.println(
							"=====================================================================================");
				}

				Scanner scan = new Scanner(System.in);
				while (true) {
					System.out.println(
							"Wybierz opcję:\n add - dodanie zadania\n edit - edycja zadania\n delete -usunięcie zadania\n quit - zakończenie programu");
					choice = scan.nextLine();
					if (choice.equals("add")) {
						System.out.println("Podaj nazwę nowego zadania");
						String title = scan.nextLine();

						System.out.println("Podaj opis nowego zadania");
						String description = scan.nextLine();

						Excercise exc = new Excercise();

						exc.setTitle((title));
						exc.setDescription((description));
						exc.saveToDB(conn);

						System.out.println(
								"Zadanie '" + exc.getTitle() + "' zapisane do bazy dannych z id = " + exc.getId());
						System.out.println();
						break;

					} else if (choice.equals("edit")) {
						System.out.println("Podaj id zadania dla edycji");
						int id = 0;
						while (true) {
							try {
								id = Integer.parseInt(scan.nextLine());
								break;
							} catch (NumberFormatException e) {
								System.out.println("Wpisz liczbę całkowitą:");
								// scan.nextLine();
							}
						}
						Excercise exc = Excercise.loadExcById(conn, id);
						try {
							exc.loadExcById(conn, exc.getId());
							System.out.println("Podaj nową nazwę zadania");
							String title = scan.nextLine();

							System.out.println("Podaj nowy opis zadania");
							String description = scan.nextLine();

							exc.setTitle(title);
							exc.setDescription(description);
							exc.saveToDB(conn);
							System.out.println("Nazwa zadania id=" + exc.getId() + " zmieniona na '" + exc.getTitle() + ", opis zmieniony na '" + exc.getDescription() + "'");
							System.out.println();
							break;

						} catch (java.lang.NullPointerException e) {
							System.out.println("Zadania z takim id nie istnieje");
							System.out.println();
						}

						
					} else if (choice.equals("delete")) {
						System.out.println("Podaj id zadania dla usunięcia");
						int id = 0;
						while (true) {
							try {
								id = Integer.parseInt(scan.nextLine());
								break;
							} catch (NumberFormatException e) {
								System.out.println("Wpisz liczbę całkowitą:");
								// scan.nextLine();
							}
						}
						Excercise exc = Excercise.loadExcById(conn, id);
						try {
							System.out.println(
									"Usunieto zadanie o id = " + exc.getId() + " i nazwę '" + exc.getTitle() + "'");
							exc.delete(conn);
							System.out.println();
							break;
						} catch (java.lang.NullPointerException e) {
							System.out.println("Zadania z takim id nie istnieje");
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
