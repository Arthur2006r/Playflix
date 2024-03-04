package sistema.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import sistema.model.Genero;
import sistema.model.Jogo;
import sistema.model.Usuario;

public class RepositorioJogoDao implements IRepositorioJogoDao {
	private Connection connection;

	private IRepositorioUsuarioDao repositorioUsuario;
	private IRepositorioGeneroDao repositorioGenero;

	public RepositorioJogoDao(IRepositorioUsuarioDao repositorioUsuario, IRepositorioGeneroDao repositorioGenero) {
		this.connection = new ConnectionFactory().getConnection();

		this.repositorioUsuario = repositorioUsuario;
		this.repositorioGenero = repositorioGenero;
	}

	public RepositorioJogoDao(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public List<Jogo> getJogos() throws SQLException {
		String sql = "select * from Jogo";
		PreparedStatement stmt = connection.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		List<Jogo> copiaJogos = new ArrayList<>();

		while (rs.next()) {
			Usuario desenvolvedor = repositorioUsuario.getUsuario(rs.getInt("idUsuario"));
			Genero genero = repositorioGenero.getGenero(rs.getInt("idGenero"));

			Jogo jogo = Jogo.getInstance(rs.getString("nome"), rs.getString("descricao"), rs.getString("codigoFonte"),
					rs.getString("faixaEtaria"), desenvolvedor, genero);
			jogo.setIdJogo(rs.getInt("idJogo"));

			copiaJogos.add(jogo);
		}

		stmt.close();

		return copiaJogos;
	}

	@Override
	public List<Jogo> getListaMeusJogos(Usuario usuario) throws SQLException {
		String sql = "select * from Jogo where idUsuario=?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setLong(1, usuario.getIdUsuario());

		ResultSet rs = stmt.executeQuery();

		List<Jogo> copiaJogos = new ArrayList<>();

		while (rs.next()) {
			Usuario desenvolvedor = repositorioUsuario.getUsuario(rs.getInt("idUsuario"));
			Genero genero = repositorioGenero.getGenero(rs.getInt("idGenero"));

			Jogo jogo = Jogo.getInstance(rs.getString("nome"), rs.getString("descricao"), rs.getString("codigoFonte"),
					rs.getString("faixaEtaria"), desenvolvedor, genero);
			jogo.setIdJogo(rs.getInt("idJogo"));

			copiaJogos.add(jogo);
		}

		stmt.close();

		return copiaJogos;
	}

	public Jogo getJogo(int idJogo) throws SQLException {
		String sql = "select * from Jogo where idJogo=?";

		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, idJogo);

		ResultSet rs = stmt.executeQuery();

		Jogo jogo = null;

		if (rs.next()) {
			Usuario desenvolvedor = repositorioUsuario.getUsuario(rs.getInt("idUsuario"));
			Genero genero = repositorioGenero.getGenero(rs.getInt("idGenero"));

			jogo = Jogo.getInstance(rs.getString("nome"), rs.getString("descricao"), rs.getString("codigoFonte"),
					rs.getString("faixaEtaria"), desenvolvedor, genero);
			jogo.setIdJogo(rs.getInt("idJogo"));
		}

		stmt.close();

		return jogo;
	}

	@Override
	public void inserir(Jogo jogo) throws SQLException {
		if (jogo != null) {
			if (!isNomeRepetido(jogo)) {
				String sql = "insert into Jogo "
						+ "(nome, descricao, codigoFonte, faixaEtaria, dataPublicacao, idUsuario, idGenero) "
						+ "values (?,?,?,?,?,?,?)";

				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, jogo.getNome());
				stmt.setString(2, jogo.getDescricao());
				stmt.setString(3, jogo.getCodigoFonte());
				stmt.setString(4, jogo.getFaixaEtaria());
				stmt.setTimestamp(5, new Timestamp(jogo.getDataPublicacao().getTime()));
				stmt.setInt(6, jogo.getDesenvolvedor().getIdUsuario());
				stmt.setInt(7, jogo.getGenero().getIdGenero());

				stmt.execute();
				stmt.close();
			} else {
				throw new ErroInserirException(
						"Não foi possível inserir o jogo no repositório! Não é permitido jogos com o nome igual. Tente novamente mais tarde.");
			}
		} else {
			throw new ErroInserirException(
					"Não foi possível inserir o jogo no repositório! O objeto jogo foi passado como nulo. Tente novamente mais tarde.");
		}
	}

	@Override
	public void alterar(Jogo jogo) throws SQLException {
		if (jogo != null) {
			if (!isNomeRepetido(jogo)) {
				String sql = "update Jogo set nome=?, descricao=?, codigoFonte=?, faixaEtaria=?, idGenero=? where idJogo=?";
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, jogo.getNome());
				stmt.setString(2, jogo.getDescricao());
				stmt.setString(3, jogo.getCodigoFonte());
				stmt.setString(4, jogo.getFaixaEtaria());
				stmt.setInt(5, jogo.getGenero().getIdGenero());
				stmt.setInt(6, jogo.getIdJogo());

				stmt.execute();
				stmt.close();
			} else {
				throw new ErroAlteracaoException(
						"Não foi possível alterar o jogo no repositório! Não é permitido jogos com o nome igual. Tente novamente mais tarde.");
			}
		} else {
			throw new ErroInserirException(
					"Não foi possível alterar o jogo no repositório! O objeto jogo foi passado como nulo. Tente novamente mais tarde.");
		}
	}

	@Override
	public void excluir(int idJogo) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("delete " + "from Jogo where idJogo=?");
		stmt.setLong(1, idJogo);

		stmt.execute();
		stmt.close();
	}

	public boolean isNomeRepetido(Jogo jogo) throws SQLException {
		if (jogo != null) {
			for (Jogo jg : getJogos()) {
				if (jg.getNome().equals(jogo.getNome()) && jg.getIdJogo() != jogo.getIdJogo()) {
					return true;
				}
			}
		}

		return false;
	}
}
