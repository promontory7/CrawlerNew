package com.crawler.MyUtils

import com.crawler.bean.Project
import org.hibernate.Session
import org.hibernate.Transaction
import org.hibernate.cfg.Configuration

@Singleton(strict = false)
class SessionFactory{

    SessionFactory(){
        instance = new Configuration().addResource.configure().buildSessionFactory();
    }

    def getFactory(){
        if (!instance){
            instance = new Configuration().addResource.configure().buildSessionFactory();
        }
        return instance
    }
}

@Singleton(strict = false)
class HibernateUtils{
   def save2Hibernate(Project project){

       org.hibernate.SessionFactory sf = SessionFactory.instance.getFactory()
       Session s = null;
       Transaction t = null;

       try {
           s = sf.openSession();
           t = s.beginTransaction();
           s.save(project);
           t.commit();
       } catch (Exception err) {
           t.rollback();
           err.printStackTrace();
       } finally {
           s.close();
       }
   }

}