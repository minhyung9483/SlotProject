package com.slot.Common;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.slot.Model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DBConnector {
	static final String TAG = "SLOT_MYSQL";

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	static final String DB_URL = "jdbc:mysql://3.39.70.49:3306/slot?characterEncoding=utf8&serverTimezone=Asia/Seoul";
	static final String USERNAME = "thor";
	static final String PASSWORD = "ThunderTest7979!";

	/*static final String DB_URL = "jdbc:mysql://localhost:3306/slot?characterEncoding=utf8&serverTimezone=Asia/Seoul";
	static final String USERNAME = "thor";
	static final String PASSWORD = "ThunderTest7979!";*/

	/*static final String DB_URL = "jdbc:mysql://localhost:3306/slot?characterEncoding=utf8&serverTimezone=Asia/Seoul";
	static final String USERNAME = "jykim";
	static final String PASSWORD = "Wodud9407#";*/

	
	public DBConnector() {
		try {
			Logger.LogOn(false);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	////////////////////////////
	//// 	TB_Member 		////
	///////////////////////////

	public static Member getMemberByIdx(String USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		Member m = null;
		String sql = "SELECT\n" +
				"M.USER_IDX,\n" +
				"M.USER_ID,\n" +
				"M.USER_PWD,\n" +
				"M.USER_PERM,\n" +
				"M.USER_TYPE,\n" +
				"M.USER_MEMO,\n" +
				"M.USE_YN,\n" +
				"M.INST_ADMN AS INST_ADMN_IDX,\n" +
				"M_IN.USER_ID AS INST_ADMN,\n" +
				"DATE_FORMAT(M.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT,\n" +
				"DATE_FORMAT(M.CONN_DT, '%Y.%m.%d (%H:%i)') AS CONN_DT\n" +
				"FROM TB_MEMBER M\n" +
				"JOIN TB_MEMBER M_IN ON M.INST_ADMN = M_IN.USER_IDX\n" +
				"WHERE\n" +
				"M.USE_YN = 'Y' AND M.USER_IDX = ?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, USER_IDX);
			ResultSet rs = psmt.executeQuery();
			rs.last();
			if (rs.getRow() > 0) {
				m = new Member();
				rs.beforeFirst();
				while (rs.next()) {
					m.setUSER_IDX(rs.getInt("USER_IDX"));
					m.setUSER_ID(rs.getString("USER_ID"));
					m.setUSER_PWD(rs.getString("USER_PWD"));
					m.setUSER_PERM(rs.getString("USER_PERM"));
					m.setUSER_TYPE(rs.getString("USER_TYPE"));
					m.setUSER_MEMO(rs.getString("USER_MEMO"));
					m.setUSE_YN(rs.getString("USE_YN"));
					m.setINST_ADMN_IDX(rs.getInt("INST_ADMN_IDX"));
					m.setINST_ADMN(rs.getString("INST_ADMN"));
					m.setINST_DT(rs.getString("INST_DT"));
					m.setCONN_DT(rs.getString("CONN_DT"));
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}

	public static Member getMemberById(String USER_ID) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		Member m = null;
		String sql = "SELECT\n" +
				"M.USER_IDX,\n" +
				"M.USER_ID,\n" +
				"M.USER_PWD,\n" +
				"M.USER_PERM,\n" +
				"M.USER_TYPE,\n" +
				"M.USER_MEMO,\n" +
				"M.USE_YN,\n" +
				"M.INST_ADMN AS INST_ADMN_IDX,\n" +
				"M_IN.USER_ID AS INST_ADMN,\n" +
				"DATE_FORMAT(M.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT,\n" +
				"DATE_FORMAT(M.CONN_DT, '%Y.%m.%d (%H:%i)') AS CONN_DT\n" +
				"FROM TB_MEMBER M\n" +
				"JOIN TB_MEMBER M_IN ON M.INST_ADMN = M_IN.USER_IDX\n" +
				"WHERE\n" +
				"M.USE_YN = 'Y' AND M.USER_ID = ?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, USER_ID);
			ResultSet rs = psmt.executeQuery();
			rs.last();
			if (rs.getRow() > 0) {
				m = new Member();
				rs.beforeFirst();
				while (rs.next()) {
					m.setUSER_IDX(rs.getInt("USER_IDX"));
					m.setUSER_ID(rs.getString("USER_ID"));
					m.setUSER_PWD(rs.getString("USER_PWD"));
					m.setUSER_PERM(rs.getString("USER_PERM"));
					m.setUSER_TYPE(rs.getString("USER_TYPE"));
					m.setUSER_MEMO(rs.getString("USER_MEMO"));
					m.setUSE_YN(rs.getString("USE_YN"));
					m.setINST_ADMN_IDX(rs.getInt("INST_ADMN_IDX"));
					m.setINST_ADMN(rs.getString("INST_ADMN"));
					m.setINST_DT(rs.getString("INST_DT"));
					m.setCONN_DT(rs.getString("CONN_DT"));
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return m;
	}

	public static boolean CheckMemberById(String USER_ID) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		boolean result = false;
		String sql = "SELECT  * FROM TB_MEMBER WHERE 12			=? AND USE_YN='Y'";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, USER_ID);
			ResultSet rs = psmt.executeQuery();
			rs.last();

			if (rs.getRow() > 0) {
				result = true;
			}

			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static Member getMemberById_N_Password(String USER_ID, String USER_PWD) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		Member result = null;
		String sql = "SELECT\n" +
				"M.USER_IDX,\n" +
				"M.USER_ID,\n" +
				"M.USER_PWD,\n" +
				"M.USER_PERM,\n" +
				"M.USER_TYPE,\n" +
				"M.USER_MEMO,\n" +
				"M.USE_YN,\n" +
				"M.INST_ADMN AS INST_ADMN_IDX,\n" +
				"M_IN.USER_ID AS INST_ADMN,\n" +
				"DATE_FORMAT(M.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT,\n" +
				"DATE_FORMAT(M.CONN_DT, '%Y.%m.%d (%H:%i)') AS CONN_DT\n" +
				"FROM TB_MEMBER M\n" +
				"JOIN TB_MEMBER M_IN ON M.INST_ADMN = M_IN.USER_IDX\n" +
				"WHERE M.USE_YN = 'Y' AND M.USER_ID = ? AND M.USER_PWD = ?";
//				"WHERE M.USER_ID=? AND M.USER_PWD=sha2(?,512)";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, USER_ID);
			psmt.setString(2, USER_PWD);
			ResultSet rs = psmt.executeQuery();
			rs.last();
			if (rs.getRow() > 0) {
				result = new Member();
				rs.beforeFirst();
				while (rs.next()) {
					result.setUSER_IDX(rs.getInt("USER_IDX"));
					result.setUSER_ID(rs.getString("USER_ID"));
					result.setUSER_PWD(rs.getString("USER_PWD"));
					result.setUSER_PERM(rs.getString("USER_PERM"));
					result.setUSER_TYPE(rs.getString("USER_TYPE"));
					result.setUSER_MEMO(rs.getString("USER_MEMO"));
					result.setUSE_YN(rs.getString("USE_YN"));
					result.setINST_ADMN_IDX(rs.getInt("INST_ADMN_IDX"));
					result.setINST_ADMN(rs.getString("INST_ADMN"));
					result.setINST_DT(rs.getString("INST_DT"));
					result.setCONN_DT(rs.getString("CONN_DT"));
				}

			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static int getMemberListTotalCount(String SearchType, String SearchData, String USER_PERM, String USER_TYPE, int INST_ADMN_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		int result = 0;
		try {
			Class.forName(JDBC_DRIVER);

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(
					"SELECT count(*) AS CNT FROM TB_MEMBER M  WHERE M.USE_YN = 'Y' \n" +
//							("M".equals(USER_PERM) ? "" : "AND M.INST_ADMN = '"+INST_ADMN_IDX+"' \n") +
							("G".equals(USER_PERM) && INST_ADMN_IDX > 0 ? "AND (M.USER_IDX = '"+INST_ADMN_IDX+"' OR M.INST_ADMN = '"+INST_ADMN_IDX+"') \n" : "") + //총판일때
							//(USER_TYPE!=null && USER_TYPE.length() > 0? "AND (M.USER_TYPE = '"+USER_TYPE+"') \n" : "") + //유저타입
							(USER_TYPE!=null && USER_TYPE.length() > 0? "AND (M.USER_TYPE LIKE '%"+USER_TYPE+"%') \n" : "") + //유저타입
							(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND M." + SearchType + " LIKE '%" + SearchData + "%'"  : "") +
							" ;", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					result = rs.getInt("CNT");
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static List<Member> getMemberList(int USER_IDX, int Page, String SearchType, String SearchData, String USER_PERM, String USER_TYPE, int INST_ADMN_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		List<Member> result = new ArrayList<>();
		String sql = "";

		if(USER_TYPE.contains("NS")){
			sql = "SELECT\n" +
					"M.USER_IDX,\n" +
					"M.USER_ID,\n" +
					"M.USER_PWD,\n" +
					"M.USER_PERM,\n" +
					"M.USER_TYPE,\n" +
					"IFNULL(SLOT_EA, 0) AS SLOT_EA,\n" +
					"M.USER_MEMO,\n" +
					"M.USE_YN,\n" +
					"M.INST_ADMN AS INST_ADMN_IDX,\n" +
					"M_IN.USER_ID AS INST_ADMN,\n" +
					"DATE_FORMAT(M.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT,\n" +
					"DATE_FORMAT(M.CONN_DT, '%Y.%m.%d (%H:%i)') AS CONN_DT\n" +
					"FROM TB_MEMBER M\n" +
					"JOIN TB_MEMBER M_IN ON M.INST_ADMN = M_IN.USER_IDX\n" +
					"LEFT JOIN (\n" +
					"SELECT USER_IDX, COUNT(*) AS SLOT_EA \n" +
					"FROM TB_NS_SLOT \n" +
					"WHERE USE_YN = 'Y' \n" +
					"AND CONCAT(DATE_FORMAT(SLOT_ENDT, '%Y-%m-%d'), ' 23:59:59') > CURDATE()\n" +
//				"AND CONCAT(DATE_FORMAT(SLOT_STDT, '%Y-%m-%d'), ' 00:00:00') <= CURDATE()\n" +
					"GROUP BY USER_IDX\n" +
					") S ON M.USER_IDX = S.USER_IDX\n" +
					"WHERE M.USE_YN = 'Y' \n" +
					(USER_IDX > 0 ? "AND M.USER_IDX = '"+USER_IDX+"' \n" : "") +
//				("M".equals(USER_PERM) ? "" : "AND M.INST_ADMN = '"+INST_ADMN_IDX+"' \n") +
					("G".equals(USER_PERM) && INST_ADMN_IDX > 0 ? "AND (M.USER_IDX = '"+INST_ADMN_IDX+"' OR M.INST_ADMN = '"+INST_ADMN_IDX+"') \n" : "") + //총판일때
					//(USER_TYPE!=null && USER_TYPE.length() > 0? "AND (M.USER_TYPE = '"+USER_TYPE+"') \n" : "") + //유저타입
					(USER_TYPE!=null && USER_TYPE.length() > 0? "AND (M.USER_TYPE LIKE '%"+USER_TYPE+"%') \n" : "") + //유저타입
					(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND M." + SearchType + " LIKE '%" + SearchData + "%'"  : "") +
					"ORDER BY M.USER_IDX DESC LIMIT ?, 10";
		}else if(USER_TYPE.contains("NP")){
			sql = "SELECT\n" +
					"M.USER_IDX,\n" +
					"M.USER_ID,\n" +
					"M.USER_PWD,\n" +
					"M.USER_PERM,\n" +
					"M.USER_TYPE,\n" +
					"IFNULL(SLOT_EA, 0) AS SLOT_EA,\n" +
					"M.USER_MEMO,\n" +
					"M.USE_YN,\n" +
					"M.INST_ADMN AS INST_ADMN_IDX,\n" +
					"M_IN.USER_ID AS INST_ADMN,\n" +
					"DATE_FORMAT(M.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT,\n" +
					"DATE_FORMAT(M.CONN_DT, '%Y.%m.%d (%H:%i)') AS CONN_DT\n" +
					"FROM TB_MEMBER M\n" +
					"JOIN TB_MEMBER M_IN ON M.INST_ADMN = M_IN.USER_IDX\n" +
					"LEFT JOIN (\n" +
					"SELECT USER_IDX, COUNT(*) AS SLOT_EA \n" +
					"FROM TB_NP_SLOT \n" +
					"WHERE USE_YN = 'Y' \n" +
					"AND CONCAT(DATE_FORMAT(SLOT_ENDT, '%Y-%m-%d'), ' 23:59:59') > CURDATE()\n" +
//				"AND CONCAT(DATE_FORMAT(SLOT_STDT, '%Y-%m-%d'), ' 00:00:00') <= CURDATE()\n" +
					"GROUP BY USER_IDX\n" +
					") S ON M.USER_IDX = S.USER_IDX\n" +
					"WHERE M.USE_YN = 'Y' \n" +
					(USER_IDX > 0 ? "AND M.USER_IDX = '"+USER_IDX+"' \n" : "") +
//				("M".equals(USER_PERM) ? "" : "AND M.INST_ADMN = '"+INST_ADMN_IDX+"' \n") +
					("G".equals(USER_PERM) && INST_ADMN_IDX > 0 ? "AND (M.USER_IDX = '"+INST_ADMN_IDX+"' OR M.INST_ADMN = '"+INST_ADMN_IDX+"') \n" : "") + //총판일때
					//(USER_TYPE!=null && USER_TYPE.length() > 0? "AND (M.USER_TYPE = '"+USER_TYPE+"') \n" : "") + //유저타입
					(USER_TYPE!=null && USER_TYPE.length() > 0? "AND (M.USER_TYPE LIKE '%"+USER_TYPE+"%') \n" : "") + //유저타입
					(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND M." + SearchType + " LIKE '%" + SearchData + "%'"  : "") +
					"ORDER BY M.USER_IDX DESC LIMIT ?, 10";
		}

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setInt(1, Page * 10);

			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					Member m = new Member();
					m.setUSER_IDX(rs.getInt("USER_IDX"));
					m.setUSER_ID(rs.getString("USER_ID"));
					m.setUSER_PWD(rs.getString("USER_PWD"));
					m.setUSER_PERM(rs.getString("USER_PERM"));
					m.setUSER_TYPE(rs.getString("USER_TYPE"));
					m.setSLOT_EA(rs.getInt("SLOT_EA"));
					m.setUSER_MEMO(rs.getString("USER_MEMO"));
					m.setUSE_YN(rs.getString("USE_YN"));
					m.setINST_ADMN_IDX(rs.getInt("INST_ADMN_IDX"));
					m.setINST_ADMN(rs.getString("INST_ADMN"));
					m.setINST_DT(rs.getString("INST_DT"));
					m.setCONN_DT(rs.getString("CONN_DT"));
					result.add(m);
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static int InsertMemberInfo(String USER_ID, String USER_PWD,
									   String USER_PERM, String USER_TYPE, String USER_MEMO, int INST_ADMN) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "";
		if(USER_PERM!=null && USER_PERM.length() > 0){
			sql = "INSERT INTO TB_MEMBER(USER_ID, USER_PWD, USER_MEMO, INST_ADMN, INST_DT, CONN_DT, USER_TYPE, USER_PERM) \n" +
					"VALUES (?, ?, ?, ?, NOW(), NULL, ?, ?)";
		}else{
			sql = "INSERT INTO TB_MEMBER(USER_ID, USER_PWD, USER_MEMO, INST_ADMN, INST_DT, CONN_DT, USER_TYPE) \n" +
					"VALUES (?, ?, ?, ?, NOW(), NULL, ?)";
		}

		int UserIDX = 0;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.setString(1, USER_ID);
			psmt.setString(2, USER_PWD);
			psmt.setString(3, USER_MEMO);
			psmt.setInt(4, INST_ADMN);
			psmt.setString(5, USER_TYPE);
			if(USER_PERM!=null && USER_PERM.length() > 0){
				psmt.setString(6, USER_PERM);
			}
			psmt.execute();

			rs = psmt.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
			if (rs.next()) {
				UserIDX = rs.getInt(1); // 키값 초기화
			}

			rs.close();
			psmt.close();
			conn.close();
			return UserIDX;
		} catch (Exception e) {
			e.printStackTrace();
			UserIDX = -1;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return UserIDX;
	}

	public static boolean UpdateMemberInfo(int USER_IDX, String USER_PWD, String USER_PERM, String USER_TYPE, String USER_MEMO) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "UPDATE TB_MEMBER SET USER_PWD = ?, USER_MEMO = ? " +
				(USER_TYPE!=null && USER_TYPE.length() > 0 ? ", USER_TYPE = ? " : "") +
				(USER_PERM!=null && USER_PERM.length() > 0 ? ", USER_PERM = ? " : "") +
				"WHERE USER_IDX = ?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, USER_PWD);
			psmt.setString(2, USER_MEMO);
			psmt.setString(3, USER_TYPE);
			if(USER_PERM!=null && USER_PERM.length() > 0){
				psmt.setString(4, USER_PERM);
				psmt.setInt(5, USER_IDX);
			}else{
				psmt.setInt(4, USER_IDX);
			}

			psmt.execute();

			psmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean UpdateMemberInfo(int USER_IDX, String Key, String Value) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		boolean result = false;
		String sql = "UPDATE TB_MEMBER SET " + Key + "=? WHERE USER_IDX=?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, Value);
			psmt.setInt(2, USER_IDX);
			psmt.execute();

			psmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean InsertMemberLogInfo(int USER_IDX, String INST_ACTN, String INST_IP, int INST_USER) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "INSERT INTO TB_LOG_MEMBER(USER_IDX, INST_ACTN, INST_IP, INST_USER, INST_DT)" +
				" VALUES(?, ?, ?, ?, NOW())";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setInt(1, USER_IDX);
			psmt.setString(2, INST_ACTN);
			psmt.setString(3, INST_IP);
			psmt.setInt(4, INST_USER);
			psmt.execute();

			psmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean UpdateConnInfo(int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		boolean result = false;
		String sql = "UPDATE TB_MEMBER SET CONN_DT = now() WHERE USER_IDX=?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setInt(1, USER_IDX);
			psmt.execute();
			psmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	////////////////////////////////////
	//// 			TB_NS_SLOT 		////
	////////////////////////////////////

	public static int getNaverShoppingSlotListTotalCount(String SearchType, String SearchData, String USER_PERM, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		int result = 0;
		try {
			Class.forName(JDBC_DRIVER);

			if(SearchType!=null && SearchType.length() > 0){
				if("USER_ID".equals(SearchType)){
					SearchType = "M." + SearchType;
				}else{
					SearchType = "S." + SearchType;
				}
			}

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(
					"SELECT COUNT(*) AS CNT FROM TB_NS_SLOT S \n" +
							"JOIN TB_MEMBER M ON S.USER_IDX = M.USER_IDX AND M.USE_YN = 'Y' \n" +
							"LEFT JOIN TB_NS_SLOT_TYPE ST ON S.NS_SLOT_TYPE_IDX = ST.NS_SLOT_TYPE_IDX AND ST.USE_YN = 'Y' \n" +
							"WHERE S.USE_YN = 'Y'\n" +
							"AND CONCAT(DATE_FORMAT(S.SLOT_ENDT, '%Y-%m-%d'), ' 23:59:59') > CURDATE()\n" +
//							"AND CONCAT(DATE_FORMAT(S.SLOT_STDT, '%Y-%m-%d'), ' 00:00:00') <= CURDATE()\n" +
							(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND " + SearchType + " LIKE '%" + SearchData + "%'"  : "") + //검색
							("S".equals(USER_PERM) && USER_IDX > 0 ? "AND S.USER_IDX = '"+USER_IDX+"' \n" : "") + //셀러일때
							("G".equals(USER_PERM) && USER_IDX > 0 ? "AND (S.USER_IDX = '"+USER_IDX+"' OR M.INST_ADMN = '"+USER_IDX+"') \n" : "") + //총판일때
							" ;", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					result = rs.getInt("CNT");
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static List<NaverShoppingSlot> getNaverShoppingSlotList(int SLOT_IDX, int Page, String SearchType, String SearchData, String USER_PERM, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		List<NaverShoppingSlot> result = new ArrayList<>();

		if(SearchType!=null && SearchType.length() > 0){
			if("USER_ID".equals(SearchType)){
				SearchType = "M." + SearchType;
			}else{
				SearchType = "S." + SearchType;
			}
		}

		String sql = "SELECT\n" +
				"S.SLOT_IDX,\n" +
				"S.USER_IDX,\n" +
				"S.NS_SLOT_TYPE_IDX,\n" +
				"ST.TYPE_NAME,\n" +
				"M.USER_ID,\n" +
				"S.PROD_GID,\n" +
				"S.PROD_MID,\n" +
				"S.PROD_KYWD,\n" +
				"S.PROD_URL,\n" +
				"S.SLOT_STDT,\n" +
				"S.SLOT_ENDT,\n" +
				"S.SLOT_STAT,\n" +
				"S.USE_YN,\n" +
				"S.INST_USER AS INST_USER_IDX,\n" +
				"M_IN.USER_ID AS INST_USER,\n" +
				"DATE_FORMAT(S.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT,\n" +
				"S.UPDT_USER AS UPDT_USER_IDX,\n" +
				"M_UP.USER_ID AS UPDT_USER,\n" +
				"DATE_FORMAT(S.UPDT_DT, '%Y.%m.%d (%H:%i)') AS UPDT_DT\n" +
				"FROM TB_NS_SLOT S\n" +
				"JOIN TB_MEMBER M ON S.USER_IDX = M.USER_IDX AND M.USE_YN = 'Y'\n" +
				"JOIN TB_MEMBER M_IN ON S.INST_USER = M_IN.USER_IDX\n" +
				"JOIN TB_MEMBER M_UP ON S.UPDT_USER = M_UP.USER_IDX\n" +
				"LEFT JOIN TB_NS_SLOT_TYPE ST ON S.NS_SLOT_TYPE_IDX = ST.NS_SLOT_TYPE_IDX AND ST.USE_YN = 'Y' \n" +
				"WHERE S.USE_YN = 'Y'\n" +
				"AND CONCAT(DATE_FORMAT(S.SLOT_ENDT, '%Y-%m-%d'), ' 23:59:59') > CURDATE()\n" +
//				"AND CONCAT(DATE_FORMAT(S.SLOT_STDT, '%Y-%m-%d'), ' 00:00:00') <= CURDATE()\n" +
				(SLOT_IDX > 0 ? "AND S.SLOT_IDX = '"+SLOT_IDX+"' \n" : "") + //한개 구할때
				(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND " + SearchType + " LIKE '%" + SearchData + "%'"  : "") + //검색
				("S".equals(USER_PERM) && USER_IDX > 0 ? "AND S.USER_IDX = '"+USER_IDX+"' \n" : "") + //셀러일때
				("G".equals(USER_PERM) && USER_IDX > 0 ? "AND (S.USER_IDX = '"+USER_IDX+"' OR M.INST_ADMN = '"+USER_IDX+"') \n" : "") + //총판일때
				"ORDER BY S.SLOT_IDX DESC LIMIT ?, 10";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setInt(1, Page * 10);

			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					NaverShoppingSlot s = new NaverShoppingSlot();
					s.setSLOT_IDX(rs.getInt("SLOT_IDX"));
					s.setUSER_IDX(rs.getInt("USER_IDX"));
					s.setNS_SLOT_TYPE_IDX(rs.getInt("NS_SLOT_TYPE_IDX"));
					s.setTYPE_NAME(rs.getString("TYPE_NAME"));
					s.setUSER_ID(rs.getString("USER_ID"));
					s.setPROD_GID(rs.getString("PROD_GID"));
					s.setPROD_MID(rs.getString("PROD_MID"));
					s.setPROD_KYWD(rs.getString("PROD_KYWD"));
					s.setPROD_URL(rs.getString("PROD_URL"));
					s.setSLOT_STDT(rs.getString("SLOT_STDT"));
					s.setSLOT_ENDT(rs.getString("SLOT_ENDT"));
					s.setSLOT_STAT(rs.getString("SLOT_STAT"));
					s.setUSE_YN(rs.getString("USE_YN"));
					s.setINST_USER_IDX(rs.getInt("INST_USER_IDX"));
					s.setINST_USER(rs.getString("INST_USER"));
					s.setINST_DT(rs.getString("INST_DT"));
					s.setUPDT_USER_IDX(rs.getInt("UPDT_USER_IDX"));
					s.setUPDT_USER(rs.getString("UPDT_USER"));
					s.setUPDT_DT(rs.getString("UPDT_DT"));
					result.add(s);
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static int InsertNaverShoppingSlotInfo(int USER_IDX, String SLOT_STDT, String SLOT_ENDT, int INST_USER, int SLOT_TYPE) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO TB_NS_SLOT(USER_IDX, NS_SLOT_TYPE_IDX, SLOT_STDT, SLOT_ENDT, INST_USER, INST_DT, UPDT_USER, UPDT_DT) \n" +
				"VALUES(?, ?, ?, ?, ?, NOW(), ?, NOW())";
		int SlotIDX = 0;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.setInt(1, USER_IDX);
			psmt.setInt(2, SLOT_TYPE);
			psmt.setString(3, SLOT_STDT);
			psmt.setString(4, SLOT_ENDT);
			psmt.setInt(5, INST_USER);
			psmt.setInt(6, INST_USER);
			psmt.execute();

			rs = psmt.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
			if (rs.next()) {
				SlotIDX = rs.getInt(1); // 키값 초기화
			}

			rs.close();
			psmt.close();
			conn.close();
			return SlotIDX;
		} catch (Exception e) {
			e.printStackTrace();
			SlotIDX = -1;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return SlotIDX;
	}

	public static int InsertNaverShoppingSlotInfoByExcel(int USER_IDX, String SLOT_STDT, String SLOT_ENDT, int INST_USER, int SLOT_TYPE, String PROD_GID, String PROD_MID, String PROD_KYWD, String PROD_URL) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO TB_NS_SLOT(USER_IDX, NS_SLOT_TYPE_IDX, SLOT_STDT, SLOT_ENDT, INST_USER, INST_DT, UPDT_USER, UPDT_DT, PROD_GID, PROD_MID, PROD_KYWD, PROD_URL) \n" +
				"VALUES(?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";
		int SlotIDX = 0;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.setInt(1, USER_IDX);
			psmt.setInt(2, SLOT_TYPE);
			psmt.setString(3, SLOT_STDT);
			psmt.setString(4, SLOT_ENDT);
			psmt.setInt(5, INST_USER);
			psmt.setInt(6, INST_USER);
			psmt.setString(7, PROD_GID);
			psmt.setString(8, PROD_MID);
			psmt.setString(9, PROD_KYWD);
			psmt.setString(10, PROD_URL);
			psmt.execute();

			rs = psmt.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
			if (rs.next()) {
				SlotIDX = rs.getInt(1); // 키값 초기화
			}

			rs.close();
			psmt.close();
			conn.close();
			return SlotIDX;
		} catch (Exception e) {
			e.printStackTrace();
			SlotIDX = -1;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return SlotIDX;
	}

	public static boolean UpdateNaverShoppingSlotInfo(int SLOT_IDX, String PROD_GID, String PROD_MID, String PROD_KYWD,
										  String PROD_URL, String SLOT_STAT, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "";

		sql = "UPDATE TB_NS_SLOT SET PROD_GID = ?, PROD_MID = ?, PROD_KYWD = ?, PROD_URL = ?, SLOT_STAT = ?, UPDT_USER = ?, UPDT_DT = NOW() \n" +
				"WHERE SLOT_IDX = ?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, PROD_GID);
			psmt.setString(2, PROD_MID);
			psmt.setString(3, PROD_KYWD);
			psmt.setString(4, PROD_URL);
			psmt.setString(5, SLOT_STAT);
			psmt.setInt(6, USER_IDX);
			psmt.setInt(7, SLOT_IDX);
			psmt.execute();

			psmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean UpdateNaverShoppingSlotInfo(int SLOT_IDX, String Key, String Value, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "UPDATE TB_NS_SLOT SET " + Key + " = ?, UPDT_USER = ?, UPDT_DT = NOW() \n" +
				"WHERE SLOT_IDX = ?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, Value);
			psmt.setInt(2, USER_IDX);
			psmt.setInt(3, SLOT_IDX);
			psmt.execute();

			psmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean InsertNaverShoppingSlotLogInfo(int SLOT_IDX, int SLOT_DAYS, int SLOT_EA, String INST_ACTN, String INST_IP, int INST_USER) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "INSERT INTO TB_NS_LOG_SLOT(SLOT_IDX, SLOT_DAYS, SLOT_EA, INST_ACTN, INST_IP, INST_USER, INST_DT)" +
				" VALUES(?, ?, ?, ?, ?, ?, NOW())";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setInt(1, SLOT_IDX);
			psmt.setInt(2, SLOT_DAYS);
			psmt.setInt(3, SLOT_EA);
			psmt.setString(4, INST_ACTN);
			psmt.setString(5, INST_IP);
			psmt.setInt(6, INST_USER);
			psmt.execute();

			psmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	////////////////////////////////////
	//// 		TB_NS_LOG_SLOT 		////
	////////////////////////////////////

	public static int getNaverShoppingLogSlotListTotalCount(String SearchType, String SearchData, String SLOG_STDT, String SLOG_ENDT, String USER_PERM, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		int result = 0;
		try {
			Class.forName(JDBC_DRIVER);

			if(SearchType!=null && SearchType.length() > 0){
				if("USER_ID".equals(SearchType)){
					SearchType = "M." + SearchType;
				}else if("INST_ACTN".equals(SearchType)){
					SearchType = "L." + SearchType;
				}else if("PROD_GID".equals(SearchType) || "PROD_MID".equals(SearchType) || "PROD_KYWD".equals(SearchType)){
					SearchType = "S." + SearchType;
				}
			}

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(
					"SELECT COUNT(*) AS CNT \n" +
							"FROM TB_NS_LOG_SLOT L \n" +
							"LEFT JOIN TB_NS_SLOT S ON L.SLOT_IDX = S.SLOT_IDX \n" +
							"LEFT JOIN TB_MEMBER M ON S.USER_IDX = M.USER_IDX \n" +
							"LEFT JOIN TB_MEMBER M_IN ON L.INST_USER = M_IN.USER_IDX \n" +
							"LEFT JOIN TB_NS_SLOT_TYPE ST ON S.NS_SLOT_TYPE_IDX = ST.NS_SLOT_TYPE_IDX AND ST.USE_YN = 'Y' \n" +
							"WHERE 1=1 \n" +
							(SLOG_STDT!=null && SLOG_STDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_STDT + ", '%Y-%m-%d'), ' 00:00:00') <= L.INST_DT \n"  : "") + //시작일
							(SLOG_ENDT!=null && SLOG_ENDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_ENDT + ", '%Y-%m-%d'), ' 23:59:59') > L.INST_DT \n"  : "") + //종료일
							(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND " + SearchType + " LIKE '%" + SearchData + "%'"  : "") + //검색
							("S".equals(USER_PERM) && USER_IDX > 0 ? "AND S.USER_IDX = '"+USER_IDX+"' \n" : "") + //셀러일때
							("G".equals(USER_PERM) && USER_IDX > 0 ? "AND (S.USER_IDX = '"+USER_IDX+"' OR M.INST_ADMN = '"+USER_IDX+"') \n" : "") + //총판일때
							";", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					result = rs.getInt("CNT");
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static List<NaverShoppingLogSlot> getNaverShoppingLogSlotList(int Page, String SearchType, String SearchData, String SLOG_STDT, String SLOG_ENDT, String USER_PERM, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		List<NaverShoppingLogSlot> result = new ArrayList<>();

		if(SearchType!=null && SearchType.length() > 0){
			if("USER_ID".equals(SearchType)){
				SearchType = "M." + SearchType;
			}else if("INST_ACTN".equals(SearchType)){
				SearchType = "L." + SearchType;
			}else if("PROD_GID".equals(SearchType) || "PROD_MID".equals(SearchType) || "PROD_KYWD".equals(SearchType)){
				SearchType = "S." + SearchType;
			}
		}

		String sql = "SELECT \n" +
				"L.LOG_SLOT_IDX, \n" +
				"M.USER_IDX, \n" +
				"S.NS_SLOT_TYPE_IDX,\n" +
				"ST.TYPE_NAME,\n" +
				"M.USER_ID, \n" +
				"L.INST_ACTN, \n" +
				"L.SLOT_DAYS, \n" +
				"L.SLOT_EA, \n" +
				"L.SLOT_IDX, \n" +
				"S.PROD_GID, \n" +
				"S.PROD_MID, \n" +
				"S.PROD_KYWD, \n" +
				"M_IN.USER_IDX AS INST_USER_IDX, \n" +
				"M_IN.USER_ID AS INST_USER_ID, \n" +
				"DATE_FORMAT(L.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT, \n" +
				"L.INST_IP \n" +
				"FROM TB_NS_LOG_SLOT L \n" +
				"LEFT JOIN TB_NS_SLOT S ON L.SLOT_IDX = S.SLOT_IDX \n" +
				"LEFT JOIN TB_MEMBER M ON S.USER_IDX = M.USER_IDX \n" +
				"LEFT JOIN TB_MEMBER M_IN ON L.INST_USER = M_IN.USER_IDX \n" +
				"LEFT JOIN TB_NS_SLOT_TYPE ST ON S.NS_SLOT_TYPE_IDX = ST.NS_SLOT_TYPE_IDX AND ST.USE_YN = 'Y' \n" +
				"WHERE 1=1 \n" +
				(SLOG_STDT!=null && SLOG_STDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_STDT + ", '%Y-%m-%d'), ' 00:00:00') <= L.INST_DT \n"  : "") + //시작일
				(SLOG_ENDT!=null && SLOG_ENDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_ENDT + ", '%Y-%m-%d'), ' 23:59:59') > L.INST_DT \n"  : "") + //종료일
				(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND " + SearchType + " LIKE '%" + SearchData + "%'"  : "") + //검색
				("S".equals(USER_PERM) && USER_IDX > 0 ? "AND S.USER_IDX = '"+USER_IDX+"' \n" : "") + //셀러일때
				("G".equals(USER_PERM) && USER_IDX > 0 ? "AND (S.USER_IDX = '"+USER_IDX+"' OR M.INST_ADMN = '"+USER_IDX+"') \n" : "") + //총판일때
				"ORDER BY L.LOG_SLOT_IDX DESC LIMIT ?, 10";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setInt(1, Page * 10);

			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					NaverShoppingLogSlot ls = new NaverShoppingLogSlot();
					ls.setLOG_SLOT_IDX(rs.getInt("LOG_SLOT_IDX"));
					ls.setUSER_IDX(rs.getInt("USER_IDX"));
					ls.setNS_SLOT_TYPE_IDX(rs.getInt("NS_SLOT_TYPE_IDX"));
					ls.setTYPE_NAME(rs.getString("TYPE_NAME"));
					ls.setUSER_ID(rs.getString("USER_ID"));
					ls.setINST_ACTN(rs.getString("INST_ACTN"));
					ls.setSLOT_DAYS(rs.getInt("SLOT_DAYS"));
					ls.setSLOT_EA(rs.getInt("SLOT_EA"));
					ls.setSLOT_IDX(rs.getInt("SLOT_IDX"));
					ls.setPROD_GID(rs.getString("PROD_GID"));
					ls.setPROD_MID(rs.getString("PROD_MID"));
					ls.setPROD_KYWD(rs.getString("PROD_KYWD"));
					ls.setINST_USER_IDX(rs.getInt("INST_USER_IDX"));
					ls.setINST_USER_ID(rs.getString("INST_USER_ID"));
					ls.setINST_DT(rs.getString("INST_DT"));
					ls.setINST_IP(rs.getString("INST_IP"));
					result.add(ls);
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static List<NaverShoppingLogSlot> getNaverShoppingLogSlotExcelList(String SearchType, String SearchData, String SLOG_STDT, String SLOG_ENDT, String USER_PERM, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		JSONArray result = new JSONArray();
//		List<NaverShoppingLogSlot> result = new ArrayList<>();

		if(SearchType!=null && SearchType.length() > 0){
			if("USER_ID".equals(SearchType)){
				SearchType = "M." + SearchType;
			}else if("INST_ACTN".equals(SearchType)){
				SearchType = "L." + SearchType;
			}else if("PROD_GID".equals(SearchType) || "PROD_MID".equals(SearchType) || "PROD_KYWD".equals(SearchType)){
				SearchType = "S." + SearchType;
			}
		}

		String sql = "SELECT \n" +
				"L.LOG_SLOT_IDX, \n" +
				"M.USER_IDX, \n" +
				"S.NS_SLOT_TYPE_IDX,\n" +
				"ST.TYPE_NAME,\n" +
				"M.USER_ID, \n" +
				"L.INST_ACTN, \n" +
				"L.SLOT_DAYS, \n" +
				"L.SLOT_EA, \n" +
				"L.SLOT_IDX, \n" +
				"S.PROD_GID, \n" +
				"S.PROD_MID, \n" +
				"S.PROD_KYWD, \n" +
				"M_IN.USER_IDX AS INST_USER_IDX, \n" +
				"M_IN.USER_ID AS INST_USER_ID, \n" +
				"DATE_FORMAT(L.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT, \n" +
				"L.INST_IP \n" +
				"FROM TB_NS_LOG_SLOT L \n" +
				"LEFT JOIN TB_NS_SLOT S ON L.SLOT_IDX = S.SLOT_IDX \n" +
				"LEFT JOIN TB_MEMBER M ON S.USER_IDX = M.USER_IDX \n" +
				"LEFT JOIN TB_MEMBER M_IN ON L.INST_USER = M_IN.USER_IDX \n" +
				"LEFT JOIN TB_NS_SLOT_TYPE ST ON S.NS_SLOT_TYPE_IDX = ST.NS_SLOT_TYPE_IDX AND ST.USE_YN = 'Y' \n" +
				"WHERE 1=1 \n" +
				(SLOG_STDT!=null && SLOG_STDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_STDT + ", '%Y-%m-%d'), ' 00:00:00') <= L.INST_DT \n"  : "") + //시작일
				(SLOG_ENDT!=null && SLOG_ENDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_ENDT + ", '%Y-%m-%d'), ' 23:59:59') > L.INST_DT \n"  : "") + //종료일
				(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND " + SearchType + " LIKE '%" + SearchData + "%'"  : "") + //검색
				("S".equals(USER_PERM) && USER_IDX > 0 ? "AND S.USER_IDX = '"+USER_IDX+"' \n" : "") + //셀러일때
				("G".equals(USER_PERM) && USER_IDX > 0 ? "AND (S.USER_IDX = '"+USER_IDX+"' OR M.INST_ADMN = '"+USER_IDX+"') \n" : "") + //총판일때
				"ORDER BY L.LOG_SLOT_IDX DESC";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					JSONObject data = new JSONObject();
					data.put("LOG_SLOT_IDX",rs.getInt("LOG_SLOT_IDX"));
					data.put("USER_IDX",rs.getInt("USER_IDX"));
					data.put("NS_SLOT_TYPE_IDX",rs.getInt("NS_SLOT_TYPE_IDX"));
					data.put("TYPE_NAME",rs.getString("TYPE_NAME"));
					data.put("USER_ID",rs.getString("USER_ID"));
					data.put("INST_ACTN",rs.getString("INST_ACTN"));
					data.put("SLOT_DAYS",rs.getInt("SLOT_DAYS"));
					data.put("SLOT_EA",rs.getInt("SLOT_EA"));
					data.put("SLOT_IDX",rs.getInt("SLOT_IDX"));
					data.put("PROD_GID",rs.getString("PROD_GID"));
					data.put("PROD_MID",rs.getString("PROD_MID"));
					data.put("PROD_KYWD",rs.getString("PROD_KYWD"));
					data.put("INST_USER_IDX",rs.getInt("INST_USER_IDX"));
					data.put("INST_USER_ID",rs.getString("INST_USER_ID"));
					data.put("INST_DT",rs.getString("INST_DT"));
					data.put("INST_IP",rs.getString("INST_IP"));
					result.add(data);

//					NaverShoppingLogSlot ls = new NaverShoppingLogSlot();
//					ls.setLOG_SLOT_IDX(rs.getInt("LOG_SLOT_IDX"));
//					ls.setUSER_IDX(rs.getInt("USER_IDX"));
//					ls.setUSER_ID(rs.getString("USER_ID"));
//					ls.setINST_ACTN(rs.getString("INST_ACTN"));
//					ls.setSLOT_DAYS(rs.getInt("SLOT_DAYS"));
//					ls.setSLOT_EA(rs.getInt("SLOT_EA"));
//					ls.setSLOT_IDX(rs.getInt("SLOT_IDX"));
//					ls.setPROD_GID(rs.getString("PROD_GID"));
//					ls.setPROD_MID(rs.getString("PROD_MID"));
//					ls.setPROD_KYWD(rs.getString("PROD_KYWD"));
//					ls.setINST_USER_IDX(rs.getInt("INST_USER_IDX"));
//					ls.setINST_USER_ID(rs.getString("INST_USER_ID"));
//					ls.setINST_DT(rs.getString("INST_DT"));
//					ls.setINST_IP(rs.getString("INST_IP"));
//					result.add(ls);
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	////////////////////////////////////
	//// 			TB_NP_SLOT 		////
	////////////////////////////////////

	public static int getNaverPlaceSlotListTotalCount(String SearchType, String SearchData, String USER_PERM, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		int result = 0;
		try {
			Class.forName(JDBC_DRIVER);

			if(SearchType!=null && SearchType.length() > 0){
				if("USER_ID".equals(SearchType)){
					SearchType = "M." + SearchType;
				}else{
					SearchType = "S." + SearchType;
				}
			}

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(
					"SELECT COUNT(*) AS CNT FROM TB_NP_SLOT S \n" +
							"JOIN TB_MEMBER M ON S.USER_IDX = M.USER_IDX AND M.USE_YN = 'Y' \n" +
							"LEFT JOIN TB_NP_SLOT_TYPE ST ON S.NP_SLOT_TYPE_IDX = ST.NP_SLOT_TYPE_IDX AND ST.USE_YN = 'Y' \n" +
							"WHERE S.USE_YN = 'Y'\n" +
							"AND CONCAT(DATE_FORMAT(S.SLOT_ENDT, '%Y-%m-%d'), ' 23:59:59') > CURDATE()\n" +
//							"AND CONCAT(DATE_FORMAT(S.SLOT_STDT, '%Y-%m-%d'), ' 00:00:00') <= CURDATE()\n" +
							(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND " + SearchType + " LIKE '%" + SearchData + "%'"  : "") + //검색
							("S".equals(USER_PERM) && USER_IDX > 0 ? "AND S.USER_IDX = '"+USER_IDX+"' \n" : "") + //셀러일때
							("G".equals(USER_PERM) && USER_IDX > 0 ? "AND (S.USER_IDX = '"+USER_IDX+"' OR M.INST_ADMN = '"+USER_IDX+"') \n" : "") + //총판일때
							" ;", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					result = rs.getInt("CNT");
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static List<NaverPlaceSlot> getNaverPlaceSlotList(int SLOT_IDX, int Page, String SearchType, String SearchData, String USER_PERM, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		List<NaverPlaceSlot> result = new ArrayList<>();

		if(SearchType!=null && SearchType.length() > 0){
			if("USER_ID".equals(SearchType)){
				SearchType = "M." + SearchType;
			}else{
				SearchType = "S." + SearchType;
			}
		}

		String sql = "SELECT\n" +
				"S.SLOT_IDX,\n" +
				"S.USER_IDX,\n" +
				"S.NP_SLOT_TYPE_IDX,\n" +
				"ST.TYPE_NAME,\n" +
				"M.USER_ID,\n" +
				"S.PLCE_NAME,\n" +
				"S.PLCE_CODE,\n" +
				"S.PLCE_KYWD,\n" +
				"S.PLCE_URL,\n" +
				"S.SLOT_STDT,\n" +
				"S.SLOT_ENDT,\n" +
				"S.SLOT_STAT,\n" +
				"S.USE_YN,\n" +
				"S.INST_USER AS INST_USER_IDX,\n" +
				"M_IN.USER_ID AS INST_USER,\n" +
				"DATE_FORMAT(S.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT,\n" +
				"S.UPDT_USER AS UPDT_USER_IDX,\n" +
				"M_UP.USER_ID AS UPDT_USER,\n" +
				"DATE_FORMAT(S.UPDT_DT, '%Y.%m.%d (%H:%i)') AS UPDT_DT\n" +
				"FROM TB_NP_SLOT S\n" +
				"JOIN TB_MEMBER M ON S.USER_IDX = M.USER_IDX AND M.USE_YN = 'Y'\n" +
				"JOIN TB_MEMBER M_IN ON S.INST_USER = M_IN.USER_IDX\n" +
				"JOIN TB_MEMBER M_UP ON S.UPDT_USER = M_UP.USER_IDX\n" +
				"LEFT JOIN TB_NP_SLOT_TYPE ST ON S.NP_SLOT_TYPE_IDX = ST.NP_SLOT_TYPE_IDX AND ST.USE_YN = 'Y' \n" +
				"WHERE S.USE_YN = 'Y'\n" +
				"AND CONCAT(DATE_FORMAT(S.SLOT_ENDT, '%Y-%m-%d'), ' 23:59:59') > CURDATE()\n" +
//				"AND CONCAT(DATE_FORMAT(S.SLOT_STDT, '%Y-%m-%d'), ' 00:00:00') <= CURDATE()\n" +
				(SLOT_IDX > 0 ? "AND S.SLOT_IDX = '"+SLOT_IDX+"' \n" : "") + //한개 구할때
				(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND " + SearchType + " LIKE '%" + SearchData + "%'"  : "") + //검색
				("S".equals(USER_PERM) && USER_IDX > 0 ? "AND S.USER_IDX = '"+USER_IDX+"' \n" : "") + //셀러일때
				("G".equals(USER_PERM) && USER_IDX > 0 ? "AND (S.USER_IDX = '"+USER_IDX+"' OR M.INST_ADMN = '"+USER_IDX+"') \n" : "") + //총판일때
				"ORDER BY S.SLOT_IDX DESC LIMIT ?, 10";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setInt(1, Page * 10);

			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					NaverPlaceSlot s = new NaverPlaceSlot();
					s.setSLOT_IDX(rs.getInt("SLOT_IDX"));
					s.setUSER_IDX(rs.getInt("USER_IDX"));
					s.setNP_SLOT_TYPE_IDX(rs.getInt("NP_SLOT_TYPE_IDX"));
					s.setTYPE_NAME(rs.getString("TYPE_NAME"));
					s.setUSER_ID(rs.getString("USER_ID"));
					s.setPLCE_NAME(rs.getString("PLCE_NAME"));
					s.setPLCE_CODE(rs.getString("PLCE_CODE"));
					s.setPLCE_KYWD(rs.getString("PLCE_KYWD"));
					s.setPLCE_URL(rs.getString("PLCE_URL"));
					s.setSLOT_STDT(rs.getString("SLOT_STDT"));
					s.setSLOT_ENDT(rs.getString("SLOT_ENDT"));
					s.setSLOT_STAT(rs.getString("SLOT_STAT"));
					s.setUSE_YN(rs.getString("USE_YN"));
					s.setINST_USER_IDX(rs.getInt("INST_USER_IDX"));
					s.setINST_USER(rs.getString("INST_USER"));
					s.setINST_DT(rs.getString("INST_DT"));
					s.setUPDT_USER_IDX(rs.getInt("UPDT_USER_IDX"));
					s.setUPDT_USER(rs.getString("UPDT_USER"));
					s.setUPDT_DT(rs.getString("UPDT_DT"));
					result.add(s);
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static int InsertNaverPlaceSlotInfo(int USER_IDX, String SLOT_STDT, String SLOT_ENDT, int INST_USER, int SLOT_TYPE) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO TB_NP_SLOT(USER_IDX, NP_SLOT_TYPE_IDX, SLOT_STDT, SLOT_ENDT, INST_USER, INST_DT, UPDT_USER, UPDT_DT) \n" +
				"VALUES(?, ?, ?, ?, ?, NOW(), ?, NOW())";
		int SlotIDX = 0;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.setInt(1, USER_IDX);
			psmt.setInt(2, SLOT_TYPE);
			psmt.setString(3, SLOT_STDT);
			psmt.setString(4, SLOT_ENDT);
			psmt.setInt(5, INST_USER);
			psmt.setInt(6, INST_USER);
			psmt.execute();

			rs = psmt.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
			if (rs.next()) {
				SlotIDX = rs.getInt(1); // 키값 초기화
			}

			rs.close();
			psmt.close();
			conn.close();
			return SlotIDX;
		} catch (Exception e) {
			e.printStackTrace();
			SlotIDX = -1;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return SlotIDX;
	}

	public static int InsertNaverPlaceSlotInfoByExcel(int USER_IDX, String SLOT_STDT, String SLOT_ENDT, int INST_USER, int SLOT_TYPE, String PLCE_NAME, String PLCE_CODE, String PLCE_KYWD, String PLCE_URL) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "INSERT INTO TB_NP_SLOT(USER_IDX, NP_SLOT_TYPE_IDX, SLOT_STDT, SLOT_ENDT, INST_USER, INST_DT, UPDT_USER, UPDT_DT, PLCE_NAME, PLCE_CODE, PLCE_KYWD, PLCE_URL) \n" +
				"VALUES(?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?)";
		int SlotIDX = 0;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			psmt.setInt(1, USER_IDX);
			psmt.setInt(2, SLOT_TYPE);
			psmt.setString(3, SLOT_STDT);
			psmt.setString(4, SLOT_ENDT);
			psmt.setInt(5, INST_USER);
			psmt.setInt(6, INST_USER);
			psmt.setString(7, PLCE_NAME);
			psmt.setString(8, PLCE_CODE);
			psmt.setString(9, PLCE_KYWD);
			psmt.setString(10, PLCE_URL);
			psmt.execute();

			rs = psmt.getGeneratedKeys(); // 쿼리 실행 후 생성된 키 값 반환
			if (rs.next()) {
				SlotIDX = rs.getInt(1); // 키값 초기화
			}

			rs.close();
			psmt.close();
			conn.close();
			return SlotIDX;
		} catch (Exception e) {
			e.printStackTrace();
			SlotIDX = -1;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return SlotIDX;
	}

	public static boolean UpdateNaverPlaceSlotInfo(int SLOT_IDX, String PLCE_NAME, String PLCE_CODE, String PLCE_KYWD,
												   String PLCE_URL, String SLOT_STAT, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "";

		sql = "UPDATE TB_NP_SLOT SET PLCE_NAME = ?, PLCE_CODE = ?, PLCE_KYWD = ?, PLCE_URL = ?, SLOT_STAT = ?, UPDT_USER = ?, UPDT_DT = NOW() \n" +
				"WHERE SLOT_IDX = ?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, PLCE_NAME);
			psmt.setString(2, PLCE_CODE);
			psmt.setString(3, PLCE_KYWD);
			psmt.setString(4, PLCE_URL);
			psmt.setString(5, SLOT_STAT);
			psmt.setInt(6, USER_IDX);
			psmt.setInt(7, SLOT_IDX);
			psmt.execute();

			psmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean UpdateNaverPlaceSlotInfo(int SLOT_IDX, String Key, String Value, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "UPDATE TB_NP_SLOT SET " + Key + " = ?, UPDT_USER = ?, UPDT_DT = NOW() \n" +
				"WHERE SLOT_IDX = ?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, Value);
			psmt.setInt(2, USER_IDX);
			psmt.setInt(3, SLOT_IDX);
			psmt.execute();

			psmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean InsertNaverPlaceSlotLogInfo(int SLOT_IDX, int SLOT_DAYS, int SLOT_EA, String INST_ACTN, String INST_IP, int INST_USER) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		String sql = "INSERT INTO TB_NP_LOG_SLOT(SLOT_IDX, SLOT_DAYS, SLOT_EA, INST_ACTN, INST_IP, INST_USER, INST_DT)" +
				" VALUES(?, ?, ?, ?, ?, ?, NOW())";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			Logger.Debug(TAG, "MySQL Connection");
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setInt(1, SLOT_IDX);
			psmt.setInt(2, SLOT_DAYS);
			psmt.setInt(3, SLOT_EA);
			psmt.setString(4, INST_ACTN);
			psmt.setString(5, INST_IP);
			psmt.setInt(6, INST_USER);
			psmt.execute();

			psmt.close();
			conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	////////////////////////////////////
	//// 		TB_NP_LOG_SLOT 		////
	////////////////////////////////////

	public static int getNaverPlaceLogSlotListTotalCount(String SearchType, String SearchData, String SLOG_STDT, String SLOG_ENDT, String USER_PERM, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		int result = 0;
		try {
			Class.forName(JDBC_DRIVER);

			if(SearchType!=null && SearchType.length() > 0){
				if("USER_ID".equals(SearchType)){
					SearchType = "M." + SearchType;
				}else if("INST_ACTN".equals(SearchType)){
					SearchType = "L." + SearchType;
				}else if("PLCE_NAME".equals(SearchType) || "PLCE_CODE".equals(SearchType) || "PLCE_KYWD".equals(SearchType)){
					SearchType = "S." + SearchType;
				}
			}

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(
					"SELECT COUNT(*) AS CNT \n" +
							"FROM TB_NP_LOG_SLOT L \n" +
							"LEFT JOIN TB_NP_SLOT S ON L.SLOT_IDX = S.SLOT_IDX \n" +
							"LEFT JOIN TB_MEMBER M ON S.USER_IDX = M.USER_IDX \n" +
							"LEFT JOIN TB_MEMBER M_IN ON L.INST_USER = M_IN.USER_IDX \n" +
							"LEFT JOIN TB_NP_SLOT_TYPE ST ON S.NP_SLOT_TYPE_IDX = ST.NP_SLOT_TYPE_IDX AND ST.USE_YN = 'Y' \n" +
							"WHERE 1=1 \n" +
							(SLOG_STDT!=null && SLOG_STDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_STDT + ", '%Y-%m-%d'), ' 00:00:00') <= L.INST_DT \n"  : "") + //시작일
							(SLOG_ENDT!=null && SLOG_ENDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_ENDT + ", '%Y-%m-%d'), ' 23:59:59') > L.INST_DT \n"  : "") + //종료일
							(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND " + SearchType + " LIKE '%" + SearchData + "%'"  : "") + //검색
							("S".equals(USER_PERM) && USER_IDX > 0 ? "AND S.USER_IDX = '"+USER_IDX+"' \n" : "") + //셀러일때
							("G".equals(USER_PERM) && USER_IDX > 0 ? "AND (S.USER_IDX = '"+USER_IDX+"' OR M.INST_ADMN = '"+USER_IDX+"') \n" : "") + //총판일때
							";", ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					result = rs.getInt("CNT");
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static List<NaverPlaceLogSlot> getNaverPlaceLogSlotList(int Page, String SearchType, String SearchData, String SLOG_STDT, String SLOG_ENDT, String USER_PERM, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		List<NaverPlaceLogSlot> result = new ArrayList<>();

		if(SearchType!=null && SearchType.length() > 0){
			if("USER_ID".equals(SearchType)){
				SearchType = "M." + SearchType;
			}else if("INST_ACTN".equals(SearchType)){
				SearchType = "L." + SearchType;
			}else if("PLCE_NAME".equals(SearchType) || "PLCE_CODE".equals(SearchType) || "PLCE_KYWD".equals(SearchType)){
				SearchType = "S." + SearchType;
			}
		}

		String sql = "SELECT \n" +
				"L.LOG_SLOT_IDX, \n" +
				"M.USER_IDX, \n" +
				"S.NP_SLOT_TYPE_IDX,\n" +
				"ST.TYPE_NAME,\n" +
				"M.USER_ID, \n" +
				"L.INST_ACTN, \n" +
				"L.SLOT_DAYS, \n" +
				"L.SLOT_EA, \n" +
				"L.SLOT_IDX, \n" +
				"S.PLCE_NAME, \n" +
				"S.PLCE_CODE, \n" +
				"S.PLCE_KYWD, \n" +
				"M_IN.USER_IDX AS INST_USER_IDX, \n" +
				"M_IN.USER_ID AS INST_USER_ID, \n" +
				"DATE_FORMAT(L.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT, \n" +
				"L.INST_IP \n" +
				"FROM TB_NP_LOG_SLOT L \n" +
				"LEFT JOIN TB_NP_SLOT S ON L.SLOT_IDX = S.SLOT_IDX \n" +
				"LEFT JOIN TB_MEMBER M ON S.USER_IDX = M.USER_IDX \n" +
				"LEFT JOIN TB_MEMBER M_IN ON L.INST_USER = M_IN.USER_IDX \n" +
				"LEFT JOIN TB_NP_SLOT_TYPE ST ON S.NP_SLOT_TYPE_IDX = ST.NP_SLOT_TYPE_IDX AND ST.USE_YN = 'Y' \n" +
				"WHERE 1=1 \n" +
				(SLOG_STDT!=null && SLOG_STDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_STDT + ", '%Y-%m-%d'), ' 00:00:00') <= L.INST_DT \n"  : "") + //시작일
				(SLOG_ENDT!=null && SLOG_ENDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_ENDT + ", '%Y-%m-%d'), ' 23:59:59') > L.INST_DT \n"  : "") + //종료일
				(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND " + SearchType + " LIKE '%" + SearchData + "%'"  : "") + //검색
				("S".equals(USER_PERM) && USER_IDX > 0 ? "AND S.USER_IDX = '"+USER_IDX+"' \n" : "") + //셀러일때
				("G".equals(USER_PERM) && USER_IDX > 0 ? "AND (S.USER_IDX = '"+USER_IDX+"' OR M.INST_ADMN = '"+USER_IDX+"') \n" : "") + //총판일때
				"ORDER BY L.LOG_SLOT_IDX DESC LIMIT ?, 10";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setInt(1, Page * 10);

			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					NaverPlaceLogSlot ls = new NaverPlaceLogSlot();
					ls.setLOG_SLOT_IDX(rs.getInt("LOG_SLOT_IDX"));
					ls.setUSER_IDX(rs.getInt("USER_IDX"));
					ls.setNP_SLOT_TYPE_IDX(rs.getInt("NP_SLOT_TYPE_IDX"));
					ls.setTYPE_NAME(rs.getString("TYPE_NAME"));
					ls.setUSER_ID(rs.getString("USER_ID"));
					ls.setINST_ACTN(rs.getString("INST_ACTN"));
					ls.setSLOT_DAYS(rs.getInt("SLOT_DAYS"));
					ls.setSLOT_EA(rs.getInt("SLOT_EA"));
					ls.setSLOT_IDX(rs.getInt("SLOT_IDX"));
					ls.setPLCE_NAME(rs.getString("PLCE_NAME"));
					ls.setPLCE_CODE(rs.getString("PLCE_CODE"));
					ls.setPLCE_KYWD(rs.getString("PLCE_KYWD"));
					ls.setINST_USER_IDX(rs.getInt("INST_USER_IDX"));
					ls.setINST_USER_ID(rs.getString("INST_USER_ID"));
					ls.setINST_DT(rs.getString("INST_DT"));
					ls.setINST_IP(rs.getString("INST_IP"));
					result.add(ls);
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static List<NaverPlaceLogSlot> getNaverPlaceLogSlotExcelList(String SearchType, String SearchData, String SLOG_STDT, String SLOG_ENDT, String USER_PERM, int USER_IDX) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		JSONArray result = new JSONArray();
//		List<NaverPlaceLogSlot> result = new ArrayList<>();

		if(SearchType!=null && SearchType.length() > 0){
			if("USER_ID".equals(SearchType)){
				SearchType = "M." + SearchType;
			}else if("INST_ACTN".equals(SearchType)){
				SearchType = "L." + SearchType;
			}else if("PLCE_NAME".equals(SearchType) || "PLCE_CODE".equals(SearchType) || "PLCE_KYWD".equals(SearchType)){
				SearchType = "S." + SearchType;
			}
		}

		String sql = "SELECT \n" +
				"L.LOG_SLOT_IDX, \n" +
				"M.USER_IDX, \n" +
				"S.NP_SLOT_TYPE_IDX,\n" +
				"ST.TYPE_NAME,\n" +
				"M.USER_ID, \n" +
				"L.INST_ACTN, \n" +
				"L.SLOT_DAYS, \n" +
				"L.SLOT_EA, \n" +
				"L.SLOT_IDX, \n" +
				"S.PLCE_NAME, \n" +
				"S.PLCE_CODE, \n" +
				"S.PLCE_KYWD, \n" +
				"M_IN.USER_IDX AS INST_USER_IDX, \n" +
				"M_IN.USER_ID AS INST_USER_ID, \n" +
				"DATE_FORMAT(L.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT, \n" +
				"L.INST_IP \n" +
				"FROM TB_NP_LOG_SLOT L \n" +
				"LEFT JOIN TB_NP_SLOT S ON L.SLOT_IDX = S.SLOT_IDX \n" +
				"LEFT JOIN TB_MEMBER M ON S.USER_IDX = M.USER_IDX \n" +
				"LEFT JOIN TB_MEMBER M_IN ON L.INST_USER = M_IN.USER_IDX \n" +
				"LEFT JOIN TB_NP_SLOT_TYPE ST ON S.NP_SLOT_TYPE_IDX = ST.NP_SLOT_TYPE_IDX AND ST.USE_YN = 'Y' \n" +
				"WHERE 1=1 \n" +
				(SLOG_STDT!=null && SLOG_STDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_STDT + ", '%Y-%m-%d'), ' 00:00:00') <= L.INST_DT \n"  : "") + //시작일
				(SLOG_ENDT!=null && SLOG_ENDT.length() > 0 ? " AND CONCAT(DATE_FORMAT(" + SLOG_ENDT + ", '%Y-%m-%d'), ' 23:59:59') > L.INST_DT \n"  : "") + //종료일
				(SearchType!=null && SearchType.length() > 0 && SearchData!=null && SearchData.length() > 0 ? " AND " + SearchType + " LIKE '%" + SearchData + "%'"  : "") + //검색
				("S".equals(USER_PERM) && USER_IDX > 0 ? "AND S.USER_IDX = '"+USER_IDX+"' \n" : "") + //셀러일때
				("G".equals(USER_PERM) && USER_IDX > 0 ? "AND (S.USER_IDX = '"+USER_IDX+"' OR M.INST_ADMN = '"+USER_IDX+"') \n" : "") + //총판일때
				"ORDER BY L.LOG_SLOT_IDX DESC";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			Logger.Debug(TAG, "QUERY: "+psmt);
			ResultSet rs = psmt.executeQuery();

			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					JSONObject data = new JSONObject();
					data.put("LOG_SLOT_IDX",rs.getInt("LOG_SLOT_IDX"));
					data.put("USER_IDX",rs.getInt("USER_IDX"));
					data.put("NP_SLOT_TYPE_IDX",rs.getInt("NP_SLOT_TYPE_IDX"));
					data.put("TYPE_NAME",rs.getString("TYPE_NAME"));
					data.put("USER_ID",rs.getString("USER_ID"));
					data.put("INST_ACTN",rs.getString("INST_ACTN"));
					data.put("SLOT_DAYS",rs.getInt("SLOT_DAYS"));
					data.put("SLOT_EA",rs.getInt("SLOT_EA"));
					data.put("SLOT_IDX",rs.getInt("SLOT_IDX"));
					data.put("PLCE_NAME",rs.getString("PLCE_NAME"));
					data.put("PLCE_CODE",rs.getString("PLCE_CODE"));
					data.put("PLCE_KYWD",rs.getString("PLCE_KYWD"));
					data.put("INST_USER_IDX",rs.getInt("INST_USER_IDX"));
					data.put("INST_USER_ID",rs.getString("INST_USER_ID"));
					data.put("INST_DT",rs.getString("INST_DT"));
					data.put("INST_IP",rs.getString("INST_IP"));
					result.add(data);

//					NaverPlaceLogSlot ls = new NaverPlaceLogSlot();
//					ls.setLOG_SLOT_IDX(rs.getInt("LOG_SLOT_IDX"));
//					ls.setUSER_IDX(rs.getInt("USER_IDX"));
//					ls.setUSER_ID(rs.getString("USER_ID"));
//					ls.setINST_ACTN(rs.getString("INST_ACTN"));
//					ls.setSLOT_DAYS(rs.getInt("SLOT_DAYS"));
//					ls.setSLOT_EA(rs.getInt("SLOT_EA"));
//					ls.setSLOT_IDX(rs.getInt("SLOT_IDX"));
//					ls.setPLCE_NAME(rs.getString("PLCE_NAME"));
//					ls.setPLCE_CODE(rs.getString("PLCE_CODE"));
//					ls.setPLCE_KYWD(rs.getString("PLCE_KYWD"));
//					ls.setINST_USER_IDX(rs.getInt("INST_USER_IDX"));
//					ls.setINST_USER_ID(rs.getString("INST_USER_ID"));
//					ls.setINST_DT(rs.getString("INST_DT"));
//					ls.setINST_IP(rs.getString("INST_IP"));
//					result.add(ls);
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}


	/*슬롯타입*/
	public static List<NaverShoppingSlotType> getNaverShoppingSlotType(String USE_YN) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		List<NaverShoppingSlotType> r = new ArrayList<>();
		String sql = "SELECT\n" +
				"ST.NS_SLOT_TYPE_IDX,\n" +
				"ST.TYPE_NAME,\n" +
				"ST.USE_YN,\n" +
				"ST.INST_USER AS INST_USER_IDX,\n" +
				"M_IN.USER_ID AS INST_USER,\n" +
				"DATE_FORMAT(ST.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT,\n" +
				"ST.UPDT_USER AS UPDT_USER_IDX,\n" +
				"M_UP.USER_ID AS UPDT_USER,\n" +
				"DATE_FORMAT(ST.UPDT_DT, '%Y.%m.%d (%H:%i)') AS UPDT_DT\n" +
				"FROM TB_NS_SLOT_TYPE ST\n" +
				"JOIN TB_MEMBER M_IN ON ST.INST_USER = M_IN.USER_IDX\n" +
				"LEFT JOIN TB_MEMBER M_UP ON ST.UPDT_USER = M_UP.USER_IDX\n" +
				"WHERE ST.USE_YN = ?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, USE_YN);
			ResultSet rs = psmt.executeQuery();
			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					NaverShoppingSlotType result = new NaverShoppingSlotType();
					result.setNS_SLOT_TYPE_IDX(rs.getInt("NS_SLOT_TYPE_IDX"));
					result.setTYPE_NAME(rs.getString("TYPE_NAME"));
					result.setUSE_YN(rs.getString("USE_YN"));
					result.setINST_USER_IDX(rs.getInt("INST_USER_IDX"));
					result.setINST_USER(rs.getString("INST_USER"));
					result.setINST_DT(rs.getString("INST_DT"));
					result.setUPDT_USER_IDX(rs.getInt("UPDT_USER_IDX"));
					result.setUPDT_USER(rs.getString("UPDT_USER"));
					result.setUPDT_DT(rs.getString("UPDT_DT"));
					r.add(result);
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

	public static List<NaverPlaceSlotType> getNaverPlaceSlotType(String USE_YN) {
		Logger.LogOn(false);
		Connection conn = null;
		PreparedStatement psmt = null;
		List<NaverPlaceSlotType> r = new ArrayList<>();
		String sql = "SELECT\n" +
				"ST.NP_SLOT_TYPE_IDX,\n" +
				"ST.TYPE_NAME,\n" +
				"ST.USE_YN,\n" +
				"ST.INST_USER AS INST_USER_IDX,\n" +
				"M_IN.USER_ID AS INST_USER,\n" +
				"DATE_FORMAT(ST.INST_DT, '%Y.%m.%d (%H:%i)') AS INST_DT,\n" +
				"ST.UPDT_USER AS UPDT_USER_IDX,\n" +
				"M_UP.USER_ID AS UPDT_USER,\n" +
				"DATE_FORMAT(ST.UPDT_DT, '%Y.%m.%d (%H:%i)') AS UPDT_DT\n" +
				"FROM TB_NP_SLOT_TYPE ST\n" +
				"JOIN TB_MEMBER M_IN ON ST.INST_USER = M_IN.USER_IDX\n" +
				"LEFT JOIN TB_MEMBER M_UP ON ST.UPDT_USER = M_UP.USER_IDX\n" +
				"WHERE ST.USE_YN = ?";

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			psmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			psmt.setString(1, USE_YN);
			ResultSet rs = psmt.executeQuery();
			rs.last();
			if (rs.getRow() > 0) {
				rs.beforeFirst();
				while (rs.next()) {
					NaverPlaceSlotType result = new NaverPlaceSlotType();
					result.setNP_SLOT_TYPE_IDX(rs.getInt("NP_SLOT_TYPE_IDX"));
					result.setTYPE_NAME(rs.getString("TYPE_NAME"));
					result.setUSE_YN(rs.getString("USE_YN"));
					result.setINST_USER_IDX(rs.getInt("INST_USER_IDX"));
					result.setINST_USER(rs.getString("INST_USER"));
					result.setINST_DT(rs.getString("INST_DT"));
					result.setUPDT_USER_IDX(rs.getInt("UPDT_USER_IDX"));
					result.setUPDT_USER(rs.getString("UPDT_USER"));
					result.setUPDT_DT(rs.getString("UPDT_DT"));
					r.add(result);
				}
			}
			rs.close();
			psmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (psmt != null)
					psmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return r;
	}

}