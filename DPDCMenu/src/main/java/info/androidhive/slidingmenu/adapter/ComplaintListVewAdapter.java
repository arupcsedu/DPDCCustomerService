package info.androidhive.slidingmenu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.model.CustomerComplaintData;

/**
 * Created by ASUS on 11/28/2015.
 */
public class ComplaintListVewAdapter extends BaseAdapter {
    ArrayList<CustomerComplaintData> complaintList;
    Activity mainActivity;

    TextView txtTrackNo;
    TextView txtComplaintType;
    TextView txtStatus;

    public ComplaintListVewAdapter(Activity activity,
                                   ArrayList<CustomerComplaintData> list) {
        super();
        this.mainActivity = activity;
        this.complaintList = list;
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
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.complaint_list, null);
            txtTrackNo = (TextView) convertView.findViewById(R.id.txt_track_no);
            txtComplaintType = (TextView) convertView.findViewById(R.id.txt_complaint_type);
            txtStatus = (TextView) convertView.findViewById(R.id.txt_status);
        }

        CustomerComplaintData complaintData = complaintList.get(position);
        txtTrackNo.setText(complaintData.trackNo);
        txtComplaintType.setText(complaintData.complaintType);
        txtStatus.setText(complaintData.status);
        return convertView;
    }
}
