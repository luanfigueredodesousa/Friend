package com.example.friend;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;


public class Friend extends Activity {

    private EditText editTextHours;
    private EditText editTextMinutes;
    private Button buttonPlay;

    private DevicePolicyManager devicePolicyManager;
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, MyDeviceAdminReceiver.class);

        editTextHours = findViewById(R.id.editTextHours);
        editTextMinutes = findViewById(R.id.editTextMinutes);
        buttonPlay = findViewById(R.id.buttonPlay);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hours = Integer.parseInt(editTextHours.getText().toString());
                int minutes = Integer.parseInt(editTextMinutes.getText().toString());
                long timeInMillis = (hours * 3600 + minutes * 60) * 1000;

                if (devicePolicyManager.isAdminActive(componentName)) {
                    devicePolicyManager.lockNow();
                    setLockScreenTimeout(timeInMillis);
                } else {
                    Toast.makeText(Friend.this, "Você precisa ativar a administração do dispositivo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setLockScreenTimeout(long timeInMillis) {
        devicePolicyManager.setMaximumTimeToLock(componentName, timeInMillis);
    }
}
