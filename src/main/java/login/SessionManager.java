package login;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final Map<String, Session> sessions = new HashMap<>();

    private SessionManager() {
    }

    public static Session createSession() {
        Session session = new Session(UUID.randomUUID().toString());
        sessions.put(session.getId(), session);
        return session;
    }

    public static Session getSession(String id) {
        return sessions.get(id);
    }

}
