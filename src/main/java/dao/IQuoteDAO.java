package dao;

public interface IQuoteDAO<E> {
	
	 void persist(E entity);

     void remove(E entity); 
     
     void update(E entity);

     public E findById(Integer id);
}
