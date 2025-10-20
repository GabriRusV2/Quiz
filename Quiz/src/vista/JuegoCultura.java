package vista;

import controlador.Controlador;
import modelo.ModeloDatos;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Arrays;

public class JuegoCultura extends JFrame implements Vista {
	private Controlador controlador;
	private ModeloDatos modelo;
	private JLabel lblPregunta;
	private JButton[] botonesOpcion;
	private ModeloDatos miModelo; // Uso consistente para la interfaz

	// Colores y Constantes
	private static final Color COLOR_FONDO = new Color(240, 240, 240); // Gris claro, suave
	private static final Color COLOR_PRIMARIO = new Color(59, 89, 182); // Azul corporativo
	private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
	private static final Font FUENTE_PREGUNTA = new Font("Segoe UI", Font.BOLD, 22);
	private static final Font FUENTE_OPCION = new Font("Segoe UI", Font.BOLD, 14); // Fuente para el texto del botón
	private static final Font FUENTE_LETRA_OPCION = new Font("Segoe UI", Font.BOLD, 16);


	public JuegoCultura(Controlador controlador, ModeloDatos modelo) {
		this.controlador = controlador;
		this.modelo = modelo;

		setTitle("Juego: Cultura General");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 450); // Tamaño más ancho para preguntas largas
		setLocationRelativeTo(null);
		
		getContentPane().setBackground(COLOR_FONDO);
		getContentPane().setLayout(new GridBagLayout()); // Usamos GridBagLayout para centrar la tarjeta

		// --- Panel Contenedor Central (Tarjeta) ---
		JPanel panelContenedor = new JPanel(new BorderLayout(20, 20));
		panelContenedor.setBackground(Color.WHITE);
		panelContenedor.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
				new EmptyBorder(30, 30, 30, 30) // Relleno interno
		));

		// 1. Etiqueta de la Pregunta
		lblPregunta = new JLabel("", SwingConstants.CENTER);
		lblPregunta.setFont(FUENTE_PREGUNTA);
		lblPregunta.setForeground(COLOR_PRIMARIO);
		lblPregunta.setBorder(new EmptyBorder(10, 0, 15, 0));
		panelContenedor.add(lblPregunta, BorderLayout.NORTH);

		// 2. Panel de Opciones (Centro)
		JPanel panelOpciones = new JPanel(new GridLayout(2, 2, 20, 20)); // Mayor espaciado entre botones
		panelOpciones.setOpaque(false);
		
		botonesOpcion = new JButton[4];
		String[] letras = { "A", "B", "C", "D" };

		for (int i = 0; i < 4; i++) {
			final int index = i;
			botonesOpcion[i] = crearBotonOpcion(""); // Crear botón estilizado
			
			// Llama al controlador con la respuesta
			botonesOpcion[i].addActionListener(e -> {
				// Obtenemos solo el texto de la respuesta (eliminando "A: ", "B: ", etc.)
				String textoCompleto = botonesOpcion[index].getText();
				String respuesta = textoCompleto.substring(3).trim(); 
				controlador.procesarRespuestaCultura(respuesta);
			});
			panelOpciones.add(botonesOpcion[i]);
		}
		
		panelContenedor.add(panelOpciones, BorderLayout.CENTER);
		
		// Añadir la tarjeta al centro de la ventana principal
		getContentPane().add(panelContenedor, new GridBagConstraints());


		// Inicializa la primera vista
		actualizarVista();
	}
    
    // ----------------------------------------------------------------------
    // --- Lógica de la Vista ---
    // ----------------------------------------------------------------------

	// Método crucial de la Vista: Lee el Modelo y se dibuja
	public void actualizarVista() {
		int actual = modelo.getPreguntaActual();
		
		// Verificación de límite para evitar ArrayIndexOutOfBounds
		if (actual < modelo.preguntasCultura.length) {
		    String[] datos = modelo.preguntasCultura[actual];
	
		    // Actualiza la pregunta
		    lblPregunta.setText("Pregunta " + (actual + 1) + " de " + modelo.preguntasCultura.length + ": " + datos[0]);
	
		    // Actualiza las opciones
		    for (int i = 0; i < 4; i++) {
		        char letra = (char) ('A' + i);
		        // Concatenamos la letra de la opción y la respuesta
		        String textoOpcion = letra + ": " + datos[i + 1];
		        botonesOpcion[i].setText(textoOpcion);
		    }
		}
	}
    
    // ----------------------------------------------------------------------
    // --- Métodos Auxiliares de Estilo ---
    // ----------------------------------------------------------------------

    /**
     * Crea un botón de opción con estilo primario.
     */
    private JButton crearBotonOpcion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_OPCION);
        boton.setHorizontalAlignment(SwingConstants.LEFT); // Alinear el texto a la izquierda
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(COLOR_TEXTO_BOTON);
        boton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Gran relleno
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