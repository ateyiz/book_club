package com.yigit.erdemir.book_club;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.yigit.erdemir.book_club.bl.Book;
import com.yigit.erdemir.book_club.bl.BookClubBO;
import com.yigit.erdemir.book_club.bl.CreateTable;
import com.yigit.erdemir.book_club.bl.Member;

public class App {

    public static void main(String[] args) {

	try {
	    new CreateTable();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	String command;
	Scanner s = new Scanner(System.in);
	BookClubBO operation = new BookClubBO();

	while (true) {
	    try {
		System.out.println("Make your wish");
		command = s.nextLine();
		String[] commands = command.split(" ");
		if ("REGISTER".equalsIgnoreCase(commands[0])) {
		    System.out
			    .println("User has been registered! User ID is: " + operation.registerMember(commands[1]));
		}
		if ("SHOW".trim().equalsIgnoreCase(commands[0]) && "MEMBER".trim().equalsIgnoreCase(commands[1])) {
		    List<Member> members = operation.showAllMembers();
		    System.out.println("ID\t" + "  NAME\t" + "MEMBER SINCE");
		    for (Member member : members) {
			int id = member.getId();
			String name = member.getName();
			Date membersince = member.getMemberSince();
			String w = String.format("%1$td.%1$tm.%1$ty %n", membersince);
			System.out.printf("%d\t %s\t %s", id, name, w);
		    }
		}
		if ("SHOW".trim().equalsIgnoreCase(commands[0]) && "BOOKS".trim().equalsIgnoreCase(commands[1])) {
		    List<Book> books = operation.showAllBooks();
		    System.out.println("ID\t" + "  NAME\t\t" + "AUTHOR\t" + "FIRST PUBLISHED\t\t" + "BORROWED BY");
		    for (Book book : books) {
			int id = book.getId();
			String name = book.getName();
			String author = book.getAuthor();
			Date firstPublished = book.getFirstPublished();
			int borrowedBy = book.getBorrowedBy();
			String fp = "Date Unspecified";
			if (firstPublished != null) {
			    fp = String.format("%1$td.%1$tm.%1$ty", firstPublished);
			}
			System.out.printf("%d\t %s\t\t %s\t %s\t\t %d\n", id, name, author, fp, borrowedBy);
		    }
		}
		if ("ACCEPT".trim().equalsIgnoreCase(commands[0])) {
		    System.out.println("Book has been added to the library, Book ID is " + operation.addBook(commands));
		}
		if ("FIND".trim().equalsIgnoreCase(commands[0]) && "BOOK".trim().equalsIgnoreCase(commands[1])) {
		    List<Book> books = operation.findBooks(commands[2]);
		    System.out.println("ID\t" + "NAME\t" + "AUTHOR\t\t" + "FIRST_PUBLISHED\t\t" + "DONATED_BY\t"
			    + "DATE_DONATED\t" + "BORROWED_BY\t" + "DATE_BORROWED\t");
		    for (Book book : books) {
			int id = book.getId();
			String name = book.getName();
			String author = book.getAuthor();
			Date firstPublished = book.getFirstPublished();
			String fp = null;
			if (firstPublished != null) {
			    fp = String.format("%1$td.%1$tm.%1$ty", firstPublished);
			}
			String donatedBy = book.getDonator() + " (id=" + book.getDonatedBy() + ")";

			Date dateDonated = book.getDateDonated();

			String dd = null;
			if (dateDonated != null) {
			    dd = String.format("%1$td.%1$tm.%1$ty", dateDonated);
			}
			int borrowedBy = book.getBorrowedBy();
			Date dateBorrowed = book.getLastBorrowedDate();
			String db = null;
			if (dateBorrowed != null) {
			    db = String.format("%1$td.%1$tm.%1$ty", dateBorrowed);
			}
			System.out.printf("%d\t %s\t %s\t\t %s\t\t %s\t %s\t %d\t\t %s\n", id, name, author, fp,
				donatedBy, dd, borrowedBy, db);
		    }

		}
		if ("BORROW".trim().equalsIgnoreCase(commands[0])) {
		    String username = commands[1];
		    int[] books = new int[commands.length - 2];
		    for (int i = 0; i < commands.length - 2; i++) {
			books[i] = Integer.parseInt(commands[i + 2]);
		    }
		    if (operation.borrowBook(username, books)) {
			System.out.println("User has borrowed the book(s)");
		    } else
			System.out.println("User could NOT borrow the book(s)");
		}
		if ("RETURN".trim().equalsIgnoreCase(commands[0])) {
		    if (operation.returnBook(Integer.parseInt(commands[1]))) {
			System.out.println("Book has been successfully returned!");
		    } else
			System.out.println("Book has NOT been successfully returned!");
		}

		if ("HISTORY".trim().equalsIgnoreCase(commands[0])) {
		    if ("BOOK".trim().equalsIgnoreCase(commands[1])) {
		    }
		}
		if ("RENAME".trim().equalsIgnoreCase(commands[0])) {
		    if (operation.renameMember(commands[2], commands[3])) {
			System.out.println(
				"MEMBER HAS BEEN SUCCESSFULLY RENAMED FROM " + commands[2] + " TO " + commands[3]);
		    }
		}

		if ("DELETE".trim().equalsIgnoreCase(commands[0])) {
		    if ("BOOK".trim().equalsIgnoreCase(commands[1])) {
			if (operation.deleteBook(Integer.parseInt(commands[2])))
			    System.out.println("The book has been successfully deleted!");
			else
			    System.out.println("The book has NOT been successfully deleted!");

		    }
		}
		if ("EXIT".trim().equalsIgnoreCase(commands[0])) {
		    System.exit(0);
		}

	    } catch (Exception ex) {
		ex.printStackTrace();

	    }
	}
    }
}
