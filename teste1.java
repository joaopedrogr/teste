import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskManagementResource {

    @Inject
    PessoaRepository pessoaRepository;

    @Inject
    TarefaRepository tarefaRepository;

    // Adicionar uma Pessoa (POST /api/pessoas)
    @POST
    @Path("/pessoas")
    @Transactional
    public void adicionarPessoa(Pessoa pessoa) {
        pessoa.persist();
    }

    // Alterar uma Pessoa (PUT /api/pessoas/{id})
    @PUT
    @Path("/pessoas/{id}")
    @Transactional
    public void alterarPessoa(@PathParam("id") Long id, Pessoa pessoaAtualizada) {
        Pessoa pessoa = pessoaRepository.findById(id);
        if (pessoa != null) {
            pessoa.nome = pessoaAtualizada.nome;
            pessoa.departamento = pessoaAtualizada.departamento;
            // Atualize outros campos conforme necessário
        }
    }

    // Remover uma Pessoa (DELETE /api/pessoas/{id})
    @DELETE
    @Path("/pessoas/{id}")
    @Transactional
    public void removerPessoa(@PathParam("id") Long id) {
        pessoaRepository.deleteById(id);
    }

    // Adicionar uma Tarefa (POST /api/tarefas)
    @POST
    @Path("/tarefas")
    @Transactional
    public void adicionarTarefa(Tarefa tarefa) {
        tarefa.persist();
    }

    // Alocar uma Pessoa na Tarefa (PUT /api/tarefas/alocar/{id})
    @PUT
    @Path("/tarefas/alocar/{id}")
    @Transactional
    public void alocarPessoaNaTarefa(@PathParam("id") Long id, Pessoa pessoa) {
        Tarefa tarefa = tarefaRepository.findById(id);
        if (tarefa != null && pessoa.departamento.equals(tarefa.departamento)) {
            tarefa.pessoa = pessoa;
        }
    }

    // Finalizar uma Tarefa (PUT /api/tarefas/finalizar/{id})
    @PUT
    @Path("/tarefas/finalizar/{id}")
    @Transactional
    public void finalizarTarefa(@PathParam("id") Long id) {
        Tarefa tarefa = tarefaRepository.findById(id);
        if (tarefa != null) {
            tarefa.finalizado = true;
        }
    }

    // Listar pessoas trazendo nome, departamento, total horas gastas nas tarefas (GET /api/pessoas)
    @GET
    @Path("/pessoas")
    public List<Pessoa> listarPessoas() {
        return pessoaRepository.listAll();
    }

    // Buscar pessoas por nome e período, retorna média de horas gastas por tarefa (GET /api/pessoas/gastos)
    @GET
    @Path("/pessoas/gastos")
    public String buscarPessoasPorNomeEPeriodo(@QueryParam("nome") String nome,
                                                @QueryParam("periodo") String periodo) {
        // Implemente lógica para buscar pessoas por nome e período e calcular média
        return "Média de horas gastas por tarefa para " + nome + " no período " + periodo;
    }

    // Listar 3 tarefas que estejam sem pessoa alocada com os prazos mais antigos (GET /api/tarefas/pendentes)
    @GET
    @Path("/tarefas/pendentes")
    public List<Tarefa> listarTarefasPendentes() {
        // Implemente lógica para listar tarefas pendentes
        return Collections.emptyList();
    }

    // Listar departamento e quantidade de pessoas e tarefas (GET /api/departamentos)
    @GET
    @Path("/departamentos")
    public List<String> listarDepartamentos() {
        // Implemente lógica para listar departamentos
        return Collections.emptyList();
    }
}
