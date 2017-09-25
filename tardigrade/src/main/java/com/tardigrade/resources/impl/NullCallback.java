package com.tardigrade.resources.impl;

import com.tardigrade.comunication.IPack;
import com.tardigrade.utils.ICallback;

public class NullCallback implements ICallback {
	@Override
	public void doit(IPack pack) {
		return;
	}
}
