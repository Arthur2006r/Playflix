package sistema.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistema.model.Genero;
import sistema.model.Usuario;

public class RepositorioGeneroDao implements IRepositorioGeneroDao {
	private Connection connection;

	private IRepositorioUsuarioDao repositorioUsuario;

	public RepositorioGeneroDao(IRepositorioUsuarioDao repositorioUsuario) {
		connection = new ConnectionFactory().getConnection();

		this.repositorioUsuario = repositorioUsuario;
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Genero> getGeneros() throws SQLException {
		String sql = "select * from Genero";
		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		List<Genero> copiaGeneros = new ArrayList<>();

		while (rs.next()) {
			Usuario criador = repositorioUsuario.getUsuario(rs.getInt("idUsuario"));

			Genero genero = Genero.getInstance(rs.getString("nome"), rs.getString("descricao"), criador);
			genero.setIdGenero(rs.getInt("idGenero"));

			copiaGeneros.add(genero);
		}

		stmt.close();

		return copiaGeneros;
	}

	@Override
	public List<Genero> getListaMeusGeneros(Usuario usuario) throws SQLException {
		String sql = "select * from Genero where idUsuario=?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, usuario.getIdUsuario());

		ResultSet rs = stmt.executeQuery();

		List<Genero> copiaGeneros = new ArrayList<>();

		while (rs.next()) {
			Usuario criador = repositorioUsuario.getUsuario(rs.getInt("idUsuario"));

			Genero genero = Genero.getInstance(rs.getString("nome"), rs.getString("descricao"), criador);
			genero.setIdGenero(rs.getInt("idGenero"));

			copiaGeneros.add(genero);
		}

		stmt.close();

		return copiaGeneros;
	}

	public Genero getGenero(int idGenero) throws SQLException {
		String sql = "select * from Genero where idGenero=?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, idGenero);

		ResultSet rs = stmt.executeQuery();

		Genero genero = null;

		if (rs.next()) {
			Usuario criador = repositorioUsuario.getUsuario(rs.getInt("idUsuario"));

			genero = Genero.getInstance(rs.getString("nome"), rs.getString("descricao"), criador);
			genero.setIdGenero(rs.getInt("idGenero"));
		}

		stmt.close();

		return genero;
	}

	@Override
	public void inserir(Genero genero) throws SQLException {
		if (genero != null) {
			if (!isNomeRepetido(genero)) {
				String sql = "insert into Genero " + "(nome, descricao, idUsuario) " + "values (?,?,?)";

				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, genero.getNome());
				stmt.setString(2, genero.getDescricao());
				stmt.setInt(3, genero.getCriador().getIdUsuario());

				stmt.execute();
				stmt.close();
			} else {
				throw new ErroInserirException(
						"Não foi possível inserir o gênero no reposiótio! Não é permitido gêneros com o nome igual. Tente novamente mais tarde.");
			}
		} else {
			throw new ErroInserirException(
					"Não foi possível inserir o gênero no reposiótio! O objeto gênero foi passado como nulo. Tente novamente mais tarde.");
		}
	}

	@Override
	public void alterar(Genero genero) throws SQLException {
		if (genero != null) {
			if (!isNomeRepetido(genero)) {
				String sql = "update Genero set nome=?, descricao=? where idGenero=?";
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, genero.getNome());
				stmt.setString(2, genero.getDescricao());
				stmt.setInt(3, genero.getIdGenero());

				stmt.execute();
				stmt.close();
			} else {
				throw new ErroAlteracaoException(
						"Não foi possível alterar o gênero no repositório! Não é permitido gêneros com o nome igual. Tente novamente mais tarde.");
			}
		} else {
			throw new ErroInserirException(
					"Não foi possível alterar o gênero no repositório! O objeto gênero foi passado como nulo. Tente novamente mais tarde.");
		}
	}

	@Override
	public void excluir(int idGenero) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete " + "from Genero where idGenero=?");
		stmt.setLong(1, idGenero);

		stmt.execute();
		stmt.close();
	}

	@Override
	public boolean isNomeRepetido(Genero genero) throws SQLException {
		if (genero != null) {
			for (Genero ge : getGeneros()) {
				if (ge.getNome().equals(genero.getNome()) && ge.getIdGenero() != genero.getIdGenero()) {
					return true;
				}
			}
		}

		return false;
	}
}
