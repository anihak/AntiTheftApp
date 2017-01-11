package com.example.pkg.antitheftapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class SecureMessagesActivity extends Activity implements OnClickListener, OnItemClickListener
{
	final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setTheme( android.R.style.Theme_Light );
        setContentView(R.layout.main);
        
        /**
         * You can also register your intent filter here.
         * And here is example how to do this.
         *
         * IntentFilter filter = new IntentFilter( "android.provider.Telephony.SMS_RECEIVED" );
         * filter.setPriority( IntentFilter.SYSTEM_HIGH_PRIORITY );
         * registerReceiver( new com.example.pkg.incomsms.SmsReceiver(), filter );
        **/
        
        this.findViewById( R.id.UpdateList ).setOnClickListener( this );
    }

    ArrayList<String> smsList = new ArrayList<String>();
    
	public void onItemClick( AdapterView<?> parent, View view, int pos, long id ) 
	{
		try 
		{
		    	String[] splitted = smsList.get( pos ).split("\n"); 
			String sender = splitted[0];
			String encryptedData = "";
			for ( int i = 1; i < splitted.length; ++i )
			{
			    encryptedData += splitted[i];
			}
			String data = sender + "\n" + StringCryptor.decrypt( new String(SmsReceiver.PASSWORD), encryptedData );
			Toast.makeText( this, data, Toast.LENGTH_SHORT ).show();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void onClick( View v ) 
	{
		if(ContextCompat.checkSelfPermission( getBaseContext(), "android.permission.READ_SMS")== PackageManager.PERMISSION_GRANTED);
		else

		ActivityCompat.requestPermissions(SecureMessagesActivity.this,new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);



		ContentResolver contentResolver = getContentResolver();
		Cursor cursor = contentResolver.query( Uri.parse( "content://sms/inbox" ), null, null, null, null);

		int indexBody = cursor.getColumnIndex( SmsReceiver.BODY );
		int indexAddr = cursor.getColumnIndex( SmsReceiver.ADDRESS );
		
		if ( indexBody < 0 || !cursor.moveToFirst() ) return;
		
		smsList.clear();
		
		do
		{
			String str = "Sender: " + cursor.getString( indexAddr ) + "\n" + cursor.getString( indexBody );
			smsList.add( str );
		}
		while( cursor.moveToNext() );

		
		ListView smsListView = (ListView) findViewById( R.id.SMSList );
		smsListView.setAdapter( new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, smsList) );
		smsListView.setOnItemClickListener( this );
	}


}