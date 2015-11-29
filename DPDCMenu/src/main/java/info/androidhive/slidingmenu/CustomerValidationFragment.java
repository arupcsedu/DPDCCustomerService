package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.DialogFragment;

import android.widget.TextView;

import info.androidhive.slidingmenu.model.CustomerComplaintFragment;


public class CustomerValidationFragment extends Fragment {

    public CustomerValidationFragment(){}

    Button customerSubBtn;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.customer_check, container, false);
        customerSubBtn = (Button) rootView.findViewById(R.id.customerSubBtn);
        //Attaching the OnclickListener with the button.
        customerSubBtn.setOnClickListener(new View.OnClickListener() {
            Fragment customerCheckFragment = null;
            @Override
            public void onClick(View view) {
                //Creating an Intent which will invoke
                //the other Activity (DynamicLayoutActivity).
              //  Intent intent = new Intent(getApplicationContext(),
               //         DynamicLayoutActivity.class);
                //This method will start the other activity.
              //  startActivity(intent);
                customerCheckFragment = new CustomerComplaintFragment();

                if (customerCheckFragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, customerCheckFragment).commit();

                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }
            }
        });
        return rootView;
    }

}
