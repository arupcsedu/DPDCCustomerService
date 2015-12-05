package info.androidhive.slidingmenu.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.model.CustomerComplaintData;

/**
 * Created by ASUS on 11/28/2015.
 */
public class ComplaintListVewAdapter extends ArrayAdapter {
    ArrayList<CustomerComplaintData> complaintList;
    Context context;
    Activity activity;

    public ComplaintListVewAdapter(Context context, int textViewResourceId,
                                   ArrayList<CustomerComplaintData> list, Activity activity) {

        super(context, textViewResourceId, list);
        this.context = context;
        this.complaintList = list;
        this.activity = activity;
    }

    public void setData(ArrayList<CustomerComplaintData> list) {
        this.complaintList = list;
    }

    @Override
    public int getCount() {
        if(complaintList == null)
            return 0;
        return complaintList.size();
    }

    @Override
    public Object getItem(int position) {
        if(complaintList == null)
            return null;
        return complaintList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater)activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.complaint_list, parent, false);
        }

        final TextView txtComplaintType = (TextView) convertView.findViewById(R.id.txt_complaint_type);
        final TextView txtStatus = (TextView) convertView.findViewById(R.id.txt_status);
        final RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
        final TextView txtTrackNo = (TextView) convertView.findViewById(R.id.txt_track_no);

        CustomerComplaintData complaintData = complaintList.get(position);
        txtTrackNo.setText(complaintData.trackNo);
        txtComplaintType.setText(complaintData.complaintType);
        txtStatus.setText(complaintData.status);

        if(complaintData.rating == 0) {
            ratingBar.setVisibility(View.INVISIBLE);
        }
        else {
            ratingBar.setRating((float) complaintData.rating);
            ratingBar.setVisibility(View.VISIBLE);
        }

        Log.i("Adapter", "Pos: "+position + " tackno: " + txtTrackNo.getText());

        return convertView;
    }
}
