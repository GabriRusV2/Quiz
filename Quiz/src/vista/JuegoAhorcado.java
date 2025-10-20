package vista;

import controlador.Controlador;
import modelo.ModeloDatos;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class JuegoAhorcado extends JFrame {
	private Controlador controlador;
	private ModeloDatos modelo;

	private JLabel lblPista;
	private JLabel lblPalabra;
	private JLabel lblPuntaje;
	private JLabel lblAhorcadoDibujo; // Nuevo para el dibujo
	private JTextField txtLetra;
	private JButton btnAdivinar;

	// Colores y Constantes
	private static final Color COLOR_FONDO = new Color(240, 240, 240);
	private static final Color COLOR_PRIMARIO = new Color(59, 89, 182);
	private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
	private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 20);
	private static final Font FUENTE_PALABRA = new Font("Monospaced", Font.BOLD, 40); // Más grande
	private static final Font FUENTE_PUNTAJE = new Font("Segoe UI", Font.BOLD, 18);
	private static final Font FUENTE_PISTA = new Font("Segoe UI", Font.ITALIC, 16);
	private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 16);

	// Lógica interna simple del juego
	private String palabraSecreta;
	private StringBuilder palabraActual;
	private int fallosRestantes = 6;
	private int puntosObtenidos = 0;
	private int preguntaActual = 0;
	private final int totalPreguntas;

	// Representación simple del ahorcado
	private static final String[] DIBUJOS_AHORCADO = { "  +---+\n  |   |\n      |\n      |\n      |\n=========", // 0
																													// fallos
			"  +---+\n  |   |\n  O   |\n      |\n      |\n=========", // 1 fallo (cabeza)
			"  +---+\n  |   |\n  O   |\n  |   |\n      |\n=========", // 2 fallos (cuerpo)
			"  +---+\n  |   |\n  O   |\n /|   |\n      |\n=========", // 3 fallos (brazo izq)
			"  +---+\n  |   |\n  O   |\n /|\\  |\n      |\n=========", // 4 fallos (brazo der)
			"  +---+\n  |   |\n  O   |\n /|\\  |\n /    |\n=========", // 5 fallos (pierna izq)
			"  +---+\n  |   |\n  O   |\n /|\\  |\n / \\  |\n=========" // 6 fallos (pierna der)
	};

	public JuegoAhorcado(Controlador controlador, ModeloDatos modelo) {
		this.controlador = controlador;
		this.modelo = modelo;
		this.totalPreguntas = modelo.palabrasAhorcado.length;

		setTitle("Juego: El Ahorcado");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 700, 500); // Aumento el tamaño
		setLocationRelativeTo(null);
		getContentPane().setBackground(COLOR_FONDO);
		getContentPane().setLayout(new BorderLayout(20, 20));

		// -------------------------------------------------------------
		// --- Panel Izquierdo: El Dibujo del Ahorcado (Visual) ---
		// -------------------------------------------------------------
		lblAhorcadoDibujo = new JLabel();
		lblAhorcadoDibujo.setFont(new Font("Monospaced", Font.PLAIN, 18));
		lblAhorcadoDibujo.setVerticalAlignment(SwingConstants.TOP);
		lblAhorcadoDibujo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY),
				"Progreso", 0, 0, new Font("Segoe UI", Font.BOLD, 14), Color.DARK_GRAY));

		JPanel panelIzquierdo = new JPanel(new BorderLayout());
		panelIzquierdo.setPreferredSize(new Dimension(200, 0));
		panelIzquierdo.setBackground(Color.WHITE);
		panelIzquierdo.add(lblAhorcadoDibujo, BorderLayout.CENTER);

		getContentPane().add(panelIzquierdo, BorderLayout.WEST);

		// -------------------------------------------------------------
		// --- Panel Derecho/Central: Juego y Controles (Tarjeta) ---
		// -------------------------------------------------------------
		JPanel panelCentral = new JPanel(new GridLayout(4, 1, 10, 10));
		panelCentral.setBackground(Color.WHITE);
		panelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));

		// Fila 1: Puntaje
		lblPuntaje = new JLabel("Puntaje: 0 | Fallos restantes: 6", SwingConstants.CENTER);
		lblPuntaje.setFont(FUENTE_PUNTAJE);
		lblPuntaje.setForeground(COLOR_PRIMARIO.darker());
		panelCentral.add(lblPuntaje);

		// Fila 2: Pista
		lblPista = new JLabel("Pista:", SwingConstants.CENTER);
		lblPista.setFont(FUENTE_PISTA);
		panelCentral.add(lblPista);

		// Fila 3: Palabra
		lblPalabra = new JLabel("___ ___ ___", SwingConstants.CENTER);
		lblPalabra.setFont(FUENTE_PALABRA);
		panelCentral.add(lblPalabra);

		// Fila 4: Input
		JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelInput.setOpaque(false);
		txtLetra = new JTextField(5);
		txtLetra.setFont(new Font("Monospaced", Font.BOLD, 18));
		btnAdivinar = crearBotonEstilizado("Intentar");

		btnAdivinar.addActionListener(e -> procesarIntento());
		txtLetra.addActionListener(e -> procesarIntento());

		panelInput.add(new JLabel("Letra/Palabra:"));
		panelInput.add(txtLetra);
		panelInput.add(btnAdivinar);
		panelCentral.add(panelInput);

		getContentPane().add(panelCentral, BorderLayout.CENTER);

		iniciarNuevaPregunta();
	}

	// ----------------------------------------------------------------------
	// --- Lógica de Juego (Ajustada) ---
	// ----------------------------------------------------------------------

	private void iniciarNuevaPregunta() {
		if (preguntaActual < totalPreguntas) {
			String pista = modelo.palabrasAhorcado[preguntaActual].getKey();
			palabraSecreta = modelo.palabrasAhorcado[preguntaActual].getValue().toUpperCase();

			// Inicializar la palabra con guiones bajos
			palabraActual = new StringBuilder();
			for (int i = 0; i < palabraSecreta.length(); i++) {
				palabraActual.append(palabraSecreta.charAt(i) == ' ' ? ' ' : '_');
			}

			fallosRestantes = 6; // Se reinician los fallos
			actualizarVista();

			lblPista.setText("Pista: " + pista);
			txtLetra.setText("");
			txtLetra.requestFocus();
			preguntaActual++;
		} else {
			// FIN DEL JUEGO
			controlador.finalizarJuegoAhorcado(puntosObtenidos, totalPreguntas);
			this.dispose();
		}
	}

	private void procesarIntento() {
		String intento = txtLetra.getText().trim().toUpperCase();
		txtLetra.setText("");

		if (intento.isEmpty())
			return;

		boolean acierto = false;

		if (intento.length() == 1) { // Intento de letra
			char letra = intento.charAt(0);
			if (!Character.isLetter(letra))
				return;

			boolean letraEncontrada = false;
			for (int i = 0; i < palabraSecreta.length(); i++) {
				if (palabraSecreta.charAt(i) == letra && palabraActual.charAt(i) == '_') {
					palabraActual.setCharAt(i, letra);
					letraEncontrada = true;
					acierto = true; // Se encontró al menos una
				}
			}

			if (!letraEncontrada) {
				fallosRestantes--;
			}

		} else if (intento.length() > 1) { // Intento de palabra completa
			if (intento.equals(palabraSecreta)) {
				acierto = true;
				palabraActual = new StringBuilder(palabraSecreta);
			} else {
				fallosRestantes = 0; // Pérdida inmediata de fallos
			}
		}

		actualizarVista();

		// Verificar el estado del juego
		if (palabraActual.indexOf("_") == -1) {
			puntosObtenidos++;
			JOptionPane.showMessageDialog(this, "¡ACERTASTE! La palabra era: " + palabraSecreta, "Acierto",
					JOptionPane.INFORMATION_MESSAGE);
			iniciarNuevaPregunta();
		} else if (fallosRestantes <= 0) {
			JOptionPane.showMessageDialog(this, "AHORCADO. La palabra era: " + palabraSecreta, "Fallaste",
					JOptionPane.ERROR_MESSAGE);
			iniciarNuevaPregunta();
		}
	}

	public void actualizarVista() {
		lblPalabra.setText(palabraActual.toString().replaceAll("", " ").trim()); // Mostrar con espacios
		lblPuntaje.setText(String.format("Puntaje: %d | Fallos restantes: %d / 6", puntosObtenidos, fallosRestantes));

		// Actualizar el dibujo (Se resta el fallo actual a 6 para usar el índice
		// correcto del array DIBUJOS_AHORCADO)
		dibujarAhorcado(fallosRestantes);
	}

	// ----------------------------------------------------------------------
	// --- Lógica Visual Auxiliar ---
	// ----------------------------------------------------------------------

	/**
	 * Dibuja la representación ASCII del ahorcado basada en los fallos restantes.
	 */
	private void dibujarAhorcado(int fallosRestantes) {
		// Se calcula el índice de fallos ocurridos
		int fallosOcurridos = 6 - fallosRestantes;

		// Aseguramos que el índice no exceda los límites del array
		if (fallosOcurridos >= 0 && fallosOcurridos < DIBUJOS_AHORCADO.length) {
			lblAhorcadoDibujo.setText("<html><pre>" + DIBUJOS_AHORCADO[fallosOcurridos] + "</pre></html>");
		}
	}

	/**
	 * Método auxiliar para crear botones con un estilo unificado (principal).
	 */
	private JButton crearBotonEstilizado(String texto) {
		JButton boton = new JButton(texto);
		boton.setFont(FUENTE_BOTON);
		boton.setBackground(COLOR_PRIMARIO);
		boton.setForeground(COLOR_TEXTO_BOTON);
		boton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
		boton.setFocusPainted(false);

		// Simulación de "Hover"
		boton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				boton.setBackground(COLOR_PRIMARIO.darker());
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				boton.setBackground(COLOR_PRIMARIO);
			}
		});
		return boton;
	}
}