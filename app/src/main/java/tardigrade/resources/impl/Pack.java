package tardigrade.resources.impl;

import java.io.Serializable;

import tardigrade.comunication.IPack;
import tardigrade.utils.Flag;

public class Pack implements Serializable, IPack {
    private static final long serialVersionUID = 1L;
	
    private Flag key;
    private Object value;

    private Pack(Flag key, Object value){
        this.key = key;
        this.value = value;
    }

    @Override
    public Flag getKey() { return key; }

    @Override
    public Object getValue() { return value; }

    @Override
    public void setKey(Flag key) {
        this.key = key;
    }

    @Override
    public void setValue(Object value) { this.value = value; }

    public static IPack create(Flag key, Object value) {
        return new Pack(key, value);
    }
}