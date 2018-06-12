package pl.coderslab;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Solution {

	private int id;
	private Date created;
	private Date updated;
	private String description;
	private int excercise_id;
	private int users_id;

	public Solution(Date created, Date updated, String description, int excercise_id, int users_id) {
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.excercise_id = excercise_id;
		this.users_id = users_id;
	}

	public Solution() {

	}

	// zapis nowego rozwiązania do DB lub redagowanie istniejącego
	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO solution(created, updated, description, excercise_id, users_id) VALUES (?, ?, ?, ?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setDate(1, this.created);
			preparedStatement.setDate(2, this.updated);
			preparedStatement.setString(3, this.description);
			preparedStatement.setInt(4, this.excercise_id);
			preparedStatement.setInt(5, this.users_id);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}

		else {
			String sql = "UPDATE solution SET created=?, updated=?, description=?, excercise_id=?, users_id=? where id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setDate(1, this.created);
			preparedStatement.setDate(2, this.updated);
			preparedStatement.setString(3, this.description);
			preparedStatement.setInt(4, this.excercise_id);
			preparedStatement.setInt(5, this.users_id);
			preparedStatement.setInt(6, this.id);
			preparedStatement.executeUpdate();
		}
	}

	// wczytywanie z DB rozwiązania za id
	static public Solution loadSolById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM solution WHERE id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Solution loadedSol = new Solution();
			loadedSol.id = resultSet.getInt("id");
			loadedSol.created = resultSet.getDate("created");
			loadedSol.updated = resultSet.getDate("updated");
			loadedSol.description = resultSet.getString("description");
			loadedSol.excercise_id = resultSet.getInt("excercise_id");
			loadedSol.users_id = resultSet.getInt("users_id");
			return loadedSol;
		}
		return null;
	}

	// wczytywanie z DB wszystkich rozwiązań
	static public Solution[] loadAllSol(Connection conn) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSol = new Solution();
			loadedSol.id = resultSet.getInt("id");
			loadedSol.created = resultSet.getDate("created");
			loadedSol.updated = resultSet.getDate("updated");
			loadedSol.description = resultSet.getString("description");
			loadedSol.excercise_id = resultSet.getInt("excercise_id");
			loadedSol.users_id = resultSet.getInt("users_id");
			solutions.add(loadedSol);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}

	// usunięcie rozwiazania za id
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM solution WHERE id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
	//wcztywanie wszystkich rozwiązań użytkownika za jego id
	static public Solution[] loadAllSolByUserId(Connection conn, int id) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution WHERE users_id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSol = new Solution();
			loadedSol.description = resultSet.getString("description");
			loadedSol.excercise_id = resultSet.getInt("excercise_id");
			loadedSol.created = resultSet.getDate("created");
			loadedSol.updated = resultSet.getDate("updated");
			solutions.add(loadedSol);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}
	
	//wcztywanie nierozwiązanych zadań użytkownika za jego id
		static public Solution[] loadAllNotSolByUserId(Connection conn, int id) throws SQLException {
			ArrayList<Solution> solutions = new ArrayList<Solution>();
			String sql = "SELECT * FROM solution WHERE updated IS NULL AND users_id=?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Solution loadedSol = new Solution();
				loadedSol.description = resultSet.getString("description");
				loadedSol.excercise_id = resultSet.getInt("excercise_id");
				loadedSol.created = resultSet.getDate("created");
				loadedSol.updated = resultSet.getDate("updated");
				solutions.add(loadedSol);
			}
			Solution[] uArray = new Solution[solutions.size()];
			uArray = solutions.toArray(uArray);
			return uArray;
		}
	
	
	static public Solution[] loadAllByExerciseId(Connection conn, int id) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT solution.excercise_id, users.username, solution.description, solution.updated FROM solution JOIN users ON solution.users_id=users.id WHERE solution.excercise_id=? ORDER BY solution.updated DESC";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {

			Solution loadedSol = new Solution();
			loadedSol.description = resultSet.getString("description");
			loadedSol.updated = resultSet.getDate("updated");
			solutions.add(loadedSol);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}

	// przypisywanie zadania do użytkownika za id zadania i id uźytkownika
	

	public void saveToDBToUser(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO solution (created, excercise_id, users_id) VALUES (NOW(), ? ,?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			// preparedStatement.setDate(1,this.created);
			preparedStatement.setInt(1, this.excercise_id);
			preparedStatement.setInt(2, this.users_id);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);

			}
		}
	}
	
	
	//dodawanie rozwiązana za id zadania i id uzytkownika
	public void editToDB(Connection conn, int excercise_id, int users_id) throws SQLException {
		String sql = "UPDATE solution SET updated=NOW(), description=? where excercise_id = ?  AND users_id = ?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setString(1, this.description);
		preparedStatement.setInt(2, excercise_id);
		preparedStatement.setInt(3, users_id);
		preparedStatement.executeUpdate();
	}
	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getExcercise_id() {
		return excercise_id;
	}

	public void setExcercise_id(int excercise_id) {
		this.excercise_id = excercise_id;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public int getId() {
		return id;
	}

}
