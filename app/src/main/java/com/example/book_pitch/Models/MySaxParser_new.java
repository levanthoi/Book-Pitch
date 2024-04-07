package com.example.book_pitch.Models;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public  class MySaxParser_new {
    public static List<News_item> xmlParser(InputStream is){
        List<News_item> list = null;
        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            MySaxHandler_new saxHandlerNew = new MySaxHandler_new();
            xmlReader.setContentHandler(saxHandlerNew);
            xmlReader.parse(new InputSource(is));

            list = saxHandlerNew.getItems();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return list;
    }
}
