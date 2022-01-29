import { User } from "./User";

export class Post {
  postId: number;
  postPicUrl: string|undefined;
  postText: string|undefined;
  postYouUrl: string|undefined;
  userId: number;
  user: User  = {
    userId: undefined,
    username: "",
    password: undefined,
    email: undefined,
    firstName: undefined,
    lastName: undefined,
    aboutMe: undefined,
    bday: undefined,
    proPicUrl: undefined
  };
}
