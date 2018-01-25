package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.naming.ldap.Control;
import java.io.*;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Status_Report sr = new Status_Report();

        sr.Read_teamplate();
        sr.get_Host_info();

        sr.Interface_status();

        sr.Print_Template_Status();
        sr.Print_Controller_HostInfo();
    }

}
