package info.androidhive.slidingmenu;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * Created by ASUS on 12/4/2015.
 */
public class CustomerFeedbackDialog extends DialogFragment {

    RatingBar ratingBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.customer_feedback_dialog, container,
                false);
        getDialog().setTitle("Write Review");
        Button btnSubmit = (Button) rootView.findViewById(R.id.btn_submit_rating);
        ratingBar = (RatingBar)rootView.findViewById(R.id.ratingbar_complaint);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize((float)1.0);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //RatingBar ratingBar = (RatingBar) getActivity().findViewById(R.id.ratingbar_complaint);
                Toast.makeText(v.getContext(), "Rating: "+ ratingBar.getRating(), Toast.LENGTH_SHORT).show();
            }
        });

        // Do something else
        return rootView;
    }
}
