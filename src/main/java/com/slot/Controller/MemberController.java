package com.slot.Controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slot.Common.*;
import com.slot.Model.Member;
import com.slot.Model.Slot;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


@Controller
public class MemberController {
	private static final String TAG = "MEMBER_CONTROLLER";
	
	private Member member;
	
	@PostMapping("/setLogin")
	public String SetLogin(HttpServletRequest httpServletRequest, Model model) {
		String p_USER_ID = (String) httpServletRequest.getParameter("p_USER_ID");
		String p_USER_PWD = (String) httpServletRequest.getParameter("p_USER_PWD");

		Logger.Debug(TAG, "Login_POST");
		member = DBConnector.getMemberById_N_Password(p_USER_ID, p_USER_PWD);

		if(member!=null) {
			//IP
			String ip = httpServletRequest.getHeader("X-Forwarded-For");
			if (ip == null) ip = httpServletRequest.getRemoteAddr();
			member.setUSER_IP(ip);

			if(DBConnector.UpdateConnInfo(member.getUSER_IDX())) {
				ContextUtil.setAttrToSession(Protocol.Json.KEY_MEMBER, member);
				DBConnector.InsertMemberLogInfo(member.getUSER_IDX(), "L", member.getUSER_IP(), member.getUSER_IDX());
				return "redirect:/";
			}else{
				model.addAttribute(Protocol.ALERT, "Failed.");
				return "Front/v1/login";
			}
		}else {
//			Logger.Info(TAG, "Login_Failed");
			model.addAttribute(Protocol.ALERT, "아이디 또는 비밀번호가 일치하지 않습니다.");
			return "Front/v1/login";
		}
	}

	/* 유저 추가 */
	@RequestMapping(value="/setUser", method=RequestMethod.POST)
	public String SetUser(HttpServletRequest Request, HttpServletResponse response, Model model) throws Exception{
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			/* 업무시간 외 셧다운 주석처리 */
			/*if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				return "redirect:/users";
			}*/

			String USER_ID = Request.getParameter("USER_ID_IN");
			String USER_TYPE = Request.getParameter("USER_TYPE_IN");
			String RETURN_URL = "";
			String MENU = "";

			if(USER_TYPE.contains("NS")){
				RETURN_URL = "shopping";
				MENU = "네이버쇼핑유저관리";
			}else if(USER_TYPE.contains("NP")){
				RETURN_URL = "place";
				MENU = "네이버플레이스유저관리";
			}

			if(DBConnector.CheckMemberById(USER_ID)){
				model.addAttribute(Protocol.ALERT, "아이디가 이미 존재합니다.");
				model.addAttribute("MENU",MENU);
				return "Front/v1/naver-"+RETURN_URL+"-users";
			}else{
				if("M".equals(member.getUSER_PERM())){
					String USER_PWD = Request.getParameter("USER_PWD_IN");
					String USER_PERM = Request.getParameter("USER_PERM_IN");
					String USER_MEMO = Request.getParameter("USER_MEMO_IN");

					int USER_IDX = DBConnector.InsertMemberInfo(USER_ID, USER_PWD, USER_PERM!=null ? USER_PERM : "S", USER_TYPE, USER_MEMO, member.getUSER_IDX());
					if(USER_IDX > 0)DBConnector.InsertMemberLogInfo(USER_IDX, "C", member.getUSER_IP(), member.getUSER_IDX());

					model.addAttribute("MENU",MENU);
					return "redirect:/naver-"+RETURN_URL+"-users";
				}else if("G".equals(member.getUSER_PERM())){
					String USER_PWD = Request.getParameter("USER_PWD_IN");
					String USER_MEMO = Request.getParameter("USER_MEMO_IN");

					int USER_IDX = DBConnector.InsertMemberInfo(USER_ID, USER_PWD, null, USER_TYPE, USER_MEMO, member.getUSER_IDX());
					if(USER_IDX > 0)DBConnector.InsertMemberLogInfo(USER_IDX, "C", member.getUSER_IP(), member.getUSER_IDX());

					model.addAttribute("MENU",MENU);
					return "redirect:/naver-"+RETURN_URL+"-users";
				}else{
					model.addAttribute(Protocol.ALERT, "Permission denied.");
					return "redirect:/naver-"+RETURN_URL+"-slots";
				}
			}
		}
		return "redirect:/login";
	}

	/* 유저 정보 수정 */
	@RequestMapping(value="/setUser/{UserIdx}", method=RequestMethod.POST)
	public String SetUserIdx(@PathVariable("UserIdx") int UserIdx, HttpServletRequest Request, HttpServletResponse response, Model model) throws Exception{
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			String USER_TYPE = Request.getParameter("USER_TYPE_UP");
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
			/*if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				return "redirect:/naver-"+RETURN_URL+"-users";
			}*/

			Member m = DBConnector.getMemberList(UserIdx , 0, null, null, member.getUSER_PERM(), USER_TYPE, member.getUSER_IDX()).get(0);
			if(m != null){
				if("M".equals(member.getUSER_PERM())){
					String USER_PWD = Request.getParameter("USER_PWD_UP");
					String USER_PERM = Request.getParameter("USER_PERM_UP");
					String USER_MEMO = Request.getParameter("USER_MEMO_UP");

					String PAGE = Request.getParameter("PAGE_UP");
					String PARAM = Request.getParameter("PARAM_UP");
					String URL = "";
					if(PAGE != null && !"".equals(PAGE)){
						URL = URL + "/" + PAGE;
					}
					if(PARAM != null && !"".equals(PARAM)){
						URL = URL + PARAM;
					}

					if(DBConnector.UpdateMemberInfo(UserIdx, USER_PWD, USER_PERM!=null ? USER_PERM : "S", USER_TYPE, USER_MEMO)){
						DBConnector.InsertMemberLogInfo(UserIdx, "U", member.getUSER_IP(), member.getUSER_IDX());
					}

					model.addAttribute("MENU",MENU);
					return "redirect:/naver-"+RETURN_URL+"-users"+Util.encodeKorean(URL);
				}else if("G".equals(member.getUSER_PERM()) && (m.getINST_ADMN_IDX() == member.getUSER_IDX() || m.getUSER_IDX() == member.getUSER_IDX())){
					String USER_PWD = Request.getParameter("USER_PWD_UP");
					String USER_MEMO = Request.getParameter("USER_MEMO_UP");

					String PAGE = Request.getParameter("PAGE_UP");
					String PARAM = Request.getParameter("PARAM_UP");
					String URL = "";
					if(PAGE != null && !"".equals(PAGE)){
						URL = URL + "/" + PAGE;
					}
					if(PARAM != null && !"".equals(PARAM)){
						URL = URL + PARAM;
					}

					if(DBConnector.UpdateMemberInfo(UserIdx, USER_PWD, null, USER_TYPE, USER_MEMO)){
						DBConnector.InsertMemberLogInfo(UserIdx, "U", member.getUSER_IP(), member.getUSER_IDX());
					}

					model.addAttribute("MENU",MENU);
					return "redirect:/naver-"+RETURN_URL+"-users"+Util.encodeKorean(URL);
				}
			}else{
				model.addAttribute(Protocol.ALERT, "Permission denied.");
				return "redirect:/naver-"+RETURN_URL+"-slots";
			}
		}
		return "redirect:/login";
	}

	@ResponseBody
