package fauxpas.entity;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Session {
    @Id Long id;
    @Index String accessToken;
    String refreshToken;
}
