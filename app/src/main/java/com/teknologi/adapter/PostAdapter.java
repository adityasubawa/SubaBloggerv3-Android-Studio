package com.teknologi.adapter;

/*
 * Created by Aditya Subawa
 * Product: Nawa Blogger based on Blogger API v3
 * 13 Okt 2020, Denpasar, Bali - Indonesia
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teknologi.api.Item;
import com.teknologi.blogger.DetailActivity;
import com.teknologi.blogger.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>
{

    private Context context;
    private List<Item> items;


    public PostAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = (View) inflater.inflate(R.layout.post_list, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        final Item item = items.get(position);
        holder.postTitle.setText(item.getTitle());
        final String id = item.getId();


        Document document = Jsoup.parse(item.getContent());
        holder.postDescription.setText(document.text());
        Elements elements = document.select("img");
        Glide.with(context).load(elements.get(0).attr("src")).into(holder.postImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("postId",id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postTitle;
        TextView postDescription;

        public PostViewHolder(View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.postImage);
            postTitle = itemView.findViewById(R.id.postTitle);
            postDescription = itemView.findViewById(R.id.postDescription);
        }
    }
}
