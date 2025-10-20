package vista;

import controlador.Controlador;
import modelo.ModeloDatos;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Ranking extends JFrame {
	private Controlador controlador;
	private ModeloDatos modelo;
	private JTextArea txtAreaRanking;
	private JComboBox<String> cmbJuegos;

	// Colores y Constantes (Consistentes con otras vistas)
	private static final Color COLOR_FONDO = new Color(240, 240, 240); // Gris claro, suave
	private static final Color COLOR_PRIMARIO = new Color(59, 89, 182); // Azul corporativo
	private static final Color COLOR_DESTAQUE = new Color(255, 204, 0); // Amarillo/Oro para posiciones
	private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
	private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.PLAIN, 16);
	private static final Font FUENTE_RANKING = new Font("Monospaced", Font.BOLD, 16);


	public Ranking(Controlador controlador, ModeloDatos modelo) {
		this.controlador = controlador;
		this.modelo = modelo;

		setTitle("Ranking de Puntuaciones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 550); // Aumento el tamaño
		setLocationRelativeTo(null);

		getContentPane().setBackground(COLOR_FONDO);
		getContentPane().setLayout(new BorderLayout(20, 20));

		// 1. Título principal
		JLabel lblTitulo = new JLabel("Clasificación Global", SwingConstants.CENTER);
		lblTitulo.setFont(FUENTE_TITULO);
		lblTitulo.setForeground(COLOR_PRIMARIO);
		lblTitulo.setBorder(new EmptyBorder(15, 0, 0, 0));
		getContentPane().add(lblTitulo, BorderLayout.NORTH);

		// --- Panel Central Contenedor (Tarjeta) ---
		JPanel panelContenedor = new JPanel(new BorderLayout(15, 15));
		panelContenedor.setBackground(Color.WHITE);
		panelContenedor.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
				new EmptyBorder(20, 20, 20, 20) // Relleno interno
		));

		// 2. Encabezado y Selector de Juego (dentro de la tarjeta)
		JPanel panelSelector = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		panelSelector.setOpaque(false);

		JLabel lblSelector = new JLabel("Mostrar ranking para:");
		lblSelector.setFont(new Font("Segoe UI", Font.BOLD, 14));
		panelSelector.add(lblSelector);

		cmbJuegos = new JComboBox<>(new String[] { 
			ModeloDatos.JUEGO_CULTURA, 
			ModeloDatos.JUEGO_VF,
			ModeloDatos.JUEGO_ADIVINA, 
			ModeloDatos.JUEGO_AHORCADO 
		});
		cmbJuegos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		cmbJuegos.addActionListener(e -> actualizarRanking());
		panelSelector.add(cmbJuegos);

		panelContenedor.add(panelSelector, BorderLayout.NORTH);

		// 3. Área de Texto para Mostrar el Ranking
		txtAreaRanking = new JTextArea();
		txtAreaRanking.setEditable(false);
		txtAreaRanking.setFont(FUENTE_RANKING);
		txtAreaRanking.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		JScrollPane scrollPane = new JScrollPane(txtAreaRanking);
		panelContenedor.add(scrollPane, BorderLayout.CENTER);
		
		getContentPane().add(panelContenedor, BorderLayout.CENTER);


		// 4. Botón Volver
		JButton btnVolver = crearBotonTransparente("Volver al Menú de Juegos");
		btnVolver.addActionListener(e -> controlador.mostrarMenuSeleccion());

		JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelSur.setBackground(COLOR_FONDO);
		panelSur.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		panelSur.add(btnVolver);

		getContentPane().add(panelSur, BorderLayout.SOUTH);

		// Carga el ranking inicial al abrir la ventana
		actualizarRanking();
	}
    
    // ----------------------------------------------------------------------
    // --- Lógica del Ranking ---
    // ----------------------------------------------------------------------

	/**
	 * Obtiene el ranking del juego seleccionado y lo formatea con estilo.
	 */
	private void actualizarRanking() {
		String tipoJuego = (String) cmbJuegos.getSelectedItem();
		Map<String, Integer> rankingBruto = modelo.getRankingPorJuego(tipoJuego);
		StringBuilder sb = new StringBuilder();

		if (rankingBruto.isEmpty()) {
			sb.append("\n\n\tAún no hay partidas completadas para este juego.");
		} else {
			// 2. Ordenar el mapa por puntaje de forma descendente
			List<Map.Entry<String, Integer>> listaOrdenada = rankingBruto.entrySet().stream()
					.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toList());

			// 3. Formatear la salida con encabezado
			sb.append("\n");
			sb.append(String.format(" %-5s %-20s %s\n", "POS", "USUARIO", "PUNTAJE MÁXIMO"));
			sb.append("------------------------------------------\n");

			for (int i = 0; i < listaOrdenada.size(); i++) {
				Map.Entry<String, Integer> entry = listaOrdenada.get(i);
				
				// Usamos HTML para aplicar color de fondo a las primeras posiciones
				String user = entry.getKey();
				String score = String.valueOf(entry.getValue());
				
				String pos;
				if (i == 0) pos = "🥇";
				else if (i == 1) pos = "🥈";
				else if (i == 2) pos = "🥉";
				else pos = String.format("%d.", (i + 1));
				
				sb.append(String.format(" %-5s %-20s %d\n", 
				    pos, 
				    user, 
				    entry.getValue()
				));
			}
			sb.append("------------------------------------------");
		}

		txtAreaRanking.setText(sb.toString());
		txtAreaRanking.setCaretPosition(0); // Scroll al inicio
	}
    
    // ----------------------------------------------------------------------
    // --- Métodos Auxiliares de Estilo ---
    // ----------------------------------------------------------------------

    /**
     * Método auxiliar para crear botones secundarios (Volver/Cancelar) con estilo sutil.
     */
    private JButton crearBotonTransparente(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
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
}