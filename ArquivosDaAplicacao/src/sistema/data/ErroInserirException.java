package sistema.data;

public class ErroInserirException extends RuntimeException {
	public ErroInserirException() {
		super("Erro ao inserir objeto ao repositório!");
	}

	public ErroInserirException(String mensagem) {
		super(mensagem);
	}
}
