package com.dinonomous.employeecrud;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class App extends DatabaseConfig {
	public static void main(String[] args) throws SQLException {
		try(Connection connection = DatabaseConfig.getConnection()){
			System.out.println("Database connected");
			
			createTable(connection);
			
			insertEmployee(connection, "John Doe", "Developer", 75000);
            insertEmployee(connection, "Jane Smith", "Manager", 85000);
            
         // Read data
            readEmployees(connection);

            // Update data
            updateEmployeeSalary(connection, 1, 80000);

            // Delete data
            deleteEmployee(connection, 2);

            // Read data again
            readEmployees(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

private static void deleteEmployee(Connection connection, int i) throws SQLException {
		// TODO Auto-generated method stub
		String del = "DELETE FROM employees WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(del)){
			ps.setInt(1, i);
			int rowsAffected = ps.executeUpdate();
			System.out.println("Number of rows effected Deleted :"+ rowsAffected);
		}
	}

private static void updateEmployeeSalary(Connection connection, int i, int j) throws SQLException {
		// TODO Auto-generated method stub
		String updated = "UPDATE employees SET salary = ? WHERE id = ?";
		try(PreparedStatement ps = connection.prepareStatement(updated)){
			ps.setInt(1, j);
			ps.setInt(2, i);
			int rowsAffected = ps.executeUpdate();
			System.out.println("Number of rows effected :"+ rowsAffected);
		}
	}

private static void readEmployees(Connection connection)throws SQLException {
		// TODO Auto-generated method stub
		String read = "SELECT * FROM employees";
		try(PreparedStatement ps = connection.prepareStatement(read)){
			ResultSet rs = ps.executeQuery();
			System.out.println("Employees :");
			while(rs.next()) {
				System.out.printf("Employee %d %s works as a %s with salary of %d\n",rs.getInt("id"),rs.getString("name")
						,rs.getString("position"),rs.getInt("salary"));
			}
		}
	}

private static void insertEmployee(Connection connection, String name, String position, int i) throws SQLException {
		// TODO Auto-generated method stub
		String InsertEmployee = "INSERT INTO employees (name, position, salary) VALUES (?, ?, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(InsertEmployee)){
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, position);
			preparedStatement.setInt(3, i);
			preparedStatement.executeUpdate();
			System.out.println("Inserted employees "+ name);
		}
	}

private static void createTable(Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String CtreateTable = "CREATE TABLE IF NOT EXISTS employees ("+
								"id SERIAL PRIMARY KEY, " + 
								"name VARCHAR(255), "+
								"position VARCHAR(255), "+
								"salary INT"+
								")";
		try(PreparedStatement preparedStatement = connection.prepareStatement(CtreateTable)){
			preparedStatement.execute();
			System.out.println("Table Created");
		}
	}
}


