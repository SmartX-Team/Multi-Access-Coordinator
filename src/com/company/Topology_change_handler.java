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

    public class Topology_change_handler {

        Resource_info resource_info = Resource_info.getInstance();
        List<Resource_info.Device_info> D_info = resource_info.getD_info();
        List<Resource_info.Host_info> Host_list = resource_info.getHost_list();
        List<Resource_info.Selection_Info> Selection_list = resource_info.getSelection_list();
        List<String> Path_list = resource_info.getPath_info();

        public void Topology_change_detector(){
            
        }
}
