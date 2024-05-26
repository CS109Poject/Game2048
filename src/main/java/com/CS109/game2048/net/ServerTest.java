package com.CS109.game2048.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerTest {
    public static void main(String[] args) {

            try {
                // 获取本机的主机名
                String hostname = InetAddress.getLocalHost().getHostName();
                System.out.println("本机主机名：" + hostname);

                // 获取本机的IP地址
                String ipAddress = InetAddress.getLocalHost().getHostAddress();
                System.out.println("本机IP地址：" + ipAddress);
            } catch (UnknownHostException e) {
                System.err.println("无法获取本机主机名或IP地址：" + e.getMessage());
            }
        }
    }
