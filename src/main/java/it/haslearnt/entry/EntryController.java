package it.haslearnt.entry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EntryController {

    private final EntryRepository entryRepository;

    public EntryController(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/entry/submit")
    public String submit(@RequestParam String when, @RequestParam String text, @RequestParam String difficulty) {
        entryRepository.save(new Entry().today().iveLearnt(text).andItWas(difficulty));
        return "/";
    }
}
