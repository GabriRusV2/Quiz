package controlador;
//jkaKJDSJKDJKAKJAJHKSALHDLLKD
//sdfhkffflkekfwnfnlk
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.ModeloDatos;
// Importaciones de Vistas
import vista.Inicio;
import vista.JuegoAdivina;
import vista.JuegoAhorcado; // <<-- ¡NUEVA IMPORTACIÓN AÑADIDA!
import vista.JuegoCultura;
import vista.JuegoVF;
import vista.Login;
import vista.Perfil;
import vista.Ranking;
import vista.Registro;
import vista.SeleccionJuego;

public class Controlador {
	private ModeloDatos modelo;
	private JFrame ventanaActual;

	public Controlador(ModeloDatos modelo) {
		this.modelo = modelo;
	}

	// ----------------------------------------------------------------------
	// --- MÉTODOS DE NAVEGACIÓN ---
	// ----------------------------------------------------------------------

	public void iniciarAplicacion() {
		mostrarVentana(new Inicio(this));
	}

	public void mostrarVentanaLogin() {
		mostrarVentana(new Login(this));
	}

	public void mostrarVentanaRegistro() {
		mostrarVentana(new Registro(this));
	}

	public void mostrarMenuSeleccion() {
		mostrarVentana(new SeleccionJuego(this));
	}

	public void mostrarVentanaPerfil() {
		mostrarVentana(new Perfil(this, modelo));
	}

	public void mostrarVentanaRanking() {
		mostrarVentana(new Ranking(this, modelo));
	}

	// --- Navegación a Juegos ---

	public void iniciarJuegoCultura() {
		modelo.reiniciarJuego();
		mostrarVentana(new JuegoCultura(this, modelo));
	}

	public void iniciarJuegoVF() {
		modelo.reiniciarJuego();
		mostrarVentana(new JuegoVF(this, modelo));
	}

	public void iniciarJuegoAdivina() {
		modelo.reiniciarJuego();
		mostrarVentana(new JuegoAdivina(this, modelo));
	}

	public void iniciarJuegoAhorcado() {
		modelo.reiniciarJuego();
		mostrarVentana(new JuegoAhorcado(this, modelo));
	}

	private void mostrarVentana(JFrame nuevaVentana) {
		if (this.ventanaActual != null) {
			this.ventanaActual.dispose();
		}
		this.ventanaActual = nuevaVentana;
		this.ventanaActual.setVisible(true);
	}

	// ----------------------------------------------------------------------
	// --- MÉTODOS DE LÓGICA DE AUTENTICACIÓN ---
	// ----------------------------------------------------------------------

	public void procesarLogin(String username, String password) {
		if (modelo.verificarCredenciales(username, password)) {
			modelo.setUsuarioActivo(username);

			JOptionPane.showMessageDialog(ventanaActual, "¡Bienvenido, " + username + "!", "Login Exitoso",
					JOptionPane.INFORMATION_MESSAGE);

			mostrarMenuSeleccion();
		} else {
			JOptionPane.showMessageDialog(ventanaActual, "Usuario o contraseña incorrectos.", "Error de Login",
					JOptionPane.ERROR_MESSAGE);

			if (ventanaActual instanceof Login) {
				((Login) ventanaActual).limpiarCampos();
			}
		}
	}

