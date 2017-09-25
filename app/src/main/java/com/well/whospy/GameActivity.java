package com.well.whospy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import tardigrade.Tardigrade;
import tardigrade.comunication.IChannel;
import tardigrade.comunication.IPack;
import tardigrade.resources.impl.State;
import tardigrade.utils.ICallback;

public class GameActivity extends AppCompatActivity {

    Tardigrade game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game = Tardigrade.getInstance(null);

        game.mDiscovery.setOnFoundDevice(new ICallback() {
            @Override
            public void doit(IPack pack) {
                IChannel channel = (IChannel) pack.getValue();
                if(!game.gameInProgress){
                    game.mManager.registerObserver(channel);
                }
            }
        });
    }
}
