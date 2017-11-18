package lk.ac.mrt.cse.dbs.simpleexpensemanager;

import android.widget.Toast;

import java.util.List;

/**
 * Created by chathuranga on 11/18/2017.
 */

public class Popup {
    public static void printMsg(String msg) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(MyApplication.getAppContext(), msg, duration);
        toast.show();
    }
}
