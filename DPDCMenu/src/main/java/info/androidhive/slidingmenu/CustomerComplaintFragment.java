package info.androidhive.slidingmenu;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import info.androidhive.slidingmenu.R;

public class CustomerComplaintFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String TAG_ITEMS = "items";
    private static final String CUST_ID = "cust_id";
    private static final String CUSTOMER_NAME = "customer_name";
    private static final String CUSTOMER_NUM = "customer_no";
    private static final String CUST_ADDR = "address";
    private static final String LOCATION_CODE = "location_code";
    private static final String AREA_CODE = "area_code";

    public CustomerComplaintFragment(){}
    Button customerSubBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //int custId = this.getArguments().getInt(CUST_ID);
        String custNum = this.getArguments().getString(CUSTOMER_NUM);
        String custName = this.getArguments().getString(CUSTOMER_NAME);
        String custAddr = this.getArguments().getString(CUST_ADDR);
        View rootView = inflater.inflate(R.layout.fragment_customer_complaint, container, false);

        // Displaying all values on the screen
        TextView lblNum = (TextView) rootView.findViewById(R.id.customer_no);
        TextView lblName = (TextView) rootView.findViewById(R.id.customer_name);
        TextView lblAddr = (TextView) rootView.findViewById(R.id.customer_Address);

        lblNum.setText(custNum);
        lblName.setText(custName);
        lblAddr.setText(custAddr);
        lblAddr.setText(custAddr);

        return rootView;
    }

}
