package com.slot.Controller;


import com.slot.Common.*;
import com.slot.Model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


@Controller
public class SlotController {
	private static final String TAG = "SLOT_CONTROLLER";
	
	private Member member;

	/* 슬롯 추가 기존 사용 안함*/
	@RequestMapping(value="/setSlotOld/{UserIdx}", method=RequestMethod.POST)
	public String SetSlotOld(@PathVariable("UserIdx") int UserIdx, HttpServletRequest Request, Model model) throws Exception{
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			String USER_TYPE = Request.getParameter("USER_TYPE_SL");
			String RETURN_URL = "";
			String MENU = "";

			if(USER_TYPE.contains("NS")){
				RETURN_URL = "shopping";
				MENU = "네이버쇼핑유저관리";
			}else if(USER_TYPE.contains("NP")){
				RETURN_URL = "place";
				MENU = "네이버플레이스유저관리";
			}

			/* 업무시간 외 셧다운 주석처리 */
			if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				return "redirect:/naver-"+RETURN_URL+"-users";
			}

			Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);
			if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || m.getUSER_IDX() == member.getUSER_IDX()))){

				String SLOT_STDT = Request.getParameter("SLOT_STDT_SL");
				String SLOT_ENDT = Request.getParameter("SLOT_ENDT_SL");
				int SLOT_EA = Integer.parseInt(Request.getParameter("SLOT_EA_SL"));
				int SLOT_DAYS = Integer.parseInt(Request.getParameter("SLOT_DAYS_SL"));

				int SLOT_TYPE = 0;
				if("M".equals(member.getUSER_PERM())||"G".equals(member.getUSER_PERM())){
					SLOT_TYPE = Integer.parseInt(Request.getParameter("SLOT_TYPE_SL"));
				}

				String PAGE = Request.getParameter("PAGE_SL");
				String PARAM = Request.getParameter("PARAM_SL");
				String URL = "";
				if(PAGE != null && !"".equals(PAGE)){
					URL = URL + "/" + PAGE;
				}
				if(PARAM != null && !"".equals(PARAM)){
					URL = URL + PARAM;
				}

				DateFormat format = new SimpleDateFormat("yyyyMMdd");

				Date d1 = format.parse( SLOT_STDT );
				Date d2 = format.parse( SLOT_ENDT );
				long Sec = (d1.getTime() - d2.getTime()) / 1000; // 초
//				long Min = (d1.getTime() - d2.getTime()) / 60000; // 분
//				long Hour = (d1.getTime() - d2.getTime()) / 3600000; // 시
//				int SLOT_DAYS = (int) (Sec / (24*60*60)) + 1; // 작업일 = 차이일 + 1

				if(USER_TYPE.contains("NS")){
					for(int i = 0; i < SLOT_EA; i++){
						int SLOT_IDX = DBConnector.InsertNaverShoppingSlotInfo(UserIdx, SLOT_STDT, SLOT_ENDT, member.getUSER_IDX(),SLOT_TYPE);
						if(SLOT_IDX > 0)DBConnector.InsertNaverShoppingSlotLogInfo(SLOT_IDX, SLOT_DAYS, 1, "C", member.getUSER_IP(), member.getUSER_IDX());
					}
				}else if(USER_TYPE.contains("NP")){
					for(int i = 0; i < SLOT_EA; i++){
						int SLOT_IDX = DBConnector.InsertNaverPlaceSlotInfo(UserIdx, SLOT_STDT, SLOT_ENDT, member.getUSER_IDX(),SLOT_TYPE);
						if(SLOT_IDX > 0)DBConnector.InsertNaverPlaceSlotLogInfo(SLOT_IDX, SLOT_DAYS, 1, "C", member.getUSER_IP(), member.getUSER_IDX());
					}
				}

				model.addAttribute("MENU",MENU);
				return "redirect:/naver-"+RETURN_URL+"-users"+Util.encodeKorean(URL);
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-"+RETURN_URL+"-slots";
			}
		}
		return "redirect:/login";
	}

	/* 슬롯 추가 */
	@ResponseBody
	@RequestMapping(value="/setSlot", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String SetSlot(@RequestParam HashMap<String, String> param) throws Exception{
		JSONObject jobj = new JSONObject();
		jobj.put("code", "200");
		jobj.put("result", false);
		jobj.put("value", "");
		jobj.put("alert", "추가 실패.");
		jobj.put("url", "");
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			/* 업무시간 외 셧다운 주석처리 */
			if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				jobj.put("alert", "마감시간을 확인해주세요.");
				return jobj.toString();
			}

			if(param!=null && param.size() > 0) {
				try {
					JSONArray responseArray = new JSONArray();
					String URL = "";

					JSONObject jParamObject = (JSONObject)JSONValue.parse(param.get("PARAM"));

					int UserIdx = Integer.parseInt((String) jParamObject.get("USER_IDX_SL"));

					String SLOT_STDT = (String) jParamObject.get("SLOT_STDT_SL");
					String SLOT_ENDT = (String) jParamObject.get("SLOT_ENDT_SL");
					int SLOT_EA = Integer.parseInt((String) jParamObject.get("SLOT_EA_SL"));
					int SLOT_DAYS = Integer.parseInt((String) jParamObject.get("SLOT_DAYS_SL"));

					int SLOT_TYPE = 0;
					if("M".equals(member.getUSER_PERM())||"G".equals(member.getUSER_PERM())){
						SLOT_TYPE = Integer.parseInt((String) jParamObject.get("SLOT_TYPE_SL"));
					}

					String PAGE = (String) jParamObject.get("PAGE_SL");
					String PARAM = (String) jParamObject.get("PARAM_SL");
					String USER_TYPE = (String) jParamObject.get("USER_TYPE_SL");
					if(PAGE != null && !"".equals(PAGE)){
						URL = URL + "/" + PAGE;
					}
					if(PARAM != null && !"".equals(PARAM)){
						URL = URL + PARAM;
					}

					Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);
					if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || m.getUSER_IDX() == member.getUSER_IDX()))){
						if(USER_TYPE.contains("NS")){
							jParamObject.put("RESULT", "X");
							for(int i = 0; i < SLOT_EA; i++){
								int SLOT_IDX = DBConnector.InsertNaverShoppingSlotInfo(UserIdx, SLOT_STDT, SLOT_ENDT, member.getUSER_IDX(),SLOT_TYPE);
								if(SLOT_IDX > 0){
									DBConnector.InsertNaverShoppingSlotLogInfo(SLOT_IDX, SLOT_DAYS, 1, "C", member.getUSER_IP(), member.getUSER_IDX());
									jParamObject.put("RESULT", "O");
									jParamObject.put("MSG", "");
								}else{
									jParamObject.put("MSG", "DB error");
								}
							}
							responseArray.add(jParamObject);
						}else if(USER_TYPE.contains("NP")){
							jParamObject.put("RESULT", "X");
							for(int i = 0; i < SLOT_EA; i++){
								int SLOT_IDX = DBConnector.InsertNaverPlaceSlotInfo(UserIdx, SLOT_STDT, SLOT_ENDT, member.getUSER_IDX(),SLOT_TYPE);
								if(SLOT_IDX > 0){
									DBConnector.InsertNaverPlaceSlotLogInfo(SLOT_IDX, SLOT_DAYS, 1, "C", member.getUSER_IP(), member.getUSER_IDX());
									jParamObject.put("RESULT", "O");
									jParamObject.put("MSG", "");
								}else{
									jParamObject.put("MSG", "DB error");
								}
							}
							responseArray.add(jParamObject);
						}
					}else {
						jParamObject.put("MSG", "DB error");
					}
					jobj.put("value", responseArray);
					jobj.put("result", true);
					jobj.put("alert", "추가 완료.");
					jobj.put("url", URL);
				}catch(Exception e){ e.printStackTrace(); }
			}
		}
		return jobj.toString();
	}

	/* 슬롯 정보 수정 (팝업) 사용안함 */
	@RequestMapping(value="/setSlot/{UserIdx}/{SlotIdx}", method=RequestMethod.POST)
	public String SetSlotIdx(@PathVariable("UserIdx") int UserIdx, @PathVariable("SlotIdx") int SlotIdx, HttpServletRequest Request, Model model) throws Exception{
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			/* 업무시간 외 셧다운 주석처리 */
			if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				return "redirect:/naver-shopping-slots";
			}

			NaverShoppingSlot s = DBConnector.getNaverShoppingSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
			Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), null, member.getUSER_IDX()).get(0);
			if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || s.getUSER_IDX() == member.getUSER_IDX())) || s.getUSER_IDX() == member.getUSER_IDX()){
//			if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || m.getUSER_IDX() == member.getUSER_IDX())) || s.getUSER_IDX() == member.getUSER_IDX()){
				String PROD_GID = Request.getParameter("GID_"+SlotIdx);
				String PROD_MID = Request.getParameter("MID_"+SlotIdx);
				String PROD_KYWD = Request.getParameter("KYWD_"+SlotIdx);
				String PROD_URL = Request.getParameter("URL_"+SlotIdx);
//				String SLOT_STDT = Request.getParameter("SLOT_STDT");
//				String SLOT_ENDT = Request.getParameter("SLOT_ENDT");
//				int SLOT_EA = Integer.parseInt(Request.getParameter("SLOT_EA"));

				String PAGE = Request.getParameter("PAGE_"+SlotIdx);
				String PARAM = Request.getParameter("PARAM_"+SlotIdx);
				String URL = "";
				if(PAGE != null && !"".equals(PAGE)){
					URL = URL + "/" + PAGE;
				}
				if(PARAM != null && !"".equals(PARAM)){
					URL = URL + PARAM;
				}

				DBConnector.UpdateNaverShoppingSlotInfo(SlotIdx, PROD_GID, PROD_MID, PROD_KYWD,
						PROD_URL, "R", member.getUSER_IDX());
				// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)
//				DBConnector.InsertSlotLogInfo(SlotIdx, 0, 1, "U", member.getUSER_IP(), member.getUSER_IDX());
				model.addAttribute("MENU","네이버쇼핑슬롯관리");
				return "redirect:/naver-shopping-slots"+URL;
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-shopping-slots";
			}
		}
		return "redirect:/login";
	}

	/* 리스트에서 한개 슬롯 수정 */
	@ResponseBody
	@RequestMapping(value="/updateOneSlot", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String UpdateOneSlot(@RequestParam HashMap<String, String> param) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", "200");
		jobj.put("result", false);
		jobj.put("value", "");
		jobj.put("alert", "수정 실패.");
		jobj.put("url", "");
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			/* 업무시간 외 셧다운 주석처리 */
			if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				jobj.put("alert", "마감시간을 확인해주세요.");
				return jobj.toString();
			}

			if(param!=null && param.size() > 0) {
				try {
					JSONArray responseArray = new JSONArray();
					String URL = "";

					JSONObject jParamObject = (JSONObject)JSONValue.parse(param.get("PARAM"));

					int UserIdx = Integer.parseInt((String) jParamObject.get("USER_IDX"));
					int SlotIdx = Integer.parseInt((String) jParamObject.get("SLOT_IDX"));

					String PAGE = (String) jParamObject.get("PAGE");
					String PARAM = (String) jParamObject.get("PARAM");
					String USER_TYPE = (String) jParamObject.get("USER_TYPE");
					if(PAGE != null && !"".equals(PAGE)){
						URL = URL + "/" + PAGE;
					}
					if(PARAM != null && !"".equals(PARAM)){
						URL = URL + PARAM;
					}

					if(USER_TYPE.contains("NS")){
						NaverShoppingSlot s = DBConnector.getNaverShoppingSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
						Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);

						jParamObject.put("RESULT", "X");
						if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || s.getUSER_IDX() == member.getUSER_IDX())) || s.getUSER_IDX() == member.getUSER_IDX()){
							String PROD_GID = (String) jParamObject.get("PROD_GID");
							String PROD_MID = (String) jParamObject.get("PROD_MID");
							String PROD_KYWD = (String) jParamObject.get("PROD_KYWD");
							String PROD_URL = (String) jParamObject.get("PROD_URL");

							if (DBConnector.UpdateNaverShoppingSlotInfo(SlotIdx, PROD_GID, PROD_MID, PROD_KYWD,
									PROD_URL, "R", member.getUSER_IDX())) {
								// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)
//								DBConnector.InsertSlotLogInfo(SlotIdx, 0, 1, "U", member.getUSER_IP(), member.getUSER_IDX());
								jParamObject.put("RESULT", "O");
								jParamObject.put("MSG", "");
							} else {
								jParamObject.put("MSG", "DB error");
							}
						} else {
							jParamObject.put("MSG", "Non-Existent id");
						}
						responseArray.add(jParamObject);
					}else if(USER_TYPE.contains("NP")){
						NaverPlaceSlot s = DBConnector.getNaverPlaceSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
						Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);

						jParamObject.put("RESULT", "X");
						if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || s.getUSER_IDX() == member.getUSER_IDX())) || s.getUSER_IDX() == member.getUSER_IDX()){
							String PLCE_NAME = (String) jParamObject.get("PLCE_NAME");
							String PLCE_CODE = (String) jParamObject.get("PLCE_CODE");
							String PLCE_KYWD = (String) jParamObject.get("PLCE_KYWD");
							String PLCE_URL = (String) jParamObject.get("PLCE_URL");

							if (DBConnector.UpdateNaverPlaceSlotInfo(SlotIdx, PLCE_NAME, PLCE_CODE, PLCE_KYWD,
									PLCE_URL, "R", member.getUSER_IDX())) {
								// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)
//								DBConnector.InsertSlotLogInfo(SlotIdx, 0, 1, "U", member.getUSER_IP(), member.getUSER_IDX());
								jParamObject.put("RESULT", "O");
								jParamObject.put("MSG", "");
							} else {
								jParamObject.put("MSG", "DB error");
							}
						} else {
							jParamObject.put("MSG", "Non-Existent id");
						}
						responseArray.add(jParamObject);
					}
					jobj.put("value", responseArray);
					jobj.put("result", true);
					jobj.put("alert", "수정 완료.");
					jobj.put("url", URL);
				}catch(Exception e){ e.printStackTrace(); }
			}else{

			}
		}
		return jobj.toString();
	}

	/* 선택된 슬롯 수정 */
	@ResponseBody
