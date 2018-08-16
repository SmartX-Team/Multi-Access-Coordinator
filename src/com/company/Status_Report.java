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


public class Status_Report {
    private static String USER_ID ="jskim";
    private static String IP="203.237.53.130";
    private static String PASSWORD="0070";
    public static String Controoler_IP="203.237.53.130";
    public static String Controller_IP_Port="203.237.53.130:8181";
    public static String Controller_ID="karaf";
    public static String Controller_Pw="karaf";
    Resource_info resource_info = Resource_info.getInstance();
    List<Resource_info.Device_info> D_info = resource_info.getD_info();
    List<Resource_info.Host_info> Host_list = resource_info.getHost_list();
    List<Resource_info.Link_info> Link_list = resource_info.getLink_info();
    List<String> Path_list = resource_info.getPath_info();
    //List<Resource_info.Path_info> Path_list = resource_info.getPath_info();
    String Path = resource_info.getPath();



    public void Read_teamplate() throws Exception{
        JSch jsch = new JSch();

        Session session = jsch.getSession(USER_ID,IP,22);
        session.setPassword(PASSWORD);
        session.setConfig("StrictHostKeyChecking","no");
        session.connect();

        String command = "cat ~/Templates/deviceinfo.json";

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
                System.out.println("exit-status: "+channel.getExitStatus());
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        //System.out.println("Template[JSON Format]: "+template);
        Parsing_Template(template);

        channel.disconnect();
        session.disconnect();
    }

    public void Parsing_Template (String template) throws Exception {
        int i;

        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(template);
        JSONArray InfoArray = (JSONArray) jsonObject.get("DeviceInfo");

        for(i=0; i<InfoArray.size(); i++){
            Resource_info.Device_info tDinfo = new Resource_info.Device_info();
            JSONObject Object = (JSONObject) InfoArray.get(i);
            tDinfo.Dev_ID = Object.get("id").toString();
            tDinfo.Wired_IP = Object.get("Wired").toString();
            tDinfo.Wifi_IP = Object.get("Wifi").toString();
            tDinfo.LTE_IP = Object.get("LTE").toString();
            tDinfo.Wired_conn = 'X';
            tDinfo.Wifi_conn = 'X';
            tDinfo.LTE_conn = 'X';

            D_info.add(tDinfo);
        }
    }

    public void Print_Template_Status (){
        int i;
        System.out.println("Initial Multi-Access Device's interface information : ");
        for(i=0; i<D_info.size(); i++){
            System.out.println("Device ID: " + D_info.get(i).Dev_ID);
            System.out.println("Wired: " + D_info.get(i).Wired_IP);
            System.out.println("Wired conn: " + D_info.get(i).Wired_conn);
            System.out.println("Wired MAC: " + D_info.get(i).Wired_MAC);
            System.out.println("Wired location: " + D_info.get(i).Wired_loc);

            System.out.println("WiFi: " + D_info.get(i).Wifi_IP);
            System.out.println("WiFi conn: " + D_info.get(i).Wifi_conn);
            System.out.println("WiFi MAC: " + D_info.get(i).Wifi_MAC);
            System.out.println("WiFi location: " + D_info.get(i).Wifi_loc);

            System.out.println("LTE: " + D_info.get(i).LTE_IP);
            System.out.println("LTE conn: " + D_info.get(i).LTE_conn);
            System.out.println("LTE MAC: " + D_info.get(i).LTE_MAC);
            System.out.println("LTE location: " + D_info.get(i).LTE_loc);

        }

        System.out.println(" ");
        System.out.println(" ");
    }

