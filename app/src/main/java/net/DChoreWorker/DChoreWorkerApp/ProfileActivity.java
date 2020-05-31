package net.DChoreWorker.DChoreWorkerApp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewUsername, textViewCategory;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    public static String date, time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewCategory = (TextView) findViewById(R.id.categoryType);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewUsername.setText(user.getUsername());
        textViewCategory.setText(user.getCategory());




        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });



        myOnClickListener = new MyOnClickListener(this);



        if(user.getCategory().equals("Worker")) {

            recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());


            user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
            final String username = user.getUsername();



            TextView textViewBook = findViewById(R.id.textViewBook);

            textViewBook.setText("Please click to confirm!!");


            class BookedWorkerDatas extends AsyncTask<Void, Void, String> {
                ProgressBar progressBar;


                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    progressBar = (ProgressBar) findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);

                    try {
                        //converting response to json object
                        JSONObject obj = new JSONObject(s);

                        //if no error in response
                        if (obj.getBoolean("status")) {
                            Log.d("Status Message",obj.getString("status_message"));

                            JSONArray jsonArray = new JSONArray(obj.getString("users"));

                            ArrayList<BookedDataModel> data = new ArrayList<BookedDataModel>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject first = jsonArray.getJSONObject(i);
                                data.add(new BookedDataModel(
                                        first.getString("name"),
                                        first.getString("email"),
                                        first.getString("location"),
                                        first.getString("mobile"),
                                        first.getString("date"),
                                        first.getString("time"),
                                        first.getString("status")
                                ));
                            }

                            //0 for just retrieving first object you can loop it

                            //getting the user from the response
                            //JSONObject userJson = obj.getJSONObject("workers");

                            adapter = new BookedCustomAdapter(data);
                            recyclerView.setAdapter(adapter);


                        } else {
                            Toast.makeText(getApplicationContext(), obj.getString("status_message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error occurred while connecting to the server", Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }
                }

                @Override
                protected String doInBackground(Void... voids) {
                    //creating request handler object
                    RequestHandler requestHandler = new RequestHandler();

                    //creating request parameters
                    HashMap<String, String> params = new HashMap<>();
                    params.put("username", username);
                    System.out.println("Execution Happening");

                    //returing the response
                    return requestHandler.sendPostRequest(URLs.URL_WORKERS_BOOKING, params);
                }
            }
            BookedWorkerDatas bookedWorkerDatas = new BookedWorkerDatas();
            bookedWorkerDatas.execute();

        }
        else{

        }



    }



    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            Log.d("Status Message","clicked");


            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);

            TextView textViewMobile
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewMobile);

            Button date_btn
                    = (Button) viewHolder.itemView.findViewById(R.id.date_btn);

            Button time_btn
                    = (Button) viewHolder.itemView.findViewById(R.id.time_btn);


            final String selectedName = (String) textViewName.getText();
            final String selectedMobile = (String) textViewMobile.getText();
            final String date = (String) date_btn.getText();
            final String time = (String) time_btn.getText();



            AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            builder.setTitle("Confirm Work Item !");
            builder.setMessage("You are about to confirm this work for " + selectedName + " Do you want to confirm? or Press cancel to Cancel this work!");
            builder.setCancelable(false);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    User user;
                    user = SharedPrefManager.getInstance(context.getApplicationContext()).getUser();
                    final String username = user.getUsername();



                    class WorkerConfirm extends AsyncTask<Void, Void, String> {
                        ProgressBar progressBar;


                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressBar = (ProgressBar) findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.VISIBLE);

                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressBar = (ProgressBar) findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.GONE);

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(s);

                                //if no error in response
                                if (obj.getBoolean("status")) {
                                    Log.d("Status Message",obj.getString("status_message"));

                                    Toast.makeText(context.getApplicationContext(), obj.getString("status_message"), Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("status_message"), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(context.getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(context.getApplicationContext(), "Error occurred while connecting to the server", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }

                        @Override
                        protected String doInBackground(Void... voids) {
                            //creating request handler object
                            RequestHandler requestHandler = new RequestHandler();

                            //creating request parameters
                            HashMap<String, String> params = new HashMap<>();
                            params.put("username", username);
                            params.put("mobile", selectedMobile);
                            params.put("name", selectedName);
                            params.put("date", date);
                            params.put("time", time);
                            params.put("status", "Confirmed");

                            //returing the response
                            return requestHandler.sendPostRequest(URLs.URL_WORKER_BOOKING_CONFIRM, params);
                        }
                    }
                    new WorkerConfirm().execute();

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Toast.makeText(context.getApplicationContext(), "You've cancelled this work!", Toast.LENGTH_SHORT).show();

                    User user;
                    user = SharedPrefManager.getInstance(context.getApplicationContext()).getUser();
                    final String username = user.getUsername();



                    class WorkerConfirm extends AsyncTask<Void, Void, String> {
                        ProgressBar progressBar;


                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            progressBar = (ProgressBar) findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.VISIBLE);

                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            progressBar = (ProgressBar) findViewById(R.id.progressBar);
                            progressBar.setVisibility(View.GONE);

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(s);

                                //if no error in response
                                if (obj.getBoolean("status")) {
                                    Log.d("Status Message",obj.getString("status_message"));

                                    Toast.makeText(context.getApplicationContext(), obj.getString("status_message"), Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("status_message"), Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(context.getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }


                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);

                            } catch (JSONException e) {
                                Toast.makeText(context.getApplicationContext(), "Error occurred while connecting to the server", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }

                        @Override
                        protected String doInBackground(Void... voids) {
                            //creating request handler object
                            RequestHandler requestHandler = new RequestHandler();

                            //creating request parameters
                            HashMap<String, String> params = new HashMap<>();
                            params.put("username", username);
                            params.put("mobile", selectedMobile);
                            params.put("name", selectedName);
                            params.put("date", date);
                            params.put("time", time);
                            params.put("status", "Cancelled");


                            //returing the response
                            return requestHandler.sendPostRequest(URLs.URL_WORKER_BOOKING_CONFIRM, params);
                        }
                    }
                    new WorkerConfirm().execute();



                }
            });

            builder.show();

            Log.d("number", String.valueOf(selectedName) + ", Name: " +selectedName + "Date: " + date + "Time: " + time);
        }




        /*
        private void removeItem(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < WorkersData.nameArray.length; i++) {
                if (selectedName.equals(WorkersData.nameArray[i])) {
                    selectedItemId = WorkersData.mobileArray[i];
                }
            }
            removedItems.add(selectedItemId);
            data.remove(selectedItemPosition);
            adapter.notifyItemRemoved(selectedItemPosition);
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//
//        Log.d("check", "clicked!!");
//        User user;
//
//
//
//        user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
//        final String username = user.getUsername();
//
//        TextView textViewBook = findViewById(R.id.textViewBook);
//
//        textViewBook.setText("Please find your bookings below!!");
//
//
//        class BookedWorkerDatas extends AsyncTask<Void, Void, String> {
//            ProgressBar progressBar;
//
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
//
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.GONE);
//
//                try {
//                    //converting response to json object
//                    JSONObject obj = new JSONObject(s);
//
//                    //if no error in response
//                    if (obj.getBoolean("status")) {
//                        Log.d("Status Message",obj.getString("status_message"));
//
//                        JSONArray jsonArray = new JSONArray(obj.getString("workers"));
//
//                        ArrayList<BookedDataModel> data = new ArrayList<BookedDataModel>();
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject first = jsonArray.getJSONObject(i);
//                            data.add(new BookedDataModel(
//                                    first.getString("name"),
//                                    first.getString("job"),
//                                    first.getInt("age"),
//                                    first.getString("place"),
//                                    first.getInt("mobile"),
//                                    first.getDouble("experience"),
//                                    first.getString("date"),
//                                    first.getString("time")
//                            ));
//                        }
//
//                        //0 for just retrieving first object you can loop it
//
//                        //getting the user from the response
//                        //JSONObject userJson = obj.getJSONObject("workers");
//
//                        adapter = new BookedCustomAdapter(data);
//                        recyclerView.setAdapter(adapter);
//
//
//                    } else {
//                        Log.d("Status Message","Error Occured");
//
//                        //Toast.makeText(context.getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(), "Error Occured while connecting to the server", Toast.LENGTH_SHORT).show();
//
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                //creating request handler object
//                RequestHandler requestHandler = new RequestHandler();
//
//                //creating request parameters
//                HashMap<String, String> params = new HashMap<>();
//                params.put("username", username);
//                System.out.println("Execution Happening");
//
//                //returing the response
//                return requestHandler.sendPostRequest(URLs.URL_BOOKED_DATA, params);
//            }
//        }
//        BookedWorkerDatas bookedWorkerDatas = new BookedWorkerDatas();
//        bookedWorkerDatas.execute();
//
//
//        return true;
//    }

    /*

    private void addRemovedItemToList() {
        int addItemAtListPosition = 3;
        data.add(addItemAtListPosition, new DataModel(
                WorkersData.nameArray[removedItems.get(0)],
                WorkersData.versionArray[removedItems.get(0)],
                WorkersData.id_[removedItems.get(0)],
                WorkersData.drawableArray[removedItems.get(0)]
        ));
        adapter.notifyItemInserted(addItemAtListPosition);
        removedItems.remove(0);
    }
    */

}