package com.slot.Controller;


import com.slot.Common.*;
import com.slot.Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PageMoveController {
	private static final String TAG = "PAGE_MOVE_CONTROLLER";
	
	private Member member;

	@RequestMapping("/")
	public String Index(HttpServletRequest request) {
//		DBConnector set = new DBConnector();
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			/*if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				return "redirect:/users";
			}else{
				return "redirect:/slots";
			}*/
			return "redirect:/slots";
		}
		
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String Login() {
		return "Front/v1/login";
	}

	@GetMapping("/Logout")
	public String Logout() {
		ContextUtil.setAttrToSession(Protocol.Json.KEY_MEMBER, null);
		return "redirect:/login";
	}

	@RequestMapping("/slots")
	public String Slots(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			model.addAttribute("MENU","슬롯관리");
			return "Front/v1/slots";
		}

		return "redirect:/login";
	}

	@RequestMapping("/slots/{Page}")
	public String SlotsPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");

				model.addAttribute("SearchType",SearchType!=null ? SearchType : "");
				model.addAttribute("SearchValue",SearchValue!=null ? SearchValue : "");
				model.addAttribute("page", Page);
			}catch(Exception e){ e.printStackTrace(); }

			model.addAttribute("MENU","슬롯관리");
			return "Front/v1/slots";
		}
		return "redirect:/login";
	}

	@RequestMapping("/users")
	public String Users(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				model.addAttribute("MENU","유저관리");
				return "Front/v1/users";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/users/{Page}")
	public String UsersPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				try {
					String SearchType = Request.getParameter("st");
					String SearchValue = Request.getParameter("sv");

					model.addAttribute("SearchType",SearchType!=null ? SearchType : "");
					model.addAttribute("SearchValue",SearchValue!=null ? SearchValue : "");
					model.addAttribute("page", Page);
				}catch(Exception e){ e.printStackTrace(); }

				model.addAttribute("MENU","유저관리");
				return "Front/v1/users";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/log-slots")
	public String LogSlots(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				model.addAttribute("MENU","슬롯로그");
				return "Front/v1/log-slots";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/log-slots/{Page}")
	public String LogSlotsPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				try {
					String SearchType = Request.getParameter("st");
					String SearchValue = Request.getParameter("sv");
					String StartDate = Request.getParameter("sd");
					String EndDate = Request.getParameter("ed");

					model.addAttribute("SearchType",SearchType!=null ? SearchType : "");
					model.addAttribute("SearchValue",SearchValue!=null ? SearchValue : "");
					model.addAttribute("StartDate",StartDate!=null ? StartDate : "");
					model.addAttribute("EndDate",EndDate!=null ? EndDate : "");
					model.addAttribute("page", Page);
				}catch(Exception e){ e.printStackTrace(); }

				model.addAttribute("MENU","슬롯로그");
				return "Front/v1/log-slots";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/slots";
			}
		}
		return "redirect:/login";
	}

}
