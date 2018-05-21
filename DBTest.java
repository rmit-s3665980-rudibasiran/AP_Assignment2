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

			String sql = "";
			sql = "create table person (" + "name varchar(50) not null, " + "photo varchar(50), "
					+ "info varchar(100), " + "gender varchar(1), " + "age integer, " + "state varchar(5)" + ");";
			connection.prepareStatement(sql).execute();

			sql = "insert into person (name, photo, info, gender, age, state)"
					+ "values ('Rudi', 'rudi.jpg', 'LSA', 'M', 48, 'VIC');";
			connection.prepareStatement(sql).execute();

			sql = "insert into person (name, photo, info, gender, age, state)"
					+ "values ('Ahysa', 'ahysa.jpg', 'Housewife', 'F', 45, 'VIC');";
			connection.prepareStatement(sql).execute();

			sql = "select name, photo, info, gender, age, state " + "from person order by name desc;";
			rs = connection.prepareStatement(sql).executeQuery();
			while (rs.next()) {
				System.out.println(String.format(
						"Name: %1s, Photo: %1s, Info: %1s,  Gender: %1s,  Age: %1d, State: %1s", rs.getString(1),
						rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
			}

			sql = "select table_name "
					+ "from INFORMATION_SCHEMA.SYSTEM_TABLES where table_name = 'person' order by 1 desc;";
			rs = connection.prepareStatement(sql).executeQuery();
			while (rs.next()) {
				System.out.println("Table name = " + rs.getString(1));
			}

			connection.commit();

		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}
}
