package org.waveapi.api.misc;

public class Side {
    public static boolean isServer;

    public static boolean isServer() {
        return isServer;
    }

    public static boolean isClient() {
        return !isServer();
    }
}
