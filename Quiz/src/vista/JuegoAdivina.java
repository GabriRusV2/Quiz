package vista;

import controlador.Controlador;
import modelo.ModeloDatos;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JuegoAdivina extends JFrame implements Vista {
	private Controlador controlador;
	private ModeloDatos modelo;
	private JLabel lblPista;
	private JTextField txtRespuesta;
	private ModeloDatos miModelo;

	// Colores y Constantes
	private static final Color COLOR_FONDO = new Color(240, 240, 240);
	private static final Color COLOR_PRIMARIO = new Color(59, 89, 182);
	private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
	private static final Font FUENTE_PISTA = new Font("Segoe UI", Font.BOLD, 20);
	private static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.PLAIN, 16);
	private static final Font FUENTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 18);
	private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 16);

	public JuegoAdivina(Controlador controlador, ModeloDatos modelo) {
		this.controlador = controlador;
		this.modelo = modelo;

		setTitle("Juego: Adivina la Palabra");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 350);
		setLocationRelativeTo(null);

		getContentPane().setBackground(COLOR_FONDO);
		getContentPane().setLayout(new GridBagLayout());
		// --- Panel Contenedor Central (Tarjeta) ---
		JPanel panelContenedor = new JPanel(new GridBagLayout());
		panelContenedor.setBackground(Color.WHITE);
		panelContenedor.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true), new EmptyBorder(30, 30, 30, 30) // Relleno
																												// interno
		));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridwidth = 2; // Ocupa todo el ancho inicialmente

		// 1. Etiqueta de la Pista
		lblPista = new JLabel("", SwingConstants.CENTER);
		lblPista.setFont(FUENTE_PISTA);
		lblPista.setForeground(COLOR_PRIMARIO.darker());

		// Panel para la Pista con borde
		JPanel panelPista = new JPanel();
		panelPista.setBackground(new Color(230, 235, 255));
		panelPista.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(COLOR_PRIMARIO, 1),
				BorderFactory.createEmptyBorder(15, 15, 15, 15)));
		panelPista.add(lblPista);

		gbc.gridy = 0;
		panelContenedor.add(panelPista, gbc);

		// 2. Etiqueta "Tu Respuesta"
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(25, 10, 10, 10);
		JLabel lblRespuesta = new JLabel("Introduce la palabra:", SwingConstants.RIGHT);
		lblRespuesta.setFont(FUENTE_ETIQUETA);
		panelContenedor.add(lblRespuesta, gbc);

		// 3. Campo de Respuesta
		gbc.gridx = 1;
		txtRespuesta = new JTextField(15);
		txtRespuesta.setFont(FUENTE_CAMPO);
		txtRespuesta.setPreferredSize(new Dimension(200, 35));
		panelContenedor.add(txtRespuesta, gbc);

		// 4. Botón Comprobar
		JButton btnComprobar = crearBotonEstilizado("Comprobar");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2; // Ocupa todo el ancho
		gbc.insets = new Insets(20, 10, 0, 10);

		// Lógica del botón y Enter
		btnComprobar.addActionListener(e -> {
			controlador.procesarRespuestaAdivina(txtRespuesta.getText());
		});
		txtRespuesta.addActionListener(e -> {
			controlador.procesarRespuestaAdivina(txtRespuesta.getText());
		});

		panelContenedor.add(btnComprobar, gbc);

		// Añadir la tarjeta al centro de la ventana
		getContentPane().add(panelContenedor, new GridBagConstraints());

		// Inicializa la vista con la primera pista
		actualizarVista();
	}

	/**
	 * Actualiza la interfaz gráfica leyendo el estado actual del Modelo.
	 */
	public void actualizarVista() {
		int actual = modelo.getPreguntaActual();

		// Aseguramos que no se salga del array
		if (actual < modelo.palabrasAdivina.length) {
			String pista = modelo.palabrasAdivina[actual].getKey();
			lblPista.setText("Palabra " + (actual + 1) + " de " + modelo.palabrasAdivina.length + ": " + pista);
			txtRespuesta.setText("");
			txtRespuesta.requestFocusInWindow();
		}
	}

	// ----------------------------------------------------------------------
	// --- Métodos Auxiliares de Estilo ---
	// ----------------------------------------------------------------------

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

	@Override
	public void setModelo(ModeloDatos miModelo) {
		this.miModelo = miModelo;
	}

	@Override
	public void setControlador(Controlador Controlador) {
		this.controlador = Controlador;
	}
}