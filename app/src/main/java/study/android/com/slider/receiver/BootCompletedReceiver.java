package study.android.com.slider.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import study.android.com.slider.FlipActivity;
import study.android.com.slider.R;

/**
 * Created by tony on 27.08.16.
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    static String TAG="BootCompletedReceiver";
    public BootCompletedReceiver() {
    }
    public void onReceive(Context context, Intent intent) {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus =context.getApplicationContext().registerReceiver(this, ifilter);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        int chargePlugType = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlugType == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlugType == BatteryManager.BATTERY_PLUGGED_AC;

        Intent intent1 = new Intent(context, FlipActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Toast toast = Toast.makeText(context.getApplicationContext(),
                    context.getResources().getString(R.string.message), Toast.LENGTH_LONG);
            toast.show();
            Log.i(TAG, context.getResources().getString(R.string.message));

            context.startActivity(intent1);

        }
        if(isCharging||usbCharge||acCharge){

            context.startActivity(intent1);
            Toast toast = Toast.makeText(context.getApplicationContext(),
                    context.getResources().getString(R.string.messageCharg), Toast.LENGTH_LONG);
            toast.show();

        }


    }
}
