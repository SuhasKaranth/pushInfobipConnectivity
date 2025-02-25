package com.suhas.push.poc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

public class InfobipConnectViaProxy {
    public static void main(String[] args) {
        try {
            String proxyIP = "192.168.155.59"; // Replace with your proxy IP address
            int proxyPort = 443;           // Replace with your proxy port
            String targetUrl = "https://api-ae2.infobip.com";

            // Force TLS 1.2 or TLS 1.3
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2"); // or "TLSv1.3"
            sslContext.init(null, null, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

            // Create proxy instance using IP address
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyIP, proxyPort));

            // Create URL object
            URL url = new URL(targetUrl);

            // Open connection using the proxy
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection(proxy);

            // Open connection without the proxy
            //HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();


            httpConnection.setRequestMethod("GET");
            httpConnection.setConnectTimeout(5000);
            httpConnection.setReadTimeout(5000);

            // Get response code
            int responseCode = httpConnection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()))) {
                String response = reader.lines().collect(Collectors.joining("\n"));
                System.out.println("Response: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
