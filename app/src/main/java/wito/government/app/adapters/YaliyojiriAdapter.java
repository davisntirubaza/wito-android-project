package wito.government.app.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import wito.government.app.R;
import wito.government.app.activities.ArticleViewer;
import wito.government.app.activities.ItemViewer;

import java.util.ArrayList;
import java.util.HashMap;


public class YaliyojiriAdapter extends RecyclerView.Adapter <YaliyojiriAdapter.Holder> {

    Context context;
    LayoutInflater mInflater;
    ArrayList<HashMap<String,String>> data;

    public YaliyojiriAdapter(
            Context context,
            ArrayList<HashMap<String,String>> files
            ) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        data = files;
    }

    @Override
    public Holder onCreateViewHolder (ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.yaliyojiri_item_layout, parent, false);
        return new Holder(mItemView);
    }

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder (Holder holder, @SuppressLint("RecyclerView") int position) {

        HashMap<String, String> item = data.get(position);
        holder.title.setText(item.get("title"));

        holder.videoItem.setOnClickListener(go -> {
            Intent intent = new Intent(context, ArticleViewer.class);
            intent.putExtra("info", item);
            context.startActivity(intent);
        });

        holder.title.setOnClickListener(go -> {
            Intent intent = new Intent(context, ArticleViewer.class);
            intent.putExtra("info", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView title;
        ConstraintLayout videoItem;

        public Holder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView28);
            videoItem = itemView.findViewById(R.id.itemView);
        }

    }

    public void showToast(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }

}