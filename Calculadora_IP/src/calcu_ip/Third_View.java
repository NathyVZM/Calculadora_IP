package calcu_ip;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.*;

public class Third_View {
	
	private JPanel panel;
	private JLabel ip, mask, title;
	private JTextField iptext, masktext;
	private JButton calcular, limpiar, ayuda;
	public JButton volver;
	private JTextArea data;
	private Border border;
	
	public Third_View() {
		panel = new JPanel(new BorderLayout());
		
		border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		
		JPanel pNorth = crearPNorth();
		panel.add(pNorth, BorderLayout.NORTH);
		
		JPanel pCenter = crearPCenter();
		panel.add(pCenter, BorderLayout.CENTER);
		
		JPanel pSouth = crearPSouth();
		panel.add(pSouth, BorderLayout.SOUTH);
		
		calcular.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//IP
				String ip = iptext.getText();
				String mascara = masktext.getText();
				
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
					} 
					else {
						data.setText("  IP no Valida");
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

				}
				else {
					comodin = ipc.toString();
				}
				data.setText("  ~IP  =  " + comodin + "\n  ~Mascara =  " 
				+ mascara);
				
				//ARREGLO IPV6
				String ipv6[] = comodin.split("[:]", 8);
				
				//HEXTETOS - INTEGER
				int hex1 = 0;
				int hex2 = 0;
				int hex3 = 0;
				int hex4 = 0;
				int hex5 = 0;
				int hex6 = 0;
				int hex7 = 0;
				int hex8 = 0;
				
				if (!iptext.getText().isEmpty() && ipv6.length == 8) {
					hex1 = Integer.parseInt(ipv6[0], 16);
					hex2 = Integer.parseInt(ipv6[1], 16);
					hex3 = Integer.parseInt(ipv6[2], 16);
					hex4 = Integer.parseInt(ipv6[3], 16);
					hex5 = Integer.parseInt(ipv6[4], 16);
					hex6 = Integer.parseInt(ipv6[5], 16);
					hex7 = Integer.parseInt(ipv6[6], 16);
					hex8 = Integer.parseInt(ipv6[7], 16);
				}
				
				//MASCARA - INT
				int m = 0;
				if (!masktext.getText().isEmpty()) {
					m = Integer.parseInt(mascara);
				}
				
				//OBJETO PARA TOSTRING
				ToString cadena = new ToString();
				
				
				//UNICAST - 0:0:0:0:0:0:0:0/128
				if((hex1 == 0) && (hex2 == 0) && (hex3 == 0) && (hex4 == 0) && (hex5 == 0)
						&& (hex6 == 0) && (hex7 == 0) && (hex8 == 0) && (!iptext.getText().isEmpty())
						&& (!masktext.getText().isEmpty()) && (m == 128)) {
					data.setText(cadena.EIPV6(comodin, mascara, "Unicast -> Red Sin Especificar"));
				}
				
				//UNICAST - 0:0:0:0:0:0:0:1
				else if((hex1 == 0) && (hex2 == 0) && (hex3 == 0) && (hex4 == 0) && (hex5 == 0)
						&& (hex6 == 0) && (hex7 == 0) && (hex8 == 1) && (!iptext.getText().isEmpty())
						&& (!masktext.getText().isEmpty()) && (m == 128)) {
					data.setText(cadena.EIPV6(comodin, mascara, "Unicast -> Loopback"));
				}
				
