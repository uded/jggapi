package pl.mn.communicator.gadu.util;

/**
 * @version $Revision: 1.2 $
 * @author Marcin
 */
public class Util {
	public static String bytesToString(byte[] bytes){
		StringBuffer odebrane = new StringBuffer();
		odebrane.append("{");
		for (int i = 0; i < bytes.length; i++) {
			odebrane.append("'"+bytes[i]+"',");
		}
		odebrane.append("}");
		return odebrane.toString();
	}
}
