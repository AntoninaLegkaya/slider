package study.android.com.slider.model.fragments;

import android.app.Fragment;
import android.os.Bundle;

import study.android.com.slider.model.ImageModel;

/**
 * Created by tony on 03.09.16.
 */
public class BitmapsFragment extends Fragment {

    private final ImageModel imageModel;

    public BitmapsFragment() {
        this.imageModel = new ImageModel();
    }

    public BitmapsFragment(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


}
