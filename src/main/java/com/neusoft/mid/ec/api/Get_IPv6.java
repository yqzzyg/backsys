package com.neusoft.mid.ec.api;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
public class Get_IPv6 {

	public static void main(String[] args) throws IOException{
        String str = getLocalIPv6Address();
        System.out.println(str);
    }

    public static String getLocalIPv6Address() throws IOException {
        InetAddress inetAddress = null;
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        outer:
	        while (networkInterfaces.hasMoreElements()) {
	            Enumeration<InetAddress> inetAds = networkInterfaces.nextElement().getInetAddresses();
	            while (inetAds.hasMoreElements()) {
	                inetAddress = inetAds.nextElement();
	                System.out.println("inetAddress:" + inetAddress); 
	        //Check if it's ipv6 address and reserved address
	                if (inetAddress instanceof Inet6Address && !isReservedAddr(inetAddress)) {
	                    System.out.println("ipv6----"+inetAddress); 
	                	break outer;
	            }
	        }
	    }
	    String ipAddr = inetAddress.getHostAddress();
	    System.out.println("ipAddr:"+ ipAddr);
	    // Filter network card No
	    int index = ipAddr.indexOf('%');
	    if (index > 0) {
	        ipAddr = ipAddr.substring(0, index);
	    }
	    	return ipAddr;
	}


    private static boolean isReservedAddr(InetAddress inetAddr) {
        if (inetAddr.isAnyLocalAddress() || inetAddr.isLinkLocalAddress() || inetAddr.isLoopbackAddress()) {
            return true;
        }
        return false;
    }
}
