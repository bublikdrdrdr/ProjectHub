package app.service;

import app.repository.entity.Message;
import app.repository.entity.Report;

public interface MessageService {

    Message get(long id);
    long send(Message message);
    void remove(long id);
    void restore(long id);
    void report(Report report);
}
