package com.slot.Controller;


import com.slot.Common.ContextUtil;
import com.slot.Common.DBConnector;
import com.slot.Common.Protocol;
import com.slot.Common.Util;
import com.slot.Model.NaverPlaceLogSlot;
import com.slot.Model.NaverShoppingLogSlot;
import com.slot.Model.Member;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
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

	@ResponseBody
	@PostMapping("/uploadSlotExcel")
	public String UploadSlotExcel(@RequestParam HashMap<String, String> param) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", "200");
		jobj.put("result", false);
		jobj.put("value", "");
		jobj.put("alert", "업로드 실패");
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			if("M".equals(member.getUSER_PERM()) && param!=null && param.size() > 0) {
				try {
					JSONArray jParamArray	= (JSONArray) JSONValue.parse(param.get("PARAM"));
					JSONArray responseArray = new JSONArray();
					JSONObject Object = (JSONObject)JSONValue.parse(param.get("USER_TYPE"));
					String USER_TYPE = (String) Object.get("USER_TYPE");
					int success = 0;
					int fail = 0;
					for(int i=0;i<jParamArray.size();i++){
						JSONObject jParamObject = (JSONObject)jParamArray.get(i);
						Member user = DBConnector.getMemberById(jParamObject.get("유저아이디")!=null?jParamObject.get("유저아이디").toString():"");

						jParamObject.put("RESULT", "X");

						if (user != null) {
							if(USER_TYPE.contains("NS")&&user.getUSER_TYPE().contains("NS")){
								String SLOT_STDT = jParamObject.get("시작일")!=null?jParamObject.get("시작일").toString():"";
								String SLOT_ENDT = jParamObject.get("종료일")!=null?jParamObject.get("종료일").toString():"";

								int SLOT_DAYS = Util.getDaysBetween(SLOT_STDT,SLOT_ENDT);
								if(SLOT_DAYS > 0){
									int SLOT_IDX = DBConnector.InsertNaverShoppingSlotInfoByExcel(user.getUSER_IDX(),
											SLOT_STDT.replace("-",""),
											SLOT_ENDT.replace("-",""),
											member.getUSER_IDX(),
											jParamObject.get("슬롯타입코드")!=null?Integer.parseInt(jParamObject.get("슬롯타입코드").toString()):0,
											jParamObject.get("묶음MID")!=null?jParamObject.get("묶음MID").toString():"",
											jParamObject.get("단품MID")!=null?jParamObject.get("단품MID").toString():"",
											jParamObject.get("키워드")!=null?jParamObject.get("키워드").toString():"",
											jParamObject.get("URL")!=null?jParamObject.get("URL").toString():"");
									if (SLOT_IDX > 0) {
										DBConnector.InsertNaverShoppingSlotLogInfo(SLOT_IDX, SLOT_DAYS, 1, "C", member.getUSER_IP(), member.getUSER_IDX());
										jParamObject.put("RESULT", "O");
										jParamObject.put("MSG", "");
										success++;
									} else {
										jParamObject.put("MSG", "등록 실패");
										fail++;
									}
								}else{
									jParamObject.put("MSG", "날짝 오류");
									fail++;
								}
							}else if(USER_TYPE.contains("NP")&&user.getUSER_TYPE().contains("NP")){
								String SLOT_STDT = jParamObject.get("시작일")!=null?jParamObject.get("시작일").toString():"";
								String SLOT_ENDT = jParamObject.get("종료일")!=null?jParamObject.get("종료일").toString():"";

								int SLOT_DAYS = Util.getDaysBetween(SLOT_STDT,SLOT_ENDT);
								if(SLOT_DAYS > 0){
									int SLOT_IDX = DBConnector.InsertNaverPlaceSlotInfoByExcel(user.getUSER_IDX(),
											SLOT_STDT.replace("-",""),
											SLOT_ENDT.replace("-",""),
											member.getUSER_IDX(),
											jParamObject.get("슬롯타입코드")!=null?Integer.parseInt(jParamObject.get("슬롯타입코드").toString()):0,
											jParamObject.get("플레이스명")!=null?jParamObject.get("플레이스명").toString():"",
											jParamObject.get("플레이스코드")!=null?jParamObject.get("플레이스코드").toString():"",
											jParamObject.get("키워드")!=null?jParamObject.get("키워드").toString():"",
											jParamObject.get("URL")!=null?jParamObject.get("URL").toString():"");
									if (SLOT_IDX > 0) {
										DBConnector.InsertNaverPlaceSlotLogInfo(SLOT_IDX, SLOT_DAYS, 1, "C", member.getUSER_IP(), member.getUSER_IDX());
										jParamObject.put("RESULT", "O");
										jParamObject.put("MSG", "");
										success++;
									} else {
										jParamObject.put("MSG", "등록 실패");
										fail++;
									}
								}else{
									jParamObject.put("MSG", "날짝 오류");
									fail++;
								}
							}else{
								jParamObject.put("MSG", "권한 없음");
								fail++;
							}
						} else {
							jParamObject.put("MSG", "없는 아이디");
							fail++;
						}

						responseArray.add(jParamObject);
					}
					jobj.put("value", responseArray);
					jobj.put("result", true);
					jobj.put("alert", "등록성공 : "+success+", 등록실패 : "+fail);
				}catch(Exception e){ e.printStackTrace(); }
			}else{

			}
		}
		return jobj.toString();
	}

	@RequestMapping(value = "/naverShoppingSearchUser/{Page}", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String NaverShoppingSearchUserPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");
				return "/naver-shopping-users/"+Page+"?" +
						(SearchType!=null && SearchType.length() > 0 ? "st=" + SearchType +"&" : "") +
						(SearchValue!=null && SearchValue.length() > 0 ? "sv=" + SearchValue : "");
			}catch(Exception e){ e.printStackTrace(); }
		}
		return "redirect:/Login";
	}

	@RequestMapping(value = "/naverPlaceSearchUser/{Page}", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String NaverPlaceSearchUserPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");
				return "/naver-place-users/"+Page+"?" +
						(SearchType!=null && SearchType.length() > 0 ? "st=" + SearchType +"&" : "") +
						(SearchValue!=null && SearchValue.length() > 0 ? "sv=" + SearchValue : "");
			}catch(Exception e){ e.printStackTrace(); }
		}
		return "redirect:/Login";
	}

	@RequestMapping(value = "/naverShoppingSearchSlot/{Page}", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String NaverShoppingSearchSlotPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");
				return "/naver-shopping-slots/"+Page+"?" +
						(SearchType!=null && SearchType.length() > 0 ? "st=" + SearchType +"&" : "") +
						(SearchValue!=null && SearchValue.length() > 0 ? "sv=" + SearchValue : "");
			}catch(Exception e){ e.printStackTrace(); }
		}
		return "redirect:/Login";
	}

	@RequestMapping(value = "/naverPlaceSearchSlot/{Page}", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String NaverPlaceSearchSlotPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		if(member!=null) {
			try {
				String SearchType = Request.getParameter("st");
				String SearchValue = Request.getParameter("sv");
				return "/naver-place-slots/"+Page+"?" +
						(SearchType!=null && SearchType.length() > 0 ? "st=" + SearchType +"&" : "") +
						(SearchValue!=null && SearchValue.length() > 0 ? "sv=" + SearchValue : "");
			}catch(Exception e){ e.printStackTrace(); }
		}
		return "redirect:/Login";
	}

	@RequestMapping(value = "/naverShoppingSearchLogSlot/{Page}", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String NaverShoppingSearchLogSlotPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
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

				return "/naver-shopping-log-slots/"+Page+"?" +
						(SearchType!=null && SearchType.length() > 0 ? "st=" + SearchType +"&" : "") +
						(SearchValue!=null && SearchValue.length() > 0 ? "sv=" + SearchValue +"&" : "") +
						(StartDate!=null && StartDate.length() > 0 ? "sd=" + StartDate +"&" : "") +
						(EndDate!=null && EndDate.length() > 0 ? "ed=" + EndDate : "");
			}catch(Exception e){ e.printStackTrace(); }
		}
		return "redirect:/Login";
	}

	@RequestMapping(value = "/naverPlaceSearchLogSlot/{Page}", produces = "application/text; charset=UTF-8", method=RequestMethod.POST)
	public String NaverPlaceSearchLogSlotPage(@PathVariable("Page") String Page, HttpServletRequest Request, Model model) {
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

				return "/naver-place-log-slots/"+Page+"?" +
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
				String USER_TYPE = Request.getParameter("USER_TYPE");

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

				if(USER_TYPE.contains("NS")){
					List<NaverShoppingLogSlot> logSlotList = DBConnector.getNaverShoppingLogSlotExcelList(SearchType,SearchValue,StartDate,EndDate,member.getUSER_PERM(),member.getUSER_IDX());

					JSONObject search = new JSONObject();
					search.put("SearchType", SearchType);
					search.put("SearchValue", SearchValue);
					search.put("StartDate", StartDate);
					search.put("EndDate", EndDate);

					jobj.put("result", true);
					jobj.put("value", logSlotList.toString());
					jobj.put("search", search);
					jobj.put("alert", "다운로드 완료.");
				}else if(USER_TYPE.contains("NP")){
					List<NaverPlaceLogSlot> logSlotList = DBConnector.getNaverPlaceLogSlotExcelList(SearchType,SearchValue,StartDate,EndDate,member.getUSER_PERM(),member.getUSER_IDX());

					JSONObject search = new JSONObject();
					search.put("SearchType", SearchType);
					search.put("SearchValue", SearchValue);
					search.put("StartDate", StartDate);
					search.put("EndDate", EndDate);

					jobj.put("result", true);
					jobj.put("value", logSlotList.toString());
					jobj.put("search", search);
					jobj.put("alert", "다운로드 완료.");
				}

			}catch(Exception e){ e.printStackTrace(); }
		}
		return jobj.toString();
	}

}
