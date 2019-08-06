package com.yigit.erdemir.book_club.bl;

import java.util.Date;

public class History {
    private int memberID;
    private String memberName;
    private Date dateBorrowed;
    private Date dateReturned;
    private Date dateDonated;

    public int getMemberID() {
	return memberID;
    }

    public void setMemberID(int memberID) {
	this.memberID = memberID;
    }

    public String getMemberName() {
	return memberName;
    }

    public void setMemberName(String memberName) {
	this.memberName = memberName;
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
}
