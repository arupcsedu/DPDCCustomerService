package dpdc.org.customerservice.model;

/**
 * Created by Ayub on 11/29/2015.
 */
public class CustomerComplaintData {
    public String id;
    public String trackNo;
    public String remark;
    public String status;
    public String feedback;
    public String gangComment;
    public String date;
    public String complaintType;
    public int    rating;

    @Override
    public String toString() {
       return this.trackNo;
    }
}
