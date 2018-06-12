package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MainAdm3 {

	public static void main(String[] args) {

		try (Connection conn = SQLHelper.getConnection("Warsztaty_2")) {

			String choice = "";
			while (true) {
				if (choice.equals("quit")) {
					break;
				}
				UserGroup[] groups = UserGroup.loadAllGroup(conn);
				System.out.println("Wszystkie grupy:");
				System.out.println();
				
				for (UserGroup group : groups) {

					System.out.println("id grupy: " + group.getId() + ", nazwa grupy: " + group.getName());
					System.out.println(
							"=====================================================================================");
				}

				Scanner scan = new Scanner(System.in);
				while (true) {
					System.out.println(
							"Wybierz opcję:\n add - dodanie grupy\n edit - edycja grupy\n delete -usunięcie grupy\n quit - zakończenie programu");
					choice = scan.nextLine();
					if (choice.equals("add")) {
						System.out.println("Podaj nazwę nowej grupy");
						String name = scan.nextLine();

						UserGroup group = new UserGroup();

						group.setName(name);
						group.saveToDB(conn);

						System.out.println(
								"Grupa '" + group.getName() + "' zapisana do bazy dannych z id = " + group.getId());
						System.out.println();
						break;

					} else if (choice.equals("edit")) {
						System.out.println("Podaj id grupy dla edycji");
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
						UserGroup group = UserGroup.loadGroupById(conn, id);
						try {
							group.loadGroupById(conn, group.getId());
							System.out.println("Podaj nową nazwę grupy");
							String name = scan.nextLine();

							group.setName(name);
							group.saveToDB(conn);
							System.out.println("Nazwa grupy id=" + group.getId() + " zmieniona na '" + group.getName() + "'");							System.out.println();
							break;

						} catch (java.lang.NullPointerException e) {
							System.out.println("Grupy z takim id nie istnieje");
							System.out.println();
						}

						
					} else if (choice.equals("delete")) {
						System.out.println("Podaj id grupy dla usunięcia");
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
						UserGroup group = UserGroup.loadGroupById(conn, id);
						try {
							System.out.println(
									"Usunieta grupa o id = " + group.getId() + " i nazwę '" + group.getName() + "'");
							group.delete(conn);
							System.out.println();
							break;
						} catch (java.lang.NullPointerException e) {
							System.out.println("Grupy z takim id nie istnieje");
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

