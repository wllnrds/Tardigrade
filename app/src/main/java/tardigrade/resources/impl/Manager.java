package tardigrade.resources.impl;

import java.util.ArrayList;
import java.util.List;

import tardigrade.comunication.IChannel;
import tardigrade.comunication.IManager;
import tardigrade.comunication.IPack;
import tardigrade.utils.Flag;
import tardigrade.utils.ICallback;

public class Manager implements IManager {
    private static Manager ourInstance = new Manager();

    private List<IChannel> opponents;
    private int maxOpponents = 8;

    private ICallback onChangeObservers;

    private Manager(){
        onChangeObservers = new NullCallback();
        opponents = new ArrayList<IChannel>();
    }

    public static Manager getInstance() {
        return ourInstance;
    }

    @Override
    public void registerObserver(IChannel channel) {
        if(opponents.size() < maxOpponents){
            opponents.add(channel);
            onChangeObservers.doit(Pack.create(Flag.RESULT_OK, channel));
        }else{
            for (IChannel player: opponents){
                String id_opponent = player.getName().split(":")[0];
                String id_channel = channel.getName().split(":")[0];
                if(id_opponent.equals(id_channel)) {
                    onChangeObservers.doit(Pack.create(Flag.RESULT_OK, channel));
                    opponents.remove(player);
                    opponents.add(channel);
                }
            }
        }
    }

    @Override
    public void removeObserver(IChannel channel) {
        for (IChannel opponent: opponents) {
            String id_opponent = opponent.getName().split(":")[0];
            String id_channel = channel.getName().split(":")[0];

            if(id_opponent.equals(id_channel)) {
                onChangeObservers.doit(Pack.create(Flag.RESULT_OK, channel));
                opponents.remove(opponent);
            }
        }
    }

    @Override
    public void setOnChangeObservers(ICallback callback) {
        onChangeObservers = callback;
    }

    @Override
    public void notifyObservers(IPack pack) {
        for (IChannel opponent: opponents) {
            opponent.sendPack(pack);
        }
    }

    public List<String> getObserversNames(){
        List<String> names = new ArrayList<String>();

        for (IChannel opponent: opponents) {
            names.add(opponent.getName().split(":")[1]);
        }
        return names;
    }

    public List<String> getObserversIds(){
        List<String> ids = new ArrayList<String>();

        for (IChannel opponent: opponents) {
            ids.add(opponent.getName().split(":")[0]);
        }
        return ids;
    }

    @Override
    public void update(IPack pack) {
        State.getInstance().OnReceiverUpdate.doit(pack);
    }
}
