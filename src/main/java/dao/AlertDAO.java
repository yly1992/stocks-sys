package dao;

import model.Alert;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class AlertDAO implements IAlertDAO {
	
	@PersistenceContext	
	private EntityManager entityManager;	
	
	public void addAlert(Alert alert) {
		entityManager.persist(alert);		
	}
   
   
}
