package app.service;

import app.repository.entity.Message;
import app.repository.entity.Report;
import app.repository.etc.SearchParams;

import java.util.List;

public interface MessageService {

    Message get(long id);
    long send(Message message);
    void remove(long id);
    void restore(long id);
    List<Message> get(long userId, SearchParams pagination);
    List<Message> getDialogs(SearchParams pagination);
    void report(Report report);
}
