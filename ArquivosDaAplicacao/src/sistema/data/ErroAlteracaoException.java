package sistema.data;

public class ErroAlteracaoException extends RuntimeException {
	public ErroAlteracaoException() {
		super("Erro ao alterar objeto no repositório!");
	}

	public ErroAlteracaoException(String mensagem) {
		super(mensagem);
	}
}
