package at.snt.tms.rest.services;

import at.snt.tms.model.tender.Company;
import at.snt.tms.repositories.tender.CompanyRepository;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companies;

    @Autowired
    public CompanyService(CompanyRepository companies) {
        this.companies = companies;
    }

    public Iterable<Company> findAll() {
        return companies.findAll();
    }

    public Company findById(Long id) {
        Optional<Company> intState = companies.findById(id);
        if (intState.isEmpty()) {
            throw new IllegalStateException("Company not found for given id:" + id);
        }
        return intState.get();
    }

    public void add(@Header(value = "name") String name) {
        Company company = new Company(name);
        System.out.println("Add company to database: " + company);
        companies.save(company);
    }

    public void delete(Long id) {
        Company company = findById(id);
        System.out.println("Deleted internal state: " + company);
        companies.delete(company);
    }
}
