package net.DChoreWorker.DChoreApp;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class BookedCustomAdapter extends RecyclerView.Adapter<BookedCustomAdapter.MyViewHolder> {

    private ArrayList<BookedDataModel> dataSet;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewEmail;
        TextView textViewLocation;
        TextView textViewMobile;

        Button btn_date, btn_time;


        public MyViewHolder(final View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewEmail = (TextView) itemView.findViewById(R.id.textViewEmail);
            this.textViewLocation = (TextView) itemView.findViewById(R.id.textViewLocation);
            this.textViewMobile = (TextView) itemView.findViewById(R.id.textViewMobile);

            this.btn_date=(Button) itemView.findViewById(R.id.date_btn);

            this.btn_time=(Button) itemView.findViewById(R.id.time_btn);


            final Dialog dialog = new Dialog(itemView.getContext());
            dialog.setCanceledOnTouchOutside(true);


        }
    }

    public BookedCustomAdapter(ArrayList<BookedDataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {



        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewEmail = holder.textViewEmail;
        TextView textViewLocation = holder.textViewLocation;
        TextView textViewMobile = holder.textViewMobile;
        Button btn_date = holder.btn_date;
        Button btn_time = holder.btn_time;


        textViewName.setText(dataSet.get(listPosition).getName());
        textViewEmail.setText(dataSet.get(listPosition).getEmail());
        textViewLocation.setText(dataSet.get(listPosition).getLocation());
        textViewMobile.setText(String.valueOf(dataSet.get(listPosition).getMobile()));
        btn_date.setText(String.valueOf(dataSet.get(listPosition).getDate()));
        btn_time.setText(String.valueOf(dataSet.get(listPosition).getTime()));


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
