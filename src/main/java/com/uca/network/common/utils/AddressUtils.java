package com.uca.network.common.utils;

public class AddressUtils {

    public static  String highAddressUtil(String highAddr, String ipObligateHigh) {
        String[] array = highAddr.split("\\.");
        String afterAddrss = array[3];
        String frontAddrss = array[0] + "." + array[1] + "." + array[2];
        int i1 = (Integer.parseInt(afterAddrss) - Integer.parseInt(ipObligateHigh));
        String afterAddrssChange = String.valueOf(i1);
        return frontAddrss + "." + afterAddrssChange;

    }

    public static String lowAddressUtil(String lowAddress, String ipObligateLow) {
        String[] array = lowAddress.split("\\.");
        String afterAddrss = array[3];
        String frontAddrss = array[0] + "." + array[1] + "." + array[2];
        int i1 = (Integer.parseInt(afterAddrss) + Integer.parseInt(ipObligateLow));
        String afterAddrssChange = String.valueOf(i1);
        return frontAddrss + "." + afterAddrssChange;
    }


}
