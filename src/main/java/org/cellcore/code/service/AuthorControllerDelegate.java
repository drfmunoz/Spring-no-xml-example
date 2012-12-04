package org.cellcore.code.service;

import org.cellcore.code.dao.GeneralDao;
import org.cellcore.code.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Controller
public class AuthorControllerDelegate {

    @Autowired
    GeneralDao generalDao;

    @Transactional
    public List<Author> listAuthors() {
        return generalDao.list(Author.class);
    }

    @Transactional
    public Author loadAuthor(long authorId) {
        return generalDao.read(Author.class,authorId);
    }

    @Transactional
    public Author create(Author author) {
        return generalDao.save(author);
    }

    public boolean exists(String name) {
        return !generalDao.read(Author.class,"name",name).isEmpty();
    }
}
