package com.spring.leaderelection.springleaderelectionapiusingk8sconfigmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.kubernetes.fabric8.leader.Fabric8LeadershipController;
import org.springframework.integration.leader.Candidate;
import org.springframework.stereotype.Service;

@Service
public class K8sFabric8LeaderService {

    @Autowired
    private Fabric8LeadershipController fabric8LeadershipController;

    @Autowired
    private Candidate candidate;

    public boolean isLeader(){
        try{
            return fabric8LeadershipController.getLocalLeader().get().isCandidate(this.candidate);
        }catch(Exception e){
            return Boolean.FALSE;
        }
    }

}
