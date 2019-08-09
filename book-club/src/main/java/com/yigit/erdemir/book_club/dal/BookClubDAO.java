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
	    MemberDTO memberDTO = null;
	    try (ResultSet rs = myStmt.executeQuery()) {
		memberDTO = new MemberDTO();
		while (rs.next()) {
		    memberDTO.setId(rs.getInt("ID"));
		    memberDTO.setName(rs.getString("NAME"));
		    memberDTO.setDonated(rs.getInt("DONATED"));
		    memberDTO.setBorrowed(rs.getInt("BORROWED"));
		    memberDTO.setMemberSince(rs.getDate("MEMBER_SINCE"));
		}
		return memberDTO;
	    }
	}
    }

    public MemberDTO getMember(Connection con, int id) throws SQLException {
	String query = "SELECT * FROM MEMBERS WHERE ID = ?";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    myStmt.setInt(1, id);
	    MemberDTO memberDTO = null;
	    try (ResultSet rs = myStmt.executeQuery()) {
		memberDTO = new MemberDTO();
		while (rs.next()) {
		    memberDTO.setId(rs.getInt("ID"));
		    memberDTO.setName(rs.getString("NAME"));
		    memberDTO.setDonated(rs.getInt("DONATED"));
		    memberDTO.setBorrowed(rs.getInt("BORROWED"));
		    memberDTO.setMemberSince(rs.getDate("MEMBER_SINCE"));
		}
		return memberDTO;
	    }

	}
    }

    public BookDTO getBook(Connection con, int id) throws SQLException {
	String query = "SELECT * FROM BOOKS WHERE ID = ?";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    myStmt.setInt(1, id);
	    BookDTO bookDTO = null;
	    try (ResultSet rs = myStmt.executeQuery()) {
		bookDTO = new BookDTO();
		while (rs.next()) {
		    bookDTO.setId(rs.getInt("ID"));
		    bookDTO.setName(rs.getString("NAME"));
		    bookDTO.setDonatedBy(rs.getInt("DONATED_BY"));
		    bookDTO.setBorrowedBy(rs.getInt("BORROWED_BY"));
		}
	    }
	    return bookDTO;
	}
    }

    public List<BookDTO> searchBook(Connection con, String bookname) throws SQLException {
	String query = "SELECT B.*,M.NAME AS DONATOR FROM BOOKS B INNER JOIN MEMBERS M ON B.DONATED_BY=M.ID WHERE B.NAME LIKE ?";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    myStmt.setString(1, "%" + bookname + "%");
	    try (ResultSet rs = myStmt.executeQuery()) {
		List<BookDTO> bookList = new ArrayList<BookDTO>();

		while (rs.next()) {
		    BookDTO bookDTO = new BookDTO();
		    bookDTO.setId(rs.getInt("ID"));
		    bookDTO.setName(rs.getString("NAME"));
		    bookDTO.setAuthor(rs.getString("AUTHOR"));
		    bookDTO.setFirstPublished(rs.getDate("FIRST_PUBLISHED"));
		    bookDTO.setDonatedBy(rs.getInt("DONATED_BY"));
		    bookDTO.setDonator(rs.getString("DONATOR"));
		    bookDTO.setDateDonated(rs.getDate("DATE_DONATED"));
		    bookDTO.setBorrowedBy(rs.getInt("BORROWED_BY"));
		    bookDTO.setLastBorrowedDate(rs.getDate("LAST_BORROWED_DATE"));
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
	    try (ResultSet rs = myStmt.executeQuery()) {
		List<HistoryDTO> historyList = new ArrayList<HistoryDTO>();
		while (rs.next()) {
		    HistoryDTO historyDTO = new HistoryDTO();
		    historyDTO.setDonatedBy(rs.getInt("DONATED_BY"));
		    historyDTO.setBookID(rs.getInt("BOOK_ID"));
		    historyDTO.setDateBorrowed(rs.getDate("DATE_BORROWED"));
		    historyDTO.setDateDonated(rs.getDate("DATE_DONATED"));
		    historyDTO.setDateReturned(rs.getDate("DATE_RETURNED"));
		    historyList.add(historyDTO);
		}
		return historyList;
	    }
	}
    }

    public List<HistoryDTO> getBookHistory(Connection con, int bookID) throws SQLException {
	String query = "SELECT * FROM HISTORY WHERE BOOK_ID = ?";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    myStmt.setInt(1, bookID);
	    try (ResultSet rs = myStmt.executeQuery()) {
		List<HistoryDTO> historyList = new ArrayList<HistoryDTO>();
		HistoryDTO historyDTO = new HistoryDTO();
		while (rs.next()) {
		    historyDTO.setBookID(rs.getInt("BOOK_ID"));
		    historyDTO.setDonatedBy(rs.getInt("DONATED_BY"));
		    historyDTO.setDateBorrowed(rs.getDate("DATE_BORROWED"));
		    historyDTO.setDateDonated(rs.getDate("DATE_DONATED"));
		    historyDTO.setDateReturned(rs.getDate("DATE_RETURNED"));
		    historyList.add(historyDTO);
		}
		return historyList;
	    }
	}
    }

    public List<MemberDTO> getAllMembers(Connection con) throws SQLException {
	String query = "SELECT * FROM MEMBERS";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    try (ResultSet rs = myStmt.executeQuery()) {
		List<MemberDTO> memberList = new ArrayList<MemberDTO>();
		while (rs.next()) {
		    MemberDTO memberDTO = new MemberDTO();
		    memberDTO.setId(rs.getInt("ID"));
		    memberDTO.setName(rs.getString("NAME"));
		    memberDTO.setMemberSince(rs.getDate("MEMBER_SINCE"));
		    memberList.add(memberDTO);
		}
		return memberList;
	    }
	}
    }

    public List<BookDTO> getAllBooks(Connection con) throws SQLException {
	String query = "SELECT * FROM BOOKS";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    try (ResultSet rs = myStmt.executeQuery()) {
		List<BookDTO> bookList = new ArrayList<BookDTO>();
		while (rs.next()) {
		    BookDTO bookDTO = new BookDTO();
		    bookDTO.setId(rs.getInt("ID"));
		    bookDTO.setName(rs.getString("NAME"));
		    bookDTO.setAuthor(rs.getString("AUTHOR"));
		    bookDTO.setFirstPublished((rs.getDate("FIRST_PUBLISHED")));
		    bookDTO.setDonatedBy(rs.getInt("DONATED_BY"));
		    bookDTO.setBorrowedBy(rs.getInt("BORROWED_BY"));
		    bookDTO.setLastBorrowedDate(rs.getDate("LAST_BORROWED_DATE"));
		    bookList.add(bookDTO);
		}
		return bookList;
	    }
	}
    }

    public boolean insertMember(Connection con, String name) throws SQLException {
	String query = "INSERT INTO MEMBERS(NAME, MEMBER_SINCE) VALUES (?,?)";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    myStmt.setString(1, name);
	    myStmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
	    int affectedRows = myStmt.executeUpdate();
	    return affectedRows > 0;
	}
    }

    public int insertBook(Connection con, String bookname, String author, int donatedBy, Date firstPublished)
	    throws SQLException {
	int insertedID = 0;
	String query = "INSERT INTO BOOKS(NAME, AUTHOR, DONATED_BY,FIRST_PUBLISHED,DATE_DONATED) "
		+ "VALUES(?,?,?,?,?)";
	try (PreparedStatement myStmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	    myStmt.setString(1, bookname);
	    myStmt.setString(2, author);
	    myStmt.setInt(3, donatedBy);
	    if (firstPublished != null) {
		myStmt.setDate(4, new java.sql.Date(firstPublished.getTime()));
	    } else
		myStmt.setDate(4, null);

	    java.util.Date today = new java.util.Date();
	    myStmt.setDate(5, new java.sql.Date(today.getTime()));
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

    public MemberDTO findBorrower(Connection con, int bookid) throws SQLException {
	String query = "SELECT M.* FROM BOOKS B INNER JOIN MEMBERS M ON B.BORROWED_BY = M.ID WHERE B.ID=?";
	MemberDTO memberDTO = null;
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    myStmt.setInt(1, bookid);
	    try (ResultSet rs = myStmt.executeQuery()) {
		memberDTO = new MemberDTO();
		while (rs.next()) {
		    memberDTO.setId(rs.getInt("ID"));
		    memberDTO.setName(rs.getString("NAME"));
		    memberDTO.setDonated(rs.getInt("DONATED"));
		    memberDTO.setBorrowed(rs.getInt("BORROWED"));
		    memberDTO.setMemberSince(rs.getDate("MEMBER_SINCE"));
		}
	    }
	}
	return memberDTO;

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

    public boolean returnBook(Connection con, int bookID) throws SQLException {
	String query = "UPDATE BOOKS SET BORROWED_BY=null,LAST_BORROWED_DATE = null WHERE ID=? AND BORROWED_BY IS NOT NULL ";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    myStmt.setInt(1, bookID);
	    int affectedRows = myStmt.executeUpdate();
	    return affectedRows > 0;
	}
    }

    public boolean updateBook(Connection con, int bookID, int memberID) throws SQLException {
	String query = "UPDATE BOOKS SET BORROWED_BY=?,LAST_BORROWED_DATE = ? WHERE ID=? ";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    myStmt.setInt(1, memberID);
	    myStmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
	    myStmt.setInt(3, bookID);
	    int affectedRows = myStmt.executeUpdate();
	    return affectedRows > 0;
	}
    }

    public boolean deleteBook(Connection con, int id) throws SQLException {
	String query = "DELETE FROM BOOKS WHERE ID=? ";
	try (PreparedStatement myStmt = con.prepareStatement(query)) {
	    myStmt.setInt(1, id);
	    int affectedRows = myStmt.executeUpdate();
	    return affectedRows > 0;
	}
    }

}
