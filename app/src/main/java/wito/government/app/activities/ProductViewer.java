package wito.government.app.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import java.util.HashMap;

import wito.government.app.Home;
import wito.government.app.R;

public class ProductViewer extends AppCompatActivity {

    SimpleExoPlayer player;
    ProgressBar progressBar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_viewer);

        HashMap<String, String> data = (HashMap<String, String>) getIntent().getSerializableExtra("info");

        TextView title, kuanzaTxt, about, kumalizaTxt, eneoTxt;
        Button bookBtn;
        ImageView photo = findViewById(R.id.imageView13p);
        ImageView photo2 = findViewById(R.id.imageView9p);
        progressBar = findViewById(R.id.progressBarp);

        String[] photos = (String[]) getIntent().getSerializableExtra("photos");
        Glide.with(getApplicationContext()).load(photos[0]).into(photo);
        Glide.with(getApplicationContext()).load(photos[1]).into(photo2);


        getSupportActionBar().setTitle("Kuhusu bidhaa");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_new_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        title = findViewById(R.id.textView26p);
//        kuanzaTxt = findViewById(R.id.textView64p);
        about = findViewById(R.id.textView25p);
        bookBtn = findViewById(R.id.button6p);
//        kumalizaTxt = findViewById(R.id.textView66p);
//        eneoTxt = findViewById(R.id.textView27p);

        title.setText(data.get("jina"));
//        kuanzaTxt.setText(data.get("kuanza"));
        about.setText(data.get("maelezo"));
//        kumalizaTxt.setText(data.get("kumaliza"));
//        eneoTxt.setText(data.get("eneo"));


        // EXOPLAYER
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), "user-agent");
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(data.get("video"))));

        player = new SimpleExoPlayer.Builder(getApplicationContext()).build();
        player.setMediaSource(mediaSource);
        player.prepare();
        PlayerView playerView = findViewById(R.id.player_viewp);
        playerView.setPlayer(player);
        player.play();

        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                switch (playbackState) {
                    case Player.STATE_BUFFERING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_READY:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                }
                Player.Listener.super.onPlaybackStateChanged(playbackState);
            }
        });


        bookBtn.setOnClickListener(go -> {

            new AlertDialog.Builder(ProductViewer.this)
                    .setTitle("Confirm")
                    .setMessage("Are you sure you want to buy this product?")
                    .setPositiveButton("YES", (dialog, d) -> {
                        Intent back = new Intent(getApplicationContext(), Home.class);
                        showToast("Order sent!");
                        startActivity(back);
                        finish();
                    }).setNegativeButton("NO", (n, nn) -> {
                        // Do nothing!
                    }).show();


        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }


    public void showToast(String message) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
}