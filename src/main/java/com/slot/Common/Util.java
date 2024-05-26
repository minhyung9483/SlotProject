package com.slot.Common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.slot.Model.Member;
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
}
