package socketa;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class ServerS extends Frame {
   
   TextArea chatSeeTxt; 
   List<ServerThread> list;
   
   public ServerThread SThread;
   
   public ServerS() {
      super("서버");
      chatSeeTxt = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
      chatSeeTxt.setEditable(false); 
      chatSeeTxt.setFont(new Font("HYPost", Font.PLAIN, (int) 15));
      add(chatSeeTxt, BorderLayout.CENTER);
      addWindowListener(new WinListener());
      //프레임
      setSize(400,600);
      setResizable(true);
      setVisible(true); 
   }
   
   public void runServer() {
      ServerSocket server;
      Socket sock;

      try {
         list = new ArrayList<ServerThread>();
         server = new ServerSocket(5000); 
         try {
            while(true) { 
               sock = server.accept();
               SThread = new ServerThread(this, sock, chatSeeTxt); 
               SThread.start();
            }
         } catch(IOException ioe) {
            server.close();
            ioe.printStackTrace();
         }
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
      
   public static void main(String args[]) {
      ServerS s = new ServerS();
      s.runServer();
   }
      
   class WinListener extends WindowAdapter {
      public void windowClosing(WindowEvent e) {
         System.exit(0); 
      }
   }
}

   class ServerThread extends Thread {
      
   Socket sock;
   BufferedWriter output;
   BufferedReader input;
   TextArea chatSeeTxt;
   TextField text;
   String clientdata;
   ServerS cs;
   
   private static final String SEPARATOR = "|"; 
   private static final int REQ_LOGON = 1001; 
   private static final int REQ_SENDWORDS = 1021; 
   private static final int REQ_LOGOUT = 1004;
   

   public ServerThread(ServerS c, Socket s, TextArea ta) {
      
      sock = s;
      chatSeeTxt = ta;
      cs = c;
      
      try {
         input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
         output = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
      } catch(IOException ioe) {
         ioe.printStackTrace();
      }
   }
   
   public void run() {
  
      try {
         while((clientdata = input.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(clientdata, SEPARATOR); 
            int command = Integer.parseInt(st.nextToken()); 
            int cnt = cs.list.size();
            
            switch(command) { 
               case REQ_LOGON : { 
                  cs.list.add(this);    
                  String ID = st.nextToken();
                  String logms = st.nextToken();
                  chatSeeTxt.append("클라이언트 " + ID); 
                  chatSeeTxt.append(logms+"\r\n");
                  for(int i=0; i<cnt; i++) { 
                     ServerThread SThread = (ServerThread)cs.list.get(i);
                     SThread.output.write(ID + "님이 들어왔습니다.\r\n");
                     SThread.output.flush();
                  }
                  break;
               }
               case REQ_SENDWORDS : { 
                  String ID = st.nextToken();
                  String message = st.nextToken();
                  chatSeeTxt.append(ID + " : " + message + "\r\n");
                  for(int i=0; i<cnt; i++) { 
                     ServerThread SThread = (ServerThread)cs.list.get(i);
                     SThread.output.write(ID + " : " + message + "\r\n");
                     SThread.output.flush(); 
                  }  
                  break;
               }
               case REQ_LOGOUT : {  
                  cs.list.remove(this);     
                  String ID = st.nextToken();
                  String logms = st.nextToken();
                  chatSeeTxt.append("클라이언트 " + ID);
                  chatSeeTxt.append(logms+"\r\n");
                  for(int i=0; i<cnt; i++) { 
                     ServerThread SThread = (ServerThread)cs.list.get(i);
                     SThread.output.write(ID + "님이 나갔습니다.\r\n");
                           SThread.output.flush(); 
                           cnt--;
                       }
                  break;       
               }
            }
         }
      } catch(IOException e) {
         e.printStackTrace();
      }
      cs.list.remove(this); 
      try{
         sock.close(); 
      }catch(IOException ea){
         ea.printStackTrace();
      }
     }
}