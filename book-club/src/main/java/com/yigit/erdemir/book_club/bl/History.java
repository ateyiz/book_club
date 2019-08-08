package com.yigit.erdemir.book_club.bl;

import java.util.Date;

public class History {
    private int bookID;
    private String bookname;
    private Date firstPublished;
    private int borrowedBy;
    private int donatedBy;
    private String author;
    private Date dateBorrowed;
    private Date dateReturned;
    private Date dateDonated;

    public Date getDateBorrowed() {
	return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
	this.dateBorrowed = dateBorrowed;
    }

    public Date getDateReturned() {
	return dateReturned;
    }

    public void setDateReturned(Date dateReturned) {
	this.dateReturned = dateReturned;
    }

    public Date getDateDonated() {
	return dateDonated;
    }

    public void setDateDonated(Date dateDonated) {
	this.dateDonated = dateDonated;
    }

    public String getBookname() {
	return bookname;
    }

    public void setBookname(String bookname) {
	this.bookname = bookname;
    }

    public int getBookID() {
	return bookID;
    }

    public void setBookID(int bookID) {
	this.bookID = bookID;
    }

    public int getBorrowedBy() {
	return borrowedBy;
    }

    public void setBorrowedBy(int borrowedBy) {
	this.borrowedBy = borrowedBy;
    }

    public String getAuthor() {
	return author;
    }

    public void setAuthor(String author) {
	this.author = author;
    }

    public int getDonatedBy() {
	return donatedBy;
    }

    public void setDonatedBy(int donatedBy) {
	this.donatedBy = donatedBy;
    }

    public Date getFirstPublished() {
	return firstPublished;
    }

    public void setFirstPublished(Date firstPublished) {
	this.firstPublished = firstPublished;
    }

}
