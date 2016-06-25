package com.elroid.contactguardian.data;

import android.content.Context;

import com.elroid.contactguardian.util.Logger;

/**
 * Class: com.elroid.contactguardian.data.PrefData
 * Project: Contact Guardian
 * Created Date: 25/06/2016 13:59
 *
 * @author <a href="mailto:elliot@intohand.com">Elliot Long</a>
 *         Copyright (c) 2014 Intohand Ltd. All rights reserved.
 */

public class PrefData extends Data
{
	private static transient Logger logger = Logger.getLogger("PrefData");

	public PrefData(Context ctx){
		super(ctx);
	}
}
