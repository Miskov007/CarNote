package com.example.carnote;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carnote.model.AutoData;
import com.example.carnote.model.TankUpRecord;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GasTankUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
{

    public static final String NEW_TANKUP_RECORD = "NEW_TANKUP_RECORD";
    private static final String AUTO_DATA_OBJ = "AUTO_DATA_OBJ";
    private EditText dateEditText;
    private EditText mileageEditText;
    private TextView litersEditTextLabel;
    private EditText litersEditText;
    private TextView costEditTextLabel;
    private EditText costEditText;

    private Button confirmButton;
    private AutoData autoData;
    private DateFormat dateFormat;
    private TextView mileageEditTextLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gas_tank_up_layout);
        obtainExtras();
        if (savedInstanceState!=null)
        {
           autoData = (AutoData) savedInstanceState.get(AUTO_DATA_OBJ);
        }
        viewInit();
        setTitle(getResources().getString(R.string.new_tankup));
    }

    private void obtainExtras() {
        autoData = (AutoData) getIntent().getExtras().getSerializable(MainMenuActivity.SPECIAL_DATA);
    }

    private void viewInit() {
        dateEditText = (EditText) findViewById(R.id.date);
        mileageEditText = (EditText) findViewById(R.id.mileage);
        mileageEditTextLabel = (TextView) findViewById(R.id.mileage_label);

        litersEditText = (EditText) findViewById(R.id.liters);
        litersEditTextLabel = (TextView) findViewById(R.id.liters_label);
        costEditText = (EditText) findViewById(R.id.cost);
        costEditTextLabel = (TextView) findViewById(R.id.cost_label);
        confirmButton = (Button) findViewById(R.id.confirm);

        dateEditText.setText(getCurrentDate());
        dateEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(GasTankUpActivity.this, GasTankUpActivity.this, year, month, day);

                datePickerDialog.show();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (validateMileage())
                {
                    TankUpRecord tank = new TankUpRecord(getDateEditTextDate(), getMileageInteger(), getLitersInteger(), getCostInteger());
                    Intent intent = new Intent();
                    intent.putExtra(NEW_TANKUP_RECORD, tank);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        });

        mileageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                //Wyjscie z kontrolki
                if (hasFocus == false)
                {
                    validateMileage();
                }
            }
        });

        costEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {

                if (hasFocus == false)
                {
                    validateCost();
                }

            }
        });

        litersEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {

                if (hasFocus == false)
                {
                    validateLiters();
                }
            }
        });

    }

    private boolean validateLiters()
    {
        if (TextUtils.isEmpty(litersEditText.getText().toString()))
        {
            litersEditTextLabel.setText("Podaj ilosć dolanych litrów");
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }else if (Integer.valueOf(litersEditText.getText().toString()) <= 0)
        {
            litersEditTextLabel.setText("Dolane litry musza być wartością dodatnią");
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else

            {
            litersEditTextLabel.setText(getResources().getString(R.string.added_liters));
            litersEditTextLabel.setTextColor(getResources().getColor(R.color.black));
            return true;
            }
}

    private boolean validateCost()
    {
        if (TextUtils.isEmpty(costEditText.getText().toString()))
        {
            costEditTextLabel.setText("Podaj koszt");
            costEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }else if (Integer.valueOf(costEditText.getText().toString()) <= 0)
        {
            costEditTextLabel.setText("Podawaj zawsze dodatnią wartosć tankowania");
            costEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        } else

        {
            costEditTextLabel.setText(getResources().getString(R.string.tank_cost));
            costEditTextLabel.setTextColor(getResources().getColor(R.color.black));
            return true;
        }
    }

    private boolean validateMileage()
    {
        if (TextUtils.isEmpty(mileageEditText.getText().toString()))
        {
            mileageEditTextLabel.setText("Podaj przebieg");
            mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        if (Integer.valueOf(mileageEditText.getText().toString()) <=0)
        {
            mileageEditTextLabel.setText("Podaj wartość dodatnią przebiegu");
            mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
            return false;
        }
        int size = autoData.getTankUpRecord().size();
        if (autoData.getTankUpRecord().size()!=0)
            {
            Integer newMileage = Integer.valueOf(mileageEditText.getText().toString());
            Integer oldMileage = autoData.getTankUpRecord().get(size - 1).getMileage();
            if (newMileage <= oldMileage)
            {
                mileageEditTextLabel.setText("Wprowadzony przebieg jest równy lub mniejszy od wartości podanej w poprzendim tankowaniu, nie ma takiego cofania licznika!");
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.red));
                return false;
            }else
            {
                mileageEditTextLabel.setText(getResources().getString(R.string.mileage));
                mileageEditTextLabel.setTextColor(getResources().getColor(R.color.black));
                return true;
            }
        }
        return true;
    }

    private Date getDateEditTextDate()

    {

        try {
            return dateFormat.parse(dateEditText.getText().toString());
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return date;
    }

    private Integer getCostInteger() {
        return Integer.valueOf(costEditText.getText().toString());
    }

    private Integer getLitersInteger() {
        return Integer.valueOf(litersEditText.getText().toString());
    }

    private Integer getMileageInteger() {
        return Integer.valueOf(mileageEditText.getText().toString());
    }

    private String getCurrentDate()
    {
        dateFormat = DateFormat.getDateInstance();
        Date date = new Date();
        return dateFormat.format(date);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putSerializable(AUTO_DATA_OBJ, autoData);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        dateEditText.setText(dateFormat.format(calendar.getTime()));
    }
}
