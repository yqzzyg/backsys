package com.neusoft.mid.ec.api.util;

import net.sf.json.JSON;
import net.sf.json.JSONException;
import net.sf.json.xml.XMLSerializer;

import java.io.IOException;

/**
 * Created by songjunliang on 2019/9/29.
 */
public class XmlJsonUtil {

    public static JSON xml2json(String xml) throws JSONException, IOException {
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.read(xml);
    }
}
