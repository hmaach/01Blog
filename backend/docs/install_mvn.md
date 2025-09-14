### 🧱 Step 1: Check Java is installed (Maven requires it)

Run:

```bash
java -version
```

If it shows Java 17 or later, you're good. Otherwise, install Java first.

---

### 🧰 Step 2: Install Maven

#### 🔵 On Ubuntu/Debian (no sudo? skip below):

```bash
sudo apt update
sudo apt install maven
```

---

### 🔵 No `sudo`? Manual installation:

1. 📥 Download the latest Maven binary:

```bash
wget https://dlcdn.apache.org/maven/maven-3/3.8.9/binaries/apache-maven-3.8.9-bin.tar.gz
```

2. 📦 Extract it:

```bash
tar -xvzf apache-maven-3.8.9-bin.tar.gz
mv apache-maven-3.8.9 ~/maven
```

3. 🛠️ Add to your `PATH` (in `.bashrc` or `.zshrc`):

```bash
echo 'export PATH=~/maven/bin:$PATH' >> ~/.zshrc
source ~/.zshrc
```

4. ✅ Test it:

```bash
mvn -v
```