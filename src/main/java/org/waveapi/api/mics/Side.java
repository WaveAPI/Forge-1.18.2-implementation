package org.waveapi.api.mics;

public class Side {
    public static boolean isServer;

    public static boolean isServer() {
        return isServer;
    }

    public static boolean isClient() {
        return !isServer();
    }
}
