package com.fernamuruthi.mkuki.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;

import com.fernamuruthi.mkuki.App;
import com.fernamuruthi.mkuki.R;
import com.fernamuruthi.mkuki.adapter.SectionedRecyclerViewAdapter;
import com.fernamuruthi.mkuki.api.Endpoints;
import com.fernamuruthi.mkuki.adapter.PostsAdapter;
import com.fernamuruthi.mkuki.decoration.PostsItemDecoration;
import com.fernamuruthi.mkuki.api.model.Post;
import com.fernamuruthi.mkuki.api.model.Posts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.gson.internal.$Gson$Preconditions.checkArgument;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<SectionedRecyclerViewAdapter.Section> sections =
            new ArrayList<>();
    private SectionedRecyclerViewAdapter.Section[] dateSections;
    private SectionedRecyclerViewAdapter mSectionedAdapter;
    private LinearLayoutManager mLayoutManager;
    private Call<Posts> postsCall;
    private ArrayList<Post> posts = new ArrayList<>();
    private PostsAdapter postsAdapter;
    private boolean loading = false;
    private SimpleDateFormat serverDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        postsAdapter = new PostsAdapter(this, posts);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new PostsItemDecoration(this));

        mSectionedAdapter = new
                SectionedRecyclerViewAdapter(this, R.layout.layout_text_view_date,R.id.text_view,postsAdapter);

        recyclerView.setAdapter(mSectionedAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) //check for scroll down
                {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if(!isLoading()){
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            getHunts();
                        }
                    }
                }
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onStart() {
        super.onStart();
        getHunts();
    }

    private void getHunts() {
        if(postsAdapter.getItemCount()==0) postsCall = App.getRetrofit().create(Endpoints.class).posts();
        else if(posts.get(postsAdapter.getItemCount()-1)==null) postsCall = App.getRetrofit().create(Endpoints.class).posts();
        else {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(serverDateFormat.parse(posts.get(postsAdapter.getItemCount()-1).getDay()));
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                String date = serverDateFormat.format(calendar.getTime());
                postsCall = App.getRetrofit().create(Endpoints.class).posts(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        setLoading(true);
        if(postsCall!=null)
            postsCall.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                setLoading(false);
                if(response.isSuccessful()){
                    if(posts.size()>0){
                        if(posts.get(posts.size()-1)!=null)
                            if(posts.get(posts.size()-1).getDay().equals(response.body().getPosts().get(0).getDay())){
                                Log.e(LOG_TAG,"Data received is similar to an existing data set. Dump data.");
                                return;
                            }
                    }
                    if(response.body().getPosts().size()>0){
                        addSectionHeader(postsAdapter.getItemCount(), getReadableDateFormat(response.body().getPosts().get(0).getDay()));

                        posts.addAll(response.body().getPosts());

                        mSectionedAdapter.notifyDataSetChanged();

                        if(posts.size()<10){
                            getHunts();
                        }
                    }
                    else {
                        Log.e(LOG_TAG,"Error: No posts returned");
                        handleServerError("No posts returned");
                    }

                } else {
                    handleServerError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                handleServerError(t.getMessage());
                Log.e(LOG_TAG, "Error: " + t.getMessage(), t);
                setLoading(false);
            }
        });
    }

    private void addSectionHeader(int position, String unParsedDate) {
        sections.add(new SectionedRecyclerViewAdapter.Section(position,unParsedDate));
        dateSections = new SectionedRecyclerViewAdapter.Section[sections.size()];
        mSectionedAdapter.setSections(sections.toArray(dateSections));
    }

    @NonNull
    private String getReadableDateFormat(String unParsedDate) {
        SimpleDateFormat readableDateFormatThisYear = new SimpleDateFormat("EEEE, MMM d", Locale.getDefault());
        SimpleDateFormat readableDateFormat = new SimpleDateFormat("MMMM d", Locale.getDefault());

        try {
            Date date = serverDateFormat.parse(unParsedDate);

            if(DateUtils.isToday(date.getTime())) {
                return "Today";
            }
            else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                Calendar calToday = Calendar.getInstance(); // today
                calToday.add(Calendar.DAY_OF_YEAR, -1); // yesterday

                if (calToday.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
                        && calToday.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)) {
                    return "Yesterday";
                }
                else if(calToday.get(Calendar.YEAR) == cal.get(Calendar.YEAR)){
                    return String.format("%s%s", readableDateFormatThisYear.format(date), getDayOfMonthSuffix(cal.get(Calendar.DAY_OF_MONTH)));
                }
                else {
                    return readableDateFormat.format(date)+getDayOfMonthSuffix(cal.get(Calendar.DAY_OF_MONTH))+", "+cal.get(Calendar.YEAR);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "Unknown Date";
        }

    }

    private String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
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

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {

        if(loading){
            posts.add(null);
            mSectionedAdapter.notifyItemInserted(mSectionedAdapter.getItemCount()-1);
        } else {
            posts.remove(postsAdapter.getItemCount()-1);
            mSectionedAdapter.notifyItemRemoved(mSectionedAdapter.getItemCount());
        }

        this.loading = loading;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (postsCall.isExecuted() && !postsCall.isExecuted()) postsCall.cancel();
    }

}
