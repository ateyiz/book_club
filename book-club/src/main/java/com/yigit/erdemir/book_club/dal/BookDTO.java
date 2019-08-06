package com.yigit.erdemir.book_club.dal;

import java.sql.Date;

public class BookDTO {

    private int id;
    private String name;
    private String author;
    private int donatedBy;
    private Integer borrowedBy;
    private Date dateDonated;
    private Date dateBorrowed;
    private boolean isBorrowed;

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

    public int getDonatedBy() {
	return donatedBy;
    }

    public void setDonatedBy(int donatedBy) {
	this.donatedBy = donatedBy;
    }

    public Integer getBorrowedBy() {
	return borrowedBy;
    }

    public void setBorrowedBy(int borrowedBy) {
	this.borrowedBy = borrowedBy;
    }

    public Date getDateBorrowed() {
	return dateBorrowed;
    }

    public void setDateBorrowed(Date dateBorrowed) {
	this.dateBorrowed = dateBorrowed;
    }

    public Date getDateDonated() {
	return dateDonated;
    }

    public void setDateDonated(Date dateDonated) {
	this.dateDonated = dateDonated;
    }

    public boolean isBorrowed() {
	return isBorrowed;
    }

    public void setBorrowed(boolean isBorrowed) {
	this.isBorrowed = isBorrowed;
    }

}
