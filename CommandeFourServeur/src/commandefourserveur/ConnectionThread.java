package commandeFourServeur;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;


public class ConnectionThread extends Thread{
    private Socket socket;
    private InterfaceServeur i ;
    OutputStream output= null;
    InputStream input = null;

    public ConnectionThread(Socket clientSocket,InterfaceServeur i) {
        this.socket = clientSocket;
        this.i=i;
    }

    @Override
    public void run() {
        try 
        {
            output = socket.getOutputStream();
            input = socket.getInputStream();
        } catch (IOException e) 
        {
            System.out.println("erreur de connexion : "+e);
        }
        PrintWriter writer = new PrintWriter(output, true);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        writer.println(i.getTemp());
        while (true)
        {
            String choix = "";
            try 
            {
                choix = reader.readLine();
            } 
            catch (IOException e) 
            {
                InterfaceServeur.sockets.remove(socket);
                i.repaint();
                break;
            }
            if("1".equals(choix))
            {
                if ((i.getTemp() - 10) >= InterfaceServeur.min )
                    i.setTemp(i.getTemp() - 10);
            }
            else if("2".equals(choix))
            {
                if ((i.getTemp() + 10) <= InterfaceServeur.max )
                    i.setTemp(i.getTemp() + 10);
            }
            Iterator it = InterfaceServeur.sockets.iterator();
            while(it.hasNext())
            {
                try 
                {
                    Socket s=(Socket) it.next();
                    OutputStream o = s.getOutputStream();
                    PrintWriter w = new PrintWriter(o, true);
                    w.println(i.getTemp());
                } 
                catch (IOException e) 
                {
                    System.out.println("erreur de connexion : "+e);
                }
            }
            i.repaint();
        }
    }  
}

