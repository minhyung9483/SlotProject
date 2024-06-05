package com.slot.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class NaverPlaceSlotType {
	private int NP_SLOT_TYPE_IDX;		// IDX
	private String TYPE_NAME;		// 아이디
	private String SLOT_STTM;		// 시작시간
	private String SLOT_ENTM;		// 종료시간
	private String USE_YN;			// 사용여부
	private int INST_USER_IDX;			// 등록자
	private String INST_USER;			// 등록자
	private String INST_DT;			// 등록 일자
	private int UPDT_USER_IDX;			// 수정자
	private String UPDT_USER;			// 수정자
	private String UPDT_DT;			// 수정 일자
}
