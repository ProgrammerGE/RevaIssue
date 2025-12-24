export interface IssueData {
  issue_id: number;
  issue_description: string;
  project_id: number;
  severity: number;
  priority: number;
  status: string;
  comment_chain: Array<CommentChain>;
}
//placeholder need to define how to get array of comments
interface CommentChain {}
