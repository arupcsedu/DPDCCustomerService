package com.absouls.gangservice;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.absouls.gangservice.model.WebServiceHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GangMainActivity extends ActionBarActivity {

    private static String url = "http://202.79.18.105:8081/ords/dpdc_cms/customer/";
    private static String complaintUrl = "http://202.79.18.105:8081/ords/dpdc_cms/complaint_type";

    private ProgressDialog pDialog;
    // JSON Node names
    JSONArray complaintItems = null;
    private static final String TAG_ITEMS = "items";
    private static final String CUST_ID = "cust_id";
    private static final String CUSTOMER_NAME = "customer_name";
    private static final String CUSTOMER_NUM = "customer_no";
    private static final String CUST_ADDR = "address";
    private static final String COMPLAINT_TYPE_NAME = "type_name";
    private static final String COMPLAINT_TYPE_ID = "type_id";
    private static final String COMPLAINT_TYPE_SIZE = "size";
    private static final String TRACKING_NO = "tracking_no";
    private static final String GANG_ID = "gang_id";
    private static final String GANG_NAME = "gang_name";
    private static final String COMPLAINT_STATUS = "complaint_status";
    Button gangSubBtn;
    EditText gangNumText;

    String trackingNo;
    String customerNo;
    String customerName;
    String customerAddress;
    String complaintType;
    String gangId;
    String gangName;
    String complaintStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gang_main);

        gangSubBtn = (Button) findViewById(R.id.gangSubBtn);
        gangNumText = (EditText) findViewById(R.id.gang_id);

        //Attaching the OnclickListener with the button.
        gangSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating an Intent which will invoke
                //the other Activity (DynamicLayoutActivity).
                //  Intent intent = new Intent(getApplicationContext(),
                //         DynamicLayoutActivity.class);
                //This method will start the other activity.
                //  startActivity(intent);

                if (isConnectingToInternet()) {
                    // Calling async task to get json
                    Log.d("GangMainActivity", "> " + gangNumText.getText().length());
                    if (gangNumText.getText().length() > 4) {
                        hideKeyboard();
                        new GetCustomerInfo().execute();
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "Wrong Gang Number!! Please Enter Again.";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Log.e("GangMainActivity", "Error in Gang No");
                    }
                }
                else {

                    Context context = getApplicationContext();
                    CharSequence text = "Problem in Internet Connection!! Please Connect to the Internet and Try Again.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Log.e("GangMainActivity", "Error in Internet Connection");

                }
            }
        });
    }

    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gang_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideKeyboard() {
        Activity currentActivity = this;
        if(currentActivity != null) {
            InputMethodManager imm = (InputMethodManager) currentActivity.getSystemService(Context.
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentActivity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetCustomerInfo extends AsyncTask<Void, Void, Void> {

        String jsonStr;
        String complaintStr;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getApplicationContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            WebServiceHandler sh = new WebServiceHandler();

            Log.d("URL: ", "> " + url+gangNumText.getText());

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(url+gangNumText.getText(), WebServiceHandler.GET);
            complaintStr = sh.makeServiceCall(complaintUrl, WebServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    complaintItems = jsonObj.getJSONArray(TAG_ITEMS);
                    for(int i = 0; i<complaintItems.length();i++) {
                        JSONObject c = complaintItems.getJSONObject(i);

                        //id = Integer.parseInt(c.getString(CUST_ID));
                        customerNo = c.getString(CUSTOMER_NUM);
                        customerName = c.getString(CUSTOMER_NAME);
                        //location_code = c.getString(LOCATION_CODE);
                        // area_code = c.getString(AREA_CODE);
                        customerAddress = c.getString(CUST_ADDR);
                        trackingNo = c.getString(TRACKING_NO);
                        gangId = c.getString(GANG_ID);
                        gangName = c.getString(GANG_NAME);
                        complaintStatus = c.getString(COMPLAINT_STATUS);
                        complaintType = c.getString(COMPLAINT_TYPE_ID);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            // Log.d("CustomerValidationFragment", "Json List length " + customerItems.length());
            if(complaintItems!=null) {
                Bundle args = new Bundle();
                    //args.putInt(CUST_ID, id);
                args.putString(CUSTOMER_NUM, customerNo);
                args.putString(CUSTOMER_NAME, customerName);
                args.putString(CUST_ADDR, customerAddress);
                args.putString(TRACKING_NO, trackingNo);
                args.putString(GANG_ID, gangId);
                args.putString(GANG_NAME, gangName);
                args.putString(COMPLAINT_STATUS, complaintStatus);
                args.putString(COMPLAINT_TYPE_ID, complaintType);
            }
            else
            {
                Context context = getApplicationContext();
                CharSequence text = "Wrong Gang Number!! Please Enter Again.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Log.e("GangMainActivity", "Error in Parsing Data");
            }
        }

    }

}