				//UNICAST - GLOBAL
				else if((hex1 >= 8192 && hex1 <= 16383) && (hex2 >= 0 && hex2 <= 65535) && (hex3 >= 0 && hex3 <= 65535)
						&& (hex4 >= 0 && hex4 <= 65535) && (hex5 >= 0 && hex5 <= 65535) && (hex6 >= 0 && hex6 <= 65535)
						&& (hex7 >= 0 && hex7 <= 65535) && (hex8 >= 0 && hex8 <= 65535) && (!iptext.getText().isEmpty())
						&& (!masktext.getText().isEmpty())) {
					
					//UNICAST - 3FFF:FFFF::
					if((hex1 == 16383) && (hex2 == 65535) && (hex3 >= 0 && hex3 <= 65535) && (hex4 >=0 && hex4 <= 65535)
							&& (hex5 >= 0 && hex5 <= 65535) && (hex6 >= 0 && hex6 <= 65535)
							&& (hex7 >= 0 && hex7 <= 65535) && (hex8 >= 0 && hex8 <= 65535)) {
						data.setText(cadena.EIPV6(comodin, mascara, "Unicast -> Reservada para ejemplos y documentacion"));
					}
					
					//UNICAST - 2001:0DB8::
					else if((hex1 == 8193) && (hex2 == 3512) && (hex3 >= 0 && hex3 <= 65535) && (hex4 >= 0 && hex4 <= 65535)
							&& (hex5 >= 0 && hex5 <= 65535) && (hex6 >= 0 && hex6 <= 65535)
							&& (hex7 >= 0 && hex7 <= 65535) && (hex8 >= 0 && hex8 <= 65535)) {
						data.setText(cadena.EIPV6(comodin, mascara, "Unicast -> Reservada para ejemplos y documentacion"));
					}
					
					//UNICAST - 2002::
					else if((hex1 == 8194) && (hex2 >= 0 && hex2 <= 65535) && (hex3 >= 0 && hex3 <= 65535) && (hex4 >= 0 && hex4 <= 65535)
							&& (hex5 >= 0 && hex5 <= 65535) && (hex6 >= 0 && hex6 <= 65535)
							&& (hex7 >= 0 && hex7 <= 65535) && (hex8 >= 0 && hex8 <= 65535)) {
						data.setText(cadena.EIPV6(comodin, mascara, "Unicast -> Reservada para Tuneles de 6 y 4"));
					}
					else {
						data.setText(cadena.EIPV6(comodin, mascara,
								"Unicast -> Rango Global\n" + String.format("%25s", "Anycast")));
					}
				}
				
				//UNICAST - LINK LOCAL
				else if((hex1 >= 65152 && hex1 <= 65215) &&(hex2 >= 0 && hex2 <= 65535) && (hex3 >= 0 && hex3 <= 65535)
						&& (hex4 >= 0 && hex4 <= 65535) && (hex5 >= 0 && hex5 <= 65535) && (hex6 >= 0 && hex6 <= 65535)
						&& (hex7 >= 0 && hex7 <= 65535) && (hex8 >= 0 && hex8 <= 65535) && (!iptext.getText().isEmpty())
						&& (!masktext.getText().isEmpty())) {
					data.setText(cadena.EIPV6(comodin, mascara, "Unicast -> Link Local"));
				}
				
				//UNICAST - UNIQUE LOCAL
				else if((hex1 >= 64512 && hex1 <= 65023) && (hex2 >= 0 && hex2 <= 65535) && (hex3 >= 0 && hex3 <= 65535)
						&& (hex4 >= 0 && hex4 <= 65535) && (hex5 >= 0 && hex5 <= 65535) && (hex6 >= 0 && hex6 <= 65535)
						&& (hex7 >= 0 && hex7 <= 65535) && (hex8 >= 0 && hex8 <= 65535) && (!iptext.getText().isEmpty())
						&& (!masktext.getText().isEmpty())) {
					data.setText(cadena.EIPV6(comodin, mascara, "Unicast -> Unique Local"));
				}
				
