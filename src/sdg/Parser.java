/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdg;

import java.util.ArrayList;

/**
 *
 * @author DeadTomGC
 */
public class Parser {
    Drawer drawer=null;
    int spacing = 10;
    int thickness = 3;
    public Parser(){
         
    }
    public Parser(Drawer drawer){
         this.drawer=drawer;
    }
    public void parseAndRender(ArrayList<Link> links,ArrayList<Module> modules){
        int xPlace = spacing;
        int yPlace = spacing;
        Module tempMod;
        Link link1,link2,link3;
        int textDim[];
        int diff=0;
        //give initial size and position
        for(int i=0;i<modules.size();i++){
            tempMod = modules.get(i);
            tempMod.x = xPlace;
            tempMod.y = yPlace;
            xPlace+=drawer.g.getFontMetrics().stringWidth(tempMod.name)+thickness*2+spacing*3;
            tempMod.sizeX = drawer.g.getFontMetrics().stringWidth(tempMod.name)+spacing*2+2*thickness;
            tempMod.sizeY = drawer.g.getFontMetrics().getHeight()+2*spacing+2*thickness;
        }
        //analyze links and space modules according to width and height
        for(int i=0;i<links.size();i++){
            link1 = links.get(i);
            if(link1.right.index>=link1.left.index){
                if(modules.size()>link1.left.index+1){
                    tempMod = modules.get(link1.right.index-1);
                    textDim = getLongestLineLength(link1.text);
                    diff = 0;
                    if(tempMod.x+spacing*2+textDim[0]>link1.right.x){
                        diff = tempMod.x+spacing*2+textDim[0]-link1.right.x;
                        for(int j=link1.right.index;j<modules.size();j++){
                            modules.get(j).x+=diff;
                        }
                    }
                }
            }else{
                
                
            }
        }
    }
    private int[] getLongestLineLength(String text){
        int longest = 0;
        boolean notOut=true;
        int lines = 0;
        while(text.contains("\n") && notOut){
            int loc = text.indexOf("\n");
            int width = drawer.g.getFontMetrics().stringWidth(text.substring(0,loc));
            if(width>longest){
                longest = width;
            }
            if(text.length()>loc+1){
                text = text.substring(loc+1);
            }else{
                notOut = false;
            }
            lines++;
        }
        if(!text.equals("")){
            lines++;
        }
        if(longest == 0){
            int loc = text.indexOf("\n");
            longest = drawer.g.getFontMetrics().stringWidth(text.substring(0,loc));
        }
        
        int retVal[] = {longest,lines*drawer.g.getFontMetrics().getHeight()};
        return retVal;
    }
}