//	@PostMapping("/updateSlot")
	@RequestMapping(value="/updateSlot", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String UpdateSlot(@RequestParam HashMap<String, String> param) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", "200");
		jobj.put("result", false);
		jobj.put("value", "");
		jobj.put("alert", "수정 실패.");
		jobj.put("url", "");
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			/* 업무시간 외 셧다운 주석처리 */
			if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				jobj.put("alert", "마감시간을 확인해주세요.");
				return jobj.toString();
			}

			if(param!=null && param.size() > 0) {
				try {
					JSONArray jParamArray	= (JSONArray) JSONValue.parse(param.get("PARAM"));
					JSONArray responseArray = new JSONArray();
					int success = 0;
					int fail = 0;
					String URL = "";

					for(int i=0;i<jParamArray.size();i++){
						JSONObject jParamObject = (JSONObject)jParamArray.get(i);

						int UserIdx = Integer.parseInt((String) jParamObject.get("USER_IDX"));
						int SlotIdx = Integer.parseInt((String) jParamObject.get("SLOT_IDX"));

						String PAGE = (String) jParamObject.get("PAGE");
						String PARAM = (String) jParamObject.get("PARAM");
						String USER_TYPE = (String) jParamObject.get("USER_TYPE");
						if(i==0 && PAGE != null && !"".equals(PAGE)){
							URL = URL + "/" + PAGE;
						}
						if(i==0 && PARAM != null && !"".equals(PARAM)){
							URL = URL + PARAM;
						}

						if(USER_TYPE.contains("NS")){
							NaverShoppingSlot s = DBConnector.getNaverShoppingSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
							Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);

							jParamObject.put("RESULT", "X");
							if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || s.getUSER_IDX() == member.getUSER_IDX())) || s.getUSER_IDX() == member.getUSER_IDX()){
								String PROD_GID = (String) jParamObject.get("PROD_GID");
								String PROD_MID = (String) jParamObject.get("PROD_MID");
								String PROD_KYWD = (String) jParamObject.get("PROD_KYWD");
								String PROD_URL = (String) jParamObject.get("PROD_URL");

								if (DBConnector.UpdateNaverShoppingSlotInfo(SlotIdx, PROD_GID, PROD_MID, PROD_KYWD,
										PROD_URL, "R", member.getUSER_IDX())) {
									// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)
//								DBConnector.InsertSlotLogInfo(SlotIdx, 0, 1, "U", member.getUSER_IP(), member.getUSER_IDX());
									jParamObject.put("RESULT", "O");
									jParamObject.put("MSG", "");
									success++;
								} else {
									jParamObject.put("MSG", "DB error");
									fail++;
								}
							} else {
								jParamObject.put("MSG", "Non-Existent id");
								fail++;
							}
							responseArray.add(jParamObject);
						}else if(USER_TYPE.contains("NP")){
							NaverPlaceSlot s = DBConnector.getNaverPlaceSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
							Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);

							jParamObject.put("RESULT", "X");
							if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || s.getUSER_IDX() == member.getUSER_IDX())) || s.getUSER_IDX() == member.getUSER_IDX()){
								String PLCE_NAME = (String) jParamObject.get("PLCE_NAME");
								String PLCE_CODE = (String) jParamObject.get("PLCE_CODE");
								String PLCE_KYWD = (String) jParamObject.get("PLCE_KYWD");
								String PLCE_URL = (String) jParamObject.get("PLCE_URL");

								if (DBConnector.UpdateNaverPlaceSlotInfo(SlotIdx, PLCE_NAME, PLCE_CODE, PLCE_KYWD,
										PLCE_URL, "R", member.getUSER_IDX())) {
									// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)
//								DBConnector.InsertSlotLogInfo(SlotIdx, 0, 1, "U", member.getUSER_IP(), member.getUSER_IDX());
									jParamObject.put("RESULT", "O");
									jParamObject.put("MSG", "");
									success++;
								} else {
									jParamObject.put("MSG", "DB error");
									fail++;
								}
							} else {
								jParamObject.put("MSG", "Non-Existent id");
								fail++;
							}
							responseArray.add(jParamObject);
						}
					}
					jobj.put("value", responseArray);
					jobj.put("result", true);
					jobj.put("alert", success+"건 수정 완료.");
					jobj.put("url", URL);
				}catch(Exception e){ e.printStackTrace(); }
			}else{

			}
		}
		return jobj.toString();
	}

	/* 선택된 슬롯 삭제 */
	@ResponseBody
