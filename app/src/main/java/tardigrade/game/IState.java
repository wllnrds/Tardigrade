package tardigrade.game;

import java.util.List;

import tardigrade.utils.Flag;
import tardigrade.utils.ICallback;

public interface IState{
	void setOnChange(ICallback callback);
	void setOnMakeChange(ICallback callback);
	void setOnReceiverUpdate(ICallback callback);

	interface ITuple {
		void append(Object object);
		Flag getKey();
		List<Object> retrieve();
	}
}
