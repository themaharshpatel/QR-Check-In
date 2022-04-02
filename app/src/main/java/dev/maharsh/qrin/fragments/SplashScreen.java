package dev.maharsh.qrin.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.maharsh.qrin.R;
import dev.maharsh.qrin.databinding.FragmentSplashScreenBinding;


public class SplashScreen extends Fragment {

    FragmentSplashScreenBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashScreenBinding.inflate(inflater);
        new Handler(Looper.myLooper()).postDelayed(()->{
            Navigation.findNavController(container).navigate(R.id.action_splashScreen_to_homeFragment);
        },1000);

        return binding.getRoot();
    }



}