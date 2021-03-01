import java.io.*;
import java.net.*;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

import Authentication.Role;

public class server implements Runnable {
    private ServerSocket serverSocket = null;
    private static int numConnectedClients = 0;
    private boolean correctUser;
    private String[] userInfo;
    PrintWriter out = null;
    BufferedReader in = null;

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
            in.close();
            out.close();

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
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String clientMsg = null;
        List<String> list = WriterReader.readFile("mockUsers.txt");
        User user = null;
        String response = "";


        while ((clientMsg = in.readLine()) != null) {
            if (clientMsg.startsWith("l:")) {
                String[] loginInfo = clientMsg.substring(2).split(",");
                String username = loginInfo[0];
                String password = loginInfo[1];
               // String user = loginInfo[0];

                System.out.println("received '" + loginInfo[0] + " " + loginInfo[1] + "' from client");

                // Find matching user from "database"
                List<String> userStrings = WriterReader.readFile("mockUsers.txt");

                Optional<String> userStr = userStrings.stream().filter(str -> {
                    if (str.isEmpty())
                        return false;

                    String[] values = str.split(", ");
                    System.out.println(values);
                    correctUser = values[3].equals(username) && values[4].equals(password);
                    return correctUser;
                }).findFirst();

                if (userStr.isPresent()) {
                    userInfo = userStr.get().split(", ");
                    user = new User(Role.valueOf(userInfo[1]), userInfo[2]);
                }

                // Debug
                userStrings.forEach(e -> {
                    System.out.println(e);
                });

                    ArrayList<Journal> journals = WriterReader.getJournals("mockEntries.txt");
                    StringBuilder strb = new StringBuilder("");

                if (user != null) { // <- la till detta
                    for (Journal jour : journals) {
                        // canRead tar in en User så måste ha det
                        if (jour.canRead(user)) {
                            int j = jour.getPatient();
                            strb.append(j);
                        }
                    }
                } else {
                    strb.append(""); // <--- Detta måste ändras, du vill skicka något som du kan hantera
                }
                // har en lista med ints jag vill skicka tillbaka till clienten
                response = strb.toString();
                out.println(response);
                out.flush();
                System.out.println("response sent\n");
            } else if (clientMsg.startsWith("c:")) {
                // then we have a command
                String command = clientMsg.split(",")[1];
                switch (command) {
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

      //  in.close();
      //  out.close();
        System.out.println("2");
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
