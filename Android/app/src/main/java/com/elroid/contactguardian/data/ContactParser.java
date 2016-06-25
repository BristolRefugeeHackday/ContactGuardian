package com.elroid.contactguardian.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.elroid.contactguardian.util.GenUtils;
import com.elroid.contactguardian.util.LabelValue;
import com.elroid.contactguardian.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: com.elroid.contactguardian.data.ContactParser
 * Project: Contact Guardian
 * Created Date: 25/06/2016 14:35
 *
 * @author <a href="mailto:elliot@intohand.com">Elliot Long</a>
 *         Copyright (c) 2014 Intohand Ltd. All rights reserved.
 */

public class ContactParser
{
	private static transient Logger logger = Logger.getLogger("ContactParser");

	List<LabelValue> emails = new ArrayList<>();
	List<LabelValue> phoneNumbers = new ArrayList<>();
	String name = null;
	String sortName;
	String address;
	String photoPath;

	final String CONTACT_ID = ContactsContract.Contacts._ID;
	final String DISPLAY_NAME_ALTERNATIVE = "sort_key_alt";//"display_name";//

	String data1;
	String mime;
	int type;
	String typeName;
	int data1Idx;    // Index of DATA1 column
	int mimeIdx;    // Index of MIMETYPE column
	int nameIdx;    // Index of DISPLAY_NAME column

	Context ctx;

	public void parse(Cursor cursor){
		if(logger.isDebug()) logger.debug("parsing: "+print(cursor));
		int i=0;
		while (cursor.moveToNext()) {
			// Get the indexes of the MIME type and data
			mimeIdx = cursor.getColumnIndex(
					ContactsContract.Contacts.Data.MIMETYPE);
			data1Idx = cursor.getColumnIndex(
					ContactsContract.Contacts.Data.DATA1);
			int typeIdx = cursor.getColumnIndex(
					ContactsContract.CommonDataKinds.Phone.TYPE);

			print(cursor);

			// Match the data to the MIME type, store in variables
			type = -1;
			if(typeIdx > -1){
				type = cursor.getInt(typeIdx);
			}

			mime = cursor.getString(mimeIdx);
			data1 = cursor.getString(data1Idx);

			if(logger.isDebug())
				logger.info("mime[" + (i++) + "]: name(" + type + ") mime(" + mime + ") data1(" + data1 + ")");
			if (ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE.equalsIgnoreCase(mime)) {
				typeName = getTypeName(data1, cursor, false);
				if(logger.isDebug()) logger.debug("typeName: "+typeName);
				if(!GenUtils.isBlank(data1))
					emails.add(new LabelValue(data1+" ("+typeName+")", data1));
				//emails.addUnique(data1);
			}
			if (ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE.equalsIgnoreCase(mime)) {
				String num = cursor.getString(data1Idx);
				typeName = getTypeName(data1, cursor, true);
				phoneNumbers.add(new LabelValue(data1+" ("+typeName+")", data1));
			}
			if (ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE.equalsIgnoreCase(mime)) {

				int data2Idx = cursor.getColumnIndex(DISPLAY_NAME_ALTERNATIVE);

				String name = cursor.getString(data2Idx);
				if(!GenUtils.isBlank(name)){
					if(!GenUtils.isBlank(sortName) && !sortName.equals(name)){
						logger.warn("found different sort name: new("+name+") existing("+sortName+")");
					}
					if(logger.isInfo()) logger.info("found last, first name: "+name);
					sortName = name;
				}
			}
		}
	}

	public List<LabelValue> getEmails(){
		return emails;
	}

	public List<LabelValue> getPhoneNumbers(){
		return phoneNumbers;
	}

	public String getName(){
		return name;
	}

	private String getTypeName(String number, Cursor c, boolean isPhone){
		String typeIDLabel = ContactsContract.Contacts.Data.DATA2;
		String typeName = ContactsContract.Contacts.Data.DATA3;
		String tmp = null;
		int index = c.getColumnIndex(typeName);
		if(index > -1)
			tmp = c.getString(index);
		if(GenUtils.isSame(tmp, number)){
			tmp = null;//number is same as type!!
		}
		if(GenUtils.isBlank(tmp)){
			index = c.getColumnIndex(typeIDLabel);
			if(index > -1) {
				int type = c.getInt(index);
				return isPhone ? getPhoneTypeName(type) : getEmailTypeName(type);
			}
			return null;
		}
		else
			return tmp;
	}

	private String getPhoneTypeName(int type){

		switch(type){
			case ContactsContract.CommonDataKinds.Phone.TYPE_HOME: return "Home";
			case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE: return "Mobile";
			case ContactsContract.CommonDataKinds.Phone.TYPE_WORK: return "Work";
			case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE: return "Work Mobile";
			case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER: return "Other";
			case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN: return "Main";
			default: return "Unknown ("+type+")";
		}
	}
	private String getEmailTypeName(int type){

		switch(type){
			case ContactsContract.CommonDataKinds.Email.TYPE_HOME: return "Home";
			case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE: return "Mobile";
			case ContactsContract.CommonDataKinds.Email.TYPE_WORK: return "Work";
			case ContactsContract.CommonDataKinds.Email.TYPE_OTHER: return "Other";
			default: return "Unknown ("+type+")";
		}
	}

	public static String print(Cursor c){
		int lim = c.getColumnCount();
		StringBuilder result = new StringBuilder("CursorRow");
		for(int i=0; i<lim; i++){
			try {
				if(i > 0)
					result.append("\n");
				String tmp;
				if(c.getType(i) == Cursor.FIELD_TYPE_BLOB)
					tmp = "[Blob]";
				else {
					tmp = c.getString(i);
				}
				if(tmp != null)
					result.append("item(").append(i).append("): ").append(tmp)
							.append(" (").append(c.getColumnName(i)).append(")");
			} catch (Exception e) {
				logger.warn("Error accessing index " + i + ": " + e.getMessage(), e);
			}
		}
		return result.toString();
	}
}
