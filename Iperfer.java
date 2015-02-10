import java.util.ArrayList;
import java.util.Arrays;
import java.io.IOException;

public class Iperfer{

	
	public static void main(String args[]) throws IOException{
		
		//parse args
		String hostname="localhost";
		int portNum=0;
		double time=0;
		int type=0;//client 1, server 2, 0 error;		
		ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));		
		if(argsList.size()==7 && argsList.contains("-c") && argsList.contains("-h") && argsList.contains("-p") && argsList.contains("-t"))
		{
			type=1;
			hostname=argsList.get(argsList.indexOf("-h")+1);
			portNum=Integer.parseInt(argsList.get(argsList.indexOf("-p")+1));
			time=Double.parseDouble(argsList.get(argsList.indexOf("-t")+1));
		}
		else if(argsList.size()==3 && argsList.contains("-s") && argsList.contains("-p"))
		{
			type=2;
			portNum=Integer.parseInt(argsList.get(argsList.indexOf("-p")+1));
		}
		
		if(portNum<1024 || portNum>65535)
		{
			System.out.println("Error: port number must be in the range 1024 to 65535");
			System.out.println("The give port is "+portNum);
			return;
		}



		if(type==1)//client
		{
			IperfClient client = new IperfClient(hostname, portNum);
			client.sendTimedData(time);	

		}
		else if(type==2)
		{
			IperfServer server = new IperfServer(portNum);
			server.acceptAndRead();

		}
		else
			System.out.println("Error: missing or additional arguments");
		
		return;

	}
}
