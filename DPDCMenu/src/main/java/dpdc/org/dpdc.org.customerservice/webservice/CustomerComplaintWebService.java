package dpdc.org.customerservice.webservice;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import dpdc.org.customerservice.model.CustomerComplaintData;

/* Async task class to get json by making HTTP call
 * */
public class CustomerComplaintWebService extends AsyncTask<Void, Void, Void> {

    private  static final String URL = "http://202.79.18.105:8081/ords/dpdc_cms/customer_complaint";
    private static final String TAG_COMPLAINTS = "items";
    private static final String TAG_COMPLAINT_ID = "complaint_id";
    private static final String TAG_COMPLAINT_TRACK_ID = "tracking_no";
    private static final String TAG_COMPLAINT_CUSTOMER_NO = "customer_no";
    private static final String TAG_COMPLAINT_DATE = "complaint_time";
    private static final String TAG_COMPLAINT_REMARKS = "remarks";
    private static final String TAG_COMPLAINT_STATUS = "status";
    private static final String TAG_COMPLAINT_FEEDBACK = "feedback";
    private static final String TAG_COMPLAINT_GANG_COMMENT = "gang_comment";
    private static final String TAG_COMPLAINT_TYPE = "complaint_type";
    private static final String TAG_COMPLAINT_RATING = "customer_ratings";

    private WeServiceExecutionEvent eventDelegate;
    private ArrayList<CustomerComplaintData> complaintArray;
    private String customerId;

    public void queryCustomerComplaints(String customerId,
                                        WeServiceExecutionEvent eventDelegate) {
        this.customerId = customerId;
        this.eventDelegate = eventDelegate;
        this.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(this.eventDelegate != null)
            this.eventDelegate.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        // Creating service handler class instance
        WebServiceHandler sh = new WebServiceHandler();
        complaintArray = new ArrayList<CustomerComplaintData>();

        String url = URL + "/" + customerId;
        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(url, WebServiceHandler.GET);

        //Log.d("Response: ", "> " + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray complaints = jsonObj.getJSONArray(TAG_COMPLAINTS);

                // looping through All Contacts
                for (int i = 0; i < complaints.length(); i++) {
                    JSONObject complaint = complaints.getJSONObject(i);
                    CustomerComplaintData complaintData = new CustomerComplaintData();
                    //complaintData.id = complaint.getString(TAG_COMPLAINT_ID);
                    //complaintData.remark = complaint.getString(TAG_COMPLAINT_REMARKS);
                    //complaintData.date = complaint.getString(TAG_COMPLAINT_DATE);
                    complaintData.trackNo = complaint.getString(TAG_COMPLAINT_TRACK_ID);
                    complaintData.complaintType = complaint.getString(TAG_COMPLAINT_TYPE);
                    complaintData.status = complaint.getString(TAG_COMPLAINT_STATUS);
                    complaintData.rating = complaint.getInt(TAG_COMPLAINT_RATING);
                    complaintArray.add(complaintData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("WeServiceHandler", "Couldn't get any data from the url");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if(this.eventDelegate != null)
            this.eventDelegate.onPostExecute(complaintArray);
    }

}
