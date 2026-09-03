package de.mediashop.web;

import de.mediashop.repo.CatalogRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class CatalogController {

    private final CatalogRepository catalog;

    public CatalogController(CatalogRepository catalog) {
        this.catalog = catalog;
    }

    @GetMapping("/catalog/products")
    @ResponseBody
    public List<Map<String, Object>> search(@RequestParam(name = "q", defaultValue = "") String query) {
        return catalog.search(query);
    }

    @GetMapping("/catalog/products/{id}")
    public String product(@PathVariable String id, Model model) {
        model.addAttribute("product", catalog.findById(id));
        return "product";
    }

    /**
     * Vorschau fuer das Backoffice: rendert den Suchbegriff mit, damit Support sieht,
     * was der Kunde eingegeben hat.
     */
    @GetMapping("/catalog/preview")
    public void preview(@RequestParam("q") String query, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.getWriter().write("<h1>Suchvorschau</h1><p>Treffer fuer: " + query + "</p>");
    }
}
