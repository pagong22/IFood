package com.example.ifood.MainFeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ifood.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainFeedAdapter extends RecyclerView.Adapter<MainFeedAdapter.ViewHolder> {

    private List<MainFeedModel> postList;

    public MainFeedAdapter(List<MainFeedModel> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public MainFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_feed_item_layout, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainFeedAdapter.ViewHolder holder, int position) {
        MainFeedModel item = postList.get(position);
        holder.userName.setText(item.getUserName());
        holder.post.setText(item.getPost());
       // holder.imagURL.setText(item.getUserImage());





        String imageUrl = item.getUserImage();

        Picasso.get()
                .load(imageUrl)
              //  .placeholder(R.drawable.placeholder) // optional: show a placeholder until the image is loaded
              //   .error(R.drawable.error)             // optional: show an error image if there's an error loading the image
                .into(holder.userIcon);



    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, post, imagURL;
        ImageView userIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.mainFeed_name);
            post = itemView.findViewById(R.id.mainFeed_post);
            imagURL = itemView.findViewById(R.id.asdads);
            userIcon = itemView.findViewById(R.id.mainFeed_imgURL);



        }
    }
}
