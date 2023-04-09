package modelo;

public class Cliente {
	protected int matricula;
	protected String nome;
	protected String endereco;
	protected String modalidade;
	
	public Cliente() {
	}
	
	public Cliente(String nome, String enredeco, String modalidade) {
		super();
		this.nome = nome;
		this.endereco = enredeco;
		this.modalidade = modalidade;
	}
	
	public Cliente(int matricula, String nome, String endereco, String modalidade) {
		super();
		this.matricula = matricula;
		this.nome = nome;
		this.endereco = endereco;
		this.modalidade = modalidade;
	}
	
	public int getMatricula() {
		return matricula;
	}
	
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEnredeco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getModalidade() {
		return modalidade;
	}
	
	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
	
}