//	@PostMapping("/deleteSlot")
	@RequestMapping(value="/deleteSlot", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String DeleteSlot(@RequestParam HashMap<String, String> param) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", "200");
		jobj.put("result", false);
		jobj.put("value", "");
		jobj.put("alert", "삭제 실패.");
		jobj.put("url", "");
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			/* 업무시간 외 셧다운 주석처리 */
			if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				jobj.put("alert", "마감시간을 확인해주세요.");
				return jobj.toString();
			}

			if(param!=null && param.size() > 0) {
				try {
					JSONArray jParamArray	= (JSONArray) JSONValue.parse(param.get("PARAM"));
					JSONArray responseArray = new JSONArray();
					int success = 0;
					int fail = 0;
					String URL = "";

					for(int i=0;i<jParamArray.size();i++){
						JSONObject jParamObject = (JSONObject)jParamArray.get(i);

						int UserIdx = Integer.parseInt((String) jParamObject.get("USER_IDX"));
						int SlotIdx = Integer.parseInt((String) jParamObject.get("SLOT_IDX"));

						String PAGE = (String) jParamObject.get("PAGE");
						String PARAM = (String) jParamObject.get("PARAM");
						String USER_TYPE = (String) jParamObject.get("USER_TYPE");
						if(i==0 && PAGE != null && !"".equals(PAGE)){
							URL = URL + "/" + PAGE;
						}
						if(i==0 && PARAM != null && !"".equals(PARAM)){
							URL = URL + PARAM;
						}

						if(USER_TYPE.contains("NS")){
							Member user = DBConnector.getMemberByIdx(jParamObject.get("USER_IDX")!=null?jParamObject.get("USER_IDX").toString():"");

							NaverShoppingSlot s = DBConnector.getNaverShoppingSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
							Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);

							jParamObject.put("RESULT", "X");
							if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || s.getUSER_IDX() == member.getUSER_IDX()))){

								// 삭제 작업일 = 오늘날짜 -끝나는 날짜, 삭제 작업일
								LocalDateTime currentDateTime = LocalDateTime.now();
								String Today = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

								int CANC_DAYS = 0;
								int SLOT_DAYS = 0;
								String strFormat = "yyyyMMdd";    //strStartDate 와 strEndDate 의 format

								SimpleDateFormat sdf = new SimpleDateFormat(strFormat);

								Date todayDate =  sdf.parse(Today); //1204
								Date startDate =  sdf.parse(s.getSLOT_STDT()); //1205
								Date endDate =  sdf.parse(s.getSLOT_ENDT()); //1211

								CANC_DAYS = (int) ((todayDate.getTime() - endDate.getTime()) / (24*60*60*1000)); //-7
								SLOT_DAYS = (int) ((startDate.getTime() - endDate.getTime()) / (24*60*60*1000))-1; //-7

								if(CANC_DAYS < SLOT_DAYS)CANC_DAYS=SLOT_DAYS;
								if(CANC_DAYS >0)CANC_DAYS=0;

								if (DBConnector.UpdateNaverShoppingSlotInfo(SlotIdx, "USE_YN", "N", member.getUSER_IDX())) {
									// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)
									DBConnector.InsertNaverShoppingSlotLogInfo(SlotIdx, CANC_DAYS, 1, "D", member.getUSER_IP(), member.getUSER_IDX());
									jParamObject.put("RESULT", "O");
									jParamObject.put("MSG", "");
									success++;
								} else {
									jParamObject.put("MSG", "DB error");
									fail++;
								}
							} else {
								jParamObject.put("MSG", "Non-Existent id");
								fail++;
							}
							responseArray.add(jParamObject);
						}else if(USER_TYPE.contains("NP")){
							Member user = DBConnector.getMemberByIdx(jParamObject.get("USER_IDX")!=null?jParamObject.get("USER_IDX").toString():"");

							NaverPlaceSlot s = DBConnector.getNaverPlaceSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
							Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);

							jParamObject.put("RESULT", "X");
							if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || s.getUSER_IDX() == member.getUSER_IDX()))){

								// 삭제 작업일 = 오늘날짜 -끝나는 날짜, 삭제 작업일
								LocalDateTime currentDateTime = LocalDateTime.now();
								String Today = currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

								int CANC_DAYS = 0;
								int SLOT_DAYS = 0;
								String strFormat = "yyyyMMdd";    //strStartDate 와 strEndDate 의 format

								SimpleDateFormat sdf = new SimpleDateFormat(strFormat);

								Date todayDate =  sdf.parse(Today); //1204
								Date startDate =  sdf.parse(s.getSLOT_STDT()); //1205
								Date endDate =  sdf.parse(s.getSLOT_ENDT()); //1211

								CANC_DAYS = (int) ((todayDate.getTime() - endDate.getTime()) / (24*60*60*1000)); //-7
								SLOT_DAYS = (int) ((startDate.getTime() - endDate.getTime()) / (24*60*60*1000))-1; //-7

								if(CANC_DAYS < SLOT_DAYS)CANC_DAYS=SLOT_DAYS;
								if(CANC_DAYS >0)CANC_DAYS=0;

								if (DBConnector.UpdateNaverPlaceSlotInfo(SlotIdx, "USE_YN", "N", member.getUSER_IDX())) {
									// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)
									DBConnector.InsertNaverPlaceSlotLogInfo(SlotIdx, CANC_DAYS, 1, "D", member.getUSER_IP(), member.getUSER_IDX());
									jParamObject.put("RESULT", "O");
									jParamObject.put("MSG", "");
									success++;
								} else {
									jParamObject.put("MSG", "DB error");
									fail++;
								}
							} else {
								jParamObject.put("MSG", "Non-Existent id");
								fail++;
							}
							responseArray.add(jParamObject);
						}

					}
					jobj.put("value", responseArray);
					jobj.put("result", true);
					jobj.put("alert", success+"건 삭제 완료.");
					jobj.put("url", URL);
				}catch(Exception e){ e.printStackTrace(); }
			}else{

			}
		}
		return jobj.toString();
	}

	/* 선택된 슬롯 연장 */
	@ResponseBody
