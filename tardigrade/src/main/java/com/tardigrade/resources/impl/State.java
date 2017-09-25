package com.tardigrade.resources.impl;

import com.tardigrade.game.IState;
import com.tardigrade.utils.ICallback;

public class State implements IState {
    private ICallback onMakeChange;
    private ICallback OnReceiverUpdate;
    private ICallback onChange;

    private static State ourInstance = new State();

    public static State getInstance() {
        return ourInstance;
    }

    private State(){
        onMakeChange = new NullCallback();
        OnReceiverUpdate = new NullCallback();
        onChange =  new NullCallback();
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