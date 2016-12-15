package com.fernamuruthi.mkuki.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fernamuruthi.mkuki.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoViewFragment extends Fragment {

    public static final String ARG_PHOTO_URL = "arg_photo_url";

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.photo_view)
    PhotoView photoView;

    Unbinder unbinder;

    public static PhotoViewFragment newInstance(String imageUrl) {

        Bundle args = new Bundle();
        args.putString(ARG_PHOTO_URL, imageUrl);
        PhotoViewFragment fragment = new PhotoViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PhotoViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_view, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (!TextUtils.isEmpty(getArguments().getString(ARG_PHOTO_URL)))
            loadPhoto(getArguments().getString(ARG_PHOTO_URL));

        return view;
    }

    private void loadPhoto(String url) {
        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

        Glide.with(this)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        attacher.update();
                        return false;
                    }
                })
                .into(photoView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
