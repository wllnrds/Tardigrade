package com.well.whospy.Tardigrade;

import tardigrade.resources.impl.Pack;
import tardigrade.resources.impl.State;
import tardigrade.utils.Flag;


public class WhoState extends State{

    private GameTardigrade.Roles roles = null;

    public void startGame(GameTardigrade.Roles roles){
        this.roles = roles;
        onChange.doit(Pack.create(Flag.UPDATE, roles));
    }
}
