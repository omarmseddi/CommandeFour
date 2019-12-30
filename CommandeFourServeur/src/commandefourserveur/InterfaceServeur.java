package commandeFourServeur;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Properties;
import java.util.Vector;
import javax.swing.SwingWorker;


public class InterfaceServeur extends JPanel {
    public static int temp;
    public static int port=0,min,max, nbClients = 0;
    public static Vector sockets= new Vector();
    
    public InterfaceServeur() {
        try
        {
            getPropreties();
        }
        catch(IOException e){
            System.out.println("can't read file");
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("0°C", 310,200);
        g.drawString("40°C", 280,130);
        g.drawString("80°C", 210,90);
        g.drawString("120°C", 130,100);
        g.drawString("160°C", 70,160);
        g.drawString("200°C", 60,240);
        g.drawString("240°C", 110,300);
        g.drawString("280°C", 210,315);
        g.drawString("320°C", 290,270);
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial",Font.BOLD,30));
        g.drawString(temp+" °C", 160,50);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial",Font.BOLD,15));
        if (temp==max){
            g.drawString("Maximum temperature !", 110,70);            
        }
        else if(temp==min)
        {
            g.drawString("Minimum temperature !", 110,70);   
        }
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial",Font.BOLD,15));
        g.drawString("Clients number : "+sockets.size(), 130,350);
        g.setColor(Color.BLUE);
        g.fillOval(100, 100, 200,200);
        g.setColor(Color.RED);
        g.fillArc(100, 100, 200,200, 0,temp);
    }   
        
    public synchronized int getTemp() {
        return temp;
    }    
        
    public synchronized void setTemp(int t) {
        temp = t;
    }
    
    private void getPropreties() throws IOException {
        try (InputStream inputf = new FileInputStream("src\\resources\\config.properties")) 
        {
            Properties prop = new Properties();
            prop.load(inputf);
            port= Integer.parseInt(prop.getProperty("port"));
            min= Integer.parseInt(prop.getProperty("min"));
            max= Integer.parseInt(prop.getProperty("max"));
            temp=min;
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found: " + e);
        }
   }
   
    public void Connect() throws IOException {
        InterfaceServeur i =this;
        try (ServerSocket serverSocket = new ServerSocket(port)) 
        {
            while (true) 
            {
                Socket socket = serverSocket.accept();
                sockets.addElement(socket);
                i.repaint();
                new ConnectionThread(socket,i).start();
            }
        }
    }
    
/*
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
*/
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            JFrame f=new JFrame();
            f.setTitle("Four");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setPreferredSize(new Dimension(400, 400));
            f.setLocation(100,100); 
            f.setLocationRelativeTo(null);
            f.pack();
            InterfaceServeur I= new InterfaceServeur();
            f.add(I);
            f.setVisible(true);
            SwingWorker sw;
            sw = new SwingWorker()
            {
                @Override
                protected Object doInBackground() throws Exception 
                {
                    I.Connect();
                    return null;                  
                }

            };
            sw.execute();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

