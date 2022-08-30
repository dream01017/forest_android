package com.scriptsbundle.adforest.Search.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ui.PlayerView;
import com.scriptsbundle.adforest.R;
import com.scriptsbundle.adforest.helper.CatSubCatOnclicklinstener;
import com.scriptsbundle.adforest.modelsList.catSubCatlistModel;
import com.scriptsbundle.adforest.utills.SettingsMain;

import java.util.ArrayList;

public class ItemCatgorySubListAdapter extends RecyclerView.Adapter<ItemCatgorySubListAdapter.CustomViewHolder> {

    SettingsMain settingsMain;
    private ArrayList<catSubCatlistModel> list;
    private CatSubCatOnclicklinstener oNItemClickListener;
    private Context mContext;

    public ItemCatgorySubListAdapter(Context context, ArrayList<catSubCatlistModel> feedItemList) {
        this.list = feedItemList;
        this.mContext = context;
        settingsMain = new SettingsMain(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cat_sub_cat, null);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cat_video, null);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {

        final catSubCatlistModel feedItem = list.get(position);

        holder.titleTextView.setText(list.get(position).getCardName());
        holder.pathTV.setText(list.get(position).getPath());
        holder.priceTV.setText(list.get(position).getPrice());
        holder.locationTV.setText(list.get(position).getLocation());
//        setScaleAnimation(holder.itemView);
//        if (feedItem.isIs_show_countDown()) {
//            holder.cv_countdownView.setVisibility(View.VISIBLE);
//            holder.cv_countdownView.start(AdsTimerConvert.adforest_bidTimer(feedItem.getTimer_array()));
//        }
//        else{
//            holder.cv_countdownView.setVisibility(View.GONE);
//        }
//        if (!TextUtils.isEmpty(feedItem.getImageResourceId())) {
//              Picasso.get().load(feedItem.getImageResourceId())
//                    .resize(270, 270).centerCrop()
//                    .error(R.drawable.placeholder)
//                    .placeholder(R.drawable.placeholder)
//                    .into(holder.mainImage);
//        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oNItemClickListener.onItemClick(feedItem);
            }
        };
        holder.linearLayoutMain.setOnClickListener(listener);

        if (feedItem.getVideoUrl() == null || feedItem.getVideoUrl().isEmpty()) {
            holder.vvPlayer.setVisibility(View.GONE);
            holder.ivPlay.setVisibility(View.GONE);
        } else {
            holder.vvPlayer.setVisibility(View.VISIBLE);
            holder.ivPlay.setVisibility(View.GONE);

            Uri uri = Uri.parse(feedItem.getVideoUrl());

            try {
                holder.vvPlayer.getPlayer().setMediaItem(MediaItem.fromUri(uri));
                holder.vvPlayer.getPlayer().prepare();
                holder.vvPlayer.getPlayer().setPlayWhenReady(false);

                holder.vvPlayer.getPlayer().addListener(new Player.Listener() {
                    @Override
                    public void onPlaybackStateChanged(int playbackState) {

                        if (playbackState == Player.STATE_BUFFERING) {
                            holder.progressBar.setVisibility(View.VISIBLE);
                        } else {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        if (playbackState == Player.STATE_READY) {
                            holder.ivPlay.setVisibility(View.VISIBLE);
                        }

//                        if (playbackState == Player.STATE_ENDED) {
//                            holder.vvPlayer.getPlayer().seekTo(0);
//                        }
                    }

                    @Override
                    public void onIsPlayingChanged(boolean isPlaying) {
                        if (isPlaying) {
                            holder.ivPlay.setVisibility(View.GONE);
                        } else {
                            holder.ivPlay.setVisibility(View.VISIBLE);
                        }
                    }
                });

            } catch (Exception e) {
                Log.e("TAG", "Error : " + e.toString());
            }

            holder.ivPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.vvPlayer.getPlayer().play();
                }
            });

            holder.vvPlayer.getVideoSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.vvPlayer.getPlayer().isPlaying()) {
                        holder.vvPlayer.getPlayer().pause();
                    } else {
                        holder.vvPlayer.getPlayer().play();
                    }
                }
            });

            holder.vvPlayer.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    holder.vvPlayer.getPlayer().stop();
                }
            });

        }

    }
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
//        Animation anim = android.view.animation.AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    @Override
    public int getItemCount() {
        return (null != list ? list.size() : 0);
    }

    public CatSubCatOnclicklinstener getOnItemClickListener() {
        return oNItemClickListener;
    }

    public void setOnItemClickListener(CatSubCatOnclicklinstener onItemClickListener) {
        this.oNItemClickListener = onItemClickListener;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView, pathTV, priceTV, locationTV;
//        private ImageView mainImage;
        private LinearLayout linearLayoutMain;
//        private CountdownView cv_countdownView;
        private final PlayerView vvPlayer;
        private final ImageView ivPlay;
        private final ProgressBar progressBar;

        CustomViewHolder(View v) {
            super(v);

            titleTextView = v.findViewById(R.id.text_view_name);
            pathTV = v.findViewById(R.id.flow);
            priceTV = v.findViewById(R.id.prices);
            locationTV = v.findViewById(R.id.location);
//            mainImage = v.findViewById(R.id.image_view);
//            cv_countdownView = v.findViewById(R.id.cv_countdownView);

            priceTV.setTextColor(Color.parseColor(settingsMain.getMainColor()));

            linearLayoutMain = v.findViewById(R.id.linear_layout_card_view);

            vvPlayer = v.findViewById(R.id.vvPlayer);
            ivPlay = v.findViewById(R.id.ivPlay);
            progressBar = v.findViewById(R.id.progress_bar);

            ExoPlayer exoPlayer = new ExoPlayer.Builder(mContext).build();
            vvPlayer.setPlayer(exoPlayer);
        }
    }
}