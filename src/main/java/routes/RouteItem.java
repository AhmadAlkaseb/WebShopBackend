package routes;

import controllers.ItemController;
import controllers.SecurityController;
import daos.ItemDAO;
import daos.UserDAO;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import logger.CustomLogger;
import persistence.HibernateConfig;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RouteItem {

    private static ItemDAO itemDAO;
    private static UserDAO userDAO;
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
    private static SecurityController securityController = new SecurityController(emf);
    private static CustomLogger customLogger = new CustomLogger();

    public RouteItem(ItemDAO itemDAO, UserDAO userDAO) {
        this.itemDAO = itemDAO;
        this.userDAO = userDAO;
    }

    public EndpointGroup itemRoutes() {
        return () -> {
            path("/items", () -> {
                before(securityController.authenticate());
                get("/", customLogger.handleExceptions(ItemController.getAll(itemDAO)), Role.ANYONE);
                get("/{id}", customLogger.handleExceptions(ItemController.getById(itemDAO)), Role.ANYONE);
                get("/personal/{user_id}", customLogger.handleExceptions(ItemController.getAllByEmail(itemDAO)), Role.USER, Role.ADMIN);
                post("/", customLogger.handleExceptions(ItemController.create(itemDAO)), Role.USER, Role.ADMIN);
                put("/{user_id}", customLogger.handleExceptions(ItemController.update(itemDAO, userDAO)), Role.USER, Role.ADMIN);
                delete("/{user_id}", customLogger.handleExceptions(ItemController.delete(itemDAO)), Role.USER, Role.ADMIN);
            });
        };
    }
}