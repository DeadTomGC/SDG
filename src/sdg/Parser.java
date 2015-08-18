/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdg;

import java.util.ArrayList;
import sdg.Lexer.Link;
import sdg.Lexer.Module;

/**
 *
 * @author DeadTomGC
 */
public class Parser {

    Drawer drawer = null;
    public int spacing = 10;
    public int thickness = 2;
    public int loopHeight = 10;
    public int radius = 4;

    public Parser() {

    }

    public Parser(Drawer drawer) {
        this.drawer = drawer;
    }

    public void parseAndRender(ArrayList<Link> links, ArrayList<Module> modules) {

        int xPlace = spacing;
        int yPlace = spacing;
        Module tempMod;
        Link link1;
        int textDim[];
        int diff = 0;
        drawer.clear();
        //give initial size and position
        for (int i = 0; i < modules.size(); i++) {
            tempMod = modules.get(i);
            tempMod.x = xPlace;
            tempMod.y = yPlace;
            textDim = getStringDim(tempMod.name);
            xPlace += textDim[0] + thickness * 2 + spacing * 3;
            tempMod.sizeX = textDim[0] + spacing * 2 + 2 * thickness;
            tempMod.sizeY = textDim[1] + 2 * spacing + 2 * thickness;
        }
        //analyze links and space modules according to width and height
        for (int i = 0; i < links.size(); i++) {
            link1 = links.get(i);
            textDim = getStringDim(link1.text);
            if (link1.left.index == link1.right.index) {
                link1.sizeY = textDim[1] + loopHeight + (thickness * 3) / 2;
            } else {
                link1.sizeY = textDim[1] + (thickness * 3) / 2;
            }
            link1.sizeX = textDim[0];
            if (link1.right.index >= link1.left.index) {
                if (modules.size() > link1.left.index + 1) {
                    tempMod = modules.get(link1.right.index - 1);
                    diff = 0;
                    if (tempMod.x + spacing * 2 + textDim[0] + thickness > link1.right.x) {
                        diff = thickness + tempMod.x + spacing * 2 + textDim[0] - link1.right.x;
                        for (int j = link1.right.index; j < modules.size(); j++) {
                            modules.get(j).x += diff;
                        }
                    }
                }
            } else {

                tempMod = modules.get(link1.right.index + 1);
                diff = 0;
                if (tempMod.x < thickness + link1.right.x + spacing * 2 + textDim[0]) {
                    diff = thickness + link1.right.x + spacing * 2 + textDim[0] - tempMod.x;
                    for (int j = link1.right.index + 1; j < modules.size(); j++) {
                        modules.get(j).x += diff;
                    }
                }
            }
        }
        // spaced modules and heights recorded
        // place labels 
        int width = 0;
        int y = 0;
        for (int i = 0; i < links.size(); i++) {
            link1 = links.get(i);
            if (i == 0) {
                //get the bottom of the max sized module
                for (int j = 0; j < modules.size(); j++) {
                    tempMod = modules.get(j);
                    if (tempMod.y + spacing + tempMod.sizeY > y) {
                        y = tempMod.y + spacing + tempMod.sizeY;
                    }
                }
            }
            link1.y = y;
            if (link1.right.index > link1.left.index) {
                link1.x = link1.right.x + link1.right.sizeX / 2 - link1.sizeX - spacing - thickness / 2;
            } else {
                link1.x = link1.right.x + link1.right.sizeX / 2 + spacing + thickness / 2;
            }
            if (link1.x + link1.sizeX + spacing > width) {
                width = link1.x + link1.sizeX + spacing;
            }
            y += spacing + link1.sizeY;
        }
        //resize image
        int height = y+spacing;
        //get largest width
        if (modules.size() > 0 && modules.get(modules.size() - 1).x + modules.get(modules.size() - 1).sizeX + spacing > width) {
            width = modules.get(modules.size() - 1).x + modules.get(modules.size() - 1).sizeX + spacing;
        }
        //resize
        if (0 < width && (int) (drawer.getHeight() * ((float) width / drawer.getWidth())) >= height) {
            drawer.resizeImage(width, (int) (drawer.getHeight() * ((float) width / drawer.getWidth())));
        }
        if (0 < height && (int) (drawer.getWidth() * ((float) height / drawer.getHeight())) >= width) {
            drawer.resizeImage((int) (drawer.getWidth() * ((float) height / drawer.getHeight())), height);
        }
        height-=spacing;
        //render modules
        for (int i = 0; i < modules.size(); i++) {
            tempMod = modules.get(i);
            textDim = getStringDim(tempMod.name);
            drawer.drawBox(tempMod.x, tempMod.y, tempMod.sizeX, tempMod.sizeY, thickness);
            drawer.drawText(tempMod.x + tempMod.sizeX / 2, tempMod.y + tempMod.sizeY / 2 - textDim[1] / 2, tempMod.name);
            drawer.drawVerticalLine(tempMod.x + tempMod.sizeX / 2, tempMod.y + tempMod.sizeY, height - (tempMod.y + tempMod.sizeY), thickness);
        }
        //render links
        for (int i = 0; i < links.size(); i++) {
            link1 = links.get(i);
            textDim = getStringDim(link1.text);
            if (link1.left.index == link1.right.index) {
                drawer.drawText(link1.x + textDim[0] / 2, link1.y, link1.text);
                drawer.drawHook(link1.x - spacing, link1.y + textDim[1] + (thickness * 3) / 2, link1.sizeX + spacing, loopHeight, thickness, radius, false);

            } else {
                drawer.drawText(link1.x + textDim[0] / 2, link1.y, link1.text);
                if (link1.left.index > link1.right.index) {
                    drawer.drawArrow(link1.left.x + link1.left.sizeX / 2 - thickness / 2, link1.y + textDim[1] + (thickness * 3) / 2, (link1.left.x + link1.left.sizeX / 2) - (link1.right.x + link1.right.sizeX / 2) - thickness, thickness, true);
                } else {
                    drawer.drawArrow(link1.left.x + link1.left.sizeX / 2 + thickness / 2, link1.y + textDim[1] + (thickness * 3) / 2, (link1.right.x + link1.right.sizeX / 2) - (link1.left.x + link1.left.sizeX / 2) - thickness, thickness, false);
                }
            }
        }
    }

    private int[] getStringDim(String text) {
        int longest = 0;
        boolean notOut = true;
        int lines = 0;
        while (text.contains("\n") && notOut) {
            int loc = text.indexOf("\n");
            int width = drawer.g.getFontMetrics().stringWidth(text.substring(0, loc));
            if (width > longest) {
                longest = width;
            }
            if (text.length() > loc + 1) {
                text = text.substring(loc + 1);
            } else {
                notOut = false;
                lines--;
            }
            lines++;
        }
        if (!text.equals("")) {
            int width = drawer.g.getFontMetrics().stringWidth(text);
            if(width > longest)
            {
                longest = width;
            }
            lines++;
        }
        if (longest == 0) {
            int loc = text.indexOf("\n");
            if (loc > 0) {
                longest = drawer.g.getFontMetrics().stringWidth(text.substring(0, loc));
            } else {
                longest = drawer.g.getFontMetrics().stringWidth(text);
            }
        }

        int retVal[] = {longest, lines * drawer.g.getFontMetrics().getHeight()};
        return retVal;
    }
}
