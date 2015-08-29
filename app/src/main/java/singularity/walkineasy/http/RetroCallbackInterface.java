package singularity.walkineasy.http;

import android.os.Bundle;

import retrofit.Callback;


public interface RetroCallbackInterface<T> extends Callback<T> {

    public int getRequestId();

    public int setRequestId(int code);

    public void cancel();

    public void setExtras(Bundle extras);

    public boolean isCanceled();

}