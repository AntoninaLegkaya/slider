package study.android.com.slider;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.android.com.slider.bitmapTask.BitmapTask;
import study.android.com.slider.model.ImageModel;
import study.android.com.slider.model.fragments.BitmapsFragment;
import study.android.com.slider.utilits.BitmapUtilit;


/**
 * Created by tony on 29.08.16.
 */
public class FlipActivity extends Activity implements SeekBar.OnSeekBarChangeListener {
    public static int MY_PERMISSIONS_REQUEST_READ_MEDIA = 0;
    static String TAG = "FlipActivity";
    private static final String TAG_IMAGES_MODEL = "TAG_IMAGES";
    private BitmapsFragment bitmapsFragment;
    private ImageModel imageModel;
    private FragmentTransaction fTrans;
    private ArrayList<File> files;
    private int progress = 0;
    final File path = BitmapUtilit.getSDcardPath();
    @Bind(R.id.viewFlipper1)
    ViewFlipper flipper;
    @Bind(R.id.seekBar)
    SeekBar seekbar;
    @Bind(R.id.textView1)
    TextView mTextValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        bitmapsFragment =
                (BitmapsFragment) getFragmentManager().findFragmentByTag(TAG_IMAGES_MODEL);
        checkExistBitmapFragment();
        seekbar.setOnSeekBarChangeListener(this);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
            try {
                files = BitmapUtilit.getImageFilePath(path);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Could not  get image pathes");
                e.printStackTrace();
            }
            if (!files.isEmpty()) {
                for (File bitmapPath : files) {
                    BitmapTask bitmapTask = new BitmapTask(this, flipper, bitmapPath);
                    bitmapTask.execute();
                }
            } else {

                Toast toast = Toast.makeText(this,
                        "No images for virew.", Toast.LENGTH_LONG);
                toast.show();


            }

        }


    }


    private void checkExistBitmapFragment() {
        if (bitmapsFragment != null) {
            imageModel = bitmapsFragment.getImageModel();
            progress = imageModel.getValueProgress();
            mTextValue.setText(String.valueOf(progress));
            fTrans = getFragmentManager().beginTransaction();
            fTrans.remove(bitmapsFragment);
            fTrans.add(R.id.frgmCont, bitmapsFragment);
            fTrans.commit();


        } else

        {
            bitmapsFragment = new BitmapsFragment();
            imageModel = bitmapsFragment.getImageModel();
            imageModel.setValueProgress(progress);
            mTextValue.setText(String.valueOf(progress));
            getFragmentManager().beginTransaction()
                    .add(bitmapsFragment, TAG_IMAGES_MODEL)
                    .commit();
        }

    }


    // Next image
    public void flipByClick(View v) {
        if (flipper.isFlipping())// Checking flipper is flipping or not.
        {
            flipper.stopFlipping(); // stops the flipping .
        }
        flipper.showNext();// shows the next view element of ViewFlipper
    }

    // Flip by interval
    public void flipByInterval(View v) {

        flipImages();
    }

    private void flipImages() {
        if (progress == 0) {
            progress = 1;
        }
        flipper.setFlipInterval(progress * 1000);// setting the interval 1500 milliseconds
        flipper.startFlipping(); // views flipping starts.
    }

    // Stop
    public void stopClick(View v) {
        seekbar.setProgress(0);
        mTextValue.setText("0");
        flipper.stopFlipping();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_MEDIA) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                try {
                    files = BitmapUtilit.getImageFilePath(path);
                } catch (FileNotFoundException e) {
                    Log.e(TAG, "Could not  get image pathes");
                    e.printStackTrace();
                }
                if (!files.isEmpty()) {
                    for (File bitmapPath : files) {
                        BitmapTask bitmapTask = new BitmapTask(this, flipper, bitmapPath);
                        bitmapTask.execute();
                    }
                } else {
                    Toast toast = Toast.makeText(this,
                            "No images for virew.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }

        } else {

            Log.i(TAG, "Permission NOT GRANTED");
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub
        progress = seekBar.getProgress();
        imageModel.setValueProgress(progress);
        mTextValue.setText(String.valueOf(progress));
        if (flipper.isFlipping())// Checking flipper is flipping or not.
        {
            flipper.stopFlipping();
            flipImages();// stops the flipping .
        }

    }
}



