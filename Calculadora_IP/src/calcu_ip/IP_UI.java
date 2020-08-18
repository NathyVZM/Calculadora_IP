package calcu_ip;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import java.awt.event.*;

public class IP_UI{
	
	//Attributes
	private JFrame view;
	private JPanel panel;
	private JTextField iptext, masktext, hosttext;
	private JTextArea data;
	private JButton ingresar, registro, limpiar, agenerar, host, ipv6, ayuda;
	private JLabel titulo, iplabel, masklabel, hostlabel;
	private JScrollPane scroll;
	
	private Border border;
	
	 DB db = DB.getInstances();
	
	public IP_UI() {
		//FRAME
		view = new JFrame("Calculadora_IP");
		panel = new JPanel(new BorderLayout());
		view.add(panel);
		
		//Border
		border =  BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		
		
		//PANEL
		JPanel pNorth = crearPNorth();
		panel.add(pNorth, BorderLayout.NORTH);
		
		JPanel pCenter = crearPCenter();
		panel.add(pCenter, BorderLayout.CENTER);
		
		JPanel pSouth = crearPSouth();
		panel.add(pSouth, BorderLayout.SOUTH);
		
		
		//EVENT - ingresar
		ingresar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				
				//IP
				String ip = iptext.getText();
				//System.out.println(ip);
				String cds[]= ip.split("[.]", 4);
				
				//MASCARA
				String mask = masktext.getText();
				String mds[] = mask.split("[.]", 4);
				
				//IP - INTEGER
				int o1 = 0;
				int o2 = 0;
				int o3 = 0;
				int o4 = 0;
				
				//MASCARA - INTEGER
				int m1 = 0;
				int m2 = 0;
				int m3 = 0;
				int m4 = 0;
				
				try {
					if (!ip.equalsIgnoreCase("")) {
						o1 = Integer.parseInt(cds[0]);
						o2 = Integer.parseInt(cds[1]);
						o3 = Integer.parseInt(cds[2]);
						o4 = Integer.parseInt(cds[3]);
					}
					
					if (!mask.equalsIgnoreCase("")) {
						m1 = Integer.parseInt(mds[0]);
						m2 = Integer.parseInt(mds[1]);
						m3 = Integer.parseInt(mds[2]);
						m4 = Integer.parseInt(mds[3]);
					}
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				
				
				//MASCARA INVERTIDA
				int k1 = ~m1 & 0xff;
				int k2 = ~m2 & 0xff;
				int k3 = ~m3 & 0xff;
				int k4 = ~m4 & 0xff;
				
				//BINARY
				String b1 = Integer.toBinaryString(o1);
				String b2 = Integer.toBinaryString(o2);
				String b3 = Integer.toBinaryString(o3);
				String b4 = Integer.toBinaryString(o4);
				
				//INTEGER - BINARY
				int i1 = Integer.parseInt(b1);
				int i2 = Integer.parseInt(b2);
				int i3 = Integer.parseInt(b3);
				int i4 = Integer.parseInt(b4);
				
				
				//DIRECCION DE RED
				int r1 = o1 & m1;
				int r2 = o2 & m2;
				int r3 = o3 & m3;
				int r4 = o4 & m4;
				
				//DIRECCION DE RED - BINARIO
				String br1 = Integer.toBinaryString(r1);
				String br2 = Integer.toBinaryString(r2);
				String br3 = Integer.toBinaryString(r3);
				String br4 = Integer.toBinaryString(r4);
				
				//DIRECCION BROADCAST
				int c1 = o1 | k1;
				int c2 = o2 | k2;
				int c3 = o3 | k3;
				int c4 = o4 | k4;
				
				//DIRECCION BROADCAST - BINARIO
				String bc1 = Integer.toBinaryString(c1);
				String bc2 = Integer.toBinaryString(c2);
				String bc3 = Integer.toBinaryString(c3);
				String bc4 = Integer.toBinaryString(c4);
				
				//DIRECCION GATEWAY
				int g = r4 + 1;
				
				//DIRECCION GATEWAY - BINARIO
				String bg = Integer.toBinaryString(g);
				
				//OBJETO - CADENA
				ToString cadena  = new ToString();
				
				int registro = 1;
				
				
				// CLASE A -> 0.0.0.0 -> 126.255.255.255
				if ((i1 >= 0 && i1 <= 1111110) && (i2 >= 0 && i2 <= 11111111) && (i3 >= 0 && i3 <= 11111111)
						&& (i4 >= 0 && i4 <= 11111111) && (!mask.equalsIgnoreCase("")) && (!ip.equalsIgnoreCase(""))
						&& (m4 >= 0 && m4 <= 252)) {
					
					// PRIVADA - 10.x.x.x
					if ((i1 == 1010) && (i2 >= 0 && i2 <= 11111111) && (i3 >= 0 && i3 <= 11111111)
							&& (i4 >= 0 && i4 <= 11111111)) {
						
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "A", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "A", "Direccion Broadcast");
							registro ++;
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "A", "No Pertenece", "Si -> Red Privada.", r1,
									r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "A", "Red Privada");
							registro ++;
						}
					} 
					
