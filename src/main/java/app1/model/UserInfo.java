package app1.model;

public class UserInfo
{
    private boolean isAdministrator = false;
    private String username;


    public UserInfo()
    {

    }

    public boolean getIsAdministrator()
    {
        return this.isAdministrator;
    }

    public void setIsAdministrator(boolean aValue)
    {
        this.isAdministrator = aValue;
    }

    public String getUserName()
    {
        return this.username;
    }

    public void setUserName(String aUserName)
    {
        this.username = aUserName;
    }
}