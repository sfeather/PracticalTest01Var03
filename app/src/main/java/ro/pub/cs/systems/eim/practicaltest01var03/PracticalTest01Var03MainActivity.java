package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    private Button plusButton;
    private Button minusButton;
    private Button navigateToSecondaryActivityButton;
    private EditText firstNumberEditText;
    private EditText secondNumberEditText;
    private TextView resultTextView;

    private boolean serviceHasStarted = false;

    private int result;

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[MessageBroadcastReceiver]", intent.getStringExtra("ro.pub.cs.systems.eim.practicaltest01.message"));
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        plusButton = (Button)findViewById(R.id.plus_button);
        minusButton = (Button)findViewById(R.id.minus_button);
        navigateToSecondaryActivityButton = (Button)findViewById(R.id.navigate_to_secondary_activity_button);

        firstNumberEditText = (EditText)findViewById(R.id.first_number_edit_text);
        secondNumberEditText = (EditText)findViewById(R.id.second_number_edit_text);

        resultTextView = (TextView)findViewById(R.id.result_text_view);

        plusButton.setOnClickListener(it -> {
            if (!isNumeric(firstNumberEditText.getText().toString()) || !isNumeric(secondNumberEditText.getText().toString())) {
                Toast.makeText(this, "Not a number", Toast.LENGTH_LONG).show();
                return;
            }

            int firstNumber = Integer.parseInt(firstNumberEditText.getText().toString());
            int secondNumber = Integer.parseInt(secondNumberEditText.getText().toString());

            result = firstNumber + secondNumber;

            resultTextView.setText(Integer.toString(firstNumber) + " + " + Integer.toString(secondNumber) + " = " + Integer.toString(result));

            startServicePracticalTest01();
        });

        minusButton.setOnClickListener(it -> {
            if (!isNumeric(firstNumberEditText.getText().toString()) || !isNumeric(secondNumberEditText.getText().toString())) {
                Toast.makeText(this, "Not a number", Toast.LENGTH_LONG).show();
                return;
            }

            int firstNumber = Integer.parseInt(firstNumberEditText.getText().toString());
            int secondNumber = Integer.parseInt(secondNumberEditText.getText().toString());

            result = firstNumber - secondNumber;

            resultTextView.setText(Integer.toString(firstNumber) + " - " + Integer.toString(secondNumber) + " = " + Integer.toString(result));

            startServicePracticalTest01();
        });

        navigateToSecondaryActivityButton.setOnClickListener(it -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
            intent.putExtra("result", result);
            startActivityForResult(intent, 1);
        });

        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.broadcastreceiver.sum");
        intentFilter.addAction("ro.pub.cs.systems.eim.practicaltest01.broadcastreceiver.difference");
    }

    private void startServicePracticalTest01() {
        int value1 = Integer.parseInt(firstNumberEditText.getText().toString());
        int value2 = Integer.parseInt(secondNumberEditText.getText().toString());

        if (serviceHasStarted) {
            return;
        }

        serviceHasStarted = true;

        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
        intent.putExtra("value1", value1);
        intent.putExtra("value2", value2);

        getApplicationContext().startService(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString("first_number", firstNumberEditText.getText().toString());
        savedInstanceState.putString("second_number", secondNumberEditText.getText().toString());
        savedInstanceState.putString("result_number", resultTextView.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState.containsKey("first_number")) {
            firstNumberEditText.setText(savedInstanceState.getString("first_number"));
        }

        if (savedInstanceState.containsKey("second_number")) {
            secondNumberEditText.setText(savedInstanceState.getString("second_number"));
        }

        if (savedInstanceState.containsKey("result_number")) {
            resultTextView.setText(savedInstanceState.getString("result_number"));
        }

        Toast.makeText(this, "First: " + savedInstanceState.getString("first_number") + " Second: " + savedInstanceState.getString("second_number") + " Result: " + savedInstanceState.getString("result_number"), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK)
                Toast.makeText(this, "CORRECT", Toast.LENGTH_LONG).show();
            else if (resultCode == RESULT_CANCELED)
                Toast.makeText(this, "INCORRECT", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "UNKNOWN", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, new IntentFilter("ro.pub.cs.systems.eim.practicaltest01.broadcastreceiver.sum"));
//        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        System.out.println("DESTROY");
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
        getApplicationContext().stopService(intent);
    }
}