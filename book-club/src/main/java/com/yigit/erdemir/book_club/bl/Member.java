package com.yigit.erdemir.book_club.bl;

import java.sql.Date;
import java.util.List;

public class Member {
    private int id;
    private String name;
    private Date memberSince;
    private List<Book> borrowedBooks;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Date getMemberSince() {
	return memberSince;
    }

    public void setMembersince(Date memberSince) {
	this.memberSince = memberSince;
    }

    public List<Book> getBorrowedBooks() {
	return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
	this.borrowedBooks = borrowedBooks;
    }

}
