/**
 * 
 */
package com.br.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.br.modelos.Pessoa;

/**
 * @author Filipe Bezerra
 * 
 */
public class PessoaRepositorio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private EntityManager manager;

	@Inject
	public PessoaRepositorio(EntityManager manager) {

		this.manager = manager;
	}

	public Pessoa porId(Long id) {

		return manager.find(Pessoa.class, id);
	}

	public List<Pessoa> todas() {

		TypedQuery<Pessoa> query = manager.createQuery("from Pessoa",
				Pessoa.class);
		return query.getResultList();

	}

	public void adicionar(Pessoa pessoa) {

		EntityTransaction transacao = this.manager.getTransaction();
		transacao.begin();
		this.manager.persist(pessoa);
		transacao.commit();
	}
}
