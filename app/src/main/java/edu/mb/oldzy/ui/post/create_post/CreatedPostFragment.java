package edu.mb.oldzy.ui.post.create_post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import edu.mb.oldzy.R;
import edu.mb.oldzy.databinding.FragmentCreatedPostBinding;

public class CreatedPostFragment extends Fragment {
    private FragmentCreatedPostBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatedPostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnReturn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(v);
            navController.popBackStack(R.id.navigation_home, false);
            navController.navigate(R.id.navigation_home);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
