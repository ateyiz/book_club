package com.yigit.erdemir.book_club.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookClubDAO {

    public MemberDTO getMember(Connection con, String name) throws SQLException {

	String query = "SELECT * FROM MEMBERS WHERE NAME = ?";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setString(1, name);

	    MemberDTO memberDTO = new MemberDTO();

	    try (ResultSet rs = myStmt.executeQuery()) {

		while (rs.next()) {

		    memberDTO.setId(rs.getInt("ID"));

		    memberDTO.setName(rs.getString("NAME"));
		    memberDTO.setDonated(rs.getInt("DONATED"));

		    memberDTO.setBorrowed(rs.getInt("BORROWED"));

		    memberDTO.setMemberSince(rs.getDate("MEMBER_SINCE"));
		}
	    }

	    return memberDTO;
	}
    }

    public BookDTO getBook(Connection con, int id) throws SQLException {

	String query = "SELECT * FROM BOOKS WHERE id = ?";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setInt(1, id);

	    BookDTO bookDTO = new BookDTO();

	    try (ResultSet rs = myStmt.executeQuery()) {

		while (rs.next()) {

		    bookDTO.setId(rs.getInt("id"));

		    bookDTO.setName(rs.getString("name"));

		    bookDTO.setDonatedBy(rs.getInt("donated"));

		    bookDTO.setBorrowedBy(rs.getInt("borrowed"));

		    bookDTO.setDateBorrowed(rs.getDate("dateGiven"));
		}
	    }
	    return bookDTO;
	}
    }

    public List<HistoryDTO> getBookHistory(Connection con, int bookID) throws SQLException {

	String query = "SELECT * FROM HISTORY WHERE bookID = ?";

	List<HistoryDTO> historyList = new ArrayList<HistoryDTO>();

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setInt(1, bookID);

	    HistoryDTO historyDTO = new HistoryDTO();

	    try (ResultSet rs = myStmt.executeQuery()) {

		while (rs.next()) {

		    historyDTO.setBookID(rs.getInt("bookID"));

		    historyDTO.setMemberID(rs.getInt("memberID"));

		    historyDTO.setDateBorrowed(rs.getDate("dateBorrowed"));

		    historyDTO.setDateDonated(rs.getDate("dateDonated"));

		    historyDTO.setDateReturned(rs.getDate("dateReturned"));

		    historyList.add(historyDTO);

		}
	    }

	    return historyList;
	}
    }

    public List<HistoryDTO> getMemberHistory(Connection con, String username) throws SQLException {

	String query = "SELECT * FROM HISTORY WHERE MEMBER_ID = ?";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    List<HistoryDTO> historyList = new ArrayList<HistoryDTO>();

	    try (ResultSet rs = myStmt.executeQuery()) {

		while (rs.next()) {

		    HistoryDTO historyDTO = new HistoryDTO();

		    historyDTO.setMemberID(rs.getInt("MEMBER_ID"));

		    historyDTO.setBookID(rs.getInt("BOOK_ID"));

		    historyDTO.setDateBorrowed(rs.getDate("DATE_BORROWED"));

		    historyDTO.setDateDonated(rs.getDate("DATE_DONATED"));

		    historyDTO.setDateReturned(rs.getDate("DATE_RETURNED"));

		    historyList.add(historyDTO);
		}

	    }
	    return historyList;
	}

    }

    public List<MemberDTO> getAllMembers(Connection con) throws SQLException {

	List<MemberDTO> memberList = new ArrayList<MemberDTO>();

	String query = "SELECT * FROM MEMBERS";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    try (ResultSet rs = myStmt.executeQuery()) {

		while (rs.next()) {

		    MemberDTO memberDTO = new MemberDTO();

		    memberDTO.setId(rs.getInt("ID"));

		    memberDTO.setName(rs.getString("NAME"));

		    memberDTO.setMemberSince(rs.getDate("MEMBER_SINCE"));

		    memberList.add(memberDTO);
		}
	    }

	    return memberList;
	}
    }

    public boolean insertMember(Connection con, String name) throws SQLException {

	String query = "INSERT INTO MEMBERS(NAME, MEMBER_SINCE) VALUES (?,?)"; // NOW() Saati
									       // de
									       // getiriyor.
	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setString(1, name);
	    myStmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));

	    int affectedRows = myStmt.executeUpdate();

	    return affectedRows > 0;

	}
    }

    // TO DO : HOW TO RETURN ID OF INSERTED ROW (OUT PARAMETER)

    /*
     * t_pstmt = a_connection.prepareStatement("YOUR INSERT GOES HERE",
     * PreparedStatement.RETURN_GENERATED_KEYS); int rownum =
     * t_pstmt.executeUpdate(); t_resultset = t_pstmt.getGeneratedKeys(); if( rownum
     * != 0 && !t_resultset.next()) { t_iVersion = t_resultset.getInt(1); }
     */
    public int insertBook(Connection con, String bookName, String author, int donatedBy, Date firstPublished)
	    throws SQLException {
	int insertedID = 0;

	String query = "INSERT INTO BOOKS(NAME, AUTHOR, DONATED_BY,FIRST_PUBLISHED) " + "VALUES(?,?,?,?)";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setString(1, bookName);

	    myStmt.setString(2, author);

	    myStmt.setInt(3, donatedBy);

	    if (firstPublished != null) {
		myStmt.setDate(4, new java.sql.Date(firstPublished.getTime()));
	    } else
		myStmt.setDate(4, null);

	    int affectedRows = myStmt.executeUpdate(query);

	    boolean success = affectedRows > 0;

	    if (success) {
		ResultSet rs = myStmt.getGeneratedKeys();
		insertedID = rs.getInt(1);
		con.commit();

	    }
	    return insertedID;

	}
    }

    public boolean updateMember(Connection con, int id, String name) throws SQLException {

	String query = "UPDATE MEMBER SET NAME =? WHERE ID=?";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setString(1, name);

	    myStmt.setInt(2, id);

	    int affectedRows = myStmt.executeUpdate(query);

	    return affectedRows > 0;
	}
    }

    /*
     * public boolean updateMember(Connection con, int id, int donated, int
     * borrowed) throws SQLException {
     * 
     * String query = "UPDATE member SET borrowed=?, donated=? WHERE ID=?";
     * 
     * try (PreparedStatement myStmt = con.prepareStatement(query)) {
     * 
     * myStmt.setInt(1, borrowed);
     * 
     * myStmt.setInt(2, donated);
     * 
     * int affectedRows = myStmt.executeUpdate(query);
     * 
     * return affectedRows > 0; } }
     */

    // BURAYI SOR ID NEREDEN GELİYOR
    /*
     * public boolean updateBook(Connection con, int bookID, int memberID) throws
     * SQLException {
     * 
     * String query = "UPDATE books SET borrowed_by=? WHERE id=?";
     * 
     * try (PreparedStatement myStmt = con.prepareStatement(query)) {
     * 
     * myStmt.setInt(1, memberID);
     * 
     * int affectedRows = myStmt.executeUpdate(query);
     * 
     * return affectedRows > 0; } }
     */

    /*
     * public boolean setBorrowStatus(Connection con, int bookID) throws
     * SQLException {
     * 
     * String query = "UPDATE books SET borrowed_by= WHERE id=?";
     * 
     * try (PreparedStatement myStmt = con.prepareStatement(query)) {
     * 
     * int affectedRows = myStmt.executeUpdate(query);
     * 
     * return affectedRows > 0; } }
     */

    // RETURN 1=> Kitap dışarda ise kulübe geri verir.

    public boolean deleteBook(Connection con, int id) throws SQLException {
	String query = "DELETE FROM books WHERE id=? ";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setInt(1, id);

	    int affectedRows = myStmt.executeUpdate(query);

	    return affectedRows > 0;

	}
    }

    public boolean searchBook(Connection con, String bookName) {

	String query = "SELECT * FROM books WHERE name LIKE '%?%' ";
	return false;
    }

}
