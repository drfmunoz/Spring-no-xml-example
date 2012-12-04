package org.cellcore.code.service;

import org.cellcore.code.model.Message;
import org.cellcore.code.shared.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageControllerDelegate delegate;

    @Autowired
    SessionContext sessionContext;

    @RequestMapping(value = "/ajax/{authorId}/message", produces = "application/json", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Message> listMessagesExchange(@PathVariable long authorId,@RequestParam("starting") long starting) {
        return delegate.listMessagesExchange(sessionContext.getAuthor(), authorId, starting);
    }

    @RequestMapping(value = "/ajax/{authorId}/message", consumes="application/json",produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public boolean sendMessage(@PathVariable long authorId,@RequestBody Message message) {
        return delegate.sendMessage(sessionContext.getAuthor(),authorId,message);
    }

    @RequestMapping(value = "/message/index.html")
    public String messageIndex(Model model){
        model.addAttribute("author", sessionContext.getAuthor());
        return "messageHome";
    }

    @RequestMapping(value = "/message/{authorId}/index.html")
    public String messageExchange(@PathVariable long authorId,Model model){
        model.addAttribute("author", sessionContext.getAuthor());
        model.addAttribute("recipientId",authorId);
        return "message";
    }

}
