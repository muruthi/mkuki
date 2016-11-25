package com.fernamuruthi.mkuki;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fernamuruthi.mkuki.api.Endpoints;
import com.fernamuruthi.mkuki.api.adapter.PostsAdapter;
import com.fernamuruthi.mkuki.api.decoration.ItemDecoration;
import com.fernamuruthi.mkuki.api.model.Post;
import com.fernamuruthi.mkuki.api.model.Posts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Call<Posts> postsCall;
    private ArrayList<Post> posts = new ArrayList<>();
    private PostsAdapter postsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        postsAdapter = new PostsAdapter(this, posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new ItemDecoration(this));
        recyclerView.setAdapter(postsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        postsCall = App.getRetrofit().create(Endpoints.class).posts(9);
        postsCall.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if(response.isSuccessful()){
                    posts.clear();
                    posts.addAll(response.body().getPosts());
                    postsAdapter.notifyDataSetChanged();
                } else {
                    handleServerError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                handleServerError(t.getMessage());
                Log.e(LOG_TAG, "Error: " + t.getMessage(), t);
            }
        });
    }

    private void handleServerError(String status) {
        if (postsCall.isExecuted() && !postsCall.isCanceled()) {
            if (TextUtils.isEmpty(status)) {
                status = getString(R.string.error_unknown);
            }
            if (recyclerView != null)
                Snackbar.make(recyclerView, status, Snackbar.LENGTH_LONG)
                        .setAction(R.string.action_retry, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onStart();
                            }
                        }).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (postsCall.isExecuted() && !postsCall.isExecuted()) postsCall.cancel();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
