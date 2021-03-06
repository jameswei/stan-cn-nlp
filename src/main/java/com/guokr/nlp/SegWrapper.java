package com.guokr.nlp;

import java.util.Properties;

import edu.stanford.nlp.ie.crf.CRFClassifier;

import com.guokr.util.Settings;
import com.guokr.util.ClasspathProtocol;

public class SegWrapper {

    public static Settings defaults = Settings.load("classpath:seg/defaults.using.prop");

    public static CRFClassifier reload(Properties settings, Properties defaults) {
        try {
            Class.forName("com.guokr.util.ClasspathProtocol");
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace(System.err);
        }

        Settings props = new Settings(settings, defaults);
        String model = props.getProperty("model");
        CRFClassifier crf = null;
        try {
            crf = CRFClassifier.getClassifier(model, props);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace(System.err);
        }
        return crf;
    }

    public static CRFClassifier classifier = reload(Settings.empty, defaults);

    public static String segment(String text) {
        return classifier.classifyToString(text).trim();
    }
}
