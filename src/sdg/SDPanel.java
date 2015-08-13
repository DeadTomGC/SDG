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
    public Image image = null;
    int w;
    int h;
    int imageWidth;
    int imageHeight;
    public SDPanel()
    {
        
    }
    public void setImage(Image image){
        this.image=image;
    }
    public void paint(Graphics g)
    {
        super.paint(g);
        w = getWidth();
        h = getHeight();
        if(image!=null){
            g.drawImage(image, 0, 0, w, h, this);
        }
    }
}