package com.elroid.contactguardian.activity;

import android.support.v7.app.AppCompatActivity;

import com.elroid.contactguardian.data.PrefData;
import com.elroid.contactguardian.util.Logger;

/**
 * Class: com.elroid.contactguardian.activity.BaseActivity
 * Project: Contact Guardian
 * Created Date: 25/06/2016 14:14
 *
 * @author <a href="mailto:elliot@intohand.com">Elliot Long</a>
 *         Copyright (c) 2014 Intohand Ltd. All rights reserved.
 */

public class BaseActivity extends AppCompatActivity
{
	private static transient Logger logger = Logger.getLogger("BaseActivity");

	PrefData d;
	protected PrefData getData(){
		if(d == null)
			d = new PrefData(this);
		return d;
	}
}