				//MULTICAST
				else if((hex1 >= 65280 && hex1 <= 65535) && (hex2 >= 0 && hex2 <= 65535) && (hex3 >= 0 && hex3 <= 65535)
						&& (hex4 >= 0 && hex4 <= 65535) && (hex5 >= 0 && hex5 <= 65535) && (hex6 >= 0 && hex6 <= 65535)
						&& (hex7 >= 0 && hex7 <= 65535) && (hex8 >= 0 && hex8 <= 65535) && (!iptext.getText().isEmpty())
						&& (!masktext.getText().isEmpty())) {
					
					//MULTICAST - ASSIGNED - TODOS LOS NODOS
					if((hex1 == 65282) && (hex2 == 0) && (hex3 == 0) && (hex4 == 0) && (hex5 == 0) && (hex6 == 0)
							&& (hex7 == 0) && (hex8 == 1)) {
						data.setText(cadena.EIPV6(comodin, mascara, "Multicast  ->  Asignada - Dispositivos: Todos los Nodos"));
					}
					
					//MULTICAST - ASSIGNED - TODOS LOS ROUTERS
					else if((hex1 == 65282) && (hex2 == 0) && (hex3 == 0) && (hex4 == 0) && (hex5 == 0) && (hex6 == 0)
							&& (hex7 == 0) && (hex8 == 2)) {
						data.setText(cadena.EIPV6(comodin, mascara, "Multicast  ->  Asignada - Dispositivos: Todos los Routers"));
					}
					
					//MULTICAST - ASSIGNED - ROUTERS OSPF
					else if((hex1 == 65282) && (hex2 == 0) && (hex3 == 0) && (hex4 == 0) && (hex5 == 0) && (hex6 == 0)
							&& (hex7 == 0) && (hex8 == 5)) {
						data.setText(cadena.EIPV6(comodin, mascara, "Multicast  ->  Asignada - Dispositivos: Routers OSPF"));
					}
					
					//MULTICAST - ASSIGNED -ROUTERS ASIGNADOS OSPF
					else if((hex1 == 65282) && (hex2 == 0) && (hex3 == 0) && (hex4 == 0) && (hex5 == 0) && (hex6 == 0)
							&& (hex7 == 0) && (hex8 == 6)) {
						data.setText(cadena.EIPV6(comodin, mascara, "Multicast  ->  Asignada - Dispositivos: Routers asignados OSPF"));
					}
					
					//MULTICAST - ASSIGNED - ROUTERS RIP
					else if((hex1 == 65282) && (hex2 == 0) && (hex3 == 0) && (hex4 == 0) && (hex5 == 0) && (hex6 == 0)
							&& (hex7 == 0) && (hex8 == 9)) {
						data.setText(cadena.EIPV6(comodin, mascara, "Multicast  ->  Asignada - Dispositivos: Routers RIP"));
					}
					
					//MULTICAST - ASSIGNED - AGENTE DHCP
					else if((hex1 == 65282) && (hex2 == 0) && (hex3 == 0) && (hex4 == 0) && (hex5 == 0) && (hex6 == 0)
							&& (hex7 == 1) && (hex8 == 2)) {
						data.setText(cadena.EIPV6(comodin, mascara, "Multicast  ->  Asignada - Dispositivos: Agente DHCP"));
					}
					
					else {
						data.setText(cadena.EIPV6(comodin, mascara, "Multicast"));
					}
				}
				//100::/64 - PREFIJO
				else if((hex1 == 256) && (hex2 == 0) && (hex3 == 0) && (hex4 == 0) && (hex5 >= 0 && hex5 <= 65535)
						&& (hex6 >= 0 && hex6 <= 65535) && (hex7 >= 0 && hex7 <= 65535)
						&& (hex8 >= 0 && hex8 <= 65535) && (!iptext.getText().isEmpty()) && (!masktext.getText().isEmpty())) {
					data.setText(cadena.EIPV6(comodin, mascara, "Enrutamiento. Prefijo"));
				}
				
				//2001::/32 - TUNEL TEREDO
				else if((hex1 == 8193) && (hex2 == 0) && (hex3 >= 0 && hex3 <= 65535) && (hex4 >= 0 && hex4 <= 65535)
						&& (hex5 >= 0 && hex5 <= 65535) && (hex6 >= 0 && hex6 <= 65535) && (hex7 >= 0 && hex7 <= 65535)
						&& (hex8 >= 0 && hex8 <= 65535) && (!iptext.getText().isEmpty()) && (!masktext.getText().isEmpty())) {
					data.setText(cadena.EIPV6(comodin, mascara, "Internet. Tuner Teredo"));
				}
				
				//2001:20::/28 - ORCHIDv2
				else if((hex1 == 8193) && (hex2 >= 32 && hex2 <= 47) && (hex3 >= 0 && hex3 <= 65535) && (hex4 >= 0 && hex4 <= 65535)
						&& (hex5 >= 0 && hex5 <= 65535) && (hex6 >= 0 && hex6 <= 65535) && (hex6 >= 0 && hex6 <= 65535)
						&& (hex7 >= 0 && hex7 <= 65535) && (hex8 >= 0 && hex8 <= 65535) && (!iptext.getText().isEmpty())
						&& (!masktext.getText().isEmpty())) {
					data.setText(cadena.EIPV6(comodin, mascara, "Software. ORCHIDv2"));
				}
				
				//IP Y MASCARA VACIAS
				else if(iptext.getText().isEmpty() && !masktext.getText().isEmpty()) {
					data.setText("  Introduzca una IPv6");
				}
				
				//MASCARA VACIA
				else if(masktext.getText().isEmpty() && !iptext.getText().isEmpty()) {
					data.setText("  Introduzca una Mascara");
				}
				
				//IP VACIA
				else if(iptext.getText().isEmpty() && masktext.getText().isEmpty()) {
						data.setText("  Introduzca una IPv6 y una Mascara");
				}
				
