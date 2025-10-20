package vista;

import controlador.Controlador;
import modelo.ModeloDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Login extends JFrame implements Vista {

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
    public Login() {
        this(null);
    }

    public Login(Controlador controlador) {
        this.controlador = controlador;

        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 380);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);
        getContentPane().setLayout(new BorderLayout());

        // Panel contenedor central
        JPanel panelContenedor = new JPanel();
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
                new EmptyBorder(30, 30, 20, 30)
        ));
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));

        // 1. Título
        JLabel lblTitulo = new JLabel("INICIO DE SESIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_PRIMARIO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 20, 0));
        panelContenedor.add(lblTitulo);

        // 2. Campo Usuario
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(FUENTE_LABEL);
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenedor.add(lblUsuario);

        txtUsuario = new JTextField(15);
        txtUsuario.setFont(FUENTE_LABEL);
        txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtUsuario.addActionListener(e -> intentarLogin());
        panelContenedor.add(txtUsuario);

        // 3. Campo Contraseña
        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(FUENTE_LABEL);
        lblPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelContenedor.add(lblPassword);

        txtPassword = new JPasswordField(15);
        txtPassword.setFont(FUENTE_LABEL);
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtPassword.addActionListener(e -> intentarLogin());
        panelContenedor.add(txtPassword);

        // 4. Botón Login
        JButton btnLogin = crearBotonEstilizado("Entrar");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.addActionListener(e -> intentarLogin());
        btnLogin.setMaximumSize(new Dimension(150, 40));
        panelContenedor.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenedor.add(btnLogin);

        // 5. Botón Volver
        JButton btnVolver = crearBotonTransparente("Volver a Inicio");
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> controlador.iniciarAplicacion());
        panelContenedor.add(Box.createRigidArea(new Dimension(0, 10)));
        panelContenedor.add(btnVolver);

        // Añadir el panel contenedor
        getContentPane().add(panelContenedor, BorderLayout.CENTER);

        // Botón predeterminado
        getRootPane().setDefaultButton(btnLogin);
    }

    private void intentarLogin() {
        String user = txtUsuario.getText();
        String pass = new String(txtPassword.getPassword());
        if (controlador != null) {
            controlador.procesarLogin(user, pass);
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

    private JButton crearBotonTransparente(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setForeground(Color.GRAY.darker());

        // Hover
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

    @Override
    public void setModelo(ModeloDatos miModelo) {
        this.miModelo = miModelo;
    }

    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
}
