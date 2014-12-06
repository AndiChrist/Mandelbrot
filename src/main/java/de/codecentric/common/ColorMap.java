/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.codecentric.common;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andreas Christ <andreas.christ@codecentric.de>
 */
public class ColorMap {

    private static final List<Color> colors; // = new ArrayList<>();

    static public Color getColor(int t) {
        return colors.get(t);
    }

    static {
        colors = new ArrayList<>();

        colors.add(new Color(0, 0, 0));
        colors.add(new Color(0, 0, 168));
        colors.add(new Color(0, 168, 0));
        colors.add(new Color(0, 168, 168));
        colors.add(new Color(168, 0, 0));
        colors.add(new Color(168, 0, 168));
        colors.add(new Color(168, 84, 0));
        colors.add(new Color(168, 168, 168));
        colors.add(new Color(84, 84, 84));
        colors.add(new Color(84, 84, 252));
        colors.add(new Color(84, 252, 84));
        colors.add(new Color(84, 252, 252));
        colors.add(new Color(252, 84, 84));
        colors.add(new Color(252, 84, 252));
        colors.add(new Color(252, 252, 84));
        colors.add(new Color(252, 252, 252));
        colors.add(new Color(0, 0, 0));
        colors.add(new Color(20, 20, 20));
        colors.add(new Color(32, 32, 32));
        colors.add(new Color(44, 44, 44));
        colors.add(new Color(56, 56, 56));
        colors.add(new Color(68, 68, 68));
        colors.add(new Color(80, 80, 80));
        colors.add(new Color(96, 96, 96));
        colors.add(new Color(112, 112, 112));
        colors.add(new Color(128, 128, 128));
        colors.add(new Color(144, 144, 144));
        colors.add(new Color(160, 160, 160));
        colors.add(new Color(180, 180, 180));
        colors.add(new Color(200, 200, 200));
        colors.add(new Color(224, 224, 224));
        colors.add(new Color(252, 252, 252));
        colors.add(new Color(0, 0, 252));
        colors.add(new Color(64, 0, 252));
        colors.add(new Color(124, 0, 252));
        colors.add(new Color(188, 0, 252));
        colors.add(new Color(252, 0, 252));
        colors.add(new Color(252, 0, 188));
        colors.add(new Color(252, 0, 124));
        colors.add(new Color(252, 0, 64));
        colors.add(new Color(252, 0, 0));
        colors.add(new Color(252, 64, 0));
        colors.add(new Color(252, 124, 0));
        colors.add(new Color(252, 188, 0));
        colors.add(new Color(252, 252, 0));
        colors.add(new Color(188, 252, 0));
        colors.add(new Color(124, 252, 0));
        colors.add(new Color(64, 252, 0));
        colors.add(new Color(0, 252, 0));
        colors.add(new Color(0, 252, 64));
        colors.add(new Color(0, 252, 124));
        colors.add(new Color(0, 252, 188));
        colors.add(new Color(0, 252, 252));
        colors.add(new Color(0, 188, 252));
        colors.add(new Color(0, 124, 252));
        colors.add(new Color(0, 64, 252));
        colors.add(new Color(124, 124, 252));
        colors.add(new Color(156, 124, 252));
        colors.add(new Color(188, 124, 252));
        colors.add(new Color(220, 124, 252));
        colors.add(new Color(252, 124, 252));
        colors.add(new Color(252, 124, 220));
        colors.add(new Color(252, 124, 188));
        colors.add(new Color(252, 124, 156));
        colors.add(new Color(252, 124, 124));
        colors.add(new Color(252, 156, 124));
        colors.add(new Color(252, 188, 124));
        colors.add(new Color(252, 220, 124));
        colors.add(new Color(252, 252, 124));
        colors.add(new Color(220, 252, 124));
        colors.add(new Color(188, 252, 124));
        colors.add(new Color(156, 252, 124));
        colors.add(new Color(124, 252, 124));
        colors.add(new Color(124, 252, 156));
        colors.add(new Color(124, 252, 188));
        colors.add(new Color(124, 252, 220));
        colors.add(new Color(124, 252, 252));
        colors.add(new Color(124, 220, 252));
        colors.add(new Color(124, 188, 252));
        colors.add(new Color(124, 156, 252));
        colors.add(new Color(180, 180, 252));
        colors.add(new Color(196, 180, 252));
        colors.add(new Color(216, 180, 252));
        colors.add(new Color(232, 180, 252));
        colors.add(new Color(252, 180, 252));
        colors.add(new Color(252, 180, 232));
        colors.add(new Color(252, 180, 216));
        colors.add(new Color(252, 180, 196));
        colors.add(new Color(252, 180, 180));
        colors.add(new Color(252, 196, 180));
        colors.add(new Color(252, 216, 180));
        colors.add(new Color(252, 232, 180));
        colors.add(new Color(252, 252, 180));
        colors.add(new Color(232, 252, 180));
        colors.add(new Color(216, 252, 180));
        colors.add(new Color(196, 252, 180));
        colors.add(new Color(180, 252, 180));
        colors.add(new Color(180, 252, 196));
        colors.add(new Color(180, 252, 216));
        colors.add(new Color(180, 252, 232));
        colors.add(new Color(180, 252, 252));
        colors.add(new Color(180, 232, 252));
        colors.add(new Color(180, 216, 252));
        colors.add(new Color(180, 196, 252));
        colors.add(new Color(0, 0, 112));
        colors.add(new Color(28, 0, 112));
        colors.add(new Color(56, 0, 112));
        colors.add(new Color(84, 0, 112));
        colors.add(new Color(112, 0, 112));
        colors.add(new Color(112, 0, 84));
        colors.add(new Color(112, 0, 56));
        colors.add(new Color(112, 0, 28));
        colors.add(new Color(112, 0, 0));
        colors.add(new Color(112, 28, 0));
        colors.add(new Color(112, 56, 0));
        colors.add(new Color(112, 84, 0));
        colors.add(new Color(112, 112, 0));
        colors.add(new Color(84, 112, 0));
        colors.add(new Color(56, 112, 0));
        colors.add(new Color(28, 112, 0));
        colors.add(new Color(0, 112, 0));
        colors.add(new Color(0, 112, 28));
        colors.add(new Color(0, 112, 56));
        colors.add(new Color(0, 112, 84));
        colors.add(new Color(0, 112, 112));
        colors.add(new Color(0, 84, 112));
        colors.add(new Color(0, 56, 112));
        colors.add(new Color(0, 28, 112));
        colors.add(new Color(56, 56, 112));
        colors.add(new Color(68, 56, 112));
        colors.add(new Color(84, 56, 112));
        colors.add(new Color(96, 56, 112));
        colors.add(new Color(112, 56, 112));
        colors.add(new Color(112, 56, 96));
        colors.add(new Color(112, 56, 84));
        colors.add(new Color(112, 56, 68));
        colors.add(new Color(112, 56, 56));
        colors.add(new Color(112, 68, 56));
        colors.add(new Color(112, 84, 56));
        colors.add(new Color(112, 96, 56));
        colors.add(new Color(112, 112, 56));
        colors.add(new Color(96, 112, 56));
        colors.add(new Color(84, 112, 56));
        colors.add(new Color(68, 112, 56));
        colors.add(new Color(56, 112, 56));
        colors.add(new Color(56, 112, 68));
        colors.add(new Color(56, 112, 84));
        colors.add(new Color(56, 112, 96));
        colors.add(new Color(56, 112, 112));
        colors.add(new Color(56, 96, 112));
        colors.add(new Color(56, 84, 112));
        colors.add(new Color(56, 68, 112));
        colors.add(new Color(80, 80, 112));
        colors.add(new Color(88, 80, 112));
        colors.add(new Color(96, 80, 112));
        colors.add(new Color(104, 80, 112));
        colors.add(new Color(112, 80, 112));
        colors.add(new Color(112, 80, 104));
        colors.add(new Color(112, 80, 96));
        colors.add(new Color(112, 80, 88));
        colors.add(new Color(112, 80, 80));
        colors.add(new Color(112, 88, 80));
        colors.add(new Color(112, 96, 80));
        colors.add(new Color(112, 104, 80));
        colors.add(new Color(112, 112, 80));
        colors.add(new Color(104, 112, 80));
        colors.add(new Color(96, 112, 80));
        colors.add(new Color(88, 112, 80));
        colors.add(new Color(80, 112, 80));
        colors.add(new Color(80, 112, 88));
        colors.add(new Color(80, 112, 96));
        colors.add(new Color(80, 112, 104));
        colors.add(new Color(80, 112, 112));
        colors.add(new Color(80, 104, 112));
        colors.add(new Color(80, 96, 112));
        colors.add(new Color(80, 88, 112));
        colors.add(new Color(0, 0, 64));
        colors.add(new Color(16, 0, 64));
        colors.add(new Color(32, 0, 64));
        colors.add(new Color(48, 0, 64));
        colors.add(new Color(64, 0, 64));
        colors.add(new Color(64, 0, 48));
        colors.add(new Color(64, 0, 32));
        colors.add(new Color(64, 0, 16));
        colors.add(new Color(64, 0, 0));
        colors.add(new Color(64, 16, 0));
        colors.add(new Color(64, 32, 0));
        colors.add(new Color(64, 48, 0));
        colors.add(new Color(64, 64, 0));
        colors.add(new Color(48, 64, 0));
        colors.add(new Color(32, 64, 0));
        colors.add(new Color(16, 64, 0));
        colors.add(new Color(0, 64, 0));
        colors.add(new Color(0, 64, 16));
        colors.add(new Color(0, 64, 32));
        colors.add(new Color(0, 64, 48));
        colors.add(new Color(0, 64, 64));
        colors.add(new Color(0, 48, 64));
        colors.add(new Color(0, 32, 64));
        colors.add(new Color(0, 16, 64));
        colors.add(new Color(32, 32, 64));
        colors.add(new Color(40, 32, 64));
        colors.add(new Color(48, 32, 64));
        colors.add(new Color(56, 32, 64));
        colors.add(new Color(64, 32, 64));
        colors.add(new Color(64, 32, 56));
        colors.add(new Color(64, 32, 48));
        colors.add(new Color(64, 32, 40));
        colors.add(new Color(64, 32, 32));
        colors.add(new Color(64, 40, 32));
        colors.add(new Color(64, 48, 32));
        colors.add(new Color(64, 56, 32));
        colors.add(new Color(64, 64, 32));
        colors.add(new Color(56, 64, 32));
        colors.add(new Color(48, 64, 32));
        colors.add(new Color(40, 64, 32));
        colors.add(new Color(32, 64, 32));
        colors.add(new Color(32, 64, 40));
        colors.add(new Color(32, 64, 48));
        colors.add(new Color(32, 64, 56));
        colors.add(new Color(32, 64, 64));
        colors.add(new Color(32, 56, 64));
        colors.add(new Color(32, 48, 64));
        colors.add(new Color(32, 40, 64));
        colors.add(new Color(44, 44, 64));
        colors.add(new Color(48, 44, 64));
        colors.add(new Color(52, 44, 64));
        colors.add(new Color(60, 44, 64));
        colors.add(new Color(64, 44, 64));
        colors.add(new Color(64, 44, 60));
        colors.add(new Color(64, 44, 52));
        colors.add(new Color(64, 44, 48));
        colors.add(new Color(64, 44, 44));
        colors.add(new Color(64, 48, 44));
        colors.add(new Color(64, 52, 44));
        colors.add(new Color(64, 60, 44));
        colors.add(new Color(64, 64, 44));
        colors.add(new Color(60, 64, 44));
        colors.add(new Color(52, 64, 44));
        colors.add(new Color(48, 64, 44));
        colors.add(new Color(44, 64, 44));
        colors.add(new Color(44, 64, 48));
        colors.add(new Color(44, 64, 52));
        colors.add(new Color(44, 64, 60));
        colors.add(new Color(44, 64, 64));
        colors.add(new Color(44, 60, 64));
        colors.add(new Color(44, 52, 64));
        colors.add(new Color(44, 48, 64));
        colors.add(new Color(0, 0, 0));
        colors.add(new Color(0, 0, 0));
        colors.add(new Color(0, 0, 0));
        colors.add(new Color(0, 0, 0));
        colors.add(new Color(0, 0, 0));
        colors.add(new Color(0, 0, 0));
        colors.add(new Color(0, 0, 0));
        colors.add(new Color(0, 0, 0));
    }

}