package mn.security.jwt.controllers;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;

@Controller("/app")
public class ServiceController {

    @Secured({ "ALL" })
    @Get(produces = MediaType.TEXT_PLAIN)
    String getJwtGreetings(){
        return "Greetings from JWT Auth";
    }
}
