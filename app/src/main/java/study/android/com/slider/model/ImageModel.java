package study.android.com.slider.model;

/**
 * Created by tony on 03.09.16.
 */
public class ImageModel {
    int valueProgress;

    public ImageModel(int valueProgress) {
        this.valueProgress = valueProgress;
    }

    public ImageModel() {

    }

    public int getValueProgress() {
        return valueProgress;
    }

    public void setValueProgress(int valueProgress) {
        this.valueProgress = valueProgress;
    }

}
