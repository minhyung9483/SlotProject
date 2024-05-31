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
				return "redirect:/naver-shopping-users";
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
			if(member.getUSER_TYPE().contains("NS")){
				return "redirect:/naver-shopping-slots";
			}else if(member.getUSER_TYPE().contains("NP")){
				return "redirect:/naver-place-slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/naver-shopping-slots")
	public String NaverShoppingSlots(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&member.getUSER_TYPE().contains("NS")) {
			model.addAttribute("MENU","네이버쇼핑슬롯관리");
			return "Front/v1/naver-shopping-slots";
		}

		return "redirect:/login";
	}

	@RequestMapping("/naver-shopping-slots/{Page}")
	public String NaverShoppingSlotsPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&member.getUSER_TYPE().contains("NS")) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");
				String OrderType = Request.getParameter("or");

				model.addAttribute("SearchType",SearchType!=null ? SearchType : "");
				model.addAttribute("SearchValue",SearchValue!=null ? SearchValue : "");
				model.addAttribute("OrderType",OrderType!=null ? OrderType : "");
				model.addAttribute("page", Page);
			}catch(Exception e){ e.printStackTrace(); }

			model.addAttribute("MENU","네이버쇼핑슬롯관리");
			return "Front/v1/naver-shopping-slots";
		}
		return "redirect:/login";
	}

	@RequestMapping("/naver-place-slots")
	public String NaverPlaceSlots(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&member.getUSER_TYPE().contains("NP")) {
			model.addAttribute("MENU","네이버플레이스슬롯관리");
			return "Front/v1/naver-place-slots";
		}

		return "redirect:/login";
	}

	@RequestMapping("/naver-place-slots/{Page}")
	public String NavePlaceSlotsPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&member.getUSER_TYPE().contains("NP")) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");
				String OrderType = Request.getParameter("or");

				model.addAttribute("SearchType",SearchType!=null ? SearchType : "");
				model.addAttribute("SearchValue",SearchValue!=null ? SearchValue : "");
				model.addAttribute("OrderType",OrderType!=null ? OrderType : "");
				model.addAttribute("page", Page);
			}catch(Exception e){ e.printStackTrace(); }

			model.addAttribute("MENU","네이버플레이스슬롯관리");
			return "Front/v1/naver-place-slots";
		}
		return "redirect:/login";
	}

	@RequestMapping("/users")
	public String Users(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			if(member.getUSER_TYPE().contains("NS")){
				return "redirect:/naver-shopping-users";
			}else if(member.getUSER_TYPE().contains("NP")){
				return "redirect:/naver-place-users";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/naver-shopping-users")
	public String NaverShoppingUsers(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&member.getUSER_TYPE().contains("NS")) {
			if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				model.addAttribute("MENU","네이버쇼핑유저관리");
				return "Front/v1/naver-shopping-users";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-shopping-slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/naver-shopping-users/{Page}")
	public String NaverShoppingUsersPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&member.getUSER_TYPE().contains("NS")) {
			if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				try {
					String SearchType = Request.getParameter("st");
					String SearchValue = Request.getParameter("sv");

					model.addAttribute("SearchType",SearchType!=null ? SearchType : "");
					model.addAttribute("SearchValue",SearchValue!=null ? SearchValue : "");
					model.addAttribute("page", Page);
				}catch(Exception e){ e.printStackTrace(); }

				model.addAttribute("MENU","네이버쇼핑유저관리");
				return "Front/v1/naver-shopping-users";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-shopping-slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/naver-place-users")
	public String NaverPlaceUsers(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&member.getUSER_TYPE().contains("NP")) {
			if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				model.addAttribute("MENU","네이버플레이스유저관리");
				return "Front/v1/naver-place-users";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-place-slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/naver-place-users/{Page}")
	public String NaverPlaceUsersPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&member.getUSER_TYPE().contains("NP")) {
			if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				try {
					String SearchType = Request.getParameter("st");
					String SearchValue = Request.getParameter("sv");

					model.addAttribute("SearchType",SearchType!=null ? SearchType : "");
					model.addAttribute("SearchValue",SearchValue!=null ? SearchValue : "");
					model.addAttribute("page", Page);
				}catch(Exception e){ e.printStackTrace(); }

				model.addAttribute("MENU","네이버플레이스유저관리");
				return "Front/v1/naver-place-users";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-place-slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/naver-shopping-log-slots")
	public String NaverShoppingLogSlots(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&member.getUSER_TYPE().contains("NS")) {
			if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				model.addAttribute("MENU","네이버쇼핑슬롯로그");
				return "Front/v1/naver-shopping-log-slots";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-shopping-slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/naver-shopping-log-slots/{Page}")
	public String NaverShoppingLogSlotsPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null&&member.getUSER_TYPE().contains("NS")) {
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

				model.addAttribute("MENU","네이버쇼핑슬롯로그");
				return "Front/v1/naver-shopping-log-slots";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-shopping-slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/naver-place-log-slots")
	public String NaverPlaceLogSlots(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&member.getUSER_TYPE().contains("NP")) {
			if("M".equals(member.getUSER_PERM()) || "G".equals(member.getUSER_PERM())){
				model.addAttribute("MENU","네이버플레이스슬롯로그");
				return "Front/v1/naver-place-log-slots";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-place-slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/naver-place-log-slots/{Page}")
	public String NaverPlaceLogSlotsPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null&&member.getUSER_TYPE().contains("NP")) {
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

				model.addAttribute("MENU","네이버플레이스슬롯로그");
				return "Front/v1/naver-place-log-slots";
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-place-slots";
			}
		}
		return "redirect:/login";
	}

	@RequestMapping("/slot-type-manage")
	public String SlotTypeManage(HttpServletRequest request, Model model) {
		member = (Member) ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null&&"M".equals(member.getUSER_PERM())) {
			model.addAttribute("MENU","슬롯타입관리");
			return "Front/v1/slot-type-manage";
		}

		return "redirect:/login";
	}

}
