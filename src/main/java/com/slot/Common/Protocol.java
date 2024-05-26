package com.slot.Common;

public class Protocol {
	 
	public static final String ALERT					= "_alert";
	public static class Json{
		public static final String KEY_MEMBER			= "_m_member";
		public static final String KEY_VALUE			= "_m_value";
		public static final String KEY_AUTHORITY		= "_m_authority";
		public static final String KEY_PART				= "_m_part";
		public static final String KEY_DEPARTMENT		= "_m_department";

		public static final String KEY_ADMIN			= "_m_admin";
		public static final String KEY_ID				= "_m_id";
		public static final String KEY_EMAIL			= "_m_email";
		public static final String KEY_PHONE			= "_m_phone";
		public static final String KEY_NAME				= "_m_name";
		public static final String KEY_LEVEL			= "_m_level";
		public static final String KEY_STORE			= "_m_store";
		public static final String KEY_PASSWORD			= "_m_pwd";
		public static final String KEY_NUMBER			= "_m_number";
		public static final String KEY_PAGE				= "_m_page";
		public static final String KEY_TYPE				= "_m_type";
		public static final String KEY_MAX				= "_m_max";
		public static final String KEY_URL				= "_m_url";
		public static final String KEY_RESULT			= "_m_result";
		public static final String KEY_CODE				= "_m_code";
		public static final String KEY_ALL				= "_m_all";
		public static final String KEY_STATE			= "_m_state";
		public static final String KEY_MESSAGE			= "_m_message";
	}
	public static class Page{
		public static final String KEY_STYLE_CODE		= "_m_style_code";
		public static final String KEY_QUESTION_CODE		= "_m_question_code";
		public static final String KEY_NOTICE_CODE		= "_m_notice_code";
		public static final String KEY_ODM_CODE		= "_m_odm_code";
	}
	
	public static class State{
		
		public static final int KEY_SUCCESS						= 200;
		// Page Error
		public static final int ERR_PAGE_NOT_FOUND				= 404;
		// Class Error : 600
		public static final int ERR_CLASS_NOT_ENOUGH_RESULT		= 600;
		public static final int ERR_SELECT_CLASS				= 601;
		// Auth Error
		public static final int ERR_IS_NOT_MEMBER				= 700;
		public static final int ERR_LOGIN_FAILED				= 701;
	}

	public static class ResponseType{
		public static final String Code							= "code";
		public static final String Result						= "result";
		public static final String Message						= "message";
		public static final String Value						= "value";
	}
	
	public static class MemberLevel{
		public static final String CODE_ADMN					= "M";			// 관리자
		public static final String CODE_USER					= "G";			// 총판 유저
		public static final String CODE_SELL					= "S";			// 셀러 유저
	}
}
