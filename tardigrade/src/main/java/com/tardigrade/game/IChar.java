package com.tardigrade.game;


import com.tardigrade.utils.ICallback;

public interface IChar {
	void setAttribute(String attribute, String value);
	String getAttribute(String attribute);
	String getAttribute(String attribute, boolean withModificator);

	void setModificator(String id, String attribute, String expression, String description);
	String[] getModificator(String id);
	void removeModificator(String id);
	
	void setOnChangeListener(ICallback callback);
}