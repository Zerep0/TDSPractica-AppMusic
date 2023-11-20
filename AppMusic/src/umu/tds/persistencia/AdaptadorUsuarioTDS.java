package umu.tds.persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.negocio.Usuario;
import beans.Entidad;
import beans.Propiedad;



//Usa un pool para evitar problemas doble referencia con ventas
public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;

	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un cliente se le asigna un identificador �nico */
	public boolean registrarUsuario(Usuario usuario) {
		Entidad eCliente = null;

		// Si la entidad esta registrada no la registra de nuevo
		try {
			eCliente = servPersistencia.recuperarEntidad(usuario.getId());
		} catch (NullPointerException e) {}
		if (eCliente != null) return false;
		
		Boolean existeUsuario = AdaptadorUsuarioTDS.getUnicaInstancia()
				.recuperarTodosUsuarios().stream()
										 .map(u -> u.getLogin())
										 .anyMatch(u -> u.equals(usuario.getLogin()));
		if(!existeUsuario)
		{
			// crear entidad Cliente
			eCliente = new Entidad();
			eCliente.setNombre("usuario");
			eCliente.setPropiedades(new ArrayList<Propiedad>(
					Arrays.asList(new Propiedad("login", usuario.getLogin()), new Propiedad("password",usuario.getPassword()),new Propiedad("email",usuario.getEmail()),
							new Propiedad("fechaNacimiento",usuario.getFechaNacimiento().toString()),new Propiedad("premium",String.valueOf(usuario.isPremium())),
							new Propiedad("playlist",""))));

			// registrar entidad cliente
			eCliente = servPersistencia.registrarEntidad(eCliente);
			// asignar identificador unico
			// Se aprovecha el que genera el servicio de persistencia
			usuario.setId(eCliente.getId());
			return true;
		}
		
		return false;
	}

	/*public void borrarCliente(Usuario cliente) {
		// No se comprueban restricciones de integridad con Venta
		Entidad eCliente = servPersistencia.recuperarEntidad(cliente.getCodigo());

		servPersistencia.borrarEntidad(eCliente);
	}*/

	/*public void modificarCliente(Usuario cliente) {

		Entidad eCliente = servPersistencia.recuperarEntidad(cliente.getId());

		for (Propiedad prop : eCliente.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(cliente.getCodigo()));
			} else if (prop.getNombre().equals("dni")) {
				prop.setValor(cliente.getDni());
			} else if (prop.getNombre().equals("nombre")) {
				prop.setValor(cliente.getNombre());
			} else if (prop.getNombre().equals("ventas")) {
				String ventas = obtenerCodigosVentas(cliente.getVentas());
				prop.setValor(ventas);
			}
			servPersistencia.modificarPropiedad(prop);
		}

	}*/

	public Usuario recuperarUsuario(int codigo) {

		

		// si no, la recupera de la base de datos
		Entidad eUsuario;
		String nombre, email, fechaNacimiento, password, premium, playlist;
		

		// recuperar entidad
		eUsuario = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "login");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		fechaNacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento");
		password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		premium = servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium");
		
		// recuperar playlist
		
		Usuario usuario = new Usuario(nombre,password,email, LocalDate.parse(fechaNacimiento));
		usuario.setId(codigo);
		usuario.setPremium(Boolean.parseBoolean(premium));

		return usuario;
	}

	public List<Usuario> recuperarTodosUsuarios() {

		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eUser : eUsuarios) {
			usuarios.add(recuperarUsuario(eUser.getId()));
		}
		return usuarios;
	}

}