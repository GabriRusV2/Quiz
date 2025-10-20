package vista;

import controlador.Controlador;
import modelo.ModeloDatos;

public interface Vista {
	public void setModelo(ModeloDatos miModelo);

	public void setControlador(Controlador Controlador);

}