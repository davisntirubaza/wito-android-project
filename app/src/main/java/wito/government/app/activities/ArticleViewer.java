package wito.government.app.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.HashMap;

import wito.government.app.R;


public class ArticleViewer extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_viewer);

//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Yaliyojiri");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_new_24);

        HashMap<String, String> data = (HashMap<String, String>) getIntent().getSerializableExtra("info");
        TextView title, maelezo, date;
        title = findViewById(R.id.n_title);
        maelezo = findViewById(R.id.n_body);
        date = findViewById(R.id.posted_on);
        ImageView cover = findViewById(R.id.n_cover);

        title.setText(data.get("title"));
        maelezo.setText(data.get("maelezo"));
        date.setText(data.get("timestamp"));

        Glide.with(getApplicationContext()).load(Uri.parse(data.get("picha"))).into(cover);
    }
}