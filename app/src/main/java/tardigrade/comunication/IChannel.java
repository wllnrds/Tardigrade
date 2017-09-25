package tardigrade.comunication;

public interface IChannel {
	String getName();
	String getAddress();

	void sendPack(IPack pack);
	int getPort();
}
