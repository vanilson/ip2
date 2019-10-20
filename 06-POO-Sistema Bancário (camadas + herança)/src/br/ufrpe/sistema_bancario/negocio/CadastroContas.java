package br.ufrpe.sistema_bancario.negocio;
import br.ufrpe.sistema_bancario.dados.RepositorioContasArray;
import br.ufrpe.sistema_bancario.negocio.beans.Conta;
import br.ufrpe.sistema_bancario.negocio.beans.Poupanca;

public class CadastroContas {

    private RepositorioContasArray repositorio;
    
    public CadastroContas() {
        this.repositorio = RepositorioContasArray.getInstance(); 
    }
    
    public void cadastrar(Conta c) {
        if (c == null) {
            throw new IllegalArgumentException("Par�metro inv�lido");
        } else {
            if (!this.existe(c.getNumero())) {
                this.repositorio.cadastrar(c);
            } else {
                // Aqui n�o dever haver impress�o de mensagem para o usu�rio, j�
                // que essa n�o � a responsabildiade do Cadastro/Controlador.
                // O problema � resolvido com o uso de exce��es
            }
        }        
    }
    
    public void descadastrar(String numConta) {
    	Conta c = this.repositorio.procurar(numConta);
    	if (c != null &&
    			c.getSaldo() == 0) {
    		this.repositorio.remover(numConta);
		} else {
		    // Aqui n�o dever haver impress�o de mensagem para o usu�rio, j�
            // que essa n�o � a responsabildiade do Cadastro/Controlador.
            // O problema � resolvido com o uso de exce��es
		}
    }

    public Conta procurar(String num) {
        return this.repositorio.procurar(num);
    }
    
    public boolean existe(String numConta) {
    	return this.repositorio.existe(numConta);
    }

    public void remover(String num) {
        this.repositorio.remover(num);
    }

    /**
     * Procura a conta cujo n�mero � passado como par�metro e credita o valor
     * tamb�m passado como par�metro
     * 
     * @param num N�mero da conta a ser procurada
     * @param valor Valor a ser creditado na conta encontrada
     */
    public void creditar(String num, double valor) {
        Conta contaCredito = this.repositorio.procurar(num);
        if (contaCredito != null) {
            contaCredito.creditar(valor);
        }
    }
    
    /**
     * Procura a conta cujo n�mero � passado como par�metro e debita o valor
     * tamb�m passado como par�metro. Note que n�o � preciso testar se o saldo �
     * v�lido para realizar o d�bito porque o m�todo "debitar" da classe Conta
     * j� testa essa situa��o
     * 
     * @param num N�mero da conta a ser procurada
     * @param valor Valor a ser debitado na conta encontrada
     */
    public void debitar(String num, double valor) {
        Conta contaDebito = this.repositorio.procurar(num);
        if (contaDebito != null) {
            contaDebito.debitar(valor);
        }
    }
    
    /**
     * Realiza transfer�ncia de valores entre as duas contas a partir dos
     * n�meros das contas passados como par�metro
     * 
     * @param numOrigem N�mero da conta origem
     * @param numDestino N�mero da conta destino
     * @param valor Valor a ser transferido
     * @throws ContaNaoExisteException 
     * @throws SaldoInsuficienteException 
     */
    public void transferir(String numOrigem, String numDestino, double valor) {
        Conta origem = this.repositorio.procurar(numOrigem);
        Conta destino = this.repositorio.procurar(numDestino);
        if (origem != null && destino != null && origem.getSaldo() >= valor) {
            origem.debitar(valor);
            destino.creditar(valor);
        } else {
            // Aqui n�o dever haver impress�o de mensagem para o usu�rio, j�
            // que essa n�o � a responsabildiade do Cadastro/Controlador.
            // Em cada um dos casos abaixo o problema � resolvido usando exce��es
            if (origem == null || destino == null) {
                // Lan�ar exce��o correspondente
            } else if (origem.getSaldo() < valor){
                // Lan�ar exce��o correspondente
            }
        }
    }
    
    public double getSaldo(String num) {
        Conta c = this.repositorio.procurar(num);
        return c.getSaldo();
    }
    
    public static final double TAXA_JUROS = 0.01;
    
    public void renderJuros(String num) {
        Conta c = this.repositorio.procurar(num);
        if (c instanceof Poupanca) {
            ((Poupanca) c).renderJuros(TAXA_JUROS);
        } else {
            // Aqui n�o dever haver impress�o de mensagem para o usu�rio, j�
            // que essa n�o � a responsabilidade do Cadastro/Controlador.
            // O problema � resolvido com o uso de exce��es
        }
    }
}
