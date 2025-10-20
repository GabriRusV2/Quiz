package vista;

import controlador.Controlador;
import modelo.ModeloDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Inicio extends JFrame implements Vista {
	private Controlador controlador;
	private ModeloDatos miModelo; // Cambiado de 'ModeloDatos' a 'miModelo' para claridad

	// Colores y Constantes
	private static final Color COLOR_FONDO = new Color(240, 240, 240); // Gris claro, suave
	private static final Color COLOR_PRIMARIO = new Color(59, 89, 182); // Azul corporativo
	private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
	private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 36);
	private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 18);

	// Recibe el controlador en el constructor
	public Inicio(Controlador controlador) {
		this.controlador = controlador;
		setTitle("Mini Juegos Quiz - Acceso");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400); // Aumento el tamaño ligeramente
		setLocationRelativeTo(null);

		// Configurar el fondo de la ventana
		getContentPane().setBackground(COLOR_FONDO);
		getContentPane().setLayout(new GridBagLayout());

		// --- Panel Contenedor Central para dar un efecto de "tarjeta" ---
		JPanel panelContenedor = new JPanel();
		panelContenedor.setBackground(Color.WHITE);
		panelContenedor.setLayout(new GridBagLayout());
		// Añadir un borde de sombra suave (simulado con EmptyBorder y relleno)
		panelContenedor.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true), // Borde fino y redondeado
				new EmptyBorder(30, 30, 30, 30) // Relleno interno
		));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 0, 10, 0); // Espacio vertical entre elementos
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridwidth = 1;

		// 1. Título principal
		JLabel lblTitulo = new JLabel("¡Bienvenido al Quiz!");
		lblTitulo.setFont(FUENTE_TITULO);
		lblTitulo.setForeground(COLOR_PRIMARIO); // Color al título
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.gridy = 0;
		panelContenedor.add(lblTitulo, gbc);

		// 2. Subtítulo
		JLabel lblOpcion = new JLabel("Elige una opción para acceder:");
		lblOpcion.setFont(new Font("Segoe UI", Font.ITALIC, 16));
		lblOpcion.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 0, 30, 0); // Más espacio debajo del subtítulo
		panelContenedor.add(lblOpcion, gbc);

		// 3. Panel para botones (Asegura que los botones sean del mismo tamaño)
		JPanel panelBotones = new JPanel(new GridLayout(1, 2, 25, 0)); // Separación horizontal
		panelBotones.setOpaque(false); // Transparente para ver el fondo blanco del contenedor
		
		// --- Botón Login ---
		JButton btnLogin = crearBotonEstilizado("Iniciar Sesión (Login)");
		btnLogin.addActionListener(e -> controlador.mostrarVentanaLogin());
		panelBotones.add(btnLogin);

		// --- Botón Registrarse ---
		JButton btnRegistro = crearBotonEstilizado("Registrarse");
		btnRegistro.addActionListener(e -> controlador.mostrarVentanaRegistro());
		panelBotones.add(btnRegistro);

		gbc.gridy = 2;
		gbc.ipady = 15; // Relleno vertical para hacer el panel de botones más alto
		gbc.weightx = 1.0; // Hace que el panel de botones se expanda horizontalmente
		panelContenedor.add(panelBotones, gbc);

		// Añadir el contenedor central a la ventana principal
		GridBagConstraints gbcCentral = new GridBagConstraints();
		gbcCentral.fill = GridBagConstraints.BOTH;
		gbcCentral.insets = new Insets(50, 50, 50, 50); // Margen alrededor del panel
		getContentPane().add(panelContenedor, gbcCentral);
	}
    
    /**
     * Método auxiliar para crear botones con un estilo unificado.
     */
    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_PRIMARIO); // Color de fondo primario
        boton.setForeground(COLOR_TEXTO_BOTON); // Texto blanco
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Relleno
        boton.setFocusPainted(false); // Quitar el feo rectángulo de foco
        
        // Simulación de "Hover" (opcional pero muy recomendable)
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(COLOR_PRIMARIO.darker()); // Oscurecer al pasar el ratón
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