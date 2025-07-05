package edu.mb.oldzy.ui.login;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import edu.mb.oldzy.R;
import edu.mb.oldzy.databinding.FragmentLoginBinding;
import edu.mb.oldzy.ui.admin.AdminActivity;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    private LoginViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvRegister.setOnClickListener(v -> {
            findNavController(binding.getRoot()).navigate(R.id.action_navigation_login_to_navigation_register);
        });
        binding.ivBack.setOnClickListener(v -> {
            findNavController(binding.getRoot()).popBackStack();
        });
        viewModel.getLoginResult().observe(getViewLifecycleOwner(), response -> {
            if (response != null && !TextUtils.isEmpty(response.getToken())) {
                String accessToken = "Bearer " + response.getToken();
                SharedPreferences sharedPref = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("access_token", accessToken);
                editor.apply();

                viewModel.getUserInfo(accessToken);
            } else {
                binding.layoutLoading.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        viewModel.getUserResult().observe(getViewLifecycleOwner(), userResponse -> {
            if (userResponse != null) {
                if ("admin".equals(userResponse.getRole())) {
                    Intent intent = new Intent(requireActivity(), AdminActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                } else {
                    Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
            } else {
                binding.layoutLoading.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString().trim();
            String password = binding.edtPassword.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6) {
                Toast.makeText(requireContext(), "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            binding.layoutLoading.setVisibility(View.VISIBLE);
            viewModel.login(email, password);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
