package net.DChore.DChoreApp;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<DataModel> dataSet;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewJob;
        TextView textViewAge;
        TextView textViewPlace;
        TextView textViewExperience;
        TextView textViewMobile;


        public MyViewHolder(final View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewJob = (TextView) itemView.findViewById(R.id.textViewJob);
            this.textViewAge = (TextView) itemView.findViewById(R.id.textViewAge);
            this.textViewPlace = (TextView) itemView.findViewById(R.id.textViewPlace);
            this.textViewExperience = (TextView) itemView.findViewById(R.id.textViewExperience);
            this.textViewMobile = (TextView) itemView.findViewById(R.id.textViewMobile);

            final Button btn_date=(Button) itemView.findViewById(R.id.date_btn);

            final Button btn_time=(Button) itemView.findViewById(R.id.time_btn);


            final Dialog dialog = new Dialog(itemView.getContext());
            dialog.setCanceledOnTouchOutside(true);

            ProfileActivity.time = null;

            ProfileActivity.date = null;


            btn_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.setContentView(R.layout.dialog_date);

                    dialog.show();
                    final Button ok=(Button)dialog.findViewById(R.id.dialog_ok);
                    Button cancel=(Button)dialog.findViewById(R.id.dialog_cancel);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final DatePicker dp=(DatePicker) dialog.findViewById(R.id.date_picker);

                            Log.d("Test", "clicked!!!");


                            String date = dp.getDayOfMonth() + "-" + dp.getMonth() + "-" + dp.getYear();

                            System.out.println("Clicked on Agree" +  date );

                            ProfileActivity.date = date;

                            btn_date.setText(date);
                            dialog.dismiss();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }

            });

            btn_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.setContentView(R.layout.dialog_time);

                    dialog.show();
                    Button ok=(Button)dialog.findViewById(R.id.dialog_ok);
                    Button cancel=(Button)dialog.findViewById(R.id.dialog_cancel);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("TEst", "clicked!!!");
                            final TimePicker timePicker=(TimePicker) dialog.findViewById(R.id.time_picker);
                            timePicker.setIs24HourView(false);
                            String time = getTime(Integer.parseInt(pad(timePicker.getHour())), Integer.parseInt(pad(timePicker.getMinute())));
                            System.out.println("Clicked on Agree: " + " time " + time + "name");
                            ProfileActivity.time = time;
                            btn_time.setText(time);
                            dialog.dismiss();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }

            });


        }
    }

    public CustomAdapter(ArrayList<DataModel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {



        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(ProfileActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewJob = holder.textViewJob;
        TextView textViewAge = holder.textViewAge;
        TextView textViewPlace = holder.textViewPlace;
        TextView textViewExperience = holder.textViewExperience;
        TextView textViewMobile = holder.textViewMobile;

        textViewName.setText(dataSet.get(listPosition).getName());
        textViewJob.setText(dataSet.get(listPosition).getJob());
        textViewAge.setText(String.valueOf(dataSet.get(listPosition).getAge()));
        textViewPlace.setText(dataSet.get(listPosition).getPlace());
        textViewExperience.setText(dataSet.get(listPosition).getExperience().toString());
        textViewMobile.setText(String.valueOf(dataSet.get(listPosition).getMobile()));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    public static void openDialog(Context ctx) {
        final Dialog dialog = new Dialog(ctx); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_date);
        dialog.setTitle(R.string.dialog_date);
        dialog.show();

    }

    public static String pad(int input) {
        //This will avoid 05:03 coming as 5:3 etc!
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }


    public static String getTime(int hour, int min) {
        String format = "";
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        return hour + " : " + min + " " + format;
    }
}
