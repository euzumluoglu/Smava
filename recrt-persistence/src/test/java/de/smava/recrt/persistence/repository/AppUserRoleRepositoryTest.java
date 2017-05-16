package de.smava.recrt.persistence.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.smava.recrt.model.UserRole;
import de.smava.recrt.persistence.config.PersistenceConfig;
import de.smava.recrt.persistence.model.AppUserEntity;
import de.smava.recrt.persistence.model.AppUserRoleEntity;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
public class AppUserRoleRepositoryTest {
	
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private AppUserRoleRepository appUserRoleRepository;

    @Test
    public void testFindByKeyAppUser() throws Exception {
    	
    	AppUserEntity appUser = new AppUserEntity("userwithRole");
		appUser.setEmail("userwithRole@smava.de");
		appUser.setPassword("withRole");
		appUser = appUserRepository.save(appUser);
		
    	List<AppUserRoleEntity> appUserRoles = appUserRoleRepository.findByKeyAppUser(appUser);
    	   	
     	assertThat(appUserRoles.isEmpty(), is(equalTo(true)));
    	
    	AppUserRoleEntity appUserRole = new AppUserRoleEntity();
    	appUserRole.setAppUser(appUser);
    	appUserRole.setRole(UserRole.ROLE_USER);

     	appUserRoleRepository.save(appUserRole);
    	appUserRoles = appUserRoleRepository.findByKeyAppUser(appUser);
 
    	assertThat(appUserRoles.size(), is(equalTo(1)));
    	assertThat(appUserRoles.get(0).getRole(), is(equalTo(UserRole.ROLE_USER)));

    }
}