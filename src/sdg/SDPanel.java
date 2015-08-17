/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;

/**
 *
 * @author DeadTomGC
 */
class SDPanel extends Panel
{
    public Drawer drawer = null;
    int w;
    int h;
    int imageWidth;
    int imageHeight;
    public SDPanel()
    {
        
    }
    public void setDrawer(Drawer drawer){
        this.drawer=drawer;
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        w = getWidth();
        h = getHeight();
        if(drawer!=null && drawer.image!=null){
            g.drawImage(drawer.image, 0, 0, w, h, this);
        }
    }
}