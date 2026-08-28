package com.portfolio.service.admin.impl;

public class AdminCertificationServiceImpl {
    @Override
    @Transactional(readOnly=true)
    public List<CertificationResponse> getAllCertifications(){
        return certRepo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public
    CertificationResponse createCertification(CertificationRequest r){
        Certification c=new Certification(); mapper.applyRequest(c,r); return mapper.toResponse(certRepo.save(c));
    }

    @Override public CertificationResponse updateCertification(Long id, CertificationRequest r){
        Certification c=certRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Certificação",id));
        mapper.applyRequest(c,r); return mapper.toResponse(certRepo.save(c));
    }

    @Override public void deleteCertification(Long id){
        certRepo.deleteById(id);
    }

}
