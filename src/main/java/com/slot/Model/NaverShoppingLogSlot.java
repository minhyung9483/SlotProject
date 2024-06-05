package com.slot.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class NaverShoppingLogSlot {
	private int LOG_SLOT_IDX;			// 로그 IDX
	private int USER_IDX;			//
	private int NS_SLOT_TYPE_IDX;		// IDX
	private String TYPE_NAME;		// ID
	private String USER_ID;			//
	private String INST_ACTN;			// C : 신규, E : 연장, D : 삭제, U : 수정, G : 작업재개, R : 일시중지
	private int SLOT_DAYS;			//
	private int SLOT_EA;			//
	private int SLOT_IDX;			//
	private String PROD_GID;			//
	private String PROD_MID;			//
	private String PROD_KYWD;			//
	private String PROD_URL;			//
	private int INST_USER_IDX;			//
	private String INST_USER_ID;			//
	private String INST_DT;			//
	private String INST_IP;			//
}
