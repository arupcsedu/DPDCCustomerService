package info.androidhive.slidingmenu.webservice;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import info.androidhive.slidingmenu.model.CustomerComplaintData;

/**
 * Created by Ayub on 12/5/2015.
 */
public class CustomerFeedbackWebService extends AsyncTask<String, Void, String> {

    private  static final String URL = "http://202.79.18.105:8081/ords/dpdc_cms/customer_feedback";
    private String trackNo;
    private String rating;
    private WeServiceExecutionEvent eventDelegate;
    private String response;

    public void submitCustomerFeedback(String trackNo, String rating,
                                        WeServiceExecutionEvent eventDelegate) {
        this.trackNo = trackNo;
        this.rating = rating;
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
    protected String doInBackground(String... urls) {

        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("ECUSTOMER_FEEDBACK", this.rating));
        pairs.add(new BasicNameValuePair("ETRACKING_NO", this.trackNo));

        WebServiceHandler serviceHandler = new WebServiceHandler();
        response = serviceHandler.makeServiceCall(URL, WebServiceHandler.POST, pairs);

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(this.eventDelegate != null)
            this.eventDelegate.onPostExecute(response);
    }
}
