package com.guokr.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Hashtable;
import java.util.Map;

public class ClasspathProtocol {
    static {
        URL.setURLStreamHandlerFactory(new Factory("classpath", new Handler()));
    }

    static class Handler extends URLStreamHandler {
        private final ClassLoader loader;

        public Handler() {
            this.loader = getClass().getClassLoader();
        }

        public Handler(ClassLoader loader) {
            this.loader = loader;
        }

        @Override
        protected URLConnection openConnection(URL u) throws IOException {
            String path = u.getPath();
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            final URL resourceUrl = loader.getResource(path);
            if (resourceUrl!=null) {
                return resourceUrl.openConnection();
            } else {
                return null;
            }
        }
    }

    static class Factory implements URLStreamHandlerFactory {
        private final Map<String, URLStreamHandler> handlers;

        public Factory(String protocol, URLStreamHandler urlHandler) {
            handlers = new Hashtable<String, URLStreamHandler>();
            addHandler(protocol, urlHandler);
        }

        public void addHandler(String protocol, URLStreamHandler urlHandler) {
            handlers.put(protocol, urlHandler);
        }

        public URLStreamHandler createURLStreamHandler(String protocol) {
            return handlers.get(protocol);
        }
    }
}


