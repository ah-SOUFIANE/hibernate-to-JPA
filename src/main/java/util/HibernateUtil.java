/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JPA utility class with a convenient method to get the EntityManagerFactory
 * object. The name of the persistence-unit ("hiber1PU") must match the one
 * declared in META-INF/persistence.xml.
 *
 * @author X1 YOGA
 */
public class HibernateUtil {

    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            // Create the EntityManagerFactory from the standard JPA
            // META-INF/persistence.xml config file.
            entityManagerFactory = Persistence.createEntityManagerFactory("hiber1PU");
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
