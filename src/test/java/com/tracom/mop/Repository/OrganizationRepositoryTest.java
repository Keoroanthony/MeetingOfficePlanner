package com.tracom.mop.Repository;

import com.tracom.mop.Entity.Organization;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class OrganizationRepositoryTest {
    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void testCreateOrganization() {
        Organization organization = new Organization();
        organization.setId(5);
        organization.setName("skylab");

        Organization saveOrganization = organizationRepository.save(organization);
        Assertions.assertThat(saveOrganization).isNotNull();
        Assertions.assertThat(saveOrganization.getId()).isGreaterThan(0);
    }

}