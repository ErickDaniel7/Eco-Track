package io.bootify.eco_monitor.controller;

import io.bootify.eco_monitor.domain.Station;
import io.bootify.eco_monitor.model.DataDTO;
import io.bootify.eco_monitor.repos.StationRepository;
import io.bootify.eco_monitor.service.DataService;
import io.bootify.eco_monitor.util.CustomCollectors;
import io.bootify.eco_monitor.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/datas")
public class DataController {

    private final DataService dataService;
    private final StationRepository stationRepository;

    public DataController(final DataService dataService, final StationRepository stationRepository) {
        this.dataService = dataService;
        this.stationRepository = stationRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("sensorValues", stationRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Station::getId, Station::getId)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("datas", dataService.findAll());
        return "data/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("data") final DataDTO dataDTO) {
        return "data/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("data") @Valid final DataDTO dataDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "data/add";
        }
        dataService.create(dataDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("data.create.success"));
        return "redirect:/datas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("data", dataService.get(id));
        return "data/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("data") @Valid final DataDTO dataDTO, final BindingResult bindingResult,
            final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "data/edit";
        }
        dataService.update(id, dataDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("data.update.success"));
        return "redirect:/datas";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        dataService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("data.delete.success"));
        return "redirect:/datas";
    }

}
