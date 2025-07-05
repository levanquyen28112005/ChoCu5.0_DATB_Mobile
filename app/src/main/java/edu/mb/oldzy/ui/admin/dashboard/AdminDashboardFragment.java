package edu.mb.oldzy.ui.admin.dashboard;

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
import edu.mb.oldzy.databinding.FragmentAdminDashboardBinding;
import edu.mb.oldzy.ui.admin.AdminActivity;
import edu.mb.oldzy.ui.admin.AdminTab;

public class AdminDashboardFragment extends Fragment {

    private FragmentAdminDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.cardViewUser.setOnClickListener(v -> {
            ((AdminActivity) requireActivity()).adminTab = AdminTab.USERS;
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_admin);
            navController.navigate(R.id.action_nav_admin_dashboard_to_nav_admin_users);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
