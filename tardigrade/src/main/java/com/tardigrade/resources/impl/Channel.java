package com.tardigrade.resources.impl;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.tardigrade.comunication.IChannel;
import com.tardigrade.comunication.IPack;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Channel implements IChannel {
	private String name;
	
    private InetAddress host;
    private int port;
    private Socket channel;

    public Channel(String name, InetAddress host, int port) {
    	this.name = name;
    	this.host = host;
    	this.port = port;
    }

	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public String getAddress() {
		if(host != null) {
			return this.host.toString();
		}
		return null;
	}

	@SuppressLint("NewApi")
	@Override
	public void sendPack(IPack pack) {
		new Send().execute(pack);
	}

	@Override
	public int getPort() {
		return port;
	}

	@SuppressLint("NewApi")
	private class Send extends AsyncTask<IPack, Void, Void> {
		@Override
		protected Void doInBackground(IPack... params) {
			try {
				channel = new Socket(host, port);
				ObjectOutputStream outToServer = new ObjectOutputStream(channel.getOutputStream());
				outToServer.writeObject(params[0]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
