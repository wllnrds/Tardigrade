package tardigrade;

import android.app.Activity;
import android.content.Context;

import tardigrade.comunication.IDiscovery;
import tardigrade.comunication.IHub;
import tardigrade.comunication.IManager;
import tardigrade.comunication.INetwork;
import tardigrade.deck.IDeck;
import tardigrade.game.IState;
import tardigrade.resources.impl.Deck;
import tardigrade.resources.impl.Discovery;
import tardigrade.resources.impl.Hub;
import tardigrade.resources.impl.Manager;
import tardigrade.resources.impl.Network;
import tardigrade.resources.impl.State;


public class Tardigrade {
    private static Tardigrade ourInstance = null;
    public static String [] CARD_ATTRIBUTES = null;
    private Activity mContext = null;

    public IDeck mDeck;
    public IHub mHub;
    public IDiscovery mDiscovery;
    public IState mState;
    public IManager mManager;
    public INetwork mNetwork;

    public boolean gameInProgress = false;

    public static Tardigrade getInstance(Activity context) {
        if(context != null){
            ourInstance = new Tardigrade(context);
        }
        return ourInstance;
    }

    protected Tardigrade(Activity context) {
        mContext = context;
        mHub = Hub.getInstance(mContext);
        mDeck = Deck.getInstance(mContext);
        mNetwork = Network.getInstance();
        mDiscovery = Discovery.getInstance();
        mState = State.getInstance();
        mManager = Manager.getInstance();
    }

    public void changeContext(Activity context){
        this.mContext = context;
    }

    public Context getContext(){
        return mContext;
    }


}
