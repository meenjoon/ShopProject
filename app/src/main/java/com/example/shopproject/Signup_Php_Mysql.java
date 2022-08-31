package com.example.shopproject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Signup_Php_Mysql extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    private static String IP_ADDRESS = "54.180.59.123";
    private static String TAG = "phptest";



    private TextView signup_id;
    private TextView signup_pwd;
    private TextView signup_pwd2;
    private TextView signup_name;
    private TextView signup_date;
    private Button signup_button;
    private TextView questtion;
    private ImageView back;
    private Spinner spinner;
    private EditText signup_phone;
    private ImageView imageView2;

    private TextView mTextViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.question, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        questtion = (EditText) findViewById(R.id.questtion);
        signup_id = (EditText) findViewById(R.id.signup_id);
        signup_pwd = (EditText) findViewById(R.id.signup_pwd);
        signup_pwd2 = (EditText) findViewById(R.id.signup_pwd2);
        signup_name = (EditText) findViewById(R.id.signup_name);
        signup_date = (EditText) findViewById(R.id.signup_date);
        signup_phone = (EditText) findViewById(R.id.signup_phone);
        signup_button = (Button) findViewById(R.id.signup_button);
        back = (ImageView) findViewById(R.id.back);
        imageView2 = (ImageView) findViewById(R.id.imageView2);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = signup_id.getText().toString().trim();
                String pwd = signup_pwd.getText().toString().trim();
                String pwdcheck = signup_pwd2.getText().toString().trim();
                String name = signup_name.getText().toString().trim();
                String date = signup_date.getText().toString().trim();
                String question = questtion.getText().toString().trim() ;
                String spinner1 = spinner.getSelectedItem().toString().trim();
                String phone = signup_phone.getText().toString().trim();


                if (email.equals("")  || pwd.equals("") || pwdcheck.equals("") || name.equals("")|| date.equals("") || phone.equals("") || spinner1.equals("- 질문을 선택해주세요 -") || question.equals("") )
                {
                    Toast.makeText(Signup_Php_Mysql.this, "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {

                    if(pwd.equals(pwdcheck)) {
                        if(pwd.length()<=5){
                            Toast.makeText(Signup_Php_Mysql.this, "비밀번호를 6자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(!email.contains("@") || !email.contains(".com")){

                            Toast.makeText(Signup_Php_Mysql.this, "아이디에 @ 및 .com을 포함시키세요.", Toast.LENGTH_SHORT).show();
                        }
                        else if(phone.contains("-") || !(Integer.parseInt(String.valueOf(phone.charAt(1)))==1)){

                            Toast.makeText(Signup_Php_Mysql.this,"올바른 전화번호 형식으로 입력해주세요..", Toast.LENGTH_SHORT).show();

                        }
                        else if(date.length()<=7 || Integer.parseInt(String.valueOf(date.charAt(0))) >= 3 || Integer.parseInt(String.valueOf(date.charAt(4)))>1
                                || Integer.parseInt(String.valueOf(date.charAt(5)))==0  || Integer.parseInt(String.valueOf(date.charAt(6)))>3
                                ||(Integer.parseInt(String.valueOf(date.charAt(6)))==3  && Integer.parseInt(String.valueOf(date.charAt(7)))>1
                                || (Integer.parseInt(String.valueOf(date.charAt(4)))==1 &&  Integer.parseInt(String.valueOf(date.charAt(5)))>2)
                                ||Integer.parseInt(String.valueOf(date.charAt(7)))==0)
                        ) {
                            Toast.makeText(Signup_Php_Mysql.this, "생년월일 8자 이상 및 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();

                        }
                        else if (date.length()<=7 || ( Integer.parseInt(String.valueOf(date.charAt(0)))==2  && Integer.parseInt(String.valueOf(date.charAt(1)))>0
                                && Integer.parseInt(String.valueOf(date.charAt(2)))>2  && Integer.parseInt(String.valueOf(date.charAt(3)))>2)|| Integer.parseInt(String.valueOf(date.charAt(6)))>3
                                || (Integer.parseInt(String.valueOf(date.charAt(6)))==3  && Integer.parseInt(String.valueOf(date.charAt(7)))>1)
                                ||(Integer.parseInt(String.valueOf(date.charAt(4)))==1 &&  Integer.parseInt(String.valueOf(date.charAt(5)))>2
                                ||Integer.parseInt(String.valueOf(date.charAt(7)))==0)
                        ) {
                            Toast.makeText(Signup_Php_Mysql.this, "생년월일 8자 이상 및 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            InsertData task = new InsertData();
                            task.execute("http://"+IP_ADDRESS+"/android_log_insert_php.php",email,email,pwd,phone,name,date,spinner1,question);
                            Toast.makeText(Signup_Php_Mysql.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                            finish();

                        }


                    }
                    else {
                        Toast.makeText(Signup_Php_Mysql.this, "비밀번호가 일치 하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }


                }


//                InsertData task = new InsertData();
//                task.execute("http://"+IP_ADDRESS+"/android_log_insert_php.php",email,email,pwd,phone,name,date,spinner1,question);
//                finish();


            }
        });


    }

    class InsertData extends AsyncTask<String,Void,String> {
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Signup_Php_Mysql.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {
            String useremail = (String)params[1];
            String userid = (String)params[2];
            String userpw = (String)params[3];
            String userphone = (String)params[4];
            String username = (String)params[5];
            String userdate = (String)params[6];
            String userquestion = (String)params[7];
            String useranswer = (String)params[8];

            String serverURL = (String) params[0];

            String postParameters ="useremail="+useremail+"&userid="+ userid
                    +"&userpw="+userpw+"&userphone="+userphone+"&username="+username
                    +"&userdate="+userdate+"&userquestion="+userquestion+"&useranswer="+useranswer;

            try{
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);

                httpURLConnection.setConnectTimeout(5000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                //전송할 데이터가 저장된 변수를 이곳에 입력합니다. 인코딩을 고려해줘야 합니다.

                Log.d("php 데이터 : ",postParameters);

                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code-" + responseStatusCode);

                InputStream inputStream;

                if(responseStatusCode == httpURLConnection.HTTP_OK){
                    inputStream=httpURLConnection.getInputStream();
                    Log.d("php정상: ","정상적으로 출력");
                }
                else {
                    inputStream = httpURLConnection.getErrorStream();
                    Log.d("php비정상: ","비정상적으로 출력");
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) !=null ) {
                    sb.append(line);
                }

                bufferedReader.close();

                Log.d("php 값 :", sb.toString());


                return  sb.toString();


            }

            catch (Exception e) {

                Log.d(TAG, "InsertData: Error",e);

                return  new String("Error " + e.getMessage());

            }

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}