package com.fenchtose.eventbustest;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import de.greenrobot.event.EventBus;

public class MainActivity extends ActionBarActivity {

    private ActionBar actionBar;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        editText = (EditText)findViewById(R.id.edittext);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void buttonClicked(View v) {
        String text = editText.getText().toString();
        if (text.length() > 3) {
            // put to eventbus
            EventBus.getDefault().post(new MessageEvent(text));

        }
    }

    public void startServiceClicked(View v) {
        Intent intent = new Intent(this, ResponderService.class);
        startService(intent);
    }

    public void onEvent(ResponderService.ServiceMessageEvent event) {
        actionBar.setTitle(event.getMessage());
    }

    public class MessageEvent {
        private String message;

        public MessageEvent(String text) {
            message = text;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String msg) { message = msg;}
    }
}
