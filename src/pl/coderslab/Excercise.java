package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Excercise {

	private int id;
	private String title;
	private String description;

	public Excercise() {
		
	}

	public Excercise(String title, String description) {
		this.title = title;
		this.description = description;
	}

	// zapis nowego ćwiczenia do DB lub redagowanie istniejącego
	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO excercise (title, description) VALUES (?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}

		else {
			String sql = "UPDATE excercise SET title=?, description=? where id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.setInt(3, this.id);
			preparedStatement.executeUpdate();
		}
	}

	// wczytywanie z DB ćwiczenia za id
	static public Excercise loadExcById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM excercise WHERE id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Excercise loadedExc = new Excercise();
			loadedExc.id = resultSet.getInt("id");
			loadedExc.title = resultSet.getString("title");
			loadedExc.description = resultSet.getString("description");
			return loadedExc;
		}
		return null;
	}

	// wczytywanie z DB wszystkich ćwiczeń
	static public Excercise[] loadExcUsers(Connection conn) throws SQLException {
		ArrayList<Excercise> excercises = new ArrayList<Excercise>();
		String sql = "SELECT * FROM excercise";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Excercise loadedExc = new Excercise();
			loadedExc.id = resultSet.getInt("id");
			loadedExc.title = resultSet.getString("title");
			loadedExc.description = resultSet.getString("description");
			excercises.add(loadedExc);
		}
		Excercise[] uArray = new Excercise[excercises.size()];
		uArray = excercises.toArray(uArray);
		return uArray;
	}

	// usunięcie ćwiczenia za id
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM excercise WHERE id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

}
