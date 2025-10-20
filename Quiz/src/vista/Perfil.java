package vista;

import controlador.Controlador;
import modelo.ModeloDatos;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Perfil extends JFrame implements Vista {
	private Controlador controlador;
	private ModeloDatos modelo;
	// private ModeloDatos ModeloDatos; // Variable redundante eliminada
	private ModeloDatos miModelo; // Uso consistente para la interfaz

	// Colores y Constantes
	private static final Color COLOR_FONDO = new Color(240, 240, 240); // Gris claro, suave
	private static final Color COLOR_PRIMARIO = new Color(59, 89, 182); // Azul corporativo
	private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
	private static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 16);

	public Perfil(Controlador controlador, ModeloDatos modelo) {
		this.controlador = controlador;
		this.modelo = modelo;

		setTitle("Perfil de Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 550); // Aumento el tamaño para el historial
		setLocationRelativeTo(null);

		getContentPane().setBackground(COLOR_FONDO);
		getContentPane().setLayout(new GridBagLayout()); // Usamos GridBagLayout principal para centrar

		// --- Panel Contenedor Central (Tarjeta) ---
		JPanel panelContenedor = new JPanel(new BorderLayout(15, 15));
		panelContenedor.setBackground(Color.WHITE);
		panelContenedor.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
				new EmptyBorder(25, 25, 25, 25) // Relleno interno
		));

		// 1. Encabezado
		String usuario = modelo.getUsuarioActivo();
		JLabel lblTitulo = new JLabel("Perfil de: " + usuario, SwingConstants.CENTER);
		lblTitulo.setFont(FUENTE_TITULO);
		lblTitulo.setForeground(COLOR_PRIMARIO);
		panelContenedor.add(lblTitulo, BorderLayout.NORTH);

		// 2. Historial de Puntajes
		JTextArea txtAreaHistorial = new JTextArea();
		txtAreaHistorial.setEditable(false);
		txtAreaHistorial.setFont(new Font("Monospaced", Font.PLAIN, 14));
		txtAreaHistorial.setForeground(Color.DARK_GRAY);
		
		cargarHistorial(txtAreaHistorial); // Aquí se carga la información

		JScrollPane scrollPane = new JScrollPane(txtAreaHistorial);
		scrollPane.setPreferredSize(new Dimension(400, 300));
		scrollPane.setBorder(BorderFactory.createTitledBorder(
			BorderFactory.createLineBorder(Color.LIGHT_GRAY),
			"Últimos Puntajes por Juego", 
			0, 
			0, 
			FUENTE_SUBTITULO, 
			Color.BLACK)
		);
		panelContenedor.add(scrollPane, BorderLayout.CENTER);

		// 3. Botón Volver (parte inferior de la tarjeta)
		JButton btnVolver = crearBotonTransparente("Volver al Menú de Juegos");
		btnVolver.addActionListener(e -> controlador.mostrarMenuSeleccion());

		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelSur.setOpaque(false); // Necesario para que el fondo de la tarjeta se vea
		panelSur.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		panelSur.add(btnVolver);

		panelContenedor.add(panelSur, BorderLayout.SOUTH);

		// Añadir la tarjeta al centro de la ventana
		getContentPane().add(panelContenedor, new GridBagConstraints());
	}

	/**
	 * Carga el historial de puntajes desde el Modelo y lo formatea en el JTextArea.
	 */
	private void cargarHistorial(JTextArea area) {
		Map<String, List<Integer>> historial = modelo.getHistorialPuntajes();
		StringBuilder sb = new StringBuilder();

		if (historial.isEmpty() || historial.values().stream().allMatch(List::isEmpty)) {
			sb.append("\n\n\tNo hay puntajes registrados aún.");
		} else {
			// Usamos un formato más limpio y consistente
			historial.forEach((tipoJuego, puntajes) -> {
				if (!puntajes.isEmpty()) {
					// Usamos un separador destacado para cada juego
					sb.append("\n=========================================\n");
					sb.append(String.format(" %s\n", tipoJuego.toUpperCase()));
					sb.append("=========================================\n");

					// Listamos los puntajes, limitados a los 5 más recientes
					// (El Modelo ya limita a 5, pero lo iteramos)
					for (int i = 0; i < puntajes.size(); i++) {
						sb.append(String.format("  Última partida #%d: %d puntos\n", (i + 1), puntajes.get(i)));
					}
					sb.append("\n"); // Espacio extra
				}
			});
		}
		area.setText(sb.toString());
		area.setCaretPosition(0);
	}
    
    /**
     * Método auxiliar para crear botones secundarios (Volver/Cancelar) con estilo sutil.
     */
    private JButton crearBotonTransparente(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        boton.setContentAreaFilled(false); // Fondo transparente
        boton.setBorderPainted(false);     // Sin borde
        boton.setForeground(Color.GRAY.darker()); 
        
        // Simulación de "Hover"
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setForeground(COLOR_PRIMARIO); // Cambia al color primario
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setForeground(Color.GRAY.darker());
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