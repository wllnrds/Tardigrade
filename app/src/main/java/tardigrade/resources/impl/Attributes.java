package tardigrade.resources.impl;

import android.annotation.SuppressLint;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import tardigrade.game.IAttributes;

public class Attributes implements IAttributes {
    protected List<Pair<String, Object>> attributes;

    public static List<String> ATTRS = new ArrayList<String>();

    public Attributes(){
        attributes = new ArrayList<>();
    }

    public Attributes(String[] ATTRS) {
        attributes = new ArrayList<>();

        for(int i=0; i<ATTRS.length;i++) {
            setAttribute(ATTRS[i], 0);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void setAttribute(String name, Object value) {
        if(ATTRS.contains(name)){
            for (Pair<String, Object> attribute: attributes) {
                if(attribute.first.equals(name)){
                    attribute = new Pair<>(name, value);
                }
            }
        }else{
            attributes.add(new Pair<>(name, value));
        }
    }

    @SuppressLint("NewApi")
    @Override
    public Object getAttribute(String name) {
        if(ATTRS.contains(name)){
            for (Pair<String, Object> attribute: attributes) {
                if(attribute.first.equals(name)){
                    return attribute.second;
                }
            }
        }
        return null;
    }

    @SuppressLint("NewApi")
    @Override
    public void removeAttribute(String name) {
        if(ATTRS.contains(name)){
            for (Pair<String, Object> attribute: attributes) {
                if(attribute.first.equals(name)){
                    attributes.remove(attribute);
                }
            }
        }
    }
}
