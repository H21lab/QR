/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package testpackage.qrreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public final class QRreaderActivity extends Activity {

	private AlertDialog alert = null;
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.menu);
		findViewById(R.id.scan_qr_code).setOnClickListener(scanQRCode);
		findViewById(R.id.about).setOnClickListener(about);
		
	}

	public final Button.OnClickListener scanQRCode = new Button.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		}
	};
	
	public final Button.OnClickListener about = new Button.OnClickListener() {
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext()); 
			AlertDialog alert = builder.create();
			alert.setTitle(getString(R.string.about_menu));
			alert.setMessage(getString(R.string.about_message));
			alert.setButton("OK", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                //
	            }
	        });
			alert.show();
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				//String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

				try {
					contents = RSADecoder.decode(contents);
					showDialog(R.string.result_succeeded, "SIGNED BY OSK (RSA)\n" + contents);
				} catch (Exception e) {
					
					try {
						contents = DESDecoder.decode(contents);
						showDialog(R.string.result_succeeded, "SIGNED BY OSK (3DES)\n" + contents);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						showDialog(R.string.result_succeeded, "!!! NOT SIGNED BY OSK !!!\n" + contents);
					}
				}

				// showDialog(R.string.result_succeeded, "Format: " + format +
				// "\nContents: " + contents);
			} else if (resultCode == RESULT_CANCELED) {
				showDialog(R.string.result_failed, getString(R.string.result_failed_why));
			}
		}
	}

	private void showDialog(int title, CharSequence message) {
        
		AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
		alert.show();
	}
	
	@Override
	public void onConfigurationChanged(Configuration config) {
		if (alert != null) {
			alert.dismiss();
			
			alert.show();
		}
		
		super.onConfigurationChanged(config);
	}
}
