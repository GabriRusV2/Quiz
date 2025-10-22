package vista;

import controlador.Controlador;
import modelo.ModeloDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Inicio extends JFrame implements Vista {

    private Controlador controlador;
    private ModeloDatos miModelo;

    // Colores y Constantes
    private static final Color COLOR_PRIMARIO = new Color(59, 89, 182);
    private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 36);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 18);

    // Constructor vacío para WindowBuilder
    public Inicio() {
        this(null);
    }

    // Constructor principal
    public Inicio(Controlador controlador) {
        this.controlador = controlador;

        setTitle("Mini Juegos Quiz - Acceso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 400);
        setLocationRelativeTo(null);

        // ✅ Panel con imagen de fondo
        Image imagenFondo = new ImageIcon(getClass().getResource("/img/FONDO2.jpg")).getImage(); // usa PNG o JPG
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(panelFondo);

        // Panel contenedor central
        JPanel panelContenedor = new JPanel();
        panelContenedor.setBounds(0, 0, 536, 363);
        panelContenedor.setBackground(new Color(255, 255, 255, 220)); // Blanco semitransparente
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
                new EmptyBorder(30, 30, 30, 30)
        ));
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));

        // 1. Título principal
        JLabel lblTitulo = new JLabel("¡Bienvenido al Quiz!", SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_PRIMARIO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 20, 0));
        panelContenedor.add(lblTitulo);

        // 2. Subtítulo
        JLabel lblOpcion = new JLabel("Elige una opción para acceder:");
        lblOpcion.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblOpcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblOpcion.setBorder(new EmptyBorder(0, 0, 20, 0));
        panelContenedor.add(lblOpcion);

        // 3. Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(1, 2, 25, 0));
        panelBotones.setOpaque(false);

        JButton btnLogin = crearBotonEstilizado("Iniciar Sesión (Login)");
        btnLogin.addActionListener(e -> {
            if (controlador != null) controlador.mostrarVentanaLogin();
        });
        panelBotones.add(btnLogin);

        JButton btnRegistro = crearBotonEstilizado("Registrarse");
        btnRegistro.addActionListener(e -> {
            if (controlador != null) controlador.mostrarVentanaRegistro();
        });
        panelFondo.setLayout(null);
        panelBotones.add(btnRegistro);

        panelContenedor.add(panelBotones);

        // Añadir el panel contenedor al fondo
        panelFondo.add(panelContenedor);
    }

    /**
     * Método auxiliar para crear botones con un estilo unificado.
     */
    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(COLOR_TEXTO_BOTON);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setFocusPainted(false);

        // Hover
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
