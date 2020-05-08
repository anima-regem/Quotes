package phone.vishnu.quotes.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

import phone.vishnu.quotes.R;

import static android.content.Context.MODE_PRIVATE;

public class BlankFragment extends Fragment {
    private TextView sourceCodeTV, feedbackTV;
    private Button resetButton;

    public BlankFragment() {
    }

    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_blank, container, false);
        sourceCodeTV = inflate.findViewById(R.id.aboutPageViewSourceCodeTextView);
        feedbackTV = inflate.findViewById(R.id.aboutPageFeedbackTextView);
        resetButton = inflate.findViewById(R.id.aboutResetSettingsButton);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sourceCodeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uriUrl = Uri.parse("https://github.com/VishnuSanal/Quotes");
                startActivity(new Intent(Intent.ACTION_VIEW, uriUrl));
            }
        });
        feedbackTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                composeEmail(new String[]{getActivity().getString(R.string.email_address_of_developer)}, "Feedback of " + getActivity().getString(R.string.app_name));

            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String COLOR_PREFERENCE_NAME = "colorPreference";
                String BACKGROUND_PREFERENCE_NAME = "backgroundPreference";
                String FIRST_RUN_BOOLEAN = "firstRunPreference";

                SharedPreferences.Editor editor = getActivity().getSharedPreferences("phone.vishnu.quotes.sharedPreferences", MODE_PRIVATE).edit();

                editor.putString(COLOR_PREFERENCE_NAME, "#5C5C5C");
                editor.putString(BACKGROUND_PREFERENCE_NAME, "-1");
                editor.putBoolean(FIRST_RUN_BOOLEAN, true);
                editor.apply();

                Toast.makeText(getActivity(), "Settings Reset.....\nRestart App for changes to take effect.....", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void composeEmail(String[] addresses, String subject) {


        File outputFile = new File(Environment.getExternalStorageDirectory(), "logcat.txt");
        try {
            Runtime.getRuntime().exec("logcat -f " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        //TODO:
//        intent.putExtra(Intent.EXTRA_STREAM, outputFile.getAbsolutePath());
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
