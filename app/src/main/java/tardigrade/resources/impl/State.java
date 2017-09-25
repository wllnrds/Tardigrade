package tardigrade.resources.impl;

import tardigrade.Tardigrade;
import tardigrade.comunication.IPack;
import tardigrade.game.IState;
import tardigrade.utils.Flag;
import tardigrade.utils.ICallback;

public class State implements IState{
    public ICallback onMakeChange;
    public ICallback OnReceiverUpdate;
    public ICallback onChange;

    private static State ourInstance = new State();

    public static State getInstance() {
        return ourInstance;
    }

    protected State(){
        onMakeChange = new NullCallback();
        OnReceiverUpdate = new NullCallback();
        onChange =  new ICallback() {
            @Override
            public void doit(IPack pack) {
                Manager.getInstance().notifyObservers(pack);
            }
        };
    }


    @Override
    public void setOnChange(ICallback callback) {
        onChange = callback;
    }

    @Override
    public void setOnMakeChange(ICallback callback) {
        onMakeChange = callback;
    }

    @Override
    public void setOnReceiverUpdate(ICallback callback) {
        OnReceiverUpdate = callback;
    }
}