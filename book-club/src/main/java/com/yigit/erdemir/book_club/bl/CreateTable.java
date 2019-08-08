package com.yigit.erdemir.book_club.bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    Connection conn;

    /*
     * public boolean tableExists(Connection conn, String tableName) throws
     * SQLException { String dbUrl =
     * "jdbc:derby:C:\\Users\\erdemiryigit\\MyDB\\book_club;create=true"; conn =
     * DriverManager.getConnection(dbUrl); boolean tExists = false;
     * 
     * try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName,
     * null)) { while (rs.next()) { String tName = rs.getString("TABLE_NAME"); if
     * (tName != null && tName.equals(tableName)) { tExists = true;
     * System.out.println(tName); break; } } } return tExists; }
     */

    public CreateTable() throws SQLException {

	String dbUrl = "jdbc:derby:C:\\Users\\erdemiryigit\\MyDB\\book_club;create=true";

	conn = DriverManager.getConnection(dbUrl);

	try (Statement stmt = conn.createStatement()) {
	    stmt.executeUpdate(
		    "CREATE TABLE MEMBERS (ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),NAME VARCHAR(45) UNIQUE,MEMBER_SINCE DATE,BORROWED INT DEFAULT 0,DONATED INT DEFAULT 0,PRIMARY KEY (ID))");

	    stmt.executeUpdate("CREATE TABLE BOOKS ("
		    + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
		    + "NAME VARCHAR(45)," + "AUTHOR VARCHAR(45)," + "FIRST_PUBLISHED DATE,"
		    + "DONATED_BY INT, BORROWED_BY INT,"
		    + "PRIMARY KEY (ID),FOREIGN KEY (DONATED_BY) REFERENCES MEMBERS(ID),FOREIGN KEY (BORROWED_BY) REFERENCES MEMBERS(ID))");

	    stmt.executeUpdate("CREATE TABLE HISTORY (" + "BOOK_ID INT NOT NULL,"
		    + "DONATED_BY INT NOT NULL, BORROWED_BY INT,"
		    + "DATE_BORROWED DATE DEFAULT NULL, DATE_RETURNED DATE DEFAULT NULL, DATE_DONATED DATE NOT NULL,"
		    + "FOREIGN KEY (BOOK_ID) REFERENCES BOOKS(ID),FOREIGN KEY (DONATED_BY) REFERENCES MEMBERS(ID),FOREIGN KEY (BORROWED_BY) REFERENCES MEMBERS(ID))");

	} catch (Exception ex) {
	    ex.printStackTrace();
	    System.out.println("TABLE ALREADY EXISTS");
	}

    }

}