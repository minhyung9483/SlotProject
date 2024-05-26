package com.slot.Common;

public class Logger {
	
	private static boolean onoff;
	
	public static void LogOn(boolean OnOff) { onoff = OnOff; }
	public static void Info(String TAG, String Message){ if(onoff) System.out.println("[INFO "+Util.getDateNow()+"] "+TAG+" ▶▶\t "+Message); }
	public static void Debug(String TAG, String Message){ if(onoff) System.out.println("[DEBUG "+Util.getDateNow()+"] "+TAG+" ▶▶\t "+Message); }
	public static void Error(String TAG, String Message){ if(onoff) System.out.println("[ERROR "+Util.getDateNow()+"] "+TAG+" ▶▶\t "+Message); }
}
