package com.well.whospy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import tardigrade.Tardigrade;
import tardigrade.comunication.IChannel;
import tardigrade.comunication.IPack;
import tardigrade.utils.ICallback;

public class MainActivity extends AppCompatActivity {

    Tardigrade game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game = Tardigrade.getInstance(MainActivity.this);

        game.mDiscovery.setOnStartService(new ICallback() {
            @Override
            public void doit(IPack pack) {
                N("Startou!!!");
            }
        });

        game.mDiscovery.setOnFoundDevice(new ICallback() {
            @Override
            public void doit(IPack pack) {
                IChannel channel = (IChannel) pack.getValue();
                N("Achou algo" + channel.getName());
            }
        });

        game.mDiscovery.setOnLostDevice(new ICallback() {
            @Override
            public void doit(IPack pack) {
                IChannel channel = (IChannel) pack.getValue();
                N("Perdeu algo" + channel.getName());
            }
        });
        game.mDiscovery.setOnFailService(new ICallback() {
            @Override
            public void doit(IPack pack) {
                N("Deu ruim!");
            }
        });

        game.mDiscovery.start();
    }

    public void N(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
