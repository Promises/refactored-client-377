package com.jagex.runescape;

import java.awt.Frame;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class GameFrame extends Frame {
	private final GameShell gameStub;

	public GameFrame(GameShell gameStub, int width, int height) {
		this.gameStub = gameStub;
		setTitle("Jagex");
		setResizable(false);
		setSize(width + 8, height + 28);
		setVisible(true);
		toFront();
	}

	@Override
	public Graphics getGraphics() {
		Graphics graphics = super.getGraphics();
		graphics.translate(0, 22);
		return graphics;
	}

	@Override
	public void update(Graphics graphics) {
		gameStub.update(graphics);
	}

	@Override
	public void paint(Graphics graphics) {
		gameStub.paint(graphics);
	}

}
