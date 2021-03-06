/**
 * 
 */
package com.br.controladores;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.br.modelos.Lancamento;
import com.br.modelos.Pessoa;
import com.br.modelos.TipoLancamento;
import com.br.repositorio.LancamentoRepositorio;
import com.br.repositorio.PessoaRepositorio;
import com.br.servicos.CadastroLancamentos;
import com.br.servicos.NegocioException;

/**
 * @author Filipe Bezerra
 * 
 */
@Named
@javax.faces.view.ViewScoped
public class CadastroLancamentoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroLancamentos cadastro;

	@Inject
	private PessoaRepositorio pessoas;

	@Inject
	private LancamentoRepositorio lancamentos;
	
	private Lancamento lancamento = new Lancamento();
	private List<Pessoa> todasPessoas;

	public void prepararCadastro() {

		this.todasPessoas = this.pessoas.todas();
		
		if(this.lancamento == null){
			
			this.lancamento = new Lancamento();
		}
	}

	public void salvar() {

		FacesContext context = FacesContext.getCurrentInstance();

		try {

			this.cadastro.salvar(this.lancamento);
			this.lancamento = new Lancamento();

			context.addMessage(null, new FacesMessage("Lançamento cadastrado com sucesso!"));
		} catch (NegocioException e) {

			FacesMessage mensagem = new FacesMessage(e.getMessage());
			mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensagem);
		}
	}

	public void dataVencimentoAlterada(AjaxBehaviorEvent event) {
		if (this.lancamento.getDataPagamento() == null) {
			
			this.lancamento.setDataPagamento(this.lancamento.getDataVencimento());
		}
	}

	public List<String> pesquisarDescricoes (String descricao){
		
		return this.lancamentos.descricaoQueContem(descricao);
	}
	
	public List<Pessoa> getTodasPessoas() {
		return this.todasPessoas;
	}

	public TipoLancamento[] getTiposLancamentos() {

		return TipoLancamento.values();
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

}