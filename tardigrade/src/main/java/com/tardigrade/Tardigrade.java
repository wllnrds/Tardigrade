package com.tardigrade;

import android.app.Activity;
import android.content.Context;

import com.tardigrade.resources.impl.Deck;
import com.tardigrade.resources.impl.Discovery;
import com.tardigrade.resources.impl.Hub;
import com.tardigrade.resources.impl.Manager;
import com.tardigrade.resources.impl.Network;
import com.tardigrade.resources.impl.State;


public class Tardigrade {
    private static Tardigrade ourInstance = null;

    public static String [] CARD_ATTRIBUTES = { "attr_spy" };
    private Activity mContext = null;

    public Deck mDeck;
    public Hub mHub;
    public Discovery mDiscovery;
    public State mState;
    public Manager mManager;
    public Network mNetwork;

    public static Tardigrade getInstance(Activity context) {
        if(context != null){
            ourInstance = new Tardigrade(context);
        }
        return ourInstance;
    }

    private Tardigrade(Activity context) {
        mContext = context;

        mHub = Hub.getInstance(mContext);

        mDeck = Deck.getInstance(mContext);

        mNetwork = Network.getInstance();

        mDiscovery = Discovery.getInstance();
        mDiscovery.init(mNetwork);

        mState = State.getInstance();

        mManager = Manager.getInstance();

    }

    public void changeContext(Activity context){
        this.mContext = context;
    }

    public Context getContext(){
        return this.getContext();
    }
}
