package com.yigit.erdemir.book_club.dal;

import java.sql.Date;

public class MemberDTO {

    private int id;
    private String name;
    private int donated;
    private int borrowed;
    private Date memberSince;

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

    public int getDonated() {
	return donated;
    }

    public void setDonated(int donated) {
	this.donated = donated;
    }

    public int getBorrowed() {
	return borrowed;
    }

    public void setBorrowed(int borrowed) {
	this.borrowed = borrowed;
    }

    public Date getMemberSince() {
	return memberSince;
    }

    public void setMemberSince(Date memberSince) {
	this.memberSince = memberSince;
    }

}
