package edu.eci.dosw.oficioya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.HashMap;
import edu.eci.dosw.oficioya.service.User;
import edu.eci.dosw.oficioya.service.LoggingStrategy;

@SpringBootApplication
public class OficioyaApplication {
	private HashMap<String,User> users;
	private LoggingStrategy loggingStrategy;

	public static void main(String[] args) {
		SpringApplication.run(OficioyaApplication.class, args);
	}

	private void initialize(){
		this.users = new HashMap<String,User>();
	}

}
