package ma.ens.test;

import ma.ens.entities.Cours;
import ma.ens.entities.Etudiant;
import ma.ens.services.CoursService;
import ma.ens.services.EtudiantService;

public class TestManyToMany {
    public static void main(String[] args) {
        EtudiantService es = new EtudiantService();
        CoursService cs = new CoursService();

        Etudiant e = es.findById(2);
        if (e == null) {
            e = new Etudiant("Alaoui", "Ahmed", "CNE001");
            es.create(e);
            e = es.findById(e.getId());   // reload so courses is a real list, not null
        }

        Cours c1 = new Cours("Java");
        Cours c2 = new Cours("C++");
        Cours c3 = new Cours("PHP");
        cs.create(c1);
        cs.create(c2);
        cs.create(c3);

        e.getCourses().add(c1);
        e.getCourses().add(c2);
        es.update(e);
    }
}