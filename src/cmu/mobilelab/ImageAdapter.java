package cmu.mobilelab;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

class ImageAdapter extends BaseAdapter {
    int mGalleryItemBackground;
	private Context context;
	private ArrayList<String> uris;
	private static final String TAG = "ImageAdapter";


    public ImageAdapter(ArrayList<String> photoUris, Context c) {
        context = c;
        uris = photoUris;
        // See res/values/attrs.xml for the  defined values here for styling
        TypedArray a = context.obtainStyledAttributes(R.styleable.imageGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.imageGallery_android_galleryItemBackground, 0);
        a.recycle();
    }

    public int getCount() {
      return uris.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
		final ImageView i = new ImageView(context);
		final String photo = uris.get(position);
		i.setImageURI(Uri.parse(photo));
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setLayoutParams(new Gallery.LayoutParams(136, 136));
		i.setBackgroundResource(mGalleryItemBackground);
		i.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  
				intent.setAction(android.content.Intent.ACTION_VIEW);    
				intent.setDataAndType(Uri.parse(photo), "image/*");
				context.startActivity(intent);
			}
			
		});
		
		i.setOnLongClickListener(new OnLongClickListener(){
			@Override
			public boolean onLongClick(View v) {
				AlertDialog.Builder long_alert_builder = new AlertDialog.Builder(context);
        		long_alert_builder.setMessage("Remove Photo?")
        		       .setCancelable(true)
        		       .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        						i.setVisibility(View.GONE);
        						uris.remove(position);
        		          }
        		       })
        		       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		           public void onClick(DialogInterface dialog, int id) {
        		               //return nothing if the dialog is canceled 
        		        	   dialog.cancel();
        		           }
        		       });
        		
        		AlertDialog remove_photo_alert = long_alert_builder.create();
        		remove_photo_alert.show();
				
				return false;
			}

		});
		
    	return i;
    }    
    
  }