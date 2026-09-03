# MediaShop

Abo- und Bestellplattform eines Medienhaendlers. Spring Boot 3, Java 17.

## Aufbau

- `web/` – REST-Controller und Backoffice-Views (Katalog, Bestellungen, Rechnungen, Payment-Webhook)
- `repo/` – Datenzugriff via JDBC auf PostgreSQL
- `service/` – Payment-Anbindung, Token-/Nummerngenerierung, Provider-Templates
- `config/` – Spring Security (OIDC Resource Server), Provider-Konfiguration

## Lokal starten

```bash
mvn spring-boot:run
```

Tests laufen gegen eine In-Memory-H2 (`-Dspring.profiles.active=test`).

## Endpoints (Auszug)

| Methode | Pfad | Auth |
|---|---|---|
| GET | `/catalog/products?q=` | oeffentlich |
| GET | `/catalog/products/{id}` | oeffentlich |
| GET | `/orders/{id}` | Bearer-Token |
| GET | `/orders?sort=` | Bearer-Token |
| POST | `/orders/{id}/checkout` | Bearer-Token |
| GET | `/invoices/{id}` | Bearer-Token |
| POST | `/payments/callback` | Provider-Webhook |

---

> ## Hinweis
>
> **Dies ist ein Schulungsbeispiel und enthaelt absichtlich Sicherheitsmaengel.**
> Der Code dient ausschliesslich als Uebungsmaterial fuer Security-Trainings
> (Triage von SAST- und SCA-Findings). Er ist nicht fuer den produktiven
> Einsatz gedacht, und die verwendeten Abhaengigkeitsversionen sind bewusst
> veraltet. Das System, die Daten und alle Bezeichner sind frei erfunden.
