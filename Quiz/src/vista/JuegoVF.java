package vista;

import controlador.Controlador;
import modelo.ModeloDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JuegoVF extends JFrame implements Vista {
    private Controlador controlador;
    private ModeloDatos modelo;
    private JLabel lblAfirmacion;
    private JButton btnVerdadero;
    private JButton btnFalso;
    private ModeloDatos miModelo;

    // Colores y Constantes
    private static final Color COLOR_FONDO = new Color(240, 240, 240);
    private static final Color COLOR_PRIMARIO = new Color(59, 89, 182);
    private static final Color COLOR_VERDADERO = new Color(46, 204, 113);
    private static final Color COLOR_FALSO = new Color(231, 76, 60);
    private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
    private static final Font FUENTE_AFIRMACION = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 18);

    // Constructor vacío para WindowBuilder
    public JuegoVF() {
        this(null, null);
    }

    // Constructor principal
    public JuegoVF(Controlador controlador, ModeloDatos modelo) {
        this.controlador = controlador;
        this.modelo = modelo;

        setTitle("Juego: Verdadero o Falso");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 350);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);
        getContentPane().setLayout(new GridBagLayout());

        // --- Panel Contenedor Central ---
        JPanel panelContenedor = new JPanel(new BorderLayout(20, 20));
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
                new EmptyBorder(30, 30, 30, 30)
        ));

        // 1. Etiqueta de afirmación
        lblAfirmacion = new JLabel("Afirmación ejemplo", SwingConstants.CENTER);
        lblAfirmacion.setFont(FUENTE_AFIRMACION);
        lblAfirmacion.setForeground(COLOR_PRIMARIO.darker());
        lblAfirmacion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContenedor.add(lblAfirmacion, BorderLayout.CENTER);

        // 2. Panel para botones V/F
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 30, 10));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botón Verdadero
        btnVerdadero = crearBotonDecision("Verdadero", COLOR_VERDADERO);
        btnVerdadero.addActionListener(e -> {
            if (controlador != null) controlador.procesarRespuestaVF(true);
        });
        panelBotones.add(btnVerdadero);

        // Botón Falso
        btnFalso = crearBotonDecision("Falso", COLOR_FALSO);
        btnFalso.addActionListener(e -> {
            if (controlador != null) controlador.procesarRespuestaVF(false);
        });
        panelBotones.add(btnFalso);

        panelContenedor.add(panelBotones, BorderLayout.SOUTH);

        // Añadir la tarjeta al centro de la ventana
        getContentPane().add(panelContenedor, new GridBagConstraints());

        // Inicializa vista para WindowBuilder
        actualizarVista();
    }

    // ----------------------------------------------------------------------
    // --- Lógica de la Vista ---
    // ----------------------------------------------------------------------
    public void actualizarVista() {
        if (modelo != null) {
            int actual = modelo.getPreguntaActual();
            if (actual < modelo.afirmacionesVF.length) {
                String afirmacion = modelo.afirmacionesVF[actual].getKey();
                lblAfirmacion.setText("Afirmación " + (actual + 1) + ": " + afirmacion);
            }
        }
    }

    // ----------------------------------------------------------------------
    // --- Métodos Auxiliares de Estilo ---
    // ----------------------------------------------------------------------
    private JButton crearBotonDecision(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(colorFondo);
        boton.setForeground(COLOR_TEXTO_BOTON);
        boton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        boton.setFocusPainted(false);

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorFondo);
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
