package com.uksw.instantmessanger;

/**
 * Created by siwakk on 2016-12-19.
 */

public class Host {
    private String host_address;
    private String host_name;
    private String host_domain;

    public Host(String host_address, String host_domain, String host_name )
    {
        this.host_address = host_address;
        this.host_domain = host_domain;
        this.host_name = host_name;
    }

    public String getHostAddress()
    {
        return this.host_address;
    }
    public String getHostDomain()
    {
        return this.host_domain;
    }
    public String getHostName()
    {
        return this.host_name;
    }

    public void setHostAdress(String host_address) {
        this.host_address = host_address;
    }
    public void setHostDomain(String host_domain) {
        this.host_domain = host_domain;
    }
    public void setHostName(String host_name) {
        this.host_name = host_name;
    }

}
