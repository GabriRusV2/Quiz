package vista;

import controlador.Controlador;
import modelo.ModeloDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JuegoCultura extends JFrame implements Vista {

    private Controlador controlador;
    private ModeloDatos modelo;
    private JLabel lblPregunta;
    private JButton[] botonesOpcion;
    private ModeloDatos miModelo;

    // Colores y Constantes
    private static final Color COLOR_FONDO = new Color(240, 240, 240);
    private static final Color COLOR_PRIMARIO = new Color(59, 89, 182);
    private static final Color COLOR_TEXTO_BOTON = Color.WHITE;
    private static final Font FUENTE_PREGUNTA = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FUENTE_OPCION = new Font("Segoe UI", Font.BOLD, 14);

    // Constructor vacío para WindowBuilder
    public JuegoCultura() {
        this(null, null);
    }

    // Constructor principal
    public JuegoCultura(Controlador controlador, ModeloDatos modelo) {
        this.controlador = controlador;
        this.modelo = modelo;

        setTitle("Juego: Cultura General");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 750, 450);
        setLocationRelativeTo(null);
        getContentPane().setBackground(COLOR_FONDO);
        getContentPane().setLayout(new GridBagLayout()); // Centrado

        // --- Panel Contenedor Central ---
        JPanel panelContenedor = new JPanel(new BorderLayout(20, 20));
        panelContenedor.setBackground(Color.WHITE);
        panelContenedor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY.brighter(), 1, true),
                new EmptyBorder(30, 30, 30, 30)
        ));

        // 1. Pregunta
        lblPregunta = new JLabel("Pregunta ejemplo", SwingConstants.CENTER);
        lblPregunta.setFont(FUENTE_PREGUNTA);
        lblPregunta.setForeground(COLOR_PRIMARIO);
        lblPregunta.setBorder(new EmptyBorder(10, 0, 15, 0));
        panelContenedor.add(lblPregunta, BorderLayout.NORTH);

        // 2. Panel de opciones
        JPanel panelOpciones = new JPanel(new GridLayout(2, 2, 20, 20));
        panelOpciones.setOpaque(false);

        botonesOpcion = new JButton[4];
        for (int i = 0; i < 4; i++) {
            final int index = i;
            botonesOpcion[i] = crearBotonOpcion("Opción " + (i + 1));
            botonesOpcion[i].addActionListener(e -> {
                if (controlador != null) {
                    String textoCompleto = botonesOpcion[index].getText();
                    String respuesta = textoCompleto.length() > 3 ? textoCompleto.substring(3).trim() : textoCompleto;
                    controlador.procesarRespuestaCultura(respuesta);
                }
            });
            panelOpciones.add(botonesOpcion[i]);
        }

        panelContenedor.add(panelOpciones, BorderLayout.CENTER);

        // Añadir panel contenedor al centro de la ventana
        getContentPane().add(panelContenedor, new GridBagConstraints());

        // Inicializar vista para WindowBuilder
        actualizarVista();
    }

    // ----------------------------------------------------------------------
    // --- Lógica de la Vista ---
    // ----------------------------------------------------------------------
    public void actualizarVista() {
        if (modelo != null) {
            int actual = modelo.getPreguntaActual();
            if (actual < modelo.preguntasCultura.length) {
                String[] datos = modelo.preguntasCultura[actual];
                lblPregunta.setText("Pregunta " + (actual + 1) + ": " + datos[0]);

                for (int i = 0; i < 4; i++) {
                    char letra = (char) ('A' + i);
                    String textoOpcion = letra + ": " + datos[i + 1];
                    botonesOpcion[i].setText(textoOpcion);
                }
            }
        }
    }

    // ----------------------------------------------------------------------
    // --- Métodos Auxiliares de Estilo ---
    // ----------------------------------------------------------------------
    private JButton crearBotonOpcion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_OPCION);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(COLOR_TEXTO_BOTON);
        boton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
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
    public void setControlador(Controlador Controlador) {
        this.controlador = Controlador;
    }
}
