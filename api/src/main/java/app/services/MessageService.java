package app.services;

import app.entities.db.Message;
import app.entities.db.Report;

public interface MessageService {

    Message get(long id);
    long send(Message message);
    void remove(long id);
    void restore(long id);
    void report(Report report);
}
