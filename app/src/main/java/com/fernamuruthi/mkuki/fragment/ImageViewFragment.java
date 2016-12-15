package com.fernamuruthi.mkuki.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fernamuruthi.mkuki.R;
import com.fernamuruthi.mkuki.activity.PhotoViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageViewFragment extends Fragment {

    public static final String ARG_PHOTO_URL = "arg_photo_url";
    public static final String ARG_PHOTO_POSITION = "arg_photo_position";

    @BindView(R.id.image_view)
    ImageView imageView;

    Unbinder unbinder;

    private int mPosition;

    public static ImageViewFragment newInstance(String imageUrl) {

        Bundle args = new Bundle();
        args.putString(ARG_PHOTO_URL, imageUrl);
        ImageViewFragment fragment = new ImageViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ImageViewFragment newInstance(String imageUrl, int position) {

        Bundle args = new Bundle();
        args.putString(ARG_PHOTO_URL, imageUrl);
        args.putInt(ARG_PHOTO_POSITION, position);
        ImageViewFragment fragment = new ImageViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ImageViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_hunt, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (!TextUtils.isEmpty(getArguments().getString(ARG_PHOTO_URL)))
            loadPhoto(getArguments().getString(ARG_PHOTO_URL));
        mPosition = getArguments().getInt(ARG_PHOTO_POSITION,0);
        return view;
    }

    private void loadPhoto(String url) {
        Glide.with(this).load(url).centerCrop().into(imageView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.image_view)
    public void onClick() {
        Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
        intent.putExtra(ARG_PHOTO_POSITION,mPosition);
        startActivity(intent);
    }
}