    public void get_Host_info() throws  Exception{
        String DEVICE_API_URL= "http://203.237.53.130:8181/onos/v1/hosts";
        URL onos = null;
        String tIP;
        String buffer = URL_REQUEST(Controller_ID,Controller_Pw,DEVICE_API_URL,onos);
        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(buffer);
        JSONArray InfoArray = (JSONArray) jsonObject.get("hosts");

        for (int i =0; i<InfoArray.size();i++) {
            Resource_info.Host_info Temp_h = new Resource_info.Host_info();
            JSONObject Object = (JSONObject) InfoArray.get(i);
            Temp_h.ID = Object.get("id").toString();
            Temp_h.MAC = Object.get("mac").toString();
            int ip_len = Object.get("ipAddresses").toString().length();

            if (ip_len > 18) {
                tIP = Object.get("ipAddresses").toString().split(",")[0];
                Temp_h.IP = tIP.substring(2, tIP.length()-1).toString();
            } else if (ip_len > 3) {
                Temp_h.IP = Object.get("ipAddresses").toString().subSequence(2, ip_len - 2).toString();
            }

            JSONArray Object2 = (JSONArray) Object.get("locations");
            for(int j=0; j<Object2.size(); j++){
                JSONObject Obj2 = (JSONObject) Object2.get(j);
                Temp_h.location = Obj2.get("elementId").toString();
                Temp_h.location += "/"+Obj2.get("port").toString();
            }
/*            JSONArray Array2 = (JSONArray) Object.get("locations");
            for(int j=0; j<Array2.size(); j++) {
                JSONObject Object2 = (JSONObject) Array2.get(i);
                Temp_h.location = Object2.get("elementId").toString();
                Temp_h.location += "/" + Object2.get("port").toString();
            }*/
            Host_list.add(Temp_h);

        }
    }

    public void get_Link_info() throws  Exception{
        String DEVICE_API_URL= "http://203.237.53.130:8181/onos/v1/links";
        URL onos = null;
        String tIP;
        String buffer = URL_REQUEST(Controller_ID,Controller_Pw,DEVICE_API_URL,onos);
        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(buffer);
        JSONArray InfoArray = (JSONArray) jsonObject.get("links");

        for (int i =0; i<InfoArray.size();i++) {
            Resource_info.Link_info Temp_l = new Resource_info.Link_info();
            JSONObject Object = (JSONObject) InfoArray.get(i);
            Temp_l.type = Object.get("type").toString();
            Temp_l.state = Object.get("state").toString();

            JSONObject Object2 = (JSONObject) Object.get("src");
            Temp_l.src = Object2.get("device").toString();
            Temp_l.src += "/" + Object2.get("port").toString();

            JSONObject Object3 = (JSONObject) Object.get("dst");
            Temp_l.dst = Object3.get("device").toString();
            Temp_l.dst += "/" + Object3.get("port").toString();
/*            JSONArray Array2 = (JSONArray) Object.get("locations");
            for(int j=0; j<Array2.size(); j++) {
                JSONObject Object2 = (JSONObject) Array2.get(i);
                Temp_h.location = Object2.get("elementId").toString();
                Temp_h.location += "/" + Object2.get("port").toString();
            }*/
            Link_list.add(Temp_l);

        }
    }

    public void get_Path_info(String[] input) throws  Exception{
        input[0] = input[0].replace(":","%3A");
        input[0] = input[0].replace("/","%2F");

        input[1] = input[1].replace(":","%3A");
        input[1] = input[1].replace("/","%2F");

        String DEVICE_API_URL= "http://203.237.53.130:8181/onos/v1/paths/"+input[0]+"/"+input[1];
        URL onos = null;
        int i, j;
        String buffer = URL_REQUEST(Controller_ID,Controller_Pw,DEVICE_API_URL,onos);
        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(buffer);
        JSONArray InfoArray = (JSONArray) jsonObject.get("paths");


        JSONObject jsonObject1 = (JSONObject) InfoArray.get(0);
        JSONArray InfoArray1 = (JSONArray) jsonObject1.get("links");

        for(i=0; i<InfoArray1.size(); i++) {
            String Temp_p = null;
            JSONObject jsonObject2 = (JSONObject) InfoArray1.get(i);
            Temp_p += jsonObject2.get("src") + "%" + jsonObject2.get("dst");

            Path += Temp_p;
            if(i != InfoArray1.size()-1){
                Path += "%";
            }
        }

    }

