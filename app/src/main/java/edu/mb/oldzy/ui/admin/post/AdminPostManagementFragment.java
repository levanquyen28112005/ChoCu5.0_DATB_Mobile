package edu.mb.oldzy.ui.admin.post;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import edu.mb.oldzy.R;
import edu.mb.oldzy.databinding.FragmentPostManagementBinding;
import edu.mb.oldzy.domain.model.PostResponse;

public class AdminPostManagementFragment extends Fragment {

    private FragmentPostManagementBinding binding;
    private AdminPostViewModel viewModel;
    private AdminPostAdapter adminPostAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AdminPostViewModel.class);
        binding = FragmentPostManagementBinding.inflate(inflater, container, false);
        viewModel.getPendingPosts().observe(getViewLifecycleOwner(), result -> {
            List<PostResponse> sorted;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                sorted = result.stream()
                        .sorted(Comparator.comparing(response ->
                                LocalDateTime.parse(((PostResponse) response).getCreatedAt(), DateTimeFormatter.ISO_DATE_TIME)
                        ).reversed())
                        .collect(Collectors.toList());
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                sorted = result.stream()
                        .sorted(Comparator.comparing(response -> {
                            try {
                                return sdf.parse(((PostResponse) response).getCreatedAt());
                            } catch (ParseException e) {
                                return new Date(0); // Nếu lỗi thì dùng ngày cũ nhất
                            }
                        }).reversed())
                        .collect(Collectors.toList());
            }
            if (result.isEmpty()) {
                binding.tvEmptyData.setVisibility(View.VISIBLE);
                binding.recyclerView.setVisibility(View.GONE);
            } else {
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.tvEmptyData.setVisibility(View.GONE);
                adminPostAdapter.setDataSource(sorted);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adminPostAdapter = new AdminPostAdapter(Collections.emptyList());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adminPostAdapter);

        SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
        String token = sharedPref.getString("access_token", null);
        viewModel.loadPostPending(token);

        binding.ivBack.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_admin);
            navController.popBackStack();
        });

        binding.layoutApproved.setOnClickListener(v -> {
            binding.viewAll.setVisibility(View.INVISIBLE);
            binding.viewApproved.setVisibility(View.VISIBLE);
            binding.viewRejected.setVisibility(View.INVISIBLE);
            binding.viewPending.setVisibility(View.INVISIBLE);

            viewModel.loadPostApproved(token);
        });
        binding.layoutPending.setOnClickListener(v -> {
            binding.viewAll.setVisibility(View.INVISIBLE);
            binding.viewApproved.setVisibility(View.INVISIBLE);
            binding.viewRejected.setVisibility(View.INVISIBLE);
            binding.viewPending.setVisibility(View.VISIBLE);
            viewModel.loadPostPending(token);
        });
        binding.layoutRejected.setOnClickListener(v -> {
            binding.viewAll.setVisibility(View.INVISIBLE);
            binding.viewApproved.setVisibility(View.INVISIBLE);
            binding.viewRejected.setVisibility(View.VISIBLE);
            binding.viewPending.setVisibility(View.INVISIBLE);
            viewModel.loadPostReject(token);
        });
        binding.layoutAll.setOnClickListener(v -> {
            binding.viewAll.setVisibility(View.VISIBLE);
            binding.viewApproved.setVisibility(View.INVISIBLE);
            binding.viewRejected.setVisibility(View.INVISIBLE);
            binding.viewPending.setVisibility(View.INVISIBLE);
            viewModel.loadPosts(token);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
