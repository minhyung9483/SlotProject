package com.slot.Common;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;

import com.slot.Model.Member;
import com.slot.Model.NaverPlaceSlotType;
import com.slot.Model.NaverShoppingSlotType;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class Util {
	private static int curYear, curMonth, curDay, curHour, curMinute, curSecond;
	private static Calendar mCalendar;
	private static Member member;
	private static String TAG = "UTIL";

	static public String getLocalMacAddr() 
	{
		String result = "";
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface netif = NetworkInterface.getByInetAddress(ip);
			if(netif!=null){
				byte[] mac = netif.getHardwareAddress();
				for(byte b : mac){
					result += String.format(":%02x", b);
				}
				result = result.substring(1);
			}else{
				result = "NetworkInterface is Nothing.";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return result;
	}

	static public String getTimeFormat(Timestamp tmp, String Format)
	{
		// Format ex) "yyyy-MM-dd HH:mm:ss"
		return  new SimpleDateFormat(Format).format(new java.sql.Date(tmp.getTime())) + "";
	}
	
	static public String getCurrentMillisec() {
		mCalendar = Calendar.getInstance();
		curYear = mCalendar.get(Calendar.YEAR);  
		curMonth = mCalendar.get(Calendar.MONTH)+1;  
		curDay = mCalendar.get(Calendar.DAY_OF_MONTH);    
		curHour = mCalendar.get(Calendar.HOUR_OF_DAY);  
		curMinute = mCalendar.get(Calendar.MINUTE);  
		curSecond = mCalendar.get(Calendar.SECOND);  
		String s_date = Integer.toString(curYear) +
				Integer.toString(curMonth) +
				Integer.toString(curDay) +
				Integer.toString(curHour) +
				Integer.toString(curMinute) +
				Integer.toString(curSecond) +
				Integer.toString(mCalendar.get(Calendar.MILLISECOND));
		
		return s_date;
	}

	static public String getDateNowMillisec() {
		mCalendar = Calendar.getInstance();
		curYear = mCalendar.get(Calendar.YEAR);  
		curMonth = mCalendar.get(Calendar.MONTH)+1;  
		curDay = mCalendar.get(Calendar.DAY_OF_MONTH);    
		curHour = mCalendar.get(Calendar.HOUR_OF_DAY);  
		curMinute = mCalendar.get(Calendar.MINUTE);  
		curSecond = mCalendar.get(Calendar.SECOND);  
		String s_date = curYear + "-" + 
				curMonth + "-" + 
				curDay + " " + 
				curHour + ":" + 
				curMinute + ":" + 
				curSecond +"." +
				mCalendar.get(Calendar.MILLISECOND);

		return s_date;
	}
	
	
	static public String getDateNow() {
		mCalendar = Calendar.getInstance();
		curYear = mCalendar.get(Calendar.YEAR);  
		curMonth = mCalendar.get(Calendar.MONTH)+1;  
		curDay = mCalendar.get(Calendar.DAY_OF_MONTH);    
		curHour = mCalendar.get(Calendar.HOUR_OF_DAY);  
		curMinute = mCalendar.get(Calendar.MINUTE);  
		curSecond = mCalendar.get(Calendar.SECOND);  
		String s_date = curYear + "-" + 
				curMonth + "-" + 
				curDay + " " + 
				curHour + ":" + 
				curMinute + ":" + 
				curSecond;

		return s_date;
	}
	
	static public String getDateNowDate() {
		mCalendar = Calendar.getInstance();
		curMonth = mCalendar.get(Calendar.MONTH)+1;  
		curDay = mCalendar.get(Calendar.DAY_OF_MONTH);    
		curHour = mCalendar.get(Calendar.HOUR_OF_DAY);  
		curMinute = mCalendar.get(Calendar.MINUTE);  
		
		String s_date =	curMonth + "-" + 
				curDay + " " + 
				curHour + ":" + 
				curMinute;

		return s_date;
	}
	
	static public String getDateNowNum() {
		mCalendar = Calendar.getInstance();
		curYear = mCalendar.get(Calendar.YEAR);  
		curMonth = mCalendar.get(Calendar.MONTH)+1;  
		curDay = mCalendar.get(Calendar.DAY_OF_MONTH);    
		curHour = mCalendar.get(Calendar.HOUR_OF_DAY);  
		curMinute = mCalendar.get(Calendar.MINUTE);  
		curSecond = mCalendar.get(Calendar.SECOND);  

		String s_date = curYear+"-"+
				curMonth+"-"+
				curDay+"-"+
				curHour+
				curMinute+
				curSecond+"."+
				mCalendar.get(Calendar.MILLISECOND);

		return s_date;
	}

	public static boolean DirectoryCreate(String DirName)
	{
		boolean result = false;
		File desFile = new File(DirName);
		if(!desFile.isDirectory()) result = desFile.mkdirs(); 
		return result;
	}

	public static String TextHash(String Text) throws NoSuchAlgorithmException, FileNotFoundException, IOException
	{
		MessageDigest md = MessageDigest.getInstance("sha-512");
		String result = getDigest(new ByteArrayInputStream(Text.getBytes("UTF-8")), md, 2048);

		return result;
	}
	
	public static String TextHash(String Text, String Algorithm) throws NoSuchAlgorithmException, FileNotFoundException, IOException
	{
		MessageDigest md = MessageDigest.getInstance(Algorithm);
		String result = getDigest(new ByteArrayInputStream(Text.getBytes("UTF-8")), md, 2048);

		return result;
	}
	

	public static String TextHashMD5(String Text) throws NoSuchAlgorithmException, FileNotFoundException, IOException
	{
		MessageDigest md = MessageDigest.getInstance("md5");
		String result = getDigest(new ByteArrayInputStream(Text.getBytes("UTF-8")), md, 2048);

		return result;
	}
	
	public static String FileHash(String FilePath) 
	{
		MessageDigest md;
		String result = null;
		try {
			md = MessageDigest.getInstance("sha-256");
			result = getDigest(new FileInputStream(FilePath), md, 2048);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private static String getDigest(InputStream is, MessageDigest md, int byteArraySize)
			throws NoSuchAlgorithmException, IOException {

		md.reset();
		byte[] bytes = new byte[byteArraySize];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {
			md.update(bytes, 0, numBytes);
		}
		byte[] digest = md.digest();
		String result = new String(Hex.encodeHex(digest));
		return result;
	}

	public static String Encrypt(String text, String key) throws Exception
	{
		byte[] keyBytes= new byte[16];
		byte[] b= key.getBytes("UTF-8");
		int len= b.length;
		if (len > keyBytes.length) len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE,keySpec,ivSpec);
		
		byte[] results = cipher.doFinal(text.getBytes("UTF-8"));
		return new String(Base64.encodeBase64(results));
	}
	
	public static String Decrypt(String text, String key) throws Exception
	{
		byte[] keyBytes= new byte[16];
		byte[] b= key.getBytes("UTF-8");
		int len= b.length;
		if (len > keyBytes.length) len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);

		byte [] results = cipher.doFinal(Base64.decodeBase64(text.getBytes("UTF-8")));
		return new String(results,"UTF-8");
	}
	
	public static byte[] hexStringToByteArray(String str)
	{
		try {
			int len = str.length();
			byte[] data = new byte[len / 2];
			for(int i=0; i< len; i+=2) {
				data[i / 2] = (byte) (( Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i+1), 16));
			}
			return data;
		}catch(Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public static String byteArrayToHexString(byte[] bytes) {
		try {
			StringBuilder sb = new StringBuilder();
			for(byte b : bytes) {
				sb.append(String.format("%02x", b&0xff));
			}
		}catch(Exception e) { e.printStackTrace(); }
		return null;
	}
	
	public static String FileList ="";
	private static void FilePathList(String strDirPath) { 
		File path = new File(strDirPath); 
		File[] fList = path.listFiles(); 
		for( int i = 0; i < fList.length; i++) { 
			if( fList[i].isFile() ) { 
				FileList += fList[i].getPath()+"\r\n";
				System.out.println( fList[i].getPath() ); 
			} else if( fList[i].isDirectory()) { 
				FilePathList( fList[i].getPath());  
				
			}
		}
	}
	
	public static void execute(String Query) {
		 String s;
        Process p;
        try {
        	//이 변수에 명령어를 넣어주면 된다. 
            String[] cmd = {"/bin/sh","-c", Query};
            p = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println(s);
            p.waitFor();
            System.out.println("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {
        }
    }

	public static boolean checkWorkingTime(){
		boolean result = true;
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		mCalendar = Calendar.getInstance();
		curHour = mCalendar.get(Calendar.HOUR_OF_DAY);
		curMinute = mCalendar. get(Calendar.MINUTE);



		String curTime = (Integer.toString(curHour).length()==1? "0" + curHour : curHour)  + "" +	(Integer.toString(curMinute).length()==1? "0" + curMinute : curMinute);
		String curStatus = "근무시간";
		if(Integer.parseInt(curTime) >= 1700){
			result = false;
			curStatus = "근무시간 지남";
		}else if(Integer.parseInt(curTime) < 600){
			result = false;
			curStatus = "근무시간 전";
		}
		if(result){
			Logger.LogOn(false);
		}else{
			Logger.LogOn(true);
		}
		Logger.Info(TAG, "==========================");
		Logger.Info(TAG, "현재시간 : " + curTime);
		Logger.Info(TAG, "현재상태 : " + curStatus);
		Logger.Info(TAG, "워킹타임 : " + result);
		Logger.Info(TAG, "요청자 : " + member.getUSER_ID());
		Logger.Info(TAG, "요청IP : " + member.getUSER_IP());
		Logger.Info(TAG, "==========================");

		return result;
	}

	public static boolean checkShutDownTime(String USER_TYPE, int SLOT_TYPE){
		boolean result = true;
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		mCalendar = Calendar.getInstance();
		curHour = mCalendar.get(Calendar.HOUR_OF_DAY);
		curMinute = mCalendar. get(Calendar.MINUTE);

		if(USER_TYPE.contains("NS")){
			NaverShoppingSlotType naverShoppingSlotType = DBConnector.getNaverShoppingSlotType(SLOT_TYPE,"Y").get(0);

			String curTime = (Integer.toString(curHour).length()==1? "0" + curHour : curHour)  + "" +	(Integer.toString(curMinute).length()==1? "0" + curMinute : curMinute);
			String curStatus = "근무시간";
			if(Integer.parseInt(curTime) >= Integer.parseInt(naverShoppingSlotType.getSLOT_ENTM().toString())){
				result = false;
				curStatus = "근무시간 지남";
			}else if(Integer.parseInt(curTime) < Integer.parseInt(naverShoppingSlotType.getSLOT_STTM().toString())){
				result = false;
				curStatus = "근무시간 전";
			}
			if(result){
				Logger.LogOn(false);
			}else{
				Logger.LogOn(true);
			}
			Logger.Info(TAG, "==========================");
			Logger.Info(TAG, "현재시간 : " + curTime);
			Logger.Info(TAG, "시작시간 : " + Integer.parseInt(naverShoppingSlotType.getSLOT_STTM().toString()));
			Logger.Info(TAG, "종료시간 : " + Integer.parseInt(naverShoppingSlotType.getSLOT_ENTM().toString()));
			Logger.Info(TAG, "현재상태 : " + curStatus);
			Logger.Info(TAG, "워킹타임 : " + result);
			Logger.Info(TAG, "요청자 : " + member.getUSER_ID());
			Logger.Info(TAG, "요청IP : " + member.getUSER_IP());
			Logger.Info(TAG, "==========================");
		}else if(USER_TYPE.contains("NP")){
			NaverPlaceSlotType naverPlaceSlotType = DBConnector.getNaverPlaceSlotType(SLOT_TYPE,"Y").get(0);

			String curTime = (Integer.toString(curHour).length()==1? "0" + curHour : curHour)  + "" +	(Integer.toString(curMinute).length()==1? "0" + curMinute : curMinute);
			String curStatus = "근무시간";
			if(Integer.parseInt(curTime) >= Integer.parseInt(naverPlaceSlotType.getSLOT_ENTM().toString())){
				result = false;
				curStatus = "근무시간 지남";
			}else if(Integer.parseInt(curTime) < Integer.parseInt(naverPlaceSlotType.getSLOT_STTM().toString())){
				result = false;
				curStatus = "근무시간 전";
			}
			if(result){
				Logger.LogOn(false);
			}else{
				Logger.LogOn(true);
			}
			Logger.Info(TAG, "==========================");
			Logger.Info(TAG, "현재시간 : " + curTime);
			Logger.Info(TAG, "시작시간 : " + Integer.parseInt(naverPlaceSlotType.getSLOT_STTM().toString()));
			Logger.Info(TAG, "종료시간 : " + Integer.parseInt(naverPlaceSlotType.getSLOT_ENTM().toString()));
			Logger.Info(TAG, "현재상태 : " + curStatus);
			Logger.Info(TAG, "워킹타임 : " + result);
			Logger.Info(TAG, "요청자 : " + member.getUSER_ID());
			Logger.Info(TAG, "요청IP : " + member.getUSER_IP());
			Logger.Info(TAG, "==========================");
		}

		return result;
	}

	public static boolean checkShutDownTimeByName(String USER_TYPE, String SLOT_TYPE){
		boolean result = true;
		member = (Member)ContextUtil.getAttrFromSession(Protocol.Json.KEY_MEMBER);

		mCalendar = Calendar.getInstance();
		curHour = mCalendar.get(Calendar.HOUR_OF_DAY);
		curMinute = mCalendar. get(Calendar.MINUTE);

		if(USER_TYPE.contains("NS")){
			NaverShoppingSlotType naverShoppingSlotType = DBConnector.getNaverShoppingSlotTypeByName(SLOT_TYPE,"Y").get(0);

			String curTime = (Integer.toString(curHour).length()==1? "0" + curHour : curHour)  + "" +	(Integer.toString(curMinute).length()==1? "0" + curMinute : curMinute);
			String curStatus = "근무시간";
			if(Integer.parseInt(curTime) >= Integer.parseInt(naverShoppingSlotType.getSLOT_ENTM().toString())){
				result = false;
				curStatus = "근무시간 지남";
			}else if(Integer.parseInt(curTime) < Integer.parseInt(naverShoppingSlotType.getSLOT_STTM().toString())){
				result = false;
				curStatus = "근무시간 전";
			}
			if(result){
				Logger.LogOn(false);
			}else{
				Logger.LogOn(true);
			}
			Logger.Info(TAG, "==========================");
			Logger.Info(TAG, "현재시간 : " + curTime);
			Logger.Info(TAG, "시작시간 : " + Integer.parseInt(naverShoppingSlotType.getSLOT_STTM().toString()));
			Logger.Info(TAG, "종료시간 : " + Integer.parseInt(naverShoppingSlotType.getSLOT_ENTM().toString()));
			Logger.Info(TAG, "현재상태 : " + curStatus);
			Logger.Info(TAG, "워킹타임 : " + result);
			Logger.Info(TAG, "요청자 : " + member.getUSER_ID());
			Logger.Info(TAG, "요청IP : " + member.getUSER_IP());
			Logger.Info(TAG, "==========================");
		}else if(USER_TYPE.contains("NP")){
			NaverPlaceSlotType naverPlaceSlotType = DBConnector.getNaverPlaceSlotTypeByName(SLOT_TYPE,"Y").get(0);

			String curTime = (Integer.toString(curHour).length()==1? "0" + curHour : curHour)  + "" +	(Integer.toString(curMinute).length()==1? "0" + curMinute : curMinute);
			String curStatus = "근무시간";
			if(Integer.parseInt(curTime) >= Integer.parseInt(naverPlaceSlotType.getSLOT_ENTM().toString())){
				result = false;
				curStatus = "근무시간 지남";
			}else if(Integer.parseInt(curTime) < Integer.parseInt(naverPlaceSlotType.getSLOT_STTM().toString())){
				result = false;
				curStatus = "근무시간 전";
			}
			if(result){
				Logger.LogOn(false);
			}else{
				Logger.LogOn(true);
			}
			Logger.Info(TAG, "==========================");
			Logger.Info(TAG, "현재시간 : " + curTime);
			Logger.Info(TAG, "시작시간 : " + Integer.parseInt(naverPlaceSlotType.getSLOT_STTM().toString()));
			Logger.Info(TAG, "종료시간 : " + Integer.parseInt(naverPlaceSlotType.getSLOT_ENTM().toString()));
			Logger.Info(TAG, "현재상태 : " + curStatus);
			Logger.Info(TAG, "워킹타임 : " + result);
			Logger.Info(TAG, "요청자 : " + member.getUSER_ID());
			Logger.Info(TAG, "요청IP : " + member.getUSER_IP());
			Logger.Info(TAG, "==========================");
		}

		return result;
	}

	public static String encodeKorean(String url){
		// StringBuilder를 사용하여 결과를 만듭니다.
		StringBuilder result = new StringBuilder();
		try {
			// 정규식을 사용하여 한글 부분을 찾습니다.
			Pattern pattern = Pattern.compile("[가-힣]+");
			Matcher matcher = pattern.matcher(url);


			int lastEnd = 0;

			while (matcher.find()) {
				// 한글 부분 전까지의 문자열을 추가합니다.
				result.append(url, lastEnd, matcher.start());
				// 한글 부분을 인코딩하여 추가합니다.
				result.append(URLEncoder.encode(matcher.group(), StandardCharsets.UTF_8.toString()));
				lastEnd = matcher.end();
			}

			// 마지막 한글 이후의 문자열을 추가합니다.
			result.append(url.substring(lastEnd));

			// 결과 출력
//			System.out.println("Original URL: " + url);
//			System.out.println("Encoded URL: " + result.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public static int getDaysBetween(String start, String end) {
		if(start==null || "".equals(start) || end==null || "".equals(end))return 0;
		// 문자열을 LocalDate로 변환
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(start, formatter);
		LocalDate endDate = LocalDate.parse(end, formatter);

		// 두 날짜 사이의 일수 계산
		long daysBetween = ChronoUnit.DAYS.between(startDate, endDate)+1;

		// 정수로 반환
		return (int) daysBetween;
	}
	public static void init(HttpServletResponse response) {
		response.setContentType("text/html; charset=euc-kr");
		response.setCharacterEncoding("euc-kr");
	}
	public static void alert(HttpServletResponse response, String alertText) throws IOException {
		init(response);
		PrintWriter out = response.getWriter();
		out.println("<script>alert('" + alertText + "');</script> ");
		out.flush();
	}
	public static void movePage(HttpServletResponse response, String nextPage) throws IOException {
		init(response);
		PrintWriter out = response.getWriter();
		out.println("<script>location.href='" + nextPage + "';</script> ");
		out.flush();
	}
	public static void alertAndMovePage(HttpServletResponse response, String alertText, String nextPage) throws IOException {
		init(response);
		PrintWriter out = response.getWriter();
		out.println("<script>alert('" + alertText + "'); location.href='" + nextPage + "';</script> ");
		out.flush();
	}
	public static void alertAndBackPage(HttpServletResponse response, String alertText) throws IOException {
		init(response);
		PrintWriter out = response.getWriter();
		out.println("<script>alert('" + alertText + "'); history.go(-1);</script>");
		out.flush();
	}
}
