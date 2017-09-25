package tardigrade.comunication;

import tardigrade.utils.ICallback;

public interface IDiscovery {
	void init();
	
	boolean start();
	void stop();

	void setOnStartService(ICallback callback);
	void setOnStopService(ICallback callback);
	void setOnFailService(ICallback callback);
	void setOnFoundDevice(ICallback callback);
	void setOnLostDevice(ICallback callback);
	
	boolean isWorking();
}