package com.quiz.app.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthenticationController {

//	@Autowired
//	private CompanyDao companyDao;
//
//	@Autowired
//	private UserDao userDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

//	@Autowired
//	private CompanyRepository companyRepo;

	@Autowired
	private com.quiz.app.repository.UserRepository userRepo;

//	@Autowired
//	JavaMailSender javaMailSender;

	@Autowired
	private com.quiz.app.repository.RoleRepository roleRepo;

	@GetMapping("/login")
	public String customLogin(Model model) {
		return "login";
	}

	@GetMapping("/")
	public String dashboard() {
		return "Dashboard2";
	}

//	@GetMapping("/register")
//	public String register(Model model) {
//		return "registerDashboard";
//	}
//	
//	@GetMapping("/registerCompany")
//	public String registerCompany(Model model) {
//		if (!model.asMap().containsKey("company")) {
//			model.addAttribute("company", new Company());
//		}
//		return "registerCompany";
//	}
//
//	
//	@GetMapping("/registerShop")
//	public String registerShop(Model model) {
//		if (!model.asMap().containsKey("company")) {
//			model.addAttribute("company", new Company());
//		}
//		return "registerShop";
//	}
	
	/*****  ============== Registering company and Creating user ============= *****/
/*	@PostMapping("/saveCompany")
	public String saveCompany(@ModelAttribute Company company, RedirectAttributes redirect) {
		if (companyRepo.find(company.getCinNumber(), company.getPanNumber(),
				company.getGstinNumber(), company.getEmail(),company.getRegistrationNumber()).size()== 0) {
			String username = CommonMethods.generateRandomString();
			String password = CommonMethods.generateRandomString();
			User user = new User();
			user.setUsername(username);
			user.setPassword(passwordEncoder.encode(password));
			user.setRoles(Collections.singletonList(roleRepo.findByRoleName("ROLE_ADMIN")));
			company.setUser(user);
			try{
				CommonMethods.sendEmail(javaMailSender, username, password, company);
				userRepo.save(user);
				companyRepo.save(company);
				redirect.addFlashAttribute("success", " Registered successfully and Credentials send to your  mail");
				return "redirect:/";
			}
			catch(Exception e) {
				redirect.addFlashAttribute("fail", "Failed to send registration email. Please contact support.");
				redirect.addFlashAttribute("company", company);
				if(company.getRegistrationNumber()!= null) {
					return "redirect:/registerShop";
				}
				return "redirect:/registerCompany";
			}
		}
		if(company.getRegistrationNumber()!= null) {
			redirect.addFlashAttribute("company", company);
			redirect.addFlashAttribute("fail", "Shop already registered");
			return "redirect:/registerShop";
		}
		redirect.addFlashAttribute("company", company);
		redirect.addFlashAttribute("fail", "Company already registered");
		return "redirect:/registerCompany";
	}



	
	/*****  ============== Getting Reset Password Request Page  ============= *****/	
/*	@GetMapping("/resetPasswordRequest")
	public String resetPasswordRequest() {
		return "password_reset_request";
	}
	
	/*****  ============== Sending Reset Password link to mail  ============= *****/	
/*	@PostMapping("/resetPasswordRequest")
    public String resetPasswordRequest(@RequestParam("email") String email, HttpServletRequest request,Model model,RedirectAttributes redirect) throws MessagingException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            return "redirect:/resetPasswordRequest?error=User Not Found";
        }
        String resetToken = generateResetToken();
        user.setResetToken(resetToken);
        user.setResetTokenExpiration(LocalDateTime.now().plusMinutes(10)); // Set expiration to 10 minutes from now
        String resetUrl = getResetUrl(request, resetToken);
        try {
        	sendPasswordResetEmail(user.getEmail(), resetUrl, user.getName());
        	userRepo.save(user);
        	redirect.addFlashAttribute("success", "Password reset link has been sent to "+email);
		} catch (Exception e) {
			redirect.addFlashAttribute("fail", "Failed to send reset link to email. Please try again! ");
			return "redirect:/resetPasswordRequest";
		}
        return "redirect:/";
    }
	
	
	/*****  ============== Getting reset password page  ============= *****/
/*	@GetMapping("/resetPassword")
	public String resetPassword(@RequestParam("resetToken") String resetToken) {
		return "password_reset";
	}
	
	/*****  ============== Reseting password  ============= *****/
/*	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam("resetToken") String resetToken, @RequestParam("password") String password,Model model, RedirectAttributes redirect) {
	    User user = userRepo.findByResetToken(resetToken);
	    if (user == null) {
			return "redirect:/resetPasswordRequest?error=User Not Found";
		} else if (user.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
			redirect.addFlashAttribute("fail", "Token has expired");
            return "redirect:/resetPasswordRequest";	
		}
	    user.setPassword(passwordEncoder.encode(password));
	    user.setResetToken(null);
        user.setResetTokenExpiration(null);
        userRepo.save(user);
        redirect.addFlashAttribute("success", "Your password has been successfully changed");
	    return "redirect:/login";
	}
	
	/*****  ============== Method for reset password mail sender  =============  *****/
/*	public void sendPasswordResetEmail(String recipientEmail, String resetUrl, String name) throws MessagingException {
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    helper.setTo(recipientEmail);
	    helper.setFrom("info@stie.com.sg");
	    helper.setSubject("Reset Your Password");

	    String emailContent = "<html><body>"
	            + "<p>Dear "+name+",</p>"
	            + "<p>We have received a request to reset your password. Please follow the instructions below to proceed:</p>"
	            + "<ol>"
	            + "<li>Click the link below to reset your password:</li>"
	            + "<li><a href=\"" + resetUrl + "\">Reset Password</a></li>"
	            + "</ol>"
	            + "<p>If you did not initiate this request, please ignore this email.</p>"
	            + "<p>Note: This link will expire in 10 minutes.</p>"
	            + "<p>Thank you for using our Service.</p>"
	            + "<p>Best regards,</p>"
	            + "<p>ASTE</p>"
	            + "</body></html>";

	    helper.setText(emailContent, true);

	    javaMailSender.send(message);
	}

	/*****  ============== Method for generating reset password url  ============= *****/
/*	private String getResetUrl(HttpServletRequest request, String resetToken) {
	    String resetUrl = request.getRequestURL().toString();
	    resetUrl = resetUrl.replace(request.getServletPath(), "/resetPassword");
	    resetUrl += "?resetToken=" + resetToken;
	    return resetUrl;
	}

	/*****  ============== Method for generating resetToken  ============= *****/
	private String generateResetToken() {
        return UUID.randomUUID().toString();
    }

}
