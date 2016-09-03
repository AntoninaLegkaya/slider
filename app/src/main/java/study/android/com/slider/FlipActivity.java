package study.android.com.slider;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ViewFlipper;

import butterknife.Bind;
import butterknife.ButterKnife;
import study.android.com.slider.bitmapTask.BitmapTask;


/**
 * Created by tony on 29.08.16.
 */
public class FlipActivity extends Activity {
    public static int MY_PERMISSIONS_REQUEST_READ_MEDIA = 0;
    static String TAG = "FlipActivity";
    @Bind(R.id.viewFlipper1)
    ViewFlipper flipper;
//    @Bind(R.id.update)
//    Button updateClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {
            BitmapTask bitmapTask = new BitmapTask(this, flipper);
            bitmapTask.execute();

        }

    }


    // Первая кнопка
    public void flipByClick(View v) {
        if (flipper.isFlipping())// Checking flipper is flipping or not.
        {
            flipper.stopFlipping(); // stops the flipping .
        }
        flipper.showNext();// shows the next view element of ViewFlipper
    }

    // Вторая кнопка
    public void flipByInterval(View v) {
        flipper.setFlipInterval(1500);// setting the interval 1500 milliseconds
        flipper.startFlipping(); // views flipping starts.
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_MEDIA) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                BitmapTask bitmapTask = new BitmapTask(this,flipper);
                bitmapTask.execute();

            }

        } else {

            Log.i(TAG, "Permission NOT GRANTED");
        }

    }


}



