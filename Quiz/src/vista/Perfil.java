package vista;

import controlador.Controlador;
import modelo.ModeloDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class Perfil extends JFrame implements Vista {
    private Controlador controlador;
    private ModeloDatos modelo;
    private ModeloDatos miModelo;

    // Colores y Constantes
    private static final Color COLOR_FONDO = new Color(240, 240, 240);
    private static final Color COLOR_PRIMARIO = new Color(59, 89, 182);
    private static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 16);

    // Componentes
    private JLabel lblTitulo;
    private JTextArea txtAreaHistorial;
    private JButton btnVolver;

    // Constructor vacío para WindowBuilder
    public Perfil() {
        this(null, null);
    }

    public Perfil(Controlador controlador, ModeloDatos modelo) {
        this.controlador = controlador;
        this.modelo = modelo;

        setTitle("Perfil de Usuario");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 550, 550);
        setLocationRelativeTo(null);

        getContentPane().setBackground(COLOR_FONDO);
        getContentPane().setLayout(new GridBagLayout());

        // --- Panel Contenedor Central ---
        JPanel panelContenedor = new JPanel(new BorderLayout(15, 15));
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
                new EmptyBorder(25, 25, 25, 25)
        ));

        // 1. Encabezado
        String usuario = modelo != null ? modelo.getUsuarioActivo() : "Usuario Ejemplo";
        lblTitulo = new JLabel("Perfil de: " + usuario, SwingConstants.CENTER);
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_PRIMARIO);
        panelContenedor.add(lblTitulo, BorderLayout.NORTH);

        // 2. Historial de puntajes
        txtAreaHistorial = new JTextArea();
        txtAreaHistorial.setEditable(false);
        txtAreaHistorial.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtAreaHistorial.setForeground(Color.DARK_GRAY);

        cargarHistorial(txtAreaHistorial); // Cargar datos del modelo si existe

        JScrollPane scrollPane = new JScrollPane(txtAreaHistorial);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Últimos Puntajes por Juego",
                0,
                0,
                FUENTE_SUBTITULO,
                Color.BLACK
        ));
        panelContenedor.add(scrollPane, BorderLayout.CENTER);

        // 3. Botón Volver
        btnVolver = crearBotonTransparente("Volver al Menú de Juegos");
        btnVolver.addActionListener(e -> {
            if (controlador != null) controlador.mostrarMenuSeleccion();
        });

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSur.setOpaque(false);
        panelSur.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        panelSur.add(btnVolver);
        panelContenedor.add(panelSur, BorderLayout.SOUTH);

        // Añadir la tarjeta al centro de la ventana
        getContentPane().add(panelContenedor, new GridBagConstraints());
    }

    /**
     * Carga el historial de puntajes desde el Modelo y lo formatea en el JTextArea.
     * Si el modelo es null, carga datos de ejemplo para WindowBuilder.
     */
    private void cargarHistorial(JTextArea area) {
        if (modelo != null) {
            Map<String, List<Integer>> historial = modelo.getHistorialPuntajes();
            StringBuilder sb = new StringBuilder();

            if (historial.isEmpty() || historial.values().stream().allMatch(List::isEmpty)) {
                sb.append("\n\n\tNo hay puntajes registrados aún.");
            } else {
                historial.forEach((tipoJuego, puntajes) -> {
                    if (!puntajes.isEmpty()) {
                        sb.append("\n=========================================\n");
                        sb.append(String.format(" %s\n", tipoJuego.toUpperCase()));
                        sb.append("=========================================\n");
                        for (int i = 0; i < puntajes.size(); i++) {
                            sb.append(String.format("  Última partida #%d: %d puntos\n", (i + 1), puntajes.get(i)));
                        }
                        sb.append("\n");
                    }
                });
            }
            area.setText(sb.toString());
            area.setCaretPosition(0);
        } else {
            // Datos de ejemplo para WindowBuilder
            area.setText("=========================================\nJUEGO EJEMPLO\n=========================================\n  Última partida #1: 50 puntos\n  Última partida #2: 75 puntos\n\n");
        }
    }

    private JButton crearBotonTransparente(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
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

    @Override
    public void setModelo(ModeloDatos miModelo) {
        this.miModelo = miModelo;
    }

    @Override
    public void setControlador(Controlador Controlador) {
        this.controlador = Controlador;
    }
}
