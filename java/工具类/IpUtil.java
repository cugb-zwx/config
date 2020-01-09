
/**
 * Title:
 * Project: 
 * Description:
 * Date: 2019-11-22
 * Copyright: Copyright (c) 2020
 * Company: 
 *
 * @author zwx
 * @version 1.0
 */

public class IpUtil {

    private static final String default_ip = "127.0.0.1";
    private static final String[] inner_ip_pattern_array = new String[]{
            "127.0.0.1", "192.168"
    };

    public static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> ipNetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                ipNetAddresses = networkInterface.getInetAddresses();
                while (ipNetAddresses.hasMoreElements()) {
                    inetAddress = ipNetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }

    public static String getLocalIP() {
        List<String> ipList = getLocalIPList();
        for (String ip : ipList) {
            if (checkIp(ip)) {
                return ip;
            }
        }
        if (ipList.size() > 0) return ipList.get(0);
        return default_ip;
    }

    public static boolean checkIp(String ip) {
        for (String prefix : inner_ip_pattern_array) {
            if (ip.startsWith(prefix)) return false;
        }
        return true;
    }
}
