import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;


public class IperfServer{
	ServerSocket serverSocket;
	Socket clientSocket;
	DataInputStream is;
	long data;

	public IperfServer(int portNumber)throws IOException{
		serverSocket=new ServerSocket(portNumber);
	}

	//returns bytes of data sent
	public long acceptAndRead() throws IOException{
		
	    long startTime,endTime;
	    int readPackets = 0;
	    double totalTime=0;
	    byte[] readBuff= new byte[1000];
	    clientSocket=serverSocket.accept();
	    is=new DataInputStream(clientSocket.getInputStream());

	    data=0;
	    int bytesRead=0;
	    startTime = System.nanoTime();//ns

	    while(true ){
		/*try{
		    startTime = System.nanoTime();//ns
		    is.readFully(readBuff,0,readBuff.length);
		    endTime = System.nanoTime();//ns

		    totalTime += endTime - startTime;
		}
		catch(Exception e){
		    endTime = System.nanoTime();//ns
		    totalTime += endTime - startTime;
		    break;
		}
		data++;*/

		startTime = System.nanoTime();//ns
		readPackets = is.read(readBuff,0,readBuff.length);
		endTime = System.nanoTime();//ns

		totalTime += endTime - startTime;

		if(readPackets == -1)
		    break;

		data += readPackets; //bytes
		
		//System.out.println(bytesRead);
	    }

	    //long totalTime=System.nanoTime()-startTime;
	    is.close();
	    serverSocket.close();
	    
	    totalTime=totalTime*Math.pow(10,-9);//in seconds 
	    
	    double rate =  (data*Math.pow(10,-3)*8)/totalTime;//Kbps
	    System.out.println("received="+(data/1000)+" KB"+" rate="+(rate/1000)+" Mbps");
	    return data;
	}
}
