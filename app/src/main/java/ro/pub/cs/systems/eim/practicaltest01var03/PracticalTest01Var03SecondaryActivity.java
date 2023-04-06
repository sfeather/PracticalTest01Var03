package ro.pub.cs.systems.eim.practicaltest01var03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    private Button correctButton;
    private Button incorrectButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        correctButton = (Button)findViewById(R.id.correct_button);
        incorrectButton = (Button)findViewById(R.id.incorrect_button);
        resultTextView = (TextView)findViewById(R.id.result_text_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int result = extras.getInt("result");

            resultTextView.setText(Integer.toString(result));
        }

        correctButton.setOnClickListener(it -> {
            setResult(RESULT_OK, null);
            finish();
        });

        incorrectButton.setOnClickListener(it -> {
            setResult(RESULT_CANCELED, null);
            finish();
        });
    }
}