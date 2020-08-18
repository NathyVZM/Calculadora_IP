package calcu_ip;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class Second_View {
	
	private JLabel title;
	private JPanel panel;
	private JTextArea data;
	private Border border, bevel;
	public JButton volver;
	private JScrollPane scroll;
	
	 DB db = DB.getInstances();
	
	public Second_View() {
		panel = new JPanel(new BorderLayout());
		
		border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		bevel = BorderFactory.createRaisedSoftBevelBorder();

		JPanel pNorth = crearPNorth();
		panel.add(pNorth, BorderLayout.NORTH);
		
		JPanel pCenter = crearPCenter();
		panel.add(pCenter, BorderLayout.CENTER);
		
		JPanel pSouth = crearPSouth();
		panel.add(pSouth, BorderLayout.SOUTH);
	}
	
	//NEW METHOD - getJPanel()
	public JPanel getJPanel() {
		return this.panel;
	}
	
	
	//NEW METHOD - crearPNorth()
	private JPanel crearPNorth() {
		JPanel p = new JPanel();
		
		title = new JLabel(".:REGISTROS:.");
		p.add(title);
		
		p.setBorder(border);
		
		return p;
	}
	
	
	//NEW METHOD - crearPCenter()
	private JPanel crearPCenter() {
		JPanel p = new JPanel(new BorderLayout());
		
		data = new JTextArea();
		
		data.setText(db.dbStatement("select *from ipv4"));
		//data.setText(db.dbStatementIPV6("select *from ipv6"));
		p.add(data, BorderLayout.CENTER);
		data.setBorder(border);
		
		scroll = new JScrollPane(data);
		p.add(scroll, BorderLayout.CENTER);
		
		//TitledBorder titledborder = BorderFactory.createTitledBorder(border, "Registros");
		p.setBorder(bevel);
		
		return p;
	}
	
	
	//NEW METHOD - crearPSouth()
	private JPanel crearPSouth() {
		JPanel p = new JPanel(new FlowLayout());
		
		volver = new JButton("Volver");
		p.add(volver);
		
		p.setBorder(border);
		
		return p;
	}

}
