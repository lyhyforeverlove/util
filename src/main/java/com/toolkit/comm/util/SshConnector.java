package com.toolkit.comm.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:
 */
public class SshConnector {

    private String host;
    private String name;
    private String password;
    private String publicKeyPath;
    private Connection conn = null;
    private Session session = null;

    private int connectTimeout = 10 * 1000;
    private int kexTimeout = 20 * 1000;

    public SshConnector(String host, String name, String password){
        this.host = host;
        this.name = name;
        this.password = password;
    }

    public SshConnector(String host, String name, String password, String publicKeyPath){
        this.host = host;
        this.name = name;
        this.password = password;
        this.publicKeyPath = publicKeyPath;
    }

    private void connect() throws IOException {

        try{
            conn = new Connection(host);
            conn.connect(null, connectTimeout, kexTimeout);
        } catch (IOException e){
            throw new IOException("Connect host " + host + " failed." + e.getMessage(), e);
        }

    }

    public void setConnectTimeout(int connectTimeout){
        this.connectTimeout = connectTimeout;
    }

    public void setKexTimeout(int kexTimeout){
        this.kexTimeout = kexTimeout;
    }

    private boolean authenticat() throws IOException {

        boolean isAuthenticated;
        try{
            if(publicKeyPath != null) {
                //public key authenticat
                isAuthenticated = conn.authenticateWithPublicKey(name, new File(publicKeyPath), password);
            }else{
                //password authenticat
                isAuthenticated = conn.authenticateWithPassword(name, password);
            }
        } catch (IOException e){
            throw new IOException("Authentication failed. host:" + host + " " + e.getMessage(), e);
        }
        return isAuthenticated;
    }

    private void openSession() throws IOException {

        try{
            session = conn.openSession();
        } catch (IOException e){
            throw new IOException("open session failed." + " " + e.getMessage(), e);
        }

    }

    public Map<String, String> excuteCommand(String command) throws IOException {
        try {
            Map<String, String> map = new HashMap<>();
            connect();

            boolean isAuthenticated;
            isAuthenticated = authenticat();

            if (!isAuthenticated) {
                throw new IOException("Authentication failed. host:" + host);
            }

            openSession();
            session.execCommand(command);

            InputStream stdout = new StreamGobbler(session.getStdout());
            InputStream stderr = new StreamGobbler(session.getStderr());
            StringBuilder str = new StringBuilder();
            BufferedReader br;

            if(stderr.read() != -1){
                br = new BufferedReader(new InputStreamReader(stderr));
                while (true){
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }else{
                        str.append(line);
                    }
                }
                map.put("stderr", str.toString());
            }else{
                br = new BufferedReader(new InputStreamReader(stdout));

                while (true){
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }else{
                        str.append(line + System.getProperty("line.separator"));
                    }
                }
                map.put("stdout", str.toString());
            }
            return map;
        } catch (IOException e){
            throw new IOException(e);
        } finally {
            if(session != null){
                session.close();
            }
            if(conn != null){
                conn.close();
            }
        }

    }

    public static void main(String[] args) throws IOException {

        SshConnector ssh = new SshConnector("192.168.50.106", "root", "password");
//        ssh.setConnectTimeout(2000);
        Map<String, String> map = ssh.excuteCommand("vm-nat-limit i-2-119-VM 128");//vm-nat-limit i-2-147-VM 384

        if(map.containsKey("stderr")){
            System.out.println("error");
            System.out.println(map.get("stderr"));
        }else{
            System.out.println("normal");
            System.out.println(map.get("stdout"));
        }
    }
}
