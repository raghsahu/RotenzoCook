package com.cook.rotenzonew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.cook.rotenzonew.Model.BannerImage;
import com.cook.rotenzonew.R;
import com.cook.rotenzonew.Utils.URLs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.cook.rotenzonew.Utils.Base_Url.BaseUrl_slider;


public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<BannerImage> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<BannerImage> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

       // imageView.setImageResource(IMAGES.get(position));

        Picasso.with(context)
                .load(BaseUrl_slider + IMAGES.get(position).getImage())
                .into(imageView);

        view.addView(imageLayout, 0);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String news_url="https://next.mn/brand/rotenzo";

                if (!news_url.startsWith("http://") && !news_url.startsWith("https://")){
                    String  url = "http://" + news_url;

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(browserIntent);
                }else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(news_url));
                    context.startActivity(browserIntent);
                }

            }
        });


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
