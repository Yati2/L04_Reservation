package sg.edu.rp.c346.id20028056.l04_reservation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class MainActivity extends AppCompatActivity {
    EditText etName;
    EditText etPhone;
    EditText etGpSize;
    DatePicker dp;
    TimePicker tp;
    CheckBox cbConfirm;
    Button btnReserve;
    Button btnReset;
    RadioGroup rgSmoke;
    RadioButton rbSmoke;
    RadioButton rbNoSmoke;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bindings
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etGpSize = findViewById(R.id.etGpSize);
        dp = findViewById(R.id.dp);
        tp = findViewById(R.id.tp);
        cbConfirm = findViewById(R.id.cbConfirm);
        btnReserve = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);
        rgSmoke = findViewById(R.id.rgSmoke);
        rbNoSmoke = findViewById(R.id.rbNoSmoke);
        rbSmoke = findViewById(R.id.rbSmoke);

        setDefault();

        cbConfirm.setOnClickListener(new View.OnClickListener() {
            String msg;

            @Override
            public void onClick(View view) {

                if (!(etName.length() == 0 || etPhone.length() == 0 || etGpSize.length() == 0)) {
                    String date = dp.getDayOfMonth() + "/" + dp.getMonth() + "/" + dp.getYear();
                    String time = tp.getCurrentHour() + ":" + tp.getCurrentMinute();
                    String smoke;
                    int isSmoke = rgSmoke.getCheckedRadioButtonId();
                    if (isSmoke == R.id.rbSmoke) {
                        smoke = "Smoking Area";
                    } else {
                        smoke = "No-smoking Area";
                    }
                    msg = String.format("%s,%s people on %s at %s,%s.Phone:%s", etName.getText()
                            , etGpSize.getText(), date, time, smoke, etPhone.getText());
                } else {
                    msg = "Please enter all the fields!!";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        btnReserve.setOnClickListener(new View.OnClickListener() {

            String msg;

            @Override
            public void onClick(View view) {
                if (cbConfirm.isChecked()) {

                } else {
                    msg = "Please make a confirmation!";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etName.setText("");
                etPhone.setText("");
                etGpSize.setText("");
                cbConfirm.setChecked(false);
                rgSmoke.clearCheck();
                setDefault();

            }
        });
        //challenges scenario 1
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker v, int hourOfDay, int minute) {

                Log.w("MyActivity", "inside time picker");
                if (tp.getCurrentHour() < 8 && tp.getCurrentMinute() > 00) {
                    Log.w("MyActivity", "earlier");
                    tp.setCurrentHour(8);
                    tp.setCurrentMinute(00);
                }
                if (hourOfDay > 20 && minute > 00) {
                    Log.w("MyActivity", "later");
                    v.setCurrentHour(20);
                    v.setCurrentMinute(0);
                }
            }
        });

        //challenges scenario 2
        dp.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker,int year, int monthOfYear, int dayOfMonth) {
                Log.w("MyActivity", "inside date picker");
                if(!(dp.getYear()<2022 && dp.getMonth()<4 && dp.getDayOfMonth()<12))
                {
                    Log.w("MyActivity", "not today");
                    dp.updateDate(2022,4,13);

                }
                else
                {
                    Log.w("MyActivity", "roll back");
                }

            }
        });
    }

    private void setDefault() {
        dp.updateDate(2022, 4, 13);
        tp.setCurrentHour(8);
        tp.setCurrentMinute(00);
    }


}