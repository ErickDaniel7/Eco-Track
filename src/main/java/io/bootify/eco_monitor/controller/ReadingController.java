package io.bootify.eco_monitor.controller;

import io.bootify.eco_monitor.domain.Station;
import io.bootify.eco_monitor.model.ReadingDTO;
import io.bootify.eco_monitor.repos.StationRepository;
import io.bootify.eco_monitor.service.ReadingService;
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
@RequestMapping("/alerts")
public class ReadingController {

    private final ReadingService readingService;
    private final StationRepository stationRepository;

    public ReadingController(final ReadingService readingService,
                             final StationRepository stationRepository) {
        this.readingService = readingService;
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
        model.addAttribute("alerts", readingService.findAll());
        return "alert/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("alert") final ReadingDTO readingDTO) {
        return "alert/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("alert") @Valid final ReadingDTO readingDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "alert/add";
        }
        readingService.create(readingDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("alert.create.success"));
        return "redirect:/alerts";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("alert", readingService.get(id));
        return "alert/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("alert") @Valid final ReadingDTO readingDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "alert/edit";
        }
        readingService.update(id, readingDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("alert.update.success"));
        return "redirect:/alerts";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        readingService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("alert.delete.success"));
        return "redirect:/alerts";
    }

}
