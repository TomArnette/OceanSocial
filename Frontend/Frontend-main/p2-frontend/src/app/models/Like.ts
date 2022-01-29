import { UserFeedComponent } from "../pages/user-feed/user-feed.component";
import { PostService } from "../services/post/post.service";
import { UserService } from "../services/user/user.service";
import { Post } from "./Post";
import { User } from "./User";

export class Like {
  likeId?: number|undefined;
  post: { postId: number | undefined; } | undefined;
  user: { userId: number | undefined; } | undefined;
}
