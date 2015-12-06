package dpdc.org.customerservice;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ASUS on 12/6/2015.
 */
public class ProgressBarHelper {
    private ProgressDialog progressDialog;
    private int showCount = 0;

    private static ProgressBarHelper ourInstance = new ProgressBarHelper();

    public static ProgressBarHelper getInstance() {
        return ourInstance;
    }

    private ProgressBarHelper() {
    }

    public void showProgressBar(Context context) {
        if(this.showCount == 0 || progressDialog == null ||
        !(progressDialog != null && !progressDialog.isShowing())) {
            showCount = 0;
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        this.showCount++;
    }

    public void hideProgressBar() {
        this.showCount--;
        if (progressDialog != null && progressDialog.isShowing() && this.showCount == 0) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
