package com.elroid.contactguardian;

import android.app.Application;

import com.elroid.contactguardian.util.Logger;

/**
 * Class: com.elroid.contactguardian.App
 * Project: Contact Guardian
 * Created Date: 25/06/2016 15:08
 *
 * @author <a href="mailto:elliot@intohand.com">Elliot Long</a>
 *         Copyright (c) 2014 Intohand Ltd. All rights reserved.
 */

public class App extends Application
{
	private static transient Logger logger = Logger.getLogger("App");

	protected static App instance;

	public App(){
		instance = this;
	}
	public static App get(){
		return instance;
	}
}
