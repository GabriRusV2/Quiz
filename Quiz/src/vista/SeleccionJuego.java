package vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;

public class SeleccionJuego extends JFrame {
    private Controlador controlador;

    // Colores y Constantes
    private static final Color COLOR_FONDO = new Color(240, 240, 240);
    private static final Color COLOR_PRIMARIO = new Color(59, 89, 182);
    private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font FUENTE_NAVEGACION = new Font("Segoe UI", Font.PLAIN, 14);

    public SeleccionJuego(Controlador controlador) {
        this.controlador = controlador;
        setTitle("Elige tu juego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        getContentPane().setLayout(new BorderLayout(15, 15));

        // 1. Título
        JLabel lblTitulo = new JLabel("Selecciona un Juego", SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_PRIMARIO);
        lblTitulo.setBorder(new EmptyBorder(15, 0, 0, 0));
        getContentPane().add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Contenedor Central (Tarjeta) ---
        JPanel panelContenedor = new JPanel();
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
                new EmptyBorder(25, 40, 25, 40)
        ));
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));

        // --- Panel de Botones de Juego ---
        JPanel panelJuegos = new JPanel();
        panelJuegos.setOpaque(false);
        panelJuegos.setLayout(new GridLayout(4, 1, 0, 20)); // Espacio vertical 20px

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

        // Centrar los botones horizontalmente
        panelJuegos.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenedor.add(panelJuegos);

        // Centrar panelContenedor en la ventana
        JPanel wrapper = new JPanel();
        wrapper.setBackground(COLOR_FONDO);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.add(Box.createVerticalGlue());
        panelContenedor.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.add(panelContenedor);
        wrapper.add(Box.createVerticalGlue());
        getContentPane().add(wrapper, BorderLayout.CENTER);

        // --- Panel Inferior para Perfil, Ranking y Cerrar Sesión ---
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        panelInferior.setBackground(COLOR_FONDO);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JButton btnRanking = crearBotonNavegacion("Ver Ranking");
        btnRanking.addActionListener(e -> controlador.mostrarVentanaRanking());
        panelInferior.add(btnRanking);

        JButton btnPerfil = crearBotonNavegacion("Ver Perfil");
        btnPerfil.addActionListener(e -> controlador.mostrarVentanaPerfil());
        panelInferior.add(btnPerfil);

        JButton btnCerrarSesion = crearBotonNavegacion("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> controlador.iniciarAplicacion());
        panelInferior.add(btnCerrarSesion);

        getContentPane().add(panelInferior, BorderLayout.SOUTH);
    }

    // ----------------------------------------------------------------------
    // --- Métodos Auxiliares de Estilo ---
    // ----------------------------------------------------------------------

    private JButton crearBotonJuego(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(COLOR_TEXTO_BOTON);
        boton.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        boton.setFocusPainted(false);
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

    private JButton crearBotonNavegacion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_NAVEGACION);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setForeground(Color.GRAY.darker());
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setForeground(COLOR_PRIMARIO);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setForeground(Color.GRAY.darker());
            }
        });
        return boton;
    }
}
