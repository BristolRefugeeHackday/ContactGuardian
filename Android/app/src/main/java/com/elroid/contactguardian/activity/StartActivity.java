package com.elroid.contactguardian.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.elroid.contactguardian.R;
import com.elroid.contactguardian.data.ContactParser;
import com.elroid.contactguardian.util.ConfirmDialog;

import java.util.List;

public class StartActivity extends AppCompatActivity
{

	private static final int PICK_CONTACT = 667;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		Button chooseContactButton = (Button)findViewById(R.id.chooseContactButton);
		chooseContactButton.setOnClickListener(new View.OnClickListener(){
		    public void onClick(View view) {
		        chooseContact();
		    }
		});
	}

	private void chooseContact(){
		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
		startActivityForResult(intent, PICK_CONTACT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == Activity.RESULT_OK) {
			Uri contactData = data.getData();

			String name = "None";
			String phone;
			boolean hasPhone = false;
			Cursor c =  managedQuery(contactData, null, null, null, null);
			if (c.moveToFirst()) {
				name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
				hasPhone = c.getInt(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)) == 1;
				//phone = c.getString(c.getColumnIndexOrThrow())
			}



			/*ContactParser cp = new ContactParser();
			cp.parse(c);*/

			ConfirmDialog d = new ConfirmDialog();
			d.setTitle("Contact");
			d.setMessage("Name: "+name+" has phone number: "+hasPhone/*+"\nPhone:"+phone*/);
			d.setYesAction("Ok");
			d.show(getFragmentManager());
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private static String print(List list) {
		String result = "List(";
		if (list == null) return result + "null)";
		result += list.size() + "):";
		for (int i = 0; i < list.size(); i++) {
			Object o = list.get(i);
			result += "\n[" + i + "]=" + o;
		}
		return result;
	}
}
