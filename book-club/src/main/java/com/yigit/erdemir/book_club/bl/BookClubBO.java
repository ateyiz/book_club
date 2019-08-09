package com.yigit.erdemir.book_club.bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yigit.erdemir.book_club.dal.BookClubDAO;
import com.yigit.erdemir.book_club.dal.BookDTO;
import com.yigit.erdemir.book_club.dal.MemberDTO;

public class BookClubBO {
    private BookClubDAO bookClubDAO;
    String url = "jdbc:derby:C:\\Users\\erdemiryigit\\MyDB\\book_club";

    public BookClubBO() {
	this.bookClubDAO = new BookClubDAO();
    }

    // REGISTER YIGIT=> YIGIT isimli kullanıcı yaratır. Idsini ekrana döner.
    public int registerMember(String membername) throws Exception {
	int id = -1;
	try (Connection con = DriverManager.getConnection(url)) {
	    try {
		boolean isInserted = bookClubDAO.insertMember(con, membername);
		if (isInserted) {
		    con.commit();
		    MemberDTO memberDTO = bookClubDAO.getMember(con, membername);
		    id = memberDTO.getId();
		}
		return id;
	    } catch (Exception ex) {
		con.rollback();
		ex.printStackTrace();
		throw new Exception("Could NOT register member!");
	    }
	}

    }

    // SHOW MEMBER=>tüm üye id ve isimlerini üye olma tarihlerini döner.
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

    // SHOW BOOKS=>tüm kitap id ve isimlerini tarihlerini döner.
    public List<Book> showAllBooks() throws Exception {
	try (Connection con = DriverManager.getConnection(url)) {
	    List<BookDTO> data = bookClubDAO.getAllBooks(con);
	    List<Book> allBooks = new ArrayList<Book>();
	    for (BookDTO item : data) {
		Book book = new Book();
		book.setId(item.getId());
		book.setName(item.getName());
		book.setAuthor(item.getAuthor());
		book.setFirstPublished(item.getFirstPublished());
		book.setBorrowedBy(item.getBorrowedBy());
		allBooks.add(book);
	    }
	    return allBooks;
	} catch (Exception ex) {
	    ex.printStackTrace();
	    throw new Exception("Error, could NOT get all books!");
	}
    }

    // FIND BOOK IL=> içinde IL geçen kitapların id isim yazar tarih bağışlayan
    // bağışlanma ödünç ise kimde olduğu ne zaman verildiğini döner.
    public List<Book> findBooks(String bookname) throws Exception {
	try (Connection con = DriverManager.getConnection(url)) {
	    List<BookDTO> data = bookClubDAO.searchBook(con, bookname);
	    List<Book> allBooks = new ArrayList<Book>();

	    for (BookDTO item : data) {
		Book book = new Book();
		book.setId(item.getId());
		book.setName(item.getName());
		book.setAuthor((item.getAuthor()));
		book.setFirstPublished(item.getFirstPublished());
		book.setDonator(item.getDonator());
		book.setDonatedBy(item.getDonatedBy());
		book.setLastBorrowedDate(item.getLastBorrowedDate());
		book.setBorrowedBy(item.getBorrowedBy());
		Date d = item.getDateDonated();
		book.setDateDonated(d);
		allBooks.add(book);
	    }
	    return allBooks;

	} catch (Exception ex) {
	    ex.printStackTrace();
	    throw new Exception("Book could NOT be found!");
	}
    }

    // ACCEPT YIGIT ILLIAD HOMER=> YIGITten ILLIAD isimli HOMER tarafından yazılmış
    // kitabı alıp kulube katar. Kitap idsini verir.
    // ACCEPT YIGIT IT KING 1986=> YIGITten IT isimli KING tarafından 1986 yazılmış
    // kitabı Kabul eder.
    // Kitap idsini verir.
    public int addBook(String[] h) throws Exception, ParseException {
	try (Connection con = DriverManager.getConnection(url)) {
	    MemberDTO memberDTO = bookClubDAO.getMember(con, h[1]);
	    int insBookID = 0;
	    int donatedBy = memberDTO.getId();
	    int currentDonated = memberDTO.getDonated();
	    int currentBorrowed = memberDTO.getBorrowed();
	    String bookname = h[2];
	    String author = h[3];
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
	    try {
		if (h.length < 5) {
		    insBookID = bookClubDAO.insertBook(con, bookname, author, donatedBy, null);
		} else {
		    Date firstPublished = sdf.parse(h[4]);
		    insBookID = bookClubDAO.insertBook(con, bookname, author, donatedBy, firstPublished);
		}
		boolean isUpdated = bookClubDAO.updateMember(con, donatedBy, currentDonated + 1, currentBorrowed);
		if (isUpdated && insBookID != 0) {
		    con.commit();
		} else {
		    con.rollback();
		    // throw new Exception("ERROR,could NOT add book.");
		}
	    } catch (Exception ex) {
		con.rollback();
		throw new Exception("Member does NOT exist!");
	    }
	    return insBookID;
	} catch (Exception ex) {
	    ex.printStackTrace();
	    throw new Exception("ERROR,could NOT add book.");
	}
    }

