package umu.tds.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicSliderUI;

import umu.tds.controlador.ControladorAppMusic;
import umu.tds.helper.Aleatorio;
import umu.tds.helper.AlineamientoLista;
import umu.tds.helper.CustomSliderUI;
import umu.tds.negocio.Cancion;
import umu.tds.negocio.IReproductorListener;

public class MenuPremium extends JPanel implements IReproductorListener{
	private static final long serialVersionUID = 1L;
	private AlineamientoLista alineamientoListaMenu;
	private String opcionReproduccion;
	private JLabel msgMasEscuchadas;
	private ListaModelo<Cancion> miModelo;
	private JLabel btnStop;
	private JLabel btnRedo;
	private JLabel btnPlay;
	private JLabel btnForwa;
	private JLabel btnRandom;
	private MenuPremium menuPremium;
	private JList<Cancion> listaCanciones;
	private JSlider barraReproduccion = new JSlider();
	private JLabel msgDuracion = new JLabel("00:00");
	private boolean modoAleatorio;
	private JFrame frame = new JFrame("Seleccionar Carpeta");
	private static String PLAY = "play";
	private static String STOP = "stop";
	private static String PAUSE = "pause";

	/**
	 * Create the panel.
	 */
	public MenuPremium() {
		this.opcionReproduccion = PLAY;
		miModelo = new ListaModelo<Cancion>();
		menuPremium = this;
		modoAleatorio = false;
		ControladorAppMusic.getInstancia().setMenuPremium(menuPremium, barraReproduccion, msgDuracion);
		initialize();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize()
	{
		// HELPER
		alineamientoListaMenu = new AlineamientoLista("izquierda");
		
		
		setBackground(new Color(18, 159, 186));
		setLayout(new BorderLayout(0, 0));
		
		JPanel PanelReproduccion = new JPanel();
		PanelReproduccion.setBackground(new Color(18, 159, 186));
		add(PanelReproduccion, BorderLayout.SOUTH);
		
		btnStop = new JLabel("");
		btnStop.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/cuadrado.png")));
		PanelReproduccion.add(btnStop);
		
		btnRedo = new JLabel("");
		btnRedo.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/atras.png")));
		PanelReproduccion.add(btnRedo);
		
		btnPlay = new JLabel("");
		btnPlay.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
		PanelReproduccion.add(btnPlay);
		
		btnForwa = new JLabel("");
		btnForwa.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/siguiente.png")));
		PanelReproduccion.add(btnForwa);
		
		btnRandom = new JLabel("");
		btnRandom.setIcon(new ImageIcon(MenuPremium.class.getResource("/ImagenesMenu/repetir.png")));
		PanelReproduccion.add(btnRandom);
		
		JLabel btnPDF = new JLabel("");
		btnPDF.setIcon(new ImageIcon(MenuPremium.class.getResource("/ImagenesMenu/descargar.png")));
		PanelReproduccion.add(btnPDF);
		
		JPanel PanelBienvenida = new JPanel();
		PanelBienvenida.setBackground(new Color(18, 159, 186));
		add(PanelBienvenida, BorderLayout.NORTH);
		PanelBienvenida.setLayout(new BorderLayout(0, 0));
		
		msgMasEscuchadas = new JLabel("Reproduce las canciones más escuchadas");
		msgMasEscuchadas.setForeground(new Color(255, 255, 255));
		msgMasEscuchadas.setHorizontalAlignment(SwingConstants.CENTER);
		msgMasEscuchadas.setFont(new Font("Arial", Font.BOLD, 24));
		PanelBienvenida.add(msgMasEscuchadas, BorderLayout.CENTER);
		
		JPanel rellenoPremium1 = new JPanel();
		rellenoPremium1.setBackground(new Color(18, 156, 189));
		PanelBienvenida.add(rellenoPremium1, BorderLayout.SOUTH);
		
		JPanel rellenoPremium2 = new JPanel();
		rellenoPremium2.setBackground(new Color(18, 156, 189));
		PanelBienvenida.add(rellenoPremium2, BorderLayout.NORTH);
		
		JPanel PanelRecientes = new JPanel();
		PanelRecientes.setBackground(new Color(18, 159, 186));
		add(PanelRecientes, BorderLayout.CENTER);
		PanelRecientes.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		PanelRecientes.add(scrollPane, BorderLayout.CENTER);
		
		listaCanciones = new JList();
		listaCanciones.setBackground(new Color(193, 255, 245));
		listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaCanciones.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		listaCanciones.setModel(miModelo);
		listaCanciones.setSelectionBackground(new Color(0,128,255));
		listaCanciones.setSelectionForeground(Color.WHITE);
		scrollPane.setViewportView(listaCanciones);
		listaCanciones.setBorder(BorderFactory.createLineBorder(Color.black));
		listaCanciones.setCellRenderer(alineamientoListaMenu);
		
		JPanel LayoutTiempo = new JPanel();
		LayoutTiempo.setBackground(new Color(18, 159, 186));
		PanelRecientes.add(LayoutTiempo, BorderLayout.SOUTH);
		GridBagLayout gbl_LayoutTiempo = new GridBagLayout();
		gbl_LayoutTiempo.columnWidths = new int[]{55, 228, 5, 0};
		gbl_LayoutTiempo.rowHeights = new int[]{1};
		gbl_LayoutTiempo.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		gbl_LayoutTiempo.rowWeights = new double[]{1.0};
		LayoutTiempo.setLayout(gbl_LayoutTiempo);
		
		JPanel PanelTiempo = new JPanel();
		PanelTiempo.setBackground(new Color(18, 156, 189));
		GridBagConstraints gbc_PanelTiempo = new GridBagConstraints();
		gbc_PanelTiempo.insets = new Insets(0, 0, 0, 5);
		gbc_PanelTiempo.gridx = 1;
		gbc_PanelTiempo.gridy = 0;
		LayoutTiempo.add(PanelTiempo,gbc_PanelTiempo);
		
		JPanel subPanelTiempo = new JPanel();
		subPanelTiempo.setBackground(new Color(18, 156, 189));
		PanelTiempo.add(subPanelTiempo);
		subPanelTiempo.setLayout(new BorderLayout(0, 0));
		
		
		barraReproduccion.setBackground(new Color(18, 156, 189));
		barraReproduccion.setPreferredSize(new Dimension(400, 26));
		barraReproduccion.setValue(0);
		barraReproduccion.setEnabled(false);
		barraReproduccion.setUI(new CustomSliderUI(barraReproduccion));
		subPanelTiempo.add(barraReproduccion, BorderLayout.NORTH);
		
		
		msgDuracion.setForeground(new Color(255, 255, 255));
		msgDuracion.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_msgDuracion = new GridBagConstraints();
		gbc_msgDuracion.gridx = 3;
		gbc_msgDuracion.gridy = 0;
		LayoutTiempo.add(msgDuracion, gbc_msgDuracion);
		
		
        listaCanciones.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e)
        	{
        		if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
    				umu.tds.negocio.Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
    				if(c!=null)
    				{
    					opcionReproduccion = STOP;
    					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
    				}
    				ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
    				opcionReproduccion = PLAY;
                        ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuPremium);
        				c = listaCanciones.getSelectedValue();
        				if(c == null)
        				{
        					c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
        				}
        				if(c != null)
        				{
        					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
        					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
        					if(opcionReproduccion.equals(PLAY))
        					{
        						opcionReproduccion = PAUSE;
        					}
        					else
        					{
        						opcionReproduccion = PLAY;
        					}
        				}
                    
                }
        	}
        });
		
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				umu.tds.negocio.Cancion c = listaCanciones.getSelectedValue();
				if(c == null)
				{
					c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				}
				if(c != null)
				{
					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
					if(opcionReproduccion.equals(PLAY))
					{
						opcionReproduccion = PAUSE;
					}
					else
					{
						opcionReproduccion = PLAY;
					}
				}
				ControladorAppMusic.getInstancia().actualizarPanelReproduccion(menuPremium);
			}
		});
		
		btnRedo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				if(c != null)
				{
					opcionReproduccion = STOP;
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
					opcionReproduccion = PLAY;
				}
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia().comprobarPaneles(menuPremium);
				if(vengoOtroPanel && listaCanciones.getModel().getSize() > 0)
				{
					 listaCanciones.setSelectedIndex(listaCanciones.getModel().getSize()-1);
				}
				int siguienteIndice = vengoOtroPanel ? listaCanciones.getModel().getSize() - 1 
						: listaCanciones.getSelectedIndex() - 1;
		        int totalCanciones = listaCanciones.getModel().getSize();
		        if (totalCanciones > 0) {
		        	if(modoAleatorio)
					{
						siguienteIndice = vengoOtroPanel ? siguienteIndice : 
							Aleatorio.generarAletorio(0, totalCanciones, siguienteIndice+1);
					}
					else
					{
						if(siguienteIndice < 0)
			        	{
							siguienteIndice = listaCanciones.getModel().getSize()-1;
			        	}
					}
		            listaCanciones.setSelectedIndex(siguienteIndice);
		            c = (Cancion) miModelo.getElementAt(siguienteIndice);
		            opcionReproduccion = PLAY;
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
					opcionReproduccion = PAUSE;
		        }
				
			}
		});
		
		
		btnForwa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				if(c != null)
				{
					opcionReproduccion = STOP;
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
					opcionReproduccion = PLAY;
				}
				boolean vengoOtroPanel = ControladorAppMusic.getInstancia().comprobarPaneles(menuPremium);
				if(vengoOtroPanel && listaCanciones.getModel().getSize() > 0)
				{
					 listaCanciones.setSelectedIndex(0);
				}
				int indiceActual = listaCanciones.getSelectedIndex();
		        int totalCanciones = listaCanciones.getModel().getSize();
		        int siguienteIndice;
		        if (totalCanciones > 0) {
		        	if(modoAleatorio)
		        		siguienteIndice = vengoOtroPanel ? 0 : Aleatorio.generarAletorio(0, totalCanciones, indiceActual);
		        	else siguienteIndice = vengoOtroPanel ? 0 : (indiceActual + 1) % totalCanciones;
		        	
		            listaCanciones.setSelectedIndex(siguienteIndice);
		            c = (Cancion) miModelo.getElementAt(siguienteIndice);
		            opcionReproduccion = PLAY;
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
					ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
					opcionReproduccion = PAUSE;
		        }
				
			}
		});
		
		
		btnStop.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				umu.tds.negocio.Cancion c = ControladorAppMusic.getInstancia().getCancionReproduciendose();
				opcionReproduccion = STOP;
				if(c!=null)
				{
					ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
				}
				ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
				opcionReproduccion = PLAY;
				
			}
		});
		
		btnRandom.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(modoAleatorio == true)
				{
					modoAleatorio = false;
					btnRandom.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/repetir.png")));
				}
				else
				{
					modoAleatorio = true;
					btnRandom.setIcon(new ImageIcon(MenuHome.class.getResource("/ImagenesMenu/aleatorio.png")));
				}
			}
		});
		
		btnPDF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int seleccion = fileChooser.showOpenDialog(frame);
				if (seleccion == JFileChooser.APPROVE_OPTION) {
		            // Obtener la ubicación seleccionada (directorio)
		            File carpetaSeleccionada = fileChooser.getSelectedFile();

		            ControladorAppMusic.getInstancia().crearPDF(carpetaSeleccionada);
		        } else {
		            System.out.println("Operación cancelada por el usuario.");
		        }
				
			}
		});
	}
	
	public void refrescarMasEscuchadas(LinkedList<Cancion> cancionesRecientes)
	{
		// refrescarRecientes
		miModelo.actualizarLista(new ArrayList<Cancion>(cancionesRecientes));
	}
	

	static class SlideCustomizado extends BasicSliderUI  {
		public SlideCustomizado(JSlider slider) {
	        super(slider);
	    }

	    public void paintTrack(Graphics g) {
	        Rectangle trackBounds = trackRect;
	        g.setColor(Color.RED); // Cambia el color de la barra a rojo
	        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	    }
	}


	@Override
	public void actualizarEstadoReproductor(String pausa) {
		this.opcionReproduccion = pausa;	
		if(pausa == PLAY)
		{
			btnPlay.setIcon(new ImageIcon(MenuPremium.class.getResource("/ImagenesMenu/boton-de-pausa.png")));
		}
		else
		{
			btnPlay.setIcon(new ImageIcon(MenuPremium.class.getResource("/ImagenesMenu/triangulo-negro-flecha-derecha.png")));
		}
	}

	public void cancionAlFinalizar()
	{
		int indiceActual = listaCanciones.getSelectedIndex();
        int totalCanciones = listaCanciones.getModel().getSize();
        if (totalCanciones > 0) {
        	int siguienteIndice;
        	if(modoAleatorio)
        		siguienteIndice = Aleatorio.generarAletorio(0, totalCanciones, indiceActual);
        	else siguienteIndice = (indiceActual + 1) % totalCanciones;
        	
            listaCanciones.setSelectedIndex(siguienteIndice);
            Cancion c = (Cancion) miModelo.getElementAt(siguienteIndice);
            opcionReproduccion = PLAY;
			ControladorAppMusic.getInstancia().reproducirCancion(opcionReproduccion,c);
			ControladorAppMusic.getInstancia().actualizarEstadoReproductor(opcionReproduccion);
			opcionReproduccion = PAUSE;
        }
	}

}
