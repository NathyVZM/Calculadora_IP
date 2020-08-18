package split;

public class Practicas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//IPV6
		/*String ip = "2001:db8:0:f100::2";
		String oc[] = ip.split("[:]", ip.length());
		
		String cadena = "";
		
		int m = oc.length;
		
		if(m == 6) {
			for(int i = 0; i < oc.length; i++) {
				if(oc[i].equalsIgnoreCase("")) {
					oc[i] = "0000:0000:0000";
				}
				else if(oc[i].equalsIgnoreCase("0")) {
					oc[i] = "0000";
				}
				cadena += oc[i] + ":";
			}
			
			if (cadena.charAt(cadena.length()-1) == ':') {
				cadena = cadena.substring(0, cadena.length() - 1);
			}
			
			System.out.println(cadena + "\n\n");
			
			
			String completa[] = cadena.split("[:]", cadena.length());
			for(int i = 0; i < completa.length; i++) {
				System.out.println(completa[i]);
			}
		}*/
		
		//HOSTS
		/*int x = (int) (32 - (Math.log10(130)/Math.log10(2)));
		
		if(x >= 8 && x <= 15) {
			System.out.println("Red Recomendada : 10.0.0.0/" + x);
		}
		else if(x >= 16 && x <= 23) {
			System.out.println("Red Recomendada : 172.16.0.0/" + x);
		}
		else if(x >= 24 && x <= 30) {
			System.out.println("Red Recomendada : 192.168.1.0/" + x);
		}
		
		String mask = "00000000.00000000.00000000.00000000";
		
		for(int i = 0; i < x; i++) {
			mask = mask.replaceFirst("0", "1");
		}
		
		//System.out.println("Mascara : " + mask);
		String newmask[] = mask.split("[.]", 4);
		
		System.out.println("Mascara : " + Integer.parseInt(newmask[0], 2) + "."
				+ Integer.parseInt(newmask[1], 2) + "."
				+ Integer.parseInt(newmask[2], 2) + "."
				+ Integer.parseInt(newmask[3], 2));
		*/
		
		//IPV6 OBTENIDA - 2001:0DB8:AC10:FE01::
		String ip = "::1";
		
		//REMPLAZAR :0: POR :0000:
		for(int i = 0; i < ip.length(); i++) {
			char c = ip.charAt(i);
			if(c == '0') {
				ip = ip.replace(":0:", ":0000:");
				
				int cero1 = ip.indexOf("0:");
				if(cero1 == 0) {
					ip = ip.replaceFirst("0:", "0000:");
				}
				
				int cero2 = ip.lastIndexOf(":0");
				if (cero2 == (ip.length() - 2)) {
					ip = ip.substring(0, ip.length()-1);
					ip = ip + "0000";
				}
			}
		}
		
		//OBTENGO EL LARGO DE LA IP SEPARANDOLA
		String newip[] = ip.split("[:]", ip.length());
		
		//ELIMINO LOS DOBLE :: DE LA IP INICIAL EN EL COMODIN
		String comodin = ip;
		comodin = comodin.replace("::", ":");
		
		//GUARDO LA POSICION DEL :: DE LA IP INICIAL
		StringBuilder ipc = new StringBuilder(comodin);
		int j = ip.indexOf("::");
		
		char[] array = ip.toCharArray();
		int b = 0;
		if(j != 0) {
			//int bc = 0;
			for (int i = 0; i < array.length; i++) {
				if ((array[i] == ':') && (array[i-1] == ':')) {
					b++;
				} else {
					b += 0;
				}
			}
		}

		// AGREGA LOS 0000 CUANDO LOS :: ESTAN ENTRE GRUPOS
		if (j > 0 && j < ip.length()) {
			if (b <= 1) {
				for (int i = 0; i < 8 - (newip.length - 1); i++) {
					ipc.insert(j, ":0000");
				}
				comodin = ipc.toString();

				String cds[] = comodin.split("[:]", comodin.length());

				// :: AL FINAL
				if (cds.length == 8 && cds[7].equalsIgnoreCase("")) {
					ipc.append("0000");
					comodin = ipc.toString();
				}
				
				System.out.println("IPV6 = " + comodin);
			} 
			else {
				System.out.println("IP no Valida");
			}

		}
		
		// :: AL INICIO
		else if (j == 0) {
			for (int i = 0; i < 8 - (newip.length - 2); i++) {
				ipc.insert(j, ":0000");
			}
			comodin = ipc.substring(1);
			
			if(comodin.charAt(comodin.length()-1) == ':') {
				comodin = ipc.substring(1, comodin.length());
			}
			
			System.out.println("IPV6 = " + comodin);

		}
		else {
			System.out.println("IPV6 = " + comodin);
		}
	}
}
