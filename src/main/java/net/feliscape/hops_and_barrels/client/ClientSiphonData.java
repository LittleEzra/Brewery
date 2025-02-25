package net.feliscape.hops_and_barrels.client;

public class ClientSiphonData {
    private static int siphonedHearts;

    public static void set(int siphonedHearts){
        ClientSiphonData.siphonedHearts = siphonedHearts;
    }

    public static int getSiphonedHearts(){
        return siphonedHearts;
    }
}
