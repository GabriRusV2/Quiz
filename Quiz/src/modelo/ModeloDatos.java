package modelo;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors; // Necesario para el ranking

public class ModeloDatos {
	private int puntaje = 0;
	private int preguntaActual = 0;

	// --- Gestión de Perfil y Puntajes ---
	private String usuarioActivo = null;
	// Estructura para almacenar puntajes: Usuario -> Tipo de Juego -> Lista de
	// Puntajes
	private final Map<String, Map<String, List<Integer>>> puntajesUsuarios;

	// Constantes para identificar los juegos (usadas al guardar el puntaje)
	public static final String JUEGO_CULTURA = "Cultura General";
	public static final String JUEGO_VF = "Verdadero o Falso";
	public static final String JUEGO_ADIVINA = "Adivina la Palabra";
	public static final String JUEGO_AHORCADO = "El Ahorcado";

	// --- Gestión de Usuarios ---
	private final Map<String, String> usuarios;

	// --- Datos para Cultura General ---
	public final String[][] preguntasCultura = {
			{ "¿Cuál es el río más largo del mundo?", "Nilo", "Amazonas", "Misisipi", "Yangtsé", "Amazonas" },
			{ "¿Quién pintó la Mona Lisa?", "Van Gogh", "Picasso", "Da Vinci", "Rembrandt", "Da Vinci" },
			{ "¿Cuántos lados tiene un heptágono?", "5", "6", "7", "8", "7" }, };

	// --- Datos para Verdadero o Falso ---
	public final SimpleEntry<String, Boolean>[] afirmacionesVF = new SimpleEntry[] {
			new SimpleEntry<>("El sol es una estrella.", true),
			new SimpleEntry<>("París es la capital de Italia.", false),
			new SimpleEntry<>("El agua hierve a 100°C.", true), };

	// --- Datos para Adivina la Palabra ---
	public final SimpleEntry<String, String>[] palabrasAdivina = new SimpleEntry[] {
			new SimpleEntry<>("Planeta conocido como 'Planeta Rojo'.", "marte"),
			new SimpleEntry<>("Metal precioso con el símbolo 'Au'.", "oro"),
			new SimpleEntry<>("Ave de Australia que no puede volar.", "casuario"), };

	// --- Datos para El Ahorcado (NUEVO ARRAY) ---
	public final SimpleEntry<String, String>[] palabrasAhorcado = new SimpleEntry[] {
			new SimpleEntry<>("Mamífero marino más inteligente.", "Delfin"),
			new SimpleEntry<>("Herramienta que usan los carpinteros para cortar madera.", "Sierra"),
			new SimpleEntry<>("País de Sudamérica famoso por su tango.", "Argentina"), };

	public ModeloDatos() {
		usuarios = new HashMap<>();
		usuarios.put("admin", "1234");
		usuarios.put("invitado", "pass");
		usuarios.put("usuario1", "java");

		// Inicializar el mapa de puntajes
		this.puntajesUsuarios = new HashMap<>();
		inicializarPuntajesDemo(); // Añade algunos puntajes de ejemplo

		reiniciarJuego();
	}

	/** Inicializa algunos puntajes de demostración */
	private void inicializarPuntajesDemo() {
		// Asegurar que todos los usuarios tengan una entrada en el mapa de puntajes
		usuarios.keySet().forEach(user -> puntajesUsuarios.put(user, new HashMap<>()));

		// Añadir datos de ejemplo para 'admin'
		if (puntajesUsuarios.containsKey("admin")) {
			Map<String, List<Integer>> puntajesAdmin = puntajesUsuarios.get("admin");
			puntajesAdmin.put(JUEGO_CULTURA, new ArrayList<>(Arrays.asList(3, 2)));
			puntajesAdmin.put(JUEGO_VF, new ArrayList<>(Arrays.asList(3, 1)));
			puntajesAdmin.put(JUEGO_ADIVINA, new ArrayList<>(Arrays.asList(2, 3)));
			// Añadimos puntajes demo para El Ahorcado
			puntajesAdmin.put(JUEGO_AHORCADO, new ArrayList<>(Arrays.asList(2, 1)));
		}
	}

