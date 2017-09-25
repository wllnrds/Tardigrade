package tardigrade.resources.impl;

import tardigrade.comunication.IPack;
import tardigrade.utils.ICallback;

public class NullCallback implements ICallback {
	@Override
	public void doit(IPack pack) {
		return;
	}
}
