package capstone.conestoga.javafiles;

import android.app.Application;

/**
 * Created by Kailpuri on 8/3/2016.
 */
public class PizzaNPubApp extends Application {
    private static PizzaNPubApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized PizzaNPubApp getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
