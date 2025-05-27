# Netfix

Netfix is a Django-based service marketplace that connects customers with companies providing home services like plumbing, painting, electricity, and more.

## Features

- Two types of users: Customers and Companies
- Registration and login system
- Companies can create services
- Customers can request services
- Service category filtering and company profiles

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://learn.zone01oujda.ma/git/hmaach/netfix.git
cd netfix
````

### 2. Set Up a Virtual Environment (Recommended)

```bash
python3 -m venv venv
source venv/bin/activate  # On Windows use: venv\Scripts\activate
```


```bash
pip install django==3.1.14
```

### 3. Apply Migrations

```bash
python3 manage.py makemigrations
python3 manage.py migrate
```

### 4. Create a Superuser

```bash
python3 manage.py createsuperuser
```

Follow the prompts to create an admin account.

### 5. Run the Development Server

```bash
python3 manage.py runserver
```

Visit [http://127.0.0.1:8000](http://127.0.0.1:8000) in your browser.

---

## Project Structure 📁

```
netfix/
├── manage.py
├── static/
│   └── css/
├── templates/
├── netfix/          # Project settings
├── users/           # User app (registration, login, profiles)
├── services/        # Services app (create/request services)
├── main/            # Main app (home, navbar, etc.)
```

---

## Django Admin

Visit [http://127.0.0.1:8000/admin](http://127.0.0.1:8000/admin) to access the Django admin panel.

Use the superuser credentials you created earlier.
