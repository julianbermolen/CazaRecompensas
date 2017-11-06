package ar.com.cazarecompensas.cazarecompensas.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by julia on 6/11/2017.
 */

public class NotificationInstanceService extends FirebaseInstanceIdService {
    public static final String TAG = "NOTICIAS";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Token: "+ token);
    }
}
