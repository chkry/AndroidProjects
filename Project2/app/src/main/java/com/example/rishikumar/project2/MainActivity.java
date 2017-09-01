package com.example.rishikumar.project2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.rishikumar.project2.R.id.rb1;


public class MainActivity extends AppCompatActivity {

    String buds;
    String exps;
    String bals;

    long no1=0;
    long no2=0;
    long no3=0;
    long no4=0;
    long bal=0;
    long sum=0;
    long bud;
    long exp=0;
    Boolean flag=false;
    Boolean r11=false;
    Boolean r12=false;
    Boolean r13=false;
    Boolean r14=false;


    String MyPREFERENCES = "MyPrefs" ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv4 = (TextView) findViewById(R.id.tv4);
        final EditText ed1 = (EditText) findViewById(R.id.ed1);
        final EditText ed2 = (EditText) findViewById(R.id.ed2);
        Button rset = (Button) findViewById(R.id.reset);

        final TextView p1 = (TextView) findViewById(R.id.p1);
        final TextView p2 = (TextView) findViewById(R.id.p2);
        final TextView p3 = (TextView) findViewById(R.id.p3);
        final TextView p4 = (TextView) findViewById(R.id.p4);

        final Context context = getApplicationContext();

        SharedPreferences sharedPref= getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        bals = sharedPref.getString("bals","");
        buds = sharedPref.getString("buds","");


        no1= sharedPref.getLong("no1",0);
        no2= sharedPref.getLong("no2",0);
        no3= sharedPref.getLong("no3",0);
        no4= sharedPref.getLong("no4",0);

        ed1.setText(buds);
        tv4.setText(bals);



        p1.setText(Long.toString(no1));
        p2.setText(Long.toString(no2));
        p3.setText(Long.toString(no3));
        p4.setText(Long.toString(no4));

        rset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                no1=0;
                no2=0;
                no3=0;
                no4=0;
                buds="";
                exps="";
                bals="";
                sum=0;
                ed1.setText("");
                ed2.setText("");
                tv4.setText("");
                p1.setText("0");
                p2.setText("0");
                p3.setText("0");
                p4.setText("0");
                getApplicationContext().getSharedPreferences(MyPREFERENCES, 0).edit().clear().commit();

            }
        });

        ed1.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    buds =ed1.getText().toString();
                    sum=no1+no2+no3+no4;
                    if(buds.length()>0) {

                        bud =  Long.parseLong( buds);
                        bal = bud - sum;
                        bals = Long.toString(bal);


                        tv4.setText(bals);
                    }

                    if((bud-sum)<0)
                    {
                        tv4.setTextColor(Color.rgb( 205, 62, 45));
                    }
                    else{tv4.setTextColor(Color.GREEN);}

                    return true;
                }

                return false;
            }
        });

        ed2.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {

                    buds =ed1.getText().toString();
                    sum=no1+no2+no3+no4;
                    if(buds.length()>0) {

                        bud = Long.parseLong(buds);
                        bal = bud - sum;
                        bals = Long.toString(bal);


                        tv4.setText(bals);
                    }

                    exps = ed2.getText().toString();
                    if(exps.length()>0){
                    exp = Long.parseLong(exps);
                   if(flag==true) {
                     if(r11==true){   no1 =no1 +exp; sum=no1+no2+no3+no4; p1.setText(Long.toString(no1));bal=bud-sum;tv4.setText(Long.toString(bal));ed2.setText("");
                         Toast toast = Toast.makeText(context,"Added "+exp+" Rupees to Food " , Toast.LENGTH_SHORT);
                         toast.show();}
                     else if(r12==true){no2 =no2 +exp; sum=no1+no2+no3+no4; p2.setText(Long.toString(no2));bal=bud-sum;tv4.setText(Long.toString(bal));ed2.setText("");
                         Toast toast = Toast.makeText(context,"Added "+exp+" Rupees to Study " , Toast.LENGTH_SHORT);
                         toast.show();}
                     else if(r13==true){ no3 =no3 +exp ; sum=no1+no2+no3+no4; p3.setText(Long.toString(no3));bal=bud-sum;tv4.setText(Long.toString(bal));ed2.setText("");
                         Toast toast = Toast.makeText(context,"Added "+exp+" Rupees to Entertainment " , Toast.LENGTH_SHORT);
                         toast.show();}
                     else if(r14==true){ no4 =no4 +exp; sum=no1+no2+no3+no4; p4.setText(Long.toString(no4));bal=bud-sum;tv4.setText(Long.toString(bal));ed2.setText("");
                         Toast toast = Toast.makeText(context,"Added "+exp+" Rupees to Travel " , Toast.LENGTH_SHORT);
                         toast.show();}


                   }
                   else{
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, "Please , Select a Category", Toast.LENGTH_SHORT);
                        toast.show();
                   }

                        if((bud-sum)<0)
                        {
                            tv4.setTextColor(Color.rgb( 205, 62, 45));
                        }
                        else{tv4.setTextColor(Color.GREEN);}


                        return true;

                }}

                return false;
            }
        });




    }

    public void onRadioButtonClicked(View view) {








        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case rb1:
                if (checked)

                    r12=false;
                    r13=false;
                    r14=false;

                    flag=true;
                    r11=true;

                break;
            case R.id.rb2:
                if (checked)
                    r11=false;

                     r13=false;
                     r14=false;
                    flag=true;
                    r12=true;
                break;
            case R.id.rb3:
                if (checked)
                    r11=false;
                    r12=false;

                     r14=false;
                    flag=true;
                     r13=true;
                break;
            case R.id.rb4:
                if (checked)
                    r11=false;
                    r12=false;
                    r13=false;

                    flag=true;
                    r14=true;
                break;
        }
        }







        @Override
        protected void onDestroy(){
            super.onDestroy();

    SharedPreferences sharedPref = getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPref.edit();

    editor.putString("buds", buds);
    editor.putString("bals", Long.toString(bal));
    editor.putLong("no1",no1);
    editor.putLong("no2",no2);
    editor.putLong("no3",no3);
    editor.putLong("no4",no4);

    editor.commit();



}






}


