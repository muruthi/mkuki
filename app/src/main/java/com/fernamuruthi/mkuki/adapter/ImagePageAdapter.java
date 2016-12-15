package com.fernamuruthi.mkuki.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fernamuruthi.mkuki.fragment.ImageViewFragment;
import com.fernamuruthi.mkuki.fragment.PhotoViewFragment;

import java.util.ArrayList;

/**
 * Created by 001590 on 2016-12-02.
 */

public class ImagePageAdapter extends FragmentPagerAdapter {

    public static final int TYPE_PHOTO_VIEW = 100;
    public static final int TYPE_IMAGE_VIEW = 200;

    private ArrayList<String> imageUrls = new ArrayList<>();
    private int imageViewType;

    public ImagePageAdapter(FragmentManager fm, ArrayList<String> imageUrls) {
        super(fm);
        this.imageUrls = imageUrls;
        this.imageViewType = TYPE_PHOTO_VIEW;
    }

    public ImagePageAdapter(FragmentManager fm, ArrayList<String> imageUrls, int imageViewType) {
        super(fm);
        this.imageUrls = imageUrls;
        this.imageViewType = imageViewType;
    }

    @Override
    public Fragment getItem(int position) {
        switch (getImageViewType()){
            case TYPE_IMAGE_VIEW:
                return ImageViewFragment.newInstance(imageUrls.get(position), position);
            default:
                return PhotoViewFragment.newInstance(imageUrls.get(position));
        }
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    public int getImageViewType() {
        return imageViewType;
    }
}
