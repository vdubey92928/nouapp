package com.app.nouapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="studentinfo")
public class StudentInfo {
	@Id
    private long enrollmentno;
	@Column(length=50 , nullable=false)
    private String name;
	@Column(length=50 , nullable=false)
    private String fname;
	@Column(length=50 , nullable=false)
    private String mname;
	@Column(length=6 , nullable=false)
    private String gender;
	@Column(length=500 , nullable=false)
    private String address;
	@Column(length=50 , nullable=false)
    private String program;
	@Column(length=100 , nullable=false)
    private String branch;
	@Column(length=50 , nullable=false)
    private String year;
	@Column(length=15 , nullable=false)
    private String contactno;
	@Column(length=50 , nullable=false)
    private String emailaddress;
	@Column(length=30 , nullable=false)
    private String password;
	@Column(length=30 , nullable=false)
    private String regdate;
	@Column(length=100,nullable=true)
	private String profilepic;
	
	public String getProfilepic() {
		return profilepic;
	}
	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
	}
	public long getEnrollmentNo() {
		return enrollmentno;
	}
	public void setEnrollmentNo(long enrollmentNo) {
		this.enrollmentno = enrollmentNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getfName() {
		return fname;
	}
	public void setfName(String fName) {
		this.fname = fName;
	}
	public String getmName() {
		return mname;
	}
	public void setmName(String mName) {
		this.mname = mName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getContactNo() {
		return contactno;
	}
	public void setContactNo(String contactNo) {
		this.contactno = contactNo;
	}
	public String getEmailAddress() {
		return emailaddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailaddress = emailAddress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegDate() {
		return regdate;
	}
	public void setRegDate(String regDate) {
		this.regdate = regDate;
	}
	
	
    
    
}
