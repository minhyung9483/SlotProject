package com.slot.Controller;


import com.slot.Common.ContextUtil;
import com.slot.Common.DBConnector;
import com.slot.Common.Logger;
import com.slot.Common.Protocol;
import com.slot.Model.LogSlot;
import com.slot.Model.Member;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


@RestController
public class DataSetController {
	private static final String TAG="DATA_SET_CONTROLLER";
	private Member member;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/searchUser/{Page}", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String SearchUserPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");
				return "/users/"+Page+"?" +
						(SearchType!=null && SearchType.length() > 0 ? "st=" + SearchType +"&" : "") +
						(SearchValue!=null && SearchValue.length() > 0 ? "sv=" + SearchValue : "");
			}catch(Exception e){ e.printStackTrace(); }
		}
		return "redirect:/Login";
	}

	@RequestMapping(value = "/searchSlot/{Page}", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String SearchSlotPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");
				return "/slots/"+Page+"?" +
						(SearchType!=null && SearchType.length() > 0 ? "st=" + SearchType +"&" : "") +
						(SearchValue!=null && SearchValue.length() > 0 ? "sv=" + SearchValue : "");
			}catch(Exception e){ e.printStackTrace(); }
		}
		return "redirect:/Login";
	}

	@RequestMapping(value = "/searchLogSlot/{Page}", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String SearchLogSlotPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");
				String StartDate = Request.getParameter("sd");
				String EndDate = Request.getParameter("ed");

				if(SearchType!= null && "INST_ACTN".equals(SearchType) && (SearchValue!=null && SearchValue.length() > 0)){
					if("신규".equals(SearchValue)){
						SearchValue = "C";
					}else if("연장".equals(SearchValue)){
						SearchValue = "E";
					}else if("삭제".equals(SearchValue)){
						SearchValue = "D";
					}else if("수정".equals(SearchValue)){
						SearchValue = "U";
					}else if("작업재개".equals(SearchValue)){
						SearchValue = "G";
					}else if("일시정지".equals(SearchValue)){
						SearchValue = "R";
					}
				}

				return "/log-slots/"+Page+"?" +
						(SearchType!=null && SearchType.length() > 0 ? "st=" + SearchType +"&" : "") +
						(SearchValue!=null && SearchValue.length() > 0 ? "sv=" + SearchValue +"&" : "") +
						(StartDate!=null && StartDate.length() > 0 ? "sd=" + StartDate +"&" : "") +
						(EndDate!=null && EndDate.length() > 0 ? "ed=" + EndDate : "");
			}catch(Exception e){ e.printStackTrace(); }
		}
		return "redirect:/Login";
	}

	@RequestMapping(value = "/downloadLogSlot", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String SearchLogSlotPage(HttpServletRequest Request, Model model) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", "200");
		jobj.put("result", false);
		jobj.put("value", "");
		jobj.put("alert", "Permission denied.");
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null && "M".equals(member.getUSER_PERM())) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");
				String StartDate = Request.getParameter("sd");
				String EndDate = Request.getParameter("ed");

				if(SearchType!= null && "INST_ACTN".equals(SearchType) && (SearchValue!=null && SearchValue.length() > 0)){
					if("신규".equals(SearchValue)){
						SearchValue = "C";
					}else if("연장".equals(SearchValue)){
						SearchValue = "E";
					}else if("삭제".equals(SearchValue)){
						SearchValue = "D";
					}else if("수정".equals(SearchValue)){
						SearchValue = "U";
					}else if("작업재개".equals(SearchValue)){
						SearchValue = "G";
					}else if("일시정지".equals(SearchValue)){
						SearchValue = "R";
					}
				}

				List<LogSlot> logSlotList = DBConnector.getLogSlotExcelList(SearchType,SearchValue,StartDate,EndDate,member.getUSER_PERM(),member.getUSER_IDX());

				JSONObject search = new JSONObject();
				search.put("SearchType", SearchType);
				search.put("SearchValue", SearchValue);
				search.put("StartDate", StartDate);
				search.put("EndDate", EndDate);

				jobj.put("result", true);
				jobj.put("value", logSlotList.toString());
				jobj.put("search", search);
				jobj.put("alert", "다운로드 완료.");

			}catch(Exception e){ e.printStackTrace(); }
		}
		return jobj.toString();
	}

}
