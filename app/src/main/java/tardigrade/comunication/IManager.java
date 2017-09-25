package tardigrade.comunication;

import tardigrade.utils.ICallback;

public interface IManager {
	void registerObserver(IChannel channel);
	void removeObserver(IChannel channel);
	void notifyObservers(IPack pack);

	void update(IPack pack);

	void setOnChangeObservers(ICallback callback);
}