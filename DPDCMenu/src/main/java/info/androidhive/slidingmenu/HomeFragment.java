package info.androidhive.slidingmenu;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        /*Button feedback = (Button)rootView.findViewById(R.id.button);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedbackDialog(v);
            }
        });*/
        return rootView;
    }

    /*public void openFeedbackDialog(View view)
    {
        CustomerFeedbackDialog feedbackDialog = new CustomerFeedbackDialog();
        feedbackDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme);
        Bundle args = new Bundle();
        args.putInt("track_no", 1);
        feedbackDialog.setArguments(args);
        feedbackDialog.show(getFragmentManager(), "CustomerFeedbackDialog");
    }*/
}
