package com.app.nouapp.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.nouapp.dto.ResponseDto;
import com.app.nouapp.model.AdminInfo;
import com.app.nouapp.model.Response;
import com.app.nouapp.model.StudentInfo;
import com.app.nouapp.model.StudyMaterial;
import com.app.nouapp.service.ResponseRepository;
import com.app.nouapp.service.StudentInfoRepository;
import com.app.nouapp.service.StudyMaterialRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {
    
	@Autowired
	StudentInfoRepository stdrepo;
	
	@Autowired	
	ResponseRepository resrepo;
	
	@Autowired
	StudyMaterialRepository smrepo;
	
	@GetMapping("/studenthome")
	public String showStudentDashboard(HttpSession session,Model model,RedirectAttributes attrib) {
		if(session.getAttribute("studentid")!=null) {
			StudentInfo st = stdrepo.getById((long)session.getAttribute("studentid"));
            model.addAttribute("st", st);
			model.addAttribute("name", st.getName());
			return "student/studentdashboard";
		}else {
			return "redirect:/stulogin";
		}
	}
    @PostMapping("/studenthome")
	public String uploadpic(@RequestParam MultipartFile file, RedirectAttributes attrib,HttpSession session) {
		try {
			String storageFileName = file.getOriginalFilename();
			String uploadDir = "public/profile/";
			Path uploadPath = Paths.get(uploadDir);
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try(InputStream inputStream = file.getInputStream()){
				Files.copy(inputStream, Paths.get(uploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);
			}
			
			StudentInfo std = stdrepo.findById((long)session.getAttribute("studentid")).get();
			std.setProfilepic(storageFileName);
			stdrepo.save(std);
			attrib.addFlashAttribute("msg","Profile pic uploaded successfully");
			return "redirect:/student/studenthome";
		}catch(Exception e){
			attrib.addFlashAttribute("msg", "Something went wrong"+e.getMessage());
			return "redirect:/student/studenthome";
		}
	}

	
	@GetMapping("/studymaterial")
	public String showStudyMaterial(HttpSession session,Model model) {
		if(session.getAttribute("studentid")!=null) {
			StudentInfo st = stdrepo.getById((long)session.getAttribute("studentid"));
			model.addAttribute("name", st.getName());
			String program = st.getProgram();
			String branch = st.getBranch();
			String year = st.getYear();
			String materialtype="studymaterial";
			List<StudyMaterial> smlist = smrepo.findAllByType(program,branch,year,materialtype);
			model.addAttribute("smlist", smlist);
			return "student/studymaterial";
		}else {
			return "redirect:/stulogin";
		}
	}
	
	
	
	
	@GetMapping("/assignment")
	public String showAssignment(HttpSession session,Model model) {
		if(session.getAttribute("studentid")!=null) {
			StudentInfo st = stdrepo.getById((long)session.getAttribute("studentid"));
			model.addAttribute("name", st.getName());
			String program = st.getProgram();
			String branch = st.getBranch();
			String year = st.getYear();
			String materialtype="assignment";
			List<StudyMaterial> smlist = smrepo.findAllByType(program,branch,year,materialtype);
			model.addAttribute("smlist", smlist);
			return "student/assignment";
		}else {
			return "redirect:/stulogin";
		}
	}
	
	@GetMapping("/giveresponse")
	public String giveResponse(HttpSession session,Model model) {
		if(session.getAttribute("studentid")!=null) {
			StudentInfo st = stdrepo.getById((long)session.getAttribute("studentid"));
			model.addAttribute("name", st.getName());
			ResponseDto dto = new ResponseDto();
			model.addAttribute("dto", dto);
			return "student/giveresponse";
		}else {
			return "redirect:/stulogin";
		}
	}
	
	@PostMapping("/giveresponse")
	public String submitResponse(@ModelAttribute ResponseDto dto,HttpSession session,RedirectAttributes attrib) {
		if(session.getAttribute("studentid")!=null) {
			try {
				
				StudentInfo stdinfo = stdrepo.getById((Long)session.getAttribute("studentid"));
				Response res =  new Response();
				
				res.setName(stdinfo.getName());
				res.setEnrollmentno(stdinfo.getEnrollmentNo());
				res.setContactno(stdinfo.getContactNo());
				res.setResponsetype(dto.getResponsetype());
				res.setResponsetitle(dto.getResponsetitle());
				res.setResponsetext(dto.getResponsetext());
				Date dt = new Date();
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
				String resdate = df.format(dt);
				res.setResdate(resdate);
				resrepo.save(res);
				attrib.addFlashAttribute("msg","Response Submitted Succesfully");
				
				return "redirect:/student/giveresponse";
				
				
				
			}catch(Exception e) {
				attrib.addFlashAttribute("msg","Something went wrong "+e.getMessage());
				return "redirect:/student/giveresponse";
			}
		}else {
			return "redirect:/stulogin";
		}
		
		
	}
	
	
	@GetMapping("changepassword")
    public String showChangePassword(HttpSession session,RedirectAttributes attrib,Model model) {
    	if(session.getAttribute("studentid")!=null) {
    		StudentInfo st = stdrepo.getById((long)session.getAttribute("studentid"));
			model.addAttribute("name", st.getName());
			return "student/changepassword";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/stulogin";
		}
    }


	@PostMapping("/changepassword")
    public String changePassword(HttpSession session, RedirectAttributes attrib, Model model,HttpServletRequest request) {
    	try {
    		StudentInfo st = stdrepo.findById((Long) (session.getAttribute("studentid"))).get();
    		String oldpass= request.getParameter("soldpass");
    		String newpass = request.getParameter("snewpass");
    		String confirmpass= request.getParameter("sconfirmpass");
    		if(newpass.equals(confirmpass)) {
    			if(oldpass.equals(st.getPassword())) {
    				st.setPassword(confirmpass);
    				stdrepo.save(st);
    				session.invalidate();
    				attrib.addFlashAttribute("msg", "Password changed successfully");
    				return "redirect:/stulogin";
    			}else {
    				attrib.addFlashAttribute("message", "Invalid old password");
    			}
    		}else {
    			attrib.addFlashAttribute("message", "New password and confirm password not matched");
    		}
    		return "redirect:/student/changepassword";
    	}catch(Exception e){
    		attrib.addFlashAttribute("message", "something went wrong"+e.getMessage());
    		return "redirect:/student/changepassword";
    	}
    }
    
	
	@GetMapping("/logout")
	public String Logout(HttpSession session , RedirectAttributes attrib) {
		if(session.getAttribute("studentid")!=null) {
			session.invalidate();
			attrib.addFlashAttribute("msg","successfully logged out");
			return "redirect:/stulogin";
		}else {
			return "redirect:/stulogin";
		}
	}
}
