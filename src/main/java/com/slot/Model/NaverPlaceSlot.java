package com.slot.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class NaverPlaceSlot {
	private int SLOT_IDX;		// IDX
	private int USER_IDX;		// IDX
	private int NP_SLOT_TYPE_IDX;		// IDX
	private String TYPE_NAME;		// ID
	private String USER_ID;		// ID
	private String PLCE_NAME;		// 묶음MID
	private String PLCE_CODE;		// 단품MID
	private String PLCE_KYWD;		// 상품 키워드
	private String PLCE_URL;		// 상품링크
	private String SLOT_STDT;		// 시작일

	private String SLOT_ENDT;		// 종료일
	private String SLOT_STAT;		// 상태 G : 초록색, R : 빨간색
	private String USE_YN;			// 사용여부
	private int INST_USER_IDX;			// 등록자
	private String INST_USER;			// 등록자
	private String INST_DT;			// 등록 일자
	private int UPDT_USER_IDX;			// 수정자
	private String UPDT_USER;			// 수정자
	private String UPDT_DT;			// 수정 일자
}
