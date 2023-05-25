package cloud.dicsfeesono.androidsimplework.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import cloud.dicsfeesono.androidsimplework.BR;
import cloud.dicsfeesono.androidsimplework.R;
import cloud.dicsfeesono.androidsimplework.databinding.FragmentHomeBinding;
import cloud.dicsfeesono.androidsimplework.databinding.FragmentNotificationsBinding;

public class SettingFragment extends Fragment {
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        root.findViewById(R.id.setting_container).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ExampleSetting.class);
                        startActivityForResult(intent, 1);
                    }
                });
        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String newExampleText = data.getStringExtra("newExampleText");
            ((TextView)getView().findViewById(R.id.setting_example)).setText(newExampleText);
            // 更新绑定的视图
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}