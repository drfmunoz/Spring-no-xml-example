package org.cellcore.code.service;

import org.cellcore.code.model.Author;
import org.cellcore.code.shared.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthorController {

    @Autowired
    private AuthorControllerDelegate delegate;
    @Autowired
    private SessionContext sessionContext;

    @RequestMapping(value = "/ajax/author", produces = "application/json")
    @ResponseBody
    public List<Author> getAuthors() {
        return delegate.listAuthors();
    }

    @RequestMapping(value = "/ajax/author/check", produces = "application/json")
    @ResponseBody
    public boolean exists(@RequestParam("name") String name) {
        return delegate.exists(name);
    }

    @RequestMapping(value = "/ajax/author/{authorId}", produces = "application/json",method = RequestMethod.PUT)
    @ResponseBody
    public Author loadAuthor(@PathVariable long authorId) {
        Author current = delegate.loadAuthor(authorId);
        sessionContext.setAuthor(current);
        return current;
    }

    @RequestMapping(value = "/ajax/author/{authorId}", produces = "application/json",method = RequestMethod.GET)
    @ResponseBody
    public Author getAuthor(@PathVariable long authorId) {
        Author current = delegate.loadAuthor(authorId);
        return current;
    }


    @RequestMapping(value = "/ajax/author",consumes="application/json", produces = "application/json",method = RequestMethod.POST)
    @ResponseBody
    public Author createAuthor(@RequestBody Author author) {
        Author current = delegate.create(author);
        sessionContext.setAuthor(current);
        return current;
    }

    @RequestMapping(value = "index.html")
    public String authorIndex(){
        return "author";
    }
}
