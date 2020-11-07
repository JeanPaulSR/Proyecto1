package proyecto1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection con;
    private static boolean hasData = false;
	
    public ResultSet displayCities() throws ClassNotFoundException,
					    SQLException {
	if(con == null) {
	    getConnection();
	}
		
	Statement state = con.createStatement();
	ResultSet res = state.executeQuery("SELECT id, name, country, "+
					   "population, latitude, longitude " +
					   "FROM cities");
	return res;
		
    }
	
    public ResultSet displayConnections() throws ClassNotFoundException,
						 SQLException {
	if(con == null) {
	    getConnection();
	}
		
	Statement state = con.createStatement();
	ResultSet res = state.executeQuery("SELECT id_city_1, id_city_2, "+
					   "distance FROM connections");
	return res;
		
    }

    private void getConnection() throws ClassNotFoundException, SQLException {
	Class.forName("org.sqlite.JDBC");
	con = DriverManager.getConnection("jdbc:sqlite:tsp.db");
	initialize();
    }

    private void initialize() throws SQLException {
	if(!hasData) {
	    hasData = true;
			
	    Statement state = con.createStatement();
	    ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='cities'");
	    if(!res.next()) {
		System.out.println("Building the cities table with prepopulated values.");
		//need to build the table
		Statement state2 = con.createStatement();
		state2.execute("CREATE TABLE cities (id integer, name varchar(60), "
			       + "country varchar(60), population integer, latitude double, longitude double, primary key(id));");
				
		//inserting some sample data
		PreparedStatement prep = con.prepareStatement("INSERT INTO cities(name, country, population, latitude, longitude) values(?, ?, ?, ?, ?);");
		prep.setString(1, "Tokyo");
		prep.setString(2,  "Japan");
		prep.setInt(3, 31480498);
		prep.setDouble(4, 35.685000000000002273);
		prep.setDouble(5, 139.75100000000000477);
		prep.execute();
				
		PreparedStatement prep2 = con.prepareStatement("INSERT INTO cities(name, country, population, latitude, longitude) values(?, ?, ?, ?, ?);");
		prep2.setString(1, "Shanghai");
		prep2.setString(2,  "China");
		prep2.setInt(3, 14608512);
		prep2.setDouble(4, 31.045600000000000307);
		prep2.setDouble(5, 121.40000000000000568);
		prep2.execute();
				
	    }
	}
    }
	
    public void addUser(String name, String country, int population, double x,
			double y) throws ClassNotFoundException, SQLException {
	if(con == null) {
	    getConnection();
	}
		
	PreparedStatement prep = con.prepareStatement("INSERT INTO cities "+
						      "values(?, ?, ?, ?, ?, "+
						      "?);");
	prep.setString(2, name);
	prep.setString(3,  country);
	prep.setInt(4, population);
	prep.setDouble(5, x);
	prep.setDouble(6, y);
	prep.execute();		
    }
}
