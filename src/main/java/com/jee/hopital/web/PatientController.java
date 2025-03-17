package com.jee.hopital.web;

import com.jee.hopital.entities.Patient;
import com.jee.hopital.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size", defaultValue = "4")int s,
                        @RequestParam(name = "keyword", defaultValue = "")String kw) {
//        Page<Patient> pagePatients = patientRepository.findAll(PageRequest.of(p,s));
        Page<Patient> pagePatients = patientRepository.findByNomContains(kw, PageRequest.of(p,s));
        model.addAttribute("ListPatients", pagePatients.getContent());
        model.addAttribute("pages", new int [pagePatients.getTotalPages()]);
        model.addAttribute("keyword", kw);
        model.addAttribute("currentPage", p);
        return "patient";
    }

    @GetMapping("/delete")
    public String delete(Long id,@RequestParam(name = "keyword", defaultValue = "") String keyword, @RequestParam(name = "page", defaultValue = "0") int page) {
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }


}
