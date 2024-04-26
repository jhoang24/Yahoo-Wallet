package com.example.yahoowallet.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.yahoowallet.R;
import com.example.yahoowallet.authenticator.User;
import com.example.yahoowallet.components.ProgressButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddressActivity extends BaseActivity {

    private View continueButtonView;
    private TextInputLayout countryTextInputLayout, streetAddressTextInputLayout, unitTextInputLayout, cityTextInputLayout, stateTextInputLayout, postalCodeTextInputLayout;
    private TextInputEditText streetAddressTextInputEditText, unitTextInputEditText, cityTextInputEditText, postalCodeTextInputEditText;
    private AutoCompleteTextView countryAutoCompleteTextView, stateAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        continueButtonView = (View) findViewById(R.id.continueButton);
        TextView textView = (TextView) continueButtonView.findViewById(R.id.labelTextView);
        textView.setText(R.string.continue_button);

        countryTextInputLayout = (TextInputLayout) findViewById(R.id.countryTextInputLayout);
        streetAddressTextInputLayout = (TextInputLayout) findViewById(R.id.streetAddressTextInputLayout);
        unitTextInputLayout = (TextInputLayout) findViewById(R.id.unitTextInputLayout);
        cityTextInputLayout = (TextInputLayout) findViewById(R.id.cityTextInputLayout);
        stateTextInputLayout = (TextInputLayout) findViewById(R.id.stateTextInputLayout);
        postalCodeTextInputLayout = (TextInputLayout) findViewById(R.id.postalCodeTextInputLayout);
        streetAddressTextInputEditText = (TextInputEditText) findViewById(R.id.streetAddressTextInputEditText);
        unitTextInputEditText = (TextInputEditText) findViewById(R.id.unitTextInputEditText);
        cityTextInputEditText = (TextInputEditText) findViewById(R.id.cityTextInputEditText);
        postalCodeTextInputEditText = (TextInputEditText) findViewById(R.id.postalCodeTextInputEditText);
        countryAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.countryAutoCompleteTextView);
        stateAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.stateAutoCompleteTextView);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, COUNTRIES);
        countryAutoCompleteTextView.setAdapter(countryAdapter);
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, STATES);
        stateAutoCompleteTextView.setAdapter(stateAdapter);

        countryAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                countryAutoCompleteTextView.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        streetAddressTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                streetAddressTextInputEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        cityTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cityTextInputEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        stateAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                stateAutoCompleteTextView.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        postalCodeTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                postalCodeTextInputEditText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        continueButtonView.setOnClickListener(view -> {
            ProgressButton progressButton = new ProgressButton(AddressActivity.this, view);
            progressButton.buttonActivated();

            if (!isValidInfo()) {
                progressButton.buttonFinished();
                return;
            };

            User user = (User) getIntent().getSerializableExtra("USER");
            user.setCountry(countryAutoCompleteTextView.getText().toString())
                    .setAddress(streetAddressTextInputEditText.getText().toString())
                    .setCity(cityTextInputEditText.getText().toString())
                    .setState(stateAutoCompleteTextView.getText().toString())
                    .setPostalCode(postalCodeTextInputEditText.getText().toString());
            if (!unitTextInputEditText.getText().toString().isEmpty()) user.setApartmentNumber(unitTextInputEditText.getText().toString());
            user.postUser(postedUser -> { });

            new Runnable() {
                @Override
                public void run() {
                    progressButton.buttonFinished();
                }
            };

            Intent intent = new Intent(AddressActivity.this, AddCardActivity.class);
            intent.putExtra("USER", user);
            startActivity(intent);
        });
    }

    private String[] COUNTRIES = {
            "Afghanistan", "Akrotiri", "Albania", "Algeria", "American Samoa", "Andorra", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Ashmore and Cartier Islands", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Bassas de India", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswanna", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "British Virgin Islands", "Brunei", "Bulgaria", "Burkina Faso", "Burma", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Clipperton Island","Cocoas (Keeling) Islands", "Colombia", "Comoros", "Congo (Democratic Republic)", "Congo (Republic)", "Cook Islands", "Coral Sea Islands", "Costa Rica", "Cote d'lvoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Dhekelia", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Europa Island", "Falkland Islands (Islas Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "French Guinea", "French Polynesia", "French Southern and Antarctic Lands", "Gabon", "Gambia", "Gaza Strip", "Georgia", "Germany", "Ghana",
            "Gibraltar", "Glorioso Islands", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard Island and McDonald Islands", "Holy See (Vatican City)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Jamaica", "Jan Mayen", "Japan", "Jersey", "Jordan", "Juan de Nova Island", "Kazakhstan", "Kenya", "Kiribati", "Korea (North)", "Korea (South)", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia (Federated States)", "Moldova", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Namibia", "Nauru", "Navassa Island", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paracel Islands", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russia", "Rwanda", "Saint Helena",
            "Saint Kitts and Nevis", "Saint Lucia", "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia and Montenegro", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Spratly Islands", "Sri Lanka", "Sudan", "Suriname", "Svalbard", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tromelin Island", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands", "Wake Island", "Wallis and Futuna", "West Bank", "Western Sahara", "Yemen", "Zambia", "Zimbabwe"
    };

    private String[] STATES = {
            "AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"
    };

    private boolean isValidInfo() {
        return isValidAddress(countryAutoCompleteTextView.getText(), streetAddressTextInputEditText.getText().toString(), cityTextInputEditText.getText().toString(), stateAutoCompleteTextView.getText().toString(), postalCodeTextInputEditText.getText().toString());
    }

    private boolean isValidAddress(CharSequence country, CharSequence streetAddress, CharSequence city, CharSequence state, CharSequence postalCode) {
        if (TextUtils.isEmpty(country)) {
            countryTextInputLayout.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(streetAddress)) {
            streetAddressTextInputLayout.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(city)) {
            cityTextInputLayout.setError("Required");
            return false;
        }

        if (countryAutoCompleteTextView.getText().toString().equals("United States")) {
            if (TextUtils.isEmpty(state)) {
                stateTextInputLayout.setError("Required");
                return false;
            }

            if (TextUtils.isEmpty(postalCode)) {
                postalCodeTextInputLayout.setError("Required");
                return false;
            }
        }

        try {
            Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geoCoder.getFromLocationName(streetAddress + "," + city + "," + state + "," + postalCode + "," + country, 3);

            if (addresses.size() <= 0) {
                Toast.makeText(this, "Invalid Address", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}