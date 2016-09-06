package study.android.com.slider.utilits;

import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by tony on 02.09.16.
 */
public class BitmapUtilit {

    static String TAG = "BitmapUtilit";

    public static void listf(File directoryName, ArrayList<File> files) throws FileNotFoundException {
        Log.i(TAG, "Path externalMountedDevice:" + directoryName.getPath());
        File[] fList = directoryName.listFiles();
        for (File file : fList) {
            final String name = file.getName();
            if (file.isFile()) {
                Log.i(TAG, "File: " + name);
//                String ext = name.substring((name.lastIndexOf(".") + 1), name.length());
//                Log.i(TAG, "ext: " + ext);
                String mimeType = MimeTypeMap.getFileExtensionFromUrl(file.getPath().toString());
                Log.i(TAG, "mimeType: " + mimeType);
                if (mimeType.equals("jpg") || mimeType.equals("png")) {
                    Log.i(TAG, "Added File: " + name);
                    files.add(file);
                }
            } else if (file.isDirectory()) {
//                Log.i(TAG, "Папка: " + name);
                listf(file, files);
            }
        }

    }


    public static ArrayList<File> getImageFilePath(File directoryName) throws FileNotFoundException {
        ArrayList<File> files = new ArrayList<File>();
        File[] fList = directoryName.listFiles();
        for (File file : fList) {
            final String name = file.getName();
            if (file.isFile()) {

                String mimeType = MimeTypeMap.getFileExtensionFromUrl(file.getPath().toString());
                Log.i(TAG, "mimeType: " + mimeType);
                if (mimeType.equals("jpg") || mimeType.equals("png")) {
                    Log.i(TAG, "Added File: " + name);
                    files.add(file);
                }
            } else if (file.isDirectory()) {
                listf(file, files);
            }
        }
        return files;
    }


    public static String getSDcardAbsolutePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }


    public static File getSDcardPath() {
        return Environment.getExternalStorageDirectory();
    }

    // Реализация интерфейса FileNameFilter
    static class MyFileNameFilter implements FilenameFilter {

        private String ext;

        public MyFileNameFilter(String ext) {
            this.ext = ext.toLowerCase();
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(ext);
        }
    }
}
