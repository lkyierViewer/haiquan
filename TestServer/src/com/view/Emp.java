package com.view;

import java.util.Date;

public class Emp {
	private int id;
	private String name;
	private String process;
	private int bigok;
	private Date date;
	private double money;
	private double money2;
	private int teach;
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
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public int getBigok() {
		return bigok;
	}
	public void setBigok(int bigok) {
		this.bigok = bigok;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public double getMoney2() {
		return money2;
	}
	public void setMoney2(double money2) {
		this.money2 = money2;
	}
	public int getTeach() {
		return teach;
	}
	public void setTeach(int teach) {
		this.teach = teach;
	}
	public Emp(int id, String name, String process, int bigok, Date date, double money, double money2, int teach) {
		super();
		this.id = id;
		this.name = name;
		this.process = process;
		this.bigok = bigok;
		this.date = date;
		this.money = money;
		this.money2 = money2;
		this.teach = teach;
	}
	
}
