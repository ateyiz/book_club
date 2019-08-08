package com.yigit.erdemir.book_club.dal;

import java.util.Date;

public class BookDTO {

    private int id;
    private String name;
    private String author;
    private int donatedBy;
    private Date firstPublished;
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

    public boolean isBorrowed() {
	return isBorrowed;
    }

    public void setBorrowed(boolean isBorrowed) {
	this.isBorrowed = isBorrowed;
    }

    public Date getFirstPublished() {
	return firstPublished;
    }

    public void setFirstPublished(Date firstPublished) {
	this.firstPublished = firstPublished;
    }

}
