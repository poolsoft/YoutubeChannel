package id.co.sukenda.channel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.co.sukenda.channel.model.Item;
import id.co.sukenda.channel.model.YoutubeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements VideoAdapter.OnItemClickListener {

    private final static String API_KEY = "AIzaSyCTgawEUSKsT8m4k16KNgr1PNIl_9vZrO8";
    private final static String CHANNEL_ID = "UCVHFbqXqoYvEWM1Ddxl0QDg";
    private final static int MAX_RESULT = 50;
    private VideoAdapter videoAdapter;
    private List<Item> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        videoAdapter = new VideoAdapter(MainActivity.this, items, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(videoAdapter);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<YoutubeResponse> call = apiService.getVideoFromSpeificChannel(CHANNEL_ID, MAX_RESULT, API_KEY);
        call.enqueue(new Callback<YoutubeResponse>() {
            @Override
            public void onResponse(Call<YoutubeResponse> call, Response<YoutubeResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getItems().size() != 0) {
                        items.addAll(response.body().getItems());
                        videoAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<YoutubeResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(MainActivity.this, FullscreenActivity.class);
        intent.putExtra("video", item.getVideo().getVideoId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
