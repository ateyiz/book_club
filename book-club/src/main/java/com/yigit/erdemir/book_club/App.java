package com.yigit.erdemir.book_club;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.yigit.erdemir.book_club.bl.BookClubBO;
import com.yigit.erdemir.book_club.bl.CreateTable;
import com.yigit.erdemir.book_club.bl.History;
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
		System.out.println("Enter sth");
		command = s.nextLine();
		String[] commands = command.split(" ");
		if ("REGISTER".equalsIgnoreCase(commands[0])) {

		    System.out
			    .println("User has been registered! User ID is: " + operation.registerMember(commands[1]));
		}

		if ("SHOW".trim().equalsIgnoreCase(commands[0]) && "MEMBER".trim().equalsIgnoreCase(commands[1])) {
		    List<Member> members = operation.showAllMembers();
		    System.out.println("ID\t" + "  NAME\t" + "MEMBER SINCE\t");
		    for (Member member : members) {
			int id = member.getId();
			String name = member.getName();
			Date membersince = member.getMemberSince();
			String w = String.format("%1$td.%1$tm.%1$ty %n", membersince);
			// System.out.printf("ID = %d , NAME= %s, MEMBER SINCE= %s ", id, name, w);
			System.out.printf("%d\t %s\t %s", id, name, w);
		    }

		}
		// MEMBERS TABLOSUNDA COMMAND[1] İSİMLİ ÜYENİN DONATED DEĞERİ 1 ARTIRILACAK
		// BOOKS TABLOSUNA DONATED_BY EKLENECEK
		if ("ACCEPT".trim().equalsIgnoreCase(commands[0])) {

		    // DateFormat format = new SimpleDateFormat("dd, mm, yyyy", Locale.ENGLISH);
		    // Date d1 = format.parse(date);
		    System.out.println("Book has been added to the library, Book ID is " + operation.addBook(commands));

		}

		if ("FIND".trim().equalsIgnoreCase(commands[0])) {
		    if ("BOOK".trim().equalsIgnoreCase(commands[1])) {
			List<History> histories = operation.findBooks(commands[2]);
			int size = histories.size();
			System.out.println("ID\t" + "NAME\t" + "AUTHOR\t" + "FIRST_PUBLISHED\t" + "DONATED_BY\t"
				+ "DATE_DONATED\t" + "BORROWED_BY\t" + "DATE_BORROWED\t");
			for (History history : histories) {
			    int id = history.getBookID();
			    String name = history.getBookname();
			    String author = history.getAuthor();
			    Date firstPublished = history.getFirstPublished();
			    String fp = String.format("%1$td.%1$tm.%1$ty %n", firstPublished);
			    int donatedBy = history.getDonatedBy();
			    Date dateDonated = history.getDateDonated();
			    String dd = String.format("%1$td.%1$tm.%1$ty %n", dateDonated);
			    int borrowedBy = history.getBorrowedBy();
			    Date dateBorrowed = history.getDateBorrowed();
			    String db = String.format("%1$td.%1$tm.%1$ty %n", dateBorrowed);
			    System.out.printf("%d\t %s\t %s\t %d\t %s\t %d\t %s\t", id, name, author, fp, donatedBy, dd,
				    borrowedBy, db);
			}

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

		    }

		}

		if ("RETURN".trim().equalsIgnoreCase(commands[0])) {
		    operation.returnBook(Integer.parseInt(commands[1]));
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

	    } catch (Exception ex) {
		ex.printStackTrace();

	    }
	}
    }
}
