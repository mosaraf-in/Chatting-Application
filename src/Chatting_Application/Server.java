package Chatting_Application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Server implements ActionListener {

    static JFrame f = new JFrame();
    static DataOutputStream dout;
    JTextField text;
    JPanel p2;
    static Box vertical = Box.createVerticalBox();
    Server(){
        f.setLayout(null);

        JPanel p1 = new JPanel();
//        p1.setBackground(Color.GREEN);
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,370,50);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,15,20,20);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                f.setVisible(false); //System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("Icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(35,35,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(30,7,35,35);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("Icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(250,15,20,20);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("Icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(290,15,20,20);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("Icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(7,17,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel moreVert = new JLabel(i15);
        moreVert.setBounds(330,17,7,17);
        p1.add(moreVert);

        JLabel name = new JLabel("Mosaraf");
        name.setBounds(75,10,150,18);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        name.setForeground(Color.WHITE);
        p1.add(name);

        JLabel status = new JLabel("online");
        status.setBounds(75,30,100,18);
        status.setFont(new Font("SAN_SERIF",Font.PLAIN,12));
        status.setForeground(Color.WHITE);
        p1.add(status);

        p2 = new JPanel();
        p2.setBounds(5,55,360,480);
        //p2.setBackground(Color.RED);
        f.add(p2);

        text = new JTextField();
        text.setBounds(5,540,270,30);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(280,540,85,30);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("Thoma",Font.BOLD,14));
        send.addActionListener(this);
        f.add(send);

        f.setSize(370,570);
        f.setLocation(200,50);
        f.getContentPane().setBackground(Color.WHITE);
        f.setUndecorated(true);
        f.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            String out = text.getText();
            JPanel p3 = formatLabel(out);

            p2.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.add(p3,BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            p2.add(vertical,BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p><html/>");

        output.setFont(new Font("Thoma",Font.PLAIN,16));
        output.setBackground(new Color(12, 194, 182));
        panel.add(output);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10,10,10,40));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);



        return  panel;
    }

    public static void main(String[] args) {
        new Server();   // ---> anonymous Object
//        Server s = new Server(); ---> Proper Object

        try{
            ServerSocket skt = new ServerSocket(6001);
            while (true){
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
