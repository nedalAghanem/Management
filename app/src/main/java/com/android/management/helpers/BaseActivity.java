package com.android.management.helpers;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.management.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.orhanobut.hawk.Hawk;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(getApplicationContext(),
                Hawk.get(Constants.LANGUAGE_TYPE, "ar"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocaleHelper.setLocale(getApplicationContext(),
                Hawk.get(Constants.LANGUAGE_TYPE, "ar"));
    }

    public Boolean isNotEmpty(TextInputEditText editText, TextInputLayout textInputLayout) {
        if (editText.getText().toString().trim().isEmpty()) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getString(R.string.empty_field));
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean isExistId(TextInputLayout textInputLayout, Boolean isExist) {
        if (isExist) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getString(R.string.isExist_id));
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean isExistEmail(TextInputLayout textInputLayout, Boolean isExist) {
        if (isExist) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getString(R.string.isExist_email));
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean isNotEmpty(AutoCompleteTextView editText, TextInputLayout textInputLayout) {
        if (editText.getText().toString().trim().isEmpty()) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getString(R.string.empty_field));
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public Boolean isNotEmpty(String text) {
        if (text.trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_fields), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public Boolean isImageNotEmpty(String text) {
        if (text.trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_image_path), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public Boolean isValidEmail(TextInputEditText editText, TextInputLayout textInputLayout) {
        if (Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()) {
            textInputLayout.setErrorEnabled(false);
            return true;
        } else {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(getString(R.string.invalid_email));
            return false;
        }
    }

    public Boolean isConfirmPassword(
            TextInputEditText editText1,
            TextInputLayout textInputLayout1,
            TextInputEditText editText2,
            TextInputLayout textInputLayout2
    ) {
        if (editText1.getText().toString().equals(editText2.getText().toString())) {
            textInputLayout1.setErrorEnabled(false);
            textInputLayout2.setErrorEnabled(false);
            return true;
        } else {
            textInputLayout1.setError(getString(R.string.password_not_match));
            textInputLayout2.setError(getString(R.string.password_not_match));
            textInputLayout1.setErrorEnabled(true);
            textInputLayout2.setErrorEnabled(true);
            return false;
        }
    }

    private String getText(TextInputEditText editText) {
        return editText.getText().toString().trim();
    }

}
