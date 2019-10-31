package com.uca.network.common.utils;

public class AddressUtils {

    public static  String highAddressUtil(String highAddr, String ipObligateHigh) {
        String[] array = highAddr.split("\\.");
        String AfterAddrss = array[3];
        String frontAddrss = array[0] + "." + array[1] + "." + array[2];
        int i1 = (Integer.parseInt(AfterAddrss) - Integer.parseInt(ipObligateHigh));
        String AfterAddrssChange = String.valueOf(i1);
        return frontAddrss + "." + AfterAddrssChange;

    }

    public static String lowAddressUtil(String lowAddress, String ipObligateLow) {
        String[] array = lowAddress.split("\\.");
        String AfterAddrss = array[3];
        String frontAddrss = array[0] + "." + array[1] + "." + array[2];
        int i1 = (Integer.parseInt(AfterAddrss) + Integer.parseInt(ipObligateLow));
        String AfterAddrssChange = String.valueOf(i1);
        return frontAddrss + "." + AfterAddrssChange;
    }


}
