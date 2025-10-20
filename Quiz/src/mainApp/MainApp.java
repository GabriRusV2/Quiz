package mainApp;
//Me gusta el sexo
import controlador.Controlador;
import modelo.ModeloDatos;
import java.awt.EventQueue;

public class MainApp {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				// 1. Crear el Modelo 
				ModeloDatos modelo = new ModeloDatos();

				// 2. Crear el Controlador 
				Controlador controlador = new Controlador(modelo);

				// 3. Iniciar la aplicación mostrando la primera Vista
				controlador.iniciarAplicacion();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