//	@PostMapping("/deleteUser")
	@RequestMapping(value="/deleteUser", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public String DeleteUser(@RequestParam HashMap<String, String> param) {
		JSONObject jobj = new JSONObject();
		jobj.put("code", "200");
		jobj.put("result", false);
		jobj.put("value", "");
		jobj.put("alert", "삭제 실패.");
		jobj.put("url", "");
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);
		if(member!=null) {
			/* 업무시간 외 셧다운 주석처리 */
			/*if(!"M".equals(member.getUSER_PERM()) && !Util.checkWorkingTime()){
				return jobj.toString();
			}*/

			if(param!=null && param.size() > 0) {
				try {
					JSONArray jParamArray	= (JSONArray) JSONValue.parse(param.get("PARAM"));
					JSONArray responseArray = new JSONArray();
					int success = 0;
					int fail = 0;
					String URL = "";

					for(int i=0;i<jParamArray.size();i++){
						JSONObject jParamObject = (JSONObject)jParamArray.get(i);

						String PAGE = (String) jParamObject.get("PAGE");
						String PARAM = (String) jParamObject.get("PARAM");
						if(i==0 && PAGE != null && !"".equals(PAGE)){
							URL = URL + "/" + PAGE;
						}
						if(i==0 && PARAM != null && !"".equals(PARAM)){
							URL = URL + PARAM;
						}

						Member user = DBConnector.getMemberByIdx(jParamObject.get("USER_IDX")!=null?jParamObject.get("USER_IDX").toString():"");

						jParamObject.put("RESULT", "X");
						if (user != null && ("M".equals(member.getUSER_PERM()) || ("G".equals(member.getUSER_PERM()) && (user.getINST_ADMN_IDX() == member.getUSER_IDX())))) { //총판이 자기 계정을 삭제는 못함
							if (DBConnector.UpdateMemberInfo(
									jParamObject.get("USER_IDX")!=null? Integer.parseInt(jParamObject.get("USER_IDX").toString()) :0,
									"USE_YN",
									"N")) {
								DBConnector.InsertMemberLogInfo(user.getUSER_IDX(), "D", member.getUSER_IP(), member.getUSER_IDX());
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

	private void printMessage(HttpServletResponse resp, String msg) throws Exception {
		resp.setCharacterEncoding("utf-8");
		resp.setHeader("Content-Type", "text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();
		//target이 지정되지 않은 경우 history.back() 으로 처리
		out.println("<script type='text/javascript'>");
		out.println("alert('" + msg + "');");
		out.println("history.back();");
		out.println("</script>");
	}
}
