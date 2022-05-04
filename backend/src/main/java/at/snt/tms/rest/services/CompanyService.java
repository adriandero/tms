package at.snt.tms.rest.services;

import at.snt.tms.model.database.tender.Company;
import at.snt.tms.repositories.tender.CompanyRepository;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends GenericCrudRepoService<Company>{

    @Autowired
    public CompanyService(CompanyRepository companies) {
        super(companies, Company.class);
    }

    public void add(@Header(value = "name") String name) {
        Company company = new Company(name);
        System.out.println("Add company to database: " + company);
        repository.save(company);
    }
}
