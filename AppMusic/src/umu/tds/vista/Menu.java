package umu.tds.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import java.awt.Color;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.GridBagLayout;
import java.awt.Window;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.CustomSliderUI;
import umu.tds.negocio.Cancion;
import pulsador.Luz;
import javax.swing.JSlider;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;

public class Menu extends JPanel {

	private static final long serialVersionUID = 1L;
	JFrame frame = new JFrame("File Chooser");
	private Window launcher;
	/**
	 * Create the panel.
	 */
	private JPanel home,playlist,busqueda; //Panel que te lleva al home
	public Menu(Window launcher) {
		this.launcher = launcher;
		initialize();
	}
	
	private void initialize()
	{
		setBackground(new Color(18, 159, 186));
		setLayout(new BorderLayout(0, 0));
		
		JPanel PanelBotonesMenu = new JPanel();
		PanelBotonesMenu.setBackground(new Color(18, 156, 189));
		add(PanelBotonesMenu, BorderLayout.SOUTH);
		
		JLabel btnHome = new JLabel("");
		btnHome.setIcon(new ImageIcon(Menu.class.getResource("/ImagenesMenu/home_icon_190886.png")));
		PanelBotonesMenu.add(btnHome);
		
		JLabel lblNewLabel_1 = new JLabel("   ");
		PanelBotonesMenu.add(lblNewLabel_1);
		
		JLabel btnLupa = new JLabel("");
		btnLupa.setIcon(new ImageIcon(Menu.class.getResource("/ImagenesMenu/lupa.png")));
		PanelBotonesMenu.add(btnLupa);
		
		JLabel lblNewLabel_2 = new JLabel("  ");
		PanelBotonesMenu.add(lblNewLabel_2);
		
		JLabel btnPlaylist = new JLabel("");
		btnPlaylist.setIcon(new ImageIcon(Menu.class.getResource("/ImagenesMenu/libros.png")));
		PanelBotonesMenu.add(btnPlaylist);
		
		JLabel lblNewLabel_3 = new JLabel("   ");
		PanelBotonesMenu.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Menu.class.getResource("/ImagenesMenu/descargar.png")));
		PanelBotonesMenu.add(lblNewLabel);
		
		JPanel PanelBotonIcono = new JPanel();
		PanelBotonIcono.setBackground(new Color(18, 156, 189));
		add(PanelBotonIcono, BorderLayout.EAST);
		PanelBotonIcono.setLayout(new BorderLayout(0, 0));
		
		JPanel rellenoIcono1 = new JPanel();
		rellenoIcono1.setBackground(new Color(18, 156, 189));
		PanelBotonIcono.add(rellenoIcono1, BorderLayout.WEST);
		
		JPanel rellenoIcono2 = new JPanel();
		rellenoIcono2.setBackground(new Color(18, 156, 189));
		PanelBotonIcono.add(rellenoIcono2, BorderLayout.EAST);
		
		JPanel rellenoIcono3 = new JPanel();
		rellenoIcono3.setBackground(new Color(18, 156, 189));
		PanelBotonIcono.add(rellenoIcono3, BorderLayout.NORTH);
		
		Luz btnLuz = new Luz();
		PanelBotonIcono.add(btnLuz, BorderLayout.SOUTH);
		btnLuz.setColor(Color.white);
		
		JPanel PanelVolumenIcono = new JPanel();
		PanelVolumenIcono.setBackground(new Color(18, 156, 189));
		PanelBotonIcono.add(PanelVolumenIcono, BorderLayout.CENTER);
		PanelVolumenIcono.setLayout(new BorderLayout(0, 0));
		
		JLabel botonIcono = new JLabel("");
		botonIcono.setIcon(new ImageIcon(Menu.class.getResource("/ImagenesMenu/usuario.png")));
		PanelVolumenIcono.add(botonIcono, BorderLayout.NORTH);
		
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(botonIcono, popupMenu);
		
		JMenuItem btnPremium = new JMenuItem("Premium");
		popupMenu.add(btnPremium);
		
		JMenuItem btnLogout = new JMenuItem("Logout");
		popupMenu.add(btnLogout);
		
		
		
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(18, 156, 189));
		PanelVolumenIcono.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{30, 0, 0, 30, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel Volumen = new JLabel("50%");
		Volumen.setFont(new Font("Arial", Font.BOLD, 14));
		Volumen.setForeground(new Color(255, 255, 255));
		GridBagConstraints gbc_Volumen = new GridBagConstraints();
		gbc_Volumen.insets = new Insets(0, 0, 5, 5);
		gbc_Volumen.gridx = 1;
		gbc_Volumen.gridy = 1;
		panel.add(Volumen, gbc_Volumen);
		
		JSlider barraVolumen = new JSlider();
		barraVolumen.setPreferredSize(new Dimension(26, 100));
		barraVolumen.setBackground(new Color(18, 156, 189));
		barraVolumen.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_barraVolumen = new GridBagConstraints();
		gbc_barraVolumen.insets = new Insets(0, 0, 5, 5);
		gbc_barraVolumen.gridx = 1;
		gbc_barraVolumen.gridy = 2;
		barraVolumen.setUI(new CustomSliderUI(barraVolumen));
		panel.add(barraVolumen, gbc_barraVolumen);
		ControladorAppMusic.getInstancia().setSliderVolumen(barraVolumen);
		
		JPanel PanelNavegacion = new JPanel();
		PanelNavegacion.setBackground(new Color(18, 159, 186));
		add(PanelNavegacion, BorderLayout.CENTER);
		PanelNavegacion.setLayout(new CardLayout(0, 0));
		
		JPanel rellenoMargen = new JPanel();
		rellenoMargen.setBackground(new Color(18, 156, 189));
		add(rellenoMargen, BorderLayout.WEST);
		GridBagLayout gbl_rellenoMargen = new GridBagLayout();
		gbl_rellenoMargen.columnWidths = new int[]{80, 0};
		gbl_rellenoMargen.rowHeights = new int[]{0, 0};
		gbl_rellenoMargen.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_rellenoMargen.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		rellenoMargen.setLayout(gbl_rellenoMargen);
		
		// AÑADIR MENU DE HOME
		home = new MenuHome();
		PanelNavegacion.add(home, "Home");
		
		// AÑADIR MENU DE PLAYLIST
		playlist = new MenuPlaylist();
		PanelNavegacion.add(playlist,"Playlist");
		
		//AÑADIR MENU DE BUSQUEDA
		busqueda = new MenuBusqueda();
		PanelNavegacion.add(busqueda,"Busqueda");
		
		// EVENTOS 
		
		btnHome.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CardLayout cardlayout = (CardLayout) PanelNavegacion.getLayout();
				cardlayout.show(PanelNavegacion, "Home");
			}
		});
		
		btnPlaylist.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CardLayout cardlayout = (CardLayout) PanelNavegacion.getLayout();
				cardlayout.show(PanelNavegacion, "Playlist");
			}
		});
		
		btnLupa.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CardLayout cardlayout = (CardLayout) PanelNavegacion.getLayout();
				cardlayout.show(PanelNavegacion, "Busqueda");
			}
		});
		
		btnLuz.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos xml (.xml)", "xml");
		        fileChooser.setFileFilter(filter);
		        fileChooser.setAcceptAllFileFilterUsed(false);

		        int result = fileChooser.showOpenDialog(frame);
		        
		        // Verificar si el usuario seleccionó un archivo
		        if (result == JFileChooser.APPROVE_OPTION) {
		            // Obtener el archivo seleccionado
		            java.io.File selectedFile = fileChooser.getSelectedFile();
		            //System.out.println("Archivo seleccionado: " + selectedFile.getAbsolutePath());
		            ControladorAppMusic.getInstancia().cargarCanciones(selectedFile.getAbsolutePath());
		        } else {
		            System.out.println("Selección de archivo cancelada por el usuario"); 
		        }
			}
		});
		
		btnLogout.addActionListener((e) -> {
			launcher.dispose();
			Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
			if(c != null)
				ControladorAppMusic.getInstancia().reproducirCancion("stop", c);
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
						Launcher window = new Launcher();
						window.getFrame().setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		});
		
		btnPremium.addActionListener((e) -> {});
		
		barraVolumen.addChangeListener((e) -> {
			ControladorAppMusic.getInstancia().actualizarVolumen();
			Volumen.setText(barraVolumen.getValue() + "%");
		} );
		
		
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		
		
	}
	
	
	
	

	
	
	
	
	
}
