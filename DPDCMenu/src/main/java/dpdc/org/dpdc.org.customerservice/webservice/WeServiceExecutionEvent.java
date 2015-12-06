package dpdc.org.customerservice.webservice;

/**
 * Created by ASUS on 11/29/2015.
 */
public interface WeServiceExecutionEvent {
    public void onPreExecute();
    void onPostExecute(Object result);
}
