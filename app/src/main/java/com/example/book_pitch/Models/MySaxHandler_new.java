package com.example.book_pitch.Models;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class MySaxHandler_new extends DefaultHandler {
    private List<News_item> list;
    News_item item;
    String temp;
    boolean flagstart = false;

    public MySaxHandler_new() {
        list = new ArrayList<>();
    }
    public List<News_item> getItems(){
        return list;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if(flagstart== true){
            temp = new String(ch, start, length);
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(qName.equalsIgnoreCase("item")){
            item = new News_item();
            flagstart = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(qName.equalsIgnoreCase("item")){
            list.add(item);

        }else {
            if(qName.equalsIgnoreCase("title")){
                item.setTitle(temp);
            } else
                if(qName.equalsIgnoreCase("description")){
                item.setDescription(temp);
            } else
                if(qName.equalsIgnoreCase("pubDate")) {
                    item.setPubdate(temp);
                } else
                if(qName.equalsIgnoreCase("link")) {
                    item.setLink(temp);
                }
        }
    }
}
