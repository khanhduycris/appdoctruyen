package edu.fpt.apptruyentranh;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import edu.fpt.apptruyentranh.data.Users;

public class LoginFragment extends Fragment {

    private TextView tvSingUp;
    private Button btnLogin;


    TextInputLayout textInputEmail;
    TextInputLayout textInputUser;
    TextInputLayout textInputPassWord;

    NavController mController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //POST API lên sever
    public class httpPostAPI extends AsyncTask<String, String, String>{
        public String email;
        public String user;
        public String passWord;

        public httpPostAPI(String email, String user, String passWord) {
            this.email = email;
            this.user = user;
            this.passWord = passWord;
        }

        @Override
        protected String doInBackground(String... strings) {
            String noiDung="";

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con= (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                JSONObject postData=new JSONObject();
                postData.put("email",email);
                postData.put("user",user);
                postData.put("passWord",passWord);
                con.setRequestProperty("Content-type","application/json");
                OutputStream outputStream=con.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream));
                writer.append(postData.toString());
                writer.flush();
                writer.close();
                outputStream.close();
                InputStream inputStream=con.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder builder=new StringBuilder();
                String dong;
                while ((dong =reader.readLine())!=null){
                    builder.append(dong).append("\n");
                }
                reader.close();
                inputStream.close();
                con.disconnect();
                noiDung=builder.toString();
                outputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return noiDung;
            }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new Gson();
            Users users = gson.fromJson(s, edu.fpt.apptruyentranh.data.Users.class);
            }
        }



        //Check Validate
    private boolean validateUser(){
        String userName = textInputUser.getEditText().getText().toString().trim();

        if (userName.isEmpty()){
            textInputUser.setError("Field can't be empty");
            return false;
        }else if (userName.length() >15){
            textInputUser.setError("username too long");
            return false;
        }else {
            textInputUser.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be empty");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
            textInputEmail.setError("Please enter a valid email anddress");
            return false;
        }else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassWord(){
        String passWordInPut = textInputPassWord.getEditText().getText().toString().trim();

        if (passWordInPut.isEmpty()){
            textInputPassWord.setError("Field can't be empty");
            return false;
        }else {
            textInputPassWord.setError(null);
            return true;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);

        tvSingUp = view.findViewById(R.id.tv_sing_up);
        tvSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mController.navigate(R.id.signUpFragment);
            }
        });

        btnLogin = view.findViewById(R.id.btn_log_in);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !validateEmail() ||!validateUser() || !validatePassWord()){
                    return;
                }
                String input = "Email: " + textInputPassWord.getEditText().getText().toString();
                input += "\n";
                input = "UserName: " + textInputUser.getEditText().getText().toString();
                input += "\n";
                input = "PassWord: " + textInputPassWord.getEditText().getText().toString();
                Toast.makeText(getActivity(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();

                String email = textInputEmail.getEditText().getText().toString();
                String user = textInputUser.getEditText().getText().toString();
                String passWord = textInputPassWord.getEditText().getText().toString();
                
                httpPostAPI httpPostAPI = new httpPostAPI(email, user, passWord);
                httpPostAPI.execute("https://6374892508104a9c5f822be9.mockapi.io/user");

                mController.navigate(R.id.danhSachTruyen);
            }
        });
        
        textInputEmail = view.findViewById(R.id.text_in_put_layout);
        textInputUser = view.findViewById(R.id.text_in_put_user);
        textInputPassWord = view.findViewById(R.id.text_in_put_pass_word);
    }

}