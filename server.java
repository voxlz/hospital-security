import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

public class server implements Runnable {
    private ServerSocket serverSocket = null;
    private static int numConnectedClients = 0;

    public server(ServerSocket ss) throws IOException {
        serverSocket = ss;
        newListener();
    }

    public void run() {
        try {
            SSLSocket socket = (SSLSocket) serverSocket.accept();
            newListener();
            printDebug(socket);

            // Handles incoming client messages.
            handleMessages(socket);

            closeConnection(socket);
        } catch (IOException e) {
            System.out.println("Client died: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    private void closeConnection(SSLSocket socket) throws IOException {
        socket.close();
        numConnectedClients--;
        System.out.println("client disconnected");
        System.out.println(numConnectedClients + " concurrent connection(s)\n");
    }

    private void printDebug(SSLSocket socket) throws SSLPeerUnverifiedException {
        SSLSession session = socket.getSession();
        X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
        String subject = cert.getSubjectDN().getName();
        String issuer = cert.getIssuerDN().getName();
        String cerialN = cert.getSerialNumber().toString();

        numConnectedClients++;
        System.out.println("client connected");
        System.out.println("client name (cert subject DN field): " + subject);
        System.out.println("issuer name: " + issuer);
        System.out.println("serial number: " + cerialN);
        System.out.println(numConnectedClients + " concurrent connection(s)\n");
    }

    private void handleMessages(SSLSocket socket) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String clientMsg = null;
        List<String> list = WriterReader.readFile("mockUsers.txt");
        while ((clientMsg = in.readLine()) != null) {
            System.out.println("Loop");

            if (clientMsg.startsWith("l:")) {
                String[] loginInfo = clientMsg.substring(2).split(",");
                String password = loginInfo[1];
                String user = loginInfo[0];

                System.out.println("received '" + loginInfo[0] + " " + loginInfo[1] + "' from client");

                // Find matching user from "database"
                String response = list.stream().filter(str -> {
                    if (str.isEmpty())
                        return false;

                    String[] values = str.split(", ");
                    System.out.println(values);

                    return values[3].equals(user) && values[4].equals(password);
                }).map(str -> "ok").findFirst().orElse("Login Failed: Username or Password didn't match");

                // Debug
                // list.forEach(e -> {
                // System.out.println(e);
                // });

                out.println(response);
                ArrayList<Journal> journals = WriterReader.getJournals("mockEntries.txt");

                for(Journal jour: journals){
                    //canRead tar in en User så måste ha det
                }



                out.flush();
                System.out.println("response sent\n");
            }

            else if(clientMsg.startsWith("c:")) {
                //then we have a comand
                String comand = clientMsg.split(",")[1];
                String response;
                switch (comand) {
                    case "reed":
                        //gör typ som innan
                        break;

                    case "write":

                        break;

                    case "delete":
                        break;

                    default:
                        response = "not a comand";
                        out.println(response);
                }
            }
        }

        in.close();
        out.close();
        System.out.println("2");
    }

    // THE OLD MESSAGE HANDLING CODE
    private void oldHandleMessages(SSLSocket socket) throws IOException {
        PrintWriter out = null;
        BufferedReader in = null;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String clientMsg = null;
        while ((clientMsg = in.readLine()) != null) {
            String rev = new StringBuilder(clientMsg).reverse().toString();
            System.out.println("received '" + clientMsg + "' from client");
            System.out.print("sending '" + rev + "' to client...");
            out.println(rev);
            out.flush();
            System.out.println("done\n");
        }
        in.close();
        out.close();
    }

    private void newListener() {
        (new Thread(this)).start();
    } // calls run()

    public static void main(String args2[]) {
        String[] args = { "9876" };

        System.out.println("\nServer Started\n");
        int port = -1;
        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        String type = "TLS";
        try {
            ServerSocketFactory ssf = getServerSocketFactory(type);
            ServerSocket ss = ssf.createServerSocket(port);
            ss.supportedOptions();
            ((SSLServerSocket) ss).setNeedClientAuth(true); // enables client authentication
            new server(ss);
        } catch (IOException e) {
            System.out.println("Unable to start Server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static ServerSocketFactory getServerSocketFactory(String type) {
        if (type.equals("TLS")) {
            SSLServerSocketFactory ssf = null;
            try { // set up key manager to perform server authentication
                SSLContext ctx = SSLContext.getInstance("TLS");
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
                TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
                KeyStore ks = KeyStore.getInstance("JKS");
                KeyStore ts = KeyStore.getInstance("JKS");
                char[] password = "password".toCharArray();

                ks.load(new FileInputStream("serverkeystore"), password); // keystore password (storepass)
                ts.load(new FileInputStream("servertruststore"), password); // truststore password (storepass)
                kmf.init(ks, password); // certificate password (keypass)
                tmf.init(ts); // possible to use keystore as truststore here
                ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                ssf = ctx.getServerSocketFactory();
                return ssf;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ServerSocketFactory.getDefault();
        }
        return null;
    }
}
