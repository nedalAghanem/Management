package com.android.management.helpers;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.orhanobut.hawk.Hawk;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.setLocale(requireActivity(), Hawk.get(Constants.LANGUAGE_TYPE, "ar"));
    }

    @Override
    public void onResume() {
        super.onResume();
        LocaleHelper.setLocale(requireActivity(), Hawk.get(Constants.LANGUAGE_TYPE, "ar"));
    }
}
