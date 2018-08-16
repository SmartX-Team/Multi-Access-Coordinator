package com.company;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.Scanner;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


public class Intent_Installer {
    Resource_info resource_info = Resource_info.getInstance();
    List<Resource_info.Device_info> D_info = resource_info.getD_info();
    List<Resource_info.Host_info> Host_list = resource_info.getHost_list();
    List<Resource_info.Selection_Info> Selection_list = resource_info.getSelection_list();
    List<String> Path_list = resource_info.getPath_info();
    String Path = null;

    private static String USER_ID ="jskim";
    private static String IP="203.237.53.130";
    private static String PASSWORD="0070";
    public static String Controoler_IP="203.237.53.130";
    public static String Controller_IP_Port="203.237.53.130:8181";
    public static String Controller_ID="karaf";
    public static String Controller_Pw="karaf";

    public String[] User_selection(){
        int i,j;
        String User_sel1 = null, User_sel2 = null;
        String[] input = new String[2];
        String[] ID_input = new String[2];
        Scanner scan = new Scanner(System.in);

        input[0] = scan.next();
        input[1] = scan.next();

        for(i=0; i<Selection_list.size(); i++){
            if(Selection_list.get(i).Can_Info.ID.toString().equals(input[0])){
                User_sel1 = Selection_list.get(i).Sel_Info.sel_ID;
            }
            else if(Selection_list.get(i).Can_Info.ID.toString().equals(input[1])){
                User_sel2 = Selection_list.get(i).Sel_Info.sel_ID;
            }
        }

        System.out.println("********User_sel1: "+ User_sel1 +"**************");
        System.out.println("********User_sel2: "+ User_sel2 +"**************");

        ID_input[0] = User_sel1;
        ID_input[1] = User_sel2;

/*        System.out.println("********ID_input[0]: "+ ID_input[0] +"**************");
        System.out.println("********ID_input[1]: "+ ID_input[1] +"**************");*/

        return ID_input;
    }

    public void Intent_installer(){
        int i;
        System.out.println("Parsed Path information");

        for(i=0; i<Path_list.size(); i++){
            System.out.println(Path_list.get(i).toString());
        }
        System.out.println("length of Path_list: "+Path_list.size());

        System.out.println(" ");
        System.out.println(" ");
    }

    public void Connect_to_ONOS_Controller(String command) throws Exception{

        JSch jsch = new JSch();
        Session session = jsch.getSession(Controller_ID, Controoler_IP, 8101);
        session.setPassword(Controller_Pw);
        session.setConfig("StrictHostKeyChecking","no");
        session.connect();

        //String command = "hosts";

        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(command);
        channel.connect();
        InputStream in =channel.getInputStream();
        byte[] tmp = new byte[1024];
        String template="";
        while (true){
            while (in.available()>0){
                int i = in.read(tmp,0,1024);
                if (i<0)
                    break;
                //System.out.println(new String(tmp,0,i));
                template = new String(tmp,0,i);
            }
            if (channel.isClosed()) {
                //System.out.println("exit-status: "+channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(100);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        channel.disconnect();
        session.disconnect();
    }
}
