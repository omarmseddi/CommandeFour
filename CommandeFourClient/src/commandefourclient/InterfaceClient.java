package commandefourclient;
        
import java.net.Socket;
import java.io.*;
import java.util.Properties;
import javax.swing.SwingWorker;


public class InterfaceClient extends javax.swing.JFrame {
    private int port;
    private String hostname;
    private PrintWriter writer;
    private BufferedReader reader;
    private static boolean scanning=true;
    int max,min;
    
    public InterfaceClient() throws IOException {
        initComponents();
        getProperties();
    }
    
    //Get configuration values
    private void getProperties() throws IOException {
        try (InputStream inputf = new FileInputStream("src\\resources\\config.properties")) 
        {
            Properties prop = new Properties();
            prop.load(inputf);
            port= Integer.parseInt(prop.getProperty("port"));
            hostname= prop.getProperty("hostname");
            min= Integer.parseInt(prop.getProperty("min"));
            max= Integer.parseInt(prop.getProperty("max"));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found: " + e);
        }
    }
    //Connect to server
    public void Connect() throws IOException{
        Socket socket = null;  
        while(scanning)
        {
           try
           {
               socket = new Socket(hostname, port);
               scanning=false;
           }
           catch(IOException e)
           {
               jLabel2.setText("Connection failed, waiting..."); 
           } 
           try
           {
               Thread.sleep(1000);
           }
           catch(InterruptedException e)
           {
               Thread.currentThread().interrupt();
           }
        }
        jLabel2.setText("        Connected"); 
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
        InputStream input = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        String temp = reader.readLine();
        jLabel1.setText(temp+" °C");
        if(min==Integer.parseInt(temp))
            jLabel4.setText("Minimum temperature !");
        else if(max==Integer.parseInt(temp))
            jLabel4.setText("Maximum temperature !");
        else
            jLabel4.setText("");
        while(true)
        {
            try
            {
                temp = reader.readLine();
                if(min==Integer.parseInt(temp))
                    jLabel4.setText("Minimum temperature !");
                else if(max==Integer.parseInt(temp))
                    jLabel4.setText("Maximum temperature !");
                else
                    jLabel4.setText("");
                jLabel1.setText(temp+" °C");
            }
            catch(IOException e)
            {
               jLabel1.setText("");
               jLabel4.setText("");
               scanning=true; 
               Connect();
            }
         }
    }
        
    // increase or decrease temperature
    public void commander(int choix) throws IOException {
        if(!scanning){
            writer.println(choix); 
        }
    }

        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Commande Four");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/moins.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Diminuer(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/plus.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Augmenter(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));

        jLabel2.setFont(new java.awt.Font("Arial", 3, 12)); // NOI18N
        jLabel2.setForeground(java.awt.Color.green);

        jLabel3.setForeground(new java.awt.Color(255, 0, 0));

        jLabel4.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Diminuer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Diminuer
        try 
        {
            commander(1);
        }
        catch (IOException e) 
        {
             System.out.println("erreur de connexion :"+e);
        }
    }//GEN-LAST:event_Diminuer

    private void Augmenter(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Augmenter
        try 
        {
            commander(2);
        } 
        catch (IOException e) 
        {
            System.out.println("erreur de connexion :"+e);
        }
    }//GEN-LAST:event_Augmenter

    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> 
        {
            try
            {
                InterfaceClient I = new InterfaceClient();
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
                I.setVisible(true);
            }
            catch (IOException e)
            {
                System.out.println("erreur de connexion : "+e);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables

}
