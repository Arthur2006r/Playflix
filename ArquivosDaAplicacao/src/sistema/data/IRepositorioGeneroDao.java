package sistema.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import sistema.model.Genero;
import sistema.model.Usuario;

public interface IRepositorioGeneroDao {
//	public void init();

	public Connection getConnection();

	public List<Genero> getGeneros() throws SQLException;
	
	public List<Genero> getListaMeusGeneros(Usuario usuario) throws SQLException;
	
	public Genero getGenero(int idGenero) throws SQLException;

	public void inserir(Genero genero) throws SQLException;

	public void alterar(Genero genero) throws SQLException;

	public void excluir(int idGenero) throws SQLException;

	public boolean isNomeRepetido(Genero genero) throws SQLException;
}
