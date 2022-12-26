package com.example.sixtapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.sixtapplication.model.Car;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Custom extends BaseAdapter {

    private ArrayList<Car> carArrayList;
    private Context context;
    private int layout;

    public Custom(ArrayList<Car> cars, Context activity, int instanceview) {

        this.carArrayList = cars;
        this.context = activity;
        this.layout = instanceview;
    }

    private class ViewHolder{

        TextView id, model_id, model_name, name, make, group, color, series, fuel_type, fuel_level, transmission,license,cleanliness;

        ImageView imageView;
    }

    @Override
    public int getCount() {
        return carArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return carArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder = new ViewHolder();
       LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       view = layoutInflater.inflate(layout, null);

       // Type Inversions

       viewHolder.id = view.findViewById(R.id.idtext);
       viewHolder.model_id = view.findViewById(R.id.model_id);
       viewHolder.model_name = view.findViewById(R.id.model_name);
       viewHolder.name = view.findViewById(R.id.name);
       viewHolder.make = view.findViewById(R.id.make);
       viewHolder.group = view.findViewById(R.id.group);
       viewHolder.color = view.findViewById(R.id.color);
       viewHolder.series = view.findViewById(R.id.series);
       viewHolder.fuel_level = view.findViewById(R.id.fuelLevel);
       viewHolder.fuel_type = view.findViewById(R.id.fuelType);
       viewHolder.transmission = view.findViewById(R.id.transmission);
       viewHolder.license = view.findViewById(R.id.licensePlate);
       viewHolder.cleanliness = view.findViewById(R.id.cleanliness);
       viewHolder.imageView = view.findViewById(R.id.imageView);

        Car car = carArrayList.get(i);
        viewHolder.id.setText(car.getId());
        viewHolder.model_id.setText(car.getModelIdentifier());
        viewHolder.model_name.setText(car.getModel_name());
        viewHolder.name.setText(car.getName());
        viewHolder.make.setText(car.getMake());
        viewHolder.group.setText(car.getGroup());
        viewHolder.color.setText(car.getColor());
        viewHolder.series.setText(car.getSeries());
        viewHolder.fuel_level.setText(car.getFuelLevel());
        viewHolder.fuel_type.setText(car.getFuelType());
        viewHolder.transmission.setText(car.getTransmission());;
        viewHolder.license.setText(car.getLicensePlate());;
        viewHolder.cleanliness.setText(car.getInnerCleanliness());;

        //Load the Image via Picasso
        Picasso.get()
                .load(car.getCarImageUrl())
                .into(viewHolder.imageView);


        return view;
    }
}
