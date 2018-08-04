/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cetic.rssfeeds.repository;

import be.cetic.rssfeeds.entities.Parameter;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author cetic
 */
public interface ParameterRepository extends CrudRepository<Parameter, Long>{
    
}
