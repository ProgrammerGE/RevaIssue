export interface IssueData {
  issue_id: number;
  issue_title: string;
  issue_description: string;
  project_id: number;
  severity: number;
  priority: number;
  status: issueStatus;
  comment_chain: Array<CommentChain>;
}
//placeholder need to define how to get array of comments
interface CommentChain {}

export type issueStatus = 'OPEN' | 'IN_PROGRESS' | 'RESOLVED' | 'CLOSED';
