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
	// private ModeloDatos ModeloDatos; // Variable redundante eliminada
	private ModeloDatos miModelo; // Uso consistente para la interfaz

	// Colores y Constantes (Consistentes con Login e Inicio)
	private static final Color COLOR_FONDO = new Color(240, 240, 240); // Gris claro, suave
	private static final Color COLOR_PRIMARIO = new Color(59, 89, 182); // Azul corporativo
	private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
	private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
	private static final Font FUENTE_LABEL = new Font("Segoe UI", Font.PLAIN, 14);
	private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 16);

	public Registro(Controlador controlador) {
		this.controlador = controlador;
		setTitle("Registrar Nuevo Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 380); // Aumento el tamaño para mejor diseño
		setLocationRelativeTo(null);

		getContentPane().setBackground(COLOR_FONDO);
		getContentPane().setLayout(new GridBagLayout()); // GridBagLayout principal para centrar la tarjeta

		// --- Panel Contenedor Central (Tarjeta) ---
		JPanel panelContenedor = new JPanel();
		panelContenedor.setBackground(Color.WHITE);
		panelContenedor.setLayout(new GridBagLayout());
		panelContenedor.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
				new EmptyBorder(30, 30, 20, 30) // Relleno interno
		));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 15, 10, 15); // Espaciado
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// 1. Título
		JLabel lblTitulo = new JLabel("CREAR CUENTA", SwingConstants.CENTER);
		lblTitulo.setFont(FUENTE_TITULO);
		lblTitulo.setForeground(COLOR_PRIMARIO);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 0, 30, 0); // Más espacio debajo del título
		panelContenedor.add(lblTitulo, gbc);

		// --- Formulario (2 columnas) ---
		gbc.gridwidth = 1;
		gbc.insets = new Insets(8, 0, 8, 10);

		// 2. Campo Usuario
		JLabel lblUsuario = new JLabel("Nuevo Usuario:");
		lblUsuario.setFont(FUENTE_LABEL);
		gbc.gridx = 0;
		gbc.gridy = 1;
		panelContenedor.add(lblUsuario, gbc);

		gbc.gridx = 1;
		txtUsuario = new JTextField(15);
		txtUsuario.setFont(FUENTE_LABEL);
		txtUsuario.setPreferredSize(new Dimension(150, 30));
		panelContenedor.add(txtUsuario, gbc);

		// 3. Campo Contraseña
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setFont(FUENTE_LABEL);
		gbc.gridx = 0;
		gbc.gridy = 2;
		panelContenedor.add(lblPassword, gbc);

		gbc.gridx = 1;
		txtPassword = new JPasswordField(15);
		txtPassword.setFont(FUENTE_LABEL);
		txtPassword.setPreferredSize(new Dimension(150, 30));
		panelContenedor.add(txtPassword, gbc);

		// 4. Botón Registrar
		JButton btnRegistro = crearBotonEstilizado("Registrar");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(25, 0, 15, 0); // Espacio superior e inferior grande
		btnRegistro.addActionListener(e -> intentarRegistro());
		panelContenedor.add(btnRegistro, gbc);

		// 5. Botón Volver
		JButton btnVolver = crearBotonTransparente("Ya tengo cuenta (Login)");
		btnVolver.addActionListener(e -> controlador.mostrarVentanaLogin());
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 0, 0); // Pequeño espacio superior
		panelContenedor.add(btnVolver, gbc);

		// Añadir el panel contenedor al centro de la ventana
		getContentPane().add(panelContenedor, new GridBagConstraints());
	}
    
    // ----------------------------------------------------------------------
    // --- Métodos Auxiliares de Estilo y Lógica ---
    // ----------------------------------------------------------------------

	private void intentarRegistro() {
		String user = txtUsuario.getText();
		String pass = new String(txtPassword.getPassword());
		controlador.procesarRegistro(user, pass);
	}

	public void limpiarCampos() {
		txtUsuario.setText("");
		txtPassword.setText("");
		txtUsuario.requestFocusInWindow();
	}
    
    /**
     * Método auxiliar para crear botones con un estilo unificado (principal).
     */
    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(COLOR_TEXTO_BOTON);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
     * Método auxiliar para crear botones secundarios (Volver/Login) con estilo sutil.
     */
    private JButton crearBotonTransparente(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
    
    // ----------------------------------------------------------------------
    // --- Implementación de la Interfaz Vista (Corregida) ---
    // ----------------------------------------------------------------------

	@Override
	public void setModelo(ModeloDatos miModelo) {
		this.miModelo = miModelo;
	}

	@Override
	public void setControlador(Controlador Controlador) {
		this.controlador = Controlador;
	}
}