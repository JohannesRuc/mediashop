package de.mediashop.service;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Der Payment-Provider liefert Benachrichtigungstexte mit Platzhaltern
 * (z. B. "Zahlung ueber ${amount} ${currency} bestaetigt").
 */
@Service
public class ProviderTemplates {

    public String render(String template, Map<String, String> values) {
        StringSubstitutor substitutor = new StringSubstitutor(values);
        substitutor.setEnableUndefinedVariableException(false);
        return substitutor.replace(template);
    }
}
