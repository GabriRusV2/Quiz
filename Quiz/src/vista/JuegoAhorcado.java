package vista;

import controlador.Controlador;
import modelo.ModeloDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JuegoAhorcado extends JFrame {

    private Controlador controlador;
    private ModeloDatos modelo;

    private JLabel lblPista;
    private JLabel lblPalabra;
    private JLabel lblPuntaje;
    private JLabel lblAhorcadoDibujo;
    private JTextField txtLetra;
    private JButton btnAdivinar;

    // Colores y Constantes
    private static final Color COLOR_FONDO = new Color(240, 240, 240);
    private static final Color COLOR_PRIMARIO = new Color(59, 89, 182);
    private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 20);
    private static final Font FUENTE_PALABRA = new Font("Monospaced", Font.BOLD, 40);
    private static final Font FUENTE_PUNTAJE = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FUENTE_PISTA = new Font("Segoe UI", Font.ITALIC, 16);
    private static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 16);

    // Constructor vacío para WindowBuilder
    public JuegoAhorcado() {
        this(null, null);
    }

    // Constructor principal
    public JuegoAhorcado(Controlador controlador, ModeloDatos modelo) {
        this.controlador = controlador;
        this.modelo = modelo;

        setTitle("Juego: El Ahorcado");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        getContentPane().setLayout(new BorderLayout(20, 20));

        // Panel izquierdo: dibujo del ahorcado
        lblAhorcadoDibujo = new JLabel("<html><pre>Progreso del ahorcado</pre></html>");
        lblAhorcadoDibujo.setFont(new Font("Monospaced", Font.PLAIN, 18));
        lblAhorcadoDibujo.setVerticalAlignment(SwingConstants.TOP);
        lblAhorcadoDibujo.setBorder(BorderFactory.createTitledBorder("Progreso"));

        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setPreferredSize(new Dimension(200, 0));
        panelIzquierdo.setBackground(Color.WHITE);
        panelIzquierdo.add(lblAhorcadoDibujo, BorderLayout.CENTER);
        getContentPane().add(panelIzquierdo, BorderLayout.WEST);

        // Panel central: juego y controles
        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(Color.WHITE);
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));

        lblPuntaje = new JLabel("Puntaje: 0 | Fallos restantes: 6", SwingConstants.CENTER);
        lblPuntaje.setFont(FUENTE_PUNTAJE);
        lblPuntaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(lblPuntaje);

        lblPista = new JLabel("Pista: ...", SwingConstants.CENTER);
        lblPista.setFont(FUENTE_PISTA);
        lblPista.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(lblPista);

        lblPalabra = new JLabel("___ ___ ___", SwingConstants.CENTER);
        lblPalabra.setFont(FUENTE_PALABRA);
        lblPalabra.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCentral.add(lblPalabra);

        JPanel panelInput = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelInput.setOpaque(false);
        txtLetra = new JTextField(5);
        txtLetra.setFont(new Font("Monospaced", Font.BOLD, 18));
        btnAdivinar = crearBotonEstilizado("Intentar");
        panelInput.add(new JLabel("Letra/Palabra:"));
        panelInput.add(txtLetra);
        panelInput.add(btnAdivinar);
        panelCentral.add(panelInput);

        getContentPane().add(panelCentral, BorderLayout.CENTER);

        // Protección para WindowBuilder si controlador/modelo son null
        btnAdivinar.addActionListener(e -> {
            if (controlador != null) {
                // Lógica real del juego
            }
        });
        txtLetra.addActionListener(e -> {
            if (controlador != null) {
                // Lógica real del juego
            }
        });
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
}
