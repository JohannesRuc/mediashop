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

## Demo mit Docker (Training)

> **Absichtlich verwundbare Anwendung.** Nur lokal starten, nie ins Netz stellen.

Das Image baut sich selbst (keine lokale Java- oder Maven-Installation noetig) und laeuft ohne PostgreSQL:
H2 im Speicher mit den Testdaten.

```bash
docker build -t mediashop .
docker run --rm -p 127.0.0.1:8080:8080 mediashop
```

Danach z. B. `http://localhost:8080/catalog/products?q=Vinyl` oder `http://localhost:8080/catalog/products/p-1`.
Bereiche mit Login (`/orders`, `/invoices`) brauchen ein Demo-Token (bekommt ihr im Training) – statt Keycloak
prueft der Demo-Betrieb Tokens gegen den oeffentlichen Schluessel in `demo/jwt-public.pem`:

```bash
curl -H "Authorization: Bearer <token>" http://localhost:8080/orders/ord-1
```

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
