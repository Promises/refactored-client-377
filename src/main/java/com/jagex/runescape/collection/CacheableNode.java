package com.jagex.runescape.collection;

public class CacheableNode extends Node {

	public void clear() {
		if (prev == null) {
			return;
		} else {
			prev.next = next;
			next.prev = prev;
			next = null;
			prev = null;
			return;
		}
	}

	public CacheableNode() {
	}

	public CacheableNode next;
	public CacheableNode prev;
}
