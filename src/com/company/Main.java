package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.awt.image.ImageWatched;

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
        int k =0;
        Resource_info resource_info = null;
        List<Resource_info.Device_info> D_info = new ArrayList<Resource_info.Device_info>();
        List<Resource_info.Host_info> Host_list = new ArrayList<Resource_info.Host_info>();
        List<Resource_info.Link_info> Link_list = new ArrayList<Resource_info.Link_info>();
        String Path_list = null;
        Boolean flag;
        String[] user_input = new String[2];

        Status_Report sr = new Status_Report();
        Interfae_Selection is = new Interfae_Selection();
        Intent_Installer ii = new Intent_Installer();
        Topology_change_handler tcp = new Topology_change_handler();
        Backup_DB_man db = new Backup_DB_man();


        sr.Read_teamplate();
        sr.get_Host_info(Host_list);
        sr.get_Link_info(Link_list);
        sr.Interface_status(Host_list);


        is.Int_selection();
        is.Interface_Selection_Result();


        user_input = ii.User_selection();
        sr.get_Path_info(user_input);
        sr.Print_Controller_PathInfo();
        sr.Path_parser();
        sr.Print_Parsing_Path_result();
        ii.Intent_installer();

        /*Thread.sleep(60000);*/
        while(true){
            flag = tcp.Topology_change_detector(Host_list, Link_list);

            System.out.println(flag);

            Thread.sleep(60000);
        }



    }

}
