package com.thomas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

public class ImageActivity extends Activity {

	private IconAdapter adpater;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Test ArrayAdapter
		// String[] ss = {"abc","def","ghi","123","456","789","xyz"};
		// GridView gv = (GridView) findViewById(R.id.grid);
		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// R.layout.item1, ss);
		// gv.setAdapter(adapter);

		adpater = new IconAdapter();
		GridView gv = (GridView) findViewById(R.id.grid);
		gv.setAdapter(adpater);

		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Log.d("GridView", position + "");

				String msg = null;
				msg = "顯示圖檔" + (position + 1);
				Log.d("GridView", msg);

				Intent intent = new Intent(ImageActivity.this,
						ViewActivity.class);
				intent.putExtra("position", position);
				intent.putExtra("totalOfImages", adpater.getCount());
				startActivity(intent);

				Toast.makeText(ImageActivity.this, msg, Toast.LENGTH_SHORT)
						.show();
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int item_id = item.getItemId();
		switch (item_id) {
		case R.id.about:
			new AlertDialog.Builder(ImageActivity.this).setTitle("關於Show Images")
					.setMessage("Created Date:2011/07/26\n Author: Thomas Chu")
					.setPositiveButton("確定", null).show();
			break;
		case R.id.exit:
			new AlertDialog.Builder(ImageActivity.this)
					.setTitle("結束程式")
					.setMessage("您確定要結束程式?")
					.setPositiveButton("確定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									ImageActivity.this.finish();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	/**
	 * The inner class of IconAdapter
	 * @author Thomas
	 *
	 */
	public class IconAdapter extends BaseAdapter {
		int[] ids;
		String[] datas;
		String[] iconNames;
		int count;

		public IconAdapter() {
			// super();
			ContentResolver cr = getContentResolver();
			// 查詢縮圖
			Cursor c = cr.query(Images.Thumbnails.EXTERNAL_CONTENT_URI, null,
					null, null, null);
			count = c.getCount();
			ids = new int[count];
			datas = new String[count];
			iconNames = new String[count];
			String[] description = { "雪山", "夕陽", "海雲", "園景", "山雲", "滿月" };
			int index = 0;
			while (c.moveToNext()) {
				int id = c.getInt(c.getColumnIndex(Images.Thumbnails._ID));
				// index = c.getColumnIndex(Images.Media.DISPLAY_NAME);
				String displayName = c.getString(c
						.getColumnIndex(Images.Thumbnails.IMAGE_ID));
				String data = c.getString(c
						.getColumnIndex(Images.Thumbnails.DATA));
				Log.d("IMG", id + "\t" + displayName + "\t" + data);
				ids[index] = id;
				datas[index] = data;
				iconNames[index] = displayName + "." + description[index];
				index++;
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return datas.length;
		}

		@Override
		public Object getItem(int pos) {
			// TODO Auto-generated method stub
			return datas[pos];
		}

		@Override
		public long getItemId(int pos) {
			// TODO Auto-generated method stub
			return ids[pos];
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.icon, null);
				ImageView iv = (ImageView) view.findViewById(R.id.icon_image);
				Bitmap bmp = BitmapFactory.decodeFile(datas[position]);
				// 設定圖檔大小的方法
				// bmp = ThumbnailUtils.extractThumbnail(bmp, 100, 100);
				// iv.setScaleType(ScaleType.FIT_XY);
				iv.setImageBitmap(bmp);
				TextView tv = (TextView) view.findViewById(R.id.icon_text);
				tv.setText(iconNames[position]);

			}
			return view;
		}
	}
}