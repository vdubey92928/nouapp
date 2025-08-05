package com.app.nouapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="responses")
public class Response {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=50, nullable=false)
	private String name;
	@Column(nullable=false)
	private long enrollmentno;
	@Column(length=13, nullable=false)
	private String contactno;
	@Column(length=10, nullable=false)
	private String responsetype;
	@Column(length=150, nullable=false)
	private String responsetitle;
	@Column(length=1000, nullable=false)
	private String responsetext;
	@Column(length=15, nullable=false)
	private String resdate;
	
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
	public long getEnrollmentno() {
		return enrollmentno;
	}
	public void setEnrollmentno(long enrollmentno) {
		this.enrollmentno = enrollmentno;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getResponsetype() {
		return responsetype;
	}
	public void setResponsetype(String responsetype) {
		this.responsetype = responsetype;
	}
	public String getResponsetitle() {
		return responsetitle;
	}
	public void setResponsetitle(String responsetitle) {
		this.responsetitle = responsetitle;
	}
	public String getResponsetext() {
		return responsetext;
	}
	public void setResponsetext(String responsetext) {
		this.responsetext = responsetext;
	}
	public String getResdate() {
		return resdate;
	}
	public void setResdate(String resdate) {
		this.resdate = resdate;
	}
	
	
}
