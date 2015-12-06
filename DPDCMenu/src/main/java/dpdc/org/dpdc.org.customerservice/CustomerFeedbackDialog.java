package dpdc.org.customerservice;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import dpdc.org.customerservice.webservice.CustomerFeedbackWebService;

/**
 * Created by ASUS on 12/4/2015.
 */
public class CustomerFeedbackDialog extends DialogFragment {

    private RatingBar ratingBar;
    private String trackNo;
    private ProgressDialog pDialog;
    private CustomerFeedbackDialog thisDialog;
    private View rootView;

    public CustomerFeedbackDialog() {
        thisDialog = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(dpdc.org.customerservice.R.layout.customer_feedback_dialog, container, false);
        getDialog().setTitle("Customer Rating");
        Button btnSubmit = (Button) rootView.findViewById(dpdc.org.customerservice.R.id.btn_submit_rating);
        ratingBar = (RatingBar)rootView.findViewById(dpdc.org.customerservice.R.id.ratingbar_complaint);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize((float)1.0);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int rating = (int)ratingBar.getRating();

            if(rating == 0) {
                Toast.makeText(rootView.getContext(), "Please enter your rating", Toast.LENGTH_SHORT).show();
                return;
            }

            CustomerFeedbackWebService feedbackService = new CustomerFeedbackWebService();
            feedbackService.submitCustomerFeedback(trackNo, Integer.toString(rating), null);

            Toast.makeText(rootView.getContext(), "Thank you for your rating. Please refresh to see the update.", Toast.LENGTH_SHORT).show();

            thisDialog.getDialog().dismiss();


            }
        });

        return rootView;
    }

    public void setTrackNo(String trackNo)
    {
        this.trackNo = trackNo;
    }

}
