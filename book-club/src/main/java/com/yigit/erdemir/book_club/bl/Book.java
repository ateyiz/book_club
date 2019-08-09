package com.yigit.erdemir.book_club.bl;

import java.util.Date;

public class Book {
    private int id;
    private String name;
    private String author;
    private String donator;
    private int donatedBy;
    private int borrowedBy;
    private Date lastBorrowedDate;
    private Date firstPublished;
    private Date dateDonated;

    public int getDonatedBy() {
	return donatedBy;
    }

    public void setDonatedBy(int donatedBy) {
	this.donatedBy = donatedBy;
    }

    public int getBorrowedBy() {
	return borrowedBy;
    }

    public void setBorrowedBy(int borrowedBy) {
	this.borrowedBy = borrowedBy;
    }

    public Date getLastBorrowedDate() {
	return lastBorrowedDate;
    }

    public void setLastBorrowedDate(Date lastBorrowedDate) {
	this.lastBorrowedDate = lastBorrowedDate;
    }

    public Date getFirstPublished() {
	return firstPublished;
    }

    public void setFirstPublished(Date firstPublished) {
	this.firstPublished = firstPublished;
    }

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

    public String getAuthor() {
	return author;
    }

    public void setAuthor(String author) {
	this.author = author;
    }

    public String getDonator() {
	return donator;
    }

    public void setDonator(String donator) {
	this.donator = donator;
    }

    public Date getDateDonated() {
	return dateDonated;
    }

    public void setDateDonated(Date dateDonated) {
	this.dateDonated = dateDonated;
    }

}
