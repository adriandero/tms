package at.snt.tms.rest.services;

import at.snt.tms.model.tender.Company;
import at.snt.tms.repositories.tender.CompanyRepository;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends GenericCrudRepoService<Company, Long>{

    @Autowired
    public CompanyService(CompanyRepository companies) {
        super(companies, Company.class);
    }

    public void add(@Header(value = "name") String name) {
        repository.save(new Company(name));
    }
}
