package com.trimit.android.net;

import android.util.Log;

import com.google.gson.Gson;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.trimit.android.model.LocationData;
import com.trimit.android.model.LoginData;
import com.trimit.android.model.createuser.AccountPostData;
import com.trimit.android.model.createuser.UserPostData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JsonUtils {
    Gson gson;
    private static final String TAG = "JsonUtils";

    public JsonUtils(Gson gson) {
        this.gson = gson;
    }

    public AccountPostData createAccount(String firstName, String lastName, String email, String password, String timestamp){
        AccountPostData account = new AccountPostData();
        account.setEmail(email);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setTimestamp(timestamp);
        account.setPassword(password);
        return account;
    }

    public String createUser(String firstName, String lastName, String email, String password, String gender, String dob, String barberTypeId){
        String formattedDob = formatDob(dob);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timestamp = format.format(Calendar.getInstance().getTime());
        Log.d(TAG, "createUser: timestamp =   "+timestamp);
        Log.d(TAG, "createUser: formattedDob =   "+formattedDob);
        AccountPostData account =createAccount(firstName,lastName,email,password, timestamp);
        UserPostData user = new UserPostData();
        user.setGender(gender);
        user.setDob(formattedDob);
        user.setBarberTypeId(barberTypeId);
        user.setLoyaltyPoints("0");
        user.setAccount(account);
        return gson.toJson(user);
    }

    private String formatDob(String dob) {
        SimpleDateFormat formatParse = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatFormat.format(formatParse.parse(dob));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLoginData(String email, String password){
        LoginData loginData = new LoginData(email,password);
        return gson.toJson(loginData);
    }

    public String getLocationData(LatLng point) {
        String result=gson.toJson(new LocationData(point));
        Log.d(TAG, "getLocationData: "+result);
        return result;
    }
}
