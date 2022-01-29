import { Post } from "./Post";
import { User } from "./User";

export class Comment {
  postId: number;
  postText: string;
  postTime: Date;
  postParentId: number;
  userId: number;
}
