package singularity.walkineasy.fragments;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;


public class DateDialogFragment extends DialogFragment {

    public static String TAG = "DateDialogFragment";

    static Context sContext;
    static Calendar sDate;
    static int sWhoCalled;
    static DateDialogFragmentListener sListener;


    public static DateDialogFragment newInstance(Context context, int titleResource, Calendar date, int id) {
        DateDialogFragment dialog = new DateDialogFragment();

        sContext = context;
        sDate = Calendar.getInstance();
        sDate = date;
        sWhoCalled = id;


        Bundle args = new Bundle();
        args.putInt("title", titleResource);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(sContext, dateSetListener, sDate.get(Calendar.YEAR), sDate.get(Calendar.MONTH), sDate.get(Calendar.DAY_OF_MONTH));
    }


    public void setDateDialogFragmentListener(DateDialogFragmentListener listener) {
        sListener = listener;
    }


    private OnDateSetListener dateSetListener = new OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);

            //call back to the DateDialogFragment listener
            sListener.dateDialogFragmentDateSet(newDate, sWhoCalled);
        }
    };

    //---------------------------------------------------------
    //DateDialogFragment listener interface
    //---------------------------------------------------------
    public interface DateDialogFragmentListener {
        public void dateDialogFragmentDateSet(Calendar date, int whoCalled);
    }

}
