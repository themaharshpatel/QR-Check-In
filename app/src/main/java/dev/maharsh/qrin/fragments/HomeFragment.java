package dev.maharsh.qrin.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import dev.maharsh.qrin.R;
import dev.maharsh.qrin.ScannerActivity;
import dev.maharsh.qrin.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    NavController navController;

    ActivityResultLauncher<Intent> scanner = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                IntentResult result1 = IntentIntegrator.parseActivityResult(IntentIntegrator.REQUEST_CODE, result.getResultCode(), result.getData());
                if (result1.getContents() == null) {
                    Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    HomeFragmentDirections.ActionHomeFragmentToParticipantDetails action =
                            HomeFragmentDirections.actionHomeFragmentToParticipantDetails(result1.getContents());
                    navController.navigate(action);
                }
            });


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        navController = Navigation.findNavController(container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        IntentIntegrator intentIntegrator = new IntentIntegrator(requireActivity());
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setPrompt("Scan Barcode");
        intentIntegrator.setBarcodeImageEnabled(true);
        intentIntegrator.setCaptureActivity(ScannerActivity.class);
        binding.scanQr.setOnClickListener(v -> scanner.launch(intentIntegrator.createScanIntent()));


        binding.manualEntry.setOnClickListener(v -> {

            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext());
            View editText = View.inflate(requireContext(), R.layout.edit_text, null);
            editText.setPadding(convertDpToPixel(24, requireContext()), convertDpToPixel(18, requireContext()),
                    convertDpToPixel(24, requireContext()), convertDpToPixel(18, requireContext()));
            dialogBuilder.setView(editText);
            dialogBuilder.setTitle("Enter Registration Number");
            dialogBuilder.setPositiveButton("Go", (dialog, which) -> {
                String id = ((TextInputEditText)
                        editText.findViewById(R.id.registrationId_et))
                        .getEditableText().toString();
                if (!id.isEmpty()) {
                    HomeFragmentDirections.ActionHomeFragmentToParticipantDetails action =
                            HomeFragmentDirections.actionHomeFragmentToParticipantDetails(id);
                    navController.navigate(action);
                }
            });
            dialogBuilder.show();
        });


    }

    int convertDpToPixel(float dp, Context context) {
        return (int) (dp * context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


}