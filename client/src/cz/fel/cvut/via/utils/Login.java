package cz.fel.cvut.via.utils;

import cz.fel.cvut.via.entities.User;

public class Login {

	private static User loggedUser = null;
	
	public static User getLoggedUser() {
//		loggedUser = new User();
//		loggedUser.setEmail("pepik@a.cz");
//		loggedUser.setId(1);
//		loggedUser.setPassword("39bb1d9dab93707088db9117960a292bb26f91fd4857303ad0f2f51c4a4a14b4");
//		loggedUser.setToken("fadca0f0a625da0f9e090783348e68b34b6e749ce2e2559f8d060c65576e9564");
//		loggedUser.setUsername("pepa");
		return loggedUser;		
	}

	public static void setLoggedUser(User loggedUser) {
		Login.loggedUser = loggedUser;
	}
	
	
	
}
