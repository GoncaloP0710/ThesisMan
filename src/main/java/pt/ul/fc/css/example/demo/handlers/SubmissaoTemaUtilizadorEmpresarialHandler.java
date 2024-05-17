// package pt.ul.fc.css.example.demo.handlers;

// import java.util.Optional;

// import org.springframework.stereotype.Component;

// import pt.ul.fc.css.example.demo.entities.Mestrado;
// import pt.ul.fc.css.example.demo.entities.Tema;
// import pt.ul.fc.css.example.demo.entities.UtilizadorEmpresarial;
// import pt.ul.fc.css.example.demo.repositories.MestradoRepository;
// import pt.ul.fc.css.example.demo.repositories.TemaRepository;
// import pt.ul.fc.css.example.demo.repositories.UtilizadorEmpresarialRepository;
// import pt.ul.fc.css.example.exceptions.NotPresentException;

// @Component
// public class SubmissaoTemaUtilizadorEmpresarialHandler {
//     private TemaRepository temaRepository;
//     private UtilizadorEmpresarialRepository utilizadorEmpresarialRepository;
//     private MestradoRepository mestradoRepository;

//     public SubmissaoTemaUtilizadorEmpresarialHandler(TemaRepository temaRepository, UtilizadorEmpresarialRepository utilizadorEmpresarialRepository,
//                                                  MestradoRepository mestradoRepository) {
//         this.temaRepository = temaRepository;
//         this.utilizadorEmpresarialRepository = utilizadorEmpresarialRepository;
//         this.mestradoRepository = mestradoRepository;
//     }

//     //!Só pra testar
//     public void submeterTema(Integer temaId, String descricao, float remuneracaoMensal, String email) throws NotPresentException {
//         Optional<UtilizadorEmpresarial> optUtilizadorEmpresarial = utilizadorEmpresarialRepository.findByEmail(email); 
//         if (optUtilizadorEmpresarial.isEmpty()) {
//             throw new NotPresentException("Utilizador empresarial não encontrado");
//         }
//         UtilizadorEmpresarial utilizadorEmpresarial = optUtilizadorEmpresarial.get();
//         Optional<Tema> optTema = temaRepository.findById(temaId);
//         if (!optTema.isEmpty()) {
//             throw new IllegalArgumentException("Tema já existe");
//         }
//         String titulo = "";
//         Tema tema = new Tema(titulo, descricao, remuneracaoMensal, utilizadorEmpresarial);
//         temaRepository.save(tema);

//         // TODO: No ThesisManApplication nao faziamos isto mas sera que nao deviamos?
//         utilizadorEmpresarial.addTemaPropostos(tema);
//         utilizadorEmpresarialRepository.save(utilizadorEmpresarial);
//     }

//     public void adicionarMestradoCompativel(String nome, Integer temaId, String email) throws NotPresentException{
//         if (utilizadorEmpresarialRepository.findByEmail(email).isEmpty()) {
//             throw new NotPresentException("Docente não encontrado");
//         }
//         Optional<Tema> optTema = temaRepository.findById(temaId);
//         if (optTema.isEmpty()) {
//             throw new NotPresentException("Tema não encontrado");
//         }
//         Tema tema = optTema.get();
//         String temaEmail = tema.getSubmissor().getEmail();
//         if(temaEmail.equals(email)){
//             throw new IllegalArgumentException("Utilizador Empresarial não é o submissor do tema");
//         }
//         Optional<Mestrado> optMestrado = mestradoRepository.findByNome(nome);
//         if(optMestrado.isEmpty()){
//             throw new NotPresentException("Mestrado não encontrado");
//         }
//         Mestrado mestrado = optMestrado.get();
//         tema.addMestradosCompativeis(mestrado);
//         temaRepository.save(tema);
//     }
// }