	// --- Getters y Setters de Juego ---
	public int getPuntaje() {
		return puntaje;
	}

	public int getPreguntaActual() {
		return preguntaActual;
	}

	// --- Métodos de Control de Juego ---
	public void reiniciarJuego() {
		this.puntaje = 0;
		this.preguntaActual = 0;
	}

	public void avanzarPregunta() {
		this.preguntaActual++;
	}

	public void sumarPunto() {
		this.puntaje++;
	}

	// --- Métodos de Verificación de Juego ---
	public boolean verificarCultura(String respuestaUsuario) {
		String respuestaCorrecta = preguntasCultura[preguntaActual][5];
		return respuestaUsuario.equals(respuestaCorrecta);
	}

	public boolean verificarVF(boolean respuestaUsuario) {
		return respuestaUsuario == afirmacionesVF[preguntaActual].getValue();
	}

	public boolean verificarAdivina(String respuestaUsuario) {
		String respuestaCorrecta = palabrasAdivina[preguntaActual].getValue();
		return respuestaUsuario.trim().toLowerCase().equals(respuestaCorrecta);
	}

	// --- Métodos de Lógica de Autenticación ---
	public boolean verificarCredenciales(String username, String password) {
		String storedPassword = usuarios.get(username);
		return storedPassword != null && storedPassword.equals(password);
	}

	public boolean registrarNuevoUsuario(String username, String password) {
		if (usuarios.containsKey(username)) {
			return false;
		}
		usuarios.put(username, password);
		puntajesUsuarios.put(username, new HashMap<>());
		return true;
	}

	// --- MÉTODOS DE PERFIL Y PUNTAJE ---

	public void setUsuarioActivo(String username) {
		this.usuarioActivo = username;
	}

	public String getUsuarioActivo() {
		return usuarioActivo;
	}

	/**
	 * Guarda el puntaje final del juego para el usuario activo.
	 */
	public void guardarPuntajeFinal(String tipoJuego, int puntajeFinal) {
		if (usuarioActivo == null)
			return;

		Map<String, List<Integer>> puntajesJuegos = puntajesUsuarios.get(usuarioActivo);

		List<Integer> listaPuntajes = puntajesJuegos.computeIfAbsent(tipoJuego, k -> new ArrayList<>());

		listaPuntajes.add(0, puntajeFinal);

		if (listaPuntajes.size() > 5) {
			listaPuntajes.remove(5);
		}
	}

	/**
	 * Devuelve el historial de puntajes del usuario activo.
	 */
	public Map<String, List<Integer>> getHistorialPuntajes() {
		if (usuarioActivo == null)
			return Collections.emptyMap();
		return puntajesUsuarios.getOrDefault(usuarioActivo, Collections.emptyMap());
	}

	// --- MÉTODO PARA RANKING ---

	/**
	 * Calcula y devuelve el puntaje más alto de CADA usuario para un tipo de juego
	 * específico.
	 */
	public Map<String, Integer> getRankingPorJuego(String tipoJuego) {
		Map<String, Integer> ranking = new HashMap<>();

		for (Map.Entry<String, Map<String, List<Integer>>> entryUsuario : puntajesUsuarios.entrySet()) {
			String username = entryUsuario.getKey();
			Map<String, List<Integer>> historialJuegos = entryUsuario.getValue();

			List<Integer> puntajes = historialJuegos.get(tipoJuego);

			if (puntajes != null && !puntajes.isEmpty()) {
				int maxPuntaje = puntajes.stream().mapToInt(v -> v).max().orElse(0);

				if (maxPuntaje > 0) {
					ranking.put(username, maxPuntaje);
				}
			}
		}
		return ranking;
	}
}