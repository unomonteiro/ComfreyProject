package io.monteirodev.comfreyproject.ui.about;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.monteirodev.comfreyproject.R;
import io.monteirodev.comfreyproject.data.AboutDetail;
import io.monteirodev.comfreyproject.data.Detail;
import io.monteirodev.comfreyproject.ui.DetailsAdapter;

public class AboutActivity extends AppCompatActivity implements DetailsAdapter.DetailClickListener {
    private static final String TAG = AboutActivity.class.getSimpleName();

    @BindView(R.id.about_recycler_view)
    RecyclerView mRecyclerView;

    private DetailsAdapter mDetailsAdapter;
    private int mClicks = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        setupFirebase();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDetailsAdapter = new DetailsAdapter(this);
        mRecyclerView.setAdapter(mDetailsAdapter);

        setupViewModel();
    }



    private void setupFirebase() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_LOW));
            }
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]
    }

    private void setupViewModel() {
        AboutViewModel model =
                ViewModelProviders.of(this).get(AboutViewModel.class);
        model.getDetails().observe(this, new Observer<List<AboutDetail>>() {
            @Override
            public void onChanged(@Nullable List<AboutDetail> details) {
                mDetailsAdapter.setDetailList(details);
            }
        });
    }



    @Override
    public void onClick(Detail detail) {
        if (mClicks == 0) {
            getToken();
            mClicks--;
        } else if (detail.getTitle().equalsIgnoreCase("about this app")) {
            if (mClicks < 5) {
                Toast.makeText(AboutActivity.this, mClicks + " clicks to ", Toast.LENGTH_SHORT).show();
            }
            mClicks--;
        }
    }

    public void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        shareToken(msg);
                    }
                });
    }

    private void shareToken(String token) {
        // https://stackoverflow.com/a/36188125/1329854
        ShareCompat.IntentBuilder
                // getActivity() or activity field if within Fragment
                .from(this)
                // The text that will be shared
                .setText(token)
                // most general text sharing MIME type
                .setType("text/plain")
                /*
                 * The title of the chooser that the system will show
                 * to allow the user to select an app
                 */
                .setChooserTitle("Don't share your key")
                .startChooser();
    }
}
