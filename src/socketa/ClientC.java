package socketa;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientC extends Frame implements ActionListener, KeyListener {

   BufferedWriter output;
   BufferedReader input;
   Socket client;
   String ID, logm, serverdata;
   StringBuffer clientdata = new StringBuffer();
   
   private static final String SEPARATOR = "|";
   private static final int REQ_LOGON = 1001; 
   private static final int REQ_SENDWORDS = 1021; 
   private static final int REQ_LOGOUT = 1004;
   
   
   //로그인
   private JFrame logFrame; 
   private JLabel logLogoLab, logIdLab, logPwLab; 
   private JTextField logIdTxt, logPwTxt; 
   private JButton loginButt, logjoinMemButt; 
      
   //회원가입
   private JFrame joinMembershipFrame;
   private JLabel joinLogoLab, joinIdLab, joinPwLab1, joinPwLab2, joinNameLab, joinStudIdLab; 
   private JTextField joinLogID, joinLogPW1, joinLogPW2, joinNameTxt, joinStudentIdTxt;
   private JButton joinCancelButt, joinJoinMemButt;
   
   //채팅창리스트
   private JFrame listFrame; 
   private JLabel listNickLab, listIDLab; 
   private JButton  listLogoutButt, listCloseButt, listMenuProfileButt, listMenuChatButt, listMenuSetButt; 
   private JPanel listMenuPan, listProfilePan, listChatPan, listSetPan;
   private JButton list1Butt, list2Butt, list3Butt;
   
   //채팅창
   private JFrame chatFrame;
   private JTextField chatInputTxt;
   private JButton chatSendButt;
   private JPanel chatTxtSendPan;
   TextArea chatSeeTxt;
      
   public ClientC() {      
      
   //색상
         Color color1 = new Color(237,238,238); //흰색
         Color color2 = new Color(225,226,223); //연그레이
         Color color3 = new Color(200,207,200); //연한 청회색(?)
         Color color4 = new Color(165,173,167); // 청회색(?)
         
         
   //로그인창
         logFrame = new JFrame("Login");
         logLogoLab = new JLabel("로그인"); 
      //로그인   
         logIdLab = new JLabel("아이디");
         logIdTxt = new JTextField(); 
         logPwLab = new JLabel("비밀번호"); 
         logPwTxt = new JPasswordField();
         logIdTxt.addActionListener(this); 
      //버튼            
         loginButt = new JButton("로그인");
         logjoinMemButt = new JButton("회원가입"); 
         loginButt.addActionListener(this);
           logjoinMemButt.addActionListener(this);
       //위치
           logFrame.setLayout(null); 
           logLogoLab.setBounds(72,50,240,60); 
           logIdLab.setBounds(72,120,240,40);
           logIdTxt.setBounds(72,160,240,40);
           logPwLab.setBounds(72,200,240,40);
           logPwTxt.setBounds(72,240,240,40);
           loginButt.setBounds(72,340,240,40);
           logjoinMemButt.setBounds(240,390,100,20);
           logFrame.add(logLogoLab);
           logFrame.add(logIdLab);
           logFrame.add(logIdTxt);
           logFrame.add(logPwLab);
           logFrame.add(logPwTxt);
           logFrame.add(loginButt);
           logFrame.add(logjoinMemButt);
      //배경
           logIdTxt.setBorder(null); 
           logPwTxt.setBorder(null); 
           loginButt.setBorderPainted(false); 
           logjoinMemButt.setBorderPainted(false); 
           logjoinMemButt.setContentAreaFilled(false); 
         logIdTxt.setBackground(color2);
         logPwTxt.setBackground(color2);
         loginButt.setBackground(color4);
         logFrame.setBackground(color1);
      //글씨
         logLogoLab.setFont(new Font("HYPost", Font.BOLD, (int) 22)); 
         logIdLab.setFont(new Font("맑은 고딕", Font.PLAIN, (int) 17));
         logIdTxt.setFont(new Font("맑은 고딕", Font.PLAIN, (int) 18));
         logPwLab.setFont(new Font("맑은 고딕", Font.PLAIN, (int) 17));
         logPwTxt.setFont(new Font("맑은 고딕", Font.PLAIN, (int) 18));
         loginButt.setFont(new Font("맑은 고딕", Font.PLAIN, (int) 18));
         logjoinMemButt.setFont(new Font("맑은 고딕", Font.PLAIN, (int) 11));
      //프레임         
         logFrame.setSize(400,550);
         logFrame.setResizable(false); 
         logFrame.setVisible(true);
         
         
   //회원가입창 프레임
         joinMembershipFrame = new JFrame("회원가입");
         joinLogoLab = new JLabel("회원가입 정보입력"); 
      //id, 비번            
         joinIdLab = new JLabel("아이디"); 
         joinLogID = new JTextField(18); 
         joinLogID.addActionListener(this); 
         joinPwLab1 = new JLabel("비밀번호"); 
         joinLogPW1 = new JPasswordField(18);
         joinPwLab2 = new JLabel("비민번호 확인"); 
         joinLogPW2 = new JPasswordField(18);
      //이름   
         joinNameLab = new JLabel("이름"); 
         joinNameTxt = new JTextField(15); 
       //학번        
         joinStudIdLab = new JLabel("학번");
         joinStudentIdTxt = new JTextField(18);
      //버튼   
         joinCancelButt = new JButton("취소"); 
         joinJoinMemButt = new JButton("회원가입"); 
         joinCancelButt.addActionListener(this);
         joinJoinMemButt.addActionListener(this); 
      //위치
         joinMembershipFrame.setLayout(null); 
         joinLogoLab.setBounds(100,25,200,60); 
         joinIdLab.setBounds(45,100,100,40); 
         joinLogID.setBounds(150,100,200,40);
         joinPwLab1.setBounds(45,170,100,40);
         joinLogPW1.setBounds(150,170,200,40);
         joinPwLab2.setBounds(45,240,100,40);
         joinLogPW2.setBounds(150,240,200,40);
         joinNameLab.setBounds(45,310,100,40);
         joinNameTxt.setBounds(150,310,200,40);
         joinStudIdLab.setBounds(45,380,100,40);
         joinStudentIdTxt.setBounds(150,380,200,40);
         joinCancelButt.setBounds(55,470,120,40);
         joinJoinMemButt.setBounds(200,470,120,40);
         joinMembershipFrame.add(joinLogoLab);
           joinMembershipFrame.add(joinIdLab);
           joinMembershipFrame.add(joinLogID);
           joinMembershipFrame.add(joinPwLab1);
           joinMembershipFrame.add(joinLogPW1);
           joinMembershipFrame.add(joinPwLab2);       
           joinMembershipFrame.add(joinLogPW2);
           joinMembershipFrame.add(joinNameLab);
           joinMembershipFrame.add(joinNameTxt);
           joinMembershipFrame.add(joinStudIdLab);
           joinMembershipFrame.add(joinStudentIdTxt);
           joinMembershipFrame.add(joinCancelButt);
           joinMembershipFrame.add(joinJoinMemButt);
       //배경
           joinLogID.setBorder(null); 
           joinLogPW1.setBorder(null);
           joinLogPW2.setBorder(null);
           joinNameTxt.setBorder(null);
           joinStudentIdTxt.setBorder(null);
           joinCancelButt.setBorder(null);
           joinJoinMemButt.setBorder(null);
           joinLogID.setBackground(color2);
           joinLogPW1.setBackground(color2);
           joinLogPW2.setBackground(color2);
           joinNameTxt.setBackground(color2);
           joinStudentIdTxt.setBackground(color2);
           joinCancelButt.setBackground(color4);
           joinJoinMemButt.setBackground(color4);
       //글씨
           joinLogoLab.setFont(new Font("HYPost", Font.BOLD, (int) 22));
         joinIdLab.setFont(new Font("HYPost", Font.PLAIN, (int) 14));
         joinLogID.setFont(new Font("HYPost", Font.PLAIN, (int) 14));
         joinPwLab1.setFont(new Font("HYPost", Font.PLAIN, (int) 14));
         joinLogPW1.setFont(new Font("HYPost", Font.PLAIN, (int) 14));
         joinPwLab2.setFont(new Font("HYPost", Font.PLAIN, (int) 14));
         joinLogPW2.setFont(new Font("HYPost", Font.PLAIN, (int) 14));
         joinNameLab.setFont(new Font("HYPost", Font.PLAIN, (int) 14));
         joinStudIdLab.setFont(new Font("HYPost", Font.PLAIN, (int) 14));
         joinStudentIdTxt.setFont(new Font("HYPost", Font.PLAIN, (int) 14));
         joinCancelButt.setFont(new Font("맑은 고딕", Font.PLAIN, (int) 13));
         joinJoinMemButt.setFont(new Font("맑은 고딕", Font.PLAIN, (int) 13));
       //프레임     
           joinMembershipFrame.setSize(400,620); 
           joinMembershipFrame.setResizable(false); 
           joinMembershipFrame.setVisible(false); 
           
           
   //채팅목록
           listFrame = new JFrame("채팅목록");
       //메뉴바
           listMenuProfileButt = new JButton("프로필"); 
           listMenuChatButt = new JButton("채팅목록"); 
           listMenuSetButt = new JButton("설정"); 
           listMenuProfileButt.addActionListener(this);
           listMenuChatButt.addActionListener(this);
           listMenuSetButt.addActionListener(this);
       //프로필창
           ID = logIdTxt.getText();
         listNickLab = new JLabel("아이디 : ");
         listIDLab = new JLabel("Guest");
      //채팅리스트창
           list1Butt = new JButton("채팅창1");
           list2Butt = new JButton("채팅창2");
           list3Butt = new JButton("채팅창3");
           list1Butt.addActionListener(this);
           list2Butt.addActionListener(this);
           list3Butt.addActionListener(this);
       //설정창
           listLogoutButt = new JButton("로그아웃");
           listCloseButt = new JButton("종료");
           listLogoutButt.addActionListener(this);
           listCloseButt.addActionListener(this);
       //위치
           //메뉴바
           listMenuPan = new JPanel();
           listMenuPan.setLayout(null); 
           listMenuProfileButt.setBounds(20,15,80,35);
           listMenuChatButt.setBounds(120,15,80,35);
           listMenuSetButt.setBounds(280,15,80,35); 
           listMenuPan.add(listMenuProfileButt);
           listMenuPan.add(listMenuChatButt);
           listMenuPan.add(listMenuSetButt);
           //프로필파넬
           listProfilePan = new JPanel();
           listProfilePan.setLayout(null);
           listNickLab.setBounds(100,160,360,55); 
           listIDLab.setBounds(100,200,400,55); 
         listProfilePan.add(listNickLab);
         listProfilePan.add(listIDLab);
           //채팅목록파넬
           listChatPan = new JPanel();
           listChatPan.setLayout(null);
           list1Butt.setBounds(10,60,360,55); 
           list2Butt.setBounds(10,150,360,55); 
           list3Butt.setBounds(10,240,360,55); 
           listChatPan.add(list1Butt);
           listChatPan.add(list2Butt);
           listChatPan.add(list3Butt);
           //설정파넬
           listSetPan = new JPanel();
           listSetPan.setLayout(null);
           listLogoutButt.setBounds(100,160,200,45); 
           listCloseButt.setBounds(100,240,200,45); 
           listSetPan.add(listLogoutButt);
           listSetPan.add(listCloseButt);
           //프레임에 파넬 삽입
           listFrame.setLayout(null); 
           listMenuPan.setBounds(0,0,400,60); 
           listProfilePan.setBounds(0,60,400,600); 
           listChatPan.setBounds(0,60,400,600); 
           listSetPan.setBounds(0,60,400,600); 
           listFrame.add(listMenuPan);
           listFrame.add(listProfilePan);
           listFrame.add(listChatPan);
           listFrame.add(listSetPan);
           listProfilePan.setVisible(false);
           listChatPan.setVisible(true);
         listSetPan.setVisible(false);
       //배경
           listMenuProfileButt.setBorder(null); 
           listMenuChatButt.setBorder(null);
           listMenuSetButt.setBorder(null);
           list1Butt.setBorder(null);
           list2Butt.setBorder(null);
           list3Butt.setBorder(null);
           listLogoutButt.setBorder(null);
           listCloseButt.setBorder(null);
           listMenuPan.setBackground(color2); 
           listProfilePan.setBackground(color2);
           listChatPan.setBackground(color2);
           listSetPan.setBackground(color2);
           listMenuProfileButt.setBackground(color4);
           listMenuChatButt.setBackground(color4);
           listMenuSetButt.setBackground(color4);
           list1Butt.setBackground(color1); 
           list2Butt.setBackground(color1);
           list3Butt.setBackground(color1);
           listLogoutButt.setBackground(color3);
           listCloseButt.setBackground(color3);
       //글씨
           listMenuProfileButt.setFont(new Font("HYPost", Font.BOLD, (int) 13));
           listMenuChatButt.setFont(new Font("HYPost", Font.BOLD, (int) 13));
           listMenuSetButt.setFont(new Font("HYPost", Font.BOLD, (int) 13));
           listNickLab.setFont(new Font("HYPost", Font.BOLD, (int) 20));
           listIDLab.setFont(new Font("굴림", Font.BOLD, (int) 18));
           listLogoutButt.setFont(new Font("HYPost", Font.BOLD, (int) 15));
           listCloseButt.setFont(new Font("HYPost", Font.BOLD, (int) 15));
           
       //프레임  
           listFrame.setSize(400,600);
           listFrame.setResizable(false);
           listFrame.setVisible(false);    
           
           
   //채팅창
         chatFrame = new JFrame("채팅");
      //채팅창
         chatSeeTxt = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY); 
         chatSeeTxt.setEditable(false); 
         chatInputTxt = new JTextField(20); 
         chatSendButt = new JButton("보내기"); 
         chatSendButt.setPreferredSize(new Dimension(60, 27)); 
         chatInputTxt.addActionListener(this);
         chatInputTxt.addKeyListener(this);
         chatSendButt.addActionListener(this);
      //위치
         chatTxtSendPan = new JPanel();
         chatTxtSendPan.add(chatInputTxt, BorderLayout.CENTER);
         chatTxtSendPan.add(chatSendButt, BorderLayout.EAST);
         chatFrame.add(chatSeeTxt, BorderLayout.CENTER);
         chatFrame.add(chatTxtSendPan, BorderLayout.SOUTH);
      //배경
         chatSeeTxt.setBackground(color2);
         chatTxtSendPan.setBackground(color2);
         chatSendButt.setBackground(color4);
         chatSendButt.setBorder(null);
      //글씨   
         chatSeeTxt.setFont(new Font("HYPost", Font.BOLD, (int) 20));
         chatInputTxt.setFont(new Font("맑은 고딕", Font.PLAIN, (int) 17));
         chatSendButt.setFont(new Font("HYPost", Font.BOLD, (int) 14));
      //프레임   
         chatFrame.setSize(400,600);
         chatFrame.setResizable(true);
         chatFrame.setVisible(false); 
      
          
   }    
   
   
   public void actionPerformed(ActionEvent bt) {
         ID = logIdTxt.getText(); 
           //로그인창
           if (logjoinMemButt.equals(bt.getSource())) { 
              joinMembershipFrame.setVisible(true);
              
              joinLogID.setText("");
                  joinLogPW1.setText("");
                  joinLogPW2.setText("");
                  joinNameTxt.setText("");
                  joinStudentIdTxt.setText("");
             }
                       
             //회원가입창
             if (joinCancelButt.equals(bt.getSource())) { 
                joinMembershipFrame.setVisible(false);
             }
             if (joinJoinMemButt.equals(bt.getSource())) { 
                joinMembershipFrame.setVisible(false);
             }
             
           //채팅목록창
             if (listMenuProfileButt.equals(bt.getSource())) { 
                listSetPan.setVisible(false);
            listChatPan.setVisible(false);
            listProfilePan.setVisible(true);
             }
           if (listMenuChatButt.equals(bt.getSource())) { 
              listProfilePan.setVisible(false);
            listSetPan.setVisible(false);
            listChatPan.setVisible(true);
             }
           if (listMenuSetButt.equals(bt.getSource())) { 
              listProfilePan.setVisible(false);
            listChatPan.setVisible(false);
            listSetPan.setVisible(true);
             }
           
           if (listLogoutButt.equals(bt.getSource())) { 
              logFrame.setVisible(true);
              listFrame.setVisible(false);  
              chatFrame.setVisible(false);
              try { 
                 chatInputTxt.setText("");
                 clientdata.setLength(0); 
                 clientdata.append(REQ_LOGOUT); 
                 clientdata.append(SEPARATOR);
                    clientdata.append(ID);
                    clientdata.append(SEPARATOR);
                    clientdata.append("이(가) 로그아웃하였습니다.");
                    output.write(clientdata.toString() +"\r\n");
                    output.flush(); 
                   }catch(Exception e2) {
                      e2.printStackTrace();
                   }
              logIdTxt.setText("");
              logPwTxt.setText("");
             }
           if (listCloseButt.equals(bt.getSource())) { 
              System.exit(0);
             }
           
           //채팅리스트
           if (list1Butt.equals(bt.getSource())) { 
              chatFrame.setVisible(true);
              try { 
                 clientdata.setLength(0); 
                    clientdata.append(REQ_LOGON);
                    clientdata.append(SEPARATOR);
                    clientdata.append("[" + ID + "]");
                    clientdata.append(SEPARATOR);
                    clientdata.append("님이 들어왔습니다.");
                    output.write(clientdata.toString()+"\r\n");
                    output.flush();

             } catch(Exception e) {
                e.printStackTrace();
             }
             }
           
           //채팅창
           if (chatSendButt.equals(bt.getSource())) { 
              String message = new String();
              message = chatInputTxt.getText();
              try { 
                   clientdata.setLength(0); 
                   clientdata.append(REQ_SENDWORDS);
                   clientdata.append(SEPARATOR);
                   clientdata.append("[" + ID + "]");
                   clientdata.append(SEPARATOR);
                   clientdata.append(message);
                   output.write(clientdata.toString()+"\r\n");
                   output.flush(); 
                   chatInputTxt.setText("");
                } catch (IOException e) {
                   e.printStackTrace();
                }
             }
                      
             
           //회원가입
             try {  
                String id = null;
                boolean idNotRe = false;
              BufferedWriter bw = new BufferedWriter(new FileWriter("members.txt", true)); 
               BufferedReader br = new BufferedReader(new FileReader("members.txt"));
                
                //회원가입창
               if (joinJoinMemButt.equals(bt.getSource())) {
                     while(( id = br.readLine()) != null) {
                        String[] array = id.split("/");
                        if(array[0].equals(joinLogID.getText())) { 
                           idNotRe = true; 
                           JOptionPane.showMessageDialog(null, "중복 아이디입니다.");
                           break;
                        }
                     } br.close();
                     if(idNotRe==false) { 
                        bw.write(joinLogID.getText()+"/");
                      bw.write(joinLogPW1.getText()+"/");
                    bw.write(joinLogPW2.getText()+"/");
                    bw.write(joinNameTxt.getText()+"/");
                    bw.write(joinStudentIdTxt.getText()+"\r\n");
                    bw.close();
                      JOptionPane.showMessageDialog(null, "회원가입을 축하합니다.");
                     } 
                     else {
                        JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다.");
                     }
               } else if(joinCancelButt.equals(bt.getSource())) { 
                     joinLogID.setText("");
                     joinLogPW1.setText("");
                     joinLogPW2.setText("");
                     joinNameTxt.setText("");
                     joinStudentIdTxt.setText("");
               }
                  
               //로그인창
               if (loginButt.equals(bt.getSource())) { 
                  while((id=br.readLine())!=null) {
                     String[] array=id.split("/");
                     if(logIdTxt.getText().equals(array[0]) && logPwTxt.getText().equals(array[1])) { 
                        JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다");
                        logFrame.setVisible(false);
                        listFrame.setVisible(true);
                        listIDLab.setText(ID); 
                     }
                  }
               }
             } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "실패");
                ex.printStackTrace();
             }
      }
   
   
   public void keyPressed(KeyEvent ke) { 
      ID = logIdTxt.getText();
      if(ke.getKeyChar() == KeyEvent.VK_ENTER) {
            String message = new String();
            message = chatInputTxt.getText();
            
               try { 
                  clientdata.setLength(0);
                  clientdata.append(REQ_SENDWORDS);
                  clientdata.append(SEPARATOR);
                   clientdata.append("[" + ID + "]");
                   clientdata.append(SEPARATOR);
                   clientdata.append(message);
                  output.write(clientdata.toString()+"\r\n");
                  output.flush(); 
                  chatInputTxt.setText("");
               } catch (IOException e) {
                  e.printStackTrace();
               }
         }
   }
   public void keyReleased(KeyEvent ke) { 
   }

   public void keyTyped(KeyEvent ke) {
   }
   
   public void runClient() { 
      
         try {
            client = new Socket(InetAddress.getLocalHost(), 5000);
               
              input = new BufferedReader(new InputStreamReader(client.getInputStream()));
              output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            
              while(true) { 
                  serverdata = input.readLine();
                  chatSeeTxt.append(serverdata+"\r\n");
              }
         } catch(IOException e) {
            e.printStackTrace();
         }
   }
   
        
   public static void main(String args[]) {
      ClientC aa = new ClientC();
      aa.runClient();
   }
}