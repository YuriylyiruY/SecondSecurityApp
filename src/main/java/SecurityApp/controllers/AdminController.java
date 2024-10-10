package SecurityApp.controllers;


import SecurityApp.models.Auth;
import SecurityApp.models.User;
import SecurityApp.services.PeopleServiceImp;
import SecurityApp.services.PersonDetailsService;
import SecurityApp.services.RegistrationServiceImp;
import SecurityApp.services.RoleService;
import SecurityApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class AdminController {

    private final PeopleServiceImp peopleService;
    private final PersonValidator personValidator;
    private final RegistrationServiceImp registrationService;

    private final RoleService roleService;

    @Autowired
    public AdminController(PeopleServiceImp peopleService, PersonValidator personValidator, RegistrationServiceImp registrationService, RoleService roleService) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.registrationService = registrationService;

        this.roleService = roleService;
    }


    @GetMapping("/adminPage")
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "adminPage";
    }


    @GetMapping("/newAdmin")
    public String newAdmin(@ModelAttribute("persona") User user) {
        return "admin";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("person") @Valid User user,
                         @ModelAttribute("ttt") Auth auth,
                         BindingResult bindingResult) {

        // personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "new";
        registrationService.makeEncode(user);
        roleService.addRolesToTable(user);
        System.out.println(auth.getRole());
        System.out.println(auth.getIdForAuth());
        //user.setAuths(auth);
        registrationService.registerAdmin(user, auth);

        peopleService.save(user);
        return "redirect:/adminPage";
    }

    @PostMapping("/a")
    public String createSuperAdmin(@ModelAttribute("persona") @Valid User user,
                                   BindingResult bindingResult) {

        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "admin";

        registrationService.registerSuperAdmin(user);

        peopleService.save(user);
        return "redirect:/userPage";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        return "show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") User user,
                            @ModelAttribute("ttt") Auth auth,
                            Model model) {
        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);

        // model.addAttribute("auths", auths);
        return "new";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id, @ModelAttribute("ttt") Auth auth) {
        model.addAttribute("person", peopleService.findOne(id));

        List<String> list = Arrays.asList("ADMIN", "USER");

        model.addAttribute("list", list);
        return "edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid User user, BindingResult bindingResult,
                         @ModelAttribute("ttt") Auth auth,
                         HttpServletRequest request) {
//        if (bindingResult.hasErrors())
//            return "edit";
        int id = Integer.parseInt(request.getParameter("id"));
        registrationService.registerAdmin(user, auth);
        peopleService.update(id, user);
        return "redirect:/adminPage";
    }

    @PostMapping("/del/{id}")
    public String delete(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        peopleService.delete(id);
        return "redirect:/adminPage ";
    }


}
