package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    private Button plusButton;
    private Button minusButton;
    private EditText firstNumberEditText;
    private EditText secondNumberEditText;
    private TextView resultTextView;

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        plusButton = (Button)findViewById(R.id.plus_button);
        minusButton = (Button)findViewById(R.id.minus_button);

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
            resultTextView.setText(Integer.toString(firstNumber) + " + " + Integer.toString(secondNumber) + " = " + Integer.toString(firstNumber + secondNumber));
        });

        minusButton.setOnClickListener(it -> {
            if (!isNumeric(firstNumberEditText.getText().toString()) || !isNumeric(secondNumberEditText.getText().toString())) {
                Toast.makeText(this, "Not a number", Toast.LENGTH_LONG).show();
                return;
            }

            int firstNumber = Integer.parseInt(firstNumberEditText.getText().toString());
            int secondNumber = Integer.parseInt(secondNumberEditText.getText().toString());
            resultTextView.setText(Integer.toString(firstNumber) + " - " + Integer.toString(secondNumber) + " = " + Integer.toString(firstNumber - secondNumber));
        });
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
}