package wito.government.app.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import wito.government.app.R;
import wito.government.app.activities.ItemViewer;


public class FeaturedAdapter extends RecyclerView.Adapter <FeaturedAdapter.Holder> {

    Context context;
    LayoutInflater mInflater;
    ArrayList<HashMap<String,String>> data;

    public FeaturedAdapter(
            Context context,
            ArrayList<HashMap<String,String>> files
            ) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        data = files;
    }

    @Override
    public Holder onCreateViewHolder (ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.featured_item_layout, parent, false);
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

//        new AlertDialog.Builder(context)
//                .setTitle("Images URL")
//                        .setMessage(imagesText.split(", ")[0]);
////                                .show();

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
        LinearLayout itemLayout;

        public Holder(View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.textView10);
            descTxt = itemView.findViewById(R.id.textView61);
            dPhoto = itemView.findViewById(R.id.imageView8);
            itemLayout = itemView.findViewById(R.id.layoutItem);
        }

    }

    public void showToast(String message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }

}