					// IANA - 0.0.0.0
					else if ((i1 == 0) && (i2 == 0) && (i3 == 0) && (i4 == 0)) {
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "A", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "A", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "A", "No Pertenece",
									"Si -> Reservada por IANA para Identificacion Local.", r1, r2, r3, r4, br1, br2,
									br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "A", "IANA");
						}
					} 
					
					// CARRIER NAT - 100.64.X.X
					else if ((i1 == 1100100) && (i2 >= 1000000 && i2 <= 1111111) && (i3 >= 0 && i3 <= 11111111)
							&& (i4 >= 0 && i4 <= 11111111)) {
						
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "A", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "A", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "A", "No Pertenece",
									"Si -> Espacio dedicado para despliegues Carrier Grade NAT.", r1, r2, r3, r4, br1,
									br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "A", "Carrier Grade NAT");
						}
					} 
					
					// RED ACTUAL - 0.X.X.X
					else if ((i1 == 0) && (i2 >= 0 && i2 <= 11111111) && (i3 >= 0 && i3 <= 11111111)
							&& (i4 >= 1 && i4 <= 11111111)) {
						
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "A", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "A", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "A", "No Pertenece",
									"Si -> Red Actual (solo válida como dirección de origen).", r1, r2, r3, r4, br1,
									br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "A", "Red Actual");
						}
					}
					
					// PUBLICA
					else {
						
						if ((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "A", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "A", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "A", "No Pertenence", "No",
								r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "A", "No");
						}
					}
				}
				
				
				// CLASE A - 127.X.X.X - LOOPBACK
				else if ((i1 == 1111111) && (i2 >= 0 && i2 <= 11111111) && (i3 >= 0 && i3 <= 11111111)
						&& (i4 >= 0 && i4 <= 11111111) && (!mask.equalsIgnoreCase("")) && (!ip.equalsIgnoreCase(""))
						&& (m4 >= 0 && m4 <= 252)) {
					
					if ((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
						data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "A", "No Pertenece", "Si -> Direccion Broadcast.",
								r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
						db.dbIngresarIP(ip, mask, "Publica", "A", "Direccion Broadcast");
					}
					else {
						data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "A", "No Pertenece",
								"Si -> Direccion de Bucle Local o 'Loopback'.", r1, r2, r3, r4, br1, br2, br3, br4,
								c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
						db.dbIngresarIP(ip, mask, "Publica", "A", "Loopback");
					}
				}
				
				
				// CLASE B - 128.0.0.0 - 192.255.255.255
				else if ((i1 >= 10000000 && i1 <= 10111111) && (i2 >= 0 && i2 <= 11111111)
						&& (i3 >= 0 && i3 <= 11111111) && (i4 >= 0 && i4 <= 11111111) && (!mask.equalsIgnoreCase(""))
						&& (!ip.equalsIgnoreCase("")) && (m4 >= 0 && m4 <= 252)) {
					
					// PRIVADA - 172.16.x.x. -> 172.31.x.x
					if ((i1 == 10101100) && (i2 >= 10000 && i2 <= 11111) && (i3 >= 0 && i3 <= 11111111)
							&& (i4 >= 0 && i4 <= 11111111)) {
						
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "B", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "B", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "B", "No Pertenece", "Si -> Red Privada.", r1,
									r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "B", "Red Privada");
						}
					} 
					
					// APIPA - UNICAST - 169.254.X.X
					else if ((i1 == 10101001) && (i2 == 11111110) && (i3 >= 0 && i3 <= 11111111)
							&& (i4 >= 1 && i4 <= 11111110)) {
						
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "B", "Si Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "B", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "B", "Si Pertenece",
									"Si -> APIPA y Unicast. Cuando el sistema no encuentra un servidor DHCP.", r1, r2,
									r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "B", "APIPA y Unicast");
						}
					}
					
					// PUBLICA
					else {
						
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "B", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "B", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "B", "No Pertenece", "No", r1,
									r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "B", "No");
						}
					}
				}
				
				
				// CLASE C
				else if ((i1 >= 11000000 && i1 <= 11011111) && (i2 >= 0 && i2 <= 11111111)
						&& (i3 >= 0 && i3 <= 11111111) && (i4 >= 0 && i4 <= 11111111) && (!mask.equalsIgnoreCase(""))
						&& (!ip.equalsIgnoreCase("")) && (m4 >= 0 && m4 <= 252)) {
					
					// PRIVADA
					if ((i1 == 11000000) && (i2 == 10101000) && (i3 >= 0 && i3 <= 11111111)
							&& (i4 >= 0 && i4 <= 11111111)) {
						
						if ((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "C", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "C", "Direccion Broadcast");
						}
						else {
						data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "C", "No Pertenece", "Si -> Red Privada.",
								r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
						db.dbIngresarIP(ip, mask, "Privada", "C", "Red Privada");
						}
					}
					
					// PRIVADA - 192.0.0.X
					else if ((i1 == 11000000) && (i2 == 0) && (i3 == 0) && (i4 >= 0 && i4 <= 11111111)) {
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "C", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "C", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "C", "No Pertenece",
									"Si -> Asignaciones de protocolo IETF.", r1, r2, r3, r4, br1, br2, br3, br4, c1,
									c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "C", "Protocolo IETF");
						}
					} 
					
					// PUBLICA - 192.0.2.X
					else if ((i1 == 11000000) && (i2 == 0) && (i3 == 10) && (i4 >= 0 && i4 <= 11111111)) {
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "C", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "C", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "C", "No Pertenece",
									"Si -> Asignado como TEST-NET-1, documentación y ejemplos.", r1, r2, r3, r4, br1,
									br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "C", "TEST-NET-1");
						}
					} 
					
					// PUBLICA - 192.88.99.X
					else if ((i1 == 11000000) && (i2 == 1011000) && (i3 == 1100011) && (i4 >= 0 && i4 <= 11111111)) {
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "C", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "C", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "C", "No Pertenece",
									"Si -> Internet. Previamente usado para relay IPv6 a IPv4.", r1, r2, r3, r4, br1,
									br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "C", "Relay IPv6 a IPv4");
						}
						
					}
					
					// PRIVADA - 198.18.x.x - 198.19.x.x
					else if ((i1 == 11000110) && (i2 >= 10010 && i2 <= 10011) && (i3 >= 0 && i3 <= 11111111)
							&& (i4 >= 0 & i4 <= 11111111)) {
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "C", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "C", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Privada", "C", "No Pertenece",
									"Si -> Utilizada para pruebas de referencia de comunicaciones entre dos subredes separadas.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Privada", "C", "Pruebas para Subredes Separadas");
						}
					}
					
					// PUBLICA - 198.51.100.X
					else if ((i1 == 11000110) && (i2 == 110011) && (i3 == 1100100) && (i4 >= 0 && i4 <= 11111111)) {
						if ((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "C", "No Pertenece",
									"Si -> Direccion Broadcast.", r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "C", "Direccion Broadcast");
						} 
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "C", "No Pertenece",
									"Si -> Asignado como TEST-NET-2, documentación y ejemplos.", r1, r2, r3, r4, br1,
									br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "C", "TEST-NET-2");
						}
					}
					
					// PUBLICA - 203.0.113.X
					else if ((i1 == 11001011) && (i2 == 0) && (i3 == 1110001) && (i4 >= 0 && i4 <= 11111111)) {
						
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "C", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "C", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "C", "No Pertenece",
									"Si -> Asignado como TEST-NET-3, documentación y ejemplos.", r1, r2, r3, r4, br1,
									br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "C", "TEST-NET-3");
						}
					}
					
					// PUBLICA
					else {
						
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "C", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "C", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "C", "No Pertenece", "No", r1,
									r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "C", "No");
						}
					}
				}
				
				
				// CLASE D - MULTICAST - 224.x.x.x -> 239.x.x.x
				else if ((i1 >= 11100000 && i1 <= 11101111) && (i2 >= 0 && i2 <= 11111111)
						&& (i3 >= 0 && i3 <= 11111111) && (i4 >= 0 && i4 <= 11111111) && (!mask.equalsIgnoreCase(""))
						&& (!ip.equalsIgnoreCase("")) && (m4 >= 0 && m4 <= 252)) {
					
					if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
						data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "D", "No Pertenece", "Si -> Direccion Broadcast.",
								r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
						db.dbIngresarIP(ip, mask, "Publica", "D", "Direccion Broadcast");
					}
					else {
						data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "D", "No Pertenece",
								"Si -> Multicast IP.", r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2,
								bc3, bc4, g, bg));
						db.dbIngresarIP(ip, mask, "Publica", "D", "Multicast IP");
					}
				}
				
				// CLASE E - 240.x.x.x - 255.x.x.x
				else if ((i1 >= 11110000 && i1 <= 11111111) && (i2 >= 0 && i2 <= 11111111)
						&& (i3 >= 0 && i3 <= 11111111) && (i4 >= 0 && i4 <= 11111111) && (!mask.equalsIgnoreCase(""))
						&& (!ip.equalsIgnoreCase("")) && (m4 >= 0 && m4 <= 252)) {
					
					// SUBRED
					if ((i1 == 11111111) && (i2 == 11111111) && (i3 == 11111111) && (i4 == 11111111)) {
						
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "E", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "E", "Direccion Broadcast");
						}
						else {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "E", "No Pertenece",
									"Si -> Subred. Reservada para destinos multidifusión.", r1, r2, r3, r4, br1, br2,
									br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "E", "Destinos Multidifusion");
						}
					} 
					
					else {
						
						if((o1 == c1) && (o2 == c2) && (o3 == c3) && (o4 == c4)) {
							data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "E", "No Pertenece", "Si -> Direccion Broadcast.",
									r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
							db.dbIngresarIP(ip, mask, "Publica", "E", "Direccion Broadcast");
						}
						else {
						data.setText(cadena.Escribir(ip, b1, b2, b3, b4, "Publica", "E", "No Pertenece", "Si -> Internet. Reservada para usos futuros (Experimental).",
								r1, r2, r3, r4, br1, br2, br3, br4, c1, c2, c3, c4, bc1, bc2, bc3, bc4, g, bg));
						db.dbIngresarIP(ip, mask, "Publica", "E", "Experimental");
						}
					}
				}
				
				//MASCARA VACIA
				else if(mask.isEmpty() && !ip.isEmpty()) {
					data.setText("  Introduzca una Mascara de Subred, o genere una Por Defecto\n\n");
				}
				
				//MASCARA FUERA DE RANGO
				else if(m4 < 0 || m4 > 252) {
					data.setText("  Mascara Fuera de Rango");
				}
				
				//IP VACIA
				else if(ip.isEmpty() && !mask.isEmpty()) {
					data.setText("  Introduzca una IP\n\n");
				}
				
				else if(ip.isEmpty() && mask.isEmpty()) {
					data.setText("  Introduzca una IP y una Mascara de Subred, o genera una Por Defecto\n\n");
				}
				
				//else if(o1 < 0 || o1)
				
				else {
					data.setText("  IP fuera de Rango\n\n");
				}
				
				registro ++;
				
				view.invalidate();
				view.validate();
			}
		});
		//END_EVENT_JBUTTON - ingresar
		
		
		//EVENT - BUTTON - limpiar
		limpiar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				iptext.setText("");
				masktext.setText("");
				data.setText("");
				hosttext.setText("");
			}
		});
		//END_EVENT_BUTTON - limpiar
		
		
		//EVENT - BUTTON - agenerar
		agenerar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				//IP
				String ip = iptext.getText();
				String cds[]= ip.split("[.]", 4);
				
				//IP- INTEGER
				int o1 = 0;
				int o2 = 0;
				int o3 = 0;
				int o4 = 0;
				
				o1 = Integer.parseInt(cds[0]);
				o2 = Integer.parseInt(cds[1]);
				o3 = Integer.parseInt(cds[2]);
				o4 = Integer.parseInt(cds[3]);
				
				//MASCARA - INTEGER
				int m1 = 0;
				int m2 = 0;
				int m3 = 0;
				int m4 = 0;
				
				String mask = "";
				
				
				//CLASE A
				if ((o1 >= 0 && o1 <= 127) && (o2 >= 0 && o2 <= 255) && (o3 >= 0 && o3 <= 255)
						&& (o4 >= 0 && o4 <= 255)) {
					m1 = 255;
					m2 = 0;
					m3 = 0;
					m4 = 0;

					mask = m1 + "." + m2 + "." + m3 + "." + m4;

					masktext.setText(mask);
				}
				
				//CLASE B
				else if ((o1 >= 128 && o1 <= 191) && (o2 >= 0 && o2 <= 255) && (o3 >= 0 && o3 <= 255)
						&& (o4 >= 0 && o4 <= 255)) {
					m1 = 255;
					m2 = 255;
					m3 = 0;
					m4 = 0;

					mask = m1 + "." + m2 + "." + m3 + "." + m4;

					masktext.setText(mask);
				}
				
				//CLASE C
				else if((o1 >= 192 && o1 <= 223) && (o2 >= 0 && o2 <= 255) && (o3 >= 0 && o3 <= 255) && (o4>= 0 && o4 <= 255)) {
					m1 = 255;
					m2 = 255;
					m3 = 255;
					m4 = 0;

					mask = m1 + "." + m2 + "." + m3 + "." + m4;

					masktext.setText(mask);
				}
				else {
					data.setText("   No se puede generar una Mascara Por Defecto para esta IP\n\n");
				}
			}
		});
		//END_EVENT_BUTTON - agenerar
		
		
		//EVENT - BUTTON - Registros
		registro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Second_View segunda = new Second_View();
				view.setContentPane(segunda.getJPanel());
				
				segunda.volver.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						view.setContentPane(panel);
					}
				});
				
				view.invalidate();
	        	view.validate();
			}
		});
		//END_EVENT_BUTTON - Registro
		
		
		//EVENT - BUTTON - host
		host.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				ToString cadena = new ToString();
				
				String h = hosttext.getText();
				int num = 0;
				int x = 0;
				if (!hosttext.getText().isEmpty()) {
					num = Integer.parseInt(h);

					x = (int) (32 - (Math.log10(num) / Math.log10(2)));
				}
				
				String mascara = "00000000.00000000.00000000.00000000";
				
				for(int i = 0; i < x; i++) {
					mascara = mascara.replaceFirst("0", "1");
				}
				
				String newmask[] = mascara.split("[.]", 4);
				
				if((x >= 8 && x <= 15) && (!hosttext.getText().isEmpty())) {
					data.setText(cadena.EHosts(h, x, "10.0.0.0", newmask[0], newmask[1],
							newmask[2], newmask[3]));
				}
				else if((x >= 16 && x <= 23) && (!hosttext.getText().isEmpty())) {
					data.setText(cadena.EHosts(h, x, "172.16.0.0", newmask[0], newmask[1],
							newmask[2], newmask[3]));
				}
				else if((x >= 24 && x <= 30) && (!hosttext.getText().isEmpty())) {
					data.setText(cadena.EHosts(h, x, "192.168.0.0", newmask[0], newmask[1],
							newmask[2], newmask[3]));
				}
				
				else if(hosttext.getText().isEmpty()) {
					data.setText("  Ingrese una cantidad de Hosts\n");
				}
				else {
					data.setText("  Red no Calculable");;
				}
				
				view.invalidate();
	        	view.validate();
			}
		});
		//END_EVENT_BUTTON - host
		
		ipv6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Third_View tercera = new Third_View();
				view.setContentPane(tercera.getJPanel());
				
				tercera.volver.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						view.setContentPane(panel);
					}
				});
				
				view.invalidate();
	        	view.validate();
			}
		});
		//END_EVENT - BUTTON - ipv6
		
		ayuda.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ToString ayuda = new ToString();
				data.setText(ayuda.ayudaipv4());
			}
		});
		
		view.setBounds(390, 90, 600, 550);
		view.setVisible(true);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	//END_CLASS
	
	
	//NEW METHOD - crearPNorth()
	private JPanel crearPNorth() {
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel norte =  PNorth();
		p.add(norte, BorderLayout.NORTH);
		
		JPanel centro = PCenter();
		p.add(centro, BorderLayout.CENTER);
		
		return p;
	}
	
	//METHOD - PNorth() -> crearPNorth()
	private JPanel PNorth() {
		JPanel p = new JPanel(new FlowLayout());
		
		titulo = new JLabel(".:CALCULADORA IP:.");
		p.add(titulo);
		
		p.setBorder(border);
		
		
		return p;
	}
	
	//METHOD - PCenter() -> crearPCenter()
	private JPanel PCenter() {
		JPanel p = new JPanel(new BorderLayout());
		
		//TITLEDBORDER
		TitledBorder titledborder =  BorderFactory.createTitledBorder(border, "IPv4:.");
		p.setBorder(titledborder);
		
		//ingresar = new JButton("Calcular");;
		//p.add(ingresar, BorderLayout.EAST);
		
		JPanel centro = CentroC();
		p.add(centro, BorderLayout.CENTER);
		
		return p;
	}
	
	//METHOD - CentroC() - PCenter() - crearPNorth()
	private JPanel CentroC() {
		JPanel p = new JPanel(new BorderLayout());
		
		iplabel = new JLabel("   IP :   ");
		p.add(iplabel, BorderLayout.WEST);
		
		iptext = new JTextField();
		//iptext.setBorder(border);
		p.add(iptext, BorderLayout.CENTER);
		
		ingresar = new JButton("CALCULAR");
		p.add(ingresar, BorderLayout.EAST);
		
		JPanel sur = SurC();
		p.add(sur, BorderLayout.SOUTH);
		
		return p;
	}
	
	//METHOD - SurC() - CentroC() - PCenter() - crearPNorth()
	private JPanel SurC() {
		JPanel p = new JPanel(new BorderLayout());
		
		masklabel = new JLabel("   Mascara :   ");
		p.add(masklabel, BorderLayout.WEST);
		
		masktext = new JTextField();
		p.add(masktext, BorderLayout.CENTER);
		
		agenerar = new JButton("Generar Mascara");
		p.add(agenerar, BorderLayout.EAST);
		
		JPanel hosts = PHosts();
		p.add(hosts, BorderLayout.SOUTH);
		
		
		return p;
	}
	
	//METHOD - PHosts() - SurC() - CentroC - PCenter() - crearPNorth()
		private JPanel PHosts() {
			JPanel p = new JPanel(new BorderLayout());
			
			hostlabel = new JLabel("   # de Hosts :   " );
			p.add(hostlabel, BorderLayout.WEST);
			
			hosttext = new JTextField();
			p.add(hosttext, BorderLayout.CENTER);
			
			host = new JButton("Calcular Red");
			p.add(host, BorderLayout.EAST);
			
			//TITLEDBORDER
			TitledBorder titledborder = BorderFactory.createTitledBorder(border, "Generar IP por Hosts:.");
			p.setBorder(titledborder);
			return p;
		}
	
	//NEW METHOD - crearPCenter()
	private JPanel crearPCenter() {
		JPanel p =  new JPanel(new BorderLayout());
		
		//TITLEDBORDER
		TitledBorder titledborder =  BorderFactory.createTitledBorder(border, "Resultados:.");
		p.setBorder(titledborder);
		
		data = new JTextArea();
		data.setBorder(border);
		data.append(String.format("%95s", ".:CALCULADORA IPv4:.\n\n"));
		data.append("     .:Para Calcular una IP:.\n");
		data.append("     ~Ingrese una IPv4\n     ~Ingrese una Mascara\n");
		data.append("     ~Presione el boton 'CALCULAR'\n\n");
		data.append("     PD : Puede generar una Mascara Por Defecto con el boton 'Generar Mascara'\n");
		data.append("             o introducir una en el campo 'Mascara'.\n\n" );
		data.append("     .:Para calcular una Red dados los Hosts:.\n");
		data.append("     ~Ingrese una cantidad de Hosts\n");
		data.append("     ~Presione el boton 'Calcular Red'\n\n");
		p.add(data, BorderLayout.CENTER);
		
		scroll = new JScrollPane(data);
		p.add(scroll, BorderLayout.CENTER);
		
		return p;
	}
	
	
	
	//NEW METHOD - crearPSouth()
	private JPanel crearPSouth() {
		JPanel p = new JPanel(new FlowLayout());
		
		registro = new JButton("Registros");
		p.add(registro);
		
		limpiar = new JButton("Limpiar");
		p.add(limpiar);
		
		ipv6 = new JButton("Calcular IPv6");
		p.add(ipv6);
		
		ayuda = new JButton("Ayuda");
		p.add(ayuda);
		
		p.setBorder(border);
		return p;
	}
}
