package com.cook.rotenzonew.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cook.rotenzonew.Model.Videolist;
import com.cook.rotenzonew.R;

import java.util.ArrayList;
import java.util.List;

import static com.cook.rotenzonew.Utils.URLs.RECIPE_VIDEO_URL;

/**
 * Created by Raghvendra Sahu on 20/11/2019.
 */
public class VideoAdapter  extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    public List<Videolist> checkListModelList;
    Videolist checkListModel;
    public Context context;
    View viewlike;




    public VideoAdapter(ArrayList<Videolist> checkListModelArrayList,
                        FragmentActivity activity) {
        context = activity;
        checkListModelList = checkListModelArrayList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;
        LinearLayout mLinearLayout;
        VideoView video_view;
        CardView card_video;
        ImageView image_view_thm,image_play;

        public ViewHolder(View view) {
            super(view);
            viewlike = view;
            video_view = viewlike.findViewById(R.id.video_view);
            tv_name = viewlike.findViewById(R.id.tv_name);
            card_video = viewlike.findViewById(R.id.card_video);
            image_view_thm = viewlike.findViewById(R.id.image_view_thm);
            image_play = viewlike.findViewById(R.id.image_play);

        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        // final Show_Menu_Model showMenuModel = show_menu_modelArrayList.get(position);

        if (checkListModelList.size() > 0) {
            checkListModel = checkListModelList.get(position);
            viewHolder.tv_name.setText(checkListModel.getVideo_title());

            if (checkListModelList.get(position).getVideo().equalsIgnoreCase("")){
                final String vid_url=RECIPE_VIDEO_URL+checkListModelList.get(position).getVideo();

                //************find thumbnails image from video url**
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.isMemoryCacheable();
                Glide.with(context).setDefaultRequestOptions(requestOptions)
                        .load(vid_url)
                        .into(viewHolder.image_view_thm);

            }


            viewHolder.card_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        viewHolder.image_view_thm.setVisibility(View.GONE);
                        viewHolder.image_play.setVisibility(View.GONE);
                        viewHolder.video_view.setVisibility(View.VISIBLE);

                        if (checkListModelList.get(position).getVideo()!=null &&
                                !checkListModelList.get(position).getVideo().equalsIgnoreCase("")){

                            Log.e("video_url", RECIPE_VIDEO_URL+checkListModelList.get(position).getVideo());
                            MediaController vidControl = new MediaController(context);
                            String vidAddress = RECIPE_VIDEO_URL+checkListModelList.get(position).getVideo();
                            Uri vidUri = Uri.parse(vidAddress);
                            viewHolder.video_view.setVideoURI(vidUri);
                            viewHolder.video_view.start();
                            vidControl.setAnchorView(viewHolder.video_view);
                            viewHolder.video_view.setMediaController(vidControl);

                        }else {
                            Toast.makeText(context, R.string.no_video, Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){


                    }



                }
            });



        } else {
            Toast.makeText(context, R.string.no_rcord_found, Toast.LENGTH_SHORT).show();
        }




    }

    @Override
    public int getItemCount() {
        return checkListModelList.size();
    }

}
