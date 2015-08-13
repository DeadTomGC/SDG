/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import static java.lang.Math.round;

/**
 *
 * @author DeadTomGC
 */
public class Drawer {
    public BufferedImage image;
    public Graphics2D g;
    int iw,ih;
    Color lineColor;
    Color background;
    public Drawer(int w,int h){
        this.iw=w;
        this.ih=h;
        image = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);
        lineColor = Color.BLACK;
        background = Color.WHITE;
    }
    public boolean drawHook(int x,int y,int w,int h,int thickness,int radius,boolean flip){
        if(h<2*radius)
            return false;
        if(flip){
            g.setColor(lineColor);
            g.fillRect(x-w+radius, y, w-radius, thickness);
            g.fillRect(x-w+1, y+radius-1, thickness, h-radius*2+1);
            g.fillRect(x-w+radius, y+h-thickness, w-radius-thickness*3, thickness);
            g.fillArc(x-w, y-1,radius*2,radius*2,90,90);
            g.fillArc(x-w, y+h-radius*2 ,radius*2,radius*2,180,90);
            g.setColor(background);
            g.fillArc(x-w+thickness, y-1+thickness,radius*2-thickness,radius*2-thickness,90,90);
            g.fillArc(x-w+thickness, y+h-radius*2,radius*2-thickness,radius*2-thickness,180,90);
            int arrowX[] = {x,x-thickness*3,x-thickness*3};
            int arrowY[] = {y+h-thickness/2-1,y+h-thickness/2+thickness*3-1,y+h-thickness/2-1-thickness*3};
            g.setColor(lineColor);
            g.fillPolygon(arrowX, arrowY, 3);
        }
        else
        {
            g.setColor(lineColor);
            g.fillRect(x, y, w-radius, thickness);
            g.fillRect(x+w-thickness-1, y+radius-1, thickness, h-radius*2+1);
            g.fillRect(x+thickness*3, y+h-thickness, w-radius-thickness*3, thickness);
            g.fillArc(x+w-radius*2-1, y-1,radius*2,radius*2,90,-90);
            g.fillArc(x+w-radius*2-1, y+h-radius*2 ,radius*2,radius*2,-90,90);
            g.setColor(background);
            g.fillArc(x+w-radius*2-1, y-1+thickness,radius*2-thickness,radius*2-thickness,90,-90);
            g.fillArc(x+w-radius*2-1, y+h-radius*2,radius*2-thickness,radius*2-thickness,-90,90);
            int arrowX[] = {x,x+thickness*3,x+thickness*3};
            int arrowY[] = {y+h-thickness/2-1,y+h-thickness/2+thickness*3-1,y+h-thickness/2-1-thickness*3};
            g.setColor(lineColor);
            g.fillPolygon(arrowX, arrowY, 3);
        }
        return true;
    }
    public void drawArrow(int x,int y,int w,int thickness,boolean flip){
        if(flip){
            g.setColor(lineColor);
            g.fillRect(x-w+thickness*3, y, w-thickness*3, thickness);
            int arrowX[] = {x-w,x-w+thickness*3,x-w+thickness*3};
            int arrowY[] = {y+thickness/2,y+thickness/2+thickness*3,y+thickness/2-thickness*3};
            g.fillPolygon(arrowX, arrowY, 3);
        }
        else
        {
            g.setColor(lineColor);
            g.fillRect(x, y, w-thickness*3, thickness);
            int arrowX[] = {x+w,x+w-thickness*3,x+w-thickness*3};
            int arrowY[] = {y+thickness/2,y+thickness/2+thickness*3,y+thickness/2-thickness*3};
            g.fillPolygon(arrowX, arrowY, 3);
        }
        
    }
    public void drawBox(int x,int y,int w,int h,int thickness){
            g.setColor(lineColor);
            g.fillRect(x, y, w, h);
            g.setColor(background);
            g.fillRect(x+thickness, y+thickness, w-thickness*2, h-thickness*2);
    }
    
    public void drawVerticalLine(int x,int y,int h,int thickness){
            g.setColor(lineColor);
            g.fillRect(x-thickness/2, y, thickness, h);
    }
    public int drawText(int x,int y,String text){
        g.drawString(text, x, y);
        int length=0;
        length=g.getFontMetrics().stringWidth(text);
        return length;
    }
    public void setFont(Font font){
        g.setFont(font);
    }
    
}
