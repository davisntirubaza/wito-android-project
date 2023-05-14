package wito.government.app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;

import wito.government.app.Home;
import wito.government.app.R;

import java.util.HashMap;

public class ItemViewer extends AppCompatActivity {

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
        setContentView(R.layout.activity_event_viewer);

        HashMap<String, String> data = (HashMap<String, String>) getIntent().getSerializableExtra("info");

        TextView title, kuanzaTxt, about, kumalizaTxt, eneoTxt;
        Button nunuaHisaBtn, uzaHisaBtn, miadiBtn;
        ImageView photo = findViewById(R.id.imageView13);
        ImageView photo2 = findViewById(R.id.imageView9);
        progressBar = findViewById(R.id.progressBar);

        String[] photos = (String[]) getIntent().getSerializableExtra("photos");
        Glide.with(getApplicationContext()).load(photos[0]).into(photo);
        Glide.with(getApplicationContext()).load(photos[1]).into(photo2);

        getSupportActionBar().setTitle("Kuhusu mradi");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_new_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        title = findViewById(R.id.textView26);
        kuanzaTxt = findViewById(R.id.textView64);
        about = findViewById(R.id.textView25);
        nunuaHisaBtn = findViewById(R.id.button6);
        uzaHisaBtn = findViewById(R.id.button4);
        miadiBtn = findViewById(R.id.button5);
        kumalizaTxt = findViewById(R.id.textView66);
        eneoTxt = findViewById(R.id.textView27);

        title.setText(data.get("jina"));
        kuanzaTxt.setText(data.get("kuanza"));
        about.setText(data.get("maelezo"));
        kumalizaTxt.setText(data.get("kumaliza"));
        eneoTxt.setText(data.get("eneo"));


        // EXOPLAYER
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), "user-agent");
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(data.get("video"))));

        player = new SimpleExoPlayer.Builder(getApplicationContext()).build();
        player.setMediaSource(mediaSource);
        player.prepare();
        PlayerView playerView = findViewById(R.id.player_view);
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

        View.OnClickListener listener = view -> {
            // OPENING DIALOG
            NunuaHisa();
        };

        nunuaHisaBtn.setOnClickListener(listener);
        uzaHisaBtn.setOnClickListener(listener);
        miadiBtn.setOnClickListener(listener);
    }

    private void NunuaHisa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ItemViewer.this);
        builder.setTitle("Nunua hisa");

        View view = getLayoutInflater().inflate(R.layout.dialog_nunua_hisa, null);
        builder.setView(view);
        EditText edt_kiasi = view.findViewById(R.id.edt_kiasi);
        EditText edt_neno_siri = view.findViewById(R.id.edt_neno_siri);
        EditText edt_rudia_neno_siri = view.findViewById(R.id.edt_rudia_neno_siri);
        TextView cancel_button = view.findViewById(R.id.cancel_button);

        Button btn_nunua_hisa = view.findViewById(R.id.btn_nunua_hisa);

        final AlertDialog dialog = builder.create();

        btn_nunua_hisa.setOnClickListener(view1 -> {
            if (edt_kiasi.getText().toString().isEmpty()) {
                edt_kiasi.setError("Tafadhali weka kiasi!");
                edt_kiasi.requestFocus();
            } else if (edt_neno_siri.getText().toString().trim().isEmpty()) {
                edt_neno_siri.setError("Tafadhali ingiza neno siri!");
                edt_neno_siri.requestFocus();
            } else if (edt_rudia_neno_siri.getText().toString().trim().isEmpty()) {
                edt_rudia_neno_siri.setError("Tafadhali rudia neno siri!");
                edt_rudia_neno_siri.requestFocus();
            } else if (!edt_neno_siri.getText().toString().trim().equals(edt_rudia_neno_siri.getText().toString().trim())) {
                edt_rudia_neno_siri.setError("Neno siri halifanani");
                edt_rudia_neno_siri.requestFocus();
            } else {
                Toast.makeText(ItemViewer.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        cancel_button.setOnClickListener(view12 -> dialog.dismiss());
        dialog.show();
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