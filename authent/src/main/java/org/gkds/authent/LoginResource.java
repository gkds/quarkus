package org.gkds.authent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {

    private static final String JWT_ISSUER = "https://kds.albourina.io/issuer";
    private Set<User> users = Collections.synchronizedSet(new LinkedHashSet<>());
    private Map<String,String> resetTokens = Collections.synchronizedMap(new HashMap<String, String>());

    private static final String USER_ROLE = "User";
    private static final String ADMIN_ROLE = "Admin";
    private static final int ACCESS_TOKEN_EXPIRES_IN = 200 ;// expires en 200 sec;

    public LoginResource() {
        users.add(new User("gkds", "Georges", "De souza", "gkds10@yahoo.fr", "Paris", "django12", ADMIN_ROLE));
        users.add(new User("vayakani", "Essivi", "D'almeida", "essivi20@caramail.fr", "Lille", "essivi12XX", USER_ROLE));
        users.add(new User("draurel", "Aurelien", "Nzotcha", "aurelien0@gmail.fr", "Lille", "aurelien10!", USER_ROLE));
        users.add(new User("secret1", "Secretaire", "Lila", "lila2secret@yopmail.fr", "Lyon", "20?secret10", USER_ROLE));

    }

    @Inject
    JWTParser parser;


    @POST
    @Path("/login")
    public LoginResult login(LoginRequest login) throws UserNotFoundException {

        Optional<User> userFound = users.stream()
                .filter(u -> u.login.equals(login.username) && u.password.equals(login.password)).findFirst();

        if (userFound.isPresent()) {
            User user = userFound.get();
            // ACCESS TOKEN
            String access_token = Jwt.issuer(JWT_ISSUER)
                    .upn(user.login)
                    .subject(user.email)
                    .groups(new HashSet<>(Arrays.asList(user.role)))
                    .claim(Claims.family_name, user.lastname) 
                    .expiresIn(ACCESS_TOKEN_EXPIRES_IN) 
                    .sign();

            // Refresh token
            String refresh_token = Jwt.issuer(JWT_ISSUER)
                    .upn(user.login)
                    .subject(user.email)
                    .groups(new HashSet<>(Arrays.asList(user.role)))
                    // .claim(Claims.family_name, user.lastname) // 10 min
                    .expiresIn(800) // expires en 800 sec
                    .sign();
            //System.out.println(">>> Token genere:" + access_token);

            return new LoginResult(user.login, user.role, user.lastname + ' ' + user.firstname, access_token,
                    refresh_token);
        }
        throw new UserNotFoundException();

    }

    @POST
    @Path("/refresh-token")
    public LoginResult refreshToken(RefreshRequest refreshRequest) throws ParseException, UserNotFoundException {

        JsonWebToken jwt = parser.parse(refreshRequest.refreshToken);
        if(resetTokens.containsKey(jwt.getName())){

            //JsonWebToken token = parser.parse(resetTokens.get(jwt.getName()));
            if(refreshRequest.refreshToken.equals(resetTokens.get(jwt.getName()))){
                throw new UnauthorizedException("Token not valid anymore");
            }
        }
       // System.out.println(">> RefreshToken jwt.getName = " + jwt.getName());
        String username = jwt.getName();
        Optional<User> userFound = users.stream().filter(
                u -> u.login.equals(username)
                    )
                .findFirst();

        if (userFound.isPresent()) {
            User user = userFound.get();

            String access_token = Jwt.issuer(JWT_ISSUER)
                    .upn(user.login)
                    .subject(jwt.getSubject())
                    .groups(jwt.getGroups())
                    .claim(Claims.family_name, user.lastname) // 10 min
                    .expiresIn(ACCESS_TOKEN_EXPIRES_IN) 
                    .sign();

            return new LoginResult(user.login, user.role, user.lastname + ' ' + user.firstname, access_token,
                    refreshRequest.refreshToken);
        }
        throw new UserNotFoundException();
    }

    @POST
    @Path("/logout")
    public String logout(RefreshRequest refreshRequest) throws ParseException{
        JsonWebToken jwt = parser.parse(refreshRequest.refreshToken);
        resetTokens.put(jwt.getName(), jwt.getRawToken());
        
        return "ok";
    }
}