package info.androidhive.slidingmenu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import info.androidhive.slidingmenu.webservice.ComplaintInfoService;
import info.androidhive.slidingmenu.webservice.WebServiceHandler;


public class NewComplaintActivity extends Activity implements OnItemSelectedListener {

    View rootView;
    private static String url = "http://202.79.18.105:8081/ords/dpdc_cms/complaint_type";
    private ProgressDialog pDialog;
    // JSON Node names

    private static final String TAG_ITEMS = "items";
    private static final String COMPLAINT_TYPE_ID = "type_id";
    private static final String COMPLAINT_TYPE = "type_name";
    int typePos;
    String contactNo;
    String cutomerRemarks;
    ComplaintInfoService complaintInfoService;

   // String type_id;
   // String typeName;


    // contacts JSONArray
    JSONArray complaintItems = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> complaintList;
    List<String> categories = new ArrayList<String>();
    private String[] typeList;
    EditText mobileText;
    EditText remarkText;
    String customerNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
            customerNo = bundle.getString("customer_no");
        setContentView(R.layout.activity_new_complaint);

        complaintList = new ArrayList<HashMap<String, String>>();

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
       // categories.add("Meter is not working");
      //  categories.add("Meter out of date");
       // categories.add("Line fuse down");
       // categories.add("Meter reading is not right");
        //categories.add("Bill is not matching with reading");
        //categories.add("Travel");
        typeList = getResources().getStringArray(R.array.complaint_type_array);
        for(int i = 0;i<typeList.length;i++)
            categories.add(typeList[i]);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        Button customerSubBtn = (Button) findViewById(R.id.newBtnRegister);
        mobileText = (EditText) findViewById(R.id.contact_no);
        remarkText = (EditText) findViewById(R.id.new_comment);
       // contactNo = mobileText.getText().toString();
       // cutomerRemarks = remarkText.getText().toString();;
        customerSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Calling async task to get json
                    Log.e("GetComplaintInfo", "POST Called");
                new HttpAsyncTask().execute("http://202.79.18.105:8081/ords/dpdc_cms/post_customer_complaint");
            }
        });
        //new ComplaintTypeInfo().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_complaint, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        typePos = position;

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    /**
     * Async task class to get json by making HTTP call
     * */

    public static String POST(String url, ComplaintInfoService complaintInfo){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("ECOMPLAINT_ID", complaintInfo.getComplaint_id());
            jsonObject.accumulate("ETRACKING_NO", complaintInfo.getTracking_no());
            jsonObject.accumulate("ECUSTOMER_NO", complaintInfo.getCustomer_no());
            jsonObject.accumulate("EREMARKS", complaintInfo.getRemarks());
            jsonObject.accumulate("ECOMPLAINT_TYPE", complaintInfo.getComplaint_type());
            jsonObject.accumulate("ECUSTOMER_MOBILE_NO", complaintInfo.getContact_no());
            //jsonObject.accumulate("complaint_type", "Meter is not working");
            //jsonObject.accumulate("status", "Pending");
            //jsonObject.accumulate("customer_name", "Arup Kumar Sarker");
            //jsonObject.accumulate("address", "PL-63, RD-04 ,AKOTA HOUSING ,ADABOR");
            //jsonObject.accumulate("remarks", "Meter problem");

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();
            Log.d("URL", url);
            Log.d("JSON String: ", json);

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
        //CMS_GET_TRACKING_NO
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            complaintInfoService =  new ComplaintInfoService();
            Random randomGenerator = new Random();
            complaintInfoService.setComplaint_id(randomGenerator.nextInt(999));
            complaintInfoService.setComplaint_type(typePos);
            complaintInfoService.setCustomer_no(customerNo);
            complaintInfoService.setTracking_no("T124357");
            complaintInfoService.setRemarks(remarkText.getText().toString());
            complaintInfoService.setContact_no(mobileText.getText().toString());

            return POST(urls[0], complaintInfoService);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


}