    // RENAME MEMBER YIGIT YIGIT.E => Yiğit kullanıcısının ismi değişir. Kayıtları
    // etkilenmez.
    public boolean renameMember(String membername, String newName) throws Exception {
	try (Connection con = DriverManager.getConnection(url)) {
	    MemberDTO memberDTO = bookClubDAO.getMember(con, membername);
	    int id = memberDTO.getId();
	    try {
		boolean success = bookClubDAO.updateMember(con, id, newName);
		if (success) {
		    con.commit();
		} else
		    con.rollback();
		return success;
	    } catch (Exception ex) {
		con.rollback();
		ex.printStackTrace();
		throw new Exception("Member could NOT be renamed!");
	    }
	}
    }

    // BORROW YIGIT 1=> eğer kulupte ise 1 idli kitabı YIGIT’e verir değilse hata
    // verir. Aynı anda bağışladığından fazla kitap alamaz.
    public boolean borrowBook(String membername, int[] bookIDS) throws Exception {
	try (Connection con = DriverManager.getConnection(url)) {
	    MemberDTO memberDTO = bookClubDAO.getMember(con, membername);
	    if (memberDTO.getDonated() < memberDTO.getBorrowed() + bookIDS.length) {
		throw new Exception("ERROR you can NOT get more books than you have donated");
	    }
	    for (int i = 0; i < bookIDS.length; i++) {
		BookDTO bookDTO = bookClubDAO.getBook(con, bookIDS[i]);
		if (bookDTO != null && bookDTO.getBorrowedBy() == 0) {
		    bookClubDAO.updateBook(con, bookIDS[i], memberDTO.getId());
		} else {
		    con.rollback();
		    throw new Exception("id " + bookIDS[i] + " NOT found or borrowed");
		}
	    }
	    boolean success = bookClubDAO.updateMember(con, memberDTO.getId(), memberDTO.getDonated(),
		    memberDTO.getBorrowed() + bookIDS.length);
	    if (success) {
		con.commit();
	    } else
		con.rollback();
	    return success;
	}
    }

    // RETURN 1=> Kitap dışarda ise kulübe geri verir.
    public boolean returnBook(int bookID) {
	boolean isUpdated = false;
	boolean isReturned = false;
	try (Connection con = DriverManager.getConnection(url)) {
	    MemberDTO memberDTO = bookClubDAO.findBorrower(con, bookID);
	    if (memberDTO != null) {
		isReturned = bookClubDAO.returnBook(con, bookID);
		if (isReturned) {
		    isUpdated = bookClubDAO.updateMember(con, memberDTO.getId(), memberDTO.getDonated(),
			    memberDTO.getBorrowed() - 1);
		    if (isUpdated) {
			con.commit();
		    } else {
			con.rollback();
			throw new Exception("Book could NOT be returned");
		    }
		} else {
		    con.rollback();
		    throw new Exception("Book could NOT be returned");
		}
	    } else {
		con.rollback();
		throw new Exception("This book is NOT borrowed thus it could NOT be returned");
	    }
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	boolean success = (isUpdated && isReturned);
	return success;
    }

    // DELETE BOOK=> kitabı tüm kayıtları ile birlikte siler.
    public boolean deleteBook(int bookID) throws Exception {
	try (Connection con = DriverManager.getConnection(url)) {
	    try {
		boolean updateDonator = false;
		boolean updateBorrower = false;
		BookDTO bookDTO = bookClubDAO.getBook(con, bookID);
		MemberDTO donator = bookClubDAO.getMember(con, bookDTO.getDonatedBy());
		updateDonator = bookClubDAO.updateMember(con, donator.getId(), donator.getDonated() - 1,
			donator.getBorrowed());
		if (bookDTO.getBorrowedBy() != 0) {
		    MemberDTO borrower = bookClubDAO.findBorrower(con, bookID);
		    updateBorrower = bookClubDAO.updateMember(con, borrower.getId(), borrower.getDonated(),
			    borrower.getBorrowed() - 1);
		}
		boolean deleteBook = bookClubDAO.deleteBook(con, bookID);
		boolean success = (deleteBook && updateBorrower && updateDonator);
		if (success) {
		    con.commit();
		} else
		    con.rollback();
		return success;
	    } catch (Exception ex) {
		con.rollback();
		ex.printStackTrace();
		throw new Exception("Error, could NOT delete book!");
	    }
	}
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
