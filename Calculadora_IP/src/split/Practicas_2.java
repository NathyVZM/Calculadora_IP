package split;

public class Practicas_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String ip = "0000:0000:0000:0000:0000:0000:0000:0000";
		
		String ipv6[] = ip.split("[:]", 8);
		
		int hex1 = Integer.parseInt(ipv6[0], 16);
		int hex2 = Integer.parseInt(ipv6[1], 16);
		int hex3 = Integer.parseInt(ipv6[2], 16);
		int hex4 = Integer.parseInt(ipv6[3], 16);
		int hex5 = Integer.parseInt(ipv6[4], 16);
		int hex6 = Integer.parseInt(ipv6[5], 16);
		int hex7 = Integer.parseInt(ipv6[6], 16);
		int hex8 = Integer.parseInt(ipv6[7], 16);
		
		/*int hex[] = new int[8];
		for(int i = 0; i < 8; i++) {
			hex[i] = Integer.parseInt(ipv6[i], 16);
		}
		
		for(int i = 0; i < 8; i++) {
			System.out.println(hex[i]);
		}*/
		
		System.out.println(hex1 + "\n" + hex2 + "\n" + hex3 + "\n" + hex4
				+ "\n" + hex5 + "\n" + hex6 + "\n" + hex7 + "\n" + hex8);
		
		if((hex1 == 0) && (hex2 == 0) && (hex3 == 0) && (hex4 == 0) && (hex5 == 0)
				&& (hex6 == 0) && (hex7 == 0) && (hex8 == 0)){
			System.out.println("Unicast -> Red Sin Especificar");
		}
	
	
	}

}
