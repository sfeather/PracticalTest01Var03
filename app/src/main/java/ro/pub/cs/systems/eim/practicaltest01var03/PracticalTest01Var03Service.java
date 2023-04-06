package ro.pub.cs.systems.eim.practicaltest01var03;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class PracticalTest01Var03Service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int value1 = intent.getIntExtra("value1", -1);
        int value2 = intent.getIntExtra("value2", -1);

        ProcessingThread processingThread = new ProcessingThread(this, value1, value2);
        processingThread.start();

        return Service.START_REDELIVER_INTENT;
    }
}