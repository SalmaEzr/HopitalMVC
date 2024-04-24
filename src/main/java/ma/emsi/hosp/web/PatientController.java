package ma.emsi.hosp.web;

import lombok.AllArgsConstructor;
import ma.emsi.hosp.entities.Patient;
import ma.emsi.hosp.repositories.PatientRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepo patientRepo;
    @GetMapping( "/user/index")
    public String index(Model model,

                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "4") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String keyword){
        Page<Patient> pagePatients = patientRepo.findByNameContains(keyword, PageRequest.of(page,size));

        model.addAttribute("listPatient", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }


    @GetMapping("/admin/delete")
    public String delete(Long id,
                         @RequestParam(name = "keyword",defaultValue = "") String keyword,
                         @RequestParam(name = "page",defaultValue = "0") int page){
        patientRepo.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/user/displayDetails")
    public String displayDetails(Long id, Model model){
        Patient patient = patientRepo.findById(id).orElse(null);
        model.addAttribute("patient", patient);
        return "patientDetails";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }


    @GetMapping("/admin/formPatients")
    public String formPatient(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }


    @PostMapping("/admin/save")
    public String save(Model model,
                       @Validated Patient patient,
                       BindingResult bindResult,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "") String keyword

    ){
        if(bindResult.hasErrors()) return "formPatients";
        patientRepo.save(patient);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }


    @GetMapping("/admin/edit")
    public String edit(Model model, Long id,String keyword, int page){
        Patient patient = patientRepo.findById(id).orElse(null);
        if (patient == null) throw new RuntimeException("Patient not found");
        model.addAttribute("patient", patient);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "editPatient";
    }
}