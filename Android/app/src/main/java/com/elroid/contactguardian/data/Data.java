package com.elroid.contactguardian.data;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.elroid.contactguardian.util.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class: com.elroid.contactguardian.data.Data
 * Project: Contact Guardian
 * Created Date: 25/06/2016 14:01
 *
 * @author <a href="mailto:elliot@intohand.com">Elliot Long</a>
 *         Copyright (c) 2014 Intohand Ltd. All rights reserved.
 */

public class Data
{
	static private Logger logger = Logger.getLogger("Data", Logger.DEBUG);//, Logger.TRACE);

	Context ctx;

	public Data(Context ctx) {
		this.ctx = ctx;
	}

	public Context getCtx() {
		return ctx;
	}



	boolean waitForCommit = false;
	public void startTransaction(){
		waitForCommit = true;
	}

	public void commitTransaction(){
		getEditor().commit();
		waitForCommit = false;
	}

	private void commit(){
		if(!waitForCommit)
			commitTransaction();
	}

	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	private SharedPreferences getPrefs(){
		if(prefs == null)
			prefs = PreferenceManager.getDefaultSharedPreferences(getCtx());
		return prefs;
	}
	private SharedPreferences.Editor getEditor(){
		if(editor == null)
			editor = getPrefs().edit();
		return editor;
	}

	public String getString(String key, String defValue) {
		SharedPreferences prefs = getPrefs();
		//if(logger.isDebug()) logger.trace("getString(" + key + "): " + prefs.getString(key, ""));
		return prefs.getString(key, defValue);
	}


	public void set(String key, CharSequence value) {
		if(logger.isDebug()) logger.trace("set(" + key + ",<String>" + value + ")");
		if(value == null){
			if(exists(key))
				delete(key);
		}
		else{
			SharedPreferences.Editor prefs = getEditor();
			prefs.putString(key, value.toString());
			commit();
		}
		if(logger.isDebug()) logger.trace("getString: " + getString(key, ""));
		if(logger.isDebug()) logger.trace("exists: " + exists(key));
	}


	public void delete(String key) {
		long start = System.currentTimeMillis();
		if(logger.isDebug()) logger.trace("delete(" + key + ")");
		SharedPreferences.Editor prefs = getEditor();
		prefs.remove(key);
		commit();
		if(logger.isDebug()) logger.trace("delete(" + key + ") finished, elapsed: "+(System.currentTimeMillis()-start)+"ms");
	}

	public boolean exists(String key) {
		SharedPreferences prefs = getPrefs();
		return prefs.contains(key);
	}


	public void set(String key, boolean value) {
		if(logger.isDebug()) logger.debug("set(" + key + ",<boolean>" + value + ")");
		SharedPreferences.Editor prefs = getEditor();
		prefs.putBoolean(key, value);
		commit();
		if(logger.isDebug()) logger.trace("getbool: " + is(key, null));
		if(logger.isDebug()) logger.trace("exists: " + exists(key));
	}

	/**
	 * @deprecated use getBooolean(String,Boolean)
	 * @param key
	 * @param defValue
	 * @return
	 */
	public Boolean is(String key, Boolean defValue) {
		return getBoolean(key, defValue);
	}

	public Boolean getBoolean(String key, Boolean defValue) {
		SharedPreferences prefs = getPrefs();
		if(logger.isDebug()) logger.trace("getString(" + key + "): " + prefs.getBoolean(key, false));
		if(!exists(key)) return defValue;
		return prefs.getBoolean(key, false);
	}

	/**
	 * puts long value into prefs and commits
	 * @param key
	 * @param value (saved as long)
	 */
	public void set(String key, long value){
		if(logger.isDebug()) logger.debug("set(" + key + ",<long>" + value + ")");
		SharedPreferences.Editor prefs = getEditor();
		prefs.putLong(key, value);
		commit();
		//if(logger.isDebug()) logger.trace("getLong: " + getLong(key, -1));
		//if(logger.isDebug()) logger.trace("exists: " + exists(key));
	}

	public void set(String key, int value){
		if(logger.isDebug()) logger.debug("set(" + key + ",<int>" + value + ")");
		SharedPreferences.Editor prefs = getEditor();
		prefs.putInt(key, value);
		commit();
		if(logger.isDebug()) logger.trace("getInt: " + getInt(key, -1));
		if(logger.isDebug()) logger.trace("exists: " + exists(key));
	}

	/**
	 * puts float value into prefs and commits
	 * @param key
	 * @param value (saved as float)
	 */
	public void set(String key, float value){
		if(logger.isDebug()) logger.debug("set(" + key + ",<float>" + value + ")");
		SharedPreferences.Editor prefs = getEditor();
		prefs.putFloat(key, value);
		commit();
		if(logger.isDebug()) logger.trace("exists: " + exists(key));
	}

	/**
	 *puts double value into prefs and commits
	 * @param key
	 * @param value (saved a String)
	 */
	public void set(String key, double value){
		set(key, ""+value);
	}




	public int getInt(String key, int defValue){
		if(exists(key)) {
			SharedPreferences prefs = getPrefs();
			try {
				return prefs.getInt(key, defValue);
			} catch (ClassCastException e) {
				logger.warn("getting value for '"+key+"' which is an int but is stored as a string");
				int result = Integer.parseInt(prefs.getString(key, ""+defValue));
				if(result != defValue){
					delete(key);
					set(key, result);
				}
				return result;
			}
		}
		else
			return defValue;
	}
	public long getLong(String key, long defValue){
		if(exists(key)) {
			SharedPreferences prefs = getPrefs();
			try {

				return prefs.getLong(key, defValue);
			} catch (ClassCastException eIgnored) {
				try {

					long result = prefs.getInt(key, -1);
					if(result == -1) result = defValue;
					return result;
				} catch (ClassCastException e) {
					String str = prefs.getString(key, null);
					if(str != null) {
						long l = Long.parseLong(str);
						logger.warn("long value("+l+") for key("+key+") converted from string to long");
						delete(key);
						set(key, l);
						return l;
					}
					else return defValue;
				}
			}
		}
		else
			return defValue;
	}

	public float getFloat(String key, float defValue){
		if(exists(key)) {
			//return Float.parseFloat(getString(key, ""));
			SharedPreferences prefs = getPrefs();
			return prefs.getFloat(key, defValue);
		}
		else
			return defValue;
	}
	public double getDouble(String key, double defValue){
		if(exists(key))
			return Double.parseDouble(getString(key, ""));
		else
			return defValue;
	}



	public List<String> getAllKeysWithPrefix(String prefix){
		Map map = getPrefs().getAll();
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		List<String> result = new ArrayList<String>();
		while(it.hasNext()) {
			String key = it.next();
			if(key.startsWith(prefix)){
				result.add(key);
			}
		}
		return result;
	}

	/*public String toString(){
		SharedPreferences prefs = ;
		prefs.getAll()
	}*/



	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		Map map = getPrefs().getAll();
		if(map.size() > 0)
			sb.append("Data: ").append(map);
		else
			sb.append("Data: empty");
		return sb.toString();
	}


	public void clear(){
		logger.warn("clearing all user data");
		getEditor().clear();
		getEditor().commit();
		if(logger.isDebug()) logger.debug("...cleared");
	}
}
