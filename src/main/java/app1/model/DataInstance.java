package app1.model;

import java.sql.Timestamp;

public class DataInstance
{
    private int log_id;
    private Timestamp log_date;
    private Timestamp begin_ts;
    private Timestamp end_ts;
    private String type;
    private String src_ip;
    private String dst_ip;
    private String rule;
    private String app;
    private String src_network;
    private String dst_network;
    private int bytes;
    private int src_port;
    private int dst_port;
    private String protocol;


    public DataInstance()
    {

    }

    public int getLogID()
    {
        return this.log_id;
    }
    public void setLogID(int aLogID)
    {
        this.log_id = aLogID;
    }

    public Timestamp getLogDate()
    {
        return this.log_date;
    }
    public void setLogDate(Timestamp aLogDate)
    {
        this.log_date = aLogDate;
    }

    public Timestamp getBeginTS()
    {
        return this.begin_ts;
    }
    public void setBeginTS(Timestamp aBeginTS)
    {
        this.begin_ts = aBeginTS;
    }

    public Timestamp getEndTS()
    {
        return this.end_ts;
    }
    public void setEndTS(Timestamp aEndTS)
    {
        this.end_ts = aEndTS;
    }

    public String getType()
    {
        return this.type;
    }
    public void setType(String aType)
    {
        this.type = aType;
    }

    public String getSrcIP()
    {
        return this.src_ip;
    }
    public void setSrcIP(String aSrcIP)
    {
        this.src_ip = aSrcIP;
    }

    public String getDstIP()
    {
        return this.dst_ip;
    }
    public void setDstIP(String aDstIP)
    {
        this.dst_ip = aDstIP;
    }

    public String getRule()
    {
        return this.rule;
    }
    public void setRule(String aRule)
    {
        this.rule = aRule;
    }

    public String getApp()
    {
        return this.app;
    }
    public void setApp(String aApp)
    {
        this.app = aApp;
    }

    public String getSrcNetwork()
    {
        return this.src_network;
    }
    public void setSrcNetwork(String aSrcNetwork)
    {
        this.src_network = aSrcNetwork;
    }

    public String getDstNetwork()
    {
        return this.dst_network;
    }
    public void setDstNetwork(String aDstNetwork)
    {
        this.dst_network = aDstNetwork;
    }

    public int getBytes()
    {
        return this.bytes;
    }
    public void setBytes(int aBytes)
    {
        this.bytes = aBytes;
    }

    public int getSrcPort()
    {
        return this.src_port;
    }
    public void setSrcPort(int aSrcPort)
    {
        this.src_port = aSrcPort;
    }

    public int getDstPort()
    {
        return this.dst_port;
    }
    public void setDstPort(int aDstPort)
    {
        this.dst_port = aDstPort;
    }

    public String getProtocol()
    {
        return this.protocol;
    }
    public void setProtocol(String aProtocol)
    {
        this.protocol = aProtocol;
    }

}