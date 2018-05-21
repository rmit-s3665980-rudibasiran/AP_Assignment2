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
		hsqlServer.setDatabaseName(0, "TestDB");
		hsqlServer.setDatabasePath(0, "file:MYDB");
		hsqlServer.start();

		try {

			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:TestDB", "sa", "123");
			connection.prepareStatement("drop table barcodes if exists;").execute();
			connection.prepareStatement("create or replace table barcodes (id integer, barcode varchar(20) not null);")
					.execute();

			connection.prepareStatement("insert into barcodes (id, barcode)" + "values (1, '12345577');").execute();
			rs = connection.prepareStatement("select id, barcode from barcodes;").executeQuery();
			rs.next();
			System.out.println(String.format("ID: %1d, Name:%1s", rs.getInt(1), rs.getString(2)));
			connection.commit();

		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}

	}
}