				else if((hex1 < 0 || hex1 > 65535) || (hex2 < 0 || hex2 > 65535) || (hex3 < 0 || hex3 > 65535)
						|| (hex4 < 0 || hex4 > 65535) || (hex5 < 0 || hex5 > 65535) || (hex6 < 0 || hex6 > 65535)
						|| (hex7 < 0 || hex7 > 65535) || (hex8 < 0 || hex8 > 65535) && (!iptext.getText().isEmpty())
						&& (!masktext.getText().isEmpty())) {
					data.setText("  IPv6 Fuera de Rango");
				}
				else {
					data.setText(cadena.EIPV6(comodin, mascara, "Enrutamiento. Ruta por Defecto"));
				}
				
				panel.invalidate();
				panel.validate();
			}
		});
		//END_EVENT - BUTTON - calcular
		
		limpiar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				iptext.setText("");
				masktext.setText("");
				data.setText("");
				
				panel.invalidate();
				panel.validate();
			}
		});
		//END_EVENT - BUTTON - limpiar
		
		ayuda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ToString ayuda = new ToString();
				
				data.setText(ayuda.ayudaipv6());
				
				panel.invalidate();
				panel.validate();
			}
		});
	}
	
	
	//NEW METHOD - getJPanel()
	public JPanel getJPanel() {
		return this.panel;
	}
	
	
	//NEW METHOD - crearPNorth()
	private JPanel crearPNorth() {
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel norte = PNorte();
		p.add(norte, BorderLayout.NORTH);
		
		JPanel centro = PCentro();
		p.add(centro, BorderLayout.CENTER);
		
		return p;
	}
	
	//METHOD - PNorte() - crearPNorth()
	private JPanel PNorte() {
		JPanel p = new JPanel(new FlowLayout());
		
		title = new JLabel(".:CALCULADORA IP:. - IPV6");
		p.add(title);
		
		p.setBorder(border);
		
		return p;
	}
	
	//METHOD - PCentro() - crearPNorth()
	private JPanel PCentro() {
		JPanel p = new JPanel(new BorderLayout());
		
		calcular = new JButton("CALCULAR");
		p.add(calcular, BorderLayout.EAST);
		
		JPanel centro = CentroC();
		p.add(centro, BorderLayout.CENTER);
		
		TitledBorder titledborder = BorderFactory.createTitledBorder(border, "IPv6:.");
		p.setBorder(titledborder);
		
		return p;
	}
	
	//METHOD - CentroC() - PCentro() - crearPNorth()
	private JPanel CentroC() {
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel norte = NorteC();
		p.add(norte, BorderLayout.NORTH);
		
		JPanel sur = SurC();
		p.add(sur, BorderLayout.SOUTH);
		
		return p;
	}
	
	//METHOD - NorteC() - PCentro() - crearPNorth()
	private JPanel NorteC() {
		JPanel p = new JPanel(new BorderLayout());
		
		ip = new JLabel("   IP :   ");
		p.add(ip, BorderLayout.WEST);
		
		iptext = new JTextField();
		p.add(iptext, BorderLayout.CENTER);
		
		return p;
	}
	
	//METHOD - SurC() - PCentro() - crearPNorth()
	private JPanel SurC() {
		JPanel p = new JPanel(new BorderLayout());
		
		mask = new JLabel("   Mascara :  / ");
		p.add(mask, BorderLayout.WEST);
		
		masktext = new JTextField();
		p.add(masktext, BorderLayout.CENTER);
		
		return p;
	}
	
	
	//NEW METHOD - crearPCenter()
	private JPanel crearPCenter() {
		JPanel p = new JPanel(new BorderLayout());
		
		data = new JTextArea();
		data.append(String.format("%95s", ".:CALCULADORA IPv6:.\n\n"));
		data.append("     ~Introduzca una IPv6\n     ~Introduzca una Mascara\n");
		data.append("     ~Presione el boton 'CALCULAR'\n");
		p.add(data, BorderLayout.CENTER);
		
		JScrollPane scroll = new JScrollPane(data);
		p.add(scroll, BorderLayout.CENTER);
		
		TitledBorder titledborder = BorderFactory.createTitledBorder(border, "Resultados:.");
		p.setBorder(titledborder);
		
		return p;
	}
	
	
	//NEW METHOD - crearPSouth()
	private JPanel crearPSouth() {
		JPanel p = new JPanel(new FlowLayout());
		
		volver = new JButton("Volver");
		p.add(volver);
		
		limpiar = new JButton("Limpiar");
		p.add(limpiar);
		
		ayuda = new JButton("Ayuda");
		p.add(ayuda);
		
		p.setBorder(border);
		
		return p;
	}
}
