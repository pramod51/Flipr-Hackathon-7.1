package com.example.payo;

public class AdapterItem {
    private String fName;
    private String lName;
    private String email;
    private String profileUrl;
    public AdapterItem(String f_name,String l_name,String profile_url,String nemail){
        fName=f_name;
        lName=l_name;
        profileUrl=profile_url;
        email=nemail;
    }


    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileUrl() {
        return profileUrl;
    }



}
