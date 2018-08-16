package com.company;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Backup_DB_man {
    static String DB_IP = "jdbc:mysql://203.237.53.132?autoReconnect=true&useSSL=false";
    static String DB_ID = "root";
    static String DB_PW = "0070";
    Resource_info resource_info = Resource_info.getInstance();
    List<Resource_info.Device_info> D_info = resource_info.getD_info();
    List<Resource_info.Host_info> Host_list = resource_info.getHost_list();
    List<Resource_info.Selection_Info> Selection_list = resource_info.getSelection_list();
    String[] Arr = new String[4];
    String[] tmp = new String[4];
    List DB_list = new ArrayList();


    public void DB_Access(){
        try{
            Connection connection = null;
            Statement statement = null;
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(DB_IP, DB_ID, DB_PW);

            statement = connection.createStatement();
            ResultSet rs = null;
            String sql ="";statement.executeQuery("use MA_Interface;");

            statement.executeUpdate("drop table if exists Interface_Info");

            statement.executeUpdate("create table Interface_Info (\n" +
                    "ID char(8), \n" +
                    "Wired varchar(32), Wired_conn varchar(4), Wired_MAC varchar(32),\tWired_loc varchar(32),\n" +
                    "Wifi varchar(32), Wifi_conn varchar(4),  Wifi_MAC varchar(32), \tWifi_loc varchar(32),\n" +
                    "LTE varchar(32), LTE_conn varchar(4), LTE_MAC varchar(32), LTE_loc varchar(32));");

        }catch (Exception e){
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public void DB_Push(){
        try{
            int i;
            Connection connection = null;
            Statement statement = null;
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(DB_IP, DB_ID, DB_PW);

            statement = connection.createStatement();
            ResultSet rs = null;
            String sql ="";
            statement.executeQuery("use MA_Interface;");


            for(i =0; i< D_info.size(); i++){

                sql += "insert into Interface_Info value(";
                sql += "\'" + D_info.get(i).Dev_ID + "\',";
                sql += "\'" + D_info.get(i).Wired_IP + "\'" + ", " + "\'"+D_info.get(i).Wired_conn+ "\'" + ", " + "\'"+ D_info.get(i).Wired_MAC + "\'"+ ", " + "\'"+ D_info.get(i).Wired_loc+ "\'"+ ", ";
                sql += "\'" + D_info.get(i).Wifi_IP+ "\'" + ", " + "\'" + D_info.get(i).Wifi_conn + "\'" + ", " + "\'"+ D_info.get(i).Wifi_MAC + "\'"+ ", " + "\'"+ D_info.get(i).Wifi_loc+ "\'"+ ", ";
                sql += "\'" + D_info.get(i).LTE_IP + "\'" + ", " + "\'"+ D_info.get(i).LTE_conn+ "\'" + ", "+ "\'" + D_info.get(i).LTE_MAC+ "\'" + ", "+ "\'" + D_info.get(i).LTE_loc+ "\'";
                sql += ");";
                System.out.println(sql);
                statement.executeUpdate(sql);
                sql = "";
            }
        }catch (Exception e){
            System.out.println("SQLException: " + e.getMessage());
        }


    }
    public Boolean DB_Compare() {
        Boolean flag = true;
        try{
            int i,j;
            Connection connection = null;
            Statement statement = null;
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            connection = DriverManager.getConnection(DB_IP, DB_ID, DB_PW);

            statement = connection.createStatement();
            ResultSet rs = null;
            String sql ="";
            statement.executeQuery("use MA_Interface;");
            sql = "select * from Interface_Info";
            rs = statement.executeQuery(sql);
            while(rs.next()){
                Arr[0] = rs.getString(1);
                Arr[1] = rs.getString(3);
                Arr[2] = rs.getString(7);
                Arr[3] = rs.getString(11);

                /*System.out.println("Arr[0]: " + Arr[0]);
                System.out.println("Arr[1]: " + Arr[1]);
                System.out.println("Arr[2]: " + Arr[2]);
                System.out.println("Arr[3]: " + Arr[3]);*/
                DB_list.add(Arr);
            }
            for(i=0; i<Selection_list.size(); i++){
                for(j=0; j<DB_list.size(); j++){
                    tmp = (String[]) DB_list.get(j);
                    if(Selection_list.get(i).Sel_Info.ID.equals(tmp[0])){
                        if (Selection_list.get(i).Sel_Info.Int.equals("Wired")) {
                            if(Selection_list.get(i).Sel_Info.conn == Arr[1].charAt(0)){
                                continue;
                            }else{
                                flag = false;
                                break;
                            }
                        }
                    }
                }
                if(flag == false){
                    break;
                }
            }

        }catch (Exception e){
            System.out.println("SQLException: " + e.getMessage());
        }
        return flag;
    }
}
