package study.android.com.slider.bitmapTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.Bind;
import study.android.com.slider.FlipActivity;
import study.android.com.slider.R;
import study.android.com.slider.utilits.BitmapUtilit;

/**
 * Created by tony on 02.09.16.
 */
public class BitmapTask extends AsyncTask<Void, Void, Bitmap> {
    static String TAG = "BitmapTask";
    private Context context;
    private ImageView mImageView;
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ViewFlipper flipper;
    private ArrayList<File> files = new ArrayList<File>();
    private File bitmapPath;

    final File path = BitmapUtilit.getSDcardPath();

    public BitmapTask(Context context, ViewFlipper flipper) {

        this.context = context;
        this.flipper = flipper;
    }

    public BitmapTask(Context context, ViewFlipper flipper, File bitmapPath) {

        this.context = context;
        this.flipper = flipper;
        this.bitmapPath = bitmapPath;
    }

//    @Override
//    protected ArrayList<Bitmap> doInBackground(Void... voids) {
//
//        try {
//            BitmapUtilit.listf(path, files);
//        } catch (FileNotFoundException e) {
//            Log.e(TAG, "Coud not get file paths!");
//        }
//
//        if (!files.isEmpty()) {
//
//            for (File file
//                    : files) {
//                FileInputStream fis = null;
//                try {
//                    fis = new FileInputStream(file);
//                } catch (FileNotFoundException e) {
//                    Log.e(TAG, "Coudn't get bitmap from storage!");
//                    e.printStackTrace();
//                }
//                BufferedInputStream bis = new BufferedInputStream(fis);
//                Bitmap img = BitmapFactory.decodeStream(bis);
//                bitmaps.add(img);
//
//            }
//            return bitmaps;
//
//
//        } else {
//            Log.i(TAG, "Bitmaps gallery is empty!");
//        }
//
//        return null;
//    }

//    @Override
//    protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
//
//        if (!bitmaps.isEmpty()) {
//
//            for (Bitmap bitmap : bitmaps) {
//                mImageView = new ImageView(context);
//                mImageView.setImageBitmap(bitmap);
//                if (mImageView != null) {
//                    Log.i(TAG, "Added Bitmap from gallery!");
//                    flipper.addView(mImageView);
//                }
//
//            }
//
//
//        } else {
//            Toast.makeText(context, "Images didn't add to slider. Gallery is empty!", Toast.LENGTH_SHORT).show();
//
//            Log.i(TAG, "Images didn't add to slider. Gallery is empty!");
//        }
//
//
//    }


    @Override
    protected Bitmap doInBackground(Void... voids) {


        if (bitmapPath != null) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(bitmapPath);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Couldn't get bitmap from storage!");
                e.printStackTrace();
            }
            BufferedInputStream bis = new BufferedInputStream(fis);
            Bitmap img = BitmapFactory.decodeStream(bis);

            return img;


        } else

        {
            Log.i(TAG, "Bitmap is empty!");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if (bitmap != null) {
            mImageView = new ImageView(context);
            mImageView.setImageBitmap(bitmap);
            if (mImageView != null) {
                Log.i(TAG, "Added Bitmap from gallery!");
                flipper.addView(mImageView);
            }
        } else {
            Toast.makeText(context, "Images didn't add to slider. Gallery is empty!", Toast.LENGTH_SHORT).show();

            Log.i(TAG, "Images didn't add to slider. Gallery is empty!");
        }


    }


}
