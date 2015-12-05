package info.androidhive.slidingmenu;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.ComplaintListVewAdapter;
import info.androidhive.slidingmenu.model.CustomerComplaintData;
import info.androidhive.slidingmenu.webservice.CustomerComplaintWebService;
import info.androidhive.slidingmenu.webservice.WeServiceExecutionEvent;
import info.androidhive.slidingmenu.webservice.WebServiceHandler;

public class CustomerComplaintFragment extends Fragment implements WeServiceExecutionEvent {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String TAG_ITEMS = "items";
    private static final String CUST_ID = "cust_id";
    private static final String CUSTOMER_NAME = "customer_name";
    private static final String CUSTOMER_NUM = "customer_no";
    private static final String CUST_ADDR = "address";
    private static final String COMPLAINT_TYPE_NAME = "type_name";
    private static final String COMPLAINT_TYPE_ID = "type_id";
    private static final String COMPLAINT_TYPE_SIZE = "size";
    private static final String LOCATION_CODE = "location_code";
    private static final String AREA_CODE = "area_code";

    private static final String STATUS_RESOLVED = "Solved";


    Button customerSubBtn;
    String custName;
    String custNum;
    String[] complaintNameArray = new String[100];
    int[] complaintIdArray = new int[100];
    int cArraySize;

    private ArrayList<CustomerComplaintData> complaints;
    private ProgressDialog pDialog;
    private View rootView;
    private ComplaintListVewAdapter complaintListVewAdapter;
    private Menu optMenu;


    public CustomerComplaintFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(menu != null)
            menu.findItem(R.id.action_new_complaint).setVisible(true);
        optMenu = menu;
    }

    @Override
    public void onDestroyView() {
        if(optMenu != null)
            optMenu.findItem(R.id.action_new_complaint).setVisible(false);
        super.onDestroyView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // handle item selection

        switch (item.getItemId()) {

            case R.id.action_new_complaint:

                openNewComplaintActivity();

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //int custId = this.getArguments().getInt(CUST_ID);
        custNum = this.getArguments().getString(CUSTOMER_NUM);
        custName = this.getArguments().getString(CUSTOMER_NAME);
        String custAddr = this.getArguments().getString(CUST_ADDR);
        complaintNameArray = this.getArguments().getStringArray(COMPLAINT_TYPE_NAME);
        complaintIdArray =  this.getArguments().getIntArray(COMPLAINT_TYPE_ID);
        cArraySize = this.getArguments().getInt(COMPLAINT_TYPE_SIZE);

        rootView = inflater.inflate(R.layout.fragment_customer_complaint, container, false);

        // Displaying all values on the screen
        TextView lblNum = (TextView) rootView.findViewById(R.id.customer_no);
        TextView lblName = (TextView) rootView.findViewById(R.id.customer_name);
        TextView lblAddr = (TextView) rootView.findViewById(R.id.address);

        lblNum.setText(custNum);
        lblName.setText(custName);
        lblAddr.setText(custAddr);
        lblAddr.setText(custAddr);

        complaintListVewAdapter = new ComplaintListVewAdapter(this.getActivity(), complaints);
        ListView lvCustomerComplaint = (ListView)rootView.findViewById(R.id.list_customer_complaint);
        lvCustomerComplaint.setAdapter(complaintListVewAdapter);

        lvCustomerComplaint.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //String item = ((TextView)view).getText().toString();
               CustomerComplaintData item = (CustomerComplaintData)complaintListVewAdapter.getItem(position);
               if(item.rating == 0 && item.status.compareTo(STATUS_RESOLVED) == 0) {

                   CustomerFeedbackDialog feedbackDialog = new CustomerFeedbackDialog();
                   feedbackDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
                   //Bundle args = new Bundle();
                   //args.putString("track_no", item.trackNo);
                   //feedbackDialog.setArguments(args);
                   feedbackDialog.setTrackNo(item.trackNo);
                   feedbackDialog.show(getFragmentManager(), "CustomerFeedbackDialog");
               }
                else if (item.rating > 0)
                   Toast.makeText(rootView.getContext(), "You already rated it", Toast.LENGTH_SHORT).show();
                else
                   Toast.makeText(rootView.getContext(), "The complaint is not solved. You cannot rate it now", Toast.LENGTH_SHORT).show();
            }
        });

        CustomerComplaintWebService service = new CustomerComplaintWebService();
        service.queryCustomerComplaints(custNum, this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.i("CustomerComplaintFragment", "onResume");
    }

    @Override
    public void onPreExecute() {

        // Showing progress dialog
        pDialog = new ProgressDialog(rootView.getContext());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    public void onPostExecute(Object result) {
        // Dismiss the progress dialog
        if (pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }
        /**
         * Updating parsed JSON data into ListView
         * */

        complaintListVewAdapter.setData((ArrayList<CustomerComplaintData>)result);
        complaintListVewAdapter.notifyDataSetChanged();
    }

    private void openNewComplaintActivity() {
        //Creating an Intent which will invoke
        //the other Activity (DynamicLayoutActivity).
        Intent intent = new Intent(rootView.getContext(),
                NewComplaintActivity.class);
        intent.putExtra(CUSTOMER_NUM, custNum);
        intent.putExtra(COMPLAINT_TYPE_NAME, complaintNameArray);
        intent.putExtra(COMPLAINT_TYPE_ID, complaintIdArray);
        intent.putExtra(COMPLAINT_TYPE_SIZE, cArraySize);
        //This method will start the other activity.
        startActivity(intent);
    }
}
