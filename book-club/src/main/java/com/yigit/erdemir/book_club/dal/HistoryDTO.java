package com.yigit.erdemir.book_club.dal;

import java.util.Date;

public class HistoryDTO {

    private int donatedBy;
    private int bookID;
    private Date dateBorrowed;
    private Date dateReturned;
    private Date dateDonated;

    public int getBookID() {
	return bookID;
    }

    public void setBookID(int bookID) {
	this.bookID = bookID;
    }

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

    public int getDonatedBy() {
	return donatedBy;
    }

    public void setDonatedBy(int donatedBy) {
	this.donatedBy = donatedBy;
    }

}
