package info.androidhive.slidingmenu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import info.androidhive.slidingmenu.webservice.CustomerComplaintWebService;
import info.androidhive.slidingmenu.webservice.WebServiceHandler;


public class CustomerValidationFragment extends Fragment {

    private static String url = "http://202.79.18.105:8081/ords/dpdc_cms/customer/";
    private static String complaintUrl = "http://202.79.18.105:8081/ords/dpdc_cms/complaint_type";

    private ProgressDialog pDialog;
    // JSON Node names

    private static final String TAG_ITEMS = "items";
    private static final String CUST_ID = "cust_id";
    private static final String CUSTOMER_NAME = "customer_name";
    private static final String CUSTOMER_NUM = "customer_no";
    private static final String CUST_ADDR = "address";
    private static final String COMPLAINT_TYPE_NAME = "type_name";
    private static final String COMPLAINT_TYPE_ID = "type_id";
    private static final String COMPLAINT_TYPE_SIZE = "size";
    private static final String TAG_PHONE_HOME = "home";
    private static final String TAG_PHONE_OFFICE = "office";

    int id;
    String name;
    String custNumber;
    String location_code;
    String area_code;
    String address;
    String complaintTypeName;
    int [] complaintTypeIdArray = new int[100];
    String[] typeNameArray = new String[100];
    int complaintArraySize;

    // contacts JSONArray
    JSONArray customerItems = null;
    JSONArray complaintItems = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> contactList;

    public CustomerValidationFragment(){}

    Button customerSubBtn;
    EditText customerNumText;
    View rootView;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.customer_check, container, false);
        customerSubBtn = (Button) rootView.findViewById(R.id.customerSubBtn);
        customerNumText = (EditText) rootView.findViewById(R.id.customer_id);

        //Attaching the OnclickListener with the button.
        customerSubBtn.setOnClickListener(new View.OnClickListener() {
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
                    Log.d("CustomerValidationFragment", "> " + customerNumText.getText().length());
                    if (customerNumText.getText().length() == 8) {
                        hideKeyboard();
                        new GetCustomerInfo().execute();
                    } else {
                        Context context = rootView.getContext();
                        CharSequence text = "Wrong Customer Number!! Please Enter Again.";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Log.e("CustomerValidationFragment", "Error in Mobile No");
                    }
                }
                else {

                    Context context = rootView.getContext();
                    CharSequence text = "Problem in Internet Connection!! Please Connect to the Internet and Try Again.";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    Log.e("CustomerValidationFragment", "Error in Internet Connection");

                }
            }
        });
        return rootView;
    }


    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) rootView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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
            /*pDialog = new ProgressDialog(rootView.getContext());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();*/

            ProgressBarHelper.getInstance().showProgressBar(rootView.getContext());
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            WebServiceHandler sh = new WebServiceHandler();

            Log.d("URL: ", "> " + url+customerNumText.getText());

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(url+customerNumText.getText(), WebServiceHandler.GET);
            complaintStr = sh.makeServiceCall(complaintUrl, WebServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    customerItems = jsonObj.getJSONArray(TAG_ITEMS);

                    JSONObject c = customerItems.getJSONObject(0);

                    //id = Integer.parseInt(c.getString(CUST_ID));
                    custNumber = c.getString(CUSTOMER_NUM);
                    name = c.getString(CUSTOMER_NAME);
                    //location_code = c.getString(LOCATION_CODE);
                   // area_code = c.getString(AREA_CODE);
                    address = c.getString(CUST_ADDR);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            //For Complaint Type

            if (complaintStr != null) {
                try {
                    JSONObject jsonComplaintObj = new JSONObject(complaintStr);

                    // Getting JSON Array node
                    complaintItems = jsonComplaintObj.getJSONArray(TAG_ITEMS);
                    int i;
                    for(i = 0 ;i<complaintItems.length();i++) {

                        JSONObject cc = complaintItems.getJSONObject(i);

                        //id = Integer.parseInt(c.getString(CUST_ID));
                        complaintTypeName = cc.getString(COMPLAINT_TYPE_NAME);
                        complaintTypeIdArray[i] = cc.getInt(COMPLAINT_TYPE_ID);
                        typeNameArray[i] = complaintTypeName;
                    }
                    complaintArraySize = complaintItems.length();
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
            /*if (pDialog.isShowing())
                pDialog.dismiss();*/

            //ProgressBarHelper.getInstance().hideProgressBar();

           // Log.d("CustomerValidationFragment", "Json List length " + customerItems.length());
            if(customerItems!=null) {
                Fragment customerCheckFragment = new CustomerComplaintFragment();

                if (customerCheckFragment != null) {

                    Bundle args = new Bundle();
                    //args.putInt(CUST_ID, id);
                    args.putString(CUSTOMER_NUM, custNumber);
                    args.putString(CUSTOMER_NAME, name);
                    args.putString(CUST_ADDR, address);
                    args.putStringArray(COMPLAINT_TYPE_NAME, typeNameArray);
                    args.putIntArray(COMPLAINT_TYPE_ID, complaintTypeIdArray);
                    args.putInt(COMPLAINT_TYPE_SIZE, complaintArraySize);

                    customerCheckFragment.setArguments(args);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, customerCheckFragment).commit();

                } else {
                    // error in creating fragment
                    Log.e("GetCustomerInfo", "Error in creating fragment");
                }
            }
            else
            {
                Context context = rootView.getContext();
                CharSequence text = "Wrong Customer Number!! Please Enter Again.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Log.e("GetCustomerInfo", "Error in Parsing Data");
            }
        }

    }

    private void hideKeyboard() {
        Activity currentActivity = this.getActivity();
        if(currentActivity != null) {
            InputMethodManager imm = (InputMethodManager) currentActivity.getSystemService(Context.
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentActivity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