	public void procesarRegistro(String username, String password) {
		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(ventanaActual, "Por favor, completa todos los campos.", "Error de Registro",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		boolean registroExitoso = modelo.registrarNuevoUsuario(username, password);

		if (registroExitoso) {
			JOptionPane.showMessageDialog(ventanaActual, "¡Usuario registrado! Ya puedes iniciar sesión.",
					"Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

			mostrarVentanaLogin();
		} else {
			JOptionPane.showMessageDialog(ventanaActual, "El nombre de usuario '" + username + "' ya existe.",
					"Error de Registro", JOptionPane.ERROR_MESSAGE);

			if (ventanaActual instanceof Registro) {
				((Registro) ventanaActual).limpiarCampos();
			}
		}
	}

	// ----------------------------------------------------------------------
	// --- MÉTODOS DE LÓGICA DEL JUEGO Y GUARDADO ---
	// ----------------------------------------------------------------------

	public void procesarRespuestaCultura(String respuestaUsuario) {
		int totalPreguntas = modelo.preguntasCultura.length;

		boolean esCorrecta = modelo.verificarCultura(respuestaUsuario);
		String respuestaCorrecta = modelo.preguntasCultura[modelo.getPreguntaActual()][5];

		if (esCorrecta) {
			modelo.sumarPunto();
			JOptionPane.showMessageDialog(ventanaActual, "¡Correcto!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(ventanaActual, "Incorrecto. La respuesta era: " + respuestaCorrecta,
					"Resultado", JOptionPane.ERROR_MESSAGE);
		}

		modelo.avanzarPregunta();

		if (modelo.getPreguntaActual() < totalPreguntas) {
			((JuegoCultura) ventanaActual).actualizarVista();
		} else {
			modelo.guardarPuntajeFinal(ModeloDatos.JUEGO_CULTURA, modelo.getPuntaje());

			mostrarResultadoFinal(totalPreguntas);
			mostrarVentanaRanking();
		}
	}

	public void procesarRespuestaVF(boolean respuestaUsuario) {
		int totalPreguntas = modelo.afirmacionesVF.length;

		boolean esCorrecta = modelo.verificarVF(respuestaUsuario);
		String respuestaCorrecta = modelo.afirmacionesVF[modelo.getPreguntaActual()].getValue() ? "Verdadero" : "Falso";

		if (esCorrecta) {
			modelo.sumarPunto();
			JOptionPane.showMessageDialog(ventanaActual, "¡Correcto!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(ventanaActual, "Incorrecto. La respuesta correcta era: " + respuestaCorrecta,
					"Resultado", JOptionPane.ERROR_MESSAGE);
		}

		modelo.avanzarPregunta();

		if (modelo.getPreguntaActual() < totalPreguntas) {
			((JuegoVF) ventanaActual).actualizarVista();
		} else {
			modelo.guardarPuntajeFinal(ModeloDatos.JUEGO_VF, modelo.getPuntaje());

			mostrarResultadoFinal(totalPreguntas);
			mostrarVentanaRanking();
		}
	}

	public void procesarRespuestaAdivina(String respuestaUsuario) {
		int totalPreguntas = modelo.palabrasAdivina.length;

		boolean esCorrecta = modelo.verificarAdivina(respuestaUsuario);
		String respuestaCorrecta = modelo.palabrasAdivina[modelo.getPreguntaActual()].getValue();

		if (esCorrecta) {
			modelo.sumarPunto();
			JOptionPane.showMessageDialog(ventanaActual, "¡Correcto! La palabra era: " + respuestaCorrecta, "Resultado",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(ventanaActual, "Incorrecto. La palabra era: " + respuestaCorrecta,
					"Resultado", JOptionPane.ERROR_MESSAGE);
		}

		modelo.avanzarPregunta();

		if (modelo.getPreguntaActual() < totalPreguntas) {
			((JuegoAdivina) ventanaActual).actualizarVista();
		} else {
			modelo.guardarPuntajeFinal(ModeloDatos.JUEGO_ADIVINA, modelo.getPuntaje());

			mostrarResultadoFinal(totalPreguntas);
			mostrarVentanaRanking();
		}
	}

	/**
	 * Recibe y procesa el puntaje final del juego del Ahorcado. Este método es
	 * llamado directamente desde la vista JuegoAhorcado al finalizar.
	 */
	public void finalizarJuegoAhorcado(int puntajeFinal, int totalPreguntas) {
		// Guardar el puntaje
		modelo.guardarPuntajeFinal(ModeloDatos.JUEGO_AHORCADO, puntajeFinal);

		// Mostrar mensaje de resultado
		JOptionPane.showMessageDialog(null,
				"Tu puntaje final en el Ahorcado es: " + puntajeFinal + " de " + totalPreguntas,
				"Fin del Juego: El Ahorcado", JOptionPane.INFORMATION_MESSAGE);

		// Navegar al Ranking
		mostrarVentanaRanking();
	}

	private void mostrarResultadoFinal(int totalPreguntas) {
		JOptionPane.showMessageDialog(null, "Tu puntaje final es: " + modelo.getPuntaje() + " de " + totalPreguntas,
				"Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
	}
}