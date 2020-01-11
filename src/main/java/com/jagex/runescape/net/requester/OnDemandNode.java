package com.jagex.runescape.net.requester;

import com.jagex.runescape.collection.CacheableNode;

public class OnDemandNode extends CacheableNode {

	public int type;
	public int id;
	public int cyclesSinceSend;
	public byte[] buffer;
	public boolean immediate = true;
}
