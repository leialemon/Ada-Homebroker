package tech.ada.jjh.homebroker.service.create;

import org.springframework.stereotype.Service;
import tech.ada.jjh.homebroker.dto.AppUserDTOResponse;
import tech.ada.jjh.homebroker.dto.AppUserDTORequest;
import tech.ada.jjh.homebroker.mapper.AppUserMapper;
import tech.ada.jjh.homebroker.model.AppUser;
import tech.ada.jjh.homebroker.repository.UserRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final AppUserMapper appUserMapper;
    private final CreateAccessUserService createAccessUserService;

    public CreateUserService(UserRepository userRepository, AppUserMapper appUserMapper, CreateAccessUserService createAccessUserService){
        this.userRepository = userRepository;
        this.appUserMapper = appUserMapper;
        this.createAccessUserService = createAccessUserService;
    }

    public AppUserDTOResponse create(AppUserDTORequest appUserDTORequest){
        //Regra de idade. Retornar uma exceção caso a pessoa for menor de 18 anos?
        AppUser entity = appUserMapper.toAppUser(appUserDTORequest);
        entity.setTransactionHistory(new ArrayList<>());
        entity.setOrderHistory(new ArrayList<>());
        entity.setPortfolio(new HashMap<>());
        entity.setBalance(BigDecimal.ZERO);
        entity = userRepository.save(entity);
        createAccessUserService.createAccessUser(entity.getCpf(), entity.getPassword());
        return appUserMapper.toAppUserDTOResponse(entity);
    }
}
