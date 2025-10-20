package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;

public class SeleccionJuego extends JFrame {
	private Controlador controlador;
	
	// Colores y Constantes (Consistentes con otras vistas)
	private static final Color COLOR_FONDO = new Color(240, 240, 240); // Gris claro, suave
	private static final Color COLOR_PRIMARIO = new Color(59, 89, 182); // Azul corporativo
	private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
	private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
	private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 16);
	private static final Font FUENTE_NAVEGACION = new Font("Segoe UI", Font.PLAIN, 14);


	public SeleccionJuego(Controlador controlador) {
		this.controlador = controlador;
		setTitle("Elige tu juego");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 500); // Aumento el tamaño ligeramente
		setLocationRelativeTo(null);

		getContentPane().setBackground(COLOR_FONDO);
		getContentPane().setLayout(new BorderLayout(15, 15)); // Espaciado principal

		// 1. Título
		JLabel lblTitulo = new JLabel("Selecciona un Juego", SwingConstants.CENTER);
		lblTitulo.setFont(FUENTE_TITULO);
		lblTitulo.setForeground(COLOR_PRIMARIO);
		lblTitulo.setBorder(new EmptyBorder(15, 0, 0, 0));
		getContentPane().add(lblTitulo, BorderLayout.NORTH);

		// --- Panel Contenedor Central (Tarjeta para Juegos) ---
		JPanel panelContenedor = new JPanel(new BorderLayout(15, 15));
		panelContenedor.setBackground(Color.WHITE);
		panelContenedor.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
				new EmptyBorder(25, 40, 25, 40) // Relleno interno
		));

		// --- Panel de Botones de Juego ---
		JPanel panelJuegos = new JPanel(new GridLayout(4, 1, 10, 20)); // Mayor espaciado vertical
		panelJuegos.setOpaque(false); // Transparente para ver el fondo blanco del contenedor

		// --- Botones de Juego Estilizados ---
		JButton btnJuego1 = crearBotonJuego("Juego 1: Cultura General");
		btnJuego1.addActionListener(e -> controlador.iniciarJuegoCultura());
		panelJuegos.add(btnJuego1);

		JButton btnJuego2 = crearBotonJuego("Juego 2: Verdadero o Falso");
		btnJuego2.addActionListener(e -> controlador.iniciarJuegoVF());
		panelJuegos.add(btnJuego2);

		JButton btnJuego3 = crearBotonJuego("Juego 3: Adivina la Palabra");
		btnJuego3.addActionListener(e -> controlador.iniciarJuegoAdivina());
		panelJuegos.add(btnJuego3);

		JButton btnJuego4 = crearBotonJuego("Juego 4: El Ahorcado");
		btnJuego4.addActionListener(e -> controlador.iniciarJuegoAhorcado());
		panelJuegos.add(btnJuego4);

		panelContenedor.add(panelJuegos, BorderLayout.CENTER);
		
		// Usamos GridBagLayout para centrar la tarjeta en la ventana
		JPanel wrapper = new JPanel(new GridBagLayout());
		wrapper.setBackground(COLOR_FONDO);
		wrapper.add(panelContenedor);
		
		getContentPane().add(wrapper, BorderLayout.CENTER);

		// --- Panel Inferior para Perfil, Ranking y Cerrar Sesión ---
		JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10)); // Más separación horizontal
		panelInferior.setBackground(COLOR_FONDO);
		panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

		// 1. Botón para ver el Ranking
		JButton btnRanking = crearBotonNavegacion("Ver Ranking");
		btnRanking.addActionListener(e -> controlador.mostrarVentanaRanking());
		panelInferior.add(btnRanking);

		// 2. Botón para ver el Perfil
		JButton btnPerfil = crearBotonNavegacion("Ver Perfil");
		btnPerfil.addActionListener(e -> controlador.mostrarVentanaPerfil());
		panelInferior.add(btnPerfil);

		// 3. Botón para Cerrar Sesión
		JButton btnCerrarSesion = crearBotonNavegacion("Cerrar Sesión");
		btnCerrarSesion.addActionListener(e -> controlador.iniciarAplicacion());
		panelInferior.add(btnCerrarSesion);

		getContentPane().add(panelInferior, BorderLayout.SOUTH);
	}
	
    // ----------------------------------------------------------------------
    // --- Métodos Auxiliares de Estilo ---
    // ----------------------------------------------------------------------

    /**
     * Crea un botón principal para iniciar juegos.
     */
    private JButton crearBotonJuego(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(COLOR_TEXTO_BOTON);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25)); // Relleno grande
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
    
    /**
     * Crea un botón secundario para la navegación (Perfil, Ranking, Cerrar Sesión).
     */
    private JButton crearBotonNavegacion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_NAVEGACION);
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