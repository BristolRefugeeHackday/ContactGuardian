package com.elroid.contactguardian.util;

/**
 * Class: com.elroid.contactguardian.util.GenUtils
 * Project: Contact Guardian
 * Created Date: 25/06/2016 14:51
 *
 * @author <a href="mailto:elliot@intohand.com">Elliot Long</a>
 *         Copyright (c) 2014 Intohand Ltd. All rights reserved.
 */

public class GenUtils
{
	private static transient Logger logger = Logger.getLogger("GenUtils");

	public static boolean isSame(CharSequence a, CharSequence b){
		return (a == null && b == null) || (a != null && b != null && a.toString().equals(b.toString()));
	}

	public static boolean isBlank(CharSequence a){
		if(a == null) return true;
		String trimmed = a.toString().trim();
		return trimmed.equals("") || trimmed.equals("null");
	}
}
