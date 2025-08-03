package br.com.fiapfood.core.usecases.integracao.restaurante;

import br.com.fiapfood.core.gateways.impl.RestauranteGateway;
import br.com.fiapfood.core.gateways.impl.TipoCulinariaGateway;
import br.com.fiapfood.core.gateways.impl.UsuarioGateway;
import br.com.fiapfood.core.gateways.interfaces.IRestauranteGateway;
import br.com.fiapfood.core.gateways.interfaces.ITipoCulinariaGateway;
import br.com.fiapfood.core.gateways.interfaces.IUsuarioGateway;
import br.com.fiapfood.core.usecases.restaurante.impl.BuscarTodosRestaurantesUseCase;
import br.com.fiapfood.core.usecases.restaurante.interfaces.IBuscarTodosRestaurantesUseCase;
import br.com.fiapfood.infraestructure.repositories.interfaces.IRestauranteRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.ITipoCulinariaRepository;
import br.com.fiapfood.infraestructure.repositories.interfaces.IUsuarioRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Sql(scripts = {"/db_clean.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/db_load.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BuscarTodosRestaurantesUseCaseIT {

    private IBuscarTodosRestaurantesUseCase buscarTodosRestaurantesUseCase;
    private IRestauranteGateway restauranteGateway;
    private IUsuarioGateway usuarioGateway;
    private ITipoCulinariaGateway tipoCulinariaGateway;

    @Autowired
    private IRestauranteRepository restauranteRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private ITipoCulinariaRepository tipoCulinariaRepository;

    @BeforeEach
    void setUp() {
        restauranteGateway = new RestauranteGateway(restauranteRepository);
        usuarioGateway = new UsuarioGateway(usuarioRepository);
        tipoCulinariaGateway = new TipoCulinariaGateway(tipoCulinariaRepository);

        buscarTodosRestaurantesUseCase = new BuscarTodosRestaurantesUseCase(restauranteGateway, usuarioGateway, tipoCulinariaGateway);
    }

}
