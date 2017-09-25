package com.tardigrade.game;

import com.tardigrade.utils.Flag;
import com.tardigrade.utils.ICallback;

import java.util.List;

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
