package info.androidhive.slidingmenu.webservice;

/**
 * Created by arup on 12/1/15.
 */
public class ComplaintInfoService {
    private int complaint_id;
    private String tracking_no;
    private String customer_no;
    private String remarks;
    private int complaint_type;
    private String contact_no;

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public int getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(int complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getTracking_no() {
        return tracking_no;
    }

    public void setTracking_no(String tracking_no) {
        this.tracking_no = tracking_no;
    }

    public String getCustomer_no() {
        return customer_no;
    }

    public void setCustomer_no(String customer_no) {
        this.customer_no = customer_no;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getComplaint_type() {
        return complaint_type;
    }

    public void setComplaint_type(int complaint_type) {
        this.complaint_type = complaint_type;
    }
}