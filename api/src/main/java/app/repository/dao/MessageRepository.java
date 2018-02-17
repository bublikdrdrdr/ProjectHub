package app.repository.dao;

import app.repository.entity.Message;
import app.repository.etc.MessageSearchParams;

public interface MessageRepository extends CrudRepository<Message>, Searchable<Message, MessageSearchParams> {

}
