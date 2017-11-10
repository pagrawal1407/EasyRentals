package com.example.csci567.easyrentals;

import android.app.Application;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CustomSwipeAdapter extends PagerAdapter {

    private static final String TAG = "CustomSwipeAdapter";
    private int[] imageResources = { R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private String licNumber;
    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(){}

    public CustomSwipeAdapter (Context ctx, String licNumber){

        this.ctx = ctx;
        this.licNumber = licNumber;
    }


    @Override
    public int getCount() {
        return imageResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.activity_custom_swipe_adapter, container, false);

       /* String[] imageURL = {"http://45.79.76.22/EasyRentals/EasyRentals/image/download" + "?fileName="+licNumber + "Exterior1",
                "http://45.79.76.22/EasyRentals/EasyRentals/image/download" + "?fileName="+licNumber + "Exterior2",
                "http://45.79.76.22/EasyRentals/EasyRentals/image/download" + "?fileName="+licNumber + "Exterior3",
                "http://45.79.76.22/EasyRentals/EasyRentals/image/download" + "?fileName="+licNumber + "Exterior4"};
*/

        String[] imageURL = {"https://tse2.mm.bing.net/th?id=OIP.UXsoNA9L9hfcoshdd2rt1wCgCg&pid=15.1",
                "https://pbs.twimg.com/profile_images/1213713191/SigmaCareSphere_bigger.gif",
                "http://farm8.staticflickr.com/7008/6823023423_0230c36c23_m.jpg",
                "https://pbs.twimg.com/profile_images/446074168/power_T_hockey_bigger.jpg"};

        Log.d(TAG, "instantiateItem method ");
        ImageView imageView = new ImageView(ctx);
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(ctx);
        // Loader image - will be shown before loading image
        int loader = R.drawable.ic_action_search;
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView
        imgLoader.DisplayImage(imageURL[2], loader, imageView );

        //imageText.setText("Image: "+ position);
        container.addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);

    }

}
