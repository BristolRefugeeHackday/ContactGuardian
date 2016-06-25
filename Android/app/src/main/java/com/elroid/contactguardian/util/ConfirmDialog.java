package com.elroid.contactguardian.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.Spanned;
import android.text.SpannedString;
import android.view.View;
import android.widget.Button;

/**
 * Class: com.elroid.contactguardian.util.ConfirmDialog
 * Project: Contact Guardian
 * Created Date: 25/06/2016 15:01
 *
 * @author <a href="mailto:elliot@intohand.com">Elliot Long</a>
 *         Copyright (c) 2014 Intohand Ltd. All rights reserved.
 */

public class ConfirmDialog extends DialogFragment
{
	private static Logger logger = Logger.getLogger("ConfirmDialog");

	protected Spanned title;
	@DrawableRes
	protected int titleIcon;
	protected Spanned message;

	protected Action yes;
	protected Action no;
	protected Action maybe;

	boolean buttonsShown = true;


	public static class Action
	{
		public String text;
		public Runnable action;
		public boolean runInBackground;
		public boolean autoDismiss = true;

		public Action(String text){
			this.text = text;
		}

		public Action(String text, Runnable action){
			this(text, action, false);
		}

		public Action(String text, Runnable action, boolean runInBackground){
			this.text = text;
			this.action = action;
			this.runInBackground = runInBackground;
		}

		public Action(String text, Runnable action, boolean runInBackground, boolean autoDismiss){
			this.text = text;
			this.action = action;
			this.runInBackground = runInBackground;
			this.autoDismiss = autoDismiss;
		}

		@Override
		public String toString(){

			final StringBuilder sb = new StringBuilder("ButtonStruct{");
			sb.append("text='").append(text).append('\'');
			sb.append(", action=").append(action);
			sb.append(", runInBackground=").append(runInBackground);
			sb.append('}');
			return sb.toString();
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title);
		builder.setIcon(titleIcon);

		builder.setMessage(message);
		if(buttonsShown) {

			if(yes != null)
				builder.setPositiveButton(yes.text, createOnClick(yes));

			if (maybe != null)
				builder.setNeutralButton(maybe.text, createOnClick(maybe));

			if (no != null)
				builder.setNegativeButton(no.text, createOnClick(no));
		}
		// Create the AlertDialog object and return it
		final AlertDialog dialog =  builder.create();
		fixActionDismiss(yes, AlertDialog.BUTTON_POSITIVE, dialog);
		fixActionDismiss(no, AlertDialog.BUTTON_NEGATIVE, dialog);
		fixActionDismiss(maybe, AlertDialog.BUTTON_NEUTRAL, dialog);
		dialog.setCancelable(isCancelable());
		return dialog;
	}

	private void fixActionDismiss(final Action action, final int buttonType, final AlertDialog dialog){
		try {
			if(action == null) return;
			if(!action.autoDismiss){
				//fix for auto-dismiss http://stackoverflow.com/questions/2620444/how-to-prevent-a-dialog-from-closing-when-a-button-is-clicked
				dialog.setOnShowListener(new DialogInterface.OnShowListener(){
					@Override
					public void onShow(DialogInterface d){
						Button b = dialog.getButton(buttonType);
						b.setOnClickListener(new View.OnClickListener(){
							@Override
							public void onClick(View view){
								run(action);
							}
						});
					}
				});
			}
		}
		catch (Exception e) {
			logger.warn(e);
		}
	}

	private DialogInterface.OnClickListener createOnClick(final Action bs){
		return new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int id) {
				run(bs);
			}
		};
	}

	private void run(final Action bs){
		if(bs.action == null){
			logger.warn("no action to run: "+bs);
			dismiss();
			return;
		}
		if(bs.runInBackground){
			new Thread(bs.action).start();
		}
		else{
			bs.action.run();
		}
		if(bs.autoDismiss)
			dismiss();
	}

	/*@Override
	public void dismiss(){
		if(logger.isDebug())
			Printer.printStack("dismiss() called");
		super.dismiss();
	}*/

	public ConfirmDialog setTitle(Spanned title) {
		this.title = title;
		return this;
	}
	public ConfirmDialog setTitle(String title) {
		this.title = new SpannedString(title);
		return this;
	}

	public ConfirmDialog setTitleIcon(@DrawableRes int resID) {
		this.titleIcon = resID;
		return this;
	}

	public ConfirmDialog setMessage(Spanned msg) {
		this.message = msg;
		return this;
	}
	public ConfirmDialog setMessage(String msg) {
		this.message = new SpannedString(msg);
		return this;
	}
	public ConfirmDialog setYesAction(String text){
		return setYesAction(text, null);
	}


	/**
	 * Use {@code setYesAction(Action bs)} instead.
	 */
	public ConfirmDialog setYesAction(String text, Runnable action){
		yes = new Action(text, action, false);
		return this;
	}
	public ConfirmDialog setYesAction(Action bs){
		yes = bs;
		return this;
	}
	/**
	 * @deprecated Use {@code setYesAction(Action bs)} instead.
	 */
	public ConfirmDialog setYesAutoDismiss(boolean autoDismiss){
		if(yes == null) throw new RuntimeException("You need to set the yes action before you set auto dismiss");
		this.yes.autoDismiss = autoDismiss;
		return this;
	}


	/**
	 * Use {@code setMaybeAction(Action bs)} instead.
	 */
	public ConfirmDialog setMaybeAction(String text, Runnable action){
		maybe = new Action(text, action);
		return this;
	}
	public ConfirmDialog setMaybeAction(Action bs){
		maybe = bs;
		return this;
	}

	public ConfirmDialog setNoAction(String text){
		no = new Action(text);
		return this;
	}

	/**
	 * Use {@code setNoAction(Action bs)} instead.
	 */
	public ConfirmDialog setNoAction(String text, Runnable action){
		no = new Action(text, action);
		return this;
	}
	public ConfirmDialog setNoAction(Action bs){
		no = bs;
		return this;
	}

	private String dialogTag = "ConfirmDialog";

	public String getDialogTag(){
		return dialogTag;
	}

	public void setDialogTag(String dialogTag){
		this.dialogTag = dialogTag;
	}

	public void show(FragmentManager fm){
		FragmentTransaction ft = fm.beginTransaction();
		//ft.commitAllowingStateLoss();
		Fragment prev = fm.findFragmentByTag(getDialogTag());
		if(prev != null){
			ft.remove(prev);
		}
		//ft.addToBackStack(null);
		ft.commitAllowingStateLoss();

		// Create and show the dialog.
		show(fm, getDialogTag());
	}

	/**
	 * With the old architecture, sometimes this method was overridden.
	 * This is here to flag that at compile time.
	 * @return
	 */
	@Nullable
	@Override
	public final View getView() {
		return super.getView();
	}
}
