package org.cellcore.code.service;

import org.cellcore.code.dao.GeneralDao;
import org.cellcore.code.model.Author;
import org.cellcore.code.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class MessageControllerDelegate {

    @Autowired
    GeneralDao generalDao;

    @Transactional
    public List<Message> listMessagesExchange(Author author, long authorId, long starting) {
        return generalDao.readMessages(author.getId(), authorId, starting);
    }

    @Transactional
    public boolean sendMessage(Author author, long authorId, Message message) {
        Author from=generalDao.read(Author.class,author.getId());
        Author to=generalDao.read(Author.class,authorId);
        message.setFromAuthor(from);
        message.setToAuthor(to);
        message.setSentDate(new Date());
        try{
            generalDao.save(message);
        }
        catch (Throwable t){
            return false;
        }
        return true;
    }
}
