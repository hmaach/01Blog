### 🔧 Install `mongosh` without sudo

#### 1. Download the tarball from MongoDB

Go to [MongoDB Download Center](https://www.mongodb.com/try/download/shell) and grab the latest **`mongosh` tar.gz** for Linux (x64).
Or with `wget` (example for Ubuntu 20.04 x64):

```bash
wget https://downloads.mongodb.com/compass/mongosh-2.5.7-linux-x64.tgz
```

*(replace version with the latest available)*

---

#### 2. Extract it somewhere in your home directory

```bash
tar -xvzf mongosh-2.5.7-linux-x64.tgz -C ~/
```

Now you’ll have something like:

```
~/mongosh-2.5.7-linux-x64/bin/mongosh
```

---

#### 3. Add it to your PATH

Edit your shell config (`.bashrc` or `.zshrc`):

```bash
echo 'export PATH=$HOME/mongosh-2.5.7-linux-x64/bin:$PATH' >> ~/.zshrc
source ~/.zshrc
```

---

#### 4. Test it

```bash
mongosh --version
```