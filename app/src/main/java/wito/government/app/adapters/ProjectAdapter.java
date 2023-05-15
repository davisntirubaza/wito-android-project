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

import java.util.ArrayList;
import java.util.HashMap;

import wito.government.app.R;
import wito.government.app.activities.ItemViewer;
import wito.government.app.activities.ProductViewer;


public class ProjectAdapter extends RecyclerView.Adapter <ProjectAdapter.Holder> {

    Context context;
    LayoutInflater mInflater;
    ArrayList<HashMap<String,String>> data;

    public ProjectAdapter(
            Context context,
            ArrayList<HashMap<String,String>> files
            ) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        data = files;
    }

    @Override
    public Holder onCreateViewHolder (ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.miradi_item_layout, parent, false);
        return new Holder(mItemView);
    }

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder (Holder holder, @SuppressLint("RecyclerView") int position) {

        HashMap<String, String> item = data.get(position);
        holder.titleTxt.setText(item.get("jina"));
        holder.descTxt.setText(item.get("maelezo"));

        String imagesText = item.get("picha");
        imagesText = imagesText.substring(1, imagesText.length() - 1);
        String[] photos = imagesText.split(", ");

        Glide.with(context).load(photos[0]).into(holder.dPhoto);

        String[] finalPhotos = photos;
        holder.itemLayout.setOnClickListener(go -> {
            Intent intent = new Intent(context, ItemViewer.class);
            intent.putExtra("photos", finalPhotos);
            intent.putExtra("info", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        TextView titleTxt, descTxt;
        ImageView dPhoto;
        ConstraintLayout itemLayout;

        public Holder(View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.projectName);
            descTxt = itemView.findViewById(R.id.projectDescription);
            dPhoto = itemView.findViewById(R.id.coverImage);
            itemLayout = itemView.findViewById(R.id.mradi_item);
        }

    }

    public void showToast(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }

}