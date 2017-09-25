package com.tardigrade.resources.impl;

import com.tardigrade.comunication.INetwork;
import com.tardigrade.utils.ICallback;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Network implements INetwork {
    private ServerSocket channel;
    private Thread receiver;

    private boolean isRunning;

    private ICallback onReceive;

    private static Network ourInstance = new Network();

    public static Network getInstance() {
        return ourInstance;
    }

    private Network() {
        isRunning = false;
        onReceive =  new NullCallback();
    }

    @Override
    public void start() {
        if(!isRunning) {
            try {
                channel = new ServerSocket(0);
                receiver = new Thread(new Receiver());
                receiver.start();
                isRunning = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        try {
            channel.close();
            isRunning = false;
            receiver.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPort() {
        if(isRunning){
            return this.channel.getLocalPort();
        }
        return -1;
    }

    @Override
    public void setOnReceivePack(ICallback callback){
        this.onReceive = callback;
    }

    @Override
    public boolean isWorking() {
        return isRunning;
    }

    private class Receiver extends Thread {
        @Override
        public void run() {
            while(isRunning) {
                try {
                    Socket sender = channel.accept();
                    ObjectInputStream streamedPack = new ObjectInputStream(sender.getInputStream());
                    Pack pack = (Pack) streamedPack.readObject();
                    onReceive.doit(pack);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
