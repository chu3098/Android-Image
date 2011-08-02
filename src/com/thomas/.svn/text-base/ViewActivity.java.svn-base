package com.thomas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewActivity extends Activity {
	
	private ImageView iv;
	private TextView header, footer;
	private int[] ids;
	private String[] datas = null;
	private String[] imageName = null;
	private String[] imageSize = null;
	int position = 1;
	int totalOfImages = 0;
	private Bitmap bmp;
	GestureDetector detector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image);
		
		//Get parameters from ImageActivity
		Intent intent = getIntent();
		position = intent.getIntExtra("position",1);	
		totalOfImages = intent.getIntExtra("totalOfImages", 1);
		
		//Start Loading Images
    	Log.d("VIEW","Start Loading Images...");
    	
    	ContentResolver cr = getContentResolver();
    	//查詢圖檔
    	Cursor c = cr.query(Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
    	
    	ids = new int[c.getCount()];
    	imageName = new String[c.getCount()];
    	imageSize = new String[c.getCount()];
    	datas = new String[c.getCount()];
    	int index = 0;
    	
//    	c.moveToPosition(position);
//    	int id = c.getInt(c.getColumnIndex(Images.Media._ID));
//		String imageName = c.getString(c.getColumnIndex(Images.Media.DISPLAY_NAME));
//		String imageSize = c.getString(c.getColumnIndex(Images.Media.SIZE));
//		String data = c.getString(c.getColumnIndex(Images.Media.DATA));    	
    	
		while (c.moveToNext()) {
			int id = c.getInt(c.getColumnIndex(Images.Media._ID));
			String displayName = c.getString(c.getColumnIndex(Images.Media.DISPLAY_NAME));
			String displaySize = c.getString(c.getColumnIndex(Images.Media.SIZE));
			String data = c.getString(c.getColumnIndex(Images.Media.DATA));
			ids[index] = id;
			imageName[index] = displayName;
			imageSize[index] = String.valueOf((Integer.parseInt(displaySize)/1024)) + "KB.";
			datas[index] = data;
			index++;
			//Log.d("IMG", id + "\t" + displayName + "\t" +data);
		}			
		
		header = (TextView) findViewById(R.id.header);
		header.setText(ids[position]+" / "+ totalOfImages);
		
		iv = (ImageView) findViewById(R.id.image);
		bmp = BitmapFactory.decodeFile(datas[position]);
		iv.setImageBitmap(bmp);
		
		footer = (TextView) findViewById(R.id.footer);
		footer.setText("檔案名稱: "+imageName[position]+"\n"+"檔案大小: "+imageSize[position]);
		
		detector = new GestureDetector(this, new MyGestureListener());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return detector.onTouchEvent(event);
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.d("VIEW", "onStop");
		if(!bmp.isRecycled())
			bmp.recycle();
	}
	
	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch (item_id) {
		case R.id.about:
			new AlertDialog.Builder(ViewActivity.this).setTitle("關於Show Images")
					.setMessage("Created Date:2011/07/26\n Author: Thomas Chu")
					.setPositiveButton("確定", null).show();
			break;
		case R.id.exit:
			//回收 圖像資源
			if(!bmp.isRecycled())  {
				bmp.recycle();
				Log.d("VIEW", "onDoubleTap >> Bitmap recycle.");
			}
			ViewActivity.this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			float x1 = e1.getX();
			float y1 = e1.getY();
			float x2 = e2.getX();
			float y2 = e2.getY();
			
			//回收 圖像資源
			if(!bmp.isRecycled()) {
				bmp.recycle();
				Log.d("Fling", "Bitmap recycle.");
			}
			
			String dir = null;
			if ((x2 - x1) > 80) { // move to right
				dir = "往右  >>";
				if(++position>5)
					position=0;
					
				header.setText(ids[position]+" / "+ totalOfImages);
				Bitmap bmp = BitmapFactory.decodeFile(datas[position]);
				iv.setImageBitmap(bmp);	
				footer.setText("檔案名稱: "+imageName[position]+"\n"+"檔案大小: "+imageSize[position]);				
			}
			if ((x2 - x1) < 80) { // move to left
				dir = "往左 <<";
				if(--position<0)
					position=5;
					
				header.setText(ids[position]+" / "+ totalOfImages);
				Bitmap bmp = BitmapFactory.decodeFile(datas[position]);
				iv.setImageBitmap(bmp);	
				footer.setText("檔案名稱: "+imageName[position]+"\n"+"檔案大小: "+imageSize[position]);
			}			
			
			Log.d("Fling", dir + "(" + x1 + "," + y1 + ")-->(" + x2 + "," + y2
					+ "), " + velocityX + "," + velocityY);
			
			
			//return super.onFling(e1, e2, velocityX, velocityY);
			return false;
		}

			
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			//回收 圖像資源
			if(!bmp.isRecycled())  {
				bmp.recycle();
				Log.d("VIEW", "onDoubleTap >> Bitmap recycle.");
			}
			
			if(++position>=totalOfImages)
				position=0;
			
			header.setText(ids[position]+" / "+ totalOfImages);
			Bitmap bmp = BitmapFactory.decodeFile(datas[position]);
			iv.setImageBitmap(bmp);	
			footer.setText("檔案名稱: "+imageName[position]+"\n"+"檔案大小: "+imageSize[position]);
			
			Log.d("VIEW", "onShowPress >> "+ position);
			
			return false;
		}	
	} //end of MyGestureListener
}