//	@PostMapping("/extendSlot")
	@RequestMapping(value="/extendSlot", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String ExtendSlot(@RequestParam HashMap<String, String> param) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", "200");
		jobj.put("result", false);
		jobj.put("value", "");
		jobj.put("alert", "연장 실패.");
		jobj.put("url", "");
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			/* 업무시간 외 셧다운 주석처리 */
			if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				jobj.put("alert", "마감시간을 확인해주세요.");
				return jobj.toString();
			}

			if(param!=null && param.size() > 0) {
				try {
					JSONArray jParamArray	= (JSONArray) JSONValue.parse(param.get("PARAM"));
					JSONArray responseArray = new JSONArray();
					int success = 0;
					int fail = 0;
					String URL = "";

					for(int i=0;i<jParamArray.size();i++){
						JSONObject jParamObject = (JSONObject)jParamArray.get(i);

						int UserIdx = Integer.parseInt((String) jParamObject.get("USER_IDX"));
						int SlotIdx = Integer.parseInt((String) jParamObject.get("SLOT_IDX"));

						String PAGE = (String) jParamObject.get("PAGE");
						String PARAM = (String) jParamObject.get("PARAM");
						String USER_TYPE = (String) jParamObject.get("USER_TYPE");
						if(i==0 && PAGE != null && !"".equals(PAGE)){
							URL = URL + "/" + PAGE;
						}
						if(i==0 && PARAM != null && !"".equals(PARAM)){
							URL = URL + PARAM;
						}

						if(USER_TYPE.contains("NS")){

//						Member user = DBConnector.getMemberByIdx(jParamObject.get("USER_IDX")!=null?jParamObject.get("USER_IDX").toString():"");

							NaverShoppingSlot s = DBConnector.getNaverShoppingSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
							Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);
							jParamObject.put("RESULT", "X");
							if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || s.getUSER_IDX() == member.getUSER_IDX()))){
								int SLOT_DAYS = Integer.parseInt((String) jParamObject.get("SLOT_DAYS"));

								// 연장 종료일 = 종료날짜 + 연장 작업일
								String strFormat = "yyyyMMdd";    //strStartDate 와 strEndDate 의 format
								SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
								Date endDate =  sdf.parse(s.getSLOT_ENDT());

								Calendar cal = Calendar.getInstance();
								cal.setTime(endDate);
								cal.add(Calendar.DATE, SLOT_DAYS);
								String EXTD_DATE = sdf.format(cal.getTime());

								if (DBConnector.UpdateNaverShoppingSlotInfo(SlotIdx, "SLOT_ENDT", EXTD_DATE, member.getUSER_IDX())) {
									// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)
									DBConnector.InsertNaverShoppingSlotLogInfo(SlotIdx, SLOT_DAYS, 1, "E", member.getUSER_IP(), member.getUSER_IDX());
									jParamObject.put("RESULT", "O");
									jParamObject.put("MSG", "");
									success++;
								} else {
									jParamObject.put("MSG", "DB error");
									fail++;
								}
							} else {
								jParamObject.put("MSG", "Non-Existent id");
								fail++;
							}
							responseArray.add(jParamObject);
						}else if(USER_TYPE.contains("NP")){

//						Member user = DBConnector.getMemberByIdx(jParamObject.get("USER_IDX")!=null?jParamObject.get("USER_IDX").toString():"");

							NaverPlaceSlot s = DBConnector.getNaverPlaceSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
							Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);
							jParamObject.put("RESULT", "X");
							if("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || s.getUSER_IDX() == member.getUSER_IDX()))){
								int SLOT_DAYS = Integer.parseInt((String) jParamObject.get("SLOT_DAYS"));

								// 연장 종료일 = 종료날짜 + 연장 작업일
								String strFormat = "yyyyMMdd";    //strStartDate 와 strEndDate 의 format
								SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
								Date endDate =  sdf.parse(s.getSLOT_ENDT());

								Calendar cal = Calendar.getInstance();
								cal.setTime(endDate);
								cal.add(Calendar.DATE, SLOT_DAYS);
								String EXTD_DATE = sdf.format(cal.getTime());

								if (DBConnector.UpdateNaverPlaceSlotInfo(SlotIdx, "SLOT_ENDT", EXTD_DATE, member.getUSER_IDX())) {
									// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)
									DBConnector.InsertNaverPlaceSlotLogInfo(SlotIdx, SLOT_DAYS, 1, "E", member.getUSER_IP(), member.getUSER_IDX());
									jParamObject.put("RESULT", "O");
									jParamObject.put("MSG", "");
									success++;
								} else {
									jParamObject.put("MSG", "DB error");
									fail++;
								}
							} else {
								jParamObject.put("MSG", "Non-Existent id");
								fail++;
							}
							responseArray.add(jParamObject);
						}

					}
					jobj.put("value", responseArray);
					jobj.put("result", true);
					jobj.put("alert", success+"건 연장 완료.");
					jobj.put("url", URL);
				}catch(Exception e){ e.printStackTrace(); }
			}else{

			}
		}
		return jobj.toString();
	}

	/* 선택된 슬롯 상태 변경 */
	@ResponseBody
