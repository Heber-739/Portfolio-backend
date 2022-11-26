
package com.portfolio.Security.Service;

import com.portfolio.Security.Entity.Rol;
import com.portfolio.Security.Enums.RolName;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.portfolio.Security.Repository.IRolRepository;

@Service
@Transactional
public class RolService {
    @Autowired
    IRolRepository iRolRepository;
    
    public Optional<Rol> getByRolName(RolName rolName){
        return iRolRepository.findByRolName(rolName);
    }
    
    public void save(Rol rol){
        iRolRepository.save(rol);
    }
    
}
