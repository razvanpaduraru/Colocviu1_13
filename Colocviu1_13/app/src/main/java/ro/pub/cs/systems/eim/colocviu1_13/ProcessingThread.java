package ro.pub.cs.systems.eim.colocviu1_13;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {
    private Context context;
    private boolean isRunning = true;

    private String textAfisat;
    private String data_si_ora;

    public ProcessingThread(Context context, String textAfisat, String data_si_ora) {
        this.context = context;
        this.textAfisat = textAfisat;
        this.data_si_ora = data_si_ora;
    }

    @Override
    public void run() {
        Log.d("[Thread Tag]", "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[Thread Tag]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction("threadText");
        intent.putExtra("message",
                data_si_ora + " si textul este : " + textAfisat);
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
