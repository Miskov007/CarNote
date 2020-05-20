package com.example.carnote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carnote.model.AutoData;

/**
 * Okno dodania nowego auta
 */

public class AddCarActivity extends AppCompatActivity
{

    public static final String AUTO_DATA_NEW_CAR = "AUTO_DATA_NEW_CAR";
    public static final String IS_NEW_CAR_MASTER_CAR = "IS_NEW_CAR_MASTER_CAR";

    private EditText makeEditText;
    private EditText modelEditText;
    private EditText colorEditText;
    private Switch isMasterCarSwitch;
    private Button confirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car_layout);
        makeEditText = (EditText) findViewById(R.id.make_edittext);
        modelEditText = (EditText) findViewById(R.id.model_edittext);
        colorEditText = (EditText) findViewById(R.id.color_edittext);

        isMasterCarSwitch = (Switch) findViewById(R.id.master_car_switch);

        confirmButton = (Button) findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               AutoData autoData = new AutoData(modelEditText.getText().toString(), makeEditText.getText().toString(), colorEditText.getText().toString());
               Boolean isMasterCar = isMasterCarSwitch.isChecked();
               Intent intent = new Intent();
               intent.putExtra(AUTO_DATA_NEW_CAR, autoData);
               intent.putExtra(IS_NEW_CAR_MASTER_CAR, isMasterCar);
               setResult(Activity.RESULT_OK, intent);
               finish();
            }
        });
    }
}
