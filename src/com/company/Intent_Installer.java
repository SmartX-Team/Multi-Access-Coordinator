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
    String[] Src_Dst_Mac = resource_info.getSrc_Dst_Mac();
    String Path = null;

    private static String USER_ID ="jskim";
    private static String IP="203.237.53.130";
    private static String PASSWORD="0070";
    public static String Controoler_IP="203.237.53.130";
    public static String Controller_IP_Port="203.237.53.130:8181";
    public static String Controller_ID="karaf";
    public static String Controller_Pw="karaf";

    public String[] User_selection() {

        String[] input = new String[2];


        Scanner scan = new Scanner(System.in);

        input[0] = scan.next();
        input[1] = scan.next();

        return input;
    }

    public String[] Selection_list_iterator(List<Resource_info.Selection_Info> Selection_list,int i, int j){
        String[] input = new String[2];

        input[0] = Selection_list.get(i).Sel_Info.sel_ID;
        input[1] = Selection_list.get(j).Sel_Info.sel_ID;

        return input;
    }
    public String[] Host_selection(String input[]){
        int i,j;
        String[] Sel = new String [2];
        for(i=0; i<Selection_list.size(); i++){
            if(Selection_list.get(i).Can_Info.ID.toString().equals(input[0])){
               Sel[0] = Selection_list.get(i).Sel_Info.sel_ID;
            }
            else if(Selection_list.get(i).Can_Info.ID.toString().equals(input[1])){
                Sel[1] = Selection_list.get(i).Sel_Info.sel_ID;
            }
        }

        System.out.println("********User_sel1: "+ Sel[0] +"**************");
        System.out.println("********User_sel2: "+ Sel[1] +"**************");


/*        System.out.println("********ID_input[0]: "+ ID_input[0] +"**************");
        System.out.println("********ID_input[1]: "+ ID_input[1] +"**************");*/

        return Sel;
    }

    public void Intent_installer() throws Exception{
        int i;
        String Command1,Command2;
        if(Path_list.size() > 1) {
            for (i = 0; i < Path_list.size() - 1; i += 2) {
                Command1 = "";
                Command2 = "";

                Command1 += "add-point-intent -s "+ Src_Dst_Mac[0] + " -d " + Src_Dst_Mac[1] + " " + Path_list.get(i).toString() + " " + Path_list.get(i + 1).toString();
                Command2 += "add-point-intent -s "+ Src_Dst_Mac[1] + " -d " + Src_Dst_Mac[0] + " " + Path_list.get(i + 1).toString() + " " + Path_list.get(i).toString();
                System.out.println("Command1: " + Command1);
                System.out.println("Command2: " + Command2);
                Connect_to_ONOS_Controller(Command1);
                Connect_to_ONOS_Controller(Command2);
                System.out.println(" ");
            }
        }else{
            Command1 = "";
            Command2 = "";

            Command1 += "add-point-intent " + Path_list.get(1).toString() + " " + Path_list.get(2).toString();
            Command2 += "add-point-intent " + Path_list.get(2).toString() + " " + Path_list.get(1).toString();
            System.out.println("Command1: " + Command1);
            System.out.println("Command2: " + Command2);
            Connect_to_ONOS_Controller(Command1);
            Connect_to_ONOS_Controller(Command2);
            System.out.println(" ");
        }
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

    public void remove_All_Intent() throws Exception {
        System.out.println("Remove all Intent! ");
        String command = "remove-intent --purge org.onosproject.cli";
        Connect_to_ONOS_Controller(command);
    }
}
