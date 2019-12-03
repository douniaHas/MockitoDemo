package service;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmployeeManagerEntry {
    //log.util ??
    private static final Logger LOGGER = LogManager.getLogger(EmployeeManagerEntry.class);

    public static void main(String[] args){
        /*1- Instancie EmployeeManager avec son constructeur public par défaut
        * 2- Injecte les dépendances avec @Inject
        * 3- Instancie les dépendances avec leur constructeur public par défaut
        * */
        Injector injector = Guice.createInjector(new BasicModule());
        EmployeeManager employeeManager = injector.getInstance(EmployeeManager.class);
        LOGGER.info(employeeManager);
        employeeManager.payEmployees();
    }

}
