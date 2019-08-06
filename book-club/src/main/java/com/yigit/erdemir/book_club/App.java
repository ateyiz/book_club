package com.yigit.erdemir.book_club;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.yigit.erdemir.book_club.bl.BookClubBO;
import com.yigit.erdemir.book_club.bl.CreateTable;
import com.yigit.erdemir.book_club.bl.Member;

public class App {

    public static void main(String[] args) {

	try {
	    CreateTable c = new CreateTable();
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
		    for (Member member : members) {
			int id = member.getId();
			String name = member.getName();
			Date membersince = member.getMemberSince();
			String w = String.format("%1$td.%1$tm.%1$ty %n", membersince);
			System.out.printf("ID = %d , NAME= %s, MEMBER SINCE= %s ", id, name, w);
		    }

		}

		if (commands[0] == "ACCEPT") {

		    String date = commands[4];
		    DateFormat format = new SimpleDateFormat("dd, mm, yyyy", Locale.ENGLISH);
		    Date d1 = format.parse(date);
		    operation.addBook(commands[1], commands[2], commands[3], d1);
		}

		if ("FIND".trim().equalsIgnoreCase(commands[0])) {
		    operation.findBook(commands[1]);
		}

		if ("BORROW".trim().equalsIgnoreCase(commands[0])) {
		    // AQ
		    String username = commands[1];
		    int[] books = new int[commands.length - 2];
		    for (int i = 0; i < commands.length - 2; i++) {
			books[i] = Integer.parseInt(commands[i + 2]);
		    }
		    // operation.borrowBook(username, books);
		}

		if (commands[0] == "RETURN") {
		    operation.returnBook(Integer.parseInt(commands[1]));
		}

		if (commands[0] == "HISTORY") {
		    if (commands[1] == "BOOK") {
		    }
		}

	    } catch (Exception ex) {
		ex.printStackTrace();

	    }
	}
    }
}
