package singularity.walkineasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * @author Sharath Pandeshwar
 * @since 29/08/15.
 */
public class BarcodeScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "BarcodeScannerActivity";
    public static final String INTENT_EXTRA_SCAN_RESULT = "intent_extra_scan_result";

    private ZXingScannerView mScannerView;
    private Bundle mBundle;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume

        Intent intent = getIntent();
        mBundle = intent.getExtras();
        if (mBundle == null) {
            mBundle = new Bundle();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

        mBundle.putString(INTENT_EXTRA_SCAN_RESULT, rawResult.getText());
        Intent i = new Intent();
        i.putExtras(mBundle);
        setResult(RESULT_OK, i);
        finish();
    }
}
