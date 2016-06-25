package com.elroid.contactguardian.util;

import android.util.Log;

/**
 * Class: com.elroid.contactguardian.util.Logger
 * Project: Contact Guardian
 * Created Date: 25/06/2016 14:02
 *
 * @author <a href="mailto:elliot@intohand.com">Elliot Long</a>
 *         Copyright (c) 2014 Intohand Ltd. All rights reserved.
 */

public class Logger
{
	protected String name;
	public static final int TRACE = Log.VERBOSE;
	public static final int DEBUG = Log.DEBUG;
	public static final int INFO = Log.INFO;
	public static final int WARN = Log.WARN;
	public static final int ERROR = Log.ERROR;

	protected static String prefix = "i2h";

	protected static int level = TRACE;
	private int levelOverride;

	public static void init(String tag, int _level){
		prefix = tag;

		level = _level;
		//log.tag.<YOUR_LOG_TAG>=<LEVEL>
		/*String prop = "log.tag."+tag;
		System.setProperty(prop, getName(level));
		Log.i(prefix, "Set log level: "+prop+":"+getName(level));*/
	}



	public static String getLogTag() {
		return prefix;
	}

	/**
	 *
	 * @param c class to log
	 * @param logLevel minimum level to log
	 * @param reduceIfNecessary true if the level should be adjusted down as well as up, false if just needs adjusting up
	 * @return logger
	 */
	public static Logger getLogger(Class c, int logLevel, boolean reduceIfNecessary){
		return getLogger(c.getSimpleName(), logLevel, reduceIfNecessary);
	}
	public static Logger getLogger(String tag, int logLevel, boolean reduceIfNecessary){
		String prop = "log.tag."+pre(prefix, tag);
		int levelToSet = logLevel;
		if(!reduceIfNecessary && levelToSet < level)
			levelToSet = level;
		System.setProperty(prop, getName(levelToSet));
		return new Logger(tag, levelToSet);
	}

	public static Logger getLogger(Class c){
		return new Logger(c.getSimpleName());
	}
	public static Logger getLogger(String tag){
		return new Logger(tag);
	}

	public static Logger getLogger(Class c, int logLevel){
		return getLogger(c, logLevel, false);
	}
	public static Logger getLogger(String tag, int logLevel){
		return getLogger(tag, logLevel, false);
	}

	public Logger(String name) {
		this.name = name;
	}

	public Logger(String name, int levelOverride) {
		this(name);
		this.levelOverride = levelOverride;
	}

	/*public static Logger getLogger(Class c){
		return new Logger(c.getSimpleName());
	}*/
	private boolean is(int LEVEL){
		return isFile(LEVEL) || isLogcat(LEVEL);
	}
	private boolean isFile(int LEVEL){
		return false;
	}
	private boolean isLogcat(int LEVEL){
		if(levelOverride > 0)
			return levelOverride <= LEVEL;
		else
			return level <= LEVEL;
	}
	private void logToFile(int logLevel, String tag, String str){
		//unused
	}
	private void logToFile(int logLevel, String tag, String str, Throwable t){
		//unused
	}
	public void trace(String str){
		if(isLogcat(Log.VERBOSE)) {
			Log.v(pre(), post(str));
		}
		if(isFile(Log.VERBOSE)) {
			logToFile(Log.VERBOSE, pre(), post(str));
		}
	}
	public void trace(String tag, String str){
		if(isLogcat(Log.VERBOSE)){
			Log.v(tag, post(str));
		}
		if(isFile(Log.VERBOSE)) {
			logToFile(Log.VERBOSE, tag, post(str));
		}
	}
	public void debug(String str){
		if(isLogcat(Log.DEBUG)){
			Log.d(pre(), post(str));
		}
		if(isFile(Log.DEBUG)) {
			logToFile(Log.DEBUG, pre(), post(str));
		}
	}
	public void debug(String tag, String str){
		if(isLogcat(Log.DEBUG)){
			Log.d(tag, post(str));
		}
		if(isFile(Log.DEBUG)) {
			logToFile(Log.DEBUG, tag, post(str));
		}
	}
	public void info(String str){
		if(isLogcat(Log.INFO)){
			Log.i(pre(), post(str));
		}
		if(isFile(Log.INFO)) {
			logToFile(Log.INFO, pre(), post(str));
		}
	}
	public void info(String tag, String str){
		if(isLogcat(Log.INFO)){
			Log.i(tag, post(str));
		}
		if(isFile(Log.INFO)) {
			logToFile(Log.INFO, tag, post(str));
		}
	}
	public void warn(String str){
		if(isLogcat(Log.WARN)){
			Log.w(pre(), post(str));
		}
		if(isFile(Log.WARN)) {
			logToFile(Log.WARN, pre(), post(str));
		}
	}
	public void warn(String tag, String str){
		if(isLogcat(Log.WARN)){
			Log.w(tag, post(str));
		}
		if(isFile(Log.WARN)) {
			logToFile(Log.WARN, tag, post(str));
		}
	}
	public void warn(Throwable t){
		if(isLogcat(Log.WARN)){
			Log.w(pre(), post(t), t);
		}
		if(isFile(Log.WARN)) {
			logToFile(Log.WARN, pre(), null, t);
		}
	}
	public void warn(String str, Throwable t){
		if(isLogcat(Log.WARN)){
			Log.w(pre(), post(str), t);
		}
		if(isFile(Log.WARN)) {
			logToFile(Log.WARN, pre(), post(str), t);
		}
	}
	public void warn(String tag, String str, Throwable t){
		if(isLogcat(Log.WARN)){
			Log.w(tag, post(str), t);
		}
		if(isFile(Log.WARN)) {
			logToFile(Log.WARN, tag, post(str), t);
		}
	}
	public void error(String str){
		if(isLogcat(Log.ERROR)){
			Log.e(pre(), post(str));
		}
		if(isFile(Log.ERROR)) {
			logToFile(Log.ERROR, pre(), post(str));
		}
	}
	public void error(Throwable t){
		if(isLogcat(Log.ERROR)){
			Log.e(pre(), post(t), t);
		}
		if(isFile(Log.ERROR)) {
			logToFile(Log.ERROR, pre(), null, t);
		}
	}
	public void error(String str, Throwable t){
		if(isLogcat(Log.ERROR)){
			Log.e(pre(), post(str+": "+(t == null? null : t.getMessage())), t);
		}
		if(isFile(Log.ERROR)) {
			logToFile(Log.ERROR, pre(), post(str), t);
		}
	}

