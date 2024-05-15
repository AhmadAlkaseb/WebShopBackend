package routes;

import daos.ItemDAO;
import daos.UserDAO;
import persistence.HibernateConfig;
import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;

public class Route {
    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
    private static ItemDAO itemDAO = ItemDAO.getInstance(emf);
    private static UserDAO userDAO = UserDAO.getInstance(emf);
    private static RouteItem routeItem = new RouteItem(itemDAO);
    private static RouteUser routeUser = new RouteUser();

    // Declare a public static method named addRoutes which returns an EndpointGroup
    public static EndpointGroup addRoutes() {
        // Call the combineRoutes method passing the EndpointGroup instances returned by routeItem.hotelRoutes() and roomRoute.roomRoutes()
        return combineRoutes(routeItem.itemRoutes(), routeUser.securityRoutes());
    }

    // Define a private static method named combineRoutes which takes multiple EndpointGroup instances as arguments
    private static EndpointGroup combineRoutes(EndpointGroup... endpointGroups) {
        // Define a lambda expression for the EndpointGroup
        return () -> {
            // Iterate through each EndpointGroup passed as arguments
            for (EndpointGroup group : endpointGroups) {
                // Add the endpoints from each EndpointGroup
                group.addEndpoints();
            }
        };
    }
}
