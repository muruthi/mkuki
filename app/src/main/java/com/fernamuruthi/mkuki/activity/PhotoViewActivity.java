package com.fernamuruthi.mkuki.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fernamuruthi.mkuki.R;
import com.fernamuruthi.mkuki.fragment.ImageViewFragment;
import com.fernamuruthi.mkuki.view.HackyViewPager;
import com.fernamuruthi.mkuki.adapter.ImagePageAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.EventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoViewActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    HackyViewPager viewPager;

    ArrayList<String> imageUrls = new ArrayList<>();

    private int mPosition;
    private ImagePageAdapter imagePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);

        mPosition = getIntent().getIntExtra(ImageViewFragment.ARG_PHOTO_POSITION,0);
        imagePageAdapter = new ImagePageAdapter(getSupportFragmentManager(), imageUrls);
        viewPager.setAdapter(imagePageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.image_button_close)
    public void onClick() {
        onBackPressed();
    }

    // UI updates must run on MainThread
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ArrayList<String> arrayList) {
        imageUrls.clear();
        imageUrls.addAll(arrayList);
        imagePageAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(mPosition);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
