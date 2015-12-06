package dpdc.org.customerservice.webservice;

import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

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
