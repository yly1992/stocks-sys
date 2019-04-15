package dao;

import model.Quote;
import model.QuoteId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class QuoteDAO implements IQuoteDAO {
	
   @PersistenceContext	
   private EntityManager entityManager;	
	
   public List<Quote> findByRange( String symbol, Calendar from, Calendar to ) {
      return findByRangeInBulk(new String[]{ symbol }, from, to).get( symbol );
   }

   public List<String> getLoadedSymbols() {
      String queryStr = "Select DISTINCT q.id.symbol From Quote q";
      queryStr += " ORDER BY q.id.symbol";
      return entityManager.createQuery(queryStr, String.class).getResultList();
   }

   public Map<String, List<Quote>> findByRangeInBulk( String[] symbols, Calendar from, Calendar to ) {
      Map<String, List<Quote>> resultMap = new HashMap<String, List<Quote>>();
      String queryStr = "Select q From Quote q";
      queryStr += " WHERE q.id.symbol IN :symbols" ;
      queryStr += " AND q.id.date >= :from AND q.id.date <= :to";
      queryStr += " AND q.close >= 0";
      queryStr += " ORDER BY q.id.date DESC";
      TypedQuery<Quote> query = entityManager.createQuery(queryStr, Quote.class);
      query.setParameter( "symbols", Arrays.asList( symbols ) );
      query.setParameter( "from", from );
      query.setParameter( "to", to );
      List<Quote> list = query.getResultList();
      for(Quote quote: list){
         putQuoteInMap( quote, resultMap );
      }
      return resultMap;
   }

   private void putQuoteInMap( Quote quote, Map<String, List<Quote>> map ) {
      List<Quote> internalList = map.get( quote.getSymbol() );
      if(internalList == null){
         internalList = new ArrayList<Quote>();
         map.put( quote.getSymbol(), internalList );
      }
      internalList.add( quote );
   }

   public void removeQuotes( String symbol ) {
      String queryStr = "DELETE From Quote q";
      queryStr += " WHERE q.id.symbol = :symbol" ;
      Query query = entityManager.createQuery(queryStr);
      query.setParameter( "symbol", symbol );
      query.executeUpdate();
   }

public void persist(Object entity) {
	// TODO Auto-generated method stub
	
}

public void remove(Object entity) {
	// TODO Auto-generated method stub
	
}
@Transactional
public void update(Object entity) {
	entityManager.merge(entity);
	
}

public Object findById(Integer id) {
	// TODO Auto-generated method stub
	return null;
}

   
}
