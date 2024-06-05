package com.slot.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class NaverPlaceLogSlot {
	private int LOG_SLOT_IDX;			// 로그 IDX
	private int USER_IDX;			//
	private int NP_SLOT_TYPE_IDX;		// IDX
	private String TYPE_NAME;		// ID
	private String USER_ID;			//
	private String INST_ACTN;			// C : 신규, E : 연장, D : 삭제, U : 수정, G : 작업재개, R : 일시중지
	private int SLOT_DAYS;			//
	private int SLOT_EA;			//
	private int SLOT_IDX;			//
	private String PLCE_NAME;			//
	private String PLCE_CODE;			//
	private String PLCE_KYWD;			//
	private String PLCE_URL;			//
	private int INST_USER_IDX;			//
	private String INST_USER_ID;			//
	private String INST_DT;			//
	private String INST_IP;			//
}
