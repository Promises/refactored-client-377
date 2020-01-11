package com.jagex.runescape;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class GameFrame extends JFrame {
    private final GameShell gameStub;

    public GameFrame(GameShell gameStub, int width, int height) {
        this.gameStub = gameStub;
        pack();
        setTitle("Jagex");
        setResizable(false);
        gameStub.extraWidth = getInsets().left + getInsets().right;
        gameStub.extraHeight = getInsets().top + getInsets().bottom;
        setSize(width + gameStub.extraWidth, height + gameStub.extraHeight);
        setVisible(true);
        toFront();


    }

    @Override
    public Graphics getGraphics() {
        Graphics graphics = super.getGraphics();
        graphics.translate(gameStub.extraWidth, gameStub.extraHeight);
        return graphics;
    }

    @Override
    public void update(Graphics graphics) {
        gameStub.update();
    }

    @Override
    public void paint(Graphics graphics) {
        gameStub.paint();
    }

}
