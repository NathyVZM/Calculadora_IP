package calcu_ip;

public class ToString {
	
	public ToString() {
		
	}
	
	//METHOD TO REWRITE
	public String Escribir(String ip, String b1, String b2, String b3, String b4, String tipo, String clase, String apipa,
			String reservada, int r1, int r2, int r3, int r4, String br1, String br2, String br3, String br4, int c1, int c2, int c3, int c4,
			String bc1, String bc2, String bc3, String bc4, int g, String bg) {
		return "  ~Direccion IP : " + ip + String.format("%30s", "\t~Binario : ") + b1 + "." + b2 + "." + b3 + "." + b4
				+ "\n  ~Tipo : " + tipo + "\n  ~Clase : " + clase + "\n  ~APIPA : " + apipa
				+ "\n  ~Reservada : " + reservada
				+ "\n\n  ~Direccion de Red : " + r1 + "." + r2 + "." + r3 + "." + r4
				+ String.format("%20s", "\t~Binario : ") + br1 + "." + br2 + "." + br3 + "." + br4
				+ "\n  ~Direccion de Broadcast : " + c1 + "." + c2 + "." + c3 + "." + c4
				+ String.format("%15s", "\t~Binario : ") + bc1 + "." + bc2 + "." + bc3 + "." + bc4
				+ "\n  ~Direccion Gateway : " + r1 + "." + r2 + "." + r3 + "." + g
				+ String.format("%20s", "\t~Binario : ") + br1 + "." + br2 + "." + br3 + "." + bg
				+ "\n\n  ~Rango de Direcciones IP :  " + r1 + "." + r2 + "." + r3 + "." + g
				+ "  -  " + c1 + "." + c2 + "." + c3 + "." + (c4-1) + " \n\n\n";
				 
	}
	
	public String EHosts(String host, int x, String ip, String m1, String m2, String m3, String m4) {
		return "  ~Hosts :  " + host + "\n  ~Red Recomendada : " + ip + "/" + x + "\n  ~Mascara en DDN : "
				+ Integer.parseInt(m1, 2) + "." + Integer.parseInt(m2, 2) 
				+ "." + Integer.parseInt(m3, 2) + "." + Integer.parseInt(m4, 2) + "\n\n";
	}
	
	public String EIPV6(String ip, String mask, String tipo) {
		return "  ~IP  =  " + ip + "\n  ~Mascara  =  /" + mask + "\n  ~Tipo  =  "
				+ tipo + "\n\n";
	}
	
	public String ayudaipv4() {
		return String.format("%95s", ".:CALCULADORA IPv4:.\n\n")
				+ "     .:Para Calcular una IP:.\n"
				+ "     ~Ingrese una IPv4\n     ~Ingrese una Mascara\n"
				+ "     ~Presione el boton 'CALCULAR'\n\n"
				+ "     PD : Puede generar una Mascara Por Defecto con el boton 'Generar Mascara'\n"
				+ "             o introducir una en el campo 'Mascara'.\n\n"
				+ "     .:Para calcular una Red dados los Hosts:.\n"
				+ "     ~Ingrese una cantidad de Hosts\n"
				+ "     ~Presione el boton 'Calcular Red'\n\n";
	}
	
	public String ayudaipv6() {
		return String.format("%95s", ".:CALCULADORA IPv6:.\n\n")
				+ "     ~Introduzca una IPv6\n     ~Introduzca una Mascara\n"
				+ "     ~Presione el boton 'CALCULAR'\n";
	}

}
