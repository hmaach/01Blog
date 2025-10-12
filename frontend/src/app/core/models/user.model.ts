export class User {
  id: string;
  name: string;
  username: string;
  email: string;
  avatar: string;
  token: string;

  constructor(
    id: string,
    name: string,
    username: string,
    email: string,
    avatar: string,
    token: string
  ) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.email = email;
    this.avatar = avatar;
    this.token = token;
  }
}
