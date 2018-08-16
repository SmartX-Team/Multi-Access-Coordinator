package com.company;

import sun.swing.BakedArrayList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interfae_Selection {
    Resource_info resource_info = Resource_info.getInstance();
    List<Resource_info.Device_info> D_info = resource_info.getD_info();
    List<Resource_info.Host_info> Host_list = resource_info.getHost_list();
    List<Resource_info.Selection_Info> Selection_list = resource_info.getSelection_list();
    List<String> Path_list = new ArrayList<String>();
    String Path = null;




    public void Int_selection () {
        int i, j;
        for (i = 0; i < D_info.size(); i++) {
            Resource_info.Selection_Info Tmp_Sel = new Resource_info.Selection_Info();


            if (D_info.get(i).Wired_conn == 'O') {
                Tmp_Sel.Sel_Info.ID = D_info.get(i).Dev_ID;
                Tmp_Sel.Sel_Info.sel_ID = D_info.get(i).Wired_ID;
                Tmp_Sel.Sel_Info.Int = "Wired";
                Tmp_Sel.Sel_Info.IP = D_info.get(i).Wired_IP;
                Tmp_Sel.Sel_Info.MAC = D_info.get(i).Wired_MAC;
                Tmp_Sel.Sel_Info.loc = D_info.get(i).Wired_loc;
                Tmp_Sel.Sel_Info.conn = D_info.get(i).Wired_conn;

                Tmp_Sel.Can_Info.ID = D_info.get(i).Dev_ID;

                Tmp_Sel.Can_Info.can_ID1 = D_info.get(i).Wifi_ID;
                Tmp_Sel.Can_Info.Int1 = "Wifi";
                Tmp_Sel.Can_Info.IP1 = D_info.get(i).Wifi_IP;
                Tmp_Sel.Can_Info.MAC1 = D_info.get(i).Wifi_MAC;
                Tmp_Sel.Can_Info.loc1 = D_info.get(i).Wifi_loc;
                Tmp_Sel.Can_Info.conn1 = D_info.get(i).Wifi_conn;

                Tmp_Sel.Can_Info.can_ID2 = D_info.get(i).LTE_ID;
                Tmp_Sel.Can_Info.Int2 = "LTE";
                Tmp_Sel.Can_Info.IP2 = D_info.get(i).LTE_IP;
                Tmp_Sel.Can_Info.MAC2 = D_info.get(i).LTE_MAC;
                Tmp_Sel.Can_Info.loc2 = D_info.get(i).LTE_loc;
                Tmp_Sel.Can_Info.conn2 = D_info.get(i).LTE_conn;

            } else if (D_info.get(i).Wifi_conn == 'O') {

                Tmp_Sel.Sel_Info.ID = D_info.get(i).Dev_ID;
                Tmp_Sel.Sel_Info.sel_ID = D_info.get(i).Wifi_ID;
                Tmp_Sel.Sel_Info.Int = "Wifi";
                Tmp_Sel.Sel_Info.IP = D_info.get(i).Wifi_IP;
                Tmp_Sel.Sel_Info.MAC = D_info.get(i).Wifi_MAC;
                Tmp_Sel.Sel_Info.loc = D_info.get(i).Wifi_loc;
                Tmp_Sel.Sel_Info.conn = D_info.get(i).Wifi_conn;

                Tmp_Sel.Can_Info.ID = D_info.get(i).Dev_ID;

                Tmp_Sel.Can_Info.can_ID1 = D_info.get(i).Wired_ID;
                Tmp_Sel.Can_Info.Int1 = "Wired";
                Tmp_Sel.Can_Info.IP1 = D_info.get(i).Wired_IP;
                Tmp_Sel.Can_Info.MAC1 = D_info.get(i).Wired_MAC;
                Tmp_Sel.Can_Info.loc1 = D_info.get(i).Wired_loc;
                Tmp_Sel.Can_Info.conn1 = D_info.get(i).Wired_conn;

                Tmp_Sel.Can_Info.can_ID2 = D_info.get(i).LTE_ID;
                Tmp_Sel.Can_Info.Int2 = "LTE";
                Tmp_Sel.Can_Info.IP2 = D_info.get(i).LTE_IP;
                Tmp_Sel.Can_Info.MAC2 = D_info.get(i).LTE_MAC;
                Tmp_Sel.Can_Info.loc2 = D_info.get(i).LTE_loc;
                Tmp_Sel.Can_Info.conn2 = D_info.get(i).LTE_conn;

            } else if (D_info.get(i).LTE_conn == 'O') {

                Tmp_Sel.Sel_Info.ID = D_info.get(i).Dev_ID;

                Tmp_Sel.Sel_Info.sel_ID = D_info.get(i).LTE_ID;
                Tmp_Sel.Sel_Info.Int = "LTE";
                Tmp_Sel.Sel_Info.IP = D_info.get(i).LTE_IP;
                Tmp_Sel.Sel_Info.MAC = D_info.get(i).LTE_MAC;
                Tmp_Sel.Sel_Info.loc = D_info.get(i).LTE_loc;
                Tmp_Sel.Sel_Info.conn = D_info.get(i).LTE_conn;

                Tmp_Sel.Can_Info.ID = D_info.get(i).Dev_ID;

                Tmp_Sel.Can_Info.IP1 = D_info.get(i).Wired_IP;
                Tmp_Sel.Can_Info.Int1 = "Wired";
                Tmp_Sel.Can_Info.IP1 = D_info.get(i).Wired_IP;
                Tmp_Sel.Can_Info.MAC1 = D_info.get(i).Wired_MAC;
                Tmp_Sel.Can_Info.loc1 = D_info.get(i).Wired_loc;
                Tmp_Sel.Can_Info.conn1 = D_info.get(i).Wired_conn;

                Tmp_Sel.Can_Info.can_ID2 = D_info.get(i).Wifi_ID;
                Tmp_Sel.Can_Info.Int2 = "Wifi";
                Tmp_Sel.Can_Info.IP2 = D_info.get(i).Wifi_IP;
                Tmp_Sel.Can_Info.MAC2 = D_info.get(i).Wifi_MAC;
                Tmp_Sel.Can_Info.loc2 = D_info.get(i).Wifi_loc;
                Tmp_Sel.Can_Info.conn2 = D_info.get(i).Wifi_conn;
            }
            Selection_list.add(Tmp_Sel);



        }
    }


    public void Interface_Selection_Result(){
        int i;
        for(i=0; i<Selection_list.size(); i++){
            System.out.println("------------"+ Selection_list.get(i).Sel_Info.ID + "------------");
            System.out.println("============Selected Interface Information============");
            System.out.println("ID: " + Selection_list.get(i).Sel_Info.sel_ID);
            System.out.println("Interface: " + Selection_list.get(i).Sel_Info.Int);
            System.out.println("Interface IP: " + Selection_list.get(i).Sel_Info.IP);
            System.out.println("Interface MAC: " + Selection_list.get(i).Sel_Info.MAC);
            System.out.println("Interface location: " + Selection_list.get(i).Sel_Info.loc);

            System.out.println("***********Candidate Interface Information***********");
            if(!Selection_list.get(i).Can_Info.IP1.equals("NONE")) {
                System.out.println("ID: " + Selection_list.get(i).Can_Info.can_ID1);
                System.out.println("Interface1: " + Selection_list.get(i).Can_Info.Int1);
                System.out.println("Interface1 IP: " + Selection_list.get(i).Can_Info.IP1);
                System.out.println("Interface1 MAC: " + Selection_list.get(i).Can_Info.MAC1);
                System.out.println("Interface1 location: " + Selection_list.get(i).Can_Info.loc1);
            }
            if(!Selection_list.get(i).Can_Info.IP2.equals("NONE")) {
                System.out.println("ID: " + Selection_list.get(i).Can_Info.can_ID2);
                System.out.println("Interface2: " + Selection_list.get(i).Can_Info.Int2);
                System.out.println("Interface2 IP: " + Selection_list.get(i).Can_Info.IP2);
                System.out.println("Interface2 MAC: " + Selection_list.get(i).Can_Info.MAC2);
                System.out.println("Interface2 location: " + Selection_list.get(i).Can_Info.loc2);
            }
            if(Selection_list.get(i).Can_Info.IP2.equals("NONE") && Selection_list.get(i).Can_Info.IP1.equals("NONE")){
                System.out.println("No Candidate Interface");
            }
            System.out.println(" ");
        }

    }

}
