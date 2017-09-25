package tardigrade.comunication;

import tardigrade.utils.ICallback;

public interface INetwork {
	void start();
	void stop();

    int getPort();

	void setOnReceivePack(ICallback callback);
	boolean isWorking();
}
