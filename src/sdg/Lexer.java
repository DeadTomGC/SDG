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
public class Lexer {

    private ArrayList<String> moduleNames;
    public ArrayList<Module> modules;
    public ArrayList<Link> links;
    private int line;
    int character;
    public int errorFlag;
    public String errorMessage;

    public Lexer() {
        character = 0;
        errorFlag = 0;
        errorMessage = "";

    }

    public void lex(String input) {
        line = 0;
        character = 0;
        moduleNames = new ArrayList<String>();
        modules = new ArrayList<Module>();
        links = new ArrayList<Link>();
        errorFlag = 0;
        errorMessage = "";
        while (character < input.length() && errorFlag == 0) {
            line++;
            lexLine(input);
        }

    }

    void lexLine(String input) {

        int state = 0;
        Link link = new Link();
        char[] inputAr = input.toCharArray();
        StringBuilder namebuilder = new StringBuilder();
        String name = "";
        boolean slash = false;
        while (character < inputAr.length && inputAr[character] != '\n' && inputAr[character] != '\r') {
            if (inputAr[character] != '-' && inputAr[character] != '>' && inputAr[character] != ':') {
                if (slash && inputAr[character] == 'n') {
                    namebuilder.append('\n');
                    slash = false;
                } else {

                    if (inputAr[character] == '\\') {
                        slash = true;
                    } else {
                        slash = false;
                        namebuilder.append(inputAr[character]);
                    }
                }

                character++;
            } else {
                name = namebuilder.toString();
                if (name.equals("")) {
                    errorFlag = 1;
                    errorMessage = "Error: empty name or message L" + line;
                    return;
                }
                switch (state) {
                    case 0:
                        character = dropSpaces(inputAr, character);
                        if (inputAr.length <= character + 1 || inputAr[character] != '-' || inputAr[character + 1] != '>') {
                            errorFlag = 1;
                            errorMessage = "Error: invalid symbol L" + line;
                            return;
                        }
                        state0(link, name);
                        break;
                    case 1:
                        character = dropSpaces(inputAr, character);
                        if (inputAr.length <= character || inputAr[character] != ':') {
                            errorFlag = 1;
                            errorMessage = "Error: invalid symbol L" + line;
                            return;
                        }
                        state1(link, name);
                        break;
                    default:
                        break;

                }
                state++;
                namebuilder = new StringBuilder();
                character = dropSpaces(inputAr, character);
            }
        }
        name = namebuilder.toString();
        if (state == 1 || (!name.equals("") && state != 2)) {
            errorFlag = 1;
            errorMessage = "Error: incomplete line L" + line;
            return;
        }
        if (!name.equals("")) {
            state2(link, name);
        }
        character = dropSpaces(inputAr, character);
        character++;

    }

    int dropSpaces(char[] inputAr, int position) {
        while (position < inputAr.length && (inputAr[position] == ' ')) {
            position++;
        }
        return position;
    }

    void state0(Link link, String name) {
        if (moduleNames.contains(name)) {
            int index = moduleNames.indexOf(name);
            link.left = modules.get(index);
        } else {
            link.left = new Module();
            link.left.name = name;
            link.left.index = modules.size();
            modules.add(link.left);
            moduleNames.add(name);
        }
        character += 2;
    }

    void state1(Link link, String name) {
        if (moduleNames.contains(name)) {
            int index = moduleNames.indexOf(name);
            link.right = modules.get(index);
        } else {
            link.right = new Module();
            link.right.name = name;
            link.right.index = modules.size();
            modules.add(link.right);
            moduleNames.add(name);
        }
        character++;
    }

    void state2(Link link, String name) {
        link.text = name;
        link.index = links.size();
        links.add(link);

    }
}

class Module {

    public int x = 0;
    public int y = 0;
    public int sizeX;
    public int sizeY;
    public String name;
    public int index;
    @Override
    public String toString() {
        return name;
    }
}

class Link {

    public int y;
    public int sizeY;
    public Module left;
    public Module right;
    public String text;
    public int index;
    @Override
    public String toString() {
        return "" + left.toString() + " " + text + " " + right.toString();
    }
}
