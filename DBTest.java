package AP_Assignment2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hsqldb.Server;

public class DBTest {

	public DBTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Server hsqlServer = null;
		Connection connection = null;
		ResultSet rs = null;

		hsqlServer = new Server();
		hsqlServer.setLogWriter(null);
		hsqlServer.setSilent(true);
		hsqlServer.setDatabaseName(0, "MiniNetDB");
		hsqlServer.setDatabasePath(0, Helper.path + "file:MYDB");
		hsqlServer.start();

		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:MiniNetDB", "admin", "adminpassword");
			connection.prepareStatement("drop table person if exists;").execute();
			connection.prepareStatement("create table person (name varchar(20) not null, age integer);").execute();
			connection.prepareStatement("insert into person (name, age)" + "values ('Rudi', 48);").execute();

			rs = connection.prepareStatement("select name, age from person;").executeQuery();
			rs.next();
			System.out.println(String.format("ID: Name: %1s, %1d", rs.getString(1), rs.getInt(2)));

			connection.commit();

		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}
}
