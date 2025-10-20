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

    // Constructor vacío para WindowBuilder
    public JuegoAdivina() {
        this(null, null);
    }

    // Constructor principal
    public JuegoAdivina(Controlador controlador, ModeloDatos modelo) {
        this.controlador = controlador;
        this.modelo = modelo;

        setTitle("Juego: Adivina la Palabra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 350);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);
        getContentPane().setLayout(new BorderLayout());

        // Panel contenedor central
        JPanel panelContenedor = new JPanel();
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
                new EmptyBorder(30, 30, 30, 30)
        ));
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));

        // 1. Panel de la Pista
        lblPista = new JLabel("Pista aquí", SwingConstants.CENTER);
        lblPista.setFont(FUENTE_PISTA);
        lblPista.setForeground(COLOR_PRIMARIO.darker());
        lblPista.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPista.setBorder(new EmptyBorder(0, 0, 15, 0));

        JPanel panelPista = new JPanel();
        panelPista.setBackground(new Color(230, 235, 255));
        panelPista.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARIO, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        panelPista.add(lblPista);
        panelContenedor.add(panelPista);

        // 2. Etiqueta "Tu Respuesta"
        JLabel lblRespuesta = new JLabel("Introduce la palabra:", SwingConstants.CENTER);
        lblRespuesta.setFont(FUENTE_ETIQUETA);
        lblRespuesta.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRespuesta.setBorder(new EmptyBorder(15, 0, 5, 0));
        panelContenedor.add(lblRespuesta);

        // 3. Campo de Respuesta
        txtRespuesta = new JTextField(15);
        txtRespuesta.setFont(FUENTE_CAMPO);
        txtRespuesta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        txtRespuesta.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtRespuesta.addActionListener(e -> {
            if (controlador != null) controlador.procesarRespuestaAdivina(txtRespuesta.getText());
        });
        panelContenedor.add(txtRespuesta);

        // 4. Botón Comprobar
        JButton btnComprobar = crearBotonEstilizado("Comprobar");
        btnComprobar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnComprobar.addActionListener(e -> {
            if (controlador != null) controlador.procesarRespuestaAdivina(txtRespuesta.getText());
        });
        panelContenedor.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenedor.add(btnComprobar);

        // Añadir el panel contenedor al centro de la ventana
        getContentPane().add(panelContenedor, BorderLayout.CENTER);

        // Inicializa la vista (si hay modelo)
        if (modelo != null) {
            actualizarVista();
        }
    }

    /**
     * Actualiza la interfaz gráfica leyendo el estado actual del Modelo.
     */
    public void actualizarVista() {
        if (modelo == null) return;
        int actual = modelo.getPreguntaActual();
        if (actual < modelo.palabrasAdivina.length) {
            String pista = modelo.palabrasAdivina[actual].getKey();
            lblPista.setText("Palabra " + (actual + 1) + " de " + modelo.palabrasAdivina.length + ": " + pista);
            txtRespuesta.setText("");
            txtRespuesta.requestFocusInWindow();
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
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
}
