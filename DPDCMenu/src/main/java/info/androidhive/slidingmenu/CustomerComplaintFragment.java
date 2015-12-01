package info.androidhive.slidingmenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.slidingmenu.R;

public class CustomerComplaintFragment extends Fragment {
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

    public CustomerComplaintFragment(){}
    Button customerSubBtn;
    View rootView;
    String custName;
    String custNum;
    String[] complaintNameArray = new String[100];
    int[] complaintIdArray =new int[100];
    int cArraySize;

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
        TextView lblAddr = (TextView) rootView.findViewById(R.id.customer_Address);

        lblNum.setText(custNum);
        lblName.setText(custName);
        lblAddr.setText(custAddr);
        lblAddr.setText(custAddr);


        customerSubBtn = (Button) rootView.findViewById(R.id.btnRegister);
        //Attaching the OnclickListener with the button.
        customerSubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating an Intent which will invoke
                //the other Activity (DynamicLayoutActivity).
                Intent intent = new Intent(rootView.getContext(),
                         NewComplaintActivity.class);
                intent.putExtra("customer_no", custNum);
                intent.putExtra(COMPLAINT_TYPE_NAME, complaintNameArray);
                intent.putExtra(COMPLAINT_TYPE_ID, complaintIdArray);
                intent.putExtra(COMPLAINT_TYPE_SIZE, cArraySize);
                //This method will start the other activity.
                  startActivity(intent);
            }
        });

        return rootView;
    }

}
