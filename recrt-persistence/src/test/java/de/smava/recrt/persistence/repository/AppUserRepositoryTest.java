package de.smava.recrt.persistence.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.smava.recrt.persistence.config.PersistenceConfig;
import de.smava.recrt.persistence.model.AppUserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
public class AppUserRepositoryTest {

	@Autowired
	private AppUserRepository appUserRepository;

	@Test
	public void testSave() throws Exception {

		AppUserEntity appUserEntity = new AppUserEntity("user4");
		appUserEntity.setEmail("user4@smava.de");
		appUserEntity.setPassword("4444");
		appUserEntity = appUserRepository.save(appUserEntity);

		assertThat(appUserEntity, is(notNullValue()));
		assertThat(appUserEntity.getUsername(), is(equalTo("user4")));

	}

	@Test
	public void testFindOne() throws Exception {
		AppUserEntity appUserEntity = new AppUserEntity("user6");
		appUserEntity.setEmail("user6@smava.de");
		appUserEntity.setPassword("6666");
		appUserEntity = appUserRepository.save(appUserEntity);

		appUserEntity = appUserRepository.findOne("user6");

		assertThat(appUserEntity, is(notNullValue()));
		assertThat(appUserEntity.getUsername(), is(equalTo("user6")));

	}

	@Test
	public void testDelete() throws Exception {

		AppUserEntity appUserEntity = new AppUserEntity("user5");
		appUserEntity.setEmail("user5@smava.de");
		appUserEntity.setPassword("5555");
		appUserEntity = appUserRepository.save(appUserEntity);

		appUserEntity = appUserRepository.findOne("user5");
		assertThat(appUserEntity, is(notNullValue()));
		appUserRepository.delete("user5");
		appUserEntity = appUserRepository.findOne("user5");
		assertThat(appUserEntity, is(nullValue()));
	}
}