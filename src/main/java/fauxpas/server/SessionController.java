package fauxpas.server;

import fauxpas.entity.Session;
import fauxpas.utils.TokenGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.googlecode.objectify.ObjectifyService.ofy;

@RestController
public class SessionController {

    @RequestMapping("/test")
    public String test() {
        Session se = new Session(TokenGenerator.generateToken(), TokenGenerator.generateToken());
        ofy().save().entity(se).now();

        assert se.getId() != null;

        Session fetched2 = ofy().load().type(Session.class).id(se.getId()).now();

        return se.getId()+": "+se.getAccessToken();
    }

}
