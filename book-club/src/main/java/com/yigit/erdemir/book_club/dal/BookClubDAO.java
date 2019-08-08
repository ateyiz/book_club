package com.yigit.erdemir.book_club.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	String query = "SELECT * FROM BOOKS WHERE ID = ?";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setInt(1, id);

	    BookDTO bookDTO = new BookDTO();

	    try (ResultSet rs = myStmt.executeQuery()) {

		while (rs.next()) {

		    bookDTO.setId(rs.getInt("ID"));

		    bookDTO.setName(rs.getString("NAME"));

		    bookDTO.setDonatedBy(rs.getInt("DONATED_BY"));

		}
	    }
	    return bookDTO;
	}
    }

    public List<HistoryDTO> getBookHistory(Connection con, int bookID) throws SQLException {

	String query = "SELECT * FROM HISTORY WHERE BOOK_ID = ?";

	List<HistoryDTO> historyList = new ArrayList<HistoryDTO>();

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setInt(1, bookID);

	    HistoryDTO historyDTO = new HistoryDTO();

	    try (ResultSet rs = myStmt.executeQuery()) {

		while (rs.next()) {

		    historyDTO.setBookID(rs.getInt("BOOK_ID"));

		    historyDTO.setDonatedBy(rs.getInt("DONATED_BY"));

		    historyDTO.setDateBorrowed(rs.getDate("DATE_BORROWED"));

		    historyDTO.setDateDonated(rs.getDate("DATE_DONATED"));

		    historyDTO.setDateReturned(rs.getDate("DATE_RETURNED"));

		    historyList.add(historyDTO);

		}
	    }

	    return historyList;
	}
    }

    public List<BookDTO> searchBook(Connection con, String bookname) throws SQLException {

	String query = "SELECT * FROM BOOKS WHERE NAME LIKE ?";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    myStmt.setString(1, "%" + bookname + "%");
	    try (ResultSet rs = myStmt.executeQuery()) {

		List<BookDTO> bookList = new ArrayList<BookDTO>();

		BookDTO bookDTO = new BookDTO();

		while (rs.next()) {

		    bookDTO.setId(rs.getInt("ID"));

		    bookDTO.setName(rs.getString("NAME"));

		    bookDTO.setAuthor(rs.getString("AUTHOR"));

		    bookDTO.setDonatedBy(rs.getInt("DONATED_BY"));

		    bookList.add(bookDTO);

		}
		return bookList;
	    }
	}

    }

    public List<HistoryDTO> getMemberHistory(Connection con, int id) throws SQLException {

	String query = "SELECT * FROM HISTORY WHERE DONATED_BY = ?";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setInt(1, id);

	    List<HistoryDTO> historyList = new ArrayList<HistoryDTO>();

	    try (ResultSet rs = myStmt.executeQuery()) {

		while (rs.next()) {

		    HistoryDTO historyDTO = new HistoryDTO();

		    historyDTO.setDonatedBy(rs.getInt("DONATED_BY"));

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

    public int insertBook(Connection con, String bookname, String author, int donatedBy, Date firstPublished)
	    throws SQLException {
	int insertedID = 0;

	String query = "INSERT INTO BOOKS(NAME, AUTHOR, DONATED_BY,FIRST_PUBLISHED) " + "VALUES(?,?,?,?)";

	try (PreparedStatement myStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

	    myStmt.setString(1, bookname);

	    myStmt.setString(2, author);

	    myStmt.setInt(3, donatedBy);

	    if (firstPublished != null) {
		myStmt.setDate(4, new java.sql.Date(firstPublished.getTime()));
	    } else
		myStmt.setDate(4, null);

	    int affectedRows = myStmt.executeUpdate();

	    boolean success = affectedRows > 0;

	    if (success) {
		ResultSet rs = myStmt.getGeneratedKeys();
		if (rs.next()) {
		    insertedID = rs.getInt(1);
		}

	    }
	    return insertedID;

	}
    }

    public boolean insertHistory(Connection con, int book_id, int donatedBy, Date dateDonated) throws SQLException {

	String query = "INSERT INTO HISTORY(BOOK_ID, DONATED_BY, DATE_DONATED) VALUES (?,?,?)";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setInt(1, book_id);

	    myStmt.setInt(2, donatedBy);
	    java.sql.Date d = new java.sql.Date(dateDonated.getTime());
	    myStmt.setDate(3, d);

	    int affectedRows = myStmt.executeUpdate();

	    return affectedRows > 0;
	}
    }

    public boolean updateMember(Connection con, int id, String name) throws SQLException {

	String query = "UPDATE MEMBERS SET NAME =? WHERE ID=?";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setString(1, name);

	    myStmt.setInt(2, id);

	    int affectedRows = myStmt.executeUpdate();

	    return affectedRows > 0;
	}
    }

    public boolean updateMember(Connection con, int id, int donated, int borrowed) throws SQLException {

	String query = "UPDATE MEMBERS SET BORROWED=?, DONATED=? WHERE ID=?";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setInt(1, borrowed);

	    myStmt.setInt(2, donated);

	    myStmt.setInt(3, id);

	    int affectedRows = myStmt.executeUpdate();

	    return affectedRows > 0;
	}
    }

    // BURAYI SOR ID NEREDEN GELİYOR

    public boolean updateBook(Connection con, int bookID, int memberID) throws SQLException {

	String query = "UPDATE BOOKS SET BORROWED_BY=? WHERE id=?";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setInt(1, memberID);

	    myStmt.setInt(2, bookID);

	    int affectedRows = myStmt.executeUpdate();

	    return affectedRows > 0;
	}
    }

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
	String query = "DELETE FROM BOOKS WHERE id=? ";

	try (PreparedStatement myStmt = con.prepareStatement(query)) {

	    myStmt.setInt(1, id);

	    int affectedRows = myStmt.executeUpdate(query);

	    return affectedRows > 0;

	}
    }

}