    public static String URL_REQUEST(String USERNAME, String PASSWORD, String DEVICE_API_URL, URL onos) throws IOException {

        try {
            onos = new URL(DEVICE_API_URL);
            Authenticator.setDefault(new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME,PASSWORD.toCharArray());
                }
            });
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        String buffer="";
        HttpURLConnection urlConnection = (HttpURLConnection) onos.openConnection();
        //System.out.println(urlConnection.getResponseCode());
        int responseCode = urlConnection.getResponseCode();
        if(responseCode==200){
            InputStream is = urlConnection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = in.readLine())!=null){
                buffer = line;
            }
            //System.out.println(buffer);
        }
        return buffer;
    }

    public void Print_Controller_HostInfo (){
        int i;
        System.out.println("Host information from the SDN Controller: ");
        for(i =0; i<Host_list.size(); i++){
            System.out.println("ID: " + Host_list.get(i).ID);
            System.out.println("MAC: " + Host_list.get(i).MAC);
            System.out.println("IP: " + Host_list.get(i).IP);
            System.out.println("Location: " + Host_list.get(i).location);
        }
        System.out.println("length of H_list: "+Host_list.size());

        System.out.println(" ");
        System.out.println(" ");
    }

    public void Print_Controller_LinkInfo(){
        int i;
        System.out.println("Link information from the SDN Controller");
        for(i=0; i<Link_list.size(); i++){
            System.out.println("SRC: " + Link_list.get(i).src);
            System.out.println("dst: " + Link_list.get(i).dst);
            System.out.println("type: " + Link_list.get(i).type);
            System.out.println("state: " + Link_list.get(i).state);
        }

        System.out.println("length of Link_list: "+Link_list.size());

        System.out.println(" ");
        System.out.println(" ");
    }

    public void Interface_status(){
        int i,j;
        System.out.println("Check IoT device's connectivity");

        for(i=0; i<D_info.size(); i++){
            for(j=0; j< Host_list.size(); j++){
                if(D_info.get(i).Wired_IP.toString().equals(Host_list.get(j).IP.toString())){
                    D_info.get(i).Wired_conn = 'O';
                    D_info.get(i).Wired_ID = Host_list.get(j).ID;
                    D_info.get(i).Wired_loc = Host_list.get(j).location.toString();
                    D_info.get(i).Wired_MAC = Host_list.get(j).MAC.toString();
                }
                else if(D_info.get(i).Wifi_IP.toString().equals(Host_list.get(j).IP.toString())){
                    D_info.get(i).Wifi_conn = 'O';
                    D_info.get(i).Wifi_ID = Host_list.get(j).ID;
                    D_info.get(i).Wifi_loc = Host_list.get(j).location.toString();
                    D_info.get(i).Wifi_MAC = Host_list.get(j).MAC.toString();
                }
                else if(D_info.get(i).LTE_IP.toString().equals(Host_list.get(j).IP.toString())){
                    D_info.get(i).LTE_conn = 'O';
                    D_info.get(i).LTE_ID = Host_list.get(j).ID;
                    D_info.get(i).LTE_loc = Host_list.get(j).location.toString();
                    D_info.get(i).LTE_MAC = Host_list.get(j).MAC.toString();
                }
                else
                    continue;
            }
        }
    }

    public void Print_Controller_PathInfo(){
        int i;
        System.out.println("Path information from the SDN Controller you selected");

        System.out.println(Path);
        System.out.println("length of Link_list: "+Path.length());

        System.out.println(" ");
        System.out.println(" ");
    }

    public void Path_parser(){
        String[] path = Path.split("%");
        String a;
        int i;
        for(i=1; i<path.length-1; i++) {
            a = path[i].substring(path[i].length() - 21, path[i].length() - 2);
            a += "/" + path[i].substring(path[i].length() - 34, path[i].length() - 33);

            Path_list.add(a);
        }
    }
    public void Print_Parsing_Path_result(){
        int i;
        System.out.println("Parsed Path information");

        for(i=0; i<Path_list.size(); i++){
            System.out.println(Path_list.get(i).toString());
        }
        System.out.println("length of Link_list: "+Path.length());

        System.out.println(" ");
        System.out.println(" ");
    }

}
