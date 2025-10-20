package vista;

import controlador.Controlador;
import modelo.ModeloDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Registro extends JFrame implements Vista {
    private Controlador controlador;
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private ModeloDatos miModelo;

    // Colores y Constantes
    private static final Color COLOR_FONDO = new Color(240, 240, 240);
    private static final Color COLOR_PRIMARIO = new Color(59, 89, 182);
    private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font FUENTE_LABEL = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 16);

    // Constructor vacío para WindowBuilder
    public Registro() {
        this(null);
    }

    public Registro(Controlador controlador) {
        this.controlador = controlador;
        setTitle("Registrar Nuevo Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 380);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);

        // Panel contenedor principal
        JPanel panelContenedor = new JPanel();
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
                new EmptyBorder(30, 30, 20, 30)
        ));
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));

        // 1. Título
        JLabel lblTitulo = new JLabel("CREAR CUENTA", SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_PRIMARIO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 30, 0));
        panelContenedor.add(lblTitulo);

        // 2. Panel para campos
        JPanel panelCampos = new JPanel();
        panelCampos.setBackground(Color.WHITE);
        panelCampos.setLayout(new GridLayout(2, 2, 10, 15));

        JLabel lblUsuario = new JLabel("Nuevo Usuario:");
        lblUsuario.setFont(FUENTE_LABEL);
        txtUsuario = new JTextField(15);
        txtUsuario.setFont(FUENTE_LABEL);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(FUENTE_LABEL);
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(FUENTE_LABEL);

        panelCampos.add(lblUsuario);
        panelCampos.add(txtUsuario);
        panelCampos.add(lblPassword);
        panelCampos.add(txtPassword);

        panelContenedor.add(panelCampos);

        // 3. Botón Registrar
        JButton btnRegistro = crearBotonEstilizado("Registrar");
        btnRegistro.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistro.setMaximumSize(new Dimension(200, 40));
        btnRegistro.setBorder(new EmptyBorder(20, 0, 0, 0));
        btnRegistro.addActionListener(e -> intentarRegistro());
        panelContenedor.add(btnRegistro);

        // 4. Botón Volver
        JButton btnVolver = crearBotonTransparente("Ya tengo cuenta (Login)");
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.setBorder(new EmptyBorder(10, 0, 0, 0));
        btnVolver.addActionListener(e -> {
            if (controlador != null) controlador.mostrarVentanaLogin();
        });
        panelContenedor.add(btnVolver);

        // Añadir al JFrame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelContenedor, BorderLayout.CENTER);
    }

    // ------------------------------------------------------------
    // Lógica y estilos
    // ------------------------------------------------------------
    private void intentarRegistro() {
        if (controlador != null) {
            String user = txtUsuario.getText();
            String pass = new String(txtPassword.getPassword());
            controlador.procesarRegistro(user, pass);
        }
    }

    public void limpiarCampos() {
        txtUsuario.setText("");
        txtPassword.setText("");
        txtUsuario.requestFocusInWindow();
    }

    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(COLOR_TEXTO_BOTON);
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

    private JButton crearBotonTransparente(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

    // ------------------------------------------------------------
    // Implementación de la interfaz Vista
    // ------------------------------------------------------------
    @Override
    public void setModelo(ModeloDatos miModelo) {
        this.miModelo = miModelo;
    }

    @Override
    public void setControlador(Controlador Controlador) {
        this.controlador = Controlador;
    }
}