	/*** Convenience methods **********************************************************************/
	public void v(String str){
		trace(str);
	}
	public void w(String str){
		warn(str);
	}
	public void i(String str){
		info(str);
	}
	public void e(String str){
		error(str);
	}
	/*** End Convenience methods ******************************************************************/

	public static String getName(int level){
		switch(level){
			case TRACE: return "VERBOSE";
			case DEBUG: return "DEBUG";
			case INFO: 	return "INFO";
			case WARN: 	return "WARN";
			case ERROR: return "ERROR";
			case -1: 	return "SUPPRESS";
			default: 	return "Unknown";
		}
	}
	public void wtf(String str){
		error("WTF?! - "+str);
		//Log.wtf(pre(name), str);
	}
	public void wtf(Throwable t){
		error("WTF?!", t);
		//Log.wtf(pre(name), t.getMessage(), t);
	}
	public void wtf(String str, Throwable t){
		error("WTF?! - "+str, t);
		//Log.wtf(pre(name), str+": "+t.getMessage(), t);
	}
	protected String pre(){
		return pre(prefix, name);
	}
	protected static String pre(String tag, String name){
		//max 23 chars!!
		//return name+" - "+prefix;
		//return TextUtils.chop(tag+"."+name, 23);
		//return new Date().toTime(true)+": "+TextUtils.chop(tag+"."+name, 23);
		return chop(tag, 23);
	}

	public static String chop(String str, int num){
		if(str == null) return null;
		else if(str.length() <= num) return str;
		else return str.substring(0, num);
	}

	protected String post(Throwable t){
		return post(t==null ? null : t.toString());
	}
	protected String post(CharSequence txt){
		return name+": "+txt;
	}

	public boolean isInfo(){
		//if(level > INFO) return false;//if(true) return true;//to do debug
		return is(INFO);
		//return Log.isLoggable(pre(name), INFO);
	}
	public boolean isDebug(){
		//if(level > DEBUG) return false;//if(true) return true;//to do debug
		return is(DEBUG);
		/*boolean isDebug = is(DEBUG);
		if(isDebug) {
			Log.i("Logger", "isDebug true for: " + this+" isInfo("+isInfo()+")");
		}
		else
			Log.d("Logger", "this is not debug: "+this);
		return isDebug;*/
	}

	public boolean isTrace(){
		//if(level > DEBUG) return false;//if(true) return true;//to do debug
		return is(TRACE);
		//return Log.isLoggable(pre(name), DEBUG);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Logger{");
		sb.append("name='").append(name).append('\'');
		sb.append(", level=").append(level);
		sb.append(", levelOverride=").append(levelOverride);
		sb.append('}');
		return sb.toString();
	}
}
