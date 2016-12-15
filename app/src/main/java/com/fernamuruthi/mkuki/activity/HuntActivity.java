package com.fernamuruthi.mkuki.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fernamuruthi.mkuki.App;
import com.fernamuruthi.mkuki.R;
import com.fernamuruthi.mkuki.adapter.CommentRecyclerViewAdapter;
import com.fernamuruthi.mkuki.adapter.ImagePageAdapter;
import com.fernamuruthi.mkuki.adapter.PostsAdapter;
import com.fernamuruthi.mkuki.adapter.UserRecyclerViewAdapter;
import com.fernamuruthi.mkuki.api.Endpoints;
import com.fernamuruthi.mkuki.api.model.Comment;
import com.fernamuruthi.mkuki.api.model.InstallLink;
import com.fernamuruthi.mkuki.api.model.Media;
import com.fernamuruthi.mkuki.api.model.Post;
import com.fernamuruthi.mkuki.api.model.PostResponse;
import com.fernamuruthi.mkuki.api.model.User;
import com.fernamuruthi.mkuki.decoration.CommentItemDecoration;
import com.fernamuruthi.mkuki.decoration.PostsItemDecoration;
import com.fernamuruthi.mkuki.util.NumberUtils;
import com.fernamuruthi.mkuki.view.HackyViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HuntActivity extends AppCompatActivity {

    private static final String LOG_TAG = HuntActivity.class.getSimpleName();

    @BindView(R.id.view_pager)
    HackyViewPager viewPager;
    @BindView(R.id.image_view_thumbnail)
    ImageView imageViewThumbnail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.text_view_name)
    TextView textViewName;
    @BindView(R.id.image_view_trending)
    ImageView imageViewTrending;
    @BindView(R.id.image_view_android)
    ImageView imageViewAndroid;
    @BindView(R.id.text_view_tagline)
    TextView textViewTagline;
    @BindView(R.id.text_view_vote_count)
    TextView textViewVoteCount;
    @BindView(R.id.btn_get_it)
    Button btnGetIt;
    @BindView(R.id.circle_image_view_hunter)
    CircleImageView circleImageViewHunter;
    @BindView(R.id.text_view_hunter_name)
    TextView textViewHunterName;
    @BindView(R.id.recycler_view_user)
    RecyclerView recyclerViewUser;
    @BindView(R.id.btn_view_all_makers)
    Button btnViewAllMakers;
    @BindView(R.id.layout_no_makers)
    RelativeLayout layoutNoMakers;
    @BindView(R.id.recycler_view_comments)
    RecyclerView recyclerViewComments;
    @BindView(R.id.recycler_view_related_posts)
    RecyclerView recyclerViewRelatedPosts;
    @BindView(R.id.progress_bar_comments)
    ProgressBar progressBarComments;
    @BindView(R.id.progress_bar_related_posts)
    ProgressBar progressBarRelatedPosts;
    @BindView(R.id.layout_no_comments)
    RelativeLayout layoutNoComments;
    @BindView(R.id.layout_no_related_posts)
    RelativeLayout layoutNoRelatedPosts;

    private Post post;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private ImagePageAdapter imagePageAdapter;
    private UserRecyclerViewAdapter userRecyclerViewAdapter;
    private Call<PostResponse> postCall;
    private CommentRecyclerViewAdapter commentRecyclerViewAdapter;
    private ArrayList<Comment> comments = new ArrayList<>();
    private PostsAdapter postsAdapter;
    private boolean loading;
    private Menu menu;
    private String installLink;
    private CustomTabsIntent.Builder builder;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hunt);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagePageAdapter = new ImagePageAdapter(getSupportFragmentManager(), imageUrls, ImagePageAdapter.TYPE_IMAGE_VIEW);
        viewPager.setAdapter(imagePageAdapter);

        post = EventBus.getDefault().removeStickyEvent(Post.class);
        if (post != null) {
            refreshPost();
            loadUI();
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    if (post != null) collapsingToolbar.setTitle(post.getName());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(null);
                    isShow = false;
                }
            }
        });

        builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.ic_close_white_24dp));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hunt, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_install:
                if(!TextUtils.isEmpty(installLink)){
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(this, Uri.parse(installLink));
                }
                break;
            case R.id.action_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, String.format("I just discovered \"%s\" on Mkuki a ProductHunt client for Android.", post.getName()));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, post.getTagline()+" "+post.getRedirectUrl());
                startActivity(Intent.createChooser(sharingIntent, "Share"));
                setIntent(sharingIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshPost() {
        setLoading(true);
        postCall = App.getRetrofit().create(Endpoints.class).post(post.getId());
        postCall.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                setLoading(false);
                if (response.isSuccessful()) {
                    post = response.body().getPost();
                    loadUI();
                } else {
                    handleServerError(response.message());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                handleServerError(t.getMessage());
                Log.e(LOG_TAG, "Error: " + t.getMessage(), t);
                setLoading(false);
            }
        });
    }

    private void loadUI() {
        //Glide.with(this).load(post.getThumbnail().getImageUrl()).fitCenter().placeholder(R.color.colorLightGrey).into(imageViewThumbnail);
        textViewName.setText(post.getName());
        textViewTagline.setText(post.getTagline());
        textViewVoteCount.setText(NumberUtils.format(post.getVotesCount()));
        if (post.getVotesCount() > 500) imageViewTrending.setVisibility(View.VISIBLE);
        if (post.getUser() != null) {
            Glide.with(this).load(post.getUser().getImageUrl().getFourtyPx()).fitCenter().placeholder(R.color.colorLightGrey).dontAnimate().into(circleImageViewHunter);
            textViewHunterName.setText(post.getUser().getName());
        }
        if (!imageUrls.contains(post.getScreenshots().getThreeHundredPxUrl()))
            imageUrls.add(post.getScreenshots().getThreeHundredPxUrl());
        if (post.getMedia() != null) {
            if (post.getMedia().size() > 0) {
                int mediaLastItemPosition = post.getMedia().size() - 1;
                for (Media media : post.getMedia()) {
                    if (post.getMedia().indexOf(media) == mediaLastItemPosition) {
                        Log.d(LOG_TAG, "Skipped first entry icon");
                    } else if (media.getMediaType().equals("image")) {
                        imageUrls.add(media.getImageUrl());
                    }
                }
            }
        }

        EventBus.getDefault().postSticky(imageUrls);
        imagePageAdapter.notifyDataSetChanged();

        if (post.getMakers().size() > 0) {
            if (userRecyclerViewAdapter == null) {
                ArrayList<User> firstFourUsers = new ArrayList<>();
                if (post.getMakers().size() <= 4) firstFourUsers = post.getMakers();
                else {
                    for (int i = 0; i < 4; i++) {
                        firstFourUsers.add(post.getMakers().get(i));
                    }
                    btnViewAllMakers.setVisibility(View.VISIBLE);
                }
                userRecyclerViewAdapter = new UserRecyclerViewAdapter(getApplicationContext(), firstFourUsers);
                recyclerViewUser.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
                recyclerViewUser.setNestedScrollingEnabled(false);
                recyclerViewUser.setAdapter(userRecyclerViewAdapter);
            } else {
                userRecyclerViewAdapter.notifyDataSetChanged();
            }

            layoutNoMakers.setVisibility(View.GONE);
            recyclerViewUser.setVisibility(View.VISIBLE);

        } else {
            layoutNoMakers.setVisibility(View.VISIBLE);
            recyclerViewUser.setVisibility(View.GONE);
        }

        if (post.getComments() != null) {
            setComments();
            layoutNoComments.setVisibility(View.GONE);
            if (commentRecyclerViewAdapter == null) {
                commentRecyclerViewAdapter = new CommentRecyclerViewAdapter(getApplicationContext(), comments);
                recyclerViewComments.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerViewComments.setNestedScrollingEnabled(false);
                recyclerViewComments.setAdapter(commentRecyclerViewAdapter);
                recyclerViewComments.addItemDecoration(new CommentItemDecoration(this));
            } else {
                commentRecyclerViewAdapter.notifyDataSetChanged();
            }
            if(comments.size()==0&&!isLoading()){
                layoutNoComments.setVisibility(View.VISIBLE);
            }
        }
        else if(!isLoading()){
            layoutNoComments.setVisibility(View.VISIBLE);
        }

        if (post.getRelatedPosts() != null) {
            layoutNoRelatedPosts.setVisibility(View.GONE);
            if (postsAdapter == null) {
                postsAdapter = new PostsAdapter(getApplicationContext(), post.getRelatedPosts());
                recyclerViewRelatedPosts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerViewRelatedPosts.setNestedScrollingEnabled(false);
                recyclerViewRelatedPosts.setAdapter(postsAdapter);
                recyclerViewRelatedPosts.addItemDecoration(new PostsItemDecoration(this));
            } else {
                postsAdapter.notifyDataSetChanged();
            }
            if(post.getRelatedPosts().size()==0&&!isLoading()){
                layoutNoRelatedPosts.setVisibility(View.VISIBLE);
            }
        }
        else if(!isLoading()){
            layoutNoRelatedPosts.setVisibility(View.VISIBLE);
        }

        if(post.getInstallLinks()!=null){
            for(InstallLink installLink:post.getInstallLinks()){
                if(installLink.getPlatform()!=null)
                    if(installLink.getPlatform().equals("android")){
                        setInstallLink(installLink.getRedirectUrl());
                        if(menu!=null) menu.findItem(R.id.action_install).setVisible(true);
                    }
            }
        }
    }

    private void setComments() {
        comments.clear();
        for (Comment comment : post.getComments()) {
            comments.add(comment);
            if (comment.getChildComments() != null) {
                comments.addAll(comment.getChildComments());
            }
        }
    }

    @OnClick(R.id.btn_get_it)
    public void onClick() {
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(post.getRedirectUrl()));
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    private void handleServerError(String status) {
        if (postCall.isExecuted() && !postCall.isCanceled()) {
            if (TextUtils.isEmpty(status)) {
                status = getString(R.string.error_unknown);
            }
            if (textViewHunterName != null)
                Snackbar.make(textViewHunterName, status, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action_retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onStart();
                            }
                        }).show();
        }
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        if (loading) {
            progressBarComments.setVisibility(View.VISIBLE);
            progressBarRelatedPosts.setVisibility(View.VISIBLE);
        } else {
            progressBarComments.setVisibility(View.GONE);
            progressBarRelatedPosts.setVisibility(View.GONE);
        }
        this.loading = loading;
    }

    public String getInstallLink() {
        return installLink;
    }

    public void setInstallLink(String installLink) {
        this.installLink = installLink;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (postCall != null)
            if (postCall.isExecuted() && !postCall.isCanceled()) postCall.cancel();
        EventBus.getDefault().removeAllStickyEvents();
    }
}
