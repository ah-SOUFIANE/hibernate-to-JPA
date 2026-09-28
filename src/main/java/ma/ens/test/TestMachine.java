package ma.ens.test;

import java.util.Date;
import ma.ens.entities.Machine;
import ma.ens.entities.Salle;
import ma.ens.services.MachineService;
import ma.ens.services.SalleService;

public class TestMachine {
    public static void main(String[] args) {
        MachineService ms = new MachineService();
        SalleService ss = new SalleService();

        Salle s = ss.findById(3);
        if (s == null) {
            s = new Salle("S3", "Salle 3");
            ss.create(s);
        }

        ms.create(new Machine("HP", new Date("2020/09/02"), 1000, s));


        for (Machine m : ss.findById(s.getId()).getMachines())
            System.out.println(m);
    }
}