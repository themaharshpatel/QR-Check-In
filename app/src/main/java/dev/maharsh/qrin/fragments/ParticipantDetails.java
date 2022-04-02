package dev.maharsh.qrin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import dev.maharsh.qrin.R;
import dev.maharsh.qrin.databinding.FragmentParticipantDetailsBinding;
import dev.maharsh.qrin.datamodel.ParticipantsDetailsModel;

public class ParticipantDetails extends BottomSheetDialogFragment {

    FragmentParticipantDetailsBinding binding;
    String registrationId;
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentParticipantDetailsBinding.inflate(inflater);
        ParticipantDetailsArgs args = ParticipantDetailsArgs.fromBundle(getArguments());
        registrationId = args.getRegId();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        navController = NavHostFragment
                .findNavController(
                        Objects.requireNonNull(
                                requireActivity()
                                        .getSupportFragmentManager()
                                        .findFragmentById(R.id.fragment_container)));

        DatabaseReference reference = database.getReference().child("Events").child(registrationId);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ParticipantsDetailsModel model = snapshot.getValue(ParticipantsDetailsModel.class);
                binding.progressBar.hide();
                if (model == null) {
                    Toast.makeText(requireContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                    navController.navigateUp();
                } else {
                    binding.mainGroup.setVisibility(View.VISIBLE);
                    if (model.isPresent()) {
                        binding.markPresenseBtn.setEnabled(false);
                    }
                    binding.name.setText(model.getName());
                    binding.emailId.setText(model.getEmail());
                    binding.regId.setText(String.valueOf(model.getRegId()));
                    binding.seatNo.setText(model.getSeatNo());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.markPresenseBtn.setOnClickListener(v -> reference.child("Present")
                .setValue(true).addOnCompleteListener(task -> {
                    Toast.makeText(requireContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                    v.setEnabled(false);
                }));

        binding.closeBtn.setOnClickListener(v -> navController.navigateUp());


    }
}