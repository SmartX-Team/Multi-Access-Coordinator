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
        int k =0;
        Resource_info resource_info = null;
        List<Resource_info.Device_info> D_info = new ArrayList<Resource_info.Device_info>();
        List<Resource_info.Host_info> Host_list = new ArrayList<Resource_info.Host_info>();
        String Path_list = null;
        Boolean flag;
        Status_Report sr = new Status_Report();
        Interfae_Selection is = new Interfae_Selection();
        Intent_Installer ii = new Intent_Installer();
        Backup_DB_man db = new Backup_DB_man();

        sr.Read_teamplate();
        sr.get_Host_info();
        sr.Interface_status();

        // 링크 정보 출력
        /*sr.get_Link_info();
        sr.Print_Controller_LinkInfo();*/


        /*System.out.println("######## After compare with SDN (Before Selection) ##########");
        sr.Print_Template_Status();*/
        is.Int_selection();
        is.Interface_Selection_Result();



        sr.get_Path_info(ii.User_selection());
        sr.Print_Controller_PathInfo();
        sr.Path_parser();
        ii.Intent_installer();
        /*sr.Print_Parsing_Path_result();*/

        /*db.DB_Access();
        db.DB_Push();
        while(true) {
            sr.Read_teamplate();
            sr.get_Host_info();
            sr.Interface_status();
            is.Interface_Selection_Result();

            if(db.DB_Compare()){
                System.out.println("Interface are not Changed [" +k+ "]");
                k++;

            }else{
                System.out.println("Interface Changed [" +k+ "]");
                k++;

            }

            Thread.sleep(60000);
        }*/
    }

}
