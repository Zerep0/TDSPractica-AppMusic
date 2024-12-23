package umu.tds.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Usuario {
	private int id;
	private String email, login, password;
	private LocalDate fechaNacimiento;
	private boolean premium;
	private LinkedList<Cancion> cancionesRecientes;
	private LinkedList<Cancion> cancionesFavoritas;
	private HashMap<String, Playlist> cancionesPlaylist;
	private static final int MAX_RECIENTES = 10;
	public double saldo;

	public Usuario(String login, String password, String email, LocalDate fechaNacimiento) {
		this.email = email;
		this.id = 0;
		this.login = login;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.premium = false;
		cancionesRecientes = new LinkedList<Cancion>();
		cancionesFavoritas = new LinkedList<Cancion>();
		cancionesPlaylist = new HashMap<String,Playlist>();
		cancionesPlaylist.put("Favoritas", new Playlist("Favoritas"));
		saldo = 50.0;
	}

	/**
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}


	public String getPassword() {
		return password;
	}

	public String getEmail()
	{
		return email;
	}
	
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	
	public boolean isPremium()
	{
		return premium;
	}
	
	public void setPremium(boolean premium)
	{
		this.premium = premium;
	}
	
	public LinkedList<Cancion> getRecientes()
	{
		return cancionesRecientes;
	}
	
	public LinkedList<Integer> getRecientesNum()
	{
		LinkedList<Integer> nums = new LinkedList<Integer>();
		for (umu.tds.negocio.Cancion can : cancionesRecientes) {
			nums.add(can.getId());
		}
		return nums;
	}
	
	public void addReciente(Cancion c, boolean esCargaInicial)
	{
		if(esCargaInicial)
			cancionesRecientes.add(c);
		else
		{
			if(cancionesRecientes.contains(c))
			{
				cancionesRecientes.remove(c);
				cancionesRecientes.addFirst(c);
			}else
			{
				if(cancionesRecientes.size() == MAX_RECIENTES)
				{
					cancionesRecientes.removeLast();
					cancionesRecientes.addFirst(c);
				}
				else cancionesRecientes.addFirst(c);
			}
		}
		
	}
	
	public LinkedList<Cancion> getFavoritas()
	{
		return cancionesFavoritas;
	}
	
	public LinkedList<Integer> getFavoritasNum()
	{
		LinkedList<Integer> nums = new LinkedList<Integer>();
		for (umu.tds.negocio.Cancion can : cancionesFavoritas) {
			nums.add(can.getId());
		}
		return nums;
	}
	
	public void addFavorita(Cancion c)
	{
		if(!cancionesFavoritas.contains(c))
		{
			cancionesFavoritas.add(c);
			cancionesPlaylist.get("Favoritas").addCancion(c);
		}
			
			
	}
	
	public void removeFavorita(Cancion c)
	{
		cancionesFavoritas.remove(c);
		cancionesPlaylist.get("Favoritas").borrarCancion(c);
	}
	
	public boolean isFavorita(Cancion c)
	{
		return cancionesFavoritas.contains(c);
	}
	
	public LinkedList<Cancion> getCancionesPlaylist(String playlist)
	{
		if(cancionesPlaylist.containsKey(playlist))
		{
			return cancionesPlaylist.get(playlist).getCanciones();
		}
		else return null;
	}
	
	public void setPlaylist(String playlist,Playlist list)
	{
		cancionesPlaylist.put(playlist,list);
		System.out.println("Se ha creado la playlist " + playlist);
	}
	
	public void erasePlaylist(String playlist)
	{
		cancionesPlaylist.remove(playlist, cancionesPlaylist.get(playlist));
		System.out.println("Se ha borrado la playlist " + playlist);

	}
	
	public boolean isPlaylist(String playlist)
	{
		
		if(playlist.contains(playlist))
		{
			return true;
		}
		return false;
	}
	
	public void setNuevasCanciones(String playlist, Cancion c)
	{
		Playlist aux = cancionesPlaylist.get(playlist);
		aux.addCancion(c);
		cancionesPlaylist.put(playlist, aux);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email, fechaNacimiento, id, login, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email) && Objects.equals(fechaNacimiento, other.fechaNacimiento)
			 && Objects.equals(login, other.login) && Objects.equals(password, other.password);
	}

	public ArrayList<String> getNombresPlaylists()
	{
		return new ArrayList<String>(cancionesPlaylist.keySet());
	}
	
	public double getSaldo()
	{
		return saldo;
	}
	
	public void setSaldo(double saldo)
	{
		this.saldo = saldo;
	}
}
