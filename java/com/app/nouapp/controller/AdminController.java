package com.app.nouapp.controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.engine.jdbc.Size;
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

import com.app.nouapp.dto.StudyMaterialDto;
import com.app.nouapp.model.AdminInfo;
import com.app.nouapp.model.Enquiry;
import com.app.nouapp.model.Response;
import com.app.nouapp.model.StudentInfo;
import com.app.nouapp.model.StudyMaterial;
import com.app.nouapp.service.AdminInfoRepository;
import com.app.nouapp.service.EnquiryRepository;
import com.app.nouapp.service.ResponseRepository;
import com.app.nouapp.service.StudentInfoRepository;
import com.app.nouapp.service.StudyMaterialRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminInfoRepository adrepo;
	
	@Autowired
	StudentInfoRepository stdrepo;
	
	@Autowired
	EnquiryRepository erepo;
	
	@Autowired
	ResponseRepository resrepo;
	
	@Autowired
	StudyMaterialRepository smrepo;
	
    @GetMapping("/adhome")
	public String showAdminDashboard(HttpSession session,RedirectAttributes attrib,Model model) {
    	
		if(session.getAttribute("admin")!=null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("name", ad.getName());
		
			int scount = (int) stdrepo.count();
			model.addAttribute("scount", scount);
			
			List<StudyMaterial> smlist = smrepo.findByMaterial("studymaterial");
			int smcount = smlist.size();
			model.addAttribute("smcount", smcount);
			
			List<StudyMaterial> slist = smrepo.findByMaterial("assignment");
			int ascount = slist.size();
			model.addAttribute("ascount", ascount);
			
			List<Response> rlist = resrepo.findByResponseType("feedback");
			int fcount = rlist.size();
			model.addAttribute("fcount", fcount);
			
			List<Response> relist = resrepo.findByResponseType("feedback");
			int ccount = relist.size();
			model.addAttribute("ccount", ccount);
			
			int ecount = (int)erepo.count();
			model.addAttribute("ecount", ecount);
			
			
			return "admin/admindashboard";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
	}
    
    @GetMapping("/managestu")
    public String showManageStudent(HttpSession session, RedirectAttributes attrib, Model model) {
    	if(session.getAttribute("admin")!=null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("name", ad.getName());
			
			List<StudentInfo> slist = stdrepo.findAll();
			model.addAttribute("slist",slist);
			
    		return "admin/managestu";
    	}else {
    		attrib.addFlashAttribute("msg", "session expired");
    		return "redirect:/adminlogin";
    	}
    }
    
    @GetMapping("/deletestudent")
    public String deleteStudent(@RequestParam long enroll, HttpSession session,RedirectAttributes attrib,Model model) {
    	
		if(session.getAttribute("admin")!=null) {
			 StudentInfo stdinfo = stdrepo.findById(enroll).get();
			 stdrepo.delete(stdinfo);
			 attrib.addFlashAttribute("msg", " "+stdinfo.getName()+ " is deleted successfully.");
			 return "redirect:/admin/managestu";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
	}
    
    @GetMapping("/viewenquiries")
    public String showViewEnquiries(HttpSession session, RedirectAttributes attrib, Model model) {
    	if(session.getAttribute("admin")!=null) {
			AdminInfo  ad= adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("name", ad.getName());
			
			List<Enquiry> elist = erepo.findAll();
			model.addAttribute("elist",elist);
			
    		return "admin/viewenquiries";
    	}else {
    		attrib.addFlashAttribute("msg", "session expired");
    		return "redirect:/adminlogin";
    	}
    }
    @GetMapping("/viewdetail")
    public String viewdetail(@RequestParam Long id, HttpSession session,RedirectAttributes attrib,Model model) {
    	
		if(session.getAttribute("admin")!=null) {
			 StudentInfo st= stdrepo.findById(id).get();
			 model.addAttribute("st", st);
			 
			 return "admin/viewdetail";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
	} 
    
    
    @GetMapping("/deleteenquiry")
    public String deleteEnquiry(@RequestParam int id, HttpSession session,RedirectAttributes attrib,Model model) {
    	
		if(session.getAttribute("admin")!=null) {
			 Enquiry e = erepo.findById(id).get();
			 erepo.delete(e);
			 attrib.addFlashAttribute("msg", " "+e.getName()+ " is deleted successfully.");
			 return "redirect:/admin/viewenquiries";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
	}
    

    
    @GetMapping("/managestudymaterial")
    public String manageStudymaterial(HttpSession session,RedirectAttributes attrib,Model model) {
    	if(session.getAttribute("admin")!=null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("name", ad.getName());
			
			List<StudyMaterial> smList = smrepo.findAll();
			model.addAttribute("smlist",smList);
			return "admin/managestudymaterial";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
		
    }
    @GetMapping("/deletematerial")
    public String deleteMaterial(@RequestParam int id, HttpSession session,RedirectAttributes attrib,Model model) {
    	
		if(session.getAttribute("admin")!=null) {
			 StudyMaterial st = smrepo.findById(id).get();
			 smrepo.delete(st);
			 attrib.addFlashAttribute("msg", " "+st.getFilename()+ " is deleted successfully.");
			 return "redirect:/admin/managestudymaterial";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
	}
    
    
    @GetMapping("/addstudymaterial")
    public String showAddStudyMaterial(HttpSession session,RedirectAttributes attrib,Model model) {
    	if(session.getAttribute("admin")!=null) {
    		AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("name", ad.getName()); 
			StudyMaterialDto dto = new StudyMaterialDto();
			model.addAttribute("dto",dto);
			return "admin/addstudymaterial";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
    }
    
    @PostMapping("addstudymaterial")
    public String AddStudyMaterial(@ModelAttribute StudyMaterialDto dto,RedirectAttributes attrib) {
    	try {
    		
    		MultipartFile filedata = dto.getFilename();
    		String storageFileName = filedata.getOriginalFilename();
    		long size = filedata.getSize()/(1024*1024);    //fle size in mb
    		if(size>5) {
    			attrib.addFlashAttribute("msg", "file should be less than 5mb");
    			return "redirect:/admin/addstudymaterial";
    		}
    		String uploaddir = "public/mat/";
    		Path uploadpath = Paths.get(uploaddir);
    		
    		if(!Files.exists(uploadpath)) {   
    			Files.createDirectories(uploadpath);
    		}
    		
    		try(InputStream inputStream = filedata.getInputStream()){
    			Files.copy(inputStream, Paths.get(uploaddir+storageFileName), StandardCopyOption.REPLACE_EXISTING);
    		}
    		
    		StudyMaterial mat = new StudyMaterial();
    		mat.setCourse(dto.getCourse());
    		mat.setBranch(dto.getBranch());
    		mat.setYear(dto.getYear());
    		mat.setSubject(dto.getSubject());
    		mat.setTopic(dto.getTopic());
    		mat.setMaterialtype(dto.getMaterialtype());
    		mat.setFilename(storageFileName);
    		Date dt = new Date();
    		SimpleDateFormat df= new SimpleDateFormat();
    		String posteddate = df.format(dt);
    		mat.setPosteddate(posteddate);
    		
    		smrepo.save(mat);
    		attrib.addFlashAttribute("msg", dto.getMaterialtype()+" uploaded succesfully");
    		return "redirect:/admin/addstudymaterial";
    	}catch(Exception e) {
    		attrib.addFlashAttribute("msg", "something went wrong"+e.getMessage());
    		return "redirect:/admin/addstudymaterial";
    	}
    }
    
    
    
    @GetMapping("/viewfeedback")
	public String showFeedback(HttpSession session,RedirectAttributes attrib,Model model) {
    	
		if(session.getAttribute("admin")!=null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("name", ad.getName());
			List<Response> flist = resrepo.findByResponseType("feedback");
			model.addAttribute("flist", flist);
			return "admin/viewfeedback";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
		
	}
    @GetMapping("/deletefeedback")
    public String deletefeedback(@RequestParam int id, HttpSession session,RedirectAttributes attrib,Model model) {
    	
		if(session.getAttribute("admin")!=null) {
			 Response e = resrepo.findById(id).get();
			 resrepo.delete(e);
			 attrib.addFlashAttribute("msg", " "+e.getName()+ " is deleted successfully.");
			 return "redirect:/admin/viewfeedback";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
	}
    
    
    @GetMapping("/viewcomplaint")
	public String showComplaint(HttpSession session,RedirectAttributes attrib,Model model) {
    	
		if(session.getAttribute("admin")!=null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("name", ad.getName());
			List<Response> clist = resrepo.findByResponseType("complaint");
			model.addAttribute("clist",clist);
			return "admin/viewcomplaint";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
		
	}
    
    @GetMapping("/deletecomplaint")
    public String deletecomplaint(@RequestParam int id, HttpSession session,RedirectAttributes attrib,Model model) {
    	
		if(session.getAttribute("admin")!=null) {
			 Response e = resrepo.findById(id).get();
			 resrepo.delete(e);
			 attrib.addFlashAttribute("msg", " "+e.getName()+ " is deleted successfully.");
			 return "redirect:/admin/viewcomplaint";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
	}
  
    
    @GetMapping("changepassword")
    public String showChangePassword(HttpSession session,RedirectAttributes attrib,Model model) {
    	if(session.getAttribute("admin")!=null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("name", ad.getName());
			return "admin/changepassword";
		}else {
			attrib.addFlashAttribute("msg", "Session Expired");
			return "redirect:/adminlogin";
		}
    }
    @PostMapping("/changepassword")
    public String changePassword(HttpSession session, RedirectAttributes attrib, Model model,HttpServletRequest request) {
    	try {
    		AdminInfo ad = adrepo.findById(session.getAttribute("admin").toString()).get();
    		String oldpass= request.getParameter("oldpass");
    		String newpass = request.getParameter("newpass");
    		String confirmpass= request.getParameter("confirmpass");
    		if(newpass.equals(confirmpass)) {
    			if(oldpass.equals(ad.getPassword())) {
    				ad.setPassword(confirmpass);
    				adrepo.save(ad);
    				session.invalidate();
    				attrib.addFlashAttribute("msg", "Password changed successfully");
    				return "redirect:/adminlogin";
    			}else {
    				attrib.addFlashAttribute("message", "Invalid old password");
    			}
    		}else {
    			attrib.addFlashAttribute("message", "New password and confirm password not matched");
    		}
    		return "redirect:/admin/changepassword";
    	}catch(Exception e){
    		attrib.addFlashAttribute("message", "something went wrong"+e.getMessage());
    		return "redirect:/admin/changepassword";
    	}
    	
    }
    
    @GetMapping("/logout")
    public String Logout(HttpSession session,RedirectAttributes attrib) {
    	if(session.getAttribute("admin")!=null) {
    		session.invalidate();
    		attrib.addFlashAttribute("msg", "successfully logged out");
    		return "redirect:/adminlogin";
    	}else {
    		attrib.addFlashAttribute("msg", "Session Expirred");
    		return "redirect:/adminlogin";
    	}
    }
    

    
}
