package com.app.nouapp.controller;


import java.text.SimpleDateFormat;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.nouapp.API.SendEmailService;
import com.app.nouapp.dto.AdminInfoDto;
import com.app.nouapp.dto.EnquiryDto;
import com.app.nouapp.dto.StudentInfoDto;
import com.app.nouapp.model.AdminInfo;
import com.app.nouapp.model.Enquiry;
import com.app.nouapp.model.StudentInfo;
import com.app.nouapp.service.AdminInfoRepository;
import com.app.nouapp.service.EnquiryRepository;
import com.app.nouapp.service.StudentInfoRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
    @Autowired
	EnquiryRepository erepo ;
	
    @Autowired
	StudentInfoRepository srepo;
    
    @Autowired
    AdminInfoRepository adrepo;
    
    @Autowired
    private SendEmailService emailService;
    
    
    
    
	@GetMapping("/index")
	public String showIndex() {
		return "index";
	}
	@GetMapping("/aboutus")
	public String showAboutUs() {
		return "aboutus";
	}
	@GetMapping("/registration")
	public String showRegistration(Model model ) {
		StudentInfoDto dto = new StudentInfoDto();
		model.addAttribute("dto", dto);
		return "registration";
	}
	@GetMapping("/stulogin")
	public String showStudentLogin(Model model) {
		StudentInfoDto dto = new StudentInfoDto();
		model.addAttribute("dto", dto);
		return "stulogin";
	}
	@GetMapping("/adminlogin")
	public String showAdminLogin(Model model) {
		AdminInfoDto dto = new AdminInfoDto();
		model.addAttribute("dto", dto);
		return "adminlogin";
	}
	
	@GetMapping("/contactus")
	public String showConatactUs(Model model , RedirectAttributes attrib) {
		EnquiryDto dto = new EnquiryDto();
		model.addAttribute("dto", dto);
		return "contactus";
	}
	
	@PostMapping("/contactus")	
	public String saveEnquiry(@ModelAttribute EnquiryDto dto, RedirectAttributes attrib ) {
		Enquiry e = new Enquiry();
		e.setName(dto.getName());
		e.setGender(dto.getGender());
		e.setAddress(dto.getAddress());
		e.setContactNo(dto.getContactNo());
		e.setEmailAddress(dto.getEmailAddress());
		e.setEnquiryText(dto.getEnquiryText());
		Date dt = new Date();
		SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		String postedDate = df.format(dt);
		e.setPostedDate(postedDate);
		erepo.save(e);
		attrib.addFlashAttribute("msg", "Enquiry is saved");
		return "redirect:/contactus";
	}
	
	@PostMapping("/registration")
	public String Registration(@ModelAttribute StudentInfoDto dto, RedirectAttributes attrib) {
		try {
			StudentInfo s = new StudentInfo();
			s.setEnrollmentNo(dto.getEnrollmentNo());
			s.setName(dto.getName());
			s.setfName(dto.getfName());
			s.setmName(dto.getmName());
			s.setGender(dto.getGender());
			s.setAddress(dto.getAddress());
			s.setProgram(dto.getProgram());
			s.setBranch(dto.getBranch());
			s.setYear(dto.getYear());
			s.setContactNo(dto.getContactNo());
			s.setEmailAddress(dto.getEmailAddress());
			s.setPassword(dto.getPassword());
			Date dt = new Date();
			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
			String postedDate = df.format(dt);
			s.setRegDate(postedDate);
			srepo.save(s);
			emailService.sendEmail(dto.getName(), dto.getEmailAddress());
			attrib.addFlashAttribute("msg", "Registration is successfull and mail sent");   // for otp validation use scheduler
			
		}catch(Exception e) {
			attrib.addFlashAttribute("msg", "Something went wrong"+e.getMessage());
		}
		
		return "redirect:/registration";
	}
	
	@PostMapping("/adminlogin")
	public String AdminLogin(@ModelAttribute AdminInfoDto dto,HttpSession session,RedirectAttributes attrib) {
		try {
			AdminInfo ad = adrepo.getById(dto.getUserid());
			if(ad.getPassword().equals(dto.getPassword())) {
				
				session.setAttribute("admin", ad.getUserid().toString());
				return "redirect:/admin/adhome";
			}else{
				attrib.addFlashAttribute("msg", "Invalid user");
				return "redirect:/adminlogin";
			}
		}catch(Exception e) {
			attrib.addFlashAttribute("msg", "User not found "+e.getMessage());
			return "redirect:/adminlogin";
		}
		
	}
	
	@PostMapping("/stulogin")
	public String studentLogin(@ModelAttribute StudentInfoDto dto,HttpSession session,RedirectAttributes attrib) {
		try {
			StudentInfo st=srepo.getById(dto.getEnrollmentNo());
		
			if(st.getPassword().equals(dto.getPassword())) {
				session.setAttribute("studentid", st.getEnrollmentNo());
//				attrib.addFlashAttribute("msg", "welcome to nou");
				return "redirect:/student/studenthome";
			}else {
				attrib.addFlashAttribute("msg", "register yourself first");
				return "redirect:/stulogin";
			}
		}catch(Exception e){
			attrib.addFlashAttribute("msg", "User not found ");
			return "redirect:/stulogin";
		}
	}
	
}
