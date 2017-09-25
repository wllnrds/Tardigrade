package com.well.whospy.Tardigrade;

import android.app.Activity;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tardigrade.Tardigrade;
import tardigrade.deck.ICard;
import tardigrade.resources.impl.Card;
import tardigrade.resources.impl.Manager;

public class GameTardigrade extends Tardigrade {
    protected GameTardigrade(Activity context) {
        super(context);
        super.CARD_ATTRIBUTES = new String[]{"ATTR_IDIMAGE", "ATTR_PLACE", "ATTR_RULE"};
    }

    public void startGame(){
        if(((Manager)mManager).getObserversIds().size() >= 3){
            try {
                ((WhoState)mState).startGame(new Roles(1));
                gameInProgress = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Roles implements Serializable {
        private String gameId = "1";
        private List<RoleToPlayer> player_roles = new ArrayList<RoleToPlayer>();

        public Roles(int gameId) throws IOException {
            this.gameId = gameId + "";
            mDeck.loadDeck();

            List<String> ids = ((Manager)mManager).getObserversIds();
            List<ICard> roles = mDeck.getAllCards();

            shuffle(ids, 0);
            shuffle(roles, 8 - ids.size());
            roles.add(Card.Create("0", "Espião", "Descubra onde seus amigos estão", null));
            shuffle(roles, 0);

            for(int i=0; i< ids.size(); i++){
                player_roles.add(new RoleToPlayer(ids.get(i), roles.get(i).getId()));
            }
        }

        public String getMyRole(String id){
            for(RoleToPlayer player : player_roles){
                if(player.idPlayer.equals(id)){
                    return player.idRole;
                }
            }

            return "-1";
        }

        public class RoleToPlayer{
            String idPlayer;
            String idRole;

            public RoleToPlayer(String id, String role){
                this.idPlayer = id;
                this.idRole = role;
            }
        }

        public void shuffle(List<?> list, int remove){
            long seed = System.nanoTime();

            if(remove > 1){
                while(list.size() > remove){
                    list.remove(0);
                }
            }

            Collections.shuffle(list, new Random(seed));
        }
    }
}
