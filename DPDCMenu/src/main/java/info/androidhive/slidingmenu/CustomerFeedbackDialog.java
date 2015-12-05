package info.androidhive.slidingmenu;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import info.androidhive.slidingmenu.webservice.CustomerFeedbackWebService;
import info.androidhive.slidingmenu.webservice.WeServiceExecutionEvent;

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

        rootView = inflater.inflate(R.layout.customer_feedback_dialog, container, false);
        getDialog().setTitle("Customer Rating");
        Button btnSubmit = (Button) rootView.findViewById(R.id.btn_submit_rating);
        ratingBar = (RatingBar)rootView.findViewById(R.id.ratingbar_complaint);
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
