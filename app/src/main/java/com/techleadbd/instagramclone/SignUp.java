package com.techleadbd.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSave;
    private EditText edtName, edtAge, edtWeight, edtHeight, edtBatingSpeed;
    private TextView txtGetData;
    private Button btnGetAllData;
    private String allCricketers;
    private Button btnTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allCricketers = "";
        btnSave = findViewById(R.id.saveCricketerInfoToSarver);
        btnSave.setOnClickListener(SignUp.this);

        edtName = findViewById(R.id.edtCricketerUserName);
        edtAge = findViewById(R.id.edtCricketerAge);
        edtWeight = findViewById(R.id.edtCricketerWeight);
        edtHeight = findViewById(R.id.edtCricketerHeight);
        edtBatingSpeed = findViewById(R.id.edtCricketerBatingSpeed);
        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnTransition = findViewById(R.id.btnNextButton);


        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Cricketer");
                parseQuery.getInBackground("SBFwYK6iQ7", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object != null && e == null){
                            txtGetData.setText(object.get("cricketerName")+ "-" + "Bating speed is: "+object.get("bating_speed"));

                        }
                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("Cricketer");

                //queryAll.whereGreaterThan("cricketerAge", 34);
                queryAll.whereGreaterThanOrEqualTo("cricketerAge",70);
                queryAll.setLimit(1);
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if (e == null ){
                            if (objects.size() > 0){
                                for (ParseObject cricketer : objects){
                                    allCricketers = allCricketers + cricketer.get("cricketerName") + "\n";
                                }
                                FancyToast.makeText(SignUp.this,allCricketers, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                            }
                        }   else {
                            FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                        }
                    }
                });
            }
        });

        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignUpLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {

        try {
            final ParseObject cricketer = new ParseObject("Cricketer");
            cricketer.put("cricketerName", edtName.getText().toString());
            cricketer.put("cricketerAge", Integer.parseInt(edtAge.getText().toString()));
            cricketer.put("cricketerWeight", Integer.parseInt(edtWeight.getText().toString()));
            cricketer.put("cricketerHeight", Integer.parseInt(edtHeight.getText().toString()));
            cricketer.put("bating_speed", Integer.parseInt(edtBatingSpeed.getText().toString()));

            cricketer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUp.this, cricketer.get("cricketerName") + " is save server!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
                    }
                }
            });
        }catch (Exception e){
            FancyToast.makeText(SignUp.this,e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        }
    }


}
