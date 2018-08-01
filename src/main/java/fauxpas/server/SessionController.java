package fauxpas.server;

import com.google.cloud.datastore.Entity;
import fauxpas.entities.SessionRecord;
import fauxpas.utils.GDatastore;
import fauxpas.utils.TokenGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @RequestMapping("/test")
    public String testSession() {

        SessionRecord session = new SessionRecord("client1", TokenGenerator.generateToken(), 0, 100);
        Entity seshEntity = session.toEntity();
        GDatastore.setEntity(seshEntity);

        session = GDatastore.getSession("client1").orElse(
              new SessionRecord("empty", "blank", -1, -1));


        return session.getClient_id()+" " +session.getAccessToken()+" " +session.getIssued()
              +" " +session.getTtl();

    }

}
