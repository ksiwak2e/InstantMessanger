package com.uksw.instantmessanger;

/**
 * Created by siwakk on 2016-12-19.
 */

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class HostsModel {
    private static HostsModel hostsModel;
    private List<Host> hosts;

    public static HostsModel get(Context context)
    {
        if(hostsModel == null)
        {
            hostsModel = new HostsModel(context);
        }
        return  hostsModel;
    }

    private HostsModel(Context context)
    {
        hosts = new ArrayList<>();
        initHosts(context);

    }

    private void initHosts(Context context)
    {
        //Create the Foods and add them to the list;


        Host contact1 = new Host("192.168.1.1", "server.com", "User1");
        hosts.add(contact1);
        Host contact2 = new Host("192.168.1.2", "server.com", "User2");
        hosts.add(contact2);
    }

    public List<Host> getHosts()
    {
        return hosts;
    }

}