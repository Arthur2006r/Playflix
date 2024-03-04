package sistema.data;

public class ErroExcluirException extends RuntimeException {
	public ErroExcluirException() {
		super("Erro ao excluir objeto no repositório!");
	}

	public ErroExcluirException(String mensagem) {
		super(mensagem);
	}
}
