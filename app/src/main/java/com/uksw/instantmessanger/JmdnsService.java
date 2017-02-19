package com.uksw.instantmessanger;

/**
 * Created by siwakk on 2017-01-20.
 */

//imports for jmdns from external library
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager.MulticastLock;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class JmdnsService {

    public JmdnsService(Context context){
        this.context=context;
    }
    MulticastLock lock;

    Context context;
    private String type = "_presence._tcp.local.";
    private JmDNS jmdns = null;
    private ServiceListener listener = null;
    private ServiceInfo serviceInfo;
    List<Host> hosts = null;

    public void setUp() {
        WifiManager wifi = (WifiManager) context.getSystemService(android.content.Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifi.getConnectionInfo().getIpAddress());
        InetAddress addr = null;
        hosts=new ArrayList<>();
        try {
            addr = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String host = addr.getHostName();
        lock = wifi.createMulticastLock("mylockthereturn");
        lock.setReferenceCounted(true);
        lock.acquire();
        try {
            jmdns = JmDNS.create(addr,host);
            jmdns.addServiceListener(type, listener = new ServiceListener() {

                @Override
                public void serviceResolved(ServiceEvent ev) {
                    ev.getInfo().getHostAddresses();
                    ev.getName();
                    ev.getInfo().getDomain();
                    Host hostToAdd = new Host(ev.getInfo().getHostAddress(), ev.getInfo().getDomain(), ev.getName());
                    hosts.add(hostToAdd);
                    SearchHostsActivity.updateAdapter(hosts);
                    String msg="Service resolved: " + ev.getInfo().getQualifiedName() + " port:" + ev.getInfo().getPort();
                    Log.d("JMDNSService",msg);
                }

                @Override
                public void serviceRemoved(ServiceEvent ev) {
                    String msg="Service removed: " + ev.getName();
                    Log.d("JMDNSService",msg);
                }

                @Override
                public void serviceAdded(ServiceEvent event) {
                    // Required to force serviceResolved to be called again (after the first search)
                    jmdns.requestServiceInfo(event.getType(), event.getName(), 1);
                }
            });
            //serviceInfo = ServiceInfo.create("_presence._tcp.local.", "AndroidTest2", 0, "plain test service from android");
            //jmdns.registerService(serviceInfo);
            createService(getPhoneName());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public String getPhoneName() {
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = myDevice.getName();
        return deviceName;
    }

    public void removeService(){
        if(jmdns!=null) {
            if (listener != null) {
                jmdns.removeServiceListener(type, listener);
                listener = null;
            }
            jmdns.unregisterAllServices();
            try {
                jmdns.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            jmdns = null;
        }
        if (lock.isHeld()){
            lock.release();
        }

    }

    public ServiceInfo createService (String broadcast_name){
        serviceInfo = ServiceInfo.create("_presence._tcp.local.", broadcast_name, 0, "plain test service from android");
        try {
            jmdns.registerService(serviceInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serviceInfo;
    }

    ServiceInfo getServiceInfo(){
        return this.serviceInfo;
    }
}
