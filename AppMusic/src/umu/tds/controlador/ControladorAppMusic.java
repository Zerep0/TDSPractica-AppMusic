package umu.tds.controlador;



import umu.tds.observer.*;
import CargadorCanciones.Cancion;
import CargadorCanciones.Canciones;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import umu.tds.negocio.CargadorCanciones;
import umu.tds.negocio.CatalogoCanciones;
import umu.tds.negocio.CatalogoUsuarios;
import umu.tds.negocio.Usuario;

import umu.tds.persistencia.IAdaptadorUsuarioDAO;
import umu.tds.vista.Alerta;
import umu.tds.vista.Inicio;
import umu.tds.vista.Registro;

public class ControladorAppMusic implements ICancionesListener{
	private static final String MENSAJE_USUARIO_REPETIDO = "El nombre de usuario ya esta cogido";
	private static final String MENSAJE_USUARIO_REGISTRADO = "Se ha registrado con exito";
	private static final String MENSAJE_USUARIO_LOGIN = "Se ha iniciado sesion correctamente";
	private static final String MENSAJE_LOGIN_FALLO = "Usuario o contraseña inválidos";
	
	private static final String ASUNTO_ERROR = "Usuario repetido";
	private static final String ASUNTO_ERROR_CAMPO = "Campo Faltante";
	private static final String ASUNTO_REGISTRO = "Registrado";

	private static final String ASUNTO_ERROR_LOGIN = "Login error";
	private static final String ASUNTO_LOGIN = "Login exitoso";
	
	private String mensaje_error;  //Variable global para errores de falta de campos rellenados
	
	private static ControladorAppMusic unicaInstancia;
		
	private CatalogoUsuarios catalogoUsuarios;
	
	private CatalogoCanciones catalogoCanciones;
	
	private ArrayList<IUsuarioListener> listeners = new ArrayList<IUsuarioListener>();
	
	private ControladorAppMusic()
	{
		
		inicializarCatalogos();
		
		CargadorCanciones.INSTANCE.addListener(this);
	}
	
	public static ControladorAppMusic getInstancia()
	{
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppMusic();
		return unicaInstancia;
	}

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoCanciones = CatalogoCanciones.getUnicaInstancia();
	}
	
	public Boolean registrarUsuario(JTextField login, JPasswordField password, JTextField email, LocalDate fechaNacimiento, Registro ventana) //JTextField pasar en vez de string
	{
		mensaje_error = "No estan los siguientes campos rellenados: ";
		boolean error = errorCreacion(login,password,email,fechaNacimiento);
		if(error)
		{
			Alerta.INSTANCIA.mostrarAlerta(mensaje_error, ASUNTO_ERROR_CAMPO, ventana);
			return false;
		}
		
		Usuario user = new Usuario(login.getText(),new String(password.getPassword()),email.getText(),fechaNacimiento);
		
		if(!catalogoUsuarios.addUsuario(user))
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_REPETIDO, ASUNTO_ERROR, ventana);
			return false;
		}else
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_REGISTRADO, ASUNTO_REGISTRO, ventana);
			UsuarioEvent e = new UsuarioEvent(user.getLogin());
			notificarCambioNombre(e);
			return true;
		}
	}
	
	public Boolean loginUsuario(JTextField login, JPasswordField password,Inicio ventana)
	{
		// precondiciones
		if(login.getFont().isItalic() || password.getFont().isItalic())
		{
			// alertas
			return false;
		}
		List<Usuario> lista = CatalogoUsuarios.getUnicaInstancia().getUsuarios();
		// encuentra si existe un usuario dada una lista de usuarios con el mismo usuario y contraseña
		
		Usuario usuario = lista.stream().filter(us -> us.getLogin().equals(login.getText()) && us.getPassword().equals(new String(password.getPassword())))
												  .findFirst().orElse(null);
		if(usuario != null)
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_USUARIO_LOGIN,ASUNTO_LOGIN , ventana);
			UsuarioEvent e = new UsuarioEvent(usuario.getLogin());
			notificarCambioNombre(e);
			return true;
		}else 
		{
			Alerta.INSTANCIA.mostrarAlerta(MENSAJE_LOGIN_FALLO,ASUNTO_ERROR_LOGIN , ventana);
			return false;
		}
			
	}
	
	public void registrarGit(String user, String password)
	{
		GitHub github = null;
		try {
			github = new GitHubBuilder().withPassword(user,password).build();
			new GitHubBuilder();
			System.out.println("¿Login válido?:" + github.isCredentialValid());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

	}
	
	public void cargarCanciones(String rutaArchivo)
	{
		umu.tds.negocio.CargadorCanciones.INSTANCE.setArchivoCanciones(rutaArchivo);
	}
	

	
	
	
	
	//--------------------------------------METODOS AUXILIARES---------------------------------------------
	private boolean isErrorTexto(JTextField text,String campo)
	{
		if(text.getText().equals(campo) && text.getFont().isItalic())
		{
			return true;
		}
		return false;
	}
	
	private boolean isErrorContraseña(JPasswordField password,String campo)
	{
		if(new String(password.getPassword()).equals(campo) && password.getFont().isItalic())
		{
			return true;
		}
		return false;
	}
	
	private boolean errorCreacion(JTextField login, JPasswordField password, JTextField email, LocalDate fechaNacimiento)
	{
		Boolean error = false;
		if(isErrorTexto(login,"User"))
		{
			error = true;
			mensaje_error += "Login ";
		}
		if(isErrorContraseña(password, "Password"))
		{
			error = true;
			mensaje_error += "Password ";
		}
		if(isErrorTexto(email,"Email"))
		{
			error = true;
			mensaje_error += "Email ";
		}
		if(fechaNacimiento == null)
		{
			error = true;
			mensaje_error += "FechaNacimiento";
		}
		return error;
	}
	
	public synchronized void addUsuarioListener(IUsuarioListener listener){
		listeners.add(listener);
	}
	
	@SuppressWarnings("unchecked")
	void notificarCambioNombre(UsuarioEvent e)
	{
		ArrayList<IUsuarioListener> lista;
		synchronized (this) {
			lista = (ArrayList<IUsuarioListener>) listeners.clone();
		}
		for(IUsuarioListener users : lista)
		{
			users.actualizar(e);
		}
	}

	
	@Override
	public void actualizarCanciones(CancionEvent e) {
		// TODO: convertir de Cancion a Cancion jaja
		List<umu.tds.negocio.Cancion> lista = new LinkedList<umu.tds.negocio.Cancion>();
		for (Cancion c : e.getCanciones().getCancion()) {
			umu.tds.negocio.Cancion can = new umu.tds.negocio.Cancion(c.getTitulo(),c.getURL(),c.getEstilo(),c.getInterprete());
			System.out.println("Cancion: " + c.getTitulo() + " " + c.getURL() + " " + c.getEstilo() + " " + c.getInterprete()+ " ");
			lista.add(can);
		}
		
		// TODO: sincronizar list
		catalogoCanciones.registrarCanciones(lista);
		
	}
	
	
}
