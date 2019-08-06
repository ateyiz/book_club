package com.yigit.erdemir.book_club.bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yigit.erdemir.book_club.dal.BookClubDAO;
import com.yigit.erdemir.book_club.dal.MemberDTO;

public class BookClubBO {

    private BookClubDAO bookClubDAO;

    String url = "jdbc:derby:C:\\Users\\erdemiryigit\\MyDB\\book_club";

    public BookClubBO() {
	this.bookClubDAO = new BookClubDAO();
    }

    // REGISTER YIGIT=> YIGIT isimli kullanıcı yaratır. Idsini ekrana döner.
    public int registerMember(String memberName) throws SQLException {

	int id = -1;

	try (Connection con = DriverManager.getConnection(url)) {
	    boolean isInserted = bookClubDAO.insertMember(con, memberName);

	    if (isInserted) {
		con.commit();
		MemberDTO memberDTO = bookClubDAO.getMember(con, memberName);

		id = memberDTO.getId();
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	    throw new SQLException("Could NOT register member!");
	}

	return id;

    }

    // ACCEPT YIGIT ILLIAD HOMER=> YIGITten ILLIAD isimli HOMER tarafından yazılmış
    // kitabı alıp kulube katar. Kitap idsini verir.

    public int addBook(String username, String bookname, String author, Date firstPublished) throws SQLException {
	try (Connection con = DriverManager.getConnection(url)) {

	    MemberDTO memberDTO = bookClubDAO.getMember(con, username);

	    int id = memberDTO.getId();

	    return bookClubDAO.insertBook(con, bookname, author, id, firstPublished);

	} catch (SQLException ex) {
	    ex.printStackTrace();
	    throw new SQLException("ERROR,could NOT add book.");

	}

    }

    // RENAME MEMBER YIGIT YIGIT.E => Yiğit kullanıcısının ismi değişir. Kayıtları
    // etkilenmez.

    public boolean renameMember(String memberName, String newName) throws SQLException {

	try (Connection con = DriverManager.getConnection(url)) {

	    MemberDTO memberDTO = bookClubDAO.getMember(con, memberName);

	    int id = memberDTO.getId();

	    boolean success = bookClubDAO.updateMember(con, id, newName);

	    if (success) {
		con.commit();
	    }

	    return success;

	} catch (SQLException ex) {
	    ex.printStackTrace();
	    throw new SQLException("Member could NOT be renamed!");
	}
    }

    public List<Member> showAllMembers() throws Exception {
	try (Connection con = DriverManager.getConnection(url)) {

	    List<MemberDTO> data = bookClubDAO.getAllMembers(con);

	    List<Member> allMembers = new ArrayList<Member>();

	    for (MemberDTO item : data) {
		Member member = new Member();
		member.setId(item.getId());
		member.setMembersince(item.getMemberSince());
		member.setName(item.getName());
		allMembers.add(member);
	    }
	    return allMembers;
	} catch (Exception ex) {
	    ex.printStackTrace();
	    throw new Exception("Error, could NOT get all members!");
	}
    }

    // BORROW YIGIT 1=> eğer kulupte ise 1 idli kitabı YIGIT’e verir değilse hata
    // verir. Aynı anda bağışladığından fazla kitap alamaz.

    /*
     * public boolean borrowBook(String memberName, int[] bookIDS) throws
     * SQLException {
     * 
     * try (Connection con = DriverManager.getConnection(url)) {
     * 
     * MemberDTO memberDTO = bookClubDAO.getMember(con, memberName); if
     * (memberDTO.getDonated() < memberDTO.getBorrowed() + bookIDS.length) { throw
     * new SQLException("ERROR you can NOT get more than you have donated"); }
     * 
     * for (int i = 0; i < bookIDS.length; i++) { BookDTO bookDTO =
     * bookClubDAO.getBook(con, bookIDS[i]); if (!bookDTO.isBorrowed()) { boolean
     * updated = bookClubDAO.updateBook(con, bookIDS[i], memberDTO.getId()); if
     * (!updated) { throw new SQLException("an ERROR occured while updating book: "
     * + bookDTO.getName()); } } else { throw new SQLException(bookDTO.getName() +
     * " is already borrowed!"); }
     * 
     * } boolean success = bookClubDAO.updateMember(con, memberDTO.getId(),
     * memberDTO.getDonated(), memberDTO.getBorrowed() + bookIDS.length); if
     * (success) { con.commit();
     * 
     * } return success; } }
     */

    // RETURN 1=> Kitap dışarda ise kulübe geri verir.
    public boolean returnBook(int bookID) {
	try (Connection con = DriverManager.getConnection(url)) {
	    // BookDTO bookDTO = bookClubDAO.updateBook(con, bookID, name))

	} catch (Exception ex) {

	}
	return false;

    }

    // DELETE BOOK=> kitabı tüm kayıtları ile birlikte siler.

    public boolean deleteBook(int bookID) throws SQLException {
	try (Connection con = DriverManager.getConnection(url)) {

	    boolean success = bookClubDAO.deleteBook(con, bookID);
	    con.commit();
	    return success;

	} catch (Exception ex) {
	    ex.printStackTrace();
	    throw new SQLException("Error, could NOT get all members!");
	}
    }

    public Book findBook(String bookName) {
	return null;
    }

    // HISTORY BOOK 1 =>1 idli kitabın hangi kullanıcı tarafından ne zaman
    // alındığını, geri getirildiğini basar. Kitap bilgileri ile

    /*
     * public History[] getBookHistory(int id) throws Exception { try (Connection
     * con = DriverManager.getConnection(url)) {
     * 
     * HistoryDTO hist = bookClubDAO.getHistory(con, id); History h = new History();
     * return h; // return success;
     * 
     * } catch (Exception ex) { ex.printStackTrace(); throw new
     * Exception("Error, could NOT get all members!"); } }
     */

}