//	@PostMapping("/statusSlot")
	@RequestMapping(value="/statusSlot", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String StatusSlot(@RequestParam HashMap<String, String> param) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", "200");
		jobj.put("result", false);
		jobj.put("value", "");
		jobj.put("alert", "확인 실패.");
		jobj.put("url", "");
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			/* 업무시간 외 셧다운 주석처리 */
			if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				jobj.put("alert", "마감시간을 확인해주세요.");
				return jobj.toString();
			}

			if(param!=null && param.size() > 0) {
				try {
					JSONArray jParamArray	= (JSONArray) JSONValue.parse(param.get("PARAM"));
					JSONArray responseArray = new JSONArray();
					int success = 0;
					int fail = 0;
					String URL = "";

					for(int i=0;i<jParamArray.size();i++){
						JSONObject jParamObject = (JSONObject)jParamArray.get(i);

						int UserIdx = Integer.parseInt((String) jParamObject.get("USER_IDX"));
						int SlotIdx = Integer.parseInt((String) jParamObject.get("SLOT_IDX"));

						String PAGE = (String) jParamObject.get("PAGE");
						String PARAM = (String) jParamObject.get("PARAM");
						String USER_TYPE = (String) jParamObject.get("USER_TYPE");
						if(i==0 && PAGE != null && !"".equals(PAGE)){
							URL = URL + "/" + PAGE;
						}
						if(i==0 && PARAM != null && !"".equals(PARAM)){
							URL = URL + PARAM;
						}

						if(USER_TYPE.contains("NS")){
							//						Member user = DBConnector.getMemberByIdx(jParamObject.get("USER_IDX")!=null?jParamObject.get("USER_IDX").toString():"");

							NaverShoppingSlot s = DBConnector.getNaverShoppingSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
							Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);

							jParamObject.put("RESULT", "X");
							if("M".equals(member.getUSER_PERM())){

								if (DBConnector.UpdateNaverShoppingSlotInfo(SlotIdx, "SLOT_STAT", "G", member.getUSER_IDX())) {
									// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)

									jParamObject.put("RESULT", "O");
									jParamObject.put("MSG", "");
									success++;
								} else {
									jParamObject.put("MSG", "DB error");
									fail++;
								}
							} else {
								jParamObject.put("MSG", "Non-Existent id");
								fail++;
							}
							responseArray.add(jParamObject);
						}else if(USER_TYPE.contains("NP")){
							//						Member user = DBConnector.getMemberByIdx(jParamObject.get("USER_IDX")!=null?jParamObject.get("USER_IDX").toString():"");

							NaverPlaceSlot s = DBConnector.getNaverPlaceSlotList(SlotIdx , 0, null, null, null, member.getUSER_PERM(), member.getUSER_IDX()).get(0);
							Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);

							jParamObject.put("RESULT", "X");
							if("M".equals(member.getUSER_PERM())){

								if (DBConnector.UpdateNaverPlaceSlotInfo(SlotIdx, "SLOT_STAT", "G", member.getUSER_IDX())) {
									// 슬롯 로그는 신규, 연장, 삭제만 (수정, 상태변경은 X)

									jParamObject.put("RESULT", "O");
									jParamObject.put("MSG", "");
									success++;
								} else {
									jParamObject.put("MSG", "DB error");
									fail++;
								}
							} else {
								jParamObject.put("MSG", "Non-Existent id");
								fail++;
							}
							responseArray.add(jParamObject);
						}

					}
					jobj.put("value", responseArray);
					jobj.put("result", true);
					jobj.put("alert", success+"건 확인 완료.");
					jobj.put("url", URL);
				}catch(Exception e){ e.printStackTrace(); }
			}else{

			}
		}
		return jobj.toString();
	}

	/* 슬롯타입 추가 */
	@RequestMapping(value="/setSlotType", method=RequestMethod.POST)
	public String SetSlotType(HttpServletRequest Request, HttpServletResponse response, Model model) throws Exception{
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			if(!"M".equals(member.getUSER_PERM())){
				return "redirect:/login";
			}
			String USER_TYPE = Request.getParameter("USER_TYPE_IN");

			if(USER_TYPE.contains("NS")){
				String TYPE_NAME = Request.getParameter("NS_TYPE_NAME_IN");
				int SLOT_TYPE_IDX = DBConnector.InsertNaverShoppingSlotType(TYPE_NAME, "Y", member.getUSER_IDX());
			}else if(USER_TYPE.contains("NP")){
				String TYPE_NAME = Request.getParameter("NP_TYPE_NAME_IN");
				int SLOT_TYPE_IDX = DBConnector.InsertNaverPlaceSlotType(TYPE_NAME, "Y", member.getUSER_IDX());
			}
			return "redirect:/slot-type-manage";
		}
		return "redirect:/login";
	}

	/* 슬롯타입 수정 */
	@RequestMapping(value="/setSlotType/{SLOT_TYPE_IDX}", method=RequestMethod.POST)
	public String SetSlotTypeIdx(@PathVariable("SLOT_TYPE_IDX") int SLOT_TYPE_IDX, HttpServletRequest Request, Model model) throws Exception{
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			if(!"M".equals(member.getUSER_PERM())){
				return "redirect:/login";
			}
			String USER_TYPE = Request.getParameter("USER_TYPE_UP");

			if(USER_TYPE.contains("NS")){
				NaverShoppingSlotType st = DBConnector.getNaverShoppingSlotType(SLOT_TYPE_IDX,"Y").get(0);
				if(st != null){
					String TYPE_NAME = Request.getParameter("NS_TYPE_NAME_UP");
					DBConnector.UpdateNaverShoppingSlotType(SLOT_TYPE_IDX, "TYPE_NAME", TYPE_NAME, member.getUSER_IDX());
				}
			}else if(USER_TYPE.contains("NP")){
				NaverPlaceSlotType st = DBConnector.getNaverPlaceSlotType(SLOT_TYPE_IDX,"Y").get(0);
				if(st != null){
					String TYPE_NAME = Request.getParameter("NP_TYPE_NAME_UP");
					DBConnector.UpdateNaverPlaceSlotType(SLOT_TYPE_IDX, "TYPE_NAME", TYPE_NAME, member.getUSER_IDX());
				}
			}
			return "redirect:/slot-type-manage";
		}
		return "redirect:/login";
	}

	/* 슬롯타입 삭제 */
	@ResponseBody
	@RequestMapping(value="/deleteSlotType", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String DeleteNaverShoppingSlotType(@RequestParam HashMap<String, String> param) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", "200");
		jobj.put("result", false);
		jobj.put("value", "");
		jobj.put("alert", "삭제 실패.");
		jobj.put("url", "");
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			if(!"M".equals(member.getUSER_PERM())){
				return jobj.toString();
			}

			if(param!=null && param.size() > 0) {
				try {
					JSONArray responseArray = new JSONArray();

					JSONObject jParamObject = (JSONObject)JSONValue.parse(param.get("PARAM"));

					int SLOT_TYPE_IDX = Integer.parseInt((String) jParamObject.get("SLOT_TYPE_IDX"));
					String USER_TYPE = (String) jParamObject.get("USER_TYPE");

					if(USER_TYPE.contains("NS")){
						jParamObject.put("RESULT", "X");

						if (DBConnector.UpdateNaverShoppingSlotType(SLOT_TYPE_IDX, "USE_YN", "N", member.getUSER_IDX())) {
							jParamObject.put("RESULT", "O");
							jParamObject.put("MSG", "");
						} else {
							jParamObject.put("MSG", "DB error");
						}
						
						responseArray.add(jParamObject);
					}else if(USER_TYPE.contains("NP")){
						jParamObject.put("RESULT", "X");

						if (DBConnector.UpdateNaverPlaceSlotType(SLOT_TYPE_IDX, "USE_YN", "N", member.getUSER_IDX())) {
							jParamObject.put("RESULT", "O");
							jParamObject.put("MSG", "");
						} else {
							jParamObject.put("MSG", "DB error");
						}

						responseArray.add(jParamObject);
					}
					jobj.put("value", responseArray);
					jobj.put("result", true);
					jobj.put("alert", "삭제 완료.");
				}catch(Exception e){ e.printStackTrace(); }
			}else{

			}
		}
		return jobj.toString();
	}

}
