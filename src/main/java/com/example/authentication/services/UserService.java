package com.example.authentication.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authentication.models.User;
import com.example.authentication.repositories.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;


// registrar el usuario y hacer Hash a su password

	public User registerUser(User user) {
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
		return userRepo.save(user);
	}
	
	// encontrar un usuario por su email

	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	// encontrar un usuario por su id
	public User findUserById(Long id) {
		Optional<User> u = userRepo.findById(id);

		if (u.isPresent()) {
			return u.get();
		} else {
			return null;
		}
	}

// autenticar usuario (LOGIN)

	public boolean authenticateUser(String email, String password) {
	// primero encontrar el usuario por su email
	        User user = userRepo.findByEmail(email);
	        System.out.println(user.getEmail() + " AQUI USER MAIL");
	// si no lo podemos encontrar por su email, retornamos false
	        if(user == null) {
	            return false;
	        } else {
	// si el password coincide devolvemos true, sino, devolvemos false
	            if(BCrypt.checkpw(password, user.getPassword())) {
	                return true;
	            } else {
	                return false;
	            }
	        }
	    }
	
	

}


