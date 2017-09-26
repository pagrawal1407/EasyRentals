package com.example.csci567.easyrentals;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import utility.DataPOJO;

/**
 * Created by Parag on 7/5/2017.
 */

public class ListViewAdapter extends BaseAdapter {

    ArrayList<DataPOJO> mlist = new ArrayList<DataPOJO>();
    Context mcontext;
    private android.os.Handler handler;
    static class ViewHolder {
        public TextView title;
        public TextView location;
        public TextView zipcode;
        public TextView year;
        public ImageView image;
    }

    public ListViewAdapter(ArrayList<DataPOJO> list, Context context) {
        this.mlist = list;
        this.mcontext = context;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_view_item, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) rowView.findViewById(R.id.carTitle);
            viewHolder.zipcode = (TextView) rowView.findViewById(R.id.zipcode);
            viewHolder.location = (TextView) rowView.findViewById(R.id.car_location);
            viewHolder.year = (TextView) rowView.findViewById(R.id.car_year);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.itemImage);
            rowView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();
        final DataPOJO data = mlist.get(position);
        if (!data.model.equals("") && !data.make.equals("")) {
            String title = data.make + " " + data.model;
            holder.title.setText(title);
        }

        if (data.year != 0) {
            String year = Integer.toString(data.year);
            holder.year.setText(year);
        }

        if (data.address.zipcode != 0) {
            String zipcode = Double.toString(data.address.zipcode);
            holder.zipcode.setText(zipcode);
        }

        if (data.address.geoLocation.getLatitude() != 0 && data.address.geoLocation.getLongitude() != 0) {
            Geocoder gc = new Geocoder(mcontext);
            List<Address> addList = null;
            try {
                addList = gc.getFromLocation(data.address.geoLocation.getLatitude(), data.address.geoLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address add = addList.get(0);
            String location = add.getLocality();
            holder.location.setText(location);

        }
        handler = new android.os.Handler();
        final String[] image = new String[1];
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                // RequestBody fileBody = RequestBody.create(MediaType.parse(content_type),bos.toByteArray());
                String URL = "http://45.79.76.22:9080/EasyRentals/image/download" + "?fileName="+data.drivingLicenseNumber;

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(URL)
                        .build();

                okhttp3.Response response = null;

                try {
                    response = client.newCall(request).execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    image[0] = jsonObject.optString("data","abc");

                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            byte[] decodedImage = Base64.decode(image[0], Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                            holder.image.setImageBitmap(decodedByte);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        t.start();

        return rowView;
    }
}
