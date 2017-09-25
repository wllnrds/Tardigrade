package com.tardigrade.comunication;

import com.tardigrade.utils.ICallback;

public interface INetwork {
	void start();
	void stop();

    int getPort();

	void setOnReceivePack(ICallback callback);
	boolean isWorking();
}
