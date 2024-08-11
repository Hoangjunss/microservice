package com.baconbao.manager_service.services.service;

import com.baconbao.manager_service.dto.AuthenticationRequest;
import com.baconbao.manager_service.dto.CompanyDTO;
import com.baconbao.manager_service.models.Company;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    CompanyDTO findById(Integer id);
    CompanyDTO create(CompanyDTO company);
    CompanyDTO update(CompanyDTO company);
    void deleteById(Integer id);
    List<CompanyDTO> getCompanyDTOs();
    List<CompanyDTO> getCompanyByType(String type);
    CompanyDTO setHRToCompany(AuthenticationRequest authenticationRequest,Integer idCompany);
    CompanyDTO deleteHRToCompany(Integer idHR, Integer idCompany);
}
