package ro.pub.cs.systems.eim.practicaltest01var03;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private Random random = new Random();

    private int sum;
    private int difference;

    public ProcessingThread(Context context, int value1, int value2) {
        this.context = context;

        this.sum = value1 + value2;
        this.difference = value1 - value2;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage1();
            sleep();
            sendMessage2();
            sleep();

//            stopThread();
        }

//        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage1() {
        Intent intent = new Intent();
        intent.setAction("ro.pub.cs.systems.eim.practicaltest01.broadcastreceiver.sum");
        intent.putExtra("ro.pub.cs.systems.eim.practicaltest01.message", "sum: " + sum);
        context.sendBroadcast(intent);
    }

    private void sendMessage2() {
        Intent intent = new Intent();
        intent.setAction("ro.pub.cs.systems.eim.practicaltest01.broadcastreceiver.difference");
        intent.putExtra("ro.pub.cs.systems.eim.practicaltest01.message", "difference: " + difference);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
