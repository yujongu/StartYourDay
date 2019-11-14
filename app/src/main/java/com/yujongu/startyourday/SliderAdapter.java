package com.yujongu.startyourday;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    ArrayList<CityInfo> copyCities;

    boolean deleted = false;

    public SliderAdapter(Context context, ArrayList<CityInfo> cities){
        this.context = context;
        this.copyCities = cities;
    }

    @Override
    public int getCount() {
        return copyCities.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.weather_layout, container, false);

        ImageButton searchCityBt = view.findViewById(R.id.btSearchCity);

        final TextView temp = view.findViewById(R.id.tvTemp);
        TextView desc = view.findViewById(R.id.tvDesc);
        TextView name = view.findViewById(R.id.tvCityName);
        final Button celBt = view.findViewById(R.id.btCel);
        final Button fehBt = view.findViewById(R.id.btFeh);
        ImageView weatherIcon = view.findViewById(R.id.ivWeather);

        final Button deleteBt = view.findViewById(R.id.btDeletePage);
        if (position == 0){
            deleteBt.setVisibility(View.GONE);
        }


        temp.setText(String.valueOf(copyCities.get(position).getCel()));
        desc.setText(copyCities.get(position).getDesc());
        name.setText(copyCities.get(position).getName());

        celBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp.setText(String.valueOf(copyCities.get(position).getCel()));

                celBt.setBackgroundResource(R.color.colorBlue);
                fehBt.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        fehBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp.setText(String.valueOf(copyCities.get(position).getFeh()));

                celBt.setBackgroundColor(Color.TRANSPARENT);
                fehBt.setBackgroundResource(R.color.colorBlue);
            }
        });
        searchCityBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchActivity.class);

                final int requestCode = 1;
                ((Activity)context).startActivityForResult(intent, requestCode);
            }
        });
        deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyCities.remove(position);
                notifyDataSetChanged();
            }
        });



        String imageUrl = "http://openweathermap.org/img/wn/";
        Picasso.get().load(imageUrl + copyCities.get(position).getImageId() + "@2x.png").into(weatherIcon);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
