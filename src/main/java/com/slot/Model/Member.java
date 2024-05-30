package com.slot.Model;

import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Member {
	private int USER_IDX;		// IDX
	private String USER_ID;		// 아이디
	private String USER_PWD;		// 비밀번호
	private String USER_PERM;		// 권한 M : 마스터, G : 총판, S : 셀러
	private String USER_TYPE;		// NP : 플레이스, NS : 쇼핑, A : 둘 다
	private int SLOT_EA;		// 현재 슬롯 개수
	private String USER_MEMO;		// 메모
	private String USER_IP;		// IP
	private String USE_YN;			// 사용여부
	private int INST_ADMN_IDX;		// 추가 관리자
	private String INST_ADMN;		// 추가 관리자
	private String INST_DT;		// 가입 일자
	private String CONN_DT;		// 마지막 접속 일자
}
