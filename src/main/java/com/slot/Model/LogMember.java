package com.slot.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LogMember {
	private int LOG_USER_IDX;			// 로그 IDX
	private int USER_IDX;		// IDX
	private String USER_ID;		// ID
	private String USER_NAME;		// 이름
	private String INST_ACTN;		// C : 신규, U : 수정, D : 삭제, L : 로그인
	private String INST_IP;			// IP
	private int INST_USER;		// 등록자
	private String INST_DT;			// 등록 일자
}
