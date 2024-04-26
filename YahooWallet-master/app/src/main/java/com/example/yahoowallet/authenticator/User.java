package com.example.yahoowallet.authenticator;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class User implements Serializable {

    public interface UserCallback {
        void onSuccess(User user);
    }

    private final static String URL = "https://yw-backend.herokuapp.com";

    private String uid;

    private String email;
    private String password;

    private String firstName, lastName;

    private String address;
    private String state;
    private String postalCode;
    private String country;
    private String city;
    private String apartmentNumber;

    private String creditCard;
    private String expiration;
    private String cvv;

    public User() {
        this.uid = null;
    }

    public void postUser(UserCallback callback) {
        if (this.uid == null) {
            create(user1 -> {

            });
        }

        String postURL = URL + "/field?uid=" + this.uid;
        String fields = "";

        // At least one field exists
        if (this.email == null) return;

        if (this.email != null) fields += "&email=" + this.email;
        if (this.password != null) fields += "&password=" + this.password;
        if (this.firstName != null) fields += "&firstName=" + this.firstName;
        if (this.lastName != null) fields += "&lastName=" + this.lastName;
        if (this.address != null) fields += "&address=" + this.address;
        if (this.state != null) fields += "&state=" + this.state;
        if (this.postalCode != null) fields += "&postalCode=" + this.postalCode;
        if (this.country != null) fields += "&country=" + this.country;
        if (this.city != null) fields += "&city=" + this.city;
        if (this.apartmentNumber != null) fields += "&apartmentNumber=" + this.apartmentNumber;
        if (this.creditCard != null) fields += "&creditCard=" + this.creditCard;
        if (this.expiration != null) fields += "&expiration=" + this.expiration;
        if (this.cvv != null) fields += "&cvv=" + this.cvv;

        Log.d("YahooWallet", postURL + fields);

        RequestBody requestBody = RequestBody.create(null, new byte[0]);
        Request.Builder formBody = new Request.Builder().url(postURL + fields)
            .method("PUT", requestBody)
            .header("Content-Length", "0");
        OkHttpClient client = new OkHttpClient();
        client.newCall(formBody.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    callback.onSuccess(User.this);
                }
            }
        });
    }

    public void create(UserCallback callback) {
        RequestBody requestBody = RequestBody.create(null, new byte[0]);
        Request.Builder formBody = new Request.Builder().url(URL + "/user")
                .method("POST", requestBody)
                .header("Content-Length", "0");
        OkHttpClient client = new OkHttpClient();
        client.newCall(formBody.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody.string());
                        String path = jsonResponse.getString("path");
                        uid = path.substring(6);

                        callback.onSuccess(User.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public String getUid() {
        return this.uid;
    }

    public User setName(String name) {
        String lastName = "";
        String firstName= "";
        if (name.split("\\w+").length > 1) {
            lastName = name.substring(name.lastIndexOf(" ")+1);
            firstName = name.substring(0, name.lastIndexOf(' '));
        } else {
            firstName = name;
        }

        if (!lastName.isEmpty()) this.setLastName(lastName);
        if (!firstName.isEmpty()) this.setFirstName(firstName);

        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public User setState(String state) {
        this.state = state;
        return this;
    }

    public User setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public User setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
        return this;
    }

    public User setCreditCard(String creditCard) {
        this.creditCard = creditCard;
        return this;
    }

    public User setExpiration(Date expirationDate) {
        this.expiration = expirationDate.toString();
        return this;
    }

    public User setCvv(String cvv) {
        this.cvv = cvv;
        return this;
    }
}